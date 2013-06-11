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

package org.richfaces.sandbox;

import java.util.Comparator;

import org.richfaces.demo.datagrid.model.Issue;
import org.richfaces.demo.datagrid.model.JiraUser;

/**
 * @author $Autor$
 *
 */
public class Comparators {
	
	private final Comparator<JiraUser> jiraUserComparator = new Comparator<JiraUser> () {
		public int compare(JiraUser o1, JiraUser o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	private final Comparator<Issue> assigneeComparator = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return jiraUserComparator.compare(o1.getAssignee(), o2.getAssignee());
		}
	};
	
	private final Comparator<Issue> reporterComparator = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return jiraUserComparator.compare(o1.getReporter(), o2.getReporter());
		}
	};
	
	public Comparator<Issue> getAssigneeComparator() {
		return assigneeComparator;
	}
	
	public Comparator<Issue> getReporterComparator() {
		return reporterComparator;
	}
	
}