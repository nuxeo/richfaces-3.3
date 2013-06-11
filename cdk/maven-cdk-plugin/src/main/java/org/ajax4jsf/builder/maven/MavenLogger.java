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

import org.ajax4jsf.builder.generator.Logger;
import org.apache.maven.plugin.logging.Log;

/**
 * @author shura
 *
 */
public class MavenLogger implements Logger {
	
	private Log _log;

	/**
	 * @param _log
	 */
	public MavenLogger(Log _log) {
		super();
		this._log = _log;
	}

	/**
	 * @param content
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#debug(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void debug(CharSequence content, Throwable error) {
		_log.debug(content, error);
	}

	/**
	 * @param content
	 * @see org.apache.maven.plugin.logging.Log#debug(java.lang.CharSequence)
	 */
	public void debug(CharSequence content) {
		_log.debug(content);
	}

	/**
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#debug(java.lang.Throwable)
	 */
	public void debug(Throwable error) {
		_log.debug(error);
	}

	/**
	 * @param content
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#error(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void error(CharSequence content, Throwable error) {
		_log.error(content, error);
	}

	/**
	 * @param content
	 * @see org.apache.maven.plugin.logging.Log#error(java.lang.CharSequence)
	 */
	public void error(CharSequence content) {
		_log.error(content);
	}

	/**
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#error(java.lang.Throwable)
	 */
	public void error(Throwable error) {
		_log.error(error);
	}

	/**
	 * @param content
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#info(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void info(CharSequence content, Throwable error) {
		_log.info(content, error);
	}

	/**
	 * @param content
	 * @see org.apache.maven.plugin.logging.Log#info(java.lang.CharSequence)
	 */
	public void info(CharSequence content) {
		_log.info(content);
	}

	/**
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#info(java.lang.Throwable)
	 */
	public void info(Throwable error) {
		_log.info(error);
	}

	/**
	 * @return
	 * @see org.apache.maven.plugin.logging.Log#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return _log.isDebugEnabled();
	}

	/**
	 * @return
	 * @see org.apache.maven.plugin.logging.Log#isErrorEnabled()
	 */
	public boolean isErrorEnabled() {
		return _log.isErrorEnabled();
	}

	/**
	 * @return
	 * @see org.apache.maven.plugin.logging.Log#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		return _log.isInfoEnabled();
	}

	/**
	 * @return
	 * @see org.apache.maven.plugin.logging.Log#isWarnEnabled()
	 */
	public boolean isWarnEnabled() {
		return _log.isWarnEnabled();
	}

	/**
	 * @param content
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#warn(java.lang.CharSequence, java.lang.Throwable)
	 */
	public void warn(CharSequence content, Throwable error) {
		_log.warn(content, error);
	}

	/**
	 * @param content
	 * @see org.apache.maven.plugin.logging.Log#warn(java.lang.CharSequence)
	 */
	public void warn(CharSequence content) {
		_log.warn(content);
	}

	/**
	 * @param error
	 * @see org.apache.maven.plugin.logging.Log#warn(java.lang.Throwable)
	 */
	public void warn(Throwable error) {
		_log.warn(error);
	}

}
