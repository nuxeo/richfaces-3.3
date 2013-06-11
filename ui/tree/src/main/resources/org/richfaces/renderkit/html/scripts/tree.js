Richfaces.TreeSelectEvent = "Richfaces.TreeSelectEvent";
Richfaces.TreeExpandEvent = "Richfaces.TreeExpandEvent";
Richfaces.TreeCollapseEvent = "Richfaces.TreeCollapseEvent";

Richfaces.TreeComposite = Class.create({

	parent: null,
	
	initialize: function() {
		this.childs = [];
	},
	
	removeChild: function(child) {
		if (this.childs.length) {
			var idx = this.childs.indexOf(child);
			if (idx != -1) {
				var removedChildren = this.childs.splice(idx, 1);
				if (removedChildren) {
					for (var i = 0; i < removedChildren.length; i++) {
						removedChildren[i].parent = undefined;
					}
				}
			}
		}
	},
	
	addChild: function(child, idx) {
		var start;
		if (typeof idx != 'undefined') {
			start = idx;
		} else {
			start = this.childs.length;
		}
		
		this.childs.splice(start, 0, child);
		child.parent = this;
	},
	
	clearChildren: function() {
		for (var i = 0; i < this.childs.length; i++) {
			this.childs[i].parent = undefined;
		}
		
		this.childs = [];
	}
	
});

Tree = Class.create(Richfaces.TreeComposite);
Tree.ID_DEVIDER = ":";
Tree.ID_CONTENT = "content";
Tree.ID_CHILDS_ROW = "childs";
Tree.ID_CHILDS_TD = "td";
Tree.ID_HANDLES = "handles";
Tree.ID_HANDLE = "handle";
Tree.ID_HANDLE_IMG_EXPANDED = "img:expanded";
Tree.ID_HANDLE_IMG_COLLAPSED = "img:collapsed";
Tree.ID_ICON = "icon";
Tree.ID_TEXT = "text";
Tree.ID_MAIN_ROW = "mainRow";
Tree.CLASS_ITEM_SELECTED = "dr-tree-i-sel";
Tree.CLASS_ITEM_EXPANDED = "dr-tree-h-ic-line-exp";
Tree.CLASS_ITEM_COLLAPSED = "dr-tree-h-ic-line-clp";
Tree.CLASS_AJAX_SELECTED_LISTENER_FLAG = "ajax_selected_listener_flag";

