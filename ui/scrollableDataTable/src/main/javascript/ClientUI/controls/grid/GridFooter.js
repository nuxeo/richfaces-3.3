/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.GridFooter");

ClientUILib.requireClass("ClientUI.common.box.Box");

/*
/* GridHeader.js - Grid control header pane
 * TODO: add comments
 */
ClientUI.controls.grid.GridFooter = Class.create(ClientUI.common.box.Box, {
	initialize: function($super, template, grid) {
		this.grid = grid;
		$super(template);
		this.createControl(template);
	},
	createControl: function(template) {
		var errMsg = "";
		if(!template) {
			errMsg = "Invalid template specified for GridFooter.";
			ClientUILib.log(ClientUILogger.ERROR, errMsg);
			throw(errMsg);
		}
		
		if(!this.parseTemplate(template)) {
			errMsg = "Unable to parse template. GridFooter requires template specified over table element with one row.";
			ClientUILib.log(ClientUILogger.ERROR, errMsg);
			throw(errMsg);
		}

		this.controlCreated = true;
	},
	parseTemplate: function(template) {
		if(!template) {
			return false;
		}

		var childs = template.childNodes;
		for(var i=0; i<childs.length; i++) {
			if(childs[i].tagName && childs[i].tagName.toLowerCase() == "div") {
				this.container = new ClientUI.common.box.Box(childs[i], null, true);
				this.container.setStyle({"z-index": 100});
				break;
			}
		}
		
		var normal = null, frozen = null;
		var childs = this.container.getElement().childNodes;
		for(var i=0; i<childs.length; i++) {
			if(childs[i].id && childs[i].id.indexOf("FrozenBox")>=0) {
				frozen = childs[i];
			}
			else if(childs[i].id && childs[i].id.indexOf("NormalBox")>=0){
				normal = childs[i];
			}
		}

		if(!normal || !frozen) {
			errMsg = "Unable to parse template for GridFooter. Unable to find FrozenBox or NormalBox.";
			ClientUILib.log(ClientUILogger.ERROR, errMsg);
			throw(errMsg);
		}
		this.contentBox = new ClientUI.common.box.Box(normal);
		this.frozenContentBox = new ClientUI.common.box.Box(frozen);
		
		// create row template
		var ch = this.contentBox.getElement().firstChild;
		while(ch) {
			if(ch.tagName && ch.tagName.toLowerCase()=="table") {
				this.headerRow = new ClientUI.common.box.Box($(ch), null, true);
				break;
			}
			ch = ch.nextSibling;
		}
		ch = this.frozenContentBox.getElement().firstChild;
		while(ch) {
			if(ch.tagName && ch.tagName.toLowerCase()=="table") {
				this.headerFrozenRow = new ClientUI.common.box.Box($(ch), null, true);
				break;
			}
			ch = ch.nextSibling;
		}		

		this.helpObj = new ClientUI.common.box.Box(this.frozenContentBox.getElement(), null, true);
				
		this.frozenSubstrate = new ClientUI.common.box.Box(this.grid.getElement().id + ":fs", this.getElement());
		this.frozenSubstrate.getElement().name = this.getElement().id + "FRFrm";
		this.frozenSubstrate.setHeight(this.headerRow.element.offsetHeight);			
		return true;
	},
	updateSize: function() {
		this.setHeight(this.headerRow.element.offsetHeight);
	},
	updateLayout: function($super) {
		if(!this.controlCreated || !this.grid.controlCreated) {
			return;
		}
		$super();
	
		var height = this.element.offsetHeight;
		var frozenContentWidth = this.frozenContentBox.getElement().offsetWidth;

		this.contentBox.setHeight(height);
		this.contentBox.moveTo(frozenContentWidth - this.grid.getScrollOffset(), 0);
		this.frozenContentBox.setHeight(height);
		this.frozenContentBox.moveTo(0, 0);
		
		var viewWidth = this.grid.getBody().scrollBox.getViewportWidth();
		this.container.setWidth(viewWidth);
		this.setWidth(viewWidth);
		this.frozenSubstrate.setWidth(frozenContentWidth);
	},
	adjustScrollPosition: function(pos) {
		this.contentBox.moveToX(this.frozenContentBox.getElement().offsetWidth - pos);	
	},
	
	hideColumn: function(index, frozen) {
		var rows;
		if(frozen) {
			rows = this.headerFrozenRow.getElement().rows;
		} else {
			rows = this.headerRow.getElement().rows;
		}
		for(var i=0; i<rows.length; i++) {
			rows[i].removeChild(rows[i].cells[index]);
		}
	}	
});
