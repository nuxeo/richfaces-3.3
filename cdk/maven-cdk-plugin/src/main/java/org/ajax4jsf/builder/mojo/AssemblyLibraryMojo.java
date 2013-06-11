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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ajax4jsf.builder.config.ParsingException;
import org.ajax4jsf.builder.xml.NamesListComparator;
import org.ajax4jsf.builder.xml.XMLBody;
import org.ajax4jsf.builder.xml.XMLBodyMerge;
import org.ajax4jsf.builder.xml.XPathComparator;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Resource;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.apache.maven.project.ProjectBuildingException;
import org.apache.velocity.VelocityContext;
import org.codehaus.plexus.archiver.UnArchiver;
import org.codehaus.plexus.archiver.manager.ArchiverManager;
import org.codehaus.plexus.util.DirectoryScanner;
import org.w3c.dom.Node;

/**
 * This plugin assembly full components library from modules, included in parent
 * project. Steps to create library : 1. Got parent project, and check modules
 * included in them. 2. For every module , build project and got it properties.
 * 3. Resolve module artifact, check for "jar" type and include/exclude
 * criteria. for accepted modules, unpack it to classes directory, and put
 * config files in separate directories. 4. Append unpacked directory to
 * resources, included in result jar. 5. Merge all META-INF/faces-config.xml ,
 * *.tld, *.taglib.xml, *.component-dependencies.xml , resources-config.xml into ones. 
 * 6. append dependencies of included projects to this.
 * 
 * @author shura
 * @goal assembly
 * @requiresDependencyResolution compile
 * @phase generate-resources
 */
public class AssemblyLibraryMojo extends AbstractCDKMojo {

	private static final String TEMPLATES_PREFIX = "/templates/";

	private static final String TEMPLATES12_PREFIX = "/templates12/";

	private static final String FACES_CONFIG_TEMPLATE = "faces-config.vm";

	private static final String RESOURCES_CONFIG_TEMPLATE = "resources-config.vm";

	private static final String COMPONENT_DEPENDENCIES_TEMPLATE = "component-dependencies.vm";

	private static final String XCSS_TEMPLATE = "xcss.vm";

	private static final String TLD_TEMPLATE = "tld.vm";

	private static final String TAGLIB_TEMPLATE = "taglib.vm";

	private static final String[] TLD_TAG_NAMES = new String[] {
		"description", "display-name", "icon", "tlib-version", 
		"short-name", "uri", "validator", "listener", "tag", 
		"tag-file", "function", "taglib-extension"
	};

	private static final String[] TAGLIB_TAG_NAMES = new String[] {
		"library-class", "namespace", "tag", "function"
	};
	
	private static final String[] DEPENDENCIES_TAG_NAMES = new String[] {
		"component"
	};

	private static final Comparator<Node> TLD_COMPARATOR = new NamesListComparator(
			new XPathComparator("listener-class/text()", "name/text()"), TLD_TAG_NAMES);
	
	private static final Comparator<Node> FACELET_COMPARATOR = new NamesListComparator(
			new XPathComparator("function-name/text()", "tag-name/text()"), TAGLIB_TAG_NAMES);
	
	private static final Comparator<Node> DEPENDENCIES_COMPARATOR = new NamesListComparator(
			new XPathComparator("name/text()"), DEPENDENCIES_TAG_NAMES);

	/**
	 * Used to look up Artifacts in the remote repository.
	 * 
	 * @component
	 */
	private org.apache.maven.artifact.factory.ArtifactFactory factory;

	/**
	 * Used to look up Artifacts in the remote repository.
	 * 
	 * @component
	 */
	private org.apache.maven.artifact.resolver.ArtifactResolver resolver;

	/**
	 * The local repository.
	 * 
	 * @parameter expression="${localRepository}"
	 */
	private ArtifactRepository localRepository;

	/**
	 * To look up Archiver/UnArchiver implementations
	 * 
	 * @component
	 */
	private ArchiverManager archiverManager;

	/**
	 * Project builder
	 * 
	 * @component
	 */
	private MavenProjectBuilder mavenProjectBuilder;

