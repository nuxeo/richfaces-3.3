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
import java.util.Locale;

import org.aja4jsf.builder.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.VelocityContext;

/**
 * This mojo is intended to create new theme within existing project.
 * <p><b>Usage</b> <tt>mvn cdk:add-theme -Dname=&lt;themeName&gt; [ -Dpackage=&lt;themePackage&gt; ]</tt></p>
 * This adds new theme named &lt;themeName&gt; to project resources/META-INF/themes folder.<br/>
 * If <em>package</em> is not specified, it is set same as groupId.<br/>
 * 
 * Following parameters can be used
 * <li><strong>name</strong> - name of the theme. <strong>Required.</strong></li>
 * <li><strong>package</strong> - base package of the theme. If not specified, <em>groupId</em> is used</li>
 * 
 * @goal add-theme
 * @requiresProject true
 * 
 * @author Nick Belaevski
 */
public class CreateThemeMojo extends AbstractCreateMojo {

	private static final String XCSS = ".xcss";

	private static final String META_INF_THEMES = "META-INF/themes/";

	private static final String XCSS_THEMES_PACKAGE = "/renderkit/html/css";

	private static final String THEME_PROPERTIES = ".theme.properties";

	private static final String RENDERKIT_THEMES = "renderkit.html";

	/**
	 * Name of base package of theme.
	 * If it is not set, GroupId is used.
	 * @parameter expression="${package}" default-value="${project.groupId}"
	 */
	private String packageName;
	
	
	/**
	 * Name of theme. Required parameter.
	 * @parameter expression="${name}"
	 * @required
	 */
	private String themeName;
	
	private String generateRendererName(String themeName) {
		StringBuilder rendererTypeBuilder = new StringBuilder();
		rendererTypeBuilder.append(Character.toUpperCase(themeName.charAt(0)));
		rendererTypeBuilder.append(themeName.subSequence(1, themeName.length()));
		rendererTypeBuilder.append("Renderer");
		return rendererTypeBuilder.toString();
	}
	
	private void generateTheme(String packageName, String themeName) throws MojoExecutionException, MojoFailureException {
		Resource themeProperties = new Resource("theme/themes/theme.properties");
		Resource themeXCSS = new Resource("theme/css/theme.xcss");
		Resource themeConfig = new Resource("theme/component/theme.xml");
		Resource themeTemplate = new Resource("theme/template/theme.jspx");
		
		File resourceDir = new File(project.getBasedir(), SRC_MAIN_RESOURCES);
		
		String packagePath = packageName.replace('.', '/');
		String xcssPackagePath = packagePath + XCSS_THEMES_PACKAGE;
		String rendererName = generateRendererName(themeName);
		String rendererType = packageName + '.' + rendererName;
		String rendererClassName = packageName + '.' + RENDERKIT_THEMES + '.' + rendererName;

		getLog().debug("Relative path to package is " + packagePath);

		VelocityContext velocityMasterContext = new VelocityContext();
		velocityMasterContext.put("packagePath", packagePath);
		velocityMasterContext.put("xcssPackagePath", xcssPackagePath);
		velocityMasterContext.put("name", themeName);
		velocityMasterContext.put("rendererType", rendererType);
		velocityMasterContext.put("rendererClassName", rendererClassName);
		
		Resource properties = new Resource(META_INF_THEMES + themeName + THEME_PROPERTIES);
		try {
			filterJarResource(themeProperties, properties, resourceDir, velocityMasterContext);
		} catch (Exception e) {
			throw new MojoExecutionException("An exception occured while filtering resource " + themeProperties + " into " + properties, e);
		}
		
		Resource xcss = new Resource(xcssPackagePath + '/' + themeName + XCSS);
		try {
			filterJarResource(themeXCSS, xcss, resourceDir, velocityMasterContext);
		} catch (Exception e) {
			throw new MojoExecutionException("An exception occured while filtering resource " + themeXCSS + " into " + xcss, e);
		}
		
		File configDir = new File(project.getBasedir(), "src/main/config/component");
		Resource config = new Resource(themeName + ".xml");
		try {
			filterJarResource(themeConfig, config, configDir, velocityMasterContext);
		} catch (Exception e) {
			throw new MojoExecutionException("An exception occured while filtering resource " + themeConfig + " into " + config, e);
		}

		File templateDir = new File(project.getBasedir(), "src/main/templates");
		Resource template = new Resource(packagePath + '/' + themeName + ".jspx");
		try {
			filterJarResource(themeTemplate, template, templateDir, velocityMasterContext);
		} catch (Exception e) {
			throw new MojoExecutionException("An exception occured while filtering resource " + themeTemplate + " into " + template, e);
		}
	}

	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Executing " + project);
		getLog().info("Generating Theme " + themeName);

		String themePackageName = packageName.replace('/', '.').toLowerCase(Locale.US);
		String shortName = themeName;
		getLog().debug("Root package " + packageName);
		getLog().debug("Theme package is " + themePackageName);

		if (!isValidJavaName(themeName)){
			throw new MojoExecutionException("Invalid theme name '" + themeName + "'. Please type another name.");
		}

		if (!isValidPackageName(packageName)) {
			throw new MojoExecutionException("Invalid theme package '" + packageName + "'. Please type another package.");
		}
		
		generateTheme(themePackageName, shortName);
		
		getLog().info("Generating Theme successful");
	}

}
