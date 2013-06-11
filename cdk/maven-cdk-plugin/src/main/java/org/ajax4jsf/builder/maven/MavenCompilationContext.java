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

package org.ajax4jsf.builder.maven;

import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.builder.AbstractCompilationContext;
import org.ajax4jsf.templatecompiler.elements.ElementsFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

/**
 * @author shura
 *
 */
public class MavenCompilationContext extends AbstractCompilationContext {

	private MavenLogger logger;
	private VelocityEngine engine;
	
	/**
	 * @param logger
	 * @throws CompilationException 
	 */
	public MavenCompilationContext(MavenLogger logger,ClassLoader loader,VelocityEngine engine) throws CompilationException {
		super(loader);
		this.logger = logger;
		this.engine = engine;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#debug(java.lang.String)
	 */
	public void debug(String content) {
		logger.debug(content);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#debug(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String content, Throwable error) {
		logger.debug(content, error);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#error(java.lang.String)
	 */
	public void error(String content) {
		logger.error(content);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String content, Throwable error) {
		logger.error(content, error);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#info(java.lang.String)
	 */
	public void info(String content) {
		logger.info(content);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String content, Throwable error) {
		logger.info(content, error);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#warn(java.lang.String)
	 */
	public void warn(String content) {
		logger.warn(content);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String content, Throwable error) {
		logger.warn(content, error);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#getElementsFactory()
	 */
	public ElementsFactory getElementsFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#getTemplate(java.lang.String)
	 */
	public Template getTemplate(String name) throws CompilationException {
		try {
		return engine.getTemplate(name);
	} catch (ResourceNotFoundException e) {
		throw new CompilationException(e.getLocalizedMessage());
	} catch (ParseErrorException e) {
		throw new CompilationException(e.getLocalizedMessage());
	} catch (Exception e) {
		throw new CompilationException(e.getLocalizedMessage());
	}
	}

}
