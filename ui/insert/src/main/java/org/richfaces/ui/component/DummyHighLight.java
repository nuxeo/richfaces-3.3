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
package org.richfaces.ui.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererUtils.HTML;

/**
 * @author asmirnov
 *
 */
public class DummyHighLight implements Highlight {

	/* (non-Javadoc)
	 * @see org.richfaces.ui.component.Highlight#highlight(java.lang.String, java.io.InputStream, java.io.OutputStream, java.lang.String, boolean)
	 */
	public void highlight(String name, InputStream in, ResponseWriter out,
			String encoding) throws IOException {
		out.startElement("pre",null);
		InputStreamReader reader;
		if (null != encoding) {
			reader = new InputStreamReader(in, encoding);
		} else {
			reader = new InputStreamReader(in);
		}
		char[] temp = new char[1024];
			int bytes;
			while ((bytes = reader.read(temp)) > 0) {
				out.writeText(temp, 0, bytes);
			}
		out.endElement("pre");
	}


}
