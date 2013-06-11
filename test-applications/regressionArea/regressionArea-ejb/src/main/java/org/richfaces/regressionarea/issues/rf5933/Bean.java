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


package org.richfaces.regressionarea.issues.rf5933;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * This test case for: <a href="https://jira.jboss.org/jira/browse/RF-5933">RF-5933 - 
 * AJAX: oncomplete called for aborted timed out requests</a>
 * 
 * @author Nick Belaevski
 * @since 3.3.1
 */

@Name("rf5933")
@Scope(ScopeType.APPLICATION)
public class Bean {

	public void test() throws Exception {
		System.out.println("rf5993.Bean.test()");
		Thread.sleep(5000);
	}
}
