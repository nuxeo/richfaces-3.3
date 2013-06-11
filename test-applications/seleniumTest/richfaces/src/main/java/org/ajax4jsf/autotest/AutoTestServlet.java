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
package org.ajax4jsf.autotest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO Class description goes here.
 * @author Andrey
 *
 */
public class AutoTestServlet extends HttpServlet {
	
    private static final long serialVersionUID = -7355230949138030161L;

    private static final String COMPONENT_PARAMETER_NAME = "c";
	
	private static final String EVENT_PARAMETER_NAME = "ev";
	
	private static final String ATTRIBUTE_PARAMETER_NAME = "at_name";
	
	private static final String ATTRIBUTE_PARAMETER_VALUE = "at_value";
	
	private static final String CONTEXT_NAME = "SeleniumAutoTestContext";
	
	String header = "<html xmlns=\"http://www.w3.org/1999/xhtml\" " + 
		"xmlns:a4j=\"http://richfaces.org/a4j\" " +
		"xmlns:rich=\"http://richfaces.org/rich\" " +
		"xmlns:h=\"http://java.sun.com/jsf/html\" " +
		"xmlns:f=\"http://java.sun.com/jsf/core\" " +
		"xmlns:ui=\"http://java.sun.com/jsf/facelets\">";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			AutoTestContext context = (AutoTestContext)getServletContext().getAttribute(CONTEXT_NAME);
			if (context == null) {
				throw new NullPointerException("Test context is null");
			}
		
			PrintWriter writer = resp.getWriter();
			writer.write(header);
			
			writer.write("<");
			writer.write(context.getComponent());
			writer.write(" ");
			writer.write("id=\"test\" ");
			
			for (String ev : context.getEvents()) {
				writer.write(ev + "=\"EventQueue.fire('"+ev+"')\" ");
			}
			
			for (String attribute : context.getAttributes().keySet()) {
				writer.write(attribute + "=\"" + context.getAttributes().get(attribute)+"\" ");
			}
			
			
			writer.write(">");
			writer.write("</" + context.getComponent() + ">");
			
			writer.write("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String component = req.getParameter(COMPONENT_PARAMETER_NAME);
		String [] events = req.getParameterValues(EVENT_PARAMETER_NAME);
		String [] attributes_name = req.getParameterValues(ATTRIBUTE_PARAMETER_NAME);
		String [] attributes_value = req.getParameterValues(ATTRIBUTE_PARAMETER_VALUE);
		
		
		AutoTestContext context = new AutoTestContext(component);
		for (String event: events) {
			context.addEvent(event);
		}
		
		for (int i = 0; i < attributes_name.length; i++) {
			context.addAttribute(attributes_name[i], attributes_value[i]);
		}
		
		getServletContext().setAttribute(CONTEXT_NAME, context);
		
		resp.sendRedirect("/richfaces/faces/NEKO/pages/_autotest/autotest.xhtml");

	}
	
	

}
