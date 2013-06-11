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

package org.richfaces;
/**
 * @author $Autor$
 *
 */
public class Bean {
    
    private String name = "two";
	
    public void action() {
        System.out.println(">> Bean.action");
    }
    
	public String executeServer(){
		System.out.println("executed server");
		return null;
	}

	public String executeAjax(){
		System.out.println("executed ajax");
		return null;
	}
	
	public String executeItem1(){
		System.out.println("item1 executed ");
		return null;
	}
	
	public String executeItem2(){
		System.out.println("item2 executed ");
		return null;
	}

	public String executeItem3(){
		System.out.println("item3 executed ");
		return null;
		
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
}