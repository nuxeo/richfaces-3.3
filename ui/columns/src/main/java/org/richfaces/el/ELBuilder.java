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

package org.richfaces.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class provides EL parsing for filter and sort attributes of columns component.
 * @author Andrey Markavtsov
 *
 */
public class ELBuilder {
	
	/** Log */
	Log log = LogFactory.getLog(ELBuilder.class); 
	
	/** Original value expression string */
	String [] orig;

	/** Result buffer */
	StringBuffer res = new StringBuffer();
	
	int l;

	/** Columns var string */
	String var;

	/** Columns index string */
	String index;

	/** Replacement to change var string */
	String varReplacement;

	/** Replacement to change index string */
	String indexReplacement;

	int varL;

	int indexL;

	boolean isLiteral;
	
	String postFix;
	
	/**
	 * Constructor
	 * @param orig  - original value expression string
	 * @param var   - columns var string
	 * @param index - columns index string
	 * @param varR  - replacement for var
	 * @param indexR - replacement for index
	 */
	public ELBuilder(String orig, String var, String index, String varR,
			String indexR) {
		this.orig = orig.split("#");
		this.var = var;
		this.index = index;
		this.varReplacement = varR;
		this.indexReplacement = indexR;
		varL = var.length();
		indexL = index.length();
	
	}

	/**
	 * Parsing method
	 * @return String parsing result 
	 */
	public String parse() {
		try {
			for (String s : orig) {
				if (s != null && s.trim().length() > 0) {
					String trim = trimEL(s);
					if (!isLiteral) {
						l = trim.length();
						res.append("#{");
							internalParse(trim);
						res.append("}");
						res.append(postFix);
					}else {
						res.append(s);
					}
				}
			}
		}catch (Exception e) {
			log.error("Error occured during ValueExpression parsing. Keep old expression. " + e);
			res.append(orig);
		}
		return res.toString();
	}
	
	/**
	 * Creates new expression using replacements provided 
	 * @param expr          - original expression
	 * @param expectedType  - expected type
	 * @param factory       - expression factory
	 * @param elContext     - El context
	 * @param var           - var string
	 * @param index         - index string
	 * @param varR          - replacement for var
	 * @param indexR        - replacement for index
	 * @return 
	 */
	public static ValueExpression createValueExpression (String expr, Class<?> expectedType,ExpressionFactory factory, ELContext elContext, String var, String index, String varR,
			String indexR) {
		ELBuilder builder = new ELBuilder(expr, var, index, varR, indexR);
		String newExpr = builder.parse();
		return factory.createValueExpression(elContext, newExpr, expectedType);
	}

	/**
	 * Evaluates expression body
	 * @param orig - original expression
	 * @return
	 */
	private String trimEL(String orig) {
		int end = orig.lastIndexOf("}");
		if (orig.startsWith("{") && end != -1) {
			isLiteral = false;
			postFix = (end + 1 < orig.length()) ? orig.substring(end + 1) : "";
			return orig.substring(1, end);
		} 			
		isLiteral = true;
		return orig;
	}
	
	/**
	 * Returns var replacement
	 * @param expr
	 * @return
	 */
	public static String getVarReplacement(String expr) {
		int start = 0;
		int end = 0;
		int l = expr.length();
		boolean f = false;
		boolean f2 = true;
		for (int i = 0; i < l; i++) {
			char c = expr.charAt(i);
			if (c == '#') {
				f = true;
			}else if (c == '}') {
				end = i;
			}else if (c == '{' && f && f2) {
				start = (i + 1 < l) ? i + 1 : i;
				f2 = false;
			}
		}
		return (end > start) ? expr.substring(start, end) : null;
	}

	private int internalParse(String s) {

		final char firstVar = var.charAt(0);
		final char firstIndex = index.charAt(0);

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == firstIndex) {
				i = parseIndex(s, i, c);
			} else if (c == firstVar) {
				i = parseVar(s, i, c);
			} else if (isQuote(c)) {
				i = parseString(s, i, c);
			} else {
				res.append(c);
			}
		}
		return 0;
	}

	private int parseIndex(String s, int i, char c) {
		if (s.indexOf(index, i) == i) {
			Character before = (i != 0) ? s.charAt(i - 1) : null;
			Character after = (i + indexL < l) ? s.charAt(i + indexL) : null;
			if (isIndex(c, before, after)) {
				res.append(indexReplacement);
				return i + indexL - 1;
			} else {
				res.append(c);
				return i++;
			}
		}
		res.append(c);
		return i;
	}

	private int parseVar(String s, int i, char c) {
		if (s.indexOf(var, i) == i) {
			Character before = (i != 0) ? s.charAt(i - 1) : null;
			Character after = (i + varL < l) ? s.charAt(i + varL) : null;
			if (isVar(c, before, after)) {
				res.append(varReplacement);
				return i + varL - 1;
			} else {
				res.append(c);
				return i++;
			}
		}
		res.append(c);
		return i;
	}

	private int parseString(String s, int i, char c) {
		res.append(s.charAt(i));
		int j = i++;
		while (j < l) {
			if (isQuote(s.charAt(j))) {
				break;
			} else {
				res.append(s.charAt(j));
			}
			j++;
		}
		return j;
	}

	private boolean isIndex(Character c, Character before, Character after) {
		boolean ret = before == null
				|| (Character.isWhitespace(before) || isMath(before)
						|| before == '[' || before == '(' || before == '{');
		if (ret) {
			ret = after == null
					|| (Character.isWhitespace(after) || isMath(after)
							|| after == ']' || before == ')' || before == '}');
		}
		return ret;
	}

	private boolean isVar(Character c, Character before, Character after) {
		boolean ret = before == null
				|| (Character.isWhitespace(before) || before == '(' || before == '{');
		if (ret) {
			ret = after == null
					|| (Character.isWhitespace(after) || after == ')'
							|| after == '(' || after == ']' || after == '[' || after == '.');
		}
		return ret;
	}

	private boolean isMath(Character c) {
		return c == '+' || c == '*' || c == '-' || c == '/' || c == '%'
				|| c == '(' || c == ')';

	}

	private boolean isQuote(Character c) {
		return c == '\'' || c == '\"';
	}

}
