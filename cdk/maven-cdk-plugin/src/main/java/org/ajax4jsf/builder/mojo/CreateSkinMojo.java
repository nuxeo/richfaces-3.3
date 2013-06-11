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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.aja4jsf.builder.model.Resource;
import org.aja4jsf.builder.model.ResourceConfig;
import org.aja4jsf.builder.model.io.xpp3.ResourceConfigXpp3Reader;
import org.aja4jsf.builder.model.io.xpp3.ResourceConfigXpp3Writer;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.VelocityContext;

/**
 * This mojo is intended to create new skin add-on within existing project.
 * New resources are added to the project, and existing resource config is modified to include new files.
 * <p><b>Usage</b> <tt>mvn cdk:add-skin -Dname=&lt;skinName&gt; -Dpackage=&lt;skinPackage&gt;. </tt></p>
 * This adds new skin named &lt;skinName&gt; to project resources/META-INF/skins folder.<br/>
 * Skin resources - XCSS files are placed in &lt;skinPackage&gt;.&lt;skinName&gt; package within project resources directory.<br/>
 * If <em>package</em> is not specified, it is set same as groupId.<br/>
 * 
 * Following parameters can be used
 * <li><strong>name</strong> - name of the skin. <strong>Required.</strong></li>
 * <li><strong>package</strong> - base package of the skin. If not specified, <em>groupId</em> is used</li>
 * <li><strong>createExt</strong> - if set to true, extended skin files are added</li>
 * <li><strong>baseSkin</strong> - name of base skin</li>
 * 
 * @goal add-skin
 * @requiresProject true
 * 
 * 

 * @author Maksim Kaszynski
 *
 */
public class CreateSkinMojo extends AbstractCreateMojo {

	private static final String PROPERTIES = ".skin.properties";

	private static final String XCSS = ".xcss";

	private static final String EXT_XCSS = "-ext.xcss";

	private static final String SRC_MAIN_CONFIG_RESOURCES = "src/main/config/resources";

	private static final String META_INF_SKINS = "META-INF/skins/";
	
	/**
	 * Name of base package of skin.
	 * If it is not set, GroupId is used.
	 * @parameter expression="${package}" default-value="${project.groupId}"
	 */
	private String packageName;
	
	
	/**
	 * Name of skin. Required parameter.
	 * @parameter expression="${name}"
	 * @required
	 */
	private String skinName;
	

	/**
	 * Name of base skin.
	 * @parameter expression="${baseSkin}"
	 */
	private String baseSkin;

	/**
	 * If set to true, a set of extended classes is generated
	 * @parameter expression="${createExt}"
	 */
	private boolean createExt;
	
	
	/**
	 * @parameter expression="${plugin.artifactId}"
	 * 	@readonly
	 */
	private String pluginArtifactId;

	/**
	 * @parameter expression="${plugin.groupId}"
	 * @readonly
	 */
	private String pluginGroupId;

	/**
	 * @parameter expression="${plugin.version}"
	 * @readonly
	 */
	private String pluginVersion;

	/** @component */
	private org.apache.maven.artifact.factory.ArtifactFactory artifactFactory;

	/** @component */
	private org.apache.maven.artifact.resolver.ArtifactResolver resolver;

	/**
	 * @parameter expression="${localRepository}" 
	 * @readonly
	 * */
	private org.apache.maven.artifact.repository.ArtifactRepository localRepository;

	/**
	 * 
	 */
	private File pluginJarFile = null;

	/**
	 * 
	 */
	private File resourceDir;
	
