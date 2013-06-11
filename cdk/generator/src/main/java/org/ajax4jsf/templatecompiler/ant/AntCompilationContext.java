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

package org.ajax4jsf.templatecompiler.ant;

import org.ajax4jsf.templatecompiler.builder.AbstractCompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.apache.tools.ant.Project;
import org.apache.velocity.Template;

/**
 * @author shura
 *
 */
public class AntCompilationContext extends AbstractCompilationContext {


	private Project project;
	
	TemplateCompilerTask task;
	


	/**
	 * @param loader
	 * @throws CompilationException
	 */
	public AntCompilationContext(TemplateCompilerTask task, ClassLoader loader) throws CompilationException {
		super(loader);
		this.task = task;
		this.project = task.getProject();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#debug(java.lang.String)
	 */
	public void debug(String content) {
		project.log(content, Project.MSG_DEBUG);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#debug(java.lang.String, java.lang.Throwable)
	 */
	public void debug(String content, Throwable error) {
		project.log(content, Project.MSG_DEBUG);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#error(java.lang.String)
	 */
	public void error(String content) {
		project.log(content, Project.MSG_ERR);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String content, Throwable error) {
		project.log(content, Project.MSG_ERR);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#info(java.lang.String)
	 */
	public void info(String content) {
		project.log(content, Project.MSG_INFO);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#info(java.lang.String, java.lang.Throwable)
	 */
	public void info(String content, Throwable error) {
		project.log(content, Project.MSG_INFO);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#warn(java.lang.String)
	 */
	public void warn(String content) {
		project.log(content, Project.MSG_WARN);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#warn(java.lang.String, java.lang.Throwable)
	 */
	public void warn(String content, Throwable error) {
		project.log(content, Project.MSG_INFO);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#getTemplate(java.lang.String)
	 */
	public Template getTemplate(String name) throws CompilationException {
		return task.getTemplate(name);
	}
}
