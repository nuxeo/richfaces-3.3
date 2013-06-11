/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.builder.mojo;

import java.io.File;
import java.io.FilenameFilter;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ParsingException;
import org.ajax4jsf.builder.generator.ComponentGenerator2;
import org.ajax4jsf.builder.generator.ComponentTagGenerator;
import org.ajax4jsf.builder.generator.ConverterGenerator;
import org.ajax4jsf.builder.generator.ConverterTagGenerator;
import org.ajax4jsf.builder.generator.FaceletsTaglibGenerator;
import org.ajax4jsf.builder.generator.FacesConfigGenerator;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.generator.ListenerGenerator;
import org.ajax4jsf.builder.generator.ListenerTagGenerator;
import org.ajax4jsf.builder.generator.RenderKitBean;
import org.ajax4jsf.builder.generator.RendererGenerator;
import org.ajax4jsf.builder.generator.ResourcesConfigGenerator;
import org.ajax4jsf.builder.generator.ResourcesConfigParser;
import org.ajax4jsf.builder.generator.ResourcesDependenciesGenerator;
import org.ajax4jsf.builder.generator.TagHandlerGenerator;
import org.ajax4jsf.builder.generator.TaglibGenerator;
import org.ajax4jsf.builder.generator.ValidatorGenerator;
import org.ajax4jsf.builder.generator.ValidatorTagGenerator;
import org.ajax4jsf.builder.maven.MavenLogger;
import org.ajax4jsf.builder.velocity.BuilderContext;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * This plugin geterate JSF components and renderers source code, as well as
 * configuration files.
 * 
 * @author shura
 * @goal generate
 * @requiresDependencyResolution compile
 * @phase generate-sources
 * @execute phase="compile"
 */
