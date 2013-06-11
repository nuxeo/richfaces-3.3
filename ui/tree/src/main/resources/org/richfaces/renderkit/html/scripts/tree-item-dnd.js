/**
 * @author Konstantin Mishin
 * Implementation of DND in tree
 */

/**
 * Dropzone interface
 */
Object.extend(Tree.Item.prototype, DnD.Dropzone.prototype);

/**
 * Draggable Interface
 */
Object.extend(Tree.Item.prototype, DnD.Draggable.prototype);

Object.extend(Tree.Item.prototype, {
	getAcceptedTypes: function() {
		var opts = this.getDropzoneOptions();
		if (opts) {
			var result = opts["acceptedTypes"];
			if (result) {
				return result;
			}
		}
		
		return [];
	}, 

	mergeParams: function(func) {
		var treeParam = func(this.tree.getElement());
		var nodeParam = func(this.getElement());
	
		if (treeParam) {
			if (nodeParam) {
				Object.extend(treeParam, nodeParam);
			}
			
			return treeParam;		
		} else {
			return nodeParam;
		}
	},

	getDnDDefaultParams: function() {
		return this.mergeParams(DnD.getDnDDefaultParams);
	},

	getDnDDragParams: function() {
		return this.mergeParams(DnD.getDnDDragParams);
	},

	getDnDDropParams: function() {
		return this.mergeParams(DnD.getDnDDropParams);
	},

	getDropzoneOptions: function() {
		var attr = Richfaces.getNSAttribute("dropzoneoptions", this.elements.iconElement);
		if (attr) {
			return attr.parseJSON(EventHandlersWalk);
		}
		
		return null;
	},

/*	getTypeAssociations: function() {
		return this.options.typeMapping;
	} */
	drop: function(event, drag){
		//alert("type:" + drag.type+" source:"+drag.source.id+" target:"+this.id);
		drag.params[DnD.Dropzone.DROP_TARGET_ID] = this.id;

		var opts = this.getDropzoneOptions();
		if (opts && opts.parameters) {
			Object.extend(drag.params, opts.parameters);
		}
		
		this.tree.drop.call(this, event, drag);
		
		var options = this.getDropzoneOptions();
		if (options && options.ondropend) {
			options.ondropend();
		}
	},
		
	getIndicator: function() {
		var opts = this.getDraggableOptions();
		var indicatorId = opts ? opts.dragIndicator : null;
		var indicator = $(indicatorId);
		if (!indicator) {
			indicator = this.getOrCreateDefaultIndicator();
		}		
		return indicator;
	},
	
	ondragstart : function(event, drag) {
		//drag.params = this.options.parameters;		
		drag.params[this.id] = this.id;

		var opts = this.getDraggableOptions();
		if (opts && opts.parameters) {
			Object.extend(drag.params, opts.parameters);
		}

		this.dragEnter(event);
	},
	
	ondragend : function(event, drag) {
		this.dragStarted = false;
	},
	
	getDraggableOptions: function() {
		var drag = window.drag;
		
		if (drag && drag.treeDraggableOptions) {
			
			return drag.treeDraggableOptions;		
		} else {
			var opts = this._getDraggableOptions();

			if (drag) {
				drag.treeDraggableOptions = opts;
			}
			
			return opts;
		}
	},
	
	_getDraggableOptions: function() {
		var attr = Richfaces.getNSAttribute("draggableoptions", this.elements.iconElement);
		if (attr) {
			return attr.parseJSON(EventHandlersWalk);
		}
		
		return null;
	},
	
	getContentType: function() {
		var opts = this.getDraggableOptions();
		if (opts) {
			return opts["dragType"];
		}
		
		return "";
	},

	getElement: function() {
		//returns <tr> holding node without children
		return this.elements.textElement.parentNode;
	}
});