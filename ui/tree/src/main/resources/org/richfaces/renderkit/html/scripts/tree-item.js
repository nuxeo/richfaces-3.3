Tree.Item = Class.create(Richfaces.TreeComposite);

Tree.Item.findComponent = function(elt, axisFunction) {
	var component;
	
	while (elt && !((component = elt.object) instanceof Tree.Item)) {
		if (axisFunction) {
			elt = axisFunction(elt);
		} else {
			elt = elt.parentNode;
		}
	}
	
	return component;
};

Tree.Item.createItemForNode = function() {
	var getComponentIndex = function(node) {
		var idx = 0;
		var n = node;
	
		while (n) {
			if (n.object instanceof Tree.Item) {
				idx++;
			}
			
			n = n.previousSibling;
			
		};
		
		return idx;
	};
	
	var findParentComponent = function(node) {
		var n = node.parentNode;
		if (n) {
			n = Richfaces.previous(n);
		}
		
		if (n) {
			return n.object;
		}
	};
	
	return function(node, tree) {
		var replacedNode = $(node);
		
		var pNode = findParentComponent(replacedNode) || tree;
		var idx = getComponentIndex(replacedNode);
		
		var item = new Tree.Item(replacedNode, tree, true);
		
		pNode.addChild(item, idx);

		return item;
	};
}();

