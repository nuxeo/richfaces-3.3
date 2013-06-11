if(!window.Richfaces) window.Richfaces = {};

Richfaces.OrderingListSelectItem = Class.create(Richfaces.SelectItem);
Richfaces.OrderingListSelectItem.prototype.CLASSES = {
	ROW : {
		ACTIVE   : "rich-ordering-list-row-active",
		SELECTED : "rich-ordering-list-row-selected",
		DISABLED : "rich-ordering-list-row-disabled",
		NORMAL   : "rich-ordering-list-row"
	},
	CELL : {
		ACTIVE   : "rich-ordering-list-cell-active",
		SELECTED : "rich-ordering-list-cell-selected",
		DISABLED : "rich-ordering-list-cell-disabled",
		NORMAL   : "rich-ordering-list-cell",
		BEGIN:	" rich-ordering-list-cell-first",
		END:	" rich-ordering-list-cell-last"
	}
},


Richfaces.OrderingList = Class.create(Richfaces.ListBase, {
	initialize: function($super, containerId, contentTableId, headerTableId, focusKeeperId, ids, onclickControlId, events, controlClass, columnsClasses, rowClasses) {
		$super(containerId, contentTableId, headerTableId, focusKeeperId, onclickControlId, controlClass, columnsClasses, rowClasses);
		
		this.container.component = this;
		
		this.events = events;
		this.controlList = new Array();
		this.initControlList(containerId, ids);
		
		for (var e in this.events) {
			if (e && this.events[e]) {
				this.container.observe("rich:" + e.toString(), this.events[e]);
			}
		}
	},

	destroy: function($super) {
		$super();
		
		this.container.component = null;
	},
	
	initControlList : function(containerId, ids) {
		for (var i = 0; i < ids.length; i++) {
			var id = ids[i];
			var node = $(containerId + id[0]);
			var disNode = $(containerId + id[1]);
			if (node && disNode) { 
				node.observe('click', Richfaces.OrderingList.HANDLERS[id[0]].bindAsEventListener(this));
				this.controlList[i] = new Richfaces.Control(node, disNode, false, false, id[0]);
			}
		}
	},
	
	controlListManager : function() {
		this.selectedItems.sort(this.compareByRowIndex);
		var control;
		//FIXME
		this.controlsProcessing(["first", "last", "down", "up"], "enable");
		if ((this.shuttleItems.length == 0) || (this.selectedItems.length == 0)) {
			this.controlsProcessing(["first", "last", "down", "up"], "disable");
		} else {
			if (this.selectedItems[0].rowIndex == 0) 
				this.controlsProcessing(["first", "up"], "disable");
			if (this.selectedItems[this.selectedItems.length - 1].rowIndex == (this.shuttleItems.length - 1)) 
				this.controlsProcessing(["down", "last"], "disable");
		}
	},
	
	controlsProcessing : function(disabledControls , action) {
		for (var i = 0; i < this.controlList.length; i++) {
			control = this.controlList[i];
			if (control != null) {
				if (disabledControls != null && disabledControls.indexOf(control.action) != -1) {
					if (action == "disable") {
						control.doDisable();
					} else {
						control.doEnable();
					}
				}
			}
		}
	},
	
	onclickHandler : function($super, event) {
		$super(event);
		this.controlListManager();
	},
	
	moveActiveItem : function($super, action, event) {
		$super(action, event);
		this.controlListManager();
	},
	
	moveSelectedItems : function(action, event) {
		if (this.selectedItems.length > 0) {
			
			if (Richfaces.invokeEvent(this.events.onorderchange, this.container, "rich:onorderchange", {items: this.shuttleItems})) {
				event = window.event||event;
				var rows = this.shuttleTbody.rows;
				var item;
				
				this.selectedItems.sort(this.compareByRowIndex);
				
				if ((action == 'up') && this.getExtremeItem("first").previousSibling) {
					for (var i = 0; i < this.selectedItems.length; i++) {
						item = this.selectedItems[i];
						item.parentNode.insertBefore(item, item.previousSibling);			
					}
				} else if ((action == 'down') && this.getExtremeItem("last").nextSibling) {
					for (var i = this.selectedItems.length - 1; i > -1; i--) {
						item = this.selectedItems[i];
						item.parentNode.insertBefore(item.nextSibling, item);				
					}
				} else if (action == 'first') {
					var incr = this.selectedItems[0].rowIndex;
					for (var i = 0; i < this.selectedItems.length; i++) {
						item = this.selectedItems[i];
						item.parentNode.insertBefore(item, rows[item.rowIndex - incr]);				
					}
				} else if (action == 'last') {
					var length = this.shuttleItems.length;
					var incr = length - this.selectedItems[this.selectedItems.length - 1].rowIndex;
					for (var i = this.selectedItems.length - 1; i > -1; i--) {
						item = this.selectedItems[i];
						if (item.rowIndex + incr > length - 1) {
							item.parentNode.insertBefore(item, null);
						} else {
							item.parentNode.insertBefore(item, rows[item.rowIndex + incr]);
						}
					}
				}
				
				this.shuttleItems = new Array();
				for (var i = 0; i < rows.length; i++) {
					this.shuttleItems.push(rows[i].item);
				}
				if (action != null) 
					this.autoScrolling(action, event);
					
				this.container.fire("rich:onorderchanged", {items: this.shuttleItems});
				this.controlListManager();
			}
		}
	},
	
	onkeydownHandler : function(event) {
		var action = null;
		switch (event.keyCode) {
			case 34 : action = 'last'; 
					  this.moveSelectedItems(action ,event); 
					  Event.stop(event);
			 		  break; //page down
			case 33 : action = 'first'; 
					  this.moveSelectedItems(action, event);
					  Event.stop(event);
					  break; //page up
			case 38 : //up arrow
					  action = 'up';
					  if (event.ctrlKey) {
					  	this.moveSelectedItems(action, event);  
					  } else {
					  	this.moveActiveItem(action, event);
					  }
					  Event.stop(event);
					  break;
			case 40 : //down arrow
					  action = 'down';
					  if (event.ctrlKey) {
					  	 this.moveSelectedItems(action ,event);  
					  } else {
					  	this.moveActiveItem(action, event);
					  }
					  Event.stop(event);
					  break;
			case 65 : // Ctrl + A
					  if (event.ctrlKey) { 
						this.selectAll();
					  } 
					  this.activeItem.item.doActive(this.getExtRowClass(this.activeItem.rowIndex), this.columnsClasses);
					  this.controlListManager();
					  Event.stop(event);
					  break; 
		}
	},
	
	top : function(e) {
		this.container.fire("rich:ontopclick", {items: this.shuttleItems, selection: this.getSelection()});
		this.moveSelectedItems("first", e);
	},
	
	bottom : function(e) {
		this.container.fire("rich:onbottomclick", {items: this.shuttleItems, selection: this.getSelection()});
		this.moveSelectedItems("last", e);
	},
	
	up : function(e) {
		this.container.fire("rich:onupclick", {items: this.shuttleItems, selection: this.getSelection()});
		this.moveSelectedItems("up", e);
	},
	
	down : function(e) {
		this.container.fire("rich:ondownclick", {items: this.shuttleItems, selection: this.getSelection()});
		this.moveSelectedItems("down", e);
	}
	
});

Richfaces.OrderingList.ACTIVITY_MARKER = "a";
Richfaces.OrderingList.SELECTION_MARKER = "s";
Richfaces.OrderingList.ITEM_SEPARATOR = ",";

Richfaces.OrderingList.HANDLERS = {
	first: function (e) { this.top(e); return false; },
	last: function (e) { this.bottom(e); return false; },
	up: function (e) { this.up(e); return false; },
	down: function (e) { this.down(e); return false; }
};