public class GenerateMojo extends AbstractCDKMojo implements
		JSFGeneratorConfiguration {

	/**
	 * Project executed by first compile lifecycle.
	 * 
	 * @parameter expression="${executedProject}"
	 * @readonly
	 */
	protected MavenProject executedProject;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().debug("GenerateMojo components");
		
		//FIXME: configure plexus component instead of programmatic property set.
		
		//		velocity.getEngine().setProperty("velocimacro.library", getTemplatesPath() + "/VM_global_library.vm");
		
		if (null != executedProject) {
			Taglib taglib = checkLibraryConfig();
			ClassLoader contextCL = Thread.currentThread().getContextClassLoader();			
			// compile goal executed
			try {
				// Parse config files.
				ClassLoader generatiorLoader = createProjectClassLoader(executedProject);
				Thread.currentThread().setContextClassLoader(generatiorLoader);
				BuilderConfig config = createConfig(generatiorLoader);
				MavenLogger mavenLogger = new MavenLogger(getLog());
				// TODO - parse sources by qdox for JavaDoc comments ?
				// GenerateMojo components.
				ComponentGenerator2 compGenerator = new ComponentGenerator2(this,
						mavenLogger);
				compGenerator.createFiles(config);
				// GenerateMojo validators
				ValidatorGenerator validatorGenerator = new ValidatorGenerator(this, mavenLogger);
				validatorGenerator.createFiles(config);
				// GenerateMojo converters
				ConverterGenerator converterGenerator = new ConverterGenerator(this, mavenLogger);
				converterGenerator.createFiles(config);
				// GenerateMojo renderers
				RendererGenerator rendererGenerator = new RendererGenerator(
						this, mavenLogger);
				rendererGenerator.setSrcDir(templatesDirectory);
				rendererGenerator.createFiles(config);
				// GenerateMojo component test
/*				
				ComponentTestGenerator componentTestGenerator = new ComponentTestGenerator(this, mavenLogger);
				componentTestGenerator.setDestDir(outputTestsDirectory);
				componentTestGenerator.createFiles(config);
*/				
				// GenerateMojo tags
				ComponentTagGenerator componentTagGenerator = new ComponentTagGenerator(this, mavenLogger);
				componentTagGenerator.createFiles(config);
            
//        GenerateMojo tags for validators
            ValidatorTagGenerator validatorTagGenerator = new ValidatorTagGenerator(this, mavenLogger);
            validatorTagGenerator.createFiles(config);
            
//          GenerateMojo tags for converters
            ConverterTagGenerator converterTagGenerator = new ConverterTagGenerator(this, mavenLogger);
            converterTagGenerator.createFiles(config);
				

            ListenerTagGenerator listenerTagGenerator = new ListenerTagGenerator(this, mavenLogger);
            listenerTagGenerator.createFiles(config);

            
            // GenerateMojo tag test
/*				
				TagTestGenerator tagTestGenerator = new TagTestGenerator(this, mavenLogger);
				tagTestGenerator.setDestDir(outputTestsDirectory);
				tagTestGenerator.createFiles(config);
*/				
				// GenerateMojo tag handlers
				TagHandlerGenerator tagHandlerGenerator = new TagHandlerGenerator(
						this, mavenLogger);
				tagHandlerGenerator.createFiles(config);
				//Generate listeners
				ListenerGenerator listenerGenerator = new ListenerGenerator(this, mavenLogger);
				listenerGenerator.createFiles(config);
				
				JSFGeneratorConfiguration resourcesConfiguration = new JSFGeneratorConfiguration() {

					public ClassLoader getClassLoader() {
						return createProjectClassLoader(executedProject);
					}

					public File getDestDir() {
						return outputResourcesDirectory;
					}

					public String getKey() {
						return key;
					}

					public Template getTemplate(String name) throws GeneratorException {
						return GenerateMojo.this.getTemplate(name);
					}

					public String getTemplatesPath() {
						return GenerateMojo.this.getTemplatesPath();
					}

				};
				// GenerateMojo faces-config
				FacesConfigGenerator configGenerator = new FacesConfigGenerator(
						resourcesConfiguration, mavenLogger);
				configGenerator.setInclude(facesConfigInclude);
				configGenerator.setFacesconfig(new File(
						outputResourcesDirectory, "META-INF/faces-config.xml"));
				RenderKitBean renderKitBean = configGenerator.createRenderKit();
				renderKitBean.setRenderkitid("HTML_BASIC");
				configGenerator.createFiles(config);
				// GenerateMojo JSP taglib
				if (null != taglib) {
					TaglibGenerator taglibGenerator = new TaglibGenerator(
							resourcesConfiguration, mavenLogger);
					taglibGenerator.setUri(taglib.getUri());
					taglibGenerator.setShortname(taglib.getShortName());
					taglibGenerator.setListenerclass(taglib.getListenerClass());
					taglibGenerator.setDisplayname(taglib.getDisplayName());
					taglibGenerator.setListenerclass(taglib.getListenerClass());
					taglibGenerator.setValidatorclass(taglib.getValidatorClass());
					taglibGenerator.setTlibversion(taglib.getTlibVersion());
					taglibGenerator.setJspversion(taglib.getJspVersion());
					taglibGenerator.setInclude(taglibInclude);
					taglibGenerator.setTaglib(new File(
							outputResourcesDirectory, "META-INF/"+taglib.getShortName()
									+ ".tld"));
					taglibGenerator.createFiles(config);
					// GenerateMojo Facelets taglib
					FaceletsTaglibGenerator faceletsTaglibGenerator = new FaceletsTaglibGenerator(
							resourcesConfiguration, mavenLogger);
					faceletsTaglibGenerator.setUri(taglib.getUri());
					faceletsTaglibGenerator.setShortname(taglib.getShortName());
					faceletsTaglibGenerator.setInclude(taglibInclude);
					faceletsTaglibGenerator.setTaglib(new File(
							outputResourcesDirectory, "META-INF/"+taglib.getShortName()
									+ ".taglib.xml"));
					faceletsTaglibGenerator.createFiles(config);
				}
				
				ResourcesConfigParser resourcesConfigParser = new ResourcesConfigParser(resourcesConfiguration, mavenLogger);
				resourcesConfigParser.setTemplates(templatesDirectory);
				resourcesConfigParser.parse(config);
				
				if (taglib != null) {
					ResourcesDependenciesGenerator resourcesDependenciesGenerator = new ResourcesDependenciesGenerator(resourcesConfiguration, mavenLogger);
					resourcesDependenciesGenerator.setUri(taglib.getUri());
					resourcesDependenciesGenerator.setDependencyFile(new File(outputResourcesDirectory, 
							"META-INF/" + taglib.getShortName() + ".component-dependencies.xml"));
					resourcesDependenciesGenerator.setComponentDependencies(resourcesConfigParser.getComponentResourcesMap());
					resourcesDependenciesGenerator.createFiles(config);
				}
				
				// Generate resources configuration file resources-config.xml
				// for all images/scripts/css...
				ResourcesConfigGenerator resourcesConfigGenerator = new ResourcesConfigGenerator(resourcesConfiguration, mavenLogger);
				resourcesConfigGenerator.setResourcesConfigGeneratorBean(resourcesConfigParser.getResourcesConfigGeneratorBean());
				resourcesConfigGenerator.setInclude(resourcesInclude);
				resourcesConfigGenerator.setTemplates(templatesDirectory);
				resourcesConfigGenerator.setResourcesConfig(new File(
						outputResourcesDirectory, "META-INF/resources-config.xml"));
				resourcesConfigGenerator.createFiles(config);
				
				// Add generated sources and resources to project
				project.addCompileSourceRoot(outputJavaDirectory.getPath());
//				project.addCompileSourceRoot(outputTestsDirectory.getPath());
				
				Resource resource = new Resource();
				resource.setDirectory(outputResourcesDirectory.getPath());
//				resource.setTargetPath("META-INF");
				project.addResource(resource);
			} catch (Exception e) {
				getLog().error("Error on generate component", e);
				throw new MojoExecutionException(
						"Error in component generation", e);
			}
			Thread.currentThread().setContextClassLoader(contextCL);
		}

	}

	protected BuilderConfig createConfig(ClassLoader generatiorLoader)
			throws ParsingException {
		BuilderConfig builderConfig = new BuilderConfig(generatiorLoader,
				new MavenLogger(getLog()));
		// Get all *.xml config files
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {

				return name.toLowerCase().endsWith(".xml");
			}

		};
		boolean filesParsed = false;
		File [] directories = {
				componentConfigDirectory, 
				validatorConfigDirectory, 
				converterConfigDirectory
				};
		
		for (File directory : directories) {
			if (directory.exists()) {
				File[] files = directory.listFiles(filter);
				for (File file : files) {
					filesParsed = true;
					builderConfig.parseConfig(file);
				}
			}
		}
		if (!filesParsed) {
			getLog().warn("No component configuration files found -- probably a skin project");
		}
		/*
		if (!filesParsed) {
			throw new ParsingException("No config files found");
		}
		*/
		builderConfig.checkComponentProperties();
		return builderConfig;
	}

	public File getDestDir() {
		// TODO Auto-generated method stub
		return outputJavaDirectory;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.builder.generator.JSFGeneratorConfiguration#getClassLoader()
	 */
	public ClassLoader getClassLoader() {
		return createProjectClassLoader(executedProject);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.JSFGeneratorConfiguration#getTemplate(java.lang.String)
	 */
	public Template getTemplate(String name) throws GeneratorException {
		// TODO Auto-generated method stub
		try {
			return this.velocity.getEngine().getTemplate(name);
		} catch (ResourceNotFoundException e) {
			throw new GeneratorException(e.getLocalizedMessage());
		} catch (ParseErrorException e) {
			throw new GeneratorException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new GeneratorException(e.getLocalizedMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.JSFGeneratorConfiguration#getTemplatesPath()
	 */
	public String getTemplatesPath() {		
		return Library.JSF12.equals(library.getJsfVersion())?BuilderContext.TEMPLATES12_PATH:BuilderContext.TEMPLATES_PATH;
	}

}
