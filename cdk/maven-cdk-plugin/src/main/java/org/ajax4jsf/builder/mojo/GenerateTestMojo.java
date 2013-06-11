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

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.generator.ComponentTestGenerator;
import org.ajax4jsf.builder.generator.TagTestGenerator;
import org.ajax4jsf.builder.maven.MavenLogger;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @author dbiatenia
 * @goal generate-tests
 * @requiresDependencyResolution test
 * @phase generate-test-sources
 * @execute phase="test-compile"
 */
public class GenerateTestMojo extends GenerateMojo{

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().debug("GenerateTestMojo tests");
		if (null != executedProject) {
			Taglib taglib = checkLibraryConfig();
			// compile goal executed
			try {
				// Parse config files.
				ClassLoader generatiorLoader = createProjectClassLoader(executedProject);
				BuilderConfig config = createConfig(generatiorLoader);
				MavenLogger mavenLogger = new MavenLogger(getLog());
				// TODO - parse sources by qdox for JavaDoc comments ?
				// GenerateMojo components.
/*				
				ComponentGenerator compGenerator = new ComponentGenerator(this,
						mavenLogger);
				compGenerator.createFiles(config);
*/				
				// GenerateMojo renderers
/*				
				RendererGenerator rendererGenerator = new RendererGenerator(
						this, mavenLogger);
				rendererGenerator.setSrcDir(templatesDirectory);
				rendererGenerator.createFiles(config);
*/				
				// GenerateMojo component test 
				ComponentTestGenerator componentTestGenerator = new ComponentTestGenerator(this, mavenLogger);
				componentTestGenerator.setDestDir(outputTestsDirectory);
				componentTestGenerator.createFiles(config);
				
				// GenerateMojo tags
/*				
				TagGenerator tagGenerator = new TagGenerator(this, mavenLogger);
				tagGenerator.createFiles(config);
*/				
				// GenerateMojo tag test
				TagTestGenerator tagTestGenerator = new TagTestGenerator(this, mavenLogger);
				tagTestGenerator.setDestDir(outputTestsDirectory);
				tagTestGenerator.createFiles(config);
				
				// GenerateMojo tag handlers
/*				
				TagHandlerGenerator tagHandlerGenerator = new TagHandlerGenerator(
						this, mavenLogger);
				tagHandlerGenerator.createFiles(config);
				//Generate listeners
				ListenerGenerator listenerGenerator = new ListenerGenerator(this, mavenLogger);
				listenerGenerator.createFiles(config);
*/
/*				
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
						return GenerateTestMojo.this.getTemplate(name);
					}

					public String getTemplatesPath() {
						return GenerateTestMojo.this.getTemplatesPath();
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
				// Generate resources configuration file resources-config.xml
				// for all images/scripts/css...
				// Add generated sources and resources to project
*/				 
//				project.addCompileSourceRoot(outputJavaDirectory.getPath());
				project.addTestCompileSourceRoot(outputTestsDirectory.getPath());
/*				
				Resource resource = new Resource();
				resource.setDirectory(outputResourcesDirectory.getPath());
//				resource.setTargetPath("META-INF");
				project.addResource(resource);
*/				
			} catch (Exception e) {
				getLog().error("Error on generate component", e);
				throw new MojoExecutionException(
						"Error in component generation", e);
			}
		}

	}

}
