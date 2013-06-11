Tree.SelectionManager = Class.create();
Tree.SelectionManager.prototype = {
	initialize: function(tree) {
		this.tree = tree;

		if (!this.tree.disableKeyboardNavigation) {
			this.eventKeyDown = this.processKeyDown.bindAsEventListener(this);
			Event.observe(document, "keydown", this.eventKeyDown);
		}

		this.eventLostFocus = this.processLostFocus.bindAsEventListener(this);
		Event.observe(document, "click", this.eventLostFocus);

		this.eventPreventLostFocus = this.processPreventLostFocus.bindAsEventListener(this);
		Event.observe(this.tree.element, "click", this.eventPreventLostFocus);
	},

	destroy: function() {
		this.activeItem = null;
		this.tree = null;

		if (this.eventKeyDown) {
			Event.stopObserving(document, "keydown", this.eventKeyDown);
		}

		Event.stopObserving(document, "click", this.eventLostFocus);
	},

	restoreSelection: function() {
		if (this.tree.input.value)
		{
			var e = $(this.tree.input.value);
			if (e) 
			{
				this.inFocus = true;
				this.setSelection(e.object);
			}
		}
	},
	
	processPreventLostFocus: function(event) {
		// RF-582 fix
		if (Richfaces.eventIsSynthetic(event)) {
			return ;
		}

		this.inFocus = true;
		this.preventLostFocus = true;
	},

	processLostFocus: function(event) {
		// RF-582 fix
		if (Richfaces.eventIsSynthetic(event)) {
			return ;
		}

		if (!this.preventLostFocus) {
			this.lostFocus();
		} else {
			this.preventLostFocus = false;
		}
	},

	lostFocus: function() {
		this.inFocus = false;
	},

	setSelection: function(item) {
		item.toggleSelection();
		this.activeItem = item;
	},

	processKeyDown: function(event) {
		if (!this.activeItem) return;
		if (!($(this.activeItem.id))) { 
			this.activeItem = null;
			this.tree.input.value = "";
			return;
		}
		var noDefault = false;
		var key = event.keyCode || event.charCode;
		switch (key) {
			case Event.KEY_UP:
				if (this.inFocus) {
					if (!event.ctrlKey && !event.shiftKey && !event.altKey) {
						
						var item = this.activeItem;
						do {
							var newItem = this.getPreviousItemForSelection(item);
							
							if (newItem && newItem != item) {
								item = newItem;
							
								if (item.toggleSelection(event)) {
									this.activeItem = item;
									item = null;
								}
							} else {
								item = null;
							}
						} while (item);
						 
					}
					noDefault = true;
				}
				break;
			case Event.KEY_DOWN:
				if (this.inFocus) {
					if (!event.ctrlKey && !event.shiftKey && !event.altKey) {

						var item = this.activeItem;
						do {
							var newItem = this.getNextItemForSelection(item);
							
							if (newItem && newItem != item) {
								item = newItem;
							
								if (item.toggleSelection(event)) {
									this.activeItem = item;
									item = null;
								}

							} else {
								item = null;
							}
						} while (item);

					}
					noDefault = true;
				}
				break;
			case Event.KEY_LEFT:
				if (this.inFocus) {
					if (!event.ctrlKey && !event.shiftKey && !event.altKey && !this.activeItem.isLeaf()) {
						this.activeItem.collapse();
					}
					noDefault = true;
				}
				break;
			case Event.KEY_RIGHT:
				if (this.inFocus) {
					if (!event.ctrlKey && !event.shiftKey && !event.altKey && !this.activeItem.isLeaf()) {
						this.activeItem.expand();
					}
					noDefault = true;
				}
				break;
			case Event.KEY_TAB:
				this.lostFocus();
		}
		if (noDefault) {
			if (event.preventBubble) event.preventBubble();
			Event.stop(event);
		}
	},

	getNextItemForSelection: function(item) {
		if (!item.isCollapsed() && item.hasChilds()) {
			return item.childs.first();
		} else {
			var next = item.next();
			if (next != item) {
				return next;
			} else {
				var parentItem = item.parent;
				while (parentItem && parentItem != this.tree) {
					var next = parentItem.next();
					if (next != parentItem) {
						return next;
					} else {
						parentItem = parentItem.parent;
					}
				}
				
				return item;
			}
		}
	},

	getPreviousItemForSelection: function(item) {
		var prev = item.previous();
		if (prev == item) {
			if (item.parent != this.tree && item.parent) {
				prev = item.parent;
			}
		} else if (!prev.isCollapsed() && prev.hasChilds()) {
			prev = prev.childs.last();
			while (!prev.isCollapsed() && prev.hasChilds()) {
				prev = prev.childs.last();
			}
		}
		return prev;
	}
}
