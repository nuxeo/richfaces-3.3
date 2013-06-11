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
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Nick - mailto:nbelaevski@exadel.com created 17.04.2007
 * 
 */
public class ResourcesConfigGenerator extends FacesConfigGenerator {

	private File resourcesConfig;
	private File templatesDirectory;
	private ResourcesConfigGeneratorBean resourcesConfigGeneratorBean;
	
	private static final SAXParserFactory parserFactory = SAXParserFactory
			.newInstance();

	public ResourcesConfigGenerator(JSFGeneratorConfiguration task, Logger log) {
		super(task, log);
	}

	private void parseXCSSResource(String resourcePath,
			final ResourcesConfigGeneratorBean bean) throws SAXException,
			IOException, ParserConfigurationException {
		InputStream resourceStream = getClassLoader().getResourceAsStream(
				resourcePath);
		if (resourceStream != null) {
			debug("XCSS file exists in classpath");

			try {
				SAXParser parser = parserFactory.newSAXParser();
				parser.parse(resourceStream, new DefaultHandler() {
					@Override
					public void startElement(String uri, String localName,
							String name, Attributes attributes)
							throws SAXException {

						super.startElement(uri, localName, name, attributes);

						if ("f:resource".equals(name)) {
							String value = attributes.getValue("f:key");

							if (value != null) {
								debug("Adding resource: " + value);

								bean.addResource(value, "", null, true);
							}
						}
					}
				});

			} finally {
				try {
					resourceStream.close();
				} catch (IOException e) {
					getLog().error(e.getLocalizedMessage(), e);
				}
			}
		} else {
			getLog().error("Resource " + resourcePath + " hasn't been found!");
		}
	}

	private boolean isXCSSPath(String resourcePath) {
		return resourcePath != null && resourcePath.endsWith(".xcss");
	}

	public void createFiles(BuilderConfig config) throws GeneratorException {
		VelocityContext context = new VelocityContext();
		Template template = getTemplate();
		try {
			// Put common properties

			final ResourcesConfigGeneratorBean bean = new ResourcesConfigGeneratorBean();
			if (this.resourcesConfigGeneratorBean != null) {
				bean.merge(resourcesConfigGeneratorBean);
			}
			
			String includedContent = getIncludeContent();
			if (includedContent != null && includedContent.length() != 0) {
				String parseableContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><resource-config>"
						+ includedContent + "</resource-config>";

				SAXParser parser = parserFactory.newSAXParser();
				parser.parse(
						new InputSource(new StringReader(parseableContent)),
						new DefaultHandler() {
							private StringBuilder path;

							@Override
							public void startElement(String uri,
									String localName, String name,
									Attributes attributes) throws SAXException {

								super.startElement(uri, localName, name,
										attributes);

								if ("path".equals(name)) {
									this.path = new StringBuilder();
								}
							}

							public void characters(char[] ch, int start,
									int length) throws SAXException {
								if (this.path != null) {
									this.path.append(ch, start, length);
								}
							};

							@Override
							public void endElement(String uri,
									String localName, String name)
									throws SAXException {
								super.endElement(uri, localName, name);

								if ("resource".equals(name)) {
									if (this.path != null
											&& this.path.length() != 0) {
										String resourcePath = this.path
												.toString().trim();
										if (isXCSSPath(resourcePath)) {
											debug("XCSS file detected: "
													+ resourcePath);
											try {
												parseXCSSResource(resourcePath,
														bean);
											} catch (IOException e) {
												throw new SAXException(e
														.getLocalizedMessage(),
														e);
											} catch (ParserConfigurationException e) {
												throw new SAXException(e
														.getLocalizedMessage(),
														e);
											}
										}
									}
									this.path = null;
								}
							}
						});
			}

			Set<String> pathResourcesSet = new LinkedHashSet<String>();
			for (ResourcesConfigGeneratorBeanEntry entry : bean.getDependencies()) {
				if (entry.isPathResource()) {
					pathResourcesSet.add(entry.getPath());
				}
			}
			
			for (Iterator<String> iterator = pathResourcesSet.iterator(); iterator
					.hasNext();) {
				String resourcePath = iterator.next();

				if (isXCSSPath(resourcePath)) {
					debug("XCSS file detected: " + resourcePath);
					parseXCSSResource(resourcePath, bean);
				}
			}

			Map<String, String> classResources = new HashMap<String, String>();
			Map<String, String> pathResources = new HashMap<String, String>();
			
			for (ResourcesConfigGeneratorBeanEntry entry : bean.getDependencies()) {
				if (entry.isPathResource()) {
					pathResources.put(entry.getName(), entry.getPath());
				} else {
					classResources.put(entry.getName(), entry.getPath());
				}
			}
			
			context.put("classResources", classResources);
			context.put("pathResources", pathResources);
			context.put("resourcesConfig", this);

			File configFile = getResourcesConfig();
			File javaDir = configFile.getParentFile();
			if (!javaDir.exists()) {
				javaDir.mkdirs();
			}
			if (configFile.exists()) {
				configFile.delete();
			}
			Writer out = new BufferedWriter(new FileWriter(configFile));
			template.merge(context, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new GeneratorException(
					"Error create new resources-config.xml ", e);
		}
	}

	protected String getRootTag() {
		return "resource-config";
	}

	public File getResourcesConfig() {
		return resourcesConfig;
	}

	public void setResourcesConfig(File resourcesConfig) {
		this.resourcesConfig = resourcesConfig;
	}

	protected String getDefaultTemplateName() {
		return "resources-config.vm";
	}

	public void setTemplates(File templatesDirectory) {
		this.templatesDirectory = templatesDirectory;
	}

	public File getTemplates() {
		return this.templatesDirectory;
	}
	
	public void setResourcesConfigGeneratorBean(
			ResourcesConfigGeneratorBean resourcesConfigGeneratorBean) {
		this.resourcesConfigGeneratorBean = resourcesConfigGeneratorBean;
	}
	
	public ResourcesConfigGeneratorBean getResourcesConfigGeneratorBean() {
		return resourcesConfigGeneratorBean;
	}
}