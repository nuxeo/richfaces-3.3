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

package org.ajax4jsf.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;

/**
 * HACK - for case if servlet response already use Writer, create wrapper to stream -
 * since most of serializers use outputStream. In future, must be replaced by Dual serialization
 * capabilites.
 * @author shura
 *
 */
final class ServletStreamWriter extends ServletOutputStream {
	
	private PrintWriter _writer;
	
	private byte[] buff = new byte[1024];
	
	private int point = 0;

	private String _charset;

	/**
	 * @param writer
	 * @param charset
	 */
	public ServletStreamWriter(PrintWriter writer, String charset) {
		super();
		// TODO Auto-generated constructor stub
		_writer = writer;
		_charset = charset;
	}

	public ServletStreamWriter(PrintWriter writer) {
		this._writer = writer;
	}

	public void write(int b) throws IOException {
		buff[point++]=(byte) b;
		if(point==buff.length){
			point = 0;
			String string = null==_charset?new String(buff):new String(buff,_charset);
			_writer.write(string);
		}
	}

	/**
	 * @return Returns the charset.
	 */
	public String getCharset() {
		return _charset;
	}

	/**
	 * @param charset The charset to set.
	 */
	public void setCharset(String charset) {
		_charset = charset;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	public void close() throws IOException {
		this.flush();
		super.close();
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	public void flush() throws IOException {
		if(point>0){
			String string = null==_charset?new String(buff,0,point):new String(buff,0,point,_charset);
			_writer.write(string);
			point = 0;			
		}
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	public void write(byte[] b, int off, int len) throws IOException {
		this.flush();
		String string = null==_charset?new String(b,off,len):new String(b,off,len,_charset);
		_writer.write(string);
	}
}