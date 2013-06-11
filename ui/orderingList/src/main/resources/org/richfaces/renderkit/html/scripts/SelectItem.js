if (!window.Richfaces) { window.Richfaces = {}; }

Richfaces.getExternalClass = function(classes, index) {
	if (classes) {
		var len = classes.length;
		while (index >= len) {
			index -= len;
		} 
		return (classes[index]) ? classes[index] : "";
	}
	return "";
}
	
Richfaces.SelectItem = Class.create();

Richfaces.SelectItem.findElement = function(elt, id) {
	var e = elt;

	if (e) {
		if (e.id == id) {
			return e;
		}

		e = e.firstChild;
		while (e) {
			var result = arguments.callee(e, id);
			
			if (result) {
				return result;
			}
			
			e = e.nextSibling;
		}
	}

	return null;
};

Richfaces.SelectItem.prototype = {
	initialize : function(label, id, node) {
		this._label = label;
		this._node = node; 
		
		this._node.item = this;
		this._id = id;
		
		//XXX 2 optimize
		this.input = Richfaces.SelectItem.findElement(node, node.id + "StateInput");
		
		this.selected = /^s/.test(this.input.value);
		this.active = /^s?a/.test(this.input.value);
	},
	
	destroy: function() {
		this._node.item = null;
	},
	
	doActive : function(rowStoredClass, cellStoredClasses) {
		var classes = this.CLASSES; 
		var row = this._node;
		var rowClass = classes.ROW.ACTIVE;
		var cellClass = classes.CELL.ACTIVE;
		if (this.isSelected()) {
			rowClass = classes.ROW.SELECTED + " " + classes.ROW.ACTIVE;
			cellClass = classes.CELL.SELECTED + " " + classes.CELL.ACTIVE;
		}
		this.changeClass(row, rowClass, cellClass, rowStoredClass, cellStoredClasses);

		this.active = true;
		
		this.saveState();
	},
	
	doSelect : function(rowStoredClass, cellStoredClasses) {
		var row = this._node;
		var classes = this.CLASSES; 

		this.changeClass(row, classes.ROW.SELECTED, classes.CELL.SELECTED, rowStoredClass, cellStoredClasses);

		this.selected = true;
		
		this.saveState();
	},
	
	doNormal : function(rowStoredClass, cellStoredClasses) {
		var row = this._node;
		var classes = this.CLASSES; 

		this.changeClass(row, classes.ROW.NORMAL, classes.CELL.NORMAL, rowStoredClass, cellStoredClasses);

		this.active = false;
		this.selected = false;
		
		this.saveState();
	},
	
	isSelected : function() {
		return this.selected;
	},
	
	isActive : function() {
		return this.active;
	},
	
	changeClass : function(row, rowClassName, cellClassName, rowStoredClass, cellStoredClasses) {
			row.className = rowStoredClass + " " + rowClassName;
			var cells = row.cells;
		for (var i = 0; i < cells.length; i++) {
			var cell = cells[i];
			cell.className = Richfaces.getExternalClass(cellStoredClasses, cell.cellIndex) + " " + cellClassName;
		}
	},
	
	/*addClass : function(row, classNameRow, classNameCell) {
		if (row.addClassName) {
			row.addClassName(classNameRow);
		} else {
			Element.addClassName(row, classNameRow);
		}
		
		var cells = row.cells;
		for (var i = 0; i < cells.length; i++) {
			var cell = cells[i];
			if (cell.addClassName) {
				cell.addClassName(classNameCell);
			} else {
				Element.addClassName(cell, classNameCell);
			}
		}
	},
	
	removeClass : function(row, classNameRow, classNameCell) {
		if (row.removeClassName) {
			row.removeClassName(classNameRow);
		} else {
			Element.removeClassName(row, classNameRow);
		}
		var cells = row.cells;
		for (var i = 0; i < cells.length; i++) {
			var cell = cells[i];
			
			if (cell.removeClassName) {
				cell.removeClassName(classNameCell);
			} else {
				Element.removeClassName(cell, classNameCell);
			}
		}
	},*/
	
	saveState: function() {
		var regex = /^s?a?/;
		
		if (this.selected && this.active) {
			this.input.value = this.input.value.replace(regex, 'sa');
		} else if (this.selected) {
			this.input.value = this.input.value.replace(regex, 's');
		} else if (this.active) {
			this.input.value = this.input.value.replace(regex, 'a');
		} else {
			this.input.value = this.input.value.replace(regex, '');
		}
	}
}
