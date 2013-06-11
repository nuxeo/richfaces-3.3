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
import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.velocity.VelocityContext;

/**
 * @author shura
 * @goal create
 *
 */
public class CreateComponentMojo extends AbstractCDKMojo {

	private static final String TEMPLATES_PREFIX = "/component/";
	
	private static final String CONFIG_TEMPLATE = TEMPLATES_PREFIX+"config.xml";

	private static final String CLASS_TEMPLATE = TEMPLATES_PREFIX+"UIClass.java";

	private static final String RENDERER_TEMPLATE = TEMPLATES_PREFIX+"template.jspx";

	/**
	 * @parameter expression="${name}"
	 * @required
	 */
	private String name ;
	
	/**
	 * @parameter expression="${markup}"
	 */
	private String markup ;
	
	/**
	 * @parameter expression="${baseClass}" default-value="javax.faces.component.UIComponentBase"
	 */
	private String baseClass ;

	/**
	 * Directory where the output Java Files will be located.
	 * 
	 * @parameter expression="${project.build.plugins}"
	 * @required
	 * @readonly
	 */
     private List plugins ;
	
	/* (non-Javadoc)
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		if(project.getFile()!= null){
			boolean found= false;
			Plugin plugin = null;
			// Search for this plugin in project pom
			for (Iterator iter = plugins.iterator(); iter.hasNext();) {
				plugin = (Plugin) iter.next();
				if("maven-cdk-plugin".equals(plugin.getArtifactId())) {
					
					String groupId;
					if ("3.1.0".compareTo(plugin.getVersion()) > 0) {
						groupId = "org.ajax4jsf.cdk";
					} else {
						groupId = "org.richfaces.cdk";
					}

					if (groupId.equals(plugin.getGroupId())) {
						found = true;
						break;
					}
				}
			}
			if(found){
				try {
					createComponent(plugin);
				} catch (Exception e) {
					throw new MojoExecutionException("Error on create component",e);
				}
			} else {
				throw new MojoFailureException("This project is not configured for JSF components generation");
			}
		} else {
			throw new MojoFailureException("Goal 'create' must be run in existing project directory");
		}

	}

	/**
	 * @param plugin
	 * @throws Exception 
	 */
	private void createComponent(Plugin plugin) throws Exception {
		checkLibraryConfig();
		String className = Character.toUpperCase(name.charAt(0))+name.substring(1);
		VelocityContext context = new VelocityContext();
		context.put("name", name);
		context.put("className", className);
		String basePackage = baseClass.substring(0, baseClass.lastIndexOf('.'));
		String baseClassName = baseClass.substring(baseClass.lastIndexOf('.')+1);
		context.put("baseClass", baseClass);
		context.put("basePackage", basePackage);
		context.put("baseClassName", baseClassName);
		String prefix = library.getPrefix();
		context.put("package", prefix);
		context.put("prefix", prefix);
		String path = prefix.replace('.', '/');
		context.put("path", path);
		if(null == markup){
			markup = library.getRenderkits()[0].getMarkup();
		}
		context.put("markup", markup);
		String markupName = Character.toUpperCase(markup.charAt(0))+markup.substring(1);
		context.put("markupName", markupName);
		// Create component configuration file.
		File configFile = new File(componentConfigDirectory,name+".xml");
		writeParsedTemplate(CONFIG_TEMPLATE, context, configFile);
		File classFile = new File(project.getBuild().getSourceDirectory()+"/"+path+"/component/UI"+className+".java");
		writeParsedTemplate(CLASS_TEMPLATE, context, classFile);
		File templFile = new File(templatesDirectory,path+"/"+markup+className+".jspx");
		writeParsedTemplate(RENDERER_TEMPLATE, context, templFile);
	}

}
