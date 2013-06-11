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

package org.ajax4jsf.templatecompiler.builder;

/**
 * String utilites.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.1 $ $Date: 2006/12/20 18:56:21 $
 * 
 */
public class StringUtils {
	private static final int HEX_DIFF = 'A' - '9' - 1;

	static public String getEscapedString(final String text) {
		StringBuffer buf = new StringBuffer();

		int i = 0;
		while (i < text.length()) {
			char c = text.charAt(i);
			if (isalnum(c)) {
				buf.append(c);
			} else {
				switch (c) {
				case '"':
					buf.append("\\\"");
					break;
				case '\n':
					buf.append("\\n");
					break;
				default:
					buf.append(c);
					break;
				}
			}
			i++;
		}
		return buf.toString();
	}

	/*
	 * static public String getEscapedString( final String text ) {
	 * 
	 * StringBuffer buf = new StringBuffer();
	 * 
	 * int i = 0; while ( i < text.length() ) { char c = text.charAt(i); if (
	 * isalnum( c ) ) buf.append( c ); else { switch (c) { case '@': case '*':
	 * case '-': case '_': //case '+': case '.': case '/': buf.append( c );
	 * break; default: buf.append( hex(c) ); break; } } i++; } return
	 * buf.toString(); }
	 */
	/**
	 * Convert a <code>char</code> to a "<code>%hh</code>" string.
	 * 
	 * @param c
	 *            char to convert
	 * @return <code>%hh</code> representation of char.
	 */
	public static String hex(char c) {
		int lsi = (c & 0x0F) + '0';
		char lsd = (char) (lsi + ((lsi > '9') ? HEX_DIFF : 0));
		int msi = (c >> 4) + '0';
		char msd = (char) (msi + ((msi > '9') ? HEX_DIFF : 0));

		return "%" + msd + lsd;
	}

	/**
	 * Returns true if the char isalpha() or isdigit().
	 */
	public static boolean isalnum(char c) {
		return (isalpha(c) || isdigit(c));
	}

	/**
	 * Returns true if the char isupper() or islower().
	 */
	public static boolean isalpha(char c) {
		return (isupper(c) || islower(c));
	}

	/**
	 * Returns true if the char is from 'A' to 'Z' inclusive.
	 */
	public static boolean isupper(char c) {
		return ((c >= 'A') && (c <= 'Z'));
	}

	/**
	 * Returns true if the char is from 'a' to 'z' inclusive.
	 */
	public static boolean islower(char c) {
		return ((c >= 'a') && (c <= 'z'));
	}

	/**
	 * Returns true if the char is from '0' to '9' inclusive.
	 */
	public static boolean isdigit(char c) {
		return ((c >= '0') && (c <= '9'));
	}

}
