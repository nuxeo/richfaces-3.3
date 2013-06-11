/**
 * License Agreement.
 *
 * Ajax4jsf 1.1 - Natural Ajax for Java Server Faces (JSF)
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

package org.richfaces;
/**
 * @author Anton Belevich
 */
public class Bean {
	
	private String editEvent = "onclick";
	
	private String maxSelectWidth;
	
	private String minSelectWidth;
	
	private String selectWidth;
	
	private String defaultLabel = "Click ...";
	
	private String controlsPosition = "center";
	
	private String controlsHorizontalAlign = "right";
	
	private Object value = "A&'<laska";
	
	private String listWidth = "200px";
	
	private String listHeight = "200px";
	
	private boolean selectOnEdit;
	
	private boolean showControls;
	
	private boolean applyFromControlsOnly;
	
	private boolean editOnTab;
	
	private boolean openOnEdit;



	public String getEditEvent() {
		return editEvent;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public String getMaxSelectWidth() {
		return maxSelectWidth;
	}

	public void setMaxSelectWidth(String maxSelectWidth) {
		this.maxSelectWidth = maxSelectWidth;
	}

	public String getMinSelectWidth() {
		return minSelectWidth;
	}

	public void setMinSelectWidth(String minSelectWidth) {
		this.minSelectWidth = minSelectWidth;
	}

	public String getSelectWidth() {
		return selectWidth;
	}

	public void setSelectWidth(String selectWidth) {
		this.selectWidth = selectWidth;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public boolean isShowControls() {
		return showControls;
	}

	public void setShowControls(boolean showControls) {
		this.showControls = showControls;
	}

	public String getControlsPosition() {
		return controlsPosition;
	}

	public void setControlsPosition(String controlsPosition) {
		this.controlsPosition = controlsPosition;
	}

	public String getControlsHorizontalAlign() {
		return controlsHorizontalAlign;
	}

	public void setControlsHorizontalAlign(String controlsHorizontalAlign) {
		this.controlsHorizontalAlign = controlsHorizontalAlign;
	}

	public boolean getApplyFromControlsOnly() {
		return applyFromControlsOnly;
	}

	public void setApplyFromControlsOnly(boolean applyFromControlsOnly) {
		this.applyFromControlsOnly = applyFromControlsOnly;
	}

	public boolean isSelectOnEdit() {
		return selectOnEdit;
	}

	public void setSelectOnEdit(boolean selectOnEdit) {
		this.selectOnEdit = selectOnEdit;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getListWidth() {
		return listWidth;
	}

	public void setListWidth(String listWidth) {
		this.listWidth = listWidth;
	}

	public String getListHeight() {
		return listHeight;
	}

	public void setListHeight(String listHeight) {
		this.listHeight = listHeight;
	}

	public boolean isEditOnTab() {
		return editOnTab;
	}

	public void setEditOnTab(boolean editOnTab) {
		this.editOnTab = editOnTab;
	}

	public boolean isOpenOnEdit() {
		return openOnEdit;
	}

	public void setOpenOnEdit(boolean openOnEdit) {
		this.openOnEdit = openOnEdit;
	}
	
	
}