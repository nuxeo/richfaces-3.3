/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Created 26.10.2007
 * @author Nick Belaevski - mailto:nbelaevski@exadel.com
 * @since 3.2
 */

public class TemplateEncoderRendererBaseTest extends AbstractAjax4JsfTestCase {

	public TemplateEncoderRendererBaseTest(String name) {
		super(name);
	}

	public void testEncodeNonRendered() throws Exception {
		setupResponseWriter();
		
		TemplateEncoderRendererBase rendererBase = new TemplateEncoderRendererBase() {

			@Override
			protected Class<? extends UIComponent> getComponentClass() {
				return UIOutput.class;
			}
		};
		
		UIOutput output = new UIOutput();

		UIOutput c = new UIOutput();
		c.setRendered(false);
		c.setValue("");
		output.getChildren().add(c);
		
		//that should not fail
		rendererBase.writeScriptBody(facesContext, c, true);

		processResponseWriter();
	}
}
