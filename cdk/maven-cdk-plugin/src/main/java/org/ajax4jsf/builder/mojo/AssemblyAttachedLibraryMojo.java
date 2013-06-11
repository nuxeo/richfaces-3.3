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
import org.apache.maven.project.MavenProjectHelper;
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
 * *.tld, *.taglib.xml , resources-config.xml into ones. 6. append dependencies
 * of included projects to this.
 * 
 * @author shura
 * @goal attached
 * @phase package
 * @aggregator
 */
public class AssemblyAttachedLibraryMojo extends AbstractCDKMojo {

	private static final String TEMPLATES_PREFIX = "/templates/";

	private static final String TEMPLATES12_PREFIX = "/templates12/";

	private static final String FACES_CONFIG_TEMPLATE = "faces-config.vm";

	private static final String RESOURCES_CONFIG_TEMPLATE = "resources-config.vm";

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
     * Maven ProjectHelper
     * 
     * @component
     */
    private MavenProjectHelper projectHelper;

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
	 * The directory for compiled classes.
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 * @readonly
	 */
	private File buildDirectory;

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
	private Taglib[] taglibs;

	/**
	 * 
	 */
	public AssemblyAttachedLibraryMojo() {
		// used for plexus init.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		if(null != reactorProjects){
			getLog().info("Reactor projects");
			for (Iterator iterator = reactorProjects.iterator(); iterator.hasNext();) {
				MavenProject reactor = (MavenProject) iterator.next();
				getLog().info("Project "+reactor.getGroupId()+":"+reactor.getArtifactId());
			}
		}
		//		assemblyProjects();
		
	}

	/**
	 * @throws MojoFailureException
	 * @throws MojoExecutionException
	 */
	protected void assemblyProjects() throws MojoFailureException,
			MojoExecutionException {
		// Parent project not set for a custom lifecycles. Try to load artifact.
		if (null == parentProject || null == parentProject.getFile()) {
			Parent parentModel = project.getModel().getParent();
			if (null != parentModel) {
				String relativePath = parentModel.getRelativePath();
				File parentPom = new File(project.getFile().getAbsoluteFile().getParentFile(), relativePath);
				if (parentPom.isDirectory()) {
					parentPom = new File(parentPom, "pom.xml");
				}
				if (parentPom.exists()) {
					try {
						parentProject = mavenProjectBuilder.build(parentPom,
								localRepository, null);
					} catch (ProjectBuildingException e) {
						throw new MojoFailureException("Error get parent project for a components library");
					}
				} else {
					throw new MojoFailureException("Parent project pom file not found for a components library");					
				}
			}else {
				throw new MojoFailureException("Components library project must have parent pom with components modules");
			}

		}
		if (null != parentProject) {
			this.checkLibraryConfig();
			if (null == templates) {
				templates = Library.JSF12.equals(library.getJsfVersion()) ? TEMPLATES12_PREFIX
						: TEMPLATES_PREFIX;
			}
			Model generatedProject;
			try {
				MavenXpp3Reader reader = new MavenXpp3Reader();
				generatedProject = reader
						.read(new FileReader(project.getFile()));
			} catch (Exception e1) {
				throw new MojoExecutionException("Unable to read local POM", e1);
			}
			List modules = parentProject.getModules();
			List models = new ArrayList(modules.size());
			Map<String, Dependency> projectsDependencies = new HashMap<String, Dependency>();
			for (Iterator iter = modules.iterator(); iter.hasNext();) {
				String moduleName = (String) iter.next();
				getLog().info("Parent project have module " + moduleName);
				Model model;
				File f = new File(parentProject.getBasedir(), moduleName
						+ "/pom.xml");
				if (f.exists()) {
					try {
						model = mavenProjectBuilder.build(f, localRepository,
								null).getModel();
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
						&& (!project.getArtifactId().equals(
								model.getArtifactId()))) {
					// TODO - check include/exclude
					getLog().debug(
							"Project " + model.getName()
									+ " included to library set");
					List dependencies = model.getDependencies();
					for (Iterator iterator = dependencies.iterator(); iterator
							.hasNext();) {
						Dependency dependency = (Dependency) iterator.next();
						getLog().debug(
								dependency.getClass().getName() + " : "
										+ dependency + " with key: "
										+ dependency.getManagementKey());
						if (!"test".equals(dependency.getScope())) {
							projectsDependencies.put(dependency
									.getManagementKey(), dependency);

						}
					}
					models.add(model);
				}
			}
			// Remove modules projects from dependencise
			Set<String> unwanted = new HashSet<String>(projectsDependencies
					.size());
			for (Iterator iter = models.iterator(); iter.hasNext();) {
				Model model = (Model) iter.next();
				for (Iterator iterator = projectsDependencies.values()
						.iterator(); iterator.hasNext();) {
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
				Artifact artifact = factory.createBuildArtifact(model
						.getGroupId(), model.getArtifactId(), model
						.getVersion(), model.getPackaging());
				File moduleDir = new File(buildDirectory, model.getArtifactId());
				unpackArtifact(artifact, moduleDir, true);
				artifact = factory.createArtifactWithClassifier(model
						.getGroupId(), model.getArtifactId(), model
						.getVersion(), "jar", "sources");
				unpackArtifact(artifact, modulesSrc, false);
			}
			// Add projects dependencies to this project
			projectsDependencies.keySet().removeAll(unwanted);
			generatedProject.getDependencies().addAll(
					projectsDependencies.values());
			writePom(generatedProject);
			mergeFacesConfig(models);
			File resourcesConfig = new File(outputDirectory,
					"META-INF/resources-config.xml");
			mergeXML(models, "META-INF/resources-config.xml",
					RESOURCES_CONFIG_TEMPLATE, "/resource-config/resource", "name/text()",
					new VelocityContext(), resourcesConfig, null);
			File tld = new File(outputDirectory, "META-INF/"
					+ library.getTaglib().getShortName() + ".tld");
			mergeXML(models, includeTld, TLD_TEMPLATE,
					"/taglib/tag | /taglib/listener", 
					null, new VelocityContext(), tld, new NamesListComparator(
							new XPathComparator("name/text()", "listener-class/text()"), TLD_TAG_NAMES));
			File taglib = new File(outputDirectory, "META-INF/"
					+ library.getTaglib().getShortName() + ".taglib.xml");
			mergeXML(models, includeTaglib, TAGLIB_TEMPLATE,
					"/facelet-taglib/tag | /facelet-taglib/function",
					null, new VelocityContext(), taglib, new NamesListComparator(
							new XPathComparator("tag-name/text()", "function-name/text()"), TAGLIB_TAG_NAMES));
		}else {
			throw new MojoFailureException("Components library project must have parent pom with components modules");
		}
	}

	/**
	 * @param models
	 * @throws MojoExecutionException
	 */
	private void mergeFacesConfig(List models) throws MojoExecutionException {
		StringBuffer config = new StringBuffer();
		for (int i = 0; i < library.getRenderkits().length; i++) {
			Renderkit kit = library.getRenderkits()[i];
			kit.setContent(new StringBuffer());
		}
		for (Iterator iter = models.iterator(); iter.hasNext();) {
			Model model = (Model) iter.next();
			File moduleFacesConfig = new File(buildDirectory, model
					.getArtifactId()
					+ "/META-INF/faces-config.xml");
			if (moduleFacesConfig.exists()) {
				getLog().info(
						"Process faces-config.xml for module "
								+ model.getArtifactId());
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
		VelocityContext context = new VelocityContext();
		context.put("content", config.toString());
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
	 * Merge XML files from extracted models to one in build directory.
	 * 
	 * @param models
	 *            models collected in library.
	 * @param filename
	 *            relative path to config file in models/output.
	 * @param templateName -
	 *            name of velocity template for result file.
	 * @param commonXpath -
	 *            XPath expression fof common part of result file.
	 * @param keyXPath - XPath expression for key part of common parts
	 * @param keySet - {@link Set} to check for duplicate keys. Must not be null
	 * @param context -
	 *            Velocity context for template processing.
	 * @throws MojoExecutionException
	 */
	private void mergeXML(List<Model> models, String filename, String templateName,
			String commonXpath, String keyXPath, VelocityContext context, File target, Comparator<Node> comparator)
			throws MojoExecutionException {
		StringBuilder content = new StringBuilder();
		XMLBodyMerge bodyMerge = new XMLBodyMerge(commonXpath, keyXPath);
		List<XMLBody> xmls = new ArrayList<XMLBody>(models.size());
		String[] split = filename.split(",");
		for (Iterator<Model> iter = models.iterator(); iter.hasNext();) {
			Model model = iter.next();
			File moduleDir = new File(buildDirectory, model.getArtifactId());
			DirectoryScanner ds = new DirectoryScanner();
			ds.setFollowSymlinks(true);
			ds.setBasedir(moduleDir);
			ds.setIncludes(split);
			ds.addDefaultExcludes();
			ds.scan();
			String[] files = ds.getIncludedFiles();
			for (int i = 0; i < files.length; i++) {
				File moduleFacesConfig = new File(moduleDir, files[i]);
				getLog().info(
						"Process " + files[i] + " for module "
								+ model.getArtifactId());
				XMLBody configBody = new XMLBody();
				try {
					configBody.loadXML(new FileInputStream(moduleFacesConfig));
					xmls.add(configBody);
					bodyMerge.add(configBody);
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
		if (comparator != null) {
			bodyMerge.sort(comparator);
		}
		
		try {
			content.append(bodyMerge.getContent());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (xmls.size() > 0) {
			context.put("content", content);
			context.put("library", library);
			context.put("models", models);
			context.put("xmls", xmls);
			try {
				writeParsedTemplate(templates + templateName, context, target);
			} catch (Exception e) {
				throw new MojoExecutionException("Error to process template "
						+ templateName + " for files " + filename, e);
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
