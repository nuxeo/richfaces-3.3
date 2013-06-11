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

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.PropertyNotFoundException;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.el.parser.ArithmeticNode;
import com.sun.el.parser.AstAnd;
import com.sun.el.parser.AstBracketSuffix;
import com.sun.el.parser.AstChoice;
import com.sun.el.parser.AstDeferredExpression;
import com.sun.el.parser.AstDiv;
import com.sun.el.parser.AstDotSuffix;
import com.sun.el.parser.AstEmpty;
import com.sun.el.parser.AstEqual;
import com.sun.el.parser.AstFalse;
import com.sun.el.parser.AstFunction;
import com.sun.el.parser.AstGreaterThan;
import com.sun.el.parser.AstGreaterThanEqual;
import com.sun.el.parser.AstIdentifier;
import com.sun.el.parser.AstInteger;
import com.sun.el.parser.AstLessThan;
import com.sun.el.parser.AstLessThanEqual;
import com.sun.el.parser.AstLiteralExpression;
import com.sun.el.parser.AstMinus;
import com.sun.el.parser.AstMod;
import com.sun.el.parser.AstMult;
import com.sun.el.parser.AstNot;
import com.sun.el.parser.AstNotEqual;
import com.sun.el.parser.AstOr;
import com.sun.el.parser.AstPlus;
import com.sun.el.parser.AstString;
import com.sun.el.parser.AstTrue;
import com.sun.el.parser.AstValue;
import com.sun.el.parser.BooleanNode;
import com.sun.el.parser.ELParser;
import com.sun.el.parser.Node;

/**
 * Compiler EL-expressions.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/21 17:17:12 $
 * 
 */
public class ELCompiler implements IELCompiler {

	private static final Log log = LogFactory.getLog(ELCompiler.class);
	
	Map<String, String> functionsMap = new HashMap<String,String>();

	static {
	}

	/**
	 * 
	 */
	public ELCompiler() {
		super();
		resetVariables();
	}

	/**
	 * 
	 */
	public void resetVariables() {
		// maps.put("context", "javax.faces.context.FacesContext" );
		// maps.put("component", "javax.faces.component.UIComponent" );
		// maps.put("a4jSkin", "org.ajax4jsf.framework.skin.Skin" );

		this.functionsMap.put("a4jSkin:getParameter",
				"org.ajax4jsf.framework.skin.getParameter");
	}

	public String compileEL(String expression, CompilationContext componentBean) {
		Node node = ELParser.parse(expression);
		StringBuffer sbMain = new StringBuffer();
		processNode(node, sbMain, componentBean);
		return sbMain.toString();
	}

	/**
	 * Processing node
	 * 
	 * @param node
	 * @param sbMain
	 * @param componentBean
	 */
	private void processNode(Node node, StringBuffer sbMain,
			CompilationContext componentBean) {
		int numChildren = node.jjtGetNumChildren();

		boolean bNeedConversion = false;
		for (int i = 0; i < numChildren; i++) {
			Node childNode = node.jjtGetChild(i);
			if (childNode instanceof AstLiteralExpression) {
				bNeedConversion = true;
				break;
			}
		}

		for (int i = 0; i < numChildren; i++) {
			Node childNode = node.jjtGetChild(i);

			if (childNode instanceof AstLiteralExpression) {
				if (childNode.getImage() != null) {
					if (i > 0) {
						sbMain.append(" + ");
					}
					sbMain.append("\"");
					sbMain.append(StringUtils.getEscapedString(childNode
							.getImage()));
					sbMain.append("\"");

					if (i < (numChildren - 1)) {
						sbMain.append(" + ");
					}
				}
			} else {
				if (bNeedConversion) {
					sbMain.append("convertToString(");
				}
				boolean processing = processingNode(childNode, sbMain,
						componentBean);
				if (!processing) {
					processNode(childNode, sbMain, componentBean);
				}
				if (bNeedConversion) {
					sbMain.append(")");
				}
			}

		}
	}

