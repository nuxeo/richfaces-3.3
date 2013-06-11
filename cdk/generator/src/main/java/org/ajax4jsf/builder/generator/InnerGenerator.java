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

package org.ajax4jsf.builder.generator;

import java.io.File;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.ComponentBaseBean;
import org.ajax4jsf.builder.generator.ClassPatternSet.PatternEntry;
import org.apache.tools.ant.BuildException;
import org.apache.velocity.Template;


/**
 * Base class for all JSF generators - component java file ,
 * faces-config.xml , jsp tag java file , renderer template, jsp & facelets taglib
 * inner element of {@link org.ajax4jsf.builder.ant.JSFGeneratorTask}
 * use in ant build.xml :
 * &lt;jsfgenerator ... &gt;
 *     ....
 *     &lt;inner ... /&gt;
 *     ....
 * &lt;/jsfgenerator&gt;
 * attributes:
 * destdir - override target directory for generated files from task.
 * templatename - override default name for velocity template.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.7 $ $Date: 2007/02/26 20:48:40 $
 *
 */
public abstract class InnerGenerator {

	/**
	 * Destination directory for generated files.
	 */
	private File _destDir = null;
	
	/**
	 * Name of Velocity template fale for genetation.
	 */
	private String _templateName;
	
	private JSFGeneratorConfiguration _config;
	
	private Logger _log;

	protected ClassPatternSet _patterns = new ClassPatternSet();
	

	/**
	 * @param task
	 */
	public InnerGenerator(JSFGeneratorConfiguration config, Logger log) {
		// TODO Auto-generated constructor stub
		_config = config;
		_log = log;
	}

	/**
	 * @return the config
	 */
	public JSFGeneratorConfiguration getConfig() {
		return this._config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(JSFGeneratorConfiguration config) {
		this._config = config;
	}

	/**
	 * @return Returns the destDir.
	 * @throws GeneratorException 
	 */
	public File getDestDir() throws GeneratorException {
		File destDir = _destDir;
		if (null == destDir) {
			destDir = _config.getDestDir();
		}
		if (destDir == null) {
			throw new GeneratorException("Destination directory not set");
		} else if (destDir.exists() && !destDir.isDirectory()) {
			throw new GeneratorException("Destination not is directory");
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		return destDir;
	}

	/**
	 * @param destDir The destDir to set.
	 */
	public void setDestDir(File destDir) {
		_destDir = destDir;
	}
	
	/**
	 * @return Returns the key.
	 */
	public String getKey() {
		return this._config.getKey();
	}


	public ClassLoader getClassLoader(){
		return _config.getClassLoader();
	}
	
	/**
	 * @return Returns the template.
	 */
	public String getTemplateName() {
		if (null != _templateName) {
			return _templateName;
		} else {
			return _config.getTemplatesPath()+"/"+getDefaultTemplateName();
		}
	}

	/**
	 * @param template The template to set.
	 */
	public void setTemplateName(String template) {
		_templateName = template;
	}

	public Template getTemplate() throws GeneratorException {
		return _config.getTemplate(getTemplateName());
	}
	
	
	/**
	 * Generate concrete files.
	 * @param config
	 * @throws BuildException
	 */
	public abstract void createFiles(BuilderConfig config) throws GeneratorException;

	/**
	 * @return default template name for this generator.
	 */
	protected abstract String getDefaultTemplateName();

	public Logger getLog() {
		return _log;
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.Task#log(java.lang.String)
	 */
	public void info(String arg0) {
		getLog().info(arg0);
	}

	/* (non-Javadoc)
	 * @see org.apache.tools.ant.Task#log(java.lang.String)
	 */
	public void debug(String arg0) {
		getLog().debug(arg0);
	}

	public PatternEntry createExclude() {
		return _patterns.createExclude();
	}

	public PatternEntry createInclude() {
		return _patterns.createInclude();
	}



	public void setExcludes(String arg0) {
		_patterns.setExcludes(arg0);
	}

	public void setIncludes(String arg0) {
		_patterns.setIncludes(arg0);
	}

	/**
	 * @param includes
	 * @param excludes
	 * @param component
	 * @param includeComponent
	 * @return
	 */
	protected boolean isIncludeComponent(ComponentBaseBean component) {
		boolean includeComponent = _patterns.matchClass(component.getName());
		return includeComponent;
	}
}
