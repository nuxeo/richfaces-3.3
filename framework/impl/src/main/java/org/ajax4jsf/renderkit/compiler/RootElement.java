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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;

import org.ajax4jsf.Messages;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:45 $
 *
 */
public class RootElement extends ElementBase {
	private static final Log log = LogFactory.getLog(RootElement.class);

	private String templateName="unset";
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext, java.lang.String)
	 */
	public void encode(TemplateContext context, String breakPoint) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.ENCODE_COMPILED_TEMPLATE_INFO, getTemplateName(), breakPoint));
		}
		try {
			super.encode(context, breakPoint);
		} catch (BreakException e) {
			// encoding terminated by break
			if (log.isDebugEnabled()) {
				log.debug(Messages.getMessage(Messages.ENCODING_TEMPLATE_TERMINATED_INFO, getTemplateName(), e.getName()));
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug(Messages.getMessage(Messages.ENCODE_COMPILED_TEMPLATE_INFO2, getTemplateName()));
		}
		try {
			super.encode(context);
		} catch (BreakException e) {
			// encoding terminated by break
			if (log.isDebugEnabled()) {
				log.debug(Messages.getMessage(Messages.ENCODING_TEMPLATE_TERMINATED_INFO, getTemplateName(), e.getName()));
			}
		}
	}

	public String getTag() {
		// TODO Auto-generated method stub
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.ROOT_TAG;
	}

	/**
	 * @return Returns the templateName.
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName The templateName to set.
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
