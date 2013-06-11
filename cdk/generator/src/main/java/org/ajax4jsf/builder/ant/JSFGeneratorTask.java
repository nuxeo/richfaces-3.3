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

package org.ajax4jsf.builder.ant;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ParsingException;
import org.ajax4jsf.builder.generator.ComponentGenerator;
import org.ajax4jsf.builder.generator.FaceletsTaglibGenerator;
import org.ajax4jsf.builder.generator.FacesConfigGenerator;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.InnerGenerator;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.generator.ListenerGenerator;
import org.ajax4jsf.builder.generator.RendererGenerator;
import org.ajax4jsf.builder.generator.ComponentTagGenerator;
import org.ajax4jsf.builder.generator.TagHandlerGenerator;
import org.ajax4jsf.builder.generator.TaglibGenerator;
import org.ajax4jsf.builder.velocity.BuilderContext;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Ant.Reference;
import org.apache.tools.ant.types.FileList;
import org.apache.tools.ant.types.Path;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;


/**
 * Base Task for build componenta,renderers,faces-config,tag and tld files.
 * for use in ant build.xml , task must be defined as :
 * &lt;taskdef  classpathref="taskClassPath" 
 *   classname="com.exadel.vcp.builder.ant.JSFGeneratorTask" 
 *   name="jsfgenerator"&gt;
 * Where classpath for task must contain component-builder.jar, velocity-dep.jar 
 * commons-beanutils.jar and commons-digister.jar . This Task use JDK 1.5 
 * To invoke task , put 
 * &lt;jsfgenerator configfile="../conf/test.xml" 
 *     destdir="../generated"   
 *     description="Build classes and configs" &gt;
 *     ....
 * &lt;/jsfgenerator&gt;
 * mantadory attributes :
 *    configfile - point to xml configuration file with components descriptions. For detail of configuration,
 *     see {@link META-INF/schema/component-config.dtd} and {@link org.ajax4jsf.builder.config.ComponentBean}
 *     also, can use instead {@link org.apache.tools.ant.types.FileList} type child element &lt;configs&gt; with other config files,
 *     for syntax see Ant &lt;folelist&gt; element
 * optional attributes :
 *    destdir - destination directory for generated files. If not set, must be set in nesting generation elements.
 *    templates - directory with custom templates. If template not found in this directory, use default fom jar
 *    classpath, classpathref - classpath definition for load components,tags,renderers classes for analaize properties
 * for generation of concrete files, used nested elements :
 * 	&lt;components&gt; generate components implementation classes {@see com.exadel.vcp.builder.ant.ComponentGenerator}
 *  &lt;renderers&gt;  generate renderers implementation classes  {@see com.exadel.vcp.builder.ant.RendererGenerator}
 *  &lt;tags&gt;  generate jsp tags implementation classes {@see com.exadel.vcp.builder.ant.TagGenerator}
 *  &lt;facesconfig&gt; - generate jsf configuration file {@see com.exadel.vcp.builder.ant.FacesConfigGenerator}
 *  &lt;taglib&gt; generate taglib .tld file {@see com.exadel.vcp.builder.ant.TaglibGenerator}
 *  &lt;faceletstaglib&gt; - generate taglib for facelets {@see com.exadel.vcp.builder.ant.FaceletsTaglibGenerator}
 *  also, can use nested &lt;classpath&gt; element, same as javac ant task      
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.11 $ $Date: 2007/02/26 20:49:03 $
 *
 */
public  class JSFGeneratorTask extends Task implements JSFGeneratorConfiguration {

	private FileList _configs;
	/**
	 * Configuration file for build 
	 */
	private File configFile;
	/**
	 * Destination to place sources and config files
	 */
	private File destDir;
	
	/**
	 * Directory for velocity templates 
	 */
	private File templates;
	
	/**
	 * Classpath for load components and other generation-related classes.
	 */
	private Path _classpath;


	private	BuilderConfig _config = null;
	
	private List<InnerGenerator> _inners = new ArrayList<InnerGenerator>();
	
	private String key;
	
	private String templatesPath=BuilderContext.TEMPLATES_PATH;
	/**
	 * @return Returns the configFile.
	 */
	public File getConfigFile() {
		return configFile;
	}

