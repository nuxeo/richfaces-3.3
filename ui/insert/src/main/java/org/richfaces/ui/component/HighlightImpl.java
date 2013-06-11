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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.FacesException;
import javax.faces.context.ResponseWriter;

import com.uwyn.jhighlight.renderer.Renderer;
import com.uwyn.jhighlight.renderer.XhtmlRendererFactory;

/**
 * @author asmirnov
 *
 */
public class HighlightImpl implements Highlight {
	
	
	
	private Renderer _renderer;

	public HighlightImpl(String type) {
		_renderer = XhtmlRendererFactory.getRenderer(type);
		if(null == _renderer){
			throw new FacesException("Unknown type ["+type+"] to highlight source");
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.ui.component.Highlight#highlight(java.lang.String, java.io.InputStream, java.io.OutputStream, java.lang.String, boolean)
	 */
	public void highlight(String name, InputStream in, ResponseWriter out,
			String encoding) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		_renderer.highlight(name, in, outStream, encoding, true);
		if(null == encoding){
			out.write(outStream.toString());
		} else {
			out.write(outStream.toString(encoding));			
		}
	}

	/* (non-Javadoc)
	 * @see org.richfaces.ui.component.Highlight#highlight(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public String highlight(String name, String in, String encoding) throws IOException {
		return _renderer.highlight(name, in, encoding, true);
	}
	
	

}