	/**
	 * The reactor projects.
	 * 
	 * @parameter expression="${reactorProjects}"
	 * @required
	 * @readonly
	 */
	private List reactorProjects;

	/**
	 * The reactor projects.
	 * 
	 * @parameter expression="${project.parent}"
	 * @readonly
	 */
	private MavenProject parentProject;

	/**
	 * The list of resources we want to transfer.
	 * 
	 * @parameter default-value="src/main/config"
	 */
	private File config;

	/**
	 * The directory for compiled classes.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 * @readonly
	 */
	private File buildDirectory;

	/**
	 * @parameter default-value="${project.build.directory}/modules"
	 */
	private File modulesDirectory;

	/**
	 * The directory for compiled classes.
	 * 
	 * @parameter expression="${project.build.directory}/pom.xml"
	 * @required
	 * @readonly
	 */
	private File generatedPom;

	/**
	 * The directory for compiled classes.
	 * 
	 * @parameter expression="${project.build.directory}/src"
	 * @required
	 * @readonly
	 */
	private File modulesSrc;

	/**
	 * @parameter
	 */
	private String templates;

	/**
	 * @parameter
	 */
	private String includeTld = "META-INF/*.tld";

	/**
	 * @parameter
	 */
	private String includeTaglib = "META-INF/*.taglib.xml";

	/**
	 * @parameter
	 */
	private String includeDependencies = "META-INF/*.component-dependencies.xml";

	/**
	 * @parameter
	 */
	private String includeXcss = "**/*.xcss";

	/**
	 * @parameter
	 */
	private String excludeXcss = null;

	/**
	 * @parameter
	 */
	private String commonStyle;

	/**
	 * @parameter
	 */
	private  String templateXpath;

	/**
	 * 
	 */
	public AssemblyLibraryMojo() {
		// used for plexus init.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		setupParentProject();
		checkLibraryConfig();
		if (null == templates) {
			templates = Library.JSF12.equals(library.getJsfVersion()) ? TEMPLATES12_PREFIX
					: TEMPLATES_PREFIX;
		}
		List<Model> models = extractModules();
		mergeFacesConfig(models);
		File resourcesConfig = new File(outputDirectory,
				"META-INF/resources-config.xml");
		mergeXML(models, "META-INF/resources-config.xml", null,
				RESOURCES_CONFIG_TEMPLATE, "/resource-config/resource",
				"name/text()", new VelocityContext(), resourcesConfig, false, null);
		
		if (null != commonStyle) {
			File commonXcss = new File(outputDirectory, commonStyle);
			mergeXML(models, includeXcss, excludeXcss, XCSS_TEMPLATE, 
					templateXpath, null, new VelocityContext(), commonXcss, true, null);
		}

		if (null != library.getTaglibs() && library.getTaglibs().length > 0) {
			for (int i = 0; i < library.getTaglibs().length; i++) {
				Taglib taglib = library.getTaglibs()[i];
				List<Model> taglibModels;
				if (taglib.getIncludeModules() == null
						&& taglib.getExcludeModules() == null) {
					taglibModels = models;
				} else {
					String[] includeModules = null;
					String[] excludeModules = null;
					if (null != taglib.getIncludeModules()) {
						includeModules = taglib.getIncludeModules().split(",");
						Arrays.sort(includeModules);
					}
					if (null != taglib.getExcludeModules()) {
						excludeModules = taglib.getExcludeModules().split(",");
						Arrays.sort(excludeModules);
					}
					taglibModels = new ArrayList<Model>();
					for (Iterator iterator = models.iterator(); iterator
							.hasNext();) {
						Model model = (Model) iterator.next();
						String id = model.getArtifactId();
						if ((includeModules == null || Arrays.binarySearch(
								includeModules, id) >= 0)
								&& (excludeModules == null || Arrays
										.binarySearch(excludeModules, id) < 0)) {
							taglibModels.add(model);
						}
					}
				}
				generateTaglibAndDependencies(taglibModels, taglib);
			}
		} else {
			generateTaglibAndDependencies(models, library.getTaglib());
		}
	}