Tree.addMethods({
	initialize: function($super, id, input, switchType, events, onAjaxSelect, options) {
		$super();
		this.childs = [];
		this.elements = {};

		this.id = id;
		this.switchType = switchType;
		this.onselect = new Function('event', (events.onselect ? events.onselect : "") + "; return true;");
		this.onexpand = new Function('event', (events.onexpand ? events.onexpand : "") + "; return true;");
		this.oncollapse = new Function('event', (events.oncollapse ? events.oncollapse : "") + "; return true;");
		this.oncontextmenu = new Function('event', (events.oncontextmenu ? events.oncontextmenu : "") + "; return true;");
		
		this.onAjaxSelect = onAjaxSelect;
		this.element = $(id);
		this.element.component=this;
		this.inputId = input;
		this.input = $(this.inputId);
		this.toggleOnClick = options.toggleOnClick;
		this.showConnectingLines = options.showConnectingLines;
		this.disableKeyboardNavigation = options.disableKeyboardNavigation;
		this.rightClickSelection = options.rightClickSelection;
		
		var options = Object.extend({
				columnCount: 0
			}, arguments[1] || {}
		);
		this.options = options;

		//var d = new Date();
		this.getElements();
		//alert(new Date().getTime() - d.getTime());

		this.selectionManager = new Tree.SelectionManager(this);
		this.selectionManager.restoreSelection();
		
		this["rich:destructor"] = "destroy";

		Event.observe(this.element, "click", function(event) {
			if (Richfaces.eventIsSynthetic(event)) {
				var treeItem = event["treeItem"];

				if (event[Richfaces.TreeSelectEvent]){
					Event.stop(event);

					var itemOnSelect = treeItem.getRichAttribute("onselected");
					itemOnSelect = new Function('event', (itemOnSelect ? itemOnSelect : "") + "; return true;");
					
					var prevSelection = this.selectionManager.activeItem;
					treeItem.toggleSelection(event);

					var selectResult = itemOnSelect(event);
					if (!selectResult) {
						event["cancelSelection"] = true;
						event["treeItem"] = prevSelection;
						if (prevSelection) {
							prevSelection.toggleSelection(event);
						} else treeItem.toggleSelection(event);
						return ;
					}

					selectResult = this.onselect(event);
					if (!selectResult) {
						event["cancelSelection"] = true;
						event["treeItem"] = prevSelection;
						if (prevSelection) {
							prevSelection.toggleSelection(event);
						} else treeItem.toggleSelection(event);
						return ;
					}

					var attr = Richfaces.getNSAttribute("ajaxselectedlistener", $(event.selectedNode + Tree.ID_DEVIDER + Tree.ID_ICON));
					if (attr) {
						this.onAjaxSelect(event);
					}
				} else if (event[Richfaces.TreeExpandEvent]){
					Event.stop(event);

					var handler = treeItem.getRichAttribute("onexpand");
					if (handler) {
						new Function('event', handler).call(Event.element(event), event);
					}

					this.onexpand(event);
				} else if (event[Richfaces.TreeCollapseEvent]){
					Event.stop(event);

					var handler = treeItem.getRichAttribute("oncollapse");
					if (handler) {
						new Function('event', handler).call(Event.element(event), event);
					}

					this.oncollapse(event);
				}
			}
		}.bindAsEventListener(this));
		
		Event.observe(this.element, "contextmenu", function(event) {
			if (this.oncontextmenu(event) === false) {
				Event.stop(event);
			}
		}.bindAsEventListener(this));
	},
	
	destroy: function() {
		this.selectionManager.destroy();
		this.element.component=null;
	},

	getElements: function(ajaxUpdate) {
		this.elements.contentTd = $(this.id + 
			Tree.ID_DEVIDER + Tree.ID_CHILDS_ROW/* + Tree.ID_DEVIDER + Tree.ID_CHILDS_TD*/);

		if (this.elements.contentTd) {
			for(var child = this.elements.contentTd.firstChild; child != null; child = child.nextSibling ) {
                if (child.nodeType == 1 && child.tagName.toLowerCase() == "table") {
                    this.addChild(new Tree.Item(child, this, ajaxUpdate));
                }
            }
            /*
            for (var i = 0; i < this.elements.contentTd.childNodes.length; i++) {
				var child = this.elements.contentTd.childNodes[i];
				if (child.nodeType == 1 && child.tagName.toLowerCase() == "table") {
					this.addChild(new Tree.Item(child.id, this, this.childs.length, this.switchType, this.toggleOnClick));
				}
			}
			*/
		}
	},
	
	refreshAfterAjax: function(nodeIds, selectedNode) {
		this.getNodeElements(nodeIds);
		this.updateSelection(selectedNode);
	},

	getNodeElements: function(nodeIds) {
		if (nodeIds) {
			for (var i = 0; i < nodeIds.length; i++ ) {
				var nodeId = nodeIds[i];
				if (nodeId == this.id) {
					this.clearChildren();	
					this.getElements(true);
				} else {
					Tree.Item.createItemForNode(nodeId, this);
				}
			}
		}
	},
	
	updateSelection: function(selectedNode) {
		this.input.value = selectedNode;
		this.selectionManager.restoreSelection();
	},
	
	showNode: function(itemNode) {
		var node = itemNode;
		var offsetTopItem = 0;
		while(node && Element.isChildOf(node,this.element)) {
			offsetTopItem += node.offsetTop;
			node = node.offsetParent;
		}
		node = itemNode;
		var nodeBottom = offsetTopItem + node.offsetHeight;
		var visibleTop = this.element.scrollTop;
		var visibleBottom = visibleTop + this.element.clientHeight;
		if (offsetTopItem < visibleTop) {
			this.element.scrollTop = offsetTopItem;
		} else if (nodeBottom > visibleBottom) {
			this.element.scrollTop = nodeBottom - this.element.clientHeight;
		}
	},

	getElement: function() {
		return $(this.id);
	}
});