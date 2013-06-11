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
package org.richfaces.component;

public interface ActionPrefixHolder {
	public static String PREFIX_NORMAL = "normal:";
	public static String PREFIX_DIALOG = "dialog:";
	public static String PREFIX_CLOSE = "close:";
	public static String PREFIX_NOCLOSE = "noclose:";
	public static String PREFIX_CLOSEALL = "closeall:";
	
	public void setPrefix(String s);
	public String getPrefix();
	public void setNullPath();
	public boolean isNullPath();

}
