/**
 * Grid.js		Date created: 6.04.2007
 * Copyright (c) 2007 Exadel Inc.
 * @author Denis Morozov <dmorozov@exadel.com>
 */
ClientUILib.declarePackage("ClientUI.controls.grid.Grid");

ClientUILib.requireClass("ClientUI.common.box.Box");
ClientUILib.requireClass("ClientUI.controls.grid.GridHeader");
ClientUILib.requireClass("ClientUI.controls.grid.GridBody");
ClientUILib.requireClass("ClientUI.controls.grid.GridFooter");

/*
 * grid.js - Grid library on top of Prototype 
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 * TODO: description of control 
 *
 * TODO: usage description
 * Usage: 
 *   <script src="/javascripts/prototype.js" type="text/javascript"></script>
 *   <script src="/javascripts/grid.js" type="text/javascript"></script>
 *   <script type="text/javascript">
 *     // with valid DOM id
 *     var grid = new ClientUI.controls.grid.Grid('id_of_trigger_element', 'id_of_tooltip_to_show_element')
 *
 *     // with text
 *     var grid = new ClientUI.controls.grid.Grid('id_of_trigger_element', 'a nice description')
 *   </script>
 */
ClientUI.controls.grid.Grid = Class.create(ClientUI.common.box.Box, {
		
	initialize: function($super, element, dataModel, templates) {
		$super(element);
		
		this.dataModel = dataModel;
		this.templates = $A(templates);
	
		this.createControl();
		
	},
	createControl: function() {
		var grid = this;
		this.layout = new ClientUI.layouts.GridLayoutManager(this.getElement().id + ":c", null);
		
		var pagePart, item;
		for(var i=0; i<this.templates.length; i++) {
			item = this.templates[i];
			switch(item.pane) {
				case GridLayout_Enum.HEADER: {
					pagePart = new ClientUI.controls.grid.GridHeader($(item.ref), grid);
					this.layout.addPane(GridLayout_Enum.HEADER, pagePart);
					break;
					}
				case GridLayout_Enum.BODY: {
					pagePart = new ClientUI.controls.grid.GridBody($(item.ref), grid);
					this.layout.addPane(GridLayout_Enum.BODY, pagePart);
					break;
					}
				case GridLayout_Enum.FOOTER: {
					pagePart = new ClientUI.controls.grid.GridFooter($(item.ref), grid);
					this.layout.addPane(GridLayout_Enum.FOOTER, pagePart);
					break;
					}
			}			
		}
		
		this.currentScrollPos = 0;
		this.controlCreated = true;
		var grid = this;
		Utils.execOnLoad(
			function(){
				grid.getHeader().updateSize();
				grid.getBody().updateSize();
				if(grid.getFooter()) {grid.getFooter().updateSize();}
				grid.updateLayout();
				grid.getBody().restoreScrollState();
			},
			Utils.Condition.ElementPresent(($(this.getElement().id + ":c")).parentNode), 100);
	},
	
	updateLayout: function($super) {
		$super();
		var element = $(this.getElement().id + ":c")
		var height = element.parentNode.offsetHeight;
		if (element.offsetHeight != height) {
			element.setStyle({height: height + "px"});
		}
		this.getHeader().resetFakeColumnWidth();
		if(this.layout) {
			this.layout.updateLayout();
		}
		this.getHeader().setFakeColumnWidth();
		if (element.offsetHeight != height) {
			element.setStyle({height: height + "px"});
		}
	},
	getHeader: function() {
		return this.layout.getPane(GridLayout_Enum.HEADER);
	},
	getFooter: function() {
		return this.layout.getPane(GridLayout_Enum.FOOTER);
	},
	getBody: function() {
		return this.layout.getPane(GridLayout_Enum.BODY);		
	},
	adjustColumnWidth: function(index, width) {
		this.getHeader().adjustColumnWidth(index, width);
		this.updateLayout();
		this.getHeader().agjustSeparators();
		this.element.fire("grid:onresizecolumn",{index:index, width:width});
	},
	adjustScrollPosition: function(pos) {
		if(pos<0) {pos = 0;}
		this.currentScrollPos = pos;
		this.getHeader().adjustScrollPosition(pos);
		this.getBody().adjustScrollPosition(pos);
		if(this.getFooter()) {this.getFooter().adjustScrollPosition(pos);}
	},
	getScrollOffset: function() {
		return this.currentScrollPos ? this.currentScrollPos : 0;
	},
	setColumnMinWidth: function(index, width) {
		if(index<0 || index>=this.getHeader().getColumns().length)
			return false;
		this.getHeader().getColumns()[index].minWidth = width;
		return true;		
	},
	invalidate: function(params) {
		this.getBody().invalidate(params);
	},
	adjustColumnsWidth: function() {
		var columns = this.getHeader().getColumns();
		for(var i=0; i<columns.length; i++) {
			this.adjustColumnWidth(i, columns[i].width);
		}
	},
	ensureVisible: function(index) {
		this.getBody().ensureVisible(index);
	},
	getDataIndex: function(rowIndex) {
		var body = this.getBody();
		return body.currRange.start+rowIndex;
	},
	getRowIndex: function(dataIndex) {
		var body = this.getBody();
		return (dataIndex>=body.currRange.start && dataIndex<body.currRange.start+body.rowsCount) ?
			dataIndex - body.currRange.start : -1;
	},
	hideColumn: function(column) {
		this.adjustColumnWidth(column, 0);
	},
	reloadData: function() {
		this.getBody().reloadData();
	},
	updateRowCount: function(newCount) {
		var rowCount = parseInt(newCount);
		if(rowCount>=0) {
			this.dataModel.count = rowCount;
			this.updateLayout();
		}
	}
});			
