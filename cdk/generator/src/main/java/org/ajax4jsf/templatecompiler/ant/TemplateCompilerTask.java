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

package org.ajax4jsf.templatecompiler.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.builder.TemplateCompiler;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * Task template compiler.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/26 20:48:56 $
 * 
 */
public class TemplateCompilerTask extends Task implements FilenameFilter {
	final private static String regexComponent = "^(.*)\\.xhtml$";

	final private static Pattern patternComponent = Pattern
			.compile(regexComponent);


	private final static String TEMPLATE_FILE_EXT = ".xhtml";

	/**
	 * classpath for load component/renderer classes for introspection.
	 */
	private Path classPath;

	private File srcDir;

	private File destDir;

	protected Vector filesets = new Vector();


	/**
	 * Default constructor
	 * 
	 * @throws Exception
	 */
	public TemplateCompilerTask() throws Exception {
		super();

	}

	/**
	 * getting srcDir
	 * 
	 * @return Returns the destDir
	 */
	public File getSrcdir() {
		return this.srcDir;
	}

	/**
	 * setting a srcDir
	 * 
	 * @param destDir
	 *            The srcDir to set.
	 */
	public void setSrcdir(File srcDir) {
		this.srcDir = srcDir;
	}

	/**
	 * getting a destDit
	 * 
	 * @return Returns the destDir.
	 */
	public File getDestdir() {
		return this.destDir;
	}

	/**
	 * setting a destDir
	 * 
	 * @param destDir
	 *            The destDir to set.
	 */
	public void setDestdir(File destDir) {
		this.destDir = destDir;
	}

	/**
	 * Generate java class name from template filename
	 * 
	 * @param fileTemplateName
	 *            a string contain template filename
	 * @return string contain java class name
	 */
	public String generateJavaClassName(final String fileTemplateName) {

		String strJavaClassName = "Unknown";

		Matcher mather = patternComponent.matcher(fileTemplateName);

		if (mather.find()) {
			strJavaClassName = mather.group(1);
		} // if

		return strJavaClassName;
	}

	/**
	 * 
	 * @param sFileTemplate
	 */
	public void processTemplate(String sFileTemplate) {
		// Generate java class name

		ClassLoader loader = getClassLoader();

		try {

			CompilationContext componentBean = new AntCompilationContext(this,loader);
			TemplateCompiler templateCompiler = new TemplateCompiler();

			// componentBean.setDefaultVariables();

			InputStream inputStream = null;
			File inputFile = new File(sFileTemplate);
			String sF = inputFile.getName().toString();

			componentBean.setFullClassName(generateJavaClassName(sF));

			inputStream = new FileInputStream(inputFile);

			templateCompiler.processing(inputStream, componentBean);

			String resultPath = componentBean.getComponentFileName() + ".java";
			File javaFile = new File(getDestdir(), resultPath);
			File javaDir = javaFile.getParentFile();
			if (!javaDir.exists()) {
				javaDir.mkdirs();
			} // try

			if (javaFile.exists()) {
				javaFile.delete();
			} // if

			Writer out = new BufferedWriter(new FileWriter(javaFile));
			
			templateCompiler.generateCode(componentBean, out);

			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new BuildException(e);
		} catch (Exception e) {
			throw new BuildException(
					"Error create new Component Java file, message "
							+ e.getLocalizedMessage(), e);
		} // catch
	}

	public ClassLoader getClassLoader() {
		ClassLoader loader = getProject().createClassLoader(this.classPath);
		if (null == loader) {
			loader = this.getClass().getClassLoader();
		}
		return loader;
	}

	/**
	 * Adds a set of files to copy.
	 * 
	 * @param set
	 *            a set of files to copy
	 */
	public void addFileset(FileSet set) {
		this.filesets.addElement(set);
	}

	final private static String VELOCITY_PROPERTIES = "velocity.properties";

	
	private VelocityEngine engine;
	
