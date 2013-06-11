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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.UIDatascroller;

/**
 * Bean class for dataScroller component testing
 * @author Andrey Markavtsov
 *
 */
@SuppressWarnings("unchecked")
public class DataScrollerBean {
	
	
	private List data;
	
	private Integer tableRows = 1;
	
	private Integer totalRows = 10;
	
	private Integer maxPages = 10;
	
	private Integer page;
	
	private String onpagechange = "EventQueue.fire('onpagechange')";
	
	private String reRender;
	
	private boolean renderIfSinglePage = true;
	
	private String content1 = "content1";
	
	private String content2 = "content2";
	
	private boolean limitToList = false;
	
	private boolean ajaxSingle = true;
	
	private String listener1;
	
	private String listener2;
	
	private UIDatascroller scroller;
	
	public DataScrollerBean() {
		init();
	}
	
	public String maxPages() {
		maxPages = 5;
		return null;
	}
	
	public String onpagechange() {
		onpagechange = "return false;";
		return null;
	}
	
	public String pageVars() {
		reRender = "activePage, pagesCount,limit_input, limitControl";
		return null;
	}
	
	
	private void init() {
		data = new ArrayList();
		for (int i = 0; i < totalRows; i++) {
			String content = "Page " + (i / tableRows + 1);
			data.add(new Object [] {i + 1, content});
		}
	}
	
	public void reset() {
		tableRows = 1;
		totalRows = 10;
		maxPages = 10;
		page = null;
		data = null;
		onpagechange = "EventQueue.fire('onpagechange')";
		renderIfSinglePage = true;
		limitToList = false;
		ajaxSingle = true;
		content1 = "content1";
		content2 = "content2";
		listener1 = "reset";
		listener2 = "reset";
	}
	
	public void apply(ActionEvent event) {
		init();
	}
	
	public String getTime() {
		return String.valueOf(new Date().getTime());
	}
	
	public String changeRenderIfSinglePage() {
		if(renderIfSinglePage){
			renderIfSinglePage = false;
		}else{
			renderIfSinglePage = true;
		}
		return null;
	}
	
	public String changePage() {
		page = 5;
		return null;
	}

	/**
	 * @return the data
	 */
	public List getData() {
		if (data == null) {
			init();
		}
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List data) {
		this.data = data;
	}

	/**
	 * @return the tableRows
	 */
	public Integer getTableRows() {
		return tableRows;
	}

	/**
	 * @param tableRows the tableRows to set
	 */
	public void setTableRows(Integer tableRows) {
		this.tableRows = tableRows;
	}

	/**
	 * @return the totalRows
	 */
	public Integer getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the maxPages
	 */
	public Integer getMaxPages() {
		return maxPages;
	}

	/**
	 * @param maxPages the maxPages to set
	 */
	public void setMaxPages(Integer maxPages) {
		this.maxPages = maxPages;
	}

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the onpagechange
	 */
	public String getOnpagechange() {
		return onpagechange;
	}

	/**
	 * @param onpagechange the onpagechange to set
	 */
	public void setOnpagechange(String onpagechange) {
		this.onpagechange = onpagechange;
	}

	/**
	 * @return the reRender
	 */
	public String getReRender() {
		return reRender;
	}

	/**
	 * @param reRender the reRender to set
	 */
	public void setReRender(String reRender) {
		this.reRender = reRender;
	}

	/**
	 * @return the renderIfSinglePage
	 */
	public boolean isRenderIfSinglePage() {
		return renderIfSinglePage;
	}

	/**
	 * @param renderIfSinglePage the renderIfSinglePage to set
	 */
	public void setRenderIfSinglePage(boolean renderIfSinglePage) {
		this.renderIfSinglePage = renderIfSinglePage;
	}

	/**
	 * @return the scroller
	 */
	public UIDatascroller getScroller() {
		return scroller;
	}

	/**
	 * @param scroller the scroller to set
	 */
	public void setScroller(UIDatascroller scroller) {
		this.scroller = scroller;
	}

	public boolean isLimitToList() {
		return limitToList;
	}

	public void setLimitToList(boolean limitToList) {
		this.limitToList = limitToList;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}
	
	public void valueChangeContent1(ValueChangeEvent event) {
		listener1 = "invoked";
	}
	
	public void valueChangeContent2(ValueChangeEvent event) {
		listener2 = "invoked";
	}

	
	public String getContentListener1() {
		return listener1;
	}
	
	public String getContentListener2() {
		return listener2;
	}

	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}
	
}
