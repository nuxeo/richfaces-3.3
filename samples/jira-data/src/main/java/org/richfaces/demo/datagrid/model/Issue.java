/*
 *  Copyright
 *      Copyright (c) Exadel,Inc. 2006
 *      All rights reserved.
 *  
 *  History
 *      $Source: /cvs-master/intralinks-jsf-comps/web-projects/data-view-grid-demo/src/com/exadel/jsf/demo/datagrid/model/Issue.java,v $
 *      $Revision: 1.6 $ 
 */

package org.richfaces.demo.datagrid.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Maksim Kaszynski
 *
 */
public class Issue {
	private static int  issueIndex = 0;
	
	private int index = ++issueIndex;
	
	private String title;
	private String link;
	private String description;
	private String environment;
	private Key key;
	private Type type;
	private Priority priority;
	private JiraUser assignee;
	private JiraUser reporter;
	private String summary;
	private Status status;
	private String resolution;
	private String created;
	private String updated;
	private String version;
	private String component;
	private String due;
	private String votes;
	private String customfields;

	//TODO: provide correct mapping
	private String comments;
	private String fixVersion;
	private String timeestimate;
	private String timespent;
	
	//TODO: provide correct mapping
	private String issuelinks;
	
	private String timeoriginalestimate;
	
	/**
	 * @return the timeoriginalestimate
	 */
	public String getTimeoriginalestimate() {
		return timeoriginalestimate;
	}
	/**
	 * @param timeoriginalestimate the timeoriginalestimate to set
	 */
	public void setTimeoriginalestimate(String timeoriginalestimate) {
		this.timeoriginalestimate = timeoriginalestimate;
	}
	/**
	 * @return the timespent
	 */
	public String getTimespent() {
		return timespent;
	}
	/**
	 * @param timespent the timespent to set
	 */
	public void setTimespent(String timespent) {
		this.timespent = timespent;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the customfields
	 */
	public String getCustomfields() {
		return customfields;
	}
	/**
	 * @param customfields the customfields to set
	 */
	public void setCustomfields(String customfields) {
		this.customfields = customfields;
	}
	/**
	 * @return the votes
	 */
	public String getVotes() {
		return votes;
	}
	/**
	 * @param votes the votes to set
	 */
	public void setVotes(String votes) {
		this.votes = votes;
	}
	/**
	 * @return the due
	 */
	public String getDue() {
		return due;
	}
	/**
	 * @param due the due to set
	 */
	public void setDue(String due) {
		this.due = due;
	}
	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}
	/**
	 * @return the updated
	 */
	public String getUpdated() {
		return updated;
	}
	/**
	 * @param updated the updated to set
	 */
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	/**
	 * @return the resolution
	 */
	public String getResolution() {
		return resolution;
	}
	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the assignee
	 */
	public JiraUser getAssignee() {
		return assignee;
	}
	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(JiraUser assignee) {
		this.assignee = assignee;
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
		/*if (description != null && description.length() > 2000) {
			description = description.substring(0, 2000);
		}*/
		this.description = description;
	}
	/**
	 * @return the environment
	 */
	public String getEnvironment() {
		return environment;
	}
	/**
	 * @param environment the environment to set
	 */
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(Key key) {
		this.key = key;
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
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	/**
	 * @return the reporter
	 */
	public JiraUser getReporter() {
		return reporter;
	}
	/**
	 * @param reporter the reporter to set
	 */
	public void setReporter(JiraUser reporter) {
		this.reporter = reporter;
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
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return the fixVersion
	 */
	public String getFixVersion() {
		return fixVersion;
	}
	/**
	 * @param fixVersion the fixVersion to set
	 */
	public void setFixVersion(String fixVersion) {
		this.fixVersion = fixVersion;
	}
	/**
	 * @return the timeestimate
	 */
	public String getTimeestimate() {
		return timeestimate;
	}
	/**
	 * @param timeestimate the timeestimate to set
	 */
	public void setTimeestimate(String timeestimate) {
		this.timeestimate = timeestimate;
	}
	/**
	 * @return the issuelinks
	 */
	public String getIssuelinks() {
		return issuelinks;
	}
	/**
	 * @param issuelinks the issuelinks to set
	 */
	public void setIssuelinks(String issuelinks) {
		this.issuelinks = issuelinks;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return (index);
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return
	 * @see com.exadel.jsf.demo.datagrid.model.Priority#getId()
	 */
	public int getPriorityId() {
		return getPriority().getId();
	}
	
	public Serializable getId() {
		// TODO Auto-generated method stub
		return getKey();
	}
	
	public void resetKey() {
		key = new Key();
		key.setValue(UUID.randomUUID().toString());
	}
	
}