	/**
	 * @param models
	 * @throws MojoExecutionException
	 */
	private void generateTaglibAndDependencies(List<Model> models, Taglib taglib)
			throws MojoExecutionException {
		getLog().debug(
				"Assembly taglib for uri " + taglib.getUri()
						+ " with short name " + taglib.getShortName());
		VelocityContext taglibContext = new VelocityContext();
		taglibContext.put("taglib", taglib);
		// Build includes/excludes Xpath condition
		String nameTag = "name";
		createTagCondition(taglib, nameTag);
		File tld = new File(outputDirectory, "META-INF/" + taglib.getTaglib()
				+ ".tld");
		getLog().debug("Write JSP taglib " + tld.getPath());
		String commonXPath = "/taglib/tag"
			+ createTagCondition(taglib, "name") + " | /taglib/listener | /taglib/function";
		
		mergeXML(models, includeTld, null, TLD_TEMPLATE, commonXPath,
				"listener-class/text() | name/text()", new VelocityContext(taglibContext), tld, false, TLD_COMPARATOR);
		
		
		File faceletsTaglib = new File(outputDirectory, "META-INF/"
				+ taglib.getTaglib() + ".taglib.xml");
		commonXPath = "/facelet-taglib/tag"
			+ createTagCondition(taglib, "tag-name")
			+ " | /facelet-taglib/function";
		
		mergeXML(models, includeTaglib, null, TAGLIB_TEMPLATE, commonXPath, "tag-name/text() | function-name/text()", new VelocityContext(
				taglibContext), faceletsTaglib, false, FACELET_COMPARATOR);
		getLog().debug("Write Facelets taglib " + faceletsTaglib.getPath());
	
		File dependenciesFile = new File(outputDirectory, "META-INF/"
				+ taglib.getTaglib() + ".component-dependencies.xml");
		commonXPath = "/components/component" + createTagCondition(taglib, "name");
		
		mergeXML(models, includeDependencies, null, COMPONENT_DEPENDENCIES_TEMPLATE, commonXPath, "name/text()", new VelocityContext(
				taglibContext), dependenciesFile, false, DEPENDENCIES_COMPARATOR);
		getLog().debug("Write dependencies file " + dependenciesFile.getPath());
	}

	/**
	 * @param taglib
	 * @param nameTag
	 */
	private String createTagCondition(Taglib taglib, String nameTag) {
		StringBuffer condition = new StringBuffer();
		if (taglib.getIncludeTags() != null || taglib.getExcludeTags() != null) {
			condition.append('[');
			if (taglib.getIncludeTags() != null) {
				condition.append('(');
				String[] includes = taglib.getIncludeTags().split(",");
				for (int i = 0; i < includes.length; i++) {
					String includeTag = includes[i];
					if (i != 0) {
						condition.append(" or ");
					}
					condition.append("normalize-space(").append(nameTag)
							.append(")='").append(includeTag).append("'");
				}
				condition.append(')');
			}
			if (taglib.getIncludeTags() != null
					&& taglib.getExcludeTags() != null) {
				condition.append(" and ");
			}
			if (taglib.getExcludeTags() != null) {
				condition.append('(');
				String[] excludes = taglib.getExcludeTags().split(",");
				for (int i = 0; i < excludes.length; i++) {
					String includeTag = excludes[i];
					if (i != 0) {
						condition.append(" and ");
					}
					condition.append("normalize-space(").append(nameTag)
							.append(")!='").append(includeTag).append("'");
				}
				condition.append(')');
			}
			condition.append(']');
		}
		return condition.toString();
	}