	/**
	 * 
	 * @param node
	 * @param sbMain
	 * @param componentBean
	 * @param cMain
	 * @return
	 */
	public boolean processingNode(Node node, StringBuffer sbMain,
			CompilationContext componentBean) {
		boolean returnValue = false;
		if (node instanceof ArithmeticNode) {
			returnValue = processingArithmeticNode((ArithmeticNode) node,
					sbMain, componentBean);
		} else if (node instanceof AstIdentifier) {
			returnValue = processingIdentifier((AstIdentifier) node, sbMain,
					componentBean);
		} else if (node instanceof AstValue) {
			returnValue = processingValue((AstValue) node, sbMain,
					componentBean);
		} else if (node instanceof AstInteger) {
			returnValue = processingInteger((AstInteger) node, sbMain);
		} else if (node instanceof AstString) {
			returnValue = processingString((AstString) node, sbMain);
		} else if (node instanceof AstFunction) {
			returnValue = processingFunction((AstFunction) node, sbMain,
					componentBean);
		} else if (node instanceof AstDeferredExpression) {

		} else if (node instanceof BooleanNode) {
			returnValue = processingBooleanNode((BooleanNode) node,
					sbMain, componentBean);
		} else if (node instanceof AstNot) {
			returnValue = processingNot((AstNot) node,
					sbMain, componentBean);
		} else if (node instanceof AstChoice) {
			returnValue = processingChoice((AstChoice) node,
					sbMain, componentBean);
		} else if (node instanceof AstEmpty) {
			returnValue = processingEmpty((AstEmpty) node,
					sbMain, componentBean);
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(node.toString());
			sb.append(" (");
			sb.append(node.getClass().getName());
			sb.append(")");
			log.debug(sb.toString());
		}

		return returnValue;
	}
	
	/**
	 * Processing 'empty' node
	 * 
	 * @param node
	 * @param sb
	 * @param cMain
	 * @return
	 */
	private boolean processingEmpty(AstEmpty node, StringBuffer sbMain,
			CompilationContext componentBean) {
		boolean returnValue = false;
		StringBuffer sb1 = new StringBuffer();
		
		Node node1 = node.jjtGetChild(0);

		if (null != node1) {
			if (!(returnValue = processingNode(node1, sb1,
					componentBean))) {
				log.error("Error processing node1: "
						+ node1.getImage());
			}
		}
		
		if (returnValue) {
			sbMain.append(" isEmpty( ");
			sbMain.append(sb1);
			sbMain.append(" ) ");
		}
		
		return returnValue;
	}

	/**
	 * Processing 'choice' node
	 * 
	 * @param node
	 * @param sb
	 * @param cMain
	 * @return
	 */
	private boolean processingChoice(AstChoice node, StringBuffer sbMain,
			CompilationContext componentBean) {
		boolean returnValue = true;
		
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		StringBuffer sb3 = new StringBuffer();
		
		Node node1 = node.jjtGetChild(0);

		if (node1 != null) {
			if (!(returnValue &= processingNode(node1, sb1,
					componentBean))) {
				log.error("Error processing node1: "
						+ node1.getImage());
			}
		}
		
		Node node2 = node.jjtGetChild(1);

		if (null != node2) {
			if (!(returnValue &= processingNode(node2, sb2,
					componentBean))) {
				log.error("Error processing node2: "
						+ node2.getImage());
			}
		}
		
		Node node3 = node.jjtGetChild(2);

		if (null != node3) {
			if (!(returnValue &= processingNode(node3, sb3,
					componentBean))) {
				log.error("Error processing node3: "
						+ node3.getImage());
			}
		}
		
		if (returnValue) {
			sbMain.append(" ( ");
			sbMain.append(sb1);
			sbMain.append(" ? ");
			sbMain.append(sb2);
			sbMain.append(" : ");
			sbMain.append(sb3);
			sbMain.append(" ) ");
		}
		
		return returnValue;
	}

	/**
	 * Processing node containing 'not' expression
	 * 
	 * @param node
	 * @param sb
	 * @param cMain
	 * @return
	 */
	private boolean processingNot(AstNot node, StringBuffer sbMain,
			CompilationContext componentBean) {
		boolean returnValue = false;
		StringBuffer sb1 = new StringBuffer();
		
		Node node1 = node.jjtGetChild(0);

		if (null != node1) {
			if (!(returnValue = processingNode(node1, sb1,
					componentBean))) {
				log.error("Error processing node1: "
						+ node1.getImage());
			}
		}
		
		if (returnValue) {
			sbMain.append(" ( ! ");
			sbMain.append(sb1);
			sbMain.append(" ) ");
		}
		
		return returnValue;
	}

