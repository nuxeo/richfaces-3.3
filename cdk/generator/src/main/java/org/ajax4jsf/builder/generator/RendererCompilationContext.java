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

package org.ajax4jsf.builder.generator;

import org.ajax4jsf.templatecompiler.builder.AbstractCompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.apache.velocity.Template;

/**
 * @author shura
 * 
 */
public class RendererCompilationContext extends AbstractCompilationContext {

	private Logger _log;
	private JSFGeneratorConfiguration _config;


	/**
	 * @param loader
	 * @param configuration 
	 * @throws CompilationException
	 */
	public RendererCompilationContext(Logger log, ClassLoader loader, JSFGeneratorConfiguration configuration)
			throws CompilationException {
		super(loader);
		this._log = log;
		this._config = configuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#debug(java.lang.String)
	 */
	public void debug(String content) {
		_log.debug(content);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#debug(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void debug(String content, Throwable error) {
		_log.debug(content, error);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#error(java.lang.String)
	 */
	public void error(String content) {
		_log.error(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#error(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void error(String content, Throwable error) {
		_log.error(content, error);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#info(java.lang.String)
	 */
	public void info(String content) {
		_log.info(content);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#info(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void info(String content, Throwable error) {
		_log.info(content, error);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#warn(java.lang.String)
	 */
	public void warn(String content) {
		_log.warn(content);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#warn(java.lang.String,
	 *      java.lang.Throwable)
	 */
	public void warn(String content, Throwable error) {
		_log.warn(content, error);

	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#getTemplate(java.lang.String)
	 */
	public Template getTemplate(String name) throws CompilationException {
		// TODO Auto-generated method stub
		try {
			return _config.getTemplate(name);
		} catch (GeneratorException e) {
			throw new CompilationException(e);
		}
	}

}
