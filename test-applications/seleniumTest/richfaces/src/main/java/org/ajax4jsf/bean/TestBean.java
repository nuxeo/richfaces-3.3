/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
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
package org.ajax4jsf.bean;



import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author krasi
 * 
 */
public class TestBean  {
	String				id		= "";
	static final Logger	LOGGER	= Logger.getLogger(TestBean.class.getName());

	public TestBean() {
	}

	public String getId() {
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message 1.",
						""));

		System.out.println("GET 1 ------" + id);
		if (!id.equals("")) {
			System.out.println("GET 2 ------" + id);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message 2.",
							"11"));

			try {
				System.out.println("GET 3 ------" + id);
				throw new ObjectNotFoundException();

			} catch (ObjectNotFoundException e) {
				System.out.println("GET  4 ------" + id);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Message 3.",
								"11"));
			}

		}

		return id;
	}

	public void setId(String id) {
		System.out.println("SET 1-" + id);
		this.id = id;
	}

	public String searchByID() {
		System.out.println("searchByID");
		return null;

	}

}