	/**
	 * @return
	 * @throws MojoExecutionException
	 */
	private List<Model> extractModules() throws MojoExecutionException {
		List<String> modules = parentProject.getModules();
		List<Model> models = new ArrayList<Model>(modules.size());
		Map<String, Dependency> projectsDependencies = new HashMap<String, Dependency>();
		for (Iterator<String> iter = modules.iterator(); iter.hasNext();) {
			String moduleName = iter.next();
			getLog().info("Parent project have module " + moduleName);
			Model model;
			File f = new File(parentProject.getBasedir(), moduleName
					+ "/pom.xml");
			if (f.exists()) {
				try {
					model = mavenProjectBuilder.build(f, localRepository, null)
							.getModel();
				} catch (ProjectBuildingException e) {
					throw new MojoExecutionException(
							"Unable to read local module-POM", e);
				}
			} else {
				getLog().warn("No filesystem module-POM available");

				model = new Model();
				model.setName(moduleName);
				model.setUrl(moduleName);
			}
			if (project.getGroupId().equals(model.getGroupId())
					&& "jar".equals(model.getPackaging())
					&& (!project.getArtifactId().equals(model.getArtifactId()))) {
				// TODO - check include/exclude
				getLog().debug(
						"Project " + model.getName()
								+ " included to library set");
				List<Dependency> dependencies = model.getDependencies();
				for (Iterator<Dependency> iterator = dependencies.iterator(); iterator
						.hasNext();) {
					Dependency dependency = iterator.next();
					getLog().debug(
							dependency.getClass().getName() + " : "
									+ dependency + " with key: "
									+ dependency.getManagementKey());
					if (!"test".equals(dependency.getScope())) {
						projectsDependencies.put(dependency.getManagementKey(),
								dependency);

					}
				}
				models.add(model);
			}
		}
		// Remove modules projects from dependencise
		Set<String> unwanted = new HashSet<String>(projectsDependencies.size());
		for (Iterator<Model> iter = models.iterator(); iter.hasNext();) {
			Model model = (Model) iter.next();
			for (Iterator<Dependency> iterator = projectsDependencies.values().iterator(); iterator
					.hasNext();) {
				Dependency dependency = (Dependency) iterator.next();
				if (model.getGroupId().equals(dependency.getGroupId())
						&& model.getArtifactId().equals(
								dependency.getArtifactId())) {
					getLog().debug(
							"Remove dependency of library module "
									+ dependency.getManagementKey());
					unwanted.add(dependency.getManagementKey());
				}
			}
			// Got module and unpack it to target directory.
			Artifact artifact = factory.createBuildArtifact(model.getGroupId(),
					model.getArtifactId(), model.getVersion(), model
							.getPackaging());
			File moduleDir = new File(modulesDirectory, model.getArtifactId());
			unpackArtifact(artifact, moduleDir, true);
			artifact = factory
					.createArtifactWithClassifier(model.getGroupId(), model
							.getArtifactId(), model.getVersion(), "jar",
							"sources");
			unpackArtifact(artifact, modulesSrc, false);
		}
		// Add projects dependencies to this project
		projectsDependencies.keySet().removeAll(unwanted);
		setupGeneratedProject(projectsDependencies);
		return models;
	}

	/**
	 * @param projectsDependencies
	 * @throws MojoExecutionException
	 */
	private void setupGeneratedProject(
			Map<String, Dependency> projectsDependencies)
			throws MojoExecutionException {
		Model generatedProject;
		try {
			MavenXpp3Reader reader = new MavenXpp3Reader();
			generatedProject = reader.read(new FileReader(project.getFile()));
		} catch (Exception e1) {
			throw new MojoExecutionException("Unable to read local POM", e1);
		}
		generatedProject.getDependencies()
				.addAll(projectsDependencies.values());
		writePom(generatedProject);
		project.setDependencies(new ArrayList<Dependency>(projectsDependencies.values()));
		// project.setFile(generatedPom);
	}

	/**
	 * Check parent project, build if nesessary.
	 * 
	 * @throws MojoFailureException
	 */
	protected void setupParentProject() throws MojoFailureException {
		// Parent project not set for a custom lifecycles. Try to load artifact.
		if (null == parentProject || null == parentProject.getFile()) {
			Parent parentModel = project.getModel().getParent();
			if (null != parentModel) {
				String relativePath = parentModel.getRelativePath();
				File parentPom = new File(project.getFile().getAbsoluteFile()
						.getParentFile(), relativePath);
				if (parentPom.isDirectory()) {
					parentPom = new File(parentPom, "pom.xml");
				}
				if (parentPom.exists()) {
					try {
						parentProject = mavenProjectBuilder.build(parentPom,
								localRepository, null);
						if (null == parentProject) {
							throw new MojoFailureException(
									"Components library project must have parent pom with components modules");
						}
					} catch (ProjectBuildingException e) {
						throw new MojoFailureException(
								"Error get parent project for a components library");
					}
				} else {
					throw new MojoFailureException(
							"Parent project pom file not found for a components library");
				}
			} else {
				throw new MojoFailureException(
						"Components library project must have parent pom with components modules");
			}

		}
	}

