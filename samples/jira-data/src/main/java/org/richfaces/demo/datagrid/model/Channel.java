/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/web-projects/data-view-grid-demo/src/com/exadel/jsf/demo/datagrid/model/Channel.java,v $
 *      $Revision: 1.8 $ 
 */

package org.richfaces.demo.datagrid.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;



/**
 * @author Maksim Kaszynski
 * @modified by Anton Belevich
 *
 */
public class Channel {
	
	private static final Log log = LogFactory.getLog(Channel.class);
	
	private static final Comparator<Issue> byIdDesc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return o2.getIndex() - o1.getIndex();
		}
	};
	private static final Comparator<Issue> byIdAsc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return o1.getIndex() - o2.getIndex();
		}
	};
	
	private static final Comparator<Issue> byPriAsc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return o1.getPriority().getId() - o2.getPriority().getId();
		}
	};
	private static final Comparator<Issue> byPriDesc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return o2.getPriority().getId() - o1.getPriority().getId();
		}
	};

	private static final Comparator<JiraUser> jiraUserComparator = new Comparator<JiraUser> () {
		public int compare(JiraUser o1, JiraUser o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};
	
	private static final Comparator<Issue> byAssigneeAsc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return jiraUserComparator.compare(o1.getAssignee(), o2.getAssignee());
		}
	};
	private static final Comparator<Issue> byAssigneeDesc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return -byAssigneeAsc.compare(o1, o2);
		}
	};

	static final Comparator<Issue> byReporterAsc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return jiraUserComparator.compare(o1.getReporter(), o2.getReporter());
		}
	};
	static final Comparator<Issue> byReporterDesc = new Comparator<Issue>(){
		public int compare(Issue o1, Issue o2) {
			return -byReporterAsc.compare(o1, o2);
		}
	};

	static final Comparator<Issue> byStatusAsc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return o1.getStatus().getId() - o2.getStatus().getId();
		};
	};
	
	static final Comparator<Issue> byStatusDesc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return -byStatusAsc.compare(o1, o2);
		};
	};
	
	static final Comparator<Issue> byKeyAsc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return o1.getKey().compareTo(o2.getKey());
		};
	};
	
	static final Comparator<Issue> byKeyDesc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return -byKeyAsc.compare(o1, o2);
		};
	};
	
	static final Comparator<Issue> byResolutionDesc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return -byResolutionAsc.compare(o1, o2);
		};
	};
	
	static final Comparator<Issue> byResolutionAsc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return o1.getResolution().compareTo(o2.getResolution());
		};
	};
	
	static final Comparator<Issue> bySummaryDesc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return -bySummaryAsc.compare(o1, o2);
		};
	};
	
	static final Comparator<Issue> bySummaryAsc = new Comparator<Issue>() {
		public int compare(Issue o1, Issue o2) {
			return o1.getSummary().compareTo(o2.getSummary());
		};
	};
	
	private int maxSize;
	private List<Issue> issues = new ArrayList<Issue>();
	private String title;
	private String description;
	private String link;
	private String language;
	
	private Map <Integer, Issue> index = new HashMap<Integer, Issue>();
	
	public Channel(int maxSize) {
		this.maxSize = maxSize;
	}
	
	
	public int size() {
		return issues.size();
	}
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the issues
	 */
	public List<Issue> getIssues() {
		return issues;
	}
	/**
	 * @param issues the issues to set
	 */
	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
	
	public void addIssue(Issue issue) {
		
		if (maxSize >= 0 && issues.size() > maxSize) {
			return;
		}
		if (issue.getAssignee().getUsername().startsWith("a")) {
			issue.setAssignee(null);
		}
		issues.add(issue);
		index.put(issue.getIndex(), issue);
	}
	/**
	 * @return the index
	 */
	public Map<Integer, Issue> getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Map<Integer, Issue> index) {
		this.index = index;
	}
	
	
	public Issue findById(Integer id) {
		return getIndex().get(id);
	}
	
	public Issue findIssueByKey(Object id) {
		
		for (Issue issue : getIssues()) {
			if (issue.getId().equals(id)) {
				return issue;
			}
		}
		
		return null;
	}
	
	public void replace(Integer id, List<Issue> list) {
		Issue issue = issues.get(id.intValue());
		issues.removeAll(list);
		issues.addAll(issues.indexOf(issue)+1, list);
	}
}
