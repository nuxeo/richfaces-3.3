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

package org.ajax4jsf.templatecompiler.el;

import java.util.Formatter;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.StringUtils;

/**
 * EL-parser.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/21 17:17:12 $
 * 
 */
public class ELParser {
	private static final String SIMPLE_EXPRESSION = "\"%s\"";

	private static ELParser elParser;

	private IELCompiler elCompiler;

	private ELParser() {
		this.elCompiler = new ELCompiler();
	}

	private static ELParser getInstance() {
		if (elParser == null) {
			elParser = new ELParser();
		}
		return elParser;
	}

	public static String compileEL(String expression,
			CompilationContext componentBean) {
		return getInstance().getCompiledEL(expression, componentBean);
	}

	/**
	 * 
	 * @param expression
	 * @return
	 */
	public String getCompiledEL(String expression, CompilationContext componentBean) {
		String returnValue;
		if (isVBExpression(expression)) {
			returnValue = this.elCompiler.compileEL(expression, componentBean);
		} else {
			Object[] parameters = new Object[1];
			parameters[0] = StringUtils.getEscapedString(expression);
			returnValue = new Formatter().format(SIMPLE_EXPRESSION, parameters)
					.toString();
		}

		return returnValue;
	}

	/**
	 * Determine whether String is a value binding expression or not.
	 */
	public static boolean isVBExpression(String expression) {
		if (null == expression) {
			return false;
		}
		int start = 0;
		// check to see if attribute has an expression
		if (((start = expression.indexOf("#{")) != -1)
				&& (start < expression.indexOf('}'))) {
			return true;
		}
		return false;
	}

}