	/**
	 * Processing boolean node
	 * 
	 * @param node
	 * @param sb
	 * @param cMain
	 * @return
	 */
	private boolean processingBooleanNode(BooleanNode node,
			StringBuffer sb, CompilationContext componentBean) {
		boolean returnValue = true;
		
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		
		if (node instanceof AstFalse) {
			sb.append(" false ");
			return returnValue;
		}
		if (node instanceof AstTrue) {
			sb.append(" true ");
			return returnValue;
		}
		
		Node node1 = node.jjtGetChild(0);

		if (node1 != null) {
			if (!(returnValue &= processingNode(node1, sb1,
					componentBean))) {
				log.error("Error processing node1: "
						+ node1.getImage());
			}
		}
		
		Node node2 = node.jjtGetChild(1);

		if (null != node2) {
			if (!(returnValue &= processingNode(node2, sb2,
					componentBean))) {
				log.error("Error processing node2: "
						+ node2.getImage());
			}
		}
		
		if (returnValue) {
			sb.append(" ( ");
			sb.append(sb1);
			
			if (node instanceof AstAnd) {
				sb.append(" && ");
			} else if (node instanceof AstEqual) {
				sb.append(" == ");
			} else if (node instanceof AstGreaterThan) {
				sb.append(" > ");
			} else if (node instanceof AstGreaterThanEqual) {
				sb.append(" >= ");
			} else if (node instanceof AstLessThan) {
				sb.append(" < ");
			} else if (node instanceof AstLessThanEqual) {
				sb.append(" <= ");
			} else if (node instanceof AstNotEqual) {
				sb.append(" != ");
			} else if (node instanceof AstOr) {
				sb.append(" || ");
			}
			sb.append(sb2);			
			sb.append(" ) ");
		}
		
		return returnValue;
	}


	/**
	 * Processing arithmetic node
	 * 
	 * @param node
	 * @param sb
	 * @param cMain
	 * @return
	 */
	private boolean processingArithmeticNode(ArithmeticNode node,
			StringBuffer sb, CompilationContext componentBean) {
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();

		boolean returnValue = true;

		Node node1 = node.jjtGetChild(0);

		if (node1 != null) {
			if (!(returnValue &= processingNode(node1, sb1,
					componentBean))) {
				log.error("Error processing node1: "
						+ node1.getImage());
			}
		}
		
		Node node2 = node.jjtGetChild(1);

		if (null != node2) {
			if (!(returnValue &= processingNode(node2, sb2,
					componentBean))) {
				log.error("Error processing node2: "
						+ node2.getImage());
			}
		}

		if (returnValue) {
			sb.append(" ( ");
			sb.append(sb1);

			if (node instanceof AstDiv) {
				sb.append(" / ");
			} else if (node instanceof AstMult) {
				sb.append(" * ");
			} else if (node instanceof AstMod) {
				sb.append(" % ");
			} else if (node instanceof AstPlus) {
				sb.append(" + ");
			} else if (node instanceof AstMinus) {
				sb.append(" - ");
			}

			sb.append(sb2);
			sb.append(" ) ");
		}
		return returnValue;
	}

	/**
	 * Processing node contain integer
	 * 
	 * @param node
	 * @param sb
	 * @return
	 */
	private boolean processingInteger(AstInteger node, StringBuffer sb) {
		sb.append(node.getImage());
		return true;
	}

	/**
	 * Processing node contain string
	 * 
	 * @param node
	 * @param sb
	 * @return
	 */
	private boolean processingString(AstString node, StringBuffer sb) {
		sb.append("\"");
		sb.append(node.getString());
		sb.append("\"");
		return true;
	}

	/**
	 * 
	 * @param node
	 * @param sb
	 * @return
	 */
	private boolean processingFunction(AstFunction node, StringBuffer sb,
			CompilationContext componentBean) {

		log.debug("Processing function : " + node.getPrefix());
		log.debug("Processing function : " + node.getLocalName());
		log.debug("Processing function : " + node.getOutputName());

		String prefix = node.getPrefix();
		boolean isThis = prefix.equals("this");
		boolean isUtils = prefix.equals("utils");
		
		if (isThis || isUtils) {
			if (isUtils) {
				sb.append("getUtils().");
			}
			
			sb.append(node.getLocalName());
			sb.append("(");
			int numChildren = node.jjtGetNumChildren();
			for (int i = 0; i < numChildren; i++) {
				Node childNode = node.jjtGetChild(i);
				StringBuffer buf = new StringBuffer();
				processingNode(childNode, buf, componentBean);
				if (i != 0) {
					sb.append(",");
				}
				sb.append(buf);
			}
			sb.append(")");
		} else {
			String functionName = node.getOutputName();
			if (this.functionsMap.containsKey(functionName)) {
				sb.append(this.functionsMap.get(functionName));
				sb.append("(");
				int numChildren = node.jjtGetNumChildren();
				for (int i = 0; i < numChildren; i++) {
					Node childNode = node.jjtGetChild(i);
					StringBuffer buf = new StringBuffer();
					processingNode(childNode, buf, componentBean);
					if (i != 0) {
						sb.append(",");
					}
					sb.append(buf);
				}
				sb.append(")");
			} // if
		} // else
		return true;
	}