	public void execute() throws MojoExecutionException, MojoFailureException {

		getLog().info("Executing " + project);
		getLog().info("Generating Skin " + skinName);
		String fullSkinName = packageName.replace('/', '.') + "." + skinName;
		String shortName = skinName;
		getLog().debug("Skin name is supposed to be " + shortName);
		getLog().debug("Root package " + packageName);
		getLog().debug("Skin package is " + fullSkinName);
		
		//RF-4023
		if (!isValidJavaName(skinName)){
			throw new MojoExecutionException("Invalid skin name '" + skinName + "'. Please type another name.");
		}
		
		if (!isValidPackageName(packageName)) {
			throw new MojoExecutionException("Invalid skin package '" + packageName + "'. Please type another package.");
		}
		
		SkinInfo skinInfoTemplate = new SkinInfo();
		skinInfoTemplate.setShortName(shortName);
		skinInfoTemplate.setPackageName(fullSkinName.toLowerCase());
		skinInfoTemplate.setBaseClassResources(getArchetypeSkinTemplatesFromJar("skin/baseclasses"));
		skinInfoTemplate.setExtClassResources(getArchetypeSkinTemplatesFromJar("skin/extclasses"));
		skinInfoTemplate.setBaseSkin(baseSkin);
		skinInfoTemplate.setPropertyFile(new Resource("skin/skin.properties"));
		skinInfoTemplate.setMasterXcss(new Resource("skin/skin.xcss"));
		skinInfoTemplate.setExtendedXcss(new Resource("skin/skin.xcss"));
		skinInfoTemplate.setUseExt(createExt);
		generateSkin(skinInfoTemplate);

		
		getLog().info("Generating Skin successful");

	}
	
	private void copyJarResource(Resource template, Resource skinResource, File baseDir) throws Exception{
		File newFile = new File(baseDir, skinResource.getPath());
		if (newFile.exists()) {
			getLog().debug("File " + newFile + " already exists. Skipping.");
		} else {
			try {
				File dir = newFile.getParentFile();
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream(template.getPath());
				FileOutputStream fileOutputStream = new FileOutputStream(newFile);
				byte [] buffer = new byte[1024];
				int read = -1;
				while((read = inputStream.read(buffer)) != -1) {
					fileOutputStream.write(buffer, 0, read);
				}
				inputStream.close();
				fileOutputStream.close();
				
			} catch (Exception e) {
				throw new MojoExecutionException("Unable to write file " + newFile, e);
			}
		}
	}
	
	private List<Resource> filterComponentSkinTemplates(List<Resource> jarTemplates, SkinInfo skinInfo, File resourceDir) throws MojoExecutionException {
		List<Resource> newResources = new ArrayList<Resource>(jarTemplates.size());
		for (Resource jarTemplate : jarTemplates) {
			Resource newResource = new Resource();
			String jarTemplateName = jarTemplate.getName();
			int indexOfSlash = jarTemplateName.lastIndexOf('/');
			if (indexOfSlash > 0) {
				jarTemplateName = jarTemplateName.substring(indexOfSlash + 1);
			}
			String resourceName = skinInfo.getPackageName().replace('.', '/') + "/css/" + jarTemplateName;
			newResource.setName(resourceName);
			newResource.setPath(resourceName);
			newResources.add(newResource);
			try {
				filterJarResource(jarTemplate, newResource, resourceDir, new VelocityContext());
			} catch (Exception e) {
				throw new MojoExecutionException("An exception occured while filtering resource " + jarTemplate + " into " + newResource, e);
			}
		}
		return newResources;
	}

	
	private SkinInfo cloneSkinInfo(SkinInfo skinInfo) throws MojoExecutionException {
		try {
			return (SkinInfo) skinInfo.clone();
		} catch(CloneNotSupportedException exception) {
			throw new MojoExecutionException("Dunno why.", exception);
		}
	}
	
