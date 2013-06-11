/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
* 
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License version 2.1 as published by the Free Software Foundation.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

*/

package org.richfaces.renderkit;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

/**
 * @author mpopiolek
 * 
 */
public class ResponseWriterMock extends ResponseWriter {

    public ArrayList<String> attr;

    public ResponseWriterMock() {
        attr = new ArrayList<String>();
    }

    @Override
    public void startElement(String name, UIComponent component)
            throws IOException {
    }

    @Override
    public void writeAttribute(String name, Object value, String property)
            throws IOException {
        attr.add(name + " " + value);
    }

    @Override
    public void endElement(String name) throws IOException {
    }

    @Override
    public ResponseWriter cloneWithWriter(Writer writer) {
        return null;
    }

    @Override
    public void endDocument() throws IOException {
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public void startDocument() throws IOException {
    }

    @Override
    public void writeComment(Object comment) throws IOException {
    }

    @Override
    public void writeText(Object text, String property) throws IOException {
    }

    @Override
    public void writeText(char[] text, int off, int len) throws IOException {
    }

    @Override
    public void writeURIAttribute(String name, Object value, String property)
            throws IOException {
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        attr.add(String.valueOf(cbuf));
    }

}
