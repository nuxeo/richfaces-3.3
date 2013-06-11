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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 17.04.2007
 * 
 */
public class ResourcesConfigGeneratorBean {
	
	private List<ResourcesConfigGeneratorBeanEntry> dependencies = new ArrayList<ResourcesConfigGeneratorBeanEntry>();
	
	private String resolveResourcePath(String name, String packageName) {
		if (name.contains("/")) {
			String resolvedName;

			if (!name.startsWith("/")) {
				// need to resolve
				StringBuffer normalizedName = new StringBuffer();
				normalizedName.append(packageName.replace('.', '/'));

				if (!packageName.endsWith("/")) {
					normalizedName.append('/');
				}

				normalizedName.append(name);

				resolvedName = normalizedName.toString();
			} else {
				if (name.length() > 0) {
					resolvedName = name.substring(1);
				} else {
					resolvedName = null;
				}
			}

			return resolvedName;
		}

		return null;
	}

	public void addResource(String name, String path, ResourceType type, boolean isDerived) {
		boolean isPathResource = true;
		String resolvedPath = resolveResourcePath(name, path);

		// remove leading / from resource name.
		if (name.startsWith("/")) {
			name = name.substring(1);
		}

		if (resolvedPath == null) {
			// couldn't resolve, treat as class name
			resolvedPath = name;
			isPathResource = false;
		}

		this.dependencies.add(
			new ResourcesConfigGeneratorBeanEntry(
					name, resolvedPath, isPathResource, type, isDerived));
	}
	
	public List<ResourcesConfigGeneratorBeanEntry> getDependencies() {
		return dependencies;
	}

	public void merge(ResourcesConfigGeneratorBean bean) {
		this.dependencies.addAll(bean.dependencies);
	}
}