	/**
	 * @param configFile The configFile to set.
	 */
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	
	/**
	 * Create child element for list of config files.
	 * Task Can have only one such child
	 * @return
	 */
	public FileList createConfigs() {
		this._configs = new FileList();
		return _configs;
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.ant.JSFGeneratorConfiguration#getDestDir()
	 */
	public File getDestDir() {
		return destDir;
	}

	/**
	 * @param destDir The destDir to set.
	 */
	public void setDestDir(File destDir) {
		this.destDir = destDir;
	}

	/**
	 * @return Returns the templates.
	 */
	public File getTemplates() {
		return templates;
	}

	/**
	 * @param templates The templates to set.
	 */
	public void setTemplates(File templates) {
		this.templates = templates;
	}

	/**
	 * @return Returns the classpath.
	 */
	public Path getClasspath() {
		return _classpath;
	}

	/**
	 * @param classpath The classpath to set.
	 */
	public void setClasspath(Path classpath) {
		if(null == _classpath) {
			_classpath = classpath;
		} else {
			_classpath.add(classpath);
		}
	}

	public Path createClasspath() {
		Path classpath = new Path(getProject());
		if(null == _classpath) {
			_classpath = classpath;
		} else {
			_classpath.add(classpath);
		}
		return classpath;
	}
	
	public void setClasspathRef(Reference ref) {
		Object refObj = ref.getReferencedObject();
		if (refObj instanceof Path) {
			setClasspath( (Path) refObj );			
		}
	}
	final private static String VELOCITY_PROPERTIES = "velocity.properties";

	
	private VelocityEngine engine;
	
	private Map<String, Template> _templates = new HashMap<String, Template>();

	
	public void init() throws BuildException {
		super.init();
		Properties velocityProperties = new Properties();
		try {
		InputStream streamVelosityProperties = JSFGeneratorTask.class.getResourceAsStream(VELOCITY_PROPERTIES);
		velocityProperties.load(streamVelosityProperties);
		engine = new VelocityEngine();
		engine.init(velocityProperties);
		} catch(Exception e){
			throw new BuildException("Error init velocity engine",e);
		}
	}

	public Template getTemplate(String name) throws GeneratorException {
		Template template = _templates.get(name);
		if(null == template){
			try {
				template = engine.getTemplate(name);
			} catch (ResourceNotFoundException e) {
				throw new GeneratorException(e.getLocalizedMessage());
			} catch (ParseErrorException e) {
				throw new GeneratorException(e.getLocalizedMessage());
			} catch (Exception e) {
				throw new GeneratorException(e.getLocalizedMessage());
			}
			_templates.put(name, template);
		}
		return template;
	}

	
	/* (non-Javadoc)
	 * @see org.apache.tools.ant.Task#execute()
	 */
	public void execute() throws BuildException {
		BuilderConfig config;
		config = getBuilderConfig();
		for (Iterator<InnerGenerator> it = _inners.iterator(); it.hasNext();) {
			InnerGenerator generator = it.next();
			getProject().log("Build files for subtask "+generator.getClass().getSimpleName());
			try {
				generator.createFiles(config);
			} catch (Exception e) {
				getProject().log("Error for buid files by builder "+generator.getClass().getSimpleName(),Project.MSG_ERR);
				e.printStackTrace();
				throw new BuildException("Error for buid files by builder "+generator.getClass().getSimpleName(),e);
			}
		}
	}

	/**
	 * Lazy parsing and creation of builder configuration.
	 * @param config
	 * @return
	 * @throws BuildException
	 */
	private BuilderConfig getBuilderConfig() throws BuildException {
		if (null == _config) {
			// Test real configuration
			if (getConfigFile() == null && getConfigs() == null) {
				throw new BuildException("Config file name not set");
			}
			// Init Velocity
			try {
				BuilderContext.init(getTemplates());
			} catch (GeneratorException e1) {
				throw new BuildException(e1);
			}
			// Parse configuration
			try {
				_config = new BuilderConfig(getClassLoader(),new AntLogger(this));
				if (null != getConfigFile()) {
					_config.parseConfig(getConfigFile());
				}
				if(null != getConfigs()) {
					String[] files = getConfigs().getFiles(getProject());
					for (int i = 0; i < files.length; i++) {
						String file = files[i];
						_config.parseConfig(new File(getConfigs().getDir(getProject()),file));
					}
				}
				_config.checkComponentProperties();
			} catch (ParsingException e) {
				e.printStackTrace();
				throw new BuildException("Error building ", e);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new BuildException("Error building ", e);
			}
		}
		return _config;
	}

	public ClassLoader getClassLoader() {
		return getProject().createClassLoader(getClasspath());
	}

	public ComponentGenerator createComponents() {
		 ComponentGenerator generator = new ComponentGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}
	
	public FacesConfigGenerator createFacesconfig() {
		 FacesConfigGenerator generator = new FacesConfigGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}
	public RendererGenerator createRenderers() {
		 RendererGenerator generator = new RendererGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}
	public ComponentTagGenerator createTags() {
		 ComponentTagGenerator generator = new ComponentTagGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}
	public TaglibGenerator createTaglib() {
		 TaglibGenerator generator = new TaglibGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}
	
	public FaceletsTaglibGenerator createFaceletsTaglib() {
		FaceletsTaglibGenerator generator = new FaceletsTaglibGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}

	public TagHandlerGenerator createTagHandler() {
		 TagHandlerGenerator generator = new TagHandlerGenerator(this,new AntLogger(this));
		 _inners.add(generator);
		 return generator;
	}

	public ListenerGenerator createListenerGenerator() {
		ListenerGenerator generator = new ListenerGenerator(this, new AntLogger(this));
		_inners.add(generator);
		return generator;
	}

	/**
	 * @return Returns the configs.
	 */
	private FileList getConfigs() {
		return _configs;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.ant.JSFGeneratorConfiguration#getKey()
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @param key The key to set.
	 */
	public void setKey(String key) {
		this.key = key;
		if(key != null && key.length() == 0){
			this.key = null;
		}
			
	}

	/**
	 * @return the templatesPath
	 */
	public String getTemplatesPath() {
		return this.templatesPath;
	}

	/**
	 * @param templatesPath the templatesPath to set
	 */
	public void setTemplatesPath(String templatesPath) {
		this.templatesPath = templatesPath;
	}
	
	
}
