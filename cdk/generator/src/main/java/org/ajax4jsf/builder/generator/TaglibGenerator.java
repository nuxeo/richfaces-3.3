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
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.ConverterBean;
import org.ajax4jsf.builder.config.FunctionBean;
import org.ajax4jsf.builder.config.ListenerBean;
import org.ajax4jsf.builder.config.ValidatorBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;


/**
 * Class implement functionality for generate jsp taglib .tld file
 * inner element of {@link org.ajax4jsf.builder.ant.JSFGeneratorTask}
 * use in ant build.xml :
 * &lt;jsfgenerator ... &gt;
 *     ....
 *     &lt;taglib ... /&gt;
 *     ....
 * &lt;/jsfgenerator&gt;
 * attributes:
 * taglib - name of .tld file
 * Next properties use for common tld elements :
 * uri - taglib uri
 * shortname -
 * description - taglib description
 * displayname
 * tlibversion , default 1.2
 * jspversion , default 2.0
 * listenerclass - full java name of context listener class
 * validatorclass - full java name of jsp validator class
 * include - name of file, included in tld before first tag element
 * For include-exclude components ( by component type ) from taglib, can be used attributes
 * includes or excludes , and nested elements &lt;include ... /&gt; and &lt;exclude ... /&gt;
 * with same sintax as other ant tasks
 * {@see com.exadel.vcp.builder.ant.InnerGenerator}
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/20 20:58:02 $
 *
 */
public class TaglibGenerator extends XMLConfigGenerator {
	
	private static final String TAGLIB_TEMPLATE_NAME = "taglib.vm";

	private File _taglib = null;
	
	private String _uri = null;
	
	private String _shortname = null;
	
	private String _description = null;
	
	private String _displayname = null;
	
	private String _tlibversion = "1.2";
	
	private String _jspversion = "2.0";
	
	private String _listenerclass = null;
	
	private String _validatorclass = null;

	/**
	 * @param task
	 */
	public TaglibGenerator(JSFGeneratorConfiguration task, Logger log) {
		super(task,log);
//		_patterns.setProject(task.getProject());
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#createFiles(com.exadel.vcp.builder.config.BuilderConfig)
	 */
	public void createFiles(BuilderConfig config) throws GeneratorException {
		// parsecomponents aganist patterns.
			List<ComponentBean> compmnents = new ArrayList<ComponentBean>();
			for (Iterator<ComponentBean> iter = config.getComponents().iterator(); iter.hasNext();) {
				ComponentBean component = iter.next();
				boolean includeComponent = true;
				// Check include patterns
				if (component.getTag() != null && (component.getTag().getTaglib() == null || component.getTag().getTaglib().equals(getShortname()))) {
					includeComponent = isIncludeComponent(component);
					if (includeComponent) {
						info("Component "+component.getName() +" included in taglib "+getShortname());
						compmnents.add(component);
					} else {
						info("Component "+component.getName() +" excluded from taglib "+getShortname());						
					}
				}
			}
         List<ValidatorBean> validators = new ArrayList<ValidatorBean>();
         for (Iterator<ValidatorBean> iter = config.getValidators().iterator(); iter.hasNext();) {
            ValidatorBean validator = iter.next();
            boolean includeComponent = true;
            // Check include patterns
            if (validator.getTag() != null && (validator.getTag().getTaglib() == null || validator.getTag().getTaglib().equals(getShortname()))) {
               includeComponent = isIncludeComponent(validator);
               if (includeComponent) {
                  info("Validator "+validator.getName() +" included in taglib "+getShortname());
                  validators.add(validator);
               } else {
                  info("Validator "+validator.getName() +" excluded from taglib "+getShortname());                
               }
            }
         }
         List<ConverterBean> converters = new ArrayList<ConverterBean>();
         for (Iterator<ConverterBean> iter = config.getConverters().iterator(); iter.hasNext();) {
            ConverterBean converter =  iter.next();
            boolean includeComponent = true;
            // Check include patterns
            if (converter.getTag() != null && (converter.getTag().getTaglib() == null || converter.getTag().getTaglib().equals(getShortname()))) {
               includeComponent = isIncludeComponent(converter);
               if (includeComponent) {
                  info("Converter "+converter.getName() +" included in taglib "+getShortname());
                  converters.add(converter);
               } else {
                  info("Converter "+converter.getName() +" excluded from taglib "+getShortname());                
               }
            }
         }
         

         List<ListenerBean> listeners = new ArrayList<ListenerBean>();
         for (Iterator<ListenerBean> iter = config.getListeners().iterator(); iter.hasNext();) {
        	 ListenerBean listener =  iter.next();
            boolean includeComponent = true;
            // Check include patterns
            if (listener.getTag() != null && (listener.getTag().getTaglib() == null || listener.getTag().getTaglib().equals(getShortname()))) {
               includeComponent = isIncludeComponent(listener);
               if (includeComponent) {
                  info("Listener "+listener.getName() +" included in taglib "+getShortname());
                  listeners.add(listener);
               } else {
                  info("Listener "+listener.getName() +" excluded from taglib "+getShortname());                
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
         
			if (compmnents.size() > 0 || validators.size() > 0 || converters.size() > 0 || functions.size() > 0) {
				VelocityContext context = new VelocityContext();
				Template template = getTemplate();
				// Put common properties
				if (compmnents.size() > 0) {
					context.put("components", compmnents);
				}
				if (validators.size() > 0) {
					context.put("validators", validators);
				}
				if (converters.size() > 0) {
					context.put("converters", converters);
				}
				if (listeners.size() > 0) {
					context.put("listeners", listeners);
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
					throw new GeneratorException("Error create new taglib file ", e);
				}
			} else {
				info("No components included in taglib "+getShortname());
			}

	}

	/* (non-Javadoc)
	 * @see com.exadel.vcp.builder.ant.InnerGenerator#getDefaultTemplateName()
	 */
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return TAGLIB_TEMPLATE_NAME;
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
	public String getDisplayname() {
		return _displayname;
	}

	/**
	 * @param displayName The displayName to set.
	 */
	public void setDisplayname(String displayName) {
		_displayname = displayName;
	}

	/**
	 * @return Returns the jspVersion.
	 */
	public String getJspversion() {
		return _jspversion;
	}

	/**
	 * @param jspVersion The jspVersion to set.
	 */
	public void setJspversion(String jspVersion) {
		_jspversion = jspVersion;
	}

	/**
	 * @return Returns the listenerClass.
	 */
	public String getListenerclass() {
		return _listenerclass;
	}

	/**
	 * @param listenerClass The listenerClass to set.
	 */
	public void setListenerclass(String listenerClass) {
		_listenerclass = listenerClass;
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
	 * @return Returns the tlibVersion.
	 */
	public String getTlibversion() {
		return _tlibversion;
	}

	/**
	 * @param tlibVersion The tlibVersion to set.
	 */
	public void setTlibversion(String tlibVersion) {
		_tlibversion = tlibVersion;
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

	/**
	 * @return Returns the validatorClass.
	 */
	public String getValidatorclass() {
		return _validatorclass;
	}

	/**
	 * @param validatorClass The validatorClass to set.
	 */
	public void setValidatorclass(String validatorClass) {
		_validatorclass = validatorClass;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.XMLConfigGenerator#getRootTag()
	 */
	protected String getRootTag() {
		// 
		return "taglib";
	}

}