	/**
	 * @param models
	 * @throws MojoExecutionException
	 */
	private void mergeFacesConfig(List<Model> models) throws MojoExecutionException {
		StringBuffer facesConfig = new StringBuffer();
		for (int i = 0; i < library.getRenderkits().length; i++) {
			Renderkit kit = library.getRenderkits()[i];
			kit.setContent(new StringBuffer());
		}
		// Process all faces-config.xml from modules
		for (Iterator<Model> iter = models.iterator(); iter.hasNext();) {
			Model model = iter.next();
			File moduleFacesConfig = new File(modulesDirectory, model
					.getArtifactId()
					+ "/META-INF/faces-config.xml");
			processFacesConfigFile(facesConfig, moduleFacesConfig);
		}
		// Process faces-config from project resources
		if (null !=config) {
			processFacesConfigFile(facesConfig, new File(config, "META-INF/faces-config.xml"));
		}
		VelocityContext context = new VelocityContext();
		context.put("content", facesConfig.toString());
		context.put("library", library);
		context.put("renderkits", Arrays.asList(library.getRenderkits()));
		try {
			writeParsedTemplate(templates + FACES_CONFIG_TEMPLATE, context,
					new File(outputDirectory, "META-INF/faces-config.xml"));
		} catch (Exception e) {
			throw new MojoExecutionException(
					"Error to process faces-config template", e);
		}
	}

	/**
	 * @param config
	 * @param moduleFacesConfig
	 * @throws MojoExecutionException
	 */
	private void processFacesConfigFile(StringBuffer config,
			File moduleFacesConfig) throws MojoExecutionException {
		if (moduleFacesConfig.exists()) {
			getLog().info(
					"Process "+moduleFacesConfig.getName());
			XMLBody configBody = new XMLBody();
			try {
				configBody.loadXML(new FileInputStream(moduleFacesConfig));
				config
						.append(configBody
								.getContent("/faces-config/*[name()!=\'render-kit\']"));
				for (int i = 0; i < library.getRenderkits().length; i++) {
					Renderkit kit = library.getRenderkits()[i];
					kit
							.getContent()
							.append(
									configBody
											.getContent("/faces-config/render-kit[child::render-kit-id='"
													+ kit.getName()
													+ "']/renderer"));
				}
			} catch (FileNotFoundException e) {
				throw new MojoExecutionException(
						"Could't read faces-config file", e);
			} catch (ParsingException e) {
				throw new MojoExecutionException(
						"Error parsing faces-config file", e);
			}
		}
	}

