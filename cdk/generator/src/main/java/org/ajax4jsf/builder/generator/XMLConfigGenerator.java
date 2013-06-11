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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.ajax4jsf.builder.config.ParsingException;
import org.ajax4jsf.builder.xml.XMLBody;

/**
 * @author shura
 * 
 */
public abstract class XMLConfigGenerator extends InnerGenerator {

	private File _include = null;

	/**
	 * @param config
	 * @param log
	 */
	public XMLConfigGenerator(JSFGeneratorConfiguration config, Logger log) {
		super(config, log);
	}

	/**
	 * @return Returns the include.
	 */
	public File getInclude() {
		return _include;
	}

	/**
	 * @param include
	 *            The include to set.
	 */
	public void setInclude(File include) {
		_include = include;
	}

	public String getIncludeContent() {
		String content = "";
		File include = getInclude();
		if (null != include) {
			if (include.isDirectory()) {
				File[] files = include.listFiles();
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.isFile()) {
						content = content + getXMLBody(file);

					}
				}
			} else if (include.exists()) {
				content = getXMLBody(include);
			}
		}
		return content;
	}

	/**
	 * @param file
	 * @return
	 */
	private String getXMLBody(File file) {
		XMLBody body = new XMLBody();
		try {
			body.loadXML(new FileInputStream(file));
			if (body.isRootName(getRootTag())) {
				return body.getContent();
			}
		} catch (FileNotFoundException e) {
			getLog().warn("Not found include file ", e);
		} catch (ParsingException e) {
			getLog().warn("Error parsing include file ", e);
		}
		return "";
	}

	/**
	 * @return
	 */
	protected abstract String getRootTag();

}