	private void generateSkin(SkinInfo skinInfoTemplate) throws MojoExecutionException, MojoFailureException{
		resourceDir = new File(project.getBasedir(), SRC_MAIN_RESOURCES);
		getLog().debug("Resources directory is supposed to be" + resourceDir);
		checkAndCreateDirectory(resourceDir);

		String fullName = skinInfoTemplate.getPackageName();
		String packageFolder = fullName.replace('.', '/');
		getLog().debug("Relative path to package is " + packageFolder);

		VelocityContext velocityMasterContext = new VelocityContext();
		velocityMasterContext.put("mojo", this);
		velocityMasterContext.put("packagePath", packageFolder);
		velocityMasterContext.put("name", skinInfoTemplate.getShortName());
		
		if (baseSkin != null) {
			velocityMasterContext.put("baseSkin", baseSkin);
		}
		
		SkinInfo effectiveSkinInfo = cloneSkinInfo(skinInfoTemplate);
		List<Resource> allResources = new ArrayList<Resource>();
		velocityMasterContext.put("skinInfo", effectiveSkinInfo);
		
		//Copy basic classes, and list them
		
		List<Resource> baseClassResources = filterComponentSkinTemplates(skinInfoTemplate.getBaseClassResources(), skinInfoTemplate, resourceDir);
		
		effectiveSkinInfo.setBaseClassResources(baseClassResources);
		allResources.addAll(baseClassResources);
		velocityMasterContext.put("baseClassResources", baseClassResources);
		
		//Generate Master XCSS file
		Resource masterXcss = new Resource(META_INF_SKINS + effectiveSkinInfo.getShortName() + XCSS);
		effectiveSkinInfo.setMasterXcss(masterXcss);
		allResources.add(masterXcss);
		try {
			generateMasterXCSSFile(skinInfoTemplate.getMasterXcss(), effectiveSkinInfo.getMasterXcss(), effectiveSkinInfo.getBaseClassResources());
		} catch(Exception e) {
			throw new MojoExecutionException("Unable to generate master.xcss file " + masterXcss, e);
		}

		if (createExt) {
			//Copy Ext classes, if needed
			List<Resource> extClassResources = filterComponentSkinTemplates(skinInfoTemplate.getExtClassResources(), skinInfoTemplate, resourceDir);
			effectiveSkinInfo.setExtClassResources(extClassResources);
			allResources.addAll(extClassResources);
			velocityMasterContext.put("extClassResources", extClassResources);
			
			//Generate Master EXT XCSS file
			Resource masterExtXcss = new Resource(META_INF_SKINS + effectiveSkinInfo.getShortName() + EXT_XCSS);
			effectiveSkinInfo.setExtendedXcss(masterExtXcss);
			allResources.add(masterExtXcss);
			try {
				generateMasterXCSSFile(skinInfoTemplate.getExtendedXcss(), effectiveSkinInfo.getExtendedXcss(), effectiveSkinInfo.getExtClassResources());
			
			} catch(Exception e) {
				throw new MojoExecutionException("Unable to generate master.xcss file " + masterExtXcss, e);
			}
		}
		
		Resource properties = new Resource(META_INF_SKINS + effectiveSkinInfo.getShortName() + PROPERTIES);
		effectiveSkinInfo.setPropertyFile(properties);
		try {
			filterJarResource(skinInfoTemplate.getPropertyFile(), properties, resourceDir, velocityMasterContext);
		} catch (Exception e) {
			throw new MojoExecutionException("An exception occured while filtering resource " + skinInfoTemplate.getPropertyFile() + " into " + properties, e);
		}

		
		File resourceConfigDir = new File(project.getBasedir(), SRC_MAIN_CONFIG_RESOURCES);
		checkAndCreateDirectory(resourceConfigDir);
		File resourceConfigFile = new File(resourceConfigDir, effectiveSkinInfo.getShortName() + "-resources.xml");;
		try {
			addToResourceConfig(allResources, resourceConfigFile);
		} catch (Exception e) {
			throw new MojoExecutionException("Unable to register newly added resources in resource-config.xml", e);
		}
		
		
		packageArchetypeResourcesToBaseDir("src/main/java", effectiveSkinInfo, true);
		packageArchetypeResourcesToBaseDir("src/main/resources", effectiveSkinInfo, false);
		packageArchetypeResourcesToBaseDir("src/test/java", effectiveSkinInfo, true);
		
		
		
	}

