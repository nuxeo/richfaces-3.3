if(!window.Richfaces) window.Richfaces = {};
Richfaces.disableSelectionText = function(e) {
	e = window.event||e;
	if (e.srcElement) {
		if (e.srcElement.tagName) {
			var tagName = e.srcElement.tagName.toUpperCase();
			
			if (tagName != "INPUT" && tagName != "TEXTAREA" /* any items more? */) {
				return false;
			}
		}
	}
};


Richfaces.ListBase = Class.create();

Richfaces.ListBase.compare = function(obj1, obj2) {
	return ((obj1 == obj2) ? 0 : ((obj1 < obj2) ? -1 : 1));
}

Richfaces.ListBase.ORDERING_LIST_CLASSES = {
	normal : "rich-ordering-list-items",
	disabled : "rich-ordering-list-disabled",
	active :  "rich-ordering-list-active"
}

Richfaces.ListBase.ASC = "acs";
Richfaces.ListBase.DESC = "desc";

Richfaces.ListBase.CONTROL_SET = ["A", "INPUT", "TEXTAREA", "SELECT", "OPTION", "BUTTON"];

Richfaces.ListBase.prototype = {
	initialize : function(containerId, contentTableId, headerTableId, focusKeeperId, 
				   		  onclickControlId, controlClass, columnsClasses, rowClasses) {
		this["rich:destructor"] = "destroy";
		this.selectedItems = new Array();
		
		//this.layoutManager = layoutManager;
		this.container = $(containerId);
		this.shuttleTable = $(contentTableId);
		this.shuttleTable.onselectstart = Richfaces.disableSelectionText;
		this.focusKeeper = $(focusKeeperId);
		this.focusKeeper.focused = false;
		//this.setFocus();
		this.focusKeeper.observe("keydown", (function(e) {this.onkeydownHandler(window.event || e)}).bindAsEventListener(this));
		this.focusKeeper.observe("blur", function (e) {this.focusListener(e);}.bindAsEventListener(this));
		this.focusKeeper.observe("focus", function (e) {this.onfocusHandler(e);}.bindAsEventListener(this));
		
		this.shuttleTbody = this.shuttleTable.tBodies[0];
		
		this.activeItem = null;
		this.pseudoActiveItem = null; //it services for items selection by Shift+click
		this.items = null;
		
		//FIX
		this.rowClasses = rowClasses;
		this.columnsClasses = columnsClasses;
		
		this.controlClass = controlClass;
		this.retrieveShuttleItems(containerId, controlClass);
		this.counter;
		this.shuttle = null;
		this.sortOrder = Richfaces.ListBase.ASC;
		this.clckHandler = function(e) {this.onclickHandler(window.event || e)}.bindAsEventListener(this);
		this.shuttleTable.observe("click", this.clckHandler);

		this.layoutManager = new LayoutManager(headerTableId, contentTableId);
//---   http://jira.jboss.com/jira/browse/RF-3830 FF3 & Safari only!
		this.tableElement = document.getElementById(contentTableId);
		var rows = this.tableElement.tBodies[0].rows;
		if (rows && rows[0]) {
			this.firstTrElement = rows[0];
			if (this.firstTrElement.addEventListener && (Richfaces.browser.isFF3 || Richfaces.browser.isSafari)) {
				this.imagesOnLoad = this.imageLoadListener.bind(this);
				this.firstTrElement.addEventListener('load',this.imagesOnLoad, true);
		  	}	
		}
//---		 
		var synch = function() {this.layoutManager.widthSynchronization()}.bindAsEventListener(this);
		RichShuttleUtils.execOnLoad(
			synch, RichShuttleUtils.Condition.ElementPresent(this.container), 100
		);
	},
	
	imageLoadListener: function (evt){
		this.layoutManager.widthSynchronization();
		if (this.firstTrElement.removeEventListener && (Richfaces.browser.isFF3 || Richfaces.browser.isSafari)) {
			this.firstTrElement.removeEventListener('load',this.imagesOnLoad, true);
		}
	},
	
	destroy: function() {
		this.shuttleTable.onselectstart = null;
		var items = this.shuttleItems;
		for (var i = 0; i < items.length; i++) {
			items[i].destroy();
		}
		
	},
	
	setActiveItem : function(newActiveItem) {
		this.pseudoActiveItem = newActiveItem;
		this.activeItem = newActiveItem;
	},
	
	retrieveShuttleItems : function(containerId, controlClass) {
		var rows = this.shuttleTbody.rows;
		this.shuttleItems = new Array();
		var id;
		
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			id = row.id.split(containerId + ":")[1];
			var item = new controlClass(null, (id || i), row);
			if (item.isSelected()) {
				this.selectedItems.push(row);
			}									  
			if (item.isActive()) {
				this.setActiveItem(row);
			}
			this.shuttleItems[i] = item;
		}	
	},
	
	getExtremeItem : function(position) { //FIXME
		var extremeItem = this.selectedItems[0];
		var currentItem;
		
		for (var i = 1; i < this.selectedItems.length; i++) {
			currentItem = this.selectedItems[i];
			if (position == "first") {
				if (currentItem.rowIndex < extremeItem.rowIndex) {
					extremeItem = currentItem;
				}
			} else {
				if (currentItem.rowIndex > extremeItem.rowIndex) {
					extremeItem = currentItem;
				}
			}
		}
		return extremeItem; 
	},
	
	getEventTargetRow : function(event) {
		var activeElem;
		if (event.target) {
			//activeElem = event.rangeParent.parentNode;
			activeElem = event.target;		
		} else {
			activeElem = event.srcElement;
		}
		
		if (activeElem == null) {
			return;
		}
		
		if (activeElem.tagName && Richfaces.ListBase.CONTROL_SET.indexOf(activeElem.tagName.toUpperCase()) != -1) {
			return;
		}
			
		while (activeElem.tagName.toLowerCase() != "tr") {
			activeElem = activeElem.parentNode;
			if (!activeElem.tagName) {
				return; //for IE
			}
		}
		return activeElem;
	},
	
	onfocusHandler: function (event) {
		if (!this.activeItem && this.shuttleItems.length != 0) {
			this.setActiveItem(this.shuttleItems[0]._node);
		}

		if (this.activeItem) {
			this.activeItem.item.doActive(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses);
		}
	},
	
	onclickHandler : function(event) {
		if (event.srcElement && (event.srcElement.tagName.toLowerCase() == "tbody")) {
			return;
		}
		var activeElem = this.getEventTargetRow(event);
		if (activeElem != null) {
			
			if (event.ctrlKey) {
			 	this.addSelectedItem(activeElem);
			 	this.setActiveItem(activeElem);
			} else if (event.shiftKey) {
				if (!this.pseudoActiveItem) {
					this.selectionItem(activeElem);
					this.setActiveItem(activeElem);	
				} else {
					this.selectItemGroup(activeElem);
					this.activeItem = activeElem; //given event works with pseudoActiveItem
				}
			} else {
				this.selectionItem(activeElem);
				this.setActiveItem(activeElem);	
			}
			
			
			this.setFocus();
		}
	},

	onkeydownHandler : function(event) {
		var action = null;
		switch (event.keyCode) {
			case 38 : //up arrow
					  action = 'up';
					  this.moveActiveItem(action, event);
					  Event.stop(event);
					  break;
			case 40 : //down arrow
					  action = 'down';
					  this.moveActiveItem(action, event);
					  Event.stop(event);
					  break;
			case 65 : // Ctrl + A
					  if (event.ctrlKey) { 
						this.selectAll();
					  } 
					  this.activeItem.item.doActive(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses);
					  Event.stop(event);
					  break; 
		}
	},
	
	moveActiveItem : function(action, event) {
		var item = this.activeItem;
		var rows = this.shuttleTbody.rows;
		if ((action == 'up') && (item.rowIndex > 0)) {
			this.changeActiveItems(rows[item.rowIndex - 1], item);
		} else if ((action == 'down') && (item.rowIndex < this.shuttleItems.length - 1)) {
			this.changeActiveItems(rows[item.rowIndex + 1], item);
		}
		
		this.autoScrolling(action, event);
		
	},

	changeActiveItems : function(newItem, item) {
		item.item.doNormal();
		this.resetMarked();
		
		newItem.item.doSelect(this.getExtRowClass(newItem.rowIndex), this.columnsClasses);
		newItem.item.doActive(this.getExtRowClass(newItem.rowIndex), this.columnsClasses);
		this.setActiveItem(newItem);
		this.selectedItems.push(newItem);
	},
	
	selectAll : function() {
		this.resetMarked();
		var startIndex = 0;
		var endIndex = this.shuttleItems.length - 1;
		this.selectItemRange(startIndex, endIndex);
	},
	
	/**
	 * Click handler
	 */
	selectionItem : function(activeItem) {
		var markedShuttleItem = activeItem;
		
		this.resetMarked();
		if (activeItem.item.isSelected()) {
			activeItem.item.doNormal(this.getExtRowClass(activeItem.rowIndex), this.columnsClasses);
		} else {
			activeItem.item.doSelect(this.getExtRowClass(activeItem.rowIndex), this.columnsClasses);
			this.selectedItems[0] = markedShuttleItem; //TODO: delete 
		}
	},
	
	/**
	 * CTRL+Click handler
	 */
	addSelectedItem : function(activeItem) {
		var markedShuttleItem = activeItem;
		
		if (activeItem.item.isSelected()) {
			this.selectedItems.remove(markedShuttleItem); //TODO :delete
			activeItem.item.doNormal(this.getExtRowClass(activeItem.rowIndex), this.columnsClasses);
		} else {
			activeItem.item.doSelect(this.getExtRowClass(activeItem.rowIndex), this.columnsClasses);
			this.selectedItems.push(markedShuttleItem); //TODO :delete
		}
		
		if ((this.activeItem != null) && (this.activeItem.rowIndex != activeItem.rowIndex)) {
			//reset activity of an element
			if (this.activeItem.item.isSelected()) {
				this.activeItem.item.doSelect(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses); 			
			} else {
				this.activeItem.item.doNormal(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses);				
			}
		}
	},
	
	/**
	 * Shift+Click handler
	 */
	selectItemGroup : function(currentItem) {
		//FIXME
		var activeItemIndex = this.pseudoActiveItem.rowIndex;
		var startIndex;
		var endIndex;
		
		if (currentItem.rowIndex > activeItemIndex) {
			startIndex = activeItemIndex;
			endIndex = currentItem.rowIndex;
		} else {
			startIndex = currentItem.rowIndex;
			endIndex = activeItemIndex;
		}
		
		this.resetMarked();
		
		this.selectItemRange(startIndex, endIndex);
	},
	
	selectItemRange : function(startIndex, endIndex) {
		var rows = this.shuttleTbody.rows;
		for (var i = startIndex; i <= endIndex; i++) {
			rows[i].item.doSelect(this.getExtRowClass(rows[i].rowIndex), this.columnsClasses);
			this.selectedItems.push(rows[i]);
		}	
	},
	
	resetMarked : function() {
		var rows = this.selectedItems;
		var length = rows.length;
		for (var i = 0; i < length; i++) {
			var shuttleItem = rows[i]; 
			shuttleItem.item.doNormal(this.getExtRowClass(shuttleItem.rowIndex), this.columnsClasses);
		}	
		this.selectedItems.length = 0;
		
		//need to reset active item
	},
	
	getSelectItemByNode : function(selectItemNode) {
		for (var i = 0; i < this.shuttleItems.length; i++) {
			var item = this.shuttleItems[i];
			if (selectItemNode.rowIndex == item._node.rowIndex) {
				return item;
			}
		}
		return null;
	},
	
	autoScrolling : function(action, event) {
		this.selectedItems.sort(this.compareByRowIndex);
		var increment;
		var scrollTop = this.shuttleTable.parentNode.scrollTop;
		
		var shuttleTop = LayoutManager.getElemXY(this.shuttleTable.parentNode).top;
		
		if (action == 'up' || action == 'first') {
			var targetItemTop = LayoutManager.getElemXY(this.selectedItems[0]).top;
			increment = (targetItemTop - scrollTop) - shuttleTop;
			if (increment < 0) {
				this.shuttleTable.parentNode.scrollTop += increment;			
			}
		} else if (action == 'down' || action == 'last') {
			var item = this.selectedItems[this.selectedItems.length - 1];
			var targetItemBottom = LayoutManager.getElemXY(this.selectedItems[this.selectedItems.length - 1]).top + item.offsetHeight;
			var increment = (targetItemBottom - scrollTop) - (shuttleTop + this.shuttleTable.parentNode.clientHeight);
			if (increment > 0) {
				this.shuttleTable.parentNode.scrollTop += increment;			
			} 
		}
		if (event) Event.stop(event);
	},
	
	setFocus : function() {
		this.focusKeeper.focus();
		this.focusKeeper.focused = true;
	},
	
	focusListener : function(e) {
		e = e || window.event;
		this.focusKeeper.focused = false;
		
		if (this.activeItem) {
			if (this.activeItem.item.isSelected()) {
				this.activeItem.item.doSelect(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses);			
			} else {
				this.activeItem.item.doNormal(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses);
			}
		}
	},
	
	compareByLabel : function(obj1, obj2) {
		obj1 = obj1._label;
		obj2 = obj2._label;
		return Richfaces.ListBase.compare(obj1, obj2);
	},
	
	compareByRowIndex : function(obj1, obj2) {
		obj1 = obj1.rowIndex;
		obj2 = obj2.rowIndex;
		return Richfaces.ListBase.compare(obj1, obj2);
	},
	
	isListActive : function() {
		if ((this.activeItem != null || this.selectedItems.length != 0) && this.focusKeeper.focused) {
			return true;
		}
		return false;
	},
	
	getExtRowClass : function(index) {
		return Richfaces.getExternalClass(this.rowClasses, index);
	},
	
	getSelection : function() {
		var result = [];
		for (var i = 0; i < this.selectedItems.length; i++) {
			result[i] = this.selectedItems[i].item;
		}
		return result;
	},
	
	getItems : function() {
		return this.shuttleTbody.rows;
	}
}