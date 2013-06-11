ClientUI.controls.grid.Selection = Class.create({
	initialize: function() {
		this.ranges = [];
	},

	addId: function(id) {
		id = parseInt(id);
		if(this.isSelectedId(id))
			return;
		var i = 0;
		while(i < this.ranges.length && id >= this.ranges[i++].indexes[1]);
		i--;
		if(this.ranges[i-1] && id==(this.ranges[i-1].indexes[1]+1) ) {
			if(id==(this.ranges[i].indexes[0]-1)) {
				this.ranges[i-1].indexes[1] = this.ranges[i].indexes[1];
				this.removeRange(i);			
			} else {
				this.ranges[i-1].indexes[1]++;			
			}
		} else {
			if(this.ranges[i]){
				if(this.ranges[i] && id==(this.ranges[i].indexes[0]-1)) {
					this.ranges[i].indexes[0]--;			
				} else {
					if(id==(this.ranges[i].indexes[1]+1)){
						this.ranges[i].indexes[1]++;			
					} else {
						if(id<this.ranges[i].indexes[1]){
							this.addRange(i, new ClientUI.controls.grid.Range(id, id));					
						} else {
							this.addRange(i + 1, new ClientUI.controls.grid.Range(id, id));					
						}
					}
				}	
			} else {
				this.addRange(i, new ClientUI.controls.grid.Range(id, id));					
			}	
		} 			
	},

	addRange: function(index, range) {
		var i = this.ranges.push(range) - 2;
		if(index >= 0) {
			while(i>=index)
				this.ranges[i+1] = this.ranges[i--];
			this.ranges[i+1] = range;
		}
	},

	removeRange: function(index) {
		var i = index + 1;
		while(i!=this.ranges.length)
			this.ranges[i-1] = this.ranges[i++];
		this.ranges.pop();
	},

	isSelectedId: function(id) {
		var i = 0;
		while(i < this.ranges.length && id >= this.ranges[i].indexes[0]) {
			if(id >= this.ranges[i].indexes[0] && id <= this.ranges[i].indexes[1]) {
				return true;
			} else {
				i++;
			}
		}
		return false;
	},

	getSelectedIdsQuantity: function() {
		var number = 0;
		for (var i = 0; i < this.ranges.length; i++) {
			number+= this.ranges[i].size();
		}
		return number;
	},
	
	size: function () {
		return this.getSelectedIdsQuantity();
	},
	
	removeId: function(id) {
		id = parseInt(id);
		if(!this.isSelectedId(id))
			return;
		var i = 0;
		while(i < this.ranges.length && id > this.ranges[i++].indexes[1]);
		i--;
		if(this.ranges[i]) {
			if(id==(this.ranges[i].indexes[1]) ) {
				if(id==(this.ranges[i].indexes[0])){
					this.removeRange(i);			
				} else {
					this.ranges[i].indexes[1]--;			
				}
			} else {
				if(id==(this.ranges[i].indexes[0])){
					this.ranges[i].indexes[0]++;			
				} else {
				this.addRange(i+1, new ClientUI.controls.grid.Range(id+1, this.ranges[i].indexes[1]));			
				this.ranges[i].indexes[1] = id-1;
				}
			}
		}		
	},

	getState: function() {
		var s = this.clone();
		return {
			size: function() {
					return s.size();
			},
			
			each: function(iterator) {
				s.each(iterator);
  			},
			
			isSelected: function(id) {
				return s.isSelectedId(id);
  			}
		};
	},

	clone: function() {
		var ret =  Object.extend(new Object(),this);
		ret.ranges = new Array(ret.ranges.length);
		for (var i = 0; i < ret.ranges.length; i++) {
			ret.ranges[i] = this.ranges[i].clone();
		}		
 		return ret;
 	},

	each: function(iterator) {
		for (var i = 0; i < this.ranges.length; i++) {
			this.ranges[i].each(iterator);					
		}
 	},
  	
  	getRanges: function() {
		return this.ranges;
	},

	setRanges: function(ranges) {
		this.ranges = ranges;
	},
	
	initRanges: function(rangeStrRArray) {
		if(rangeStrRArray.length == 0) {
			this.ranges = [];
			return;
		}
		this.ranges = new Array(rangeStrRArray.length);
		var indexStrRArray;
		for(var i = 0; i < this.ranges.length; i++) {
			indexStrRArray = rangeStrRArray[i].split(",");
			this.ranges[i] = new ClientUI.controls.grid.Range(parseInt(indexStrRArray[0]), parseInt(indexStrRArray[1]));
		}
		
	}, 

	inspectRanges: function() {
		var ranges = this.getRanges();
		var ret = "";
		ranges.each( function(r) { ret += r.inspect(); } );
		return ret;
	} 
});