	/**
	 * 
	 * @param node
	 * @param sb
	 * @return
	 */
	private boolean processingIdentifier(AstIdentifier node, StringBuffer sb,
			CompilationContext componentBean) {
		String variableName = node.getImage();
		if (componentBean.containsVariable(variableName)) {
			sb.append(variableName);
		} else {
			sb.append("variables.getVariable(\"");
			sb.append(variableName);
			sb.append("\")");
		}

		return true;
	}

	/**
	 * 
	 * @param basketSuffix
	 * @return
	 */
	private String processingBracketSuffix(AstBracketSuffix basketSuffix,
			CompilationContext componentBean) {
		StringBuffer sb = new StringBuffer();
		Node node = basketSuffix.jjtGetChild(0);
		if (node instanceof AstIdentifier) {
			processingIdentifier((AstIdentifier) node, sb, componentBean);
		} else if (node instanceof AstInteger) {
			// sb.append("new Integer(");
			sb.append(node.getImage());
			// sb.append(")");
		} else if (node instanceof AstString) {
			AstString stringNode = (AstString) node;
			sb.append("\"");
			sb.append(stringNode.getString());
			sb.append("\"");
		} else {
			sb.append("\"");
			sb.append(node.getImage());
			sb.append("\"");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private PropertyDescriptor getPropertyDescriptor(Class<?> clazz,
			String propertyName, CompilationContext compilationContext) {
		return compilationContext.getPropertyDescriptor(clazz, propertyName);
	}

	private boolean processingValue(AstValue node, StringBuffer sb,
			CompilationContext componentBean) {
		String lastIndexValue = "null";
		String lastVariableType = null;
		List<String> names = new ArrayList<String>();

		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			StringBuffer sb1 = new StringBuffer();
			Node subChild = node.jjtGetChild(i);

			if (subChild instanceof AstIdentifier) {
				String variableName = subChild.getImage();
				if (componentBean.containsVariable(variableName)) {
					lastVariableType = componentBean.getVariableType(
							variableName).getName();
					names.add(variableName);
				} else {
					processingIdentifier((AstIdentifier) subChild, sb1,
							componentBean);
				}
			} else if (subChild instanceof AstDotSuffix) {
				String propertyName = subChild.getImage();
				log.debug("Object: " + lastVariableType
						+ ", property: " + propertyName);

				if (lastVariableType != null) {
					try {

						Class<?> clazz = componentBean.loadClass(lastVariableType);

						PropertyDescriptor propertyDescriptor = getPropertyDescriptor(
								clazz, propertyName, componentBean);

						if (propertyDescriptor == null) {
							throw new PropertyNotFoundException("property: "
									+ propertyName + " not found in class: "
									+ lastVariableType);
						}

						log.debug("propertyObject: "
								+ propertyDescriptor.getPropertyType()
										.getName());
						StringBuffer tmpbuf = new StringBuffer();
						tmpbuf.append(propertyDescriptor.getReadMethod()
								.getName());
						tmpbuf.append("()");
						names.add(tmpbuf.toString());

						lastVariableType = propertyDescriptor.getPropertyType()
								.getName();
					} catch (ClassNotFoundException e) {
					    log.error(e.getLocalizedMessage(), e);
					}

				} else {

					sb1.append("getProperty(");
					sb1.append(lastIndexValue);
					sb1.append(",");
					sb1.append("\"");
					sb1.append(subChild.getImage());
					sb1.append("\")");
				}
			} else if (subChild instanceof AstBracketSuffix) {
				String bracketSuffix = processingBracketSuffix(
						(AstBracketSuffix) subChild, componentBean);

				if (lastVariableType != null) {
					StringBuffer tmpbuf = new StringBuffer();
					if (lastVariableType.startsWith("[L")) {
						tmpbuf.append("[");
						tmpbuf.append(bracketSuffix);
						tmpbuf.append("]");
						names.add(tmpbuf.toString());
					}

					if ((lastVariableType.compareTo("java.util.List") == 0)
							|| (lastVariableType.compareTo("java.util.Map") == 0)) {
						tmpbuf.append("get(");
						tmpbuf.append(bracketSuffix);
						tmpbuf.append(")");
						names.add(tmpbuf.toString());
					}
				} else {

					sb1.append("getElelmentByIndex(");
					sb1.append(lastIndexValue);
					sb1.append(",");
					sb1.append(bracketSuffix);
					sb1.append(")");
				}

			}

		}

		if (names.size() != 0) {
			StringBuffer tmpbuf = new StringBuffer();
			for (String element : names) {
				if (tmpbuf.length() != 0) {
					tmpbuf.append(".");
				}
				tmpbuf.append(element);
			}
			sb.append(tmpbuf.toString());
		} else {
			sb.append(lastIndexValue);
		}

		return true;
	}

}
