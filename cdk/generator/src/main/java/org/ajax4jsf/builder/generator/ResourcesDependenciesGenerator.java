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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */
public class ResourcesDependenciesGenerator extends InnerGenerator {

	private Map<String, ResourcesConfigGeneratorBean> componentDependencies;
	private String uri;
	private File dependencyFile;

	public ResourcesDependenciesGenerator(JSFGeneratorConfiguration config,
			Logger log) {
		super(config, log);
	}

	@Override
	public void createFiles(BuilderConfig config) throws GeneratorException {
		try {
			VelocityContext context = new VelocityContext();
			Template template = getTemplate();

			Map<String, Map<String, Collection<String>>> componentsMap = new HashMap<String, Map<String, Collection<String>>>();
			for (Entry<String, ResourcesConfigGeneratorBean> entry : componentDependencies.entrySet()) {
				Map<String, Collection<String>> resourcesMap = new HashMap<String, Collection<String>>();
				componentsMap.put(entry.getKey(), resourcesMap);

				Collection<String> scripts = new LinkedHashSet<String>();
				Collection<String> styles = new LinkedHashSet<String>();

				ResourcesConfigGeneratorBean resourcesConfigBean = entry.getValue();
				List<ResourcesConfigGeneratorBeanEntry> dependencies = resourcesConfigBean.getDependencies();
				for (ResourcesConfigGeneratorBeanEntry beanEntry : dependencies) {
					if (beanEntry.isDerived()) {
						continue;
					}
					
					ResourceType type = beanEntry.getType();
					if (type != null) {
						switch (type) {
						case SCRIPT:
							scripts.add(beanEntry.getPath());
							break;

						case STYLE:
							styles.add(beanEntry.getPath());
							break;
						}
					}
				}

				resourcesMap.put("scripts", scripts);
				resourcesMap.put("styles", styles);
			}

			context.put("uri", uri);
			context.put("components", componentsMap);

			File configFile = getDependencyFile();
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
			throw new GeneratorException(e.getMessage(), e);
		}
	}

	@Override
	protected String getDefaultTemplateName() {
		return "resources-dependencies.vm";
	}

	public Map<String, ResourcesConfigGeneratorBean> getComponentDependencies() {
		return componentDependencies;
	}

	public void setComponentDependencies(
			Map<String, ResourcesConfigGeneratorBean> componentDependencies) {
		this.componentDependencies = componentDependencies;
	}

	public void setDependencyFile(File dependencyFile) {
		this.dependencyFile = dependencyFile;
	}

	public File getDependencyFile() {
		return dependencyFile;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
