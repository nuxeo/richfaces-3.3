if(!window.Richfaces) window.Richfaces = {};

Richfaces.ListShuttle = Class.create();

Richfaces.ListShuttle.Source = Class.create(Richfaces.ListBase);
Richfaces.ListShuttle.Target = Class.create(Richfaces.OrderingList);

Richfaces.ListShuttle.Source.SelectItem = Class.create(Richfaces.SelectItem);
Richfaces.ListShuttle.Source.SelectItem.prototype.CLASSES = {
	ROW : {
		ACTIVE   : "rich-shuttle-source-row-active",
		SELECTED : "rich-shuttle-source-row-selected",
		DISABLED : "rich-shuttle-source-row-disabled",
		NORMAL   : "rich-shuttle-source-row"
	},
	CELL : {
		ACTIVE   : "rich-shuttle-source-cell-active",
		SELECTED : "rich-shuttle-source-cell-selected",
		DISABLED : "rich-shuttle-source-cell-disabled",
		NORMAL   : "rich-shuttle-source-cell",
		BEGIN:	" rich-shuttle-source-cell-first",
		END:	" rich-shuttle-source-cell-last"
	}
}

Richfaces.ListShuttle.Target.SelectItem = Class.create(Richfaces.SelectItem);
Richfaces.ListShuttle.Target.SelectItem.prototype.CLASSES = {
	ROW : {
		ACTIVE   : "rich-shuttle-target-row-active",
		SELECTED : "rich-shuttle-target-row-selected",
		DISABLED : "rich-shuttle-target-row-disabled",
		NORMAL   : "rich-shuttle-target-row"
	},
	CELL : {
		ACTIVE   : "rich-shuttle-target-cell-active",
		SELECTED : "rich-shuttle-target-cell-selected",
		DISABLED : "rich-shuttle-target-cell-disabled",
		NORMAL   : "rich-shuttle-target-cell",
		BEGIN:	" rich-shuttle-target-cell-first",
		END:	" rich-shuttle-target-cell-last"
	}
}

