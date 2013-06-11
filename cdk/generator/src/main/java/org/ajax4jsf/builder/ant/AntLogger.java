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

package org.ajax4jsf.builder.ant;

import org.ajax4jsf.builder.generator.Logger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author shura
 *
 */
public class AntLogger implements Logger {

	private Task _task;

	public AntLogger(Task task) {
		this._task = task;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#debug(java.lang.CharSequence)
	 */
	public void debug(CharSequence content) {
		_task.log(content.toString(),Project.MSG_DEBUG);

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#debug(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void debug(CharSequence content, Throwable error) {
		_task.log(content.toString(),Project.MSG_DEBUG);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#debug(java.lang.Throwable)
	 */
	public void debug(Throwable error) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#error(java.lang.CharSequence)
	 */
	public void error(CharSequence content) {
		_task.log(content.toString(),Project.MSG_ERR);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#error(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void error(CharSequence content, Throwable error) {
		_task.log(content.toString(),Project.MSG_ERR);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#error(java.lang.Throwable)
	 */
	public void error(Throwable error) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#info(java.lang.CharSequence)
	 */
	public void info(CharSequence content) {
		_task.log(content.toString(),Project.MSG_INFO);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#info(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void info(CharSequence content, Throwable error) {
		_task.log(content.toString(),Project.MSG_INFO);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#info(java.lang.Throwable)
	 */
	public void info(Throwable error) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#isErrorEnabled()
	 */
	public boolean isErrorEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#isWarnEnabled()
	 */
	public boolean isWarnEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#warn(java.lang.CharSequence)
	 */
	public void warn(CharSequence content) {
		_task.log(content.toString(),Project.MSG_WARN);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#warn(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void warn(CharSequence content, Throwable error) {
		_task.log(content.toString(),Project.MSG_WARN);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.Logger#warn(java.lang.Throwable)
	 */
	public void warn(Throwable error) {
		// TODO Auto-generated method stub

	}

}
