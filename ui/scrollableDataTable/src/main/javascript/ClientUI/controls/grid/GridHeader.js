/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.GridHeader");

ClientUILib.requireClass("ClientUI.common.box.Box");
ClientUILib.requireClass("ClientUI.common.box.InlineBox");

/*
 * GridHeader.js - Grid control header pane
 * TODO: add comments
 */
ClientUI.controls.grid.GridHeader = Class.create(ClientUI.common.box.Box, {
	// constructor
	initialize: function($super, template, grid) {
		this.grid = grid;
		this.gridId = this.grid.getElement().id;
		this.normalizedId = this.grid.options.normalizedId;
		$super(template);

		// register event handlers
		this.eventSepClick = this.OnSepClick.bindAsEventListener(this);
		this.eventSepMouseDown = this.OnSepMouseDown.bindAsEventListener(this);
		this.eventSepMouseUp = this.OnSepMouseUp.bindAsEventListener(this);
		this.eventSepMouseMove = this.OnSepMouseMove.bindAsEventListener(this);
		this.eventCellMouseDown = this.OnCellMouseDown.bindAsEventListener(this);
		Event.observe(document, 'mousemove', this.eventSepMouseMove, true);
		Event.observe(document, 'mouseup', this.eventSepMouseUp, true);

		this.createControl(template);
	},
	
	// create grid header control
	createControl: function(template) {
		var errMsg = "";
		if(!template) {
			errMsg = "Invalid template specified for GridHeader.";
			ClientUILib.log(ClientUILogger.ERROR, errMsg);
			throw(errMsg);
		}

		if(!this.parseTemplate(template)) {
			errMsg = "Unable to parse template. GridHeader requires template specified over table element with one row.";
			ClientUILib.log(ClientUILogger.ERROR, errMsg);
			throw(errMsg);
		}

		this.controlCreated = true;
		this.agjustSeparators();
	},
	parseTemplate: function(template) {
		if(!template) {
			return false;
		}
		var childs = template.childNodes;
		for(var i=0; i<childs.length; i++) {
			if(childs[i].tagName && childs[i].tagName.toLowerCase() == "div") {
				this.container = $(childs[i]);
				this.container.setStyle({"z-index": 100});
				break;
			}
		}

		var normal = null, frozen = null;
		var childs = this.container.childNodes;
		for(var i=0; i<childs.length; i++) {
			if(childs[i].id && childs[i].id.indexOf("FrozenBox")>=0) {
				frozen = childs[i];
			}
			else if(childs[i].id && childs[i].id.indexOf("NormalBox")>=0){
				normal = childs[i];
			}
		}

		if(!normal || !frozen) {
			errMsg = "Unable to parse template for GridHeader. Unable to find FrozenBox or NormalBox.";
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

		var rows = this.headerFrozenRow.getElement().rows;
		var fcount = (rows.length && rows[0].cells.length && rows[0].cells[0].innerHTML)?rows[0].cells.length:0;
		var ncount = this.headerRow.getElement().rows[0].cells.length;
		var columns = new Array(fcount + ncount);
		
		var eventCellMouseDown = this.eventCellMouseDown.bind(this);
		
		// Get columns information
		var i = 0, j=0, cell;
		this.frozenCells = fcount?this.headerFrozenRow.getElement().rows[0].cells:[];
		var ids = this.grid.options.ids;
		var count = this.frozenCells.length;
		for(i=0; i<count; i++) {
			cell = this.frozenCells[i];
			columns[j] = {
				columnId: ids[i],
				styleClass: cell.className,
				id: cell.id,
				align: cell.align,
				valign: cell.vAlign,
				title: cell.title,
				minWidth: 10,
				frozen: true,
				fixedWidth: Validators.getBoolean(cell.getAttribute("fixedWidth"), false),
				sortable: Validators.getBoolean(cell.getAttribute("sortable"), false),
				sorted: Validators.getBoolean(cell.getAttribute("sorted"), "desc"),
				style : Utils.getRule("#"+ this.normalizedId + " .dr-sdt-c-" + j).style
			};
			if(columns[j].sortable)
				Event.observe(cell, 'click',  eventCellMouseDown);
			columns[j].object = new ClientUI.common.box.InlineBox(cell, null, true);
			var details = this._getCellElements(j);
			// separator
			columns[j].sep = new ClientUI.common.box.InlineBox(details[0], null, true);
			columns[j].sep.getElement().columnIndex = j;
			if(!columns[j].fixedWidth) {
				Event.observe(columns[j].sep.getElement(), 'click',  this.eventSepClick);
				Event.observe(columns[j].sep.getElement(), 'mousedown', this.eventSepMouseDown);
			}			
			else {
				sep.setStyle({cursor: 'auto'});
			}
			// sort icons
			//columns[j].sortIcon = new ClientUI.common.box.Box(details[1], null, true);
			//columns[j].sortDesc = new ClientUI.common.box.Box(details[1], null, true);
			//columns[j].sortAsc = new ClientUI.common.box.Box(details[2], null, true);
			j++;
		}

		this.cells = this.headerRow.getElement().rows[0].cells;
		count = this.cells.length;
		for(i=0; i<count; i++) {
			cell = this.cells[i];

			columns[j] = {
				columnId: ids[i],
				styleClass: cell.className,
				id: cell.id,
				align: cell.align,
				valign: cell.vAlign,
				title: cell.title,
				minWidth: 10,
				frozen: false,
				fixedWidth: Validators.getBoolean(cell.getAttribute("fixedWidth"), false),
				sortable: Validators.getBoolean(cell.getAttribute("sortable"), false),
				sorted: null,
				style : Utils.getRule("#"+ this.normalizedId + " .dr-sdt-c-" + (( i < count - 1 ) ? j : "f")).style
			};
			
			if(columns[j].sortable)
				Event.observe(cell, 'click',  eventCellMouseDown);
			columns[j].object = new ClientUI.common.box.InlineBox(cell, null, true);
			
			var details = this._getCellElements(j);
			// separator
			if (details[0]) {
				columns[j].sep = new ClientUI.common.box.InlineBox(details[0], null, true);
				columns[j].sep.getElement().columnIndex = j;
				if(!columns[j].fixedWidth) {
					Event.observe(columns[j].sep.getElement(), 'click',  this.eventSepClick);
					Event.observe(columns[j].sep.getElement(), 'mousedown', this.eventSepMouseDown);
				}			
				else {
					sep.setStyle({cursor: 'auto'});
				}
				// sort icons
				//columns[j].sortIcon = new ClientUI.common.box.Box(details[1], null, true);
				//columns[j].sortDesc = new ClientUI.common.box.Box(details[1], null, true);
				//columns[j].sortAsc = new ClientUI.common.box.Box(details[2], null, true);
			}
			j++;
		}
		
		this._columns = columns;
		this.frozenSubstrate = new ClientUI.common.box.Box(this.gridId + ":hs", this.getElement());
		this.frozenSubstrate.getElement().name = this.getElement().id + "HRFrm";
		this.frozenSubstrate.setHeight(this.headerRow.element.offsetHeight);
		
		return true;
	},
	_getCellElements: function(column) {
		var details = new Array(3);
		var clientId = this.grid.getElement().id;
		details[0] = $(clientId + ":hsep_" + column);
		details[1] = $(clientId + ":hs_" + column);
		//details[1] = document.getElementById(clientId + ":hsortd_" + column);
		//details[2] = document.getElementById(clientId + ":hsorta_" + column);
		return details;
	},
	agjustSeparators: function() {
		var i=0;
		var length = this.frozenCells.length;
		var delta = 4;
		if (this.cells[0].offsetWidth == this.cells[0].clientWidth) {
			delta--;
		}
		for(var j=0; j<length; i++,j++) {
			this._columns[i].sep.moveToX(this.frozenCells[j].offsetLeft + this.frozenCells[j].offsetWidth - delta);
		}
		var length = this.cells.length - 1;
		for(var j=0; j<length; i++,j++) {
			this._columns[i].sep.moveToX(this.cells[j].offsetLeft + this.cells[j].offsetWidth - delta);
		}
	},
	updateSize: function() {
		this.setHeight(this.headerRow.element.offsetHeight);
		this.agjustSeparators();
		this.updateHeaders();
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
		this.headerRow.setHeight(height);
		this.frozenContentBox.setHeight(height);
		this.frozenContentBox.moveTo(0, 0);
		this.headerFrozenRow.setHeight(height);
		this.frozenSubstrate.setWidth(frozenContentWidth);
		this.updateHeaders();
	},
	getColumns: function() {
		return this._columns;
	},
	// lets implement column resizer
	OnSepMouseDown: function(event) {
		this.dragColumnInfo = {
			srcElement: Event.element(event),
			dragStarted: false,
			mouseDown: true,
			startX: Event.pointerX(event),
			originalX: 0
		};		
		this.dragColumnInfo.object = this.getColumns()[this.dragColumnInfo.srcElement.columnIndex].object;
		this.dragColumnInfo.sep = this.getColumns()[this.dragColumnInfo.srcElement.columnIndex].sep;
		this.dragColumnInfo.minWidth = this.getColumns()[this.dragColumnInfo.srcElement.columnIndex].minWidth;
		
		Event.stop(event);
	},
	OnSepMouseUp: function(event) {
		if(this.dragColumnInfo && this.dragColumnInfo.dragStarted) {
			this.dragColumnInfo.dragStarted = false;
			this.dragColumnInfo.mouseDown = false;
			var delta = Event.pointerX(event) - this.dragColumnInfo.startX;
			var newWidth = this.dragColumnInfo.object.getWidth() + delta;
			if(newWidth < this.dragColumnInfo.minWidth) {
				newWidth = this.dragColumnInfo.minWidth;
			}
			setTimeout(function() {
				this.grid.adjustColumnWidth(this.dragColumnInfo.srcElement.columnIndex, newWidth); 
			}.bind(this), 10);
		}
		this._hideSplitter();
	},
	OnSepMouseMove: function(event) {
		if(this.dragColumnInfo && this.dragColumnInfo.mouseDown) {
			if(!this.dragColumnInfo.dragStarted) {
				this.dragColumnInfo.dragStarted = true;
				this._showSplitter(this.dragColumnInfo.srcElement.columnIndex);
			}
			else {
				var delta = Event.pointerX(event) - this.dragColumnInfo.startX;
				var minColumnWidth = this.dragColumnInfo.object.getWidth() - this.dragColumnInfo.minWidth;
				if(delta >= -minColumnWidth) {
					var x = this.dragColumnInfo.originalX + delta;
					this.columnSplitter.moveToX(x - 6);
				}
			}
			Event.stop(event);
		}
	},
	OnSepClick: function(event) {
		Event.stop(event);
	},
	_showSplitter: function(index) {
		if(!this.columnSplitter) {
			this._createSplitter();
		}

		var pos = this.dragColumnInfo.sep.getX();
		if(!this.getColumns()[index].frozen) {
			pos += this.frozenContentBox.getElement().offsetWidth - this.grid.getScrollOffset();
		}
		this.dragColumnInfo.originalX = pos;
		this.columnSplitter.show();
		this.columnSplitter.setHeight(this.headerRow.element.offsetHeight + this.grid.getBody().contentBox.element.offsetHeight);
		this.columnSplitter.moveTo(pos, 0);
	},
	_hideSplitter: function() {
		if(this.columnSplitter) {
			this.columnSplitter.hide();
		}
	},
	_createSplitter: function() {
		this.columnSplitter = new ClientUI.common.box.Box(this.gridId +":cs", this.grid.getElement());
		this.columnSplitter.makeAbsolute();
		this.columnSplitter.setWidth(10);
	},
	adjustScrollPosition: function(pos) {
		this.contentBox.moveToX(this.frozenContentBox.getElement().offsetWidth - pos);
	},
	OnCellMouseDown: function(event) {
		var el = Event.element(event);
		while(el && !Element.hasClassName(el, "dr-sdt-hc")) {
			el = el.parentNode;
		}
		
		if(el) {
			var sortColumnId = el.getAttribute("columnid");
			if(sortColumnId) {
				/*
				var dir = this.getColumns()[index].sorted;
				dir = (dir == "asc") ? "desc" : "asc";
				this.getColumns()[index].sorted = dir;
				
		        for(var i = 0, len = this.getColumns().length; i < len; i++) {
		            var h = this.getColumns()[i];
		            if(h.sortDesc && h.sortAsc) {
			            if(i != index) {
							Element.setStyle(h.sortDesc.getElement(), {display: 'none'});
							Element.setStyle(h.sortAsc.getElement(), {display: 'none'});
			            } else{
			            	h.sortDesc.moveTo(h.object.getWidth() - 16, 4);
			            	h.sortAsc.moveTo(h.object.getWidth() - 16, 4);
							Element.setStyle(h.sortDesc.getElement(), {display: (dir == 'desc' ? 'block' : 'none')});
							Element.setStyle(h.sortAsc.getElement(), {display: (dir == 'asc' ? 'block' : 'none')});
			            }
		            }
		        }
				*/
				var rows = this.grid.getBody().templFrozen.getElement().rows;
				var startRow = rows && rows.length>0 ? this.grid.getBody()._getRowIndex(rows[0].id) : 0;
				this.grid.element.fire("grid:onsort",{ column: sortColumnId,
					startRow: startRow,
					index: this.grid.getBody().currRange.start
				});
				Event.stop(event);
			}
		}		
	},
	
	updateHeaders : function() {
		var length = this.frozenCells.length;
		var posX = 0;
		for(var j=0; j<length; j++) {
			posX = this.updateHeader(posX, this.frozenCells[j]);
		}
		length = this.cells.length - 1;
		posX = 0;
		for(var j=0; j<length; j++) {
			posX = this.updateHeader(posX, this.cells[j]);
		}
	},
	
	updateHeader : function(posX, th) {
		var icon = $(this.gridId + ":hs_" + th.id.split("_").last());
		posX += th.offsetWidth;
		if (icon) {
			var newPosX = posX - icon.getWidth();
			var newPosY = (th.clientHeight - icon.offsetHeight)/2;
			
			newPosX = Math.floor(newPosX);
			newPosY = Math.floor(newPosY);
			
			icon.setStyle({left: newPosX + "px",top: newPosY + "px", visibility : "inherit"});
		}
		return posX;
	},
	
	adjustColumnWidth: function(column, width) {
		this._columns[column].style.width = width +"px";
		if(width<=0) this.getColumns()[column].sep.hide();
	},
	
	resetFakeColumnWidth: function() {
		this._columns.last().style.width = "0px";
	},
	
	setFakeColumnWidth: function() {
		var width = this.grid.getBody().container.element.clientWidth - this.headerFrozenRow.getElement().offsetWidth - this.headerRow.getElement().offsetWidth;
		if (width < 0) {
			width = 0;
		}
		 this._columns.last().style.width = width + "px";
	},
	
	hideColumn: function(index, frozen) {
		var column = this._columns.splice(index,1)[0];
		var input = $(this.grid.getElement().id + "_hc")
		input.value = input.value + column.columnId + ",";
		column.col.parentNode.removeChild(column.col);
		column.bodyCol.parentNode.removeChild(column.bodyCol);
		column.footerCol.parentNode.removeChild(column.footerCol);
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

Object.extend(ClientUI.controls.grid.GridHeader.prototype, {
	sepStyleClass: "dr-sdt-hsep", 
	// internal variables
	_columns: []	
});
