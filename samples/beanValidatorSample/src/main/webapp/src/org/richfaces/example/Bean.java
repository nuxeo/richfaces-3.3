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

package org.richfaces.example;

import org.hibernate.validator.CreditCardNumber;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;

/**
 * JSF bean with text property validation.
 */
public class Bean {
	
	/**
	 * Text property
	 */
	private String email;
	
	private String creditCardNumber;

	/**
	 * @return the creditCardNumber
	 */
	@CreditCardNumber
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * @return the text
	 */
	@NotEmpty
	@Email
	public String getEmail() {
		return email;
	}

	/**
	 * @param text the text to set
	 */
	public void setEmail(String text) {
		this.email = text;
	}
	
}