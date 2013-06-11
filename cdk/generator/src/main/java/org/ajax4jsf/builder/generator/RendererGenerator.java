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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.util.Iterator;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ComponentBaseBean;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.RendererBean;
import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.builder.TemplateCompiler;

/**
 * Class implement functionality for generate base renderers java files, for
 * manual extention. If renderer property override in config file set to true,
 * override existing renderers files. inner element of
 * {@link org.ajax4jsf.builder.ant.JSFGeneratorTask} use in ant build.xml :
 * &lt;jsfgenerator ... &gt; .... &lt;renderers ... /&gt; ....
 * &lt;/jsfgenerator&gt; attributes: package - optional Java package name for
 * override from configuration, used for create set of differern render-kits in
 * one task {@see com.exadel.vcp.builder.ant.InnerGenerator}
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.10 $ $Date: 2007/02/26 20:48:40 $
 * 
 */
public class RendererGenerator extends InnerGenerator {

	private static final String RENDERER_TEMPLATE_NAME = "renderer.vm";

	private File _srcDir = null;

	/**
	 * Optional package name for override from config.
	 */
	private String _package = null;

	/**
	 * @return Returns the package.
	 */
	public String getPackage() {
		return _package;
	}

	/**
	 * @param package1
	 *            The package to set.
	 */
	public void setPackage(String package1) {
		_package = package1;
	}

	/**
	 * @return the srcDir
	 */
	public File getSrcDir() {
		return this._srcDir;
	}

	/**
	 * @param srcDir
	 *            the srcDir to set
	 */
	public void setSrcDir(File srcDir) {
		this._srcDir = srcDir;
	}

	/**
	 * @param task
	 */
	public RendererGenerator(JSFGeneratorConfiguration task, Logger log) {
		super(task, log);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#createFiles(com.exadel.vcp.builder.config.BuilderConfig)
	 */
	public void createFiles(BuilderConfig config) throws GeneratorException {
		// Put common properties
		for (Iterator iter = config.getComponents().iterator(); iter.hasNext();) {
			ComponentBean component = (ComponentBean) iter.next();
			RendererBean renderer = component.getRenderer();
			createRenderer(renderer, component);
		}
		for (Iterator iter = config.getRenderers().iterator(); iter.hasNext();) {
			RendererBean renderer = (RendererBean) iter.next();
			createRenderer(renderer, null);
		}
	}

	public void createRenderer(RendererBean renderer, ComponentBaseBean component)
			throws GeneratorException {
		if (null != renderer && renderer.isGenerate()
				&& null != renderer.getTemplate()) {
			try {
				File template;
				if (null != getSrcDir()) {
					template = new File(getSrcDir(), renderer.getTemplate());
				} else {
					template = new File(renderer.getTemplate());
				}
				ClassLoader classLoader = getClassLoader();
				CompilationContext rendererBean = new RendererCompilationContext(
						getLog(), classLoader,getConfig());
				if (null != renderer.getClassname()) {
					rendererBean.setBaseclass(renderer.getClassname());

				}
				if (null != renderer.getClassname()) {
					rendererBean.setFullClassName(renderer.getClassname());
				}
				if (null != component) {
					try {
						rendererBean
								.setComponentClass(component.getClassname());
					} catch (CompilationException e) {
						if (null != component.getSuperclass()) {
							rendererBean.setComponentClass(component
									.getSuperclass());
						}
					}

				}

// unfinished check of non-static fields in renderer classes				
//				
//				String baseclassName = rendererBean.getFullClassName();
//				try {
//				    classLoader.loadClass(baseclassName);
//				} catch (ClassNotFoundException e) {
//				    getLog().debug("Class not found: " + e.getLocalizedMessage());
//
//				    baseclassName = rendererBean.getFullBaseclass();
//				    classLoader.loadClass(baseclassName);
//				}
//				
//				if (baseclassName != null) {
//				    Class<?> baseclass = classLoader.loadClass(baseclassName);
//				    Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) classLoader.loadClass("org.richfaces.AllowedRendererField");
//
//				    do {
//					Field[] fields = baseclass.getDeclaredFields();
//					for (Field field : fields) {
//					    if ((field.getModifiers() & Modifier.STATIC) == 0 && field.getAnnotation(annotationClass) == null) {
//						throw new RuntimeException("Non-static field: " + field + " in renderer class. ");
//					    }
//					}
//					
//					baseclass = baseclass.getSuperclass();
//				    } while (baseclass != null);
//				}
				
				TemplateCompiler templateCompiler = new TemplateCompiler();
				InputStream templateStream = new FileInputStream(template);
				templateCompiler.processing(templateStream, rendererBean);
				renderer.setClassname(rendererBean.getFullClassName());
				String resultPath = rendererBean.getComponentFileName();
				if (null == getPackage()) {
					resultPath = resultPath.replace('.', '/');
				} else {
					resultPath = getPackage().replace('.', '/') + "/"
							+ resultPath;
				}

				File javaFile = new File(getDestDir(), resultPath + ".java");
				File javaDir = javaFile.getParentFile();
				if (!javaDir.exists()) {
					javaDir.mkdirs();
				}
				if (javaFile.exists()) {
					if (renderer.isOverride()) {
						javaFile.delete();
					} else {
						return;
					}
				}
				Writer out = new BufferedWriter(new FileWriter(javaFile));
				templateCompiler.generateCode(rendererBean, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				throw new GeneratorException(
						"Error create Renderer Java file ", e);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#getDefaultTemplateName()
	 */
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return RENDERER_TEMPLATE_NAME;
	}

}
