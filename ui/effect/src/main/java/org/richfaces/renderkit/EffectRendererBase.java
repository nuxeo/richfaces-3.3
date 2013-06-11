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
package org.richfaces.renderkit;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.javascript.JSEncoder;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils;
import org.richfaces.component.UIEffect;
import org.richfaces.json.JSONTokener;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 09.08.2007
 *
 */
public class EffectRendererBase extends HeaderResourcesRendererBase {

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class getComponentClass() {
		return UIEffect.class;
	}

	private static final Pattern VARIABLE_PATTERN = Pattern.compile("^\\s*[_,A-Z,a-z]\\w*(?:\\.[_,A-Z,a-z]\\w*)*\\s*$");

	public String convertElementParameter(Object parameter) {
	        if (parameter==null)
	        	return "''";
		String s = parameter.toString();
		if (VARIABLE_PATTERN.matcher(s).matches()) {
			return "typeof "+s+" == \"object\" ? "+s+" : $('"+s+"')";
		} else {
			return "'"+s+"'";
		}
	}
	
	public String convertParameters(FacesContext context, UIEffect effect) throws IOException {
		String params = effect.getParams();
		if (params == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer("{" + params + "}");
		try {
			replace(context, effect, buffer);
			return buffer.toString();
		} catch (Exception e) {
			IOException exception = new IOException(e.getMessage());
			exception.initCause(e);
			
			throw exception;
		}
	}
	
	private static void replace(FacesContext context, UIComponent effect, StringBuffer s) throws Exception {
		JSONTokener x = new JSONTokener(s.toString());
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
        for (;;) {
            int idx;
        	c = x.nextClean();
            switch (c) {
            case 0:
                throw x.syntaxError("A JSONObject text must end with '}'");
            case '}':
                return;
            default:
                x.back();
            	idx = x.getMyIndex();
            	//System.out.println(s.substring(x.getMyIndex()));
                key = x.nextValue().toString();
            }

            /*
             * The key is followed by ':'. We will also tolerate '=' or '=>'.
             */

            c = x.nextClean();
            if (c == '=') {
                if (x.next() != '>') {
                    x.back();
                }
            } else if (c != ':') {
                throw x.syntaxError("Expected a ':' after a key");
            }
            
            if ("id".equals(key)) {
            	Object value = x.nextValue();
				UIComponent component = RendererUtils.getInstance().findComponentFor(effect, value.toString());
				if (component != null) {
					value = component.getClientId(context);
				}

            	
            	s.replace(idx, x.getMyIndex(), "'id': '" + value + "'");
            	
            	return ;
            } else {
            	x.nextValue();
            }

            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */

            switch (x.nextClean()) {
            case ';':
            case ',':
                if (x.nextClean() == '}') {
                    return;
                }
                x.back();
                break;
            case '}':
                return;
            default:
                throw x.syntaxError("Expected a ',' or '}'");
            }
        }
	}	
	
	public String escapeJavaScript(Object s) {
		if (s != null) {
			JSEncoder encoder = new JSEncoder();
			StringBuffer result = new StringBuffer();
			String string = s.toString();
			int length = string.length();
			
			for (int i = 0; i < length; i++) {
				result.append(encoder.encode(string.charAt(i)));
			}

			return result.toString();
		} else {
			return null;
		}
	}
}
