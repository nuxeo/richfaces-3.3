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
package org.richfaces.validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.validation.constraints.NotNull;

import org.ajax4jsf.el.ELContextWrapper;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
import org.richfaces.validator.ObjectValidator.ValidationResolver;

public class BeanValidatorTest extends AbstractAjax4JsfTestCase {

	public BeanValidatorTest(String name) {
		super(name);
	}

	
	public void setUp() throws Exception {
    	super.setUp();
    }

    public void tearDown() throws Exception {
    	super.tearDown();
    }
	
    public void testValidate() throws Exception {
    	BeanValidator validator = new BeanValidator();
    	String[] validate = validator.validate(new Bean(), "property", null, Locale.ENGLISH, null);
    	assertNotNull(validate);
    	assertEquals(1, validate.length);
	}
    
    public static class Bean {
    	String property;

		/**
		 * @return the property
		 */
		public String getProperty() {
			return property;
		}

		/**
		 * @param property the property to set
		 */
		@NotNull
		public void setProperty(String property) {
			this.property = property;
		}
    }
}
