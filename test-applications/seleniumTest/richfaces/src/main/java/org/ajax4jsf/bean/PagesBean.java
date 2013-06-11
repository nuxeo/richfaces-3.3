/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

/**
 * @author asmirnov
 * 
 */
public class PagesBean {

	private static final Pattern JSP_PATTERN = Pattern.compile(".*\\.jspx?");

	private static final Pattern XHTML_PATTERN = Pattern.compile(".*\\.xhtml");
	
	private static final Pattern TITLE_PATTERN = Pattern.compile("<title>(.*)</title>",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);

	private ServletContext _servletContext;

	private List<PageDescriptionBean> _jspPages;

	private String _path;

	private List<PageDescriptionBean> _xhtmlPages;

	/**
	 * @return the path
	 */
	public String getPath() {
		return _path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		_path = path;
	}

	/**
	 * @return the servletContext
	 */
	public ServletContext getServletContext() {
		return _servletContext;
	}

	/**
	 * @param servletContext
	 *            the servletContext to set
	 */
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public List<PageDescriptionBean> getJspPages() {
		if (_jspPages == null && null != getServletContext()) {
			_jspPages = getPagesByPattern(JSP_PATTERN);
		}

		return _jspPages;
	}

	public List<PageDescriptionBean> getXhtmlPages() {
		if (_xhtmlPages == null && null != getServletContext()) {
			_xhtmlPages = getPagesByPattern(XHTML_PATTERN);
		}

		return _xhtmlPages;
	}

	/**
	 * 
	 */
	private List<PageDescriptionBean> getPagesByPattern(Pattern pattern) {
		List<PageDescriptionBean> jspPages = new ArrayList<PageDescriptionBean>();
		Set resourcePaths = getServletContext().getResourcePaths(getPath());
		for (Iterator iterator = resourcePaths.iterator(); iterator
				.hasNext();) {
			String page = (String) iterator.next();
			if (pattern.matcher(page).matches()) {
				PageDescriptionBean pageBean = new PageDescriptionBean();
				pageBean.setPath(page);
				InputStream pageInputStream = getServletContext().getResourceAsStream(page);
				if(null != pageInputStream){
					byte[] head = new byte[1024];
					try {
						int readed = pageInputStream.read(head);
						String headString = new String(head,0,readed);
						Matcher titleMatcher = TITLE_PATTERN.matcher(headString);
						if(titleMatcher.find() && titleMatcher.group(1).length()>0){
							pageBean.setTitle(titleMatcher.group(1));
						} else {
							pageBean.setTitle(page);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						try {
							pageInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				jspPages.add(pageBean);
			}
		}
		return jspPages;
	}

}