ClientUI.controls.grid.Range = Class.create({
	initialize: function(startIndex, endIndex) {
		this.indexes = [startIndex, endIndex];
	},

	inspect: function() {
		return this.indexes[0] + "," + this.indexes[1] + ";";
	},
	toString: function() {
		return this.inspect();
	},
	
	size: function() {
		return this.indexes[1] - this.indexes[0] + 1;;
	},
	
	each: function(iterator) {
		var j = this.indexes[0];
		while(j <= this.indexes[1]) {
      		iterator(j++);					
		}
  	},
	
	clone: function() {
		var ret = Object.extend(new Object(),this);
		ret.indexes = this.indexes.clone();
		return ret;
  	}
});

ClientUI.controls.grid.SelectionManager = Class.create({
	initialize: function(grid) {
		this.grid = grid;
		this.selectionFlag;
		this.firstIndex;
		this.activeRow = -1;
		var gridElement = grid.getElement();
		this.prefix = gridElement.id;
		this.selection = new ClientUI.controls.grid.Selection();

		this.inputElement = grid.options.selectionInput;
		this.isSingleMode = "single" == grid.options.selectionMode;
		this.onselectionchange = grid.options.onselectionchange;
		this.selectedClass = grid.options.selectedClass;
		this.activeClass = grid.options.activeClass;

		this.restoreState();
		this.setListeners();
		this.eventKeyPress = this.processKeyDown.bindAsEventListener(this);
		Event.observe(document, "keydown", this.eventKeyPress);
		A4J.AJAX.AddListener({
			onafterajax: function(req, event, data) {
				if(!$(this.prefix + ":n")) {
					Event.stopObserving(document, "keydown", this.eventKeyPress);		
				}
			}.bind(this)
		});
		if (document.selection) {
			Event.observe(gridElement, "click", this.resetSelection.bindAsEventListener(this));
		}

		this.eventLostFocus = this.processLostFocus.bindAsEventListener(this);
		Event.observe(document, "click", this.eventLostFocus);

		this.eventPreventLostFocus = this.processPreventLostFocus.bindAsEventListener(this);
		Event.observe(gridElement, "click", this.eventPreventLostFocus);
		
		
		
//		var selChangeHandler = this.grid.options.onselectionchange;		
//		if (selChangeHandler) {
//			IL.Event.observe(this.grid.element, "selectionchange", selChangeHandler);
//		}
//		var deleteHandler = this.grid.options.onDeleted;		
//		if (deleteHandler) {
//			IL.Event.observe(this.grid.element, "delete", deleteHandler);
//		}

	},

	restoreState: function() {
		this.selectionFlag = null;
		var selStrAr = $(this.inputElement).value.split(";");
		var activeRow = NaN;
		while (selStrAr.length != 0 && selStrAr[selStrAr.length - 1].indexOf(",") == -1 &&
			isNaN(activeRow = Number(selStrAr.pop())));
		if (!isNaN(activeRow)) {
			this.setActiveRow(activeRow);
		}
		this.selection.initRanges(selStrAr);
	//	this.firstIndex = Number($(this.prefix + ":f").rows[0].id.split(this.prefix)[1].split(":")[2]);;
		var i = 0;
		var j;
		while(i < this.selection.ranges.length) {
			j = this.selection.ranges[i].indexes[0];
			while(j <= this.selection.ranges[i].indexes[1]) {
				var fElement = $(this.prefix + ":f:" + j);
				var nElement = $(this.prefix + ":n:" + j);
				Element.addClassName(fElement, "dr-sdt-row-selected");
				Element.addClassName(nElement, "dr-sdt-row-selected");
				Element.addClassName(fElement, "rich-sdt-row-selected");
				Element.addClassName(nElement, "rich-sdt-row-selected");
				Element.addClassName(fElement, this.selectedClass);
				Element.addClassName(nElement, this.selectedClass);
				j++;
			}
			i++;
		}
		this.oldState = this.selection.getState();
	},
	
	setListeners: function() {
		var frows = $(this.prefix + ":f").rows;
		var nrows = $(this.prefix + ":n").rows;
		this.rowCount = nrows.length;
		var rowIndex;
		for(var i = 0; i < this.rowCount; i++) {
			rowIndex = Number(nrows[i].id.split(this.prefix)[1].split(":")[2]);
			this.addListener(frows[i], rowIndex);
			this.addListener(nrows[i], rowIndex);
		}
	},
	
	addListener: function(tr, rowIndex) {
		if (tr) {
			var listener = this.processClick.bindAsEventListener(this, rowIndex);
			var cells = tr.cells;
			for(var i = 0; i < cells.length; i++) {
				Utils.DOM.Event.observe(cells[i], "click", listener);	
			}
		}
	},
	
/*	getGridSelection: function() {
		return this.selection.getRanges();
	},*/

	processPreventLostFocus: function() {
		this.inFocus = true;
		this.preventLostFocus = true;
	},

	processLostFocus: function() {
		if (!this.preventLostFocus) {
			this.lostFocus();
		} else {
			this.preventLostFocus = false;
		}
	},

	lostFocus: function() {
		this.inFocus = false;
	},

	processKeyDown: function(event) {
		if ($(this.prefix + ":n").rows.length > 0) {
			if(!event.shiftKey) {
				this.shiftRow = null;
			}		
			var range, rowIndex;
			var activeRow = this.activeRow;
			var noDefault = false;
			this.firstIndex = Number($(this.prefix + ":n").rows[0].id.split(this.prefix)[1].split(":")[2]);;
			switch (event.keyCode || event.charCode) {
				case Event.KEY_UP:
					if (this.inFocus && activeRow != null) {
						if(this.firstIndex != activeRow) {
							rowIndex = (this.rowCount + activeRow - 1) % this.rowCount;		
							if (this.isSingleMode || (!event.ctrlKey && !event.shiftKey)) {
								this.selectionFlag = "x";
								range = [rowIndex, rowIndex];
								this.setSelection(range);		
							} else if (!event.ctrlKey && event.shiftKey) {
								if(!this.shiftRow) {
									this.shiftRow = this.activeRow;
								}
								if(this.shiftRow >= this.activeRow) {
									this.addRowToSelection(rowIndex);						
								} else {
									this.removeRowFromSelection(activeRow);						
								}
							}
							noDefault = true;
							this.setActiveRow(rowIndex);
						} else {
							this.grid.getBody().showRow("up");					
						}
					}
					break;
				case Event.KEY_DOWN:
					if (this.inFocus && activeRow != null) {
						rowIndex = (activeRow + 1) % this.rowCount;		
						if(this.firstIndex != rowIndex) {
							if (this.isSingleMode || (!event.ctrlKey && !event.shiftKey)) {
								this.selectionFlag = "x";
								range = [rowIndex, rowIndex];
								this.setSelection(range);		
							} else if (!event.ctrlKey && event.shiftKey) {
								if(!this.shiftRow) {
									this.shiftRow = this.activeRow;
								}
								if(this.shiftRow <= this.activeRow) {
									this.addRowToSelection(rowIndex);						
								} else {
									this.removeRowFromSelection(activeRow);						
								}
							}
							noDefault = true;
							this.setActiveRow(rowIndex);
						} else {
							this.grid.getBody().showRow("down");					
						}
					}
					break;
				case 65: case 97:								// Ctrl-A
					if (this.inFocus && event.ctrlKey && !this.isSingleMode) {
						this.selectionFlag = "a";
						for (var i = 0; i <  this.rowCount; i++) {
							this.addRowToSelection(i);
						}
						noDefault = true;
					}
					break;
				case Event.KEY_TAB:
					this.lostFocus();
			}
			if (noDefault) {
				this.grid.getBody().showRow(this.activeRow);
				this.selectionChanged(event);			
				if (event.preventBubble) event.preventBubble();
				Event.stop(event);
			}
		}
	},

	processClick: function(event, rowIndex) {
		if(!event.shiftKey) {
			this.shiftRow = null;
		}		
		var range;
		if ( event.shiftKey && !event.ctrlKey && !event.altKey && !this.isSingleMode) {
			this.firstIndex = Number($(this.prefix + ":n").rows[0].id.split(this.prefix)[1].split(":")[2]);;
			this.selectionFlag = "x";
			if(!this.shiftRow) {
				this.shiftRow = this.activeRow;
			}
			this.startRow = this.shiftRow;
			if (((this.startRow <= rowIndex) && (this.firstIndex <= this.startRow || rowIndex < this.firstIndex))
				|| (this.startRow > rowIndex && this.firstIndex < this.startRow && rowIndex < this.firstIndex)) {
				this.endRow = rowIndex;
			} else {
				this.endRow = this.startRow;
				this.startRow = rowIndex;
			}
			range = [this.startRow, this.endRow];
			this.setSelection(range);		
		} else if (!event.shiftKey &&  event.ctrlKey && !event.altKey && !this.isSingleMode) {
			if (this.selection.isSelectedId(rowIndex)) {
				this.removeRowFromSelection(rowIndex);
			} else {
				this.addRowToSelection(rowIndex);
			}
		} else  if (this.isSingleMode || (!event.shiftKey && !event.ctrlKey && !event.altKey)) {
			this.selectionFlag = "x";
			range = [rowIndex, rowIndex];
			this.setSelection(range);		
		}
		this.setActiveRow(rowIndex);
		if (event.shiftKey && !this.isSingleMode) {
			if (window.getSelection) {
				window.getSelection().removeAllRanges();
			} else if (document.selection) {
				document.selection.empty();
			}
		}
		this.selectionChanged(event);			
	},
	
	selectionChanged: function(event) {
		$(this.inputElement).value = this.selection.inspectRanges() + this.activeRow + ";" + (this.selectionFlag ? this.selectionFlag : "") ;
		var state = this.selection.getState();			
		event.oldSelection = this.oldState;
		event.newSelection = state;
		if(this.onselectionchange) this.onselectionchange(event);
		this.oldState = state;
	},

	setShiftRow: function(event) {
		if(event.shiftKey) {
			if(!this.shiftRow) {
				this.shiftRow = this.activeRow;
			}
		} else {
			this.shiftRow = null;		
		}	
	},
	
	setSelection: function(range) {
		var i = range[0];
		range[1] = (range[1] + 1) % this.rowCount;		
		do {
			this.addRowToSelection(i);
			i = (i + 1) % this.rowCount;		
		} while (i != range[1]);
		while (i != range[0]) {
			this.removeRowFromSelection(i);
			i = (i + 1) % this.rowCount;				
		}
	},
	
	resetSelection: function(e) {
		if(e.shiftKey) {
			document.selection.empty();
		}
	},

	addRowToSelection: function(rowIndex) {
		this.selection.addId(rowIndex);
		var fElement = $(this.prefix + ":f:" + rowIndex);
		var nElement = $(this.prefix + ":n:" + rowIndex);
		Element.addClassName(fElement, "dr-sdt-row-selected");
		Element.addClassName(nElement, "dr-sdt-row-selected");
		Element.addClassName(fElement, "rich-sdt-row-selected");
		Element.addClassName(nElement, "rich-sdt-row-selected");
		Element.addClassName(fElement, this.selectedClass);
		Element.addClassName(nElement, this.selectedClass);
	},

	removeRowFromSelection: function(rowIndex) {
		this.selection.removeId(rowIndex);
		var fElement = $(this.prefix + ":f:" + rowIndex);
		var nElement = $(this.prefix + ":n:" + rowIndex);
		Element.removeClassName(fElement, "dr-sdt-row-selected");
		Element.removeClassName(nElement, "dr-sdt-row-selected");
		Element.removeClassName(fElement, "rich-sdt-row-selected");
		Element.removeClassName(nElement, "rich-sdt-row-selected");
		Element.removeClassName(fElement, this.selectedClass);
		Element.removeClassName(nElement, this.selectedClass);
	},

	setActiveRow: function(rowIndex) {
		var fElement, nElement;
		if(this.activeRow != null) {
			fElement = $(this.prefix + ":f:" + this.activeRow);
			nElement = $(this.prefix + ":n:" + this.activeRow);
		Element.removeClassName(fElement, "dr-sdt-row-active");
		Element.removeClassName(nElement, "dr-sdt-row-active");
		Element.removeClassName(fElement, "rich-sdt-row-active");
		Element.removeClassName(nElement, "rich-sdt-row-active");
		Element.removeClassName(fElement, this.activeClass);
		Element.removeClassName(nElement, this.activeClass);
		}
		fElement = $(this.prefix + ":f:" + rowIndex);
		nElement = $(this.prefix + ":n:" + rowIndex);
		Element.addClassName(fElement, "dr-sdt-row-active");
		Element.addClassName(nElement, "dr-sdt-row-active");
		Element.addClassName(fElement, "rich-sdt-row-active");
		Element.addClassName(nElement, "rich-sdt-row-active");
		Element.addClassName(fElement, this.activeClass);
		Element.addClassName(nElement, this.activeClass);
		this.activeRow = rowIndex;
	}
});
