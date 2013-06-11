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
import java.util.Iterator;
import java.util.List;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ComponentBaseBean;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.ConverterBean;
import org.ajax4jsf.builder.config.FunctionBean;
import org.ajax4jsf.builder.config.ListenerBean;
import org.ajax4jsf.builder.config.ValidatorBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.5 $ $Date: 2007/02/20 20:58:00 $
 *
 */
public class FaceletsTaglibGenerator extends XMLConfigGenerator {

	private static final String FACELETS_TAGLIB_TEMPLATE_NAME = "facelets.taglib.vm";

	private File _taglib = null;
	
	private String _uri = null;
	
	private String _shortname = null;
	
	private String _description = null;
	
	private String _displayName = null;

	/**
	 * @param task
	 */
	public FaceletsTaglibGenerator(JSFGeneratorConfiguration task, Logger log) {
		super(task,log);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#createFiles(com.exadel.vcp.builder.config.BuilderConfig)
	 */
	public void createFiles(BuilderConfig config) throws GeneratorException {
		VelocityContext context = new VelocityContext();
		Template template = getTemplate();
		// Put common properties
		List<ComponentBean> components = new ArrayList<ComponentBean>();
		for (Iterator iter = config.getComponents().iterator(); iter.hasNext();) {
			ComponentBean component = (ComponentBean) iter.next();
			boolean includeComponent = true;
			// Check include patterns
			if (component.getTag() != null || component.getTaghandler() != null) {
				includeComponent = isIncludeComponent(component);
				if (includeComponent) {
					info("Component "+component.getName() +" included to taglib "+getShortname());
					components.add(component);
				} else {
					info("Component "+component.getName() +" excluded from taglib "+getShortname());						
				}
			}
		}
		
		List<ValidatorBean> validators = new ArrayList<ValidatorBean>();
		for (Iterator iter = config.getValidators().iterator(); iter.hasNext();) {
			ValidatorBean validator = (ValidatorBean) iter.next();
			boolean includeValidator = true;
			// Check include patterns
			if (validator.getTag() != null || validator.getTaghandler() != null) {
				includeValidator = isIncludeComponent(validator);
				if (includeValidator) {
					info("Validator "+validator.getName() +" included to taglib "+getShortname());
					validators.add(validator);
				} else {
					info("Validator "+validator.getName() +" excluded from taglib "+getShortname());						
				}
			}
		}
		
		List<ConverterBean> converters = new ArrayList<ConverterBean>();
		for (Iterator iter = config.getConverters().iterator(); iter.hasNext();) {
			ConverterBean converter = (ConverterBean) iter.next();
			boolean includeConverter = true;
			// Check include patterns
			if (converter.getTag() != null || converter.getTaghandler() != null) {
				includeConverter = isIncludeComponent(converter);
				if (includeConverter) {
					info("Converter "+converter.getName() +" included to taglib "+getShortname());
					converters.add(converter);
				} else {
					info("Converter "+converter.getName() +" excluded from taglib "+getShortname());						
				}
			}
		}

		List<ListenerBean> listeners = new ArrayList<ListenerBean>();
		for (ListenerBean bean : config.getListeners()) {
			for (ComponentBaseBean componentBean : bean.getSuitableComponents()) {
				if (components.contains(componentBean) && bean.getTaghandler() != null) {
					listeners.add(bean);
					break;
				}
			}
		}

		List<FunctionBean> functions = new ArrayList<FunctionBean>();
	         for (FunctionBean functionBean : config.getFunctions()) {
	             if (isIncludeComponent(functionBean)) {
	                 info("Function "+functionBean.getName() +" included in taglib "+getShortname());
	                 functions.add(functionBean);
	             } else {
	                 info("Function "+functionBean.getName() +" excluded from taglib "+getShortname());
	             }
	         };
		
		if (components.size() > 0 || validators.size() > 0 || converters.size() > 0 || functions.size() > 0) {
				if (listeners.size() > 0) {
					context.put("listeners", listeners);
				}

				if (components.size() > 0)
				{
					context.put("components", components);
				}
				if (validators.size() > 0)
				{
					context.put("validators", validators);
				}
				if (converters.size() > 0)
				{
					context.put("converters", converters);
				}
				if (functions.size() > 0) {
				    	context.put("functions", functions);
				}
				context.put("taglib", this);
				File taglibFile = getTaglib();//new File(getDestDir(), resultPath);
				File javaDir = taglibFile.getParentFile();
				if (!javaDir.exists()) {
					javaDir.mkdirs();
				}
				try {
					if (taglibFile.exists()) {
						taglibFile.delete();
					}
					Writer out = new BufferedWriter(new FileWriter(taglibFile));
					template.merge(context, out);
					out.flush();
					out.close();
				} catch (Exception e) {
					throw new GeneratorException(
							"Error create new taglib file ", e);
				}
		}

	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#getDefaultTemplateName()
	 */
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return FACELETS_TAGLIB_TEMPLATE_NAME;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		_description = description;
	}

	/**
	 * @return Returns the displayName.
	 */
	public String getDisplayName() {
		return _displayName;
	}

	/**
	 * @param displayName The displayName to set.
	 */
	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	/**
	 * @return Returns the shortName.
	 */
	public String getShortname() {
		return _shortname;
	}

	/**
	 * @param shortName The shortName to set.
	 */
	public void setShortname(String shortName) {
		_shortname = shortName;
	}

	/**
	 * @return Returns the taglib.
	 */
	public File getTaglib() {
		return _taglib;
	}

	/**
	 * @param taglib The taglib to set.
	 */
	public void setTaglib(File taglib) {
		_taglib = taglib;
	}

	/**
	 * @return Returns the uri.
	 */
	public String getUri() {
		return _uri;
	}

	/**
	 * @param uri The uri to set.
	 */
	public void setUri(String uri) {
		_uri = uri;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.XMLConfigGenerator#getRootTag()
	 */
	protected String getRootTag() {
		
		return "facelet-taglib";
	}

}