Richfaces.ListShuttle.prototype = {
	initialize: function(targetList, sourceList, clientId, controlIds, switchByClick, switchByDblClick, events) {
		this.containerId = clientId;
		this["rich:destructor"] = "destroy";
		
		this.container = $(this.containerId);
		this.container.component = this;
		
		this.targetList = targetList;
		this.sourceList = sourceList;
		
		this.events = events;
		
		this.isFocused = false;
		this.wasMouseDown = false;
		this.skipBlurEvent = false;
		
		this.targetLayoutManager = targetList.layoutManager;
		this.sourceLayoutManager = sourceList.layoutManager;
		
		//for focus\blur custom events
		this.container.observe("focus", function (e) {this.focusOrBlurHandlerLS(e);}.bindAsEventListener(this));
		this.container.observe("keypress", function (e) {this.focusOrBlurHandlerLS(e);}.bindAsEventListener(this));
		this.container.observe("keydown", function (e) {this.focusOrBlurHandlerLS(e);}.bindAsEventListener(this));
		this.container.observe("mousedown", function (e) {this.focusOrBlurHandlerLS(e);}.bindAsEventListener(this));
		this.container.observe("click", function (e) {this.focusOrBlurHandlerLS(e);}.bindAsEventListener(this));
		this.container.observe("blur", function (e) {this.focusOrBlurHandlerLS(e);}.bindAsEventListener(this));
		
		if (switchByClick == "true") {
			this.targetList.shuttleTable.observe("click", function(e) {this.moveItemByClick(window.event||e, this.targetList, this.sourceList)}.bindAsEventListener(this));
			this.sourceList.shuttleTable.observe("click", function(e) {this.moveItemByClick(window.event||e, this.sourceList, this.targetList)}.bindAsEventListener(this));
			Event.stopObserving(this.sourceList.shuttleTable, "click", this.sourceList.clckHandler);
			Event.stopObserving(this.targetList.shuttleTable, "click", this.targetList.clckHandler);
		} else {
			if (switchByDblClick == "true"){
				this.targetList.shuttleTable.observe("dblclick", function(e) {this.moveItemByClick(window.event||e, this.targetList, this.sourceList)}.bindAsEventListener(this));
				this.sourceList.shuttleTable.observe("dblclick", function(e) {this.moveItemByClick(window.event||e, this.sourceList, this.targetList)}.bindAsEventListener(this));
			}
			sourceList._onclickHandler = sourceList.onclickHandler;
			sourceList.onclickHandler = function(e) { this.onclickHandler(e, sourceList); }.bindAsEventListener(this);
			targetList._onclickHandler = targetList.onclickHandler;
			targetList.onclickHandler = function(e) { this.onclickHandler(e, targetList); }.bindAsEventListener(this);
		}
		
		sourceList._onkeydownHandler = sourceList.onkeydownHandler;
		sourceList.onkeydownHandler = function(e) { this.onkeydownHandler(e, sourceList); }.bindAsEventListener(this);
		targetList._onkeydownHandler = targetList.onkeydownHandler;
		targetList.onkeydownHandler = function(e) { this.onkeydownHandler(e, targetList); }.bindAsEventListener(this);
			
		this.controlList = new Array();
		this.initControlList(clientId, controlIds);
	
		for (var e in this.events) {
			if (e && this.events[e]) {
				this.container.observe("rich:" + e.toString(), this.events[e]);
			}
		}
	},
	
	destroy: function() {
		this.container.component = null;
		this.targetList.destroy();
		this.sourceList.destroy();
	},
	
	initControlList : function(clientId, ids) {
		for (var i = 0; i < ids.length; i++) {
			var id = ids[i];
			var node = $(clientId + id[0]);
			var disNode = $(clientId + id[1]);
			if (node && disNode) { 
				node.observe("click", Richfaces.ListShuttle.HANDLERS[id[0]].bindAsEventListener(this));	
				this.controlList[i] = new Richfaces.Control(node, disNode, false, false, id[0]);
			}
		}
		//this.controlListManager();
	},
	
	controlListManager : function() {
		//this.controlsProcessing();
		this.controlsProcessing(["copy", "copyAll", "removeAll" ,"remove"], "enable");
		if (this.sourceList.shuttleItems.length < 1) 
			this.controlsProcessing(["copy", "copyAll"], "disable");
		if (this.sourceList.selectedItems.length < 1) 
			this.controlsProcessing(["copy"] , "disable");
		if (this.targetList.shuttleItems.length < 1) 
			this.controlsProcessing(["removeAll" ,"remove"], "disable");
		if (this.targetList.selectedItems.length < 1) {
			this.controlsProcessing(["remove"], "disable");
		} 	
	},
	
	onclickHandler : function(event, component) {
		component._onclickHandler(event);
		this.controlListManager();
		//Event.stop(event);     //bug: RF-2097
	},
	
	onkeydownHandler : function(event, component) {
		component._onkeydownHandler(event);
		this.controlListManager();
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
	
	moveItems : function(sourceComponent, targetComponent, items) {
		if (Richfaces.invokeEvent(this.events.onlistchange, this.container, "rich:onlistchange", {sourceItems: sourceComponent.shuttleItems, targetItems: targetComponent.shuttleItems})) {
			if (items.length > 0) {
				var length = items.length;
				for (var i = 0; items.length > 0;) {
					var item = items[i];
					this.moveItem(sourceComponent, targetComponent, item);
				} 
				this.controlListManager();
				if (this.targetList.controlListManager) {
					this.targetList.controlListManager();				
				}
				
				this.targetLayoutManager.widthSynchronization();
				this.sourceLayoutManager.widthSynchronization();
			
				this.container.fire("rich:onlistchanged", {sourceItems: sourceComponent.shuttleItems, targetItems: targetComponent.shuttleItems});
			}
		}
	},
	
	moveItem : function(sourceComponent, targetComponent, item) {
		if (!item) {
			return;
		}
		if (!(item instanceof Richfaces.SelectItem)) {
			item = sourceComponent.getSelectItemByNode(item);
		}
		if (Richfaces.browser.isFF2 && (targetComponent.shuttleTbody.rows.length == 0)) {
			this.tableUpdate(targetComponent);
			this.addItem(targetComponent, item);
			
			this.removeItem(sourceComponent, item);
			sourceComponent.shuttleTable.deleteRow(item._node.rowIndex);
		} else {
			this.addItem(targetComponent, item);
			this.removeItem(sourceComponent, item);
		}
	},
	
	removeItem : function(component, item) {
		var items = component.shuttleItems;
		component.selectedItems.remove(item._node);
		items.remove(item);
		if (item == component.activeItem) {
			component.activeItem == null;
		}
	},
	
	addItem : function(component, item) {
		item.doNormal(Richfaces.getExternalClass(item.rowIndex), component.columnsClasses);
		
		component.shuttleTbody.insertBefore(item._node, null);
		component.shuttleItems.push(item);
	},
	
	tableUpdate : function(component) {
		var table = component.shuttleTable;
		var tbody = table.tBodies[0];
		var newTbody = tbody.cloneNode(false);
		table.removeChild(tbody);
		table.appendChild(newTbody);
		component.shuttleTbody = table.tBodies[0];
	},
	
	moveItemByClick : function(event, sourceComponent, targetComponent, layoutManager) {
		if (Richfaces.invokeEvent(this.events.onlistchange, this.container, "rich:onlistchange", {sourceItems: sourceComponent.shuttleItems, targetItems: targetComponent.shuttleItems})) {
			var item = this.sourceList.getEventTargetRow(event);
			this.moveItem(sourceComponent, targetComponent, item);
			
			this.controlListManager();
			if (this.targetList.controlListManager) {
				this.targetList.controlListManager();
			}
			
			this.targetLayoutManager.widthSynchronization();
			this.sourceLayoutManager.widthSynchronization();
		
			this.container.fire("rich:onlistchanged", {sourceItems: sourceComponent.shuttleItems, targetItems: targetComponent.shuttleItems});
		}
	},
	
	copyAll : function() {
		this.container.fire("rich:oncopyallclick", {sourceItems: this.sourceList.shuttleItems, targetItems: this.targetList.shuttleItems, selection: this.sourceList.getSelection()});
		this.moveItems(this.sourceList, this.targetList, this.sourceList.shuttleItems);
	},
	
	copy : function() {
		this.container.fire("rich:oncopyclick", {sourceItems: this.sourceList.shuttleItems, targetItems: this.targetList.shuttleItems, selection: this.sourceList.getSelection()});
		this.moveItems(this.sourceList, this.targetList, this.sourceList.selectedItems);
	},
	
	removeAll : function() {
		this.container.fire("rich:onremoveallclick", {sourceItems: this.sourceList.shuttleItems, targetItems: this.targetList.shuttleItems, selection: this.targetList.getSelection()});
		this.moveItems(this.targetList, this.sourceList, this.targetList.shuttleItems);
	},
	
	remove : function() {
		this.container.fire("rich:onremoveclick", {sourceItems: this.sourceList.shuttleItems, targetItems: this.targetList.shuttleItems, selection: this.targetList.getSelection()});
		this.moveItems(this.targetList, this.sourceList, this.targetList.selectedItems);
	},
	
	up : function() {
		this.targetList.up();
	},
	
	down : function() {
		this.targetList.down();
	},
	
	top : function() {
		this.targetList.top();
	},
	
	bottom : function() {
		this.targetList.bottom();
	},
	
	focusOrBlurHandlerLS : function(e) {
		var componentID = e.target.id;
		if (e.type == "keydown") {
			var code = e.which;
			this.skipBlurEvent = false;
			this.wasKeyDown = true;
			if (Event.KEY_TAB == code) {
				if (e.shiftKey) {
					if ((componentID == this.sourceList.focusKeeper.id) && this.isFocused) {
						//blur:shift+tab keys were pressed
						this.fireOnblurEvent();
					} else {
						this.skipBlurEvent = true;
					}
				} else {
					if ((componentID == this.targetList.focusKeeper.id) && this.isFocused) {
						//blur:tab key was pressed
						this.fireOnblurEvent();
						
					} else {
						this.skipBlurEvent = true;
					}
				}
			}
		} else if (e.type == "mousedown") {
			this.skipBlurEvent = false;
			this.wasMouseDown = true;
			if (!this.isFocused) {
				this.fireOnfocusEvent();
			}
		} else if (e.type == "click") {
			this.wasMouseDown = false;
		} else if (e.type == "keypress") {
			this.wasKeyDown = false;
		} else if (e.type == "focus") {
			if (componentID == this.sourceList.focusKeeper.id && !this.wasMouseDown && !this.isFocused) {
				//focus:tab key was pressed
				this.fireOnfocusEvent();
			} else if (componentID == this.targetList.focusKeeper.id && !this.wasMouseDown && !this.isFocused) {
				//focus:shift+tab keys were pressed
				this.fireOnfocusEvent();
			}
		} else if (e.type == "blur") {
			//onblur event
			if (!this.wasMouseDown && !this.wasKeyDown && this.isFocused && !this.skipBlurEvent) {
				//blur:click component outside 
				this.fireOnblurEvent();
			}
		}
	},
	
	fireOnfocusEvent : function() {
		//LOG.warn("fireOnfocusEvent|");
		this.isFocused = true;
		this.container.fire("rich:onfocus", {});
	},
	
	fireOnblurEvent : function() {
		//LOG.warn("fireOnblurEvent|");
		this.isFocused = false;
		this.container.fire("rich:onblur", {});
	}
};

Richfaces.ListShuttle.HANDLERS = {
	copy:      function (e) { this.copy(); return false; },
	copyAll:   function (e) { this.copyAll(); return false; },
	remove:    function (e) { this.remove(); return false; },
	removeAll: function (e) { this.removeAll(); return false; }
};