	private List<Resource> packageArchetypeResourcesToBaseDir(String prefix, final SkinInfo skinInfo, boolean filter) throws MojoExecutionException{
		String archetypePrefix = "skin/archetype/" + prefix;
		List<Resource> archetypeResources = getArchetypeSkinTemplatesFromJar(archetypePrefix);
		List<Resource> filteredResources = new ArrayList<Resource>(archetypeResources.size());
		String newPrefix = prefix;
		String packageFolder = skinInfo.getPackageName().replace('.', '/');
		if (packageFolder != null) {
			newPrefix = prefix + "/" + packageFolder;
		}
		
		
		@SuppressWarnings("serial") VelocityContext velocityContext = new VelocityContext() {
			{
				put("skinInfo", skinInfo);
			}
		};
		for (Resource resource : archetypeResources) {
			String archetypePath = resource.getPath();
			String pathSuffix = archetypePath.substring(archetypePrefix.length());
			String newPath = newPrefix + pathSuffix;
			
			Resource newResource = new Resource(newPath);
			
			try {
				if (filter) {
					filterJarResource(resource, newResource, project.getBasedir(), velocityContext);
				} else {
					copyJarResource(resource, newResource, project.getBasedir());
				}
			} catch (Exception e) {
				throw new MojoExecutionException("Unable to process archetype file " + resource.getPath(), e);
			}
			
			filteredResources.add(newResource);
		}
		return filteredResources;
	}

	private void generateMasterXCSSFile(Resource template, Resource target, List<Resource> includedResources) throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("mojo", this);
		context.put("includedResources", includedResources);
		filterJarResource(template, target, resourceDir, context);
	}

	private Resource fromJarEntry(JarEntry entry) {
		Resource resource = new Resource();
		resource.setName(entry.getName());
		resource.setPath(entry.getName());
		return resource;
	}

	private List<Resource> getArchetypeSkinTemplatesFromJar(String prefix) throws MojoExecutionException{
		try {
			if (pluginJarFile == null) {
				pluginJarFile = getPluginArtifactJar();
			}
			List <Resource> fileNames = new ArrayList<Resource>();
			JarFile jar = new JarFile(pluginJarFile);
			Enumeration<JarEntry> entries = jar.entries();
			while(entries.hasMoreElements()) {
				JarEntry jarEntry = entries.nextElement();
				String jarEntryName = jarEntry.getName();
				if(jarEntryName.startsWith(prefix) && !jarEntry.isDirectory()) {
					fileNames.add(fromJarEntry(jarEntry));
				}
			}
			return fileNames;
		} catch (Exception e) {
			throw new MojoExecutionException("Unable to list templates within Plugin Jar file");
		}
	}

	private File getPluginArtifactJar() throws ArtifactResolutionException, ArtifactNotFoundException {
		Artifact pluginArtifact = 
			artifactFactory.createPluginArtifact(pluginGroupId, pluginArtifactId, VersionRange.createFromVersion(pluginVersion));

		resolver.resolve(pluginArtifact, Collections.emptyList(), localRepository);

		return pluginArtifact.getFile();
	}

	private void addToResourceConfig(List<Resource> resources, File resourceConfigFile) throws Exception{
		ResourceConfig resourceConfig = null;
		
		if (resourceConfigFile.exists()) {
			resourceConfig = new ResourceConfigXpp3Reader().read(new FileInputStream(resourceConfigFile));
		}
		
		if (resourceConfig == null) {
			resourceConfig = new ResourceConfig();
			resourceConfig.setResources(resources);
		} else {
			for (Resource resource : resources) {
				if (!resourceConfig.containsResource(resource)) {
					resourceConfig.addResource(resource);
				}
			}
		}
		
		
		
		new ResourceConfigXpp3Writer().write(new FileWriter(resourceConfigFile), resourceConfig);
		
	}
	
}
