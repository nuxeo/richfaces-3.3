/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.component;

import java.util.Map;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.Messages;
import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.renderkit.AjaxRendererUtils;
import org.ajax4jsf.renderkit.RendererUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.component.util.MessageUtil;
import org.richfaces.event.DataScrollerEvent;
import org.richfaces.event.DataScrollerListener;
import org.richfaces.event.DataScrollerSource;


/** JSF component class */
//xxxx nick -> alex - extend UIComponentBase and
//create event listener & event classes to define PageSwitchEvent
//public abstract class UIDatascroller extends UIComponentBase implements DataScrollerSource{
public abstract class UIDatascroller extends AjaxActionComponent
implements DataScrollerSource, ActionSource {

	public static final String LAST_PAGE_MODE_FULL = "full";

	public static final String LAST_PAGE_MODE_SHORT = "short";

	private static final Log log = LogFactory.getLog(UIDatascroller.class);
	
	private Integer page;

	public static final String COMPONENT_TYPE = "org.richfaces.Datascroller";
	public static final String COMPONENT_FAMILY = "org.richfaces.Datascroller";

	public static final String FIRST_FACET_NAME = "first";

	public static final String LAST_FACET_NAME = "last";

	public static final String NEXT_FACET_NAME = "next";

	public static final String PREVIOUS_FACET_NAME = "previous";

	public static final String FAST_FORWARD_FACET_NAME = "fastforward";

	public static final String FAST_REWIND_FACET_NAME = "fastrewind";


	public static final String FIRST_DISABLED_FACET_NAME = "first_disabled";

	public static final String LAST_DISABLED_FACET_NAME = "last_disabled";

	public static final String NEXT_DISABLED_FACET_NAME = "next_disabled";

	public static final String PREVIOUS_DISABLED_FACET_NAME
	= "previous_disabled";

	public static final String FAST_FORWARD_DISABLED_FACET_NAME
	= "fastforward_disabled";

	public static final String FAST_REWIND_DISABLED_FACET_NAME
	= "fastrewind_disabled";

	public void addScrollerListener(DataScrollerListener listener) {
		addFacesListener(listener);
	}

	public DataScrollerListener[] getScrollerListeners() {
		return (DataScrollerListener[]) getFacesListeners(
				DataScrollerListener.class);
	}

	public void removeScrollerListener(DataScrollerListener listener) {
		removeFacesListener(listener);
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
		if (event instanceof DataScrollerEvent) {
			DataScrollerEvent dataScrollerEvent = (DataScrollerEvent) event;

			updateModel(dataScrollerEvent.getPage());

			FacesContext context = getFacesContext();

			MethodExpression scrollerListener = getScrollerListener();
			if (scrollerListener != null) {
				scrollerListener.invoke(context.getELContext(), new Object[]{event});
			}
		} else if (event instanceof AjaxEvent) {
			FacesContext context = getFacesContext();

			AjaxRendererUtils.addRegionByName(context, this, this.getId());
			AjaxRendererUtils.addRegionByName(context, this, this.getFor());

			setupReRender(context);
		}
	}

	public abstract MethodExpression getScrollerListener();

	public abstract void setScrollerListener(MethodExpression scrollerListener);

	public abstract void setFor(String f);

	public abstract String getFor();

	public abstract int getFastStep();

	public abstract void setFastStep(int FastStep);

	public abstract boolean isRenderIfSinglePage();

	public abstract void setRenderIfSinglePage(boolean renderIfSinglePage);

	public abstract int getMaxPages();

	public abstract void setMaxPages(int maxPages);

	public abstract String getSelectedStyleClass();

	public abstract void setSelectedStyleClass(String selectedStyleClass);

	public abstract String getSelectedStyle();

	public abstract void setSelectedStyle(String selectedStyle);

	public abstract String getEventsQueue();

	public abstract void setEventsQueue(String eventsQueue);

	public abstract boolean isAjaxSingle();

	public abstract void setAjaxSingle(boolean ajaxSingle);

	public abstract int getRequestDelay();

	public abstract void setRequestDelay(int requestDelay);

	public abstract String getSimilarityGroupingId();

	public abstract void setSimilarityGroupingId(String similarityGroupingId);
	
	public abstract String getTableStyleClass();

	public abstract void setTableStyleClass(String tableStyleClass);

	public abstract String getStyleClass();

	public abstract String getStyle();

	public abstract void setStyleClass(String styleClass);

	public abstract void setStyle(String styleClass);

	public abstract String getAlign();

	public abstract void setAlign(String align);

	public abstract String getBoundaryControls();

	public abstract void setBoundaryControls(String boundaryControls);

	public abstract String getFastControls();

	public abstract void setFastControls(String fastControls);

	public abstract String getStepControls();

	public abstract void setStepControls(String stepControls);

	public abstract String getInactiveStyleClass();

	public abstract String getInactiveStyle();

	public abstract void setInactiveStyleClass(String inactiveStyleClass);

	public abstract void setInactiveStyle(String inactiveStyle);
	
	public abstract String getLastPageMode();
	public abstract void setLastPageMode(String lastPageMode);
	
	/**
	 * Finds the dataTable which id is mapped to the "for" property
	 *
	 * @return the dataTable component
	 */
	 public UIData getDataTable() {
		 String forAttribute = getFor();
		 UIComponent forComp;
		 if (forAttribute == null) {
			 forComp = this;
			 while ((forComp = forComp.getParent()) != null) {
				 if (forComp instanceof UIData) {
					 setFor(forComp.getId());
					 return (UIData) forComp;
				 }
			 }
			 throw new FacesException(
					 "could not find dataTable for  datascroller " + this.getId());
		 } else {
			 forComp = RendererUtils.getInstance().findComponentFor(this, forAttribute);
		 }
		 if (forComp == null) {
			 throw new IllegalArgumentException("could not find dataTable with id '"
					 + forAttribute + "'");
		 } else if (!(forComp instanceof UIData)) {
			 throw new IllegalArgumentException(
					 "component with id '" + forAttribute
					 + "' must be of type " + UIData.class.getName()
					 + ", not type "
					 + forComp.getClass().getName());
		 }
		 return (UIData) forComp;
	 }

	 private int getFastStepOrDefault() {
		 int step = getFastStep();
		 if (step <= 0) {
			 return 1;
		 } else {
			 return step;
		 }
	 }

	 /**
	  * @param data
	  * @return
	  * @see #getPage()
	  * @deprecated
	  */
	 public int getPageIndex(UIData data) {
		 return getPageIndex();
	 }

	 /**
	  * @return
	  * @see #getPage()
	  * @deprecated
	  */
	 public int getPageIndex() {
		 return getPage();
	 }

	 public int getPageForFacet(String facetName) {
		 if (facetName == null) {
			 throw new NullPointerException();
		 }

		 int newPage = 1;
		 int pageCount = getPageCount();

		 if (FIRST_FACET_NAME.equals(facetName)) {
			 newPage = 1;
		 } else if (PREVIOUS_FACET_NAME.equals(facetName)) {
			 newPage = getPage() - 1;
		 } else if (NEXT_FACET_NAME.equals(facetName)) {
			 newPage = getPage() + 1;
		 } else if (LAST_FACET_NAME.equals(facetName))  {
			 newPage = pageCount > 0 ? pageCount : 1;
		 } else if (FAST_FORWARD_FACET_NAME.equals(facetName)) {
			 newPage = getPage() + getFastStepOrDefault();
		 } else if (FAST_REWIND_FACET_NAME.equals(facetName)) {
			 newPage = getPage() - getFastStepOrDefault();
		 } else {
			 try {
				 newPage = Integer.parseInt(facetName.toString());
			 } catch (NumberFormatException e) {
				 throw new FacesException(e.getLocalizedMessage(), e);
			 }
		 }

		 if (newPage >= 1 && newPage <= pageCount) {
			 return newPage;
		 } else {
			 return 0;
		 }
	 }

	 /**
	  * Sets the page number according to the parameter recived from the
	  * commandLink
	  *
	  * @param facetName - can be "first:, "last", "next",  "previous",
	  * "fastforward", "fastrewind"
	  * 
	  * @see #setPage(int)
	  * @see #getPageForFacet(String)
	  * 
	  * @deprecated as of 3.2 replaced with <code>setPage(int)</page>
	  */
	 public void setPage(String facetName) {
		 int newPage = getPageForFacet(facetName);
		 if (newPage != 0) {
			 setPage(newPage);
		 }
	 }

	 public int getPageCount(UIData data) {
		 int rowCount = getRowCount(data);
		 int rows = getRows(data);
		 return getPageCount(data, rowCount, rows);
	 }
	 
	 public int getPageCount(UIData data, int rowCount, int rows) {
		 int pageCount;
		 if (rows > 0) {
			 pageCount = rows <= 0 ? 1 : rowCount / rows;
			 if (rowCount % rows > 0) {
				 pageCount++;
			 }
			 if (pageCount == 0) {
				 pageCount = 1;
			 }
		 } else {
			 rows = 1;
			 pageCount = 1;
		 }
		 return pageCount;
	 }

	 /** @return the page count of the uidata */
	 public int getPageCount() {
		 return getPageCount(getDataTable());
	 }

	 public int getRowCount(UIData data) {
		 int rowCount = data.getRowCount();
		 if (rowCount >= 0) {
			 return rowCount;
		 }

		 return BinarySearch.search(data);
	 }

	 /** @return int */
	 public int getRowCount() {
		 //xxx nick -> alex - scrollable models can return -1 here
		 //let's implement "dychotomic" discovery
		 // setPage(1)... if isPageAvailable() setPage(2) then 4, 8, etc.
		 // setPage() { setRowIndex(pageIdx * rows); }
		 // isPageAvailable() { return isRowAvailable() }
		 //return getUIData().getRowCount();
		 return getRowCount(getDataTable());
	 }

	 public int getRows(UIData data) {
		 int row = 0;
		 row = data.getRows();
		 if (row == 0) {
			 row = getRowCount(data);
		 }

		 return row;
	 }

	 /**
	  * @param data
	  * @return
	  * @see #getPage()
	  * @deprecated
	  */
	 public int getFirstRow(UIData data) {  
		 return data.getFirst();  
	 }  

	 /**
	  * @param rows
	  * @see #setPage(int)
	  * @deprecated
	  */
	 public void setFirstRow(int rows) {  
		 getDataTable().setFirst(rows);  
	 }  

	 // facet getter methods
	 public UIComponent getFirst() {
		 return getFacet(FIRST_FACET_NAME);
	 }

	 public UIComponent getLast() {
		 return getFacet(LAST_FACET_NAME);
	 }

	 public UIComponent getNext() {
		 return getFacet(NEXT_FACET_NAME);
	 }

	 public UIComponent getFastForward() {
		 return getFacet(FAST_FORWARD_FACET_NAME);
	 }

	 public UIComponent getFastRewind() {
		 return getFacet(FAST_REWIND_FACET_NAME);
	 }

	 public UIComponent getPrevious() {
		 return getFacet(PREVIOUS_FACET_NAME);
	 }

	 private static boolean isRendered(UIComponent component) {
		 UIComponent c = component;
		 while (c != null) {
			 if (!c.isRendered()) {
				 return false;
			 }

			 c = c.getParent();
		 }

		 return true;
	 }


	 public void setupFirstRowValue() {
		 UIData dataTable = getDataTable();
		 if (isRendered()) {
			 int rowCount = getRowCount(dataTable);
			 int rows = getRows(dataTable);
			 
			 Integer pageCount = getPageCount(dataTable, rowCount, rows);

			 int page = getPage();
			 int newPage = -1;
			 if (page < 1) {
				 newPage = 1;
			 } else if (page > pageCount) {
				 newPage = (pageCount != 0 ? pageCount : 1);
			 }

			 if (newPage != -1) {
				 FacesContext context = getFacesContext();
				 Object label = MessageUtil.getLabel(context, this);
				 String formattedMessage = Messages.getMessage(Messages.DATASCROLLER_PAGE_MISSING, 
						 new Object[] {label, page, pageCount, newPage});

				 log.warn(formattedMessage);
				 
				 page = newPage;
				 dataTable.getAttributes().put(SCROLLER_STATE_ATTRIBUTE, page);
			 }

			 if (isRendered(dataTable)) {
				 int first;
				 
				 String lastPageMode = getLastPageMode();
				 if (lastPageMode == null) {
					 lastPageMode = LAST_PAGE_MODE_SHORT;
				 } else if (!LAST_PAGE_MODE_SHORT.equals(lastPageMode) && 
						 !LAST_PAGE_MODE_FULL.equals(lastPageMode)) {
					 
					 throw new IllegalArgumentException("Illegal value of 'lastPageMode' attribute: '" + lastPageMode + "'");
				 }
					 
				 if (page != pageCount || LAST_PAGE_MODE_SHORT.equals(lastPageMode)) {
					 first = (page - 1) * rows;
				 } else {
					 first = rowCount - rows;
					 if (first < 0) {
						 first = 0;
					 }
				 }
				 
				 dataTable.setFirst(first);
			 }
		 }
	 }

	 public void setPage(int newPage) {
		 this.page = newPage;
	 }

	 public int getPage() {
		 Map<String, Object> attributes = getDataTable().getAttributes();
		 Integer state = (Integer) attributes.get(SCROLLER_STATE_ATTRIBUTE);
		 if (state != null) {
			 return state;
		 }
		 
		 if (this.page != null) {
			 return page;
		 } 

		 ValueExpression ve = getValueExpression("page");
		 if (ve != null) {
			 try {
				 Integer pageObject = (Integer) ve.getValue(getFacesContext().getELContext());

				 if (pageObject != null) {
					 return pageObject;
				 } else {
					 return 1;
				 }
			 } catch (ELException e) {
				 throw new FacesException(e);
			 }
		 } else {
			 return 1;
		 }
	 }

	 private void updateModel(int newPage) {
		 UIData dataTable = getDataTable();
		 
		 if (isRendered(dataTable)) {
			 dataTable.setFirst((newPage - 1) * getRows(dataTable));
		 }
		 
		 Map<String, Object> attributes = dataTable.getAttributes();
		 attributes.put(SCROLLER_STATE_ATTRIBUTE, newPage);
		 
		 FacesContext context = getFacesContext();
		 ValueExpression ve = getValueExpression("page");
		 if (ve != null) {
			 try {
				 ve.setValue(context.getELContext(), newPage);
				 attributes.remove(SCROLLER_STATE_ATTRIBUTE);
			 } catch (ELException e) {
				 String messageStr = e.getMessage();
				 Throwable result = e.getCause();
				 while (null != result &&
						 result.getClass().isAssignableFrom(ELException.class)) {
					 messageStr = result.getMessage();
					 result = result.getCause();
				 }
				 FacesMessage message;
				 if (null == messageStr) {
					 message =
						 MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID,
								 new Object[] { MessageUtil.getLabel(
										 context, this) });
				 } else {
					 message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							 messageStr,
							 messageStr);
				 }
				 context.getExternalContext().log(message.getSummary(), result);
				 context.addMessage(getClientId(context), message);
				 context.renderResponse();
			 } catch (IllegalArgumentException e) {
				 FacesMessage message =
					 MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID,
							 new Object[] { MessageUtil.getLabel(
									 context, this) });
				 context.getExternalContext().log(message.getSummary(), e);
				 context.addMessage(getClientId(context), message);
				 context.renderResponse();
			 } catch (Exception e) {
				 FacesMessage message =
					 MessageUtil.getMessage(context, UIInput.UPDATE_MESSAGE_ID,
							 new Object[] { MessageUtil.getLabel(
									 context, this) });
				 context.getExternalContext().log(message.getSummary(), e);
				 context.addMessage(getClientId(context), message);
				 context.renderResponse();
			 }
		 }
	 }

	 public String getFamily() {
		 return COMPONENT_FAMILY;
	 }

	 static class BinarySearch {

		 public static int search(UIData data) {
			 int rowIndex = data.getRowIndex();
			 try {
				 int n = 1;
				 int k = 2;
				 for (; ;) {
					 data.setRowIndex(k - 1);
					 if (data.isRowAvailable()) {
						 n = k;
						 k = k * 2;
					 } else {
						 break;
					 }
				 }

				 while (n < k) {
					 int kk = Math.round((n + k) / 2) + 1;
					 data.setRowIndex(kk - 1);
					 if (data.isRowAvailable()) {
						 n = kk;
					 } else {
						 k = kk - 1;
					 }
				 }

				 data.setRowIndex(k - 1);
				 if (data.isRowAvailable()) {
					 return k;
				 } else {
					 return 0;
				 }
			 } finally {
				 data.setRowIndex(rowIndex);
			 }
		 }
	 }

	 public Object saveState(FacesContext context) {
		 return new Object[] {
				 super.saveState(context),    
				 page
		 };

	 }

	 public void restoreState(FacesContext context, Object object) {
		 Object[] state = (Object[]) object;

		 super.restoreState(context, state[0]);
		 page = (Integer) state[1];
	 }

	 public static final String SCROLLER_STATE_ATTRIBUTE = COMPONENT_TYPE + ":page";

	 public boolean isLocalPageSet() {
		 return page != null;
	 }

	 public void resetLocalPage() {
		 page = null;
	 }

}