	/**
	 * Merge XML files from extracted models to one in build directory.
	 * 
	 * @param models
	 *            models collected in library.
	 * @param includes
	 *            relative path to config file in models/output.
	 * @param templateName -
	 *            name of velocity template for result file.
	 * @param commonXpath -
	 *            XPath expression fof common part of result file.
	 * @param keyXPath -
	 *            XPath expression for key part of common parts
	 * @param context -
	 *            Velocity context for template processing.
	 * @param namespaceAware TODO
	 * @param keySet -
	 *            {@link Set} to check for duplicate keys. Must not be null
	 * @throws MojoExecutionException
	 */
	private void mergeXML(List<Model> models, String includes, String excludes, String templateName,
			String commonXpath, String keyXPath, VelocityContext context,
			File target, boolean namespaceAware, Comparator<Node> comparator) throws MojoExecutionException {
		StringBuffer content = new StringBuffer();
		String[] split = includes.split(",");
		String[] excludesSplit = excludes != null ? excludes.split(",") : null;
		XMLBodyMerge xBodyMerge = new XMLBodyMerge(commonXpath, keyXPath);
		for (Iterator<Model> iter = models.iterator(); iter.hasNext();) {
			Model model = iter.next();
			File moduleDir = new File(modulesDirectory, model.getArtifactId());
			mergeXMLdir(moduleDir, namespaceAware, split, excludesSplit, xBodyMerge);
		}
		if(null!=config){
			mergeXMLdir(config, namespaceAware, split, excludesSplit, xBodyMerge);		
		}
		
		if (comparator != null) {
			xBodyMerge.sort(comparator);
		}
		try {
			content.append(xBodyMerge.getContent());
		} catch (Exception e1) {
			throw new MojoExecutionException("XML Merge Exception Occured", e1);
		}
		if (content.length() > 0) {
			context.put("content", content.toString());
			context.put("library", library);
			context.put("models", models);
			try {
				writeParsedTemplate(templates + templateName, context, target);
			} catch (Exception e) {
				throw new MojoExecutionException("Error to process template "
						+ templateName + " for files " + includes, e);
			}

		}
	}

	/**
	 * @param moduleDir
	 * @param commonXpath
	 * @param keyXPath
	 * @param namespaceAware
	 * @param keySet
	 * @param content
	 * @param xmls
	 * @param split
	 * @throws IllegalStateException
	 * @throws MojoExecutionException
	 */
	private void mergeXMLdir(File moduleDir, boolean namespaceAware, String[] split, String[] excludesSplit, XMLBodyMerge xBodyMerge)
			throws IllegalStateException, MojoExecutionException {
		DirectoryScanner ds = new DirectoryScanner();
		ds.setFollowSymlinks(true);
		ds.setBasedir(moduleDir);
		ds.setIncludes(split);
		ds.setExcludes(excludesSplit);
		ds.addDefaultExcludes();
		ds.scan();
		String[] files = ds.getIncludedFiles();
		for (int i = 0; i < files.length; i++) {
			File moduleFacesConfig = new File(moduleDir, files[i]);
			getLog().info(
					"Process " + files[i] );
			XMLBody configBody = new XMLBody();
			try {
				configBody.loadXML(new FileInputStream(moduleFacesConfig),namespaceAware);
				xBodyMerge.add(configBody);
			} catch (FileNotFoundException e) {
				throw new MojoExecutionException("Could't read  file "
						+ moduleFacesConfig.getPath(), e);
			} catch (ParsingException e) {
				throw new MojoExecutionException(
						"Error parsing config file "
								+ moduleFacesConfig.getPath(), e);
			}
		}
	}

	private void unpackArtifact(Artifact artifact, File moduleDir,
			boolean isResource) throws MojoExecutionException {
		try {
			resolver.resolve(artifact, Collections.EMPTY_LIST, localRepository);
			unpack(artifact.getFile(), moduleDir);
			if (isResource) {
				Resource resource = new Resource();
				resource.setDirectory(moduleDir.getPath());
				resource.addExclude("META-INF/faces-config.xml");
				resource.addExclude("META-INF/resources-config.xml");
				resource.addExclude("META-INF/*.taglib.xml");
				resource.addExclude("META-INF/*.component-dependencies.xml");
				resource.addExclude("META-INF/*.tld");
				project.addResource(resource);

			}
		} catch (ArtifactResolutionException e) {
			getLog().error("Error with resolve artifact " + artifact, e);
		} catch (ArtifactNotFoundException e) {
			getLog().error("Not found artifact " + artifact, e);
		}
	}

	private List<MavenProject> populateReactorProjects() {
		List<MavenProject> projects = new ArrayList<MavenProject>();
		if (reactorProjects != null && reactorProjects.size() > 1) {
			Iterator reactorItr = reactorProjects.iterator();

			while (reactorItr.hasNext()) {
				MavenProject reactorProject = (MavenProject) reactorItr.next();

				if (reactorProject != null
						&& reactorProject.getParent() != null
						&& project.getArtifactId().equals(
								reactorProject.getParent().getArtifactId())) {
					String name = reactorProject.getGroupId() + ":"
							+ reactorProject.getArtifactId();
					getLog().info("Have reactor project with name " + name);
					projects.add(reactorProject);
				}
			}
		}
		return projects;
	}