	private Map<String, Template> _templates = new HashMap<String, Template>();

	
	public void init() throws BuildException {
		super.init();
		Properties velocityProperties = new Properties();
		try {
		InputStream streamVelosityProperties = TemplateCompilerTask.class.getResourceAsStream(VELOCITY_PROPERTIES);
		velocityProperties.load(streamVelosityProperties);
		engine = new VelocityEngine();
		engine.init(velocityProperties);
		} catch(Exception e){
			throw new BuildException("Error init velocity engine",e);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	public void execute() throws BuildException {
		ArrayList arrayFiles = new ArrayList();

		if (getSrcdir() != null) {
			String directory = getSrcdir().toString();
			String[] files = new File(directory).list(this);
			for (int i = 0; i < files.length; i++) {
				arrayFiles.add(directory + File.separatorChar + files[i]);
			} // for
		}

		if (this.filesets.size() == 1) {
			// deal with the filesets
			for (int i = 0; i < this.filesets.size(); i++) {
				FileSet fs = (FileSet) this.filesets.elementAt(i);
				DirectoryScanner ds = null;
				try {
					ds = fs.getDirectoryScanner(getProject());
				} catch (BuildException e) {
					if (!e.getMessage().endsWith(" not found.")) {
						throw e;
					} else {
						log("Warning: " + e.getMessage());
						continue;
					}
				}

				File fromDir = fs.getDir(getProject());

				String[] srcFiles = ds.getIncludedFiles();

				for (int j = 0; j < srcFiles.length; j++) {
					String tempFile = fromDir.getAbsolutePath().toString()
							+ File.separator + srcFiles[j];
					arrayFiles.add(tempFile);
				}
			}

		}

		if (arrayFiles.size() == 0) {
			log("Empty list");
		} else {
			for (Iterator iter = arrayFiles.iterator(); iter.hasNext();) {
				String file = (String) iter.next();
				log("Processing file :'" + file + "'");
				processTemplate(file);
			}
		}

	}

	/**
	 * Tests if a specified file should be included in a file list.
	 * 
	 * @param dir
	 *            the directory in which the file was found.
	 * @param name
	 *            the name of the file.
	 * @return true if and only if the name should be included in the file list;
	 *         false otherwise.
	 */
	public boolean accept(File dir, String name) {
		if (name.endsWith(TEMPLATE_FILE_EXT)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the classpath to be used for this compilation.
	 * 
	 * @return Returns the classpath.
	 */
	public Path getClasspath() {
		return this.classPath;
	}

	/**
	 * Set the classpath to be used for this compilation
	 * 
	 * @param classpath
	 *            an Ant Path object containing the compilation classpath.
	 */
	public void setClasspath(Path classpath) {
		if (null == this.classPath) {
			this.classPath = classpath;
		} else {
			this.classPath.add(classpath);
		}
	}

	/**
	 * Adds a reference to a classpath defined elsewhere.
	 * 
	 * @param ref
	 *            a reference to a classpath
	 */
	public void setClasspathRef(Reference ref) {
		createClasspath().setRefid(ref);
	}

	/**
	 * Add a path to the classpath
	 * 
	 * @return a class path to be configured
	 */
	public Path createClasspath() {
		Path classpath = new Path(getProject());
		if (null == this.classPath) {
			this.classPath = classpath;
		} else {
			this.classPath.add(classpath);
		}
		return classpath;
	}

	public Template getTemplate(String name) throws CompilationException {
		Template template = _templates.get(name);
		if(null == template){
			try {
				template = engine.getTemplate(name);
			} catch (ResourceNotFoundException e) {
				throw new CompilationException(e.getLocalizedMessage());
			} catch (ParseErrorException e) {
				throw new CompilationException(e.getLocalizedMessage());
			} catch (Exception e) {
				throw new CompilationException(e.getLocalizedMessage());
			}
			_templates.put(name, template);
		}
		return template;
	}

}
