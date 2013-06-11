/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.event.sort;

/**
 * @author Maksim Kaszynski
 *
 */
public abstract class AbstractSortListener implements SortListener {
	
	/**
	 * Encapsulate sorting toggle here
	 * @param current
	 * @param suggested
	 * @return
	 */
	protected Boolean nextSortOrder(Boolean current, Boolean suggested) {
		
		if (suggested != null) {
			return suggested;
		} else {
			return (current == null) ? Boolean.TRUE : (current.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
		}
		
	}

}