	/**
	 * Unpacks the archive file.
	 * 
	 * @param file
	 *            File to be unpacked.
	 * @param location
	 *            Location where to put the unpacked files.
	 */
	private void unpack(File file, File location) throws MojoExecutionException {

		getLog().debug(
				"Unpack file " + file.getAbsolutePath() + " to: "
						+ location.getAbsolutePath());
		try {
			location.mkdirs();

			UnArchiver unArchiver;

			unArchiver = archiverManager.getUnArchiver(file);

			unArchiver.setSourceFile(file);

			unArchiver.setDestDirectory(location);

			unArchiver.setOverwrite(true);

			unArchiver.extract();

		} catch (Exception e) {
			throw new MojoExecutionException("Error unpacking file: " + file
					+ " to: " + location + "\r\n" + e.toString(), e);
		}
	}

	private void writePom(Model pom) throws MojoExecutionException {
		MavenXpp3Writer pomWriter = new MavenXpp3Writer();
		try {
			FileWriter out = new FileWriter(generatedPom);
			pomWriter.write(out, pom);
		} catch (IOException e) {
			throw new MojoExecutionException("Error for write generated pom", e);
		}

	}

	/**
	 * @return the archiverManager
	 */
	public ArchiverManager getArchiverManager() {
		return this.archiverManager;
	}

	/**
	 * @param archiverManager
	 *            the archiverManager to set
	 */
	public void setArchiverManager(ArchiverManager archiverManager) {
		this.archiverManager = archiverManager;
	}

	/**
	 * @return the buildDirectory
	 */
	public File getBuildDirectory() {
		return this.buildDirectory;
	}

	/**
	 * @param buildDirectory
	 *            the buildDirectory to set
	 */
	public void setBuildDirectory(File buildDirectory) {
		this.buildDirectory = buildDirectory;
	}

	/**
	 * @return the factory
	 */
	public org.apache.maven.artifact.factory.ArtifactFactory getFactory() {
		return this.factory;
	}

	/**
	 * @param factory
	 *            the factory to set
	 */
	public void setFactory(
			org.apache.maven.artifact.factory.ArtifactFactory factory) {
		this.factory = factory;
	}

	/**
	 * @return the localRepository
	 */
	public ArtifactRepository getLocalRepository() {
		return this.localRepository;
	}

	/**
	 * @param localRepository
	 *            the localRepository to set
	 */
	public void setLocalRepository(ArtifactRepository localRepository) {
		this.localRepository = localRepository;
	}

	/**
	 * @return the mavenProjectBuilder
	 */
	public MavenProjectBuilder getMavenProjectBuilder() {
		return this.mavenProjectBuilder;
	}

	/**
	 * @param mavenProjectBuilder
	 *            the mavenProjectBuilder to set
	 */
	public void setMavenProjectBuilder(MavenProjectBuilder mavenProjectBuilder) {
		this.mavenProjectBuilder = mavenProjectBuilder;
	}

	/**
	 * @return the parentProject
	 */
	public MavenProject getParentProject() {
		return this.parentProject;
	}

	/**
	 * @param parentProject
	 *            the parentProject to set
	 */
	public void setParentProject(MavenProject parentProject) {
		this.parentProject = parentProject;
	}

	/**
	 * @return the reactorProjects
	 */
	public List getReactorProjects() {
		return this.reactorProjects;
	}

	/**
	 * @param reactorProjects
	 *            the reactorProjects to set
	 */
	public void setReactorProjects(List reactorProjects) {
		this.reactorProjects = reactorProjects;
	}

	/**
	 * @return the resolver
	 */
	public org.apache.maven.artifact.resolver.ArtifactResolver getResolver() {
		return this.resolver;
	}

	/**
	 * @param resolver
	 *            the resolver to set
	 */
	public void setResolver(
			org.apache.maven.artifact.resolver.ArtifactResolver resolver) {
		this.resolver = resolver;
	}

}
