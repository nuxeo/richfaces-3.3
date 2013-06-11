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

package org.ajax4jsf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author $Autor$
 *
 */
public class Bean {
    	private int counter;
    	
    	private List containers ;
    
    	public Bean() {
	    containers = new ArrayList();
	    boolean availible=false;
	    boolean changed=true;
	    for(int i=0;i<10;i++){
		Container c = new Container();
		c.setContainerNumber(i);
		c.setAvailable(availible);
		c.setChanged(changed);
		availible = ! availible;
		changed = ! changed;
		containers.add(c);
	    }
	}
	/**
	 * @return the counter
	 */
	public int getCounter() {
	    return counter;
	}

	/**
	 * @param counter the counter to set
	 */
	public void setCounter(int counter) {
	    this.counter = counter;
	}

	public String inc() {
	    counter++;
	    return null;
	}
	public String dec() {
	    counter--;
	    return null;
	}
	/**
	 * @return the containers
	 */
	public List getContainers() {
	    return containers;
	}
	/**
	 * @param containers the containers to set
	 */
	public void setContainers(List containers) {
	    this.containers = containers;
	}
}