Tree.Item.addMethods({
	initialize: function($super, id, tree, ajaxUpdate) {
		$super();
		
		this.tree = tree;
		this.elements = {};
		
		this["rich:destructor"] = "destroy";
		
		var element;
		
		if (typeof id == 'string') {
			element = $(id);
			this.id = id;
		} else {
			element = id;
			this.id = element.id;
		}
		
		element.component = this;
		element.object = this;
		
		var rows = element.rows;
		this.elements.itemElement = element;
		this.elements.mainRow = rows[0];
		
		var sibling = element.nextSibling;
		var siblingId = this.id + Tree.ID_DEVIDER + Tree.ID_CHILDS_ROW;
		while (sibling && sibling.id != siblingId) {
			sibling = sibling.nextSibling
		};

		this.elements.childrenRow = sibling;
		
		var handlesId = this.id + Tree.ID_DEVIDER + Tree.ID_HANDLES;
		var iconId = this.id + Tree.ID_DEVIDER + Tree.ID_ICON;
		var textId = this.id + Tree.ID_DEVIDER + Tree.ID_TEXT;
		
		var childsTd = Richfaces.next(element);
		this.createSubNodes(childsTd);

		if (ajaxUpdate && this.tree.showConnectingLines) {
			var cell = element.rows[0].cells[0];
			
			try {
				if (cell.style && cell.style.removeExpression) {
					cell.style.backgroundImage = cell.currentStyle.backgroundImage;
					cell.style.removeExpression('backgroundImage');
				}
		
				if (childsTd) {
					if (childsTd.style && childsTd.style.removeExpression) {
						childsTd.style.backgroundImage = childsTd.currentStyle.backgroundImage;
						childsTd.style.removeExpression('backgroundImage');
					}
				}
			} catch (e) {
				//IE8 throws "not implemented" for expression-management functions 
			}
		}

		var handles = null;
		var cells = this.elements.mainRow.cells;
		if ("NETSCAPE" == RichFaces.navigatorType()) {
			for (var i = 0; i < cells.length; i++) {
				if (cells[i].id == iconId) {
					this.elements.iconElement = cells[i];
				} else if (cells[i].id == textId) {
					this.elements.textElement = cells[i];
				} else if (cells[i].id == handlesId) {
					handles = cells[i];
				}
				if (this.elements.iconElement && this.elements.textElement && handles) {
					break;
				}
			}
		} else {
			//seeking by id seems to be miserably slow in IE than by index
			this.elements.iconElement = cells[iconId];
			this.elements.textElement = cells[textId];
			handles = cells[handlesId];
		}

		var handleId = this.id + Tree.ID_DEVIDER + Tree.ID_HANDLE;
		var handleImgExpandedId = handleId + Tree.ID_DEVIDER + Tree.ID_HANDLE_IMG_EXPANDED;
		var handleImgCollapsedId = handleId + Tree.ID_DEVIDER + Tree.ID_HANDLE_IMG_COLLAPSED;

		this.elements.handle = Richfaces.firstDescendant(Richfaces.firstDescendant(handles));//Element.firstDescendant(Element.firstDescendant(handles));
		if (this.elements.handle.id == handleId) {
			this.elements.handleImgCollapsed = Richfaces.firstDescendant(this.elements.handle);
			this.elements.handleImgExpanded = Richfaces.next(this.elements.handleImgCollapsed);
			
			if (this.elements.handleImgExpanded.id != handleImgExpandedId || 
					this.elements.handleImgCollapsed.id != handleImgCollapsedId) {
			
				throw "Script inconsistency detected! Please inform developers...";
			}
		} else {
			this.elements.handle = null;
		}
		
		this.eventSelectionClick = this.toggleSelection.bindAsEventListener(this);
		this.eventMouseOut = this.processMouseOut.bindAsEventListener(this);
		this.eventMouseOver = this.processMouseOver.bindAsEventListener(this);
		
		var dragOpts = this._getDraggableOptions();
		if(dragOpts) {
			this.enableDraggableCursors(dragOpts.grab, dragOpts.grabbing);
		}	
			
		var dropOpts = this.getDropzoneOptions();
		if	(dropOpts) {
			this.enableDropzoneCursors(dropOpts.acceptCursor, dropOpts.rejectCursor);
		}

		this.observeEvents();
		
		this.previousTextClassNames = null;
		this.highlightedTextClassNames = null;
		this.selectedTextClassNames = null;
	},

	destroy: function() {
		if (this.parent) {
			this.parent.removeChild(this);
		}
		
		if (this.elements) {
			//free circular reference. 
			//
			//TODO: contents of this.elements collection can still be used after item destruction, 
			//free them in future
			this.elements.itemElement = undefined;
		}
		
		this.clearChildren();
		
		if (this == this.tree.selectionManager.activeItem) {
			this.tree.selectionManager.activeItem = null;
		}
	},

	observeEvents: function() {
		if (this.elements.iconElement) {
			var contextMenu = Richfaces.getNSAttribute("oncontextmenu", this.elements.iconElement);
			if (contextMenu && contextMenu.length > 0) {
				this.onContextMenu = new Function("event", contextMenu + "; return true;").bindAsEventListener(this);
			}

			Event.observe(this.elements.iconElement, "mousedown", this.eventSelectionClick);
			Event.observe(this.elements.iconElement, "mouseout", this.eventMouseOut);
			Event.observe(this.elements.iconElement, "mouseover", this.eventMouseOver);
			if (this.onContextMenu) {
				Event.observe(this.elements.iconElement, "contextmenu", this.onContextMenu);
			}			
		}

		if (this.elements.textElement)
		{
			Event.observe(this.elements.textElement, "mousedown", this.eventSelectionClick);
			Event.observe(this.elements.textElement, "mouseout", this.eventMouseOut);
			Event.observe(this.elements.textElement, "mouseover", this.eventMouseOver);
			if (this.onContextMenu) {
				Event.observe(this.elements.textElement, "contextmenu", this.onContextMenu);
			}			
		}

		if (this.tree.switchType=="client" && this.hasChilds()) {
			this.eventCollapsionClick = this.toggleCollapsion.bindAsEventListener(this);
			
			var handleElt = this.tree.toggleOnClick ? this.elements.mainRow : this.elements.handle;
			Event.observe(handleElt, "click", this.eventCollapsionClick);
		}
	},

	createSubNodes: function(childrenTd) {
		if (childrenTd) {
			var child = childrenTd.firstChild;
			while ( child != null )
			{
                if (child.nodeType == 1 && child.tagName.toLowerCase() == "table") {
                	if (child.object instanceof Tree.Item) {
                    	var item = child.object;
                		this.addChild(item);
                	} else {
                		this.addChild(new Tree.Item(child, this.tree));
                	}
                }
                child = child.nextSibling;
            }
		}
	},

	fireExpansionEvent: function() {
		var props = new Object();
		props[Richfaces.TreeExpandEvent] = true;
		props["expandedNode"] = this.id;
		props["treeItem"] = this;
		
		var event = Richfaces.createEvent("click", this.tree.element, null, props);
		try {
			event.fire();
		} finally {
			event.destroy();
		}
	},

	fireCollapsionEvent: function() {
		var props = new Object();
		props[Richfaces.TreeCollapseEvent] = true;
		props["collapsedNode"] = this.id;
		props["treeItem"] = this;
		
		var event = Richfaces.createEvent("click", this.tree.element, null, props);
		try {
			event.fire();
		} finally {
			event.destroy();
		}
	},

	toggleCollapsion: function() {

		var row=this.id + Tree.ID_DEVIDER + Tree.ID_CHILDS_ROW; 
		if (this.hasChilds()) Element.toggle(row);

		// Rerender main row to avoid bad representation in Opera and Konqueror/Safari
		var e = this.elements.mainRow;
		if (e) {
			Element.hide(e);
			Element.show(e);
		}
		
		var nodeStateInput = $(this.id + "NodeExpanded");

		var eIcon = this.elements.iconElement;

		if (this.isCollapsed()) {
			Element.hide(this.elements.handleImgExpanded);
			Element.show(this.elements.handleImgCollapsed);
			if( eIcon && Element.hasClassName(eIcon, Tree.CLASS_ITEM_EXPANDED)) {
				Element.removeClassName(eIcon, Tree.CLASS_ITEM_EXPANDED);
				Element.addClassName(eIcon, Tree.CLASS_ITEM_COLLAPSED);
			}

			if (nodeStateInput) {
				nodeStateInput.value = "false";
			}

			this.fireCollapsionEvent();
		} else {
			Element.show(this.elements.handleImgExpanded);
			Element.hide(this.elements.handleImgCollapsed);
			if(eIcon && Element.hasClassName(eIcon, Tree.CLASS_ITEM_COLLAPSED)) {
				Element.removeClassName(eIcon, Tree.CLASS_ITEM_COLLAPSED);
				Element.addClassName(eIcon, Tree.CLASS_ITEM_EXPANDED);
			}

			if (nodeStateInput) {
				nodeStateInput.value = "true";
			}

			this.fireExpansionEvent();
		}

	},

	getRichAttribute: function(name) {
		return Richfaces.getNSAttribute(name, this.elements.iconElement);
	},

	collapse: function() {
		if (!this.isCollapsed() && this.tree.switchType!="client") {
			var handleElt = $(this.tree.toggleOnClick ? this.elements.mainRow : this.elements.handle);
			if (handleElt) {
				handleElt.onclick();
			}
		} else if (this.hasChilds() && !this.isCollapsed()) {
			Element.hide(this.elements.childrenRow);
			Element.hide(this.elements.handleImgExpanded);
			Element.show(this.elements.handleImgCollapsed);
			var eIcon = this.elements.iconElement;		
			if(eIcon && Element.hasClassName(eIcon, Tree.CLASS_ITEM_EXPANDED)) {
				Element.removeClassName(eIcon, Tree.CLASS_ITEM_EXPANDED);
				Element.addClassName(eIcon, Tree.CLASS_ITEM_COLLAPSED);
			}

			var nodeStateInput = $(this.id + "NodeExpanded");
			if (nodeStateInput) {
				nodeStateInput.value = "false";
			}
			
			this.fireCollapsionEvent();
		}
	},

	expand: function() {
		if (this.isCollapsed() && this.tree.switchType!="client") {
			var handleElt = $(this.tree.toggleOnClick ? this.elements.mainRow : this.elements.handle);
			if (handleElt) {
				handleElt.onclick();
			}
		} else if (this.hasChilds() && this.isCollapsed()) {
			Element.show(this.elements.childrenRow);
			Element.show(this.elements.handleImgExpanded);
			Element.hide(this.elements.handleImgCollapsed);
			var eIcon = this.elements.iconElement;
			if(eIcon && Element.hasClassName(eIcon, Tree.CLASS_ITEM_COLLAPSED)) {
				Element.removeClassName(eIcon, Tree.CLASS_ITEM_COLLAPSED);
				Element.addClassName(eIcon, Tree.CLASS_ITEM_EXPANDED);
			}

			var nodeStateInput = $(this.id + "NodeExpanded");
			if (nodeStateInput) {
				nodeStateInput.value = "true";
			}
			
			this.fireExpansionEvent();
		}
	},

	isCollapsed: function() {
		var e = this.elements.childrenRow;
		if (e) {
			return e.style.display == "none";
		} else {
			return true;
		}
	},

	processMouseOut: function(e) {
		if (this.isMouseIn) {
			this.isMouseIn = false;
			var eText = this.elements.textElement;
			eText.className = this.previousTextClassNames;
			this.previousTextClassNames = null;
			if (window.drag){
				this.dragLeave(e);
			}
		}
	},

	processMouseOver: function(e) {
		if(!this.isMouseIn) {
			this.isMouseIn = true;
			var eText = this.elements.textElement;
			this.previousTextClassNames = eText.className;
			if (!this.highlightedTextClassNames)
			{
				this.highlightedTextClassNames = Richfaces.getNSAttribute("highlightedclass", eText);
			}
			if (this.highlightedTextClassNames) {
				eText.className += " "+this.highlightedTextClassNames;
			}
			if (window.drag) {
				this.dragEnter(e);
			}
		}
	},

	toggleSelection: function(e) {
		if (e && !e[Richfaces.TreeSelectEvent] && !Richfaces.eventIsSynthetic(e)) {
			if (e && e.type == "mousedown" /* can be keydown */) {
				if((this.tree.rightClickSelection && Event.isRightClick(e)) || Event.isLeftClick(e)) {
				  var src = Event.element(e);
				   if(src.tagName && 
				   		/^(input|select|option|button|textarea)$/i.test(src.tagName))
							return;

					if (this.isDragEnabled()) {
						Event.stop(e);
					}
				} else {
					//do not process non-left clicks
					return ;
				}
			}

			var props = new Object();
			props[Richfaces.TreeSelectEvent] = true;
			props["originatingEventType"] = e.type;
			props["treeItem"] = this;
			props["selectedNode"] = this.id;

			var evt = Richfaces.createEvent("click", this.tree.element, null, props);
			try {
				evt.fire();
			} finally {
				evt.destroy();
			}

			return !evt.event["cancelSelection"];
		} else {
			var activeItem = this.tree.selectionManager.activeItem;
			if (activeItem) {
				activeItem.deselect();
			}

			/*
			var attr = this.elements.text.attributes;
			var s = "";
			for (var i = 0; i < attr.length; i++) {
				s += attr[i].nodeName + ": " + attr[i].nodeValue + ";  ";
			}

			alert(s);
			*/
			if (!e || e["treeItem"])
			{
				var eText = this.elements.textElement;
				if (!this.selectedTextClassNames)
				{
					this.selectedTextClassNames = Richfaces.getNSAttribute("selectedclass", eText);
				}
				if (this.selectedTextClassNames) {
					var classNames = this.selectedTextClassNames.split(' ')
					for (var i = 0; i < classNames.length; i++) {
						Element.addClassName(eText, classNames[i]);
					}
					if (this.previousTextClassNames && this.previousTextClassNames.indexOf(this.selectedTextClassNames)==-1)
					{
						this.previousTextClassNames += " " + this.selectedTextClassNames;
					}
					this.tree.input.value = this.id;
					this.tree.selectionManager.activeItem = this;
	
					if (this.tree.options.onSelection) this.tree.options.onSelection(this.id);
					this.tree.showNode(eText.parentNode);
				}
			} else
			{
				this.tree.input.value = "";
				this.tree.selectionManager.activeItem = null;				
			}

			if (e && e["originatingEventType"] == "mousedown" /* can be keydown */) {
				this.startDrag(e);
			}

			return true;
		}
	},

	isSelected: function() {
		return Element.hasClassName(this.elements.textElement, Tree.CLASS_ITEM_SELECTED);
	},

	deselect: function() {
		var eText = this.elements.textElement;
		if (this.selectedTextClassNames) {
			var prevClassesSplit = this.previousTextClassNames ? 
					this.previousTextClassNames.split(' ') : undefined;
			
			var classNames = this.selectedTextClassNames.split(' ')
			for (var i = 0; i < classNames.length; i++) {
				var className = classNames[i];
				
				Element.removeClassName(eText, className);
				if (prevClassesSplit) {
					prevClassesSplit = prevClassesSplit.without(className);
				}
			}
			
			if (prevClassesSplit) {
				this.previousTextClassNames = prevClassesSplit.join(' ');
			}
		}
	},

	_nextForAxis: function(axisFunction) {
		var item;
		
		if (this.elements && this.elements.itemElement) {
			item = Tree.Item.findComponent(axisFunction(this.elements.itemElement), axisFunction);
		}
		
		return item || this;
	},
	
	next: function() {
		return this._nextForAxis(Richfaces.next);
	},

	previous: function() {
		return this._nextForAxis(Richfaces.previous);
	},

	hasChilds: function() {
		return this.childs && this.childs.length > 0;
	},
	
	getElement: function() {
		return $(this.id);		
	},
	
	isLeaf: function() {
		return !this.elements.handle;		
	}
	
});
