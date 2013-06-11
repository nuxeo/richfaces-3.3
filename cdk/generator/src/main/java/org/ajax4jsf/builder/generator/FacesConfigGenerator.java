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
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Class implement functionality for generate faces-config file inner element of
 * {@link org.ajax4jsf.builder.ant.JSFGeneratorTask} use in ant build.xml :
 * &lt;jsfgenerator ... &gt; .... &lt;facesconfig ... /&gt; ....
 * &lt;/jsfgenerator&gt; attributes: facesconfig - name of faces-config file
 * include - name of file, included in config renderkitid - name of renderkit
 * for renderers renderkitclass - full Java class name of
 * {@link javax.faces.render.RenderKit} implementation
 * {@see com.exadel.vcp.builder.ant.InnerGenerator} Nested elements :
 * &lt;renderkit renderkitid="..." renderkitclass="..." [package="..."] &gt; for
 * generate any of render-kits at time.
 * {@see com.exadel.vcp.builder.ant.RenderKitBean}
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.5 $ $Date: 2007/02/20 20:58:00 $
 * 
 */
public class FacesConfigGenerator extends XMLConfigGenerator {

	private static final String FACES_CONFIG_TEMPLATE_NAME = "faces-config.vm";

	private File _facesconfig = null;

	private List<RenderKitBean> renderKits = new ArrayList<RenderKitBean>();

	private ClassLoader _loader;

	/**
	 * @param task
	 */
	public FacesConfigGenerator(JSFGeneratorConfiguration task, Logger log) {
		super(task, log);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#createFiles(com.exadel.vcp.builder.config.BuilderConfig)
	 */
	public void createFiles(BuilderConfig config) throws GeneratorException {
		VelocityContext context = new VelocityContext();
		Template template = getTemplate();
		// Put common properties
		_loader = config.getLoader();
		context.put("components", config.getComponents());
		context.put("validators", config.getValidators());
		context.put("converters", config.getConverters());
		context.put("renderers", config.getRenderers());
		context.put("facesConfig", this);
		File configFile = getFacesconfig();// new File(getDestDir(),
											// resultPath);
		File javaDir = configFile.getParentFile();
		if (!javaDir.exists()) {
			javaDir.mkdirs();
		}
		try {
			if (configFile.exists()) {
				configFile.delete();
			}
			Writer out = new BufferedWriter(new FileWriter(configFile));
			template.merge(context, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new GeneratorException("Error create new faces-config.xml ",
					e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#getDefaultTemplateName()
	 */
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return FACES_CONFIG_TEMPLATE_NAME;
	}

	/**
	 * @return Returns the taglib.
	 */
	public File getFacesconfig() {
		return _facesconfig;
	}

	/**
	 * @param taglib
	 *            The taglib to set.
	 */
	public void setFacesconfig(File taglib) {
		_facesconfig = taglib;
	}

	/**
	 * @return Returns the renderKits.
	 */
	public List<RenderKitBean> getRenderKits() {
		return renderKits;
	}

	/**
	 * Create instance of child &lt;renderkit&gt; element
	 * 
	 * @return
	 */
	public RenderKitBean createRenderKit() {
		RenderKitBean renderKit = new RenderKitBean();
		this.renderKits.add(renderKit);
		return renderKit;
	}

	/**
	 * called from a template to validate if a class exists
	 * 
	 * @param className
	 * @return
	 */
	public boolean rendererExists(String className) {
		boolean exists = false;
		try {
			Class.forName(className, false, _loader);
			exists = true;
		} catch (Exception ex) {
			info("Class " + className
					+ " not found, therefore not registered in faces-config");
		}
		return exists;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.builder.generator.XMLConfigGenerator#getRootTag()
	 */
	protected String getRootTag() {

		return "faces-config";
	}
}
