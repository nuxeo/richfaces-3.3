/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.GridBody");

ClientUILib.requireClass("ClientUI.common.box.Box");

/*
 * GridHeader.js - Grid control header pane
 * TODO: add comments
 */
ClientUI.controls.grid.GridBody = Class.create(ClientUI.common.box.Box, {
	/**
	 * Constructor
	 * @param {Object} template for Grid body row
	 * @param {Object} grid parent grid object
	 */
	initialize: function($super, template, grid) {
		this.grid = grid;
		this.gridId = grid.getElement().id;
		$super(template);

		// declare event listeners
		this._eventOnHScroll = this._onContentHScroll.bindAsEventListener(this);
		this._eventOnVScroll = this._onContentVScroll.bindAsEventListener(this);
		
		this.createControl(template);
		this.registerEvents();
	},
	registerEvents: function() {
		Event.observe(this.scrollBox.element, "grid:onhcroll", this._eventOnHScroll);
		Event.observe(this.scrollBox.element, "grid:onvcroll", this._eventOnVScroll);
	},
	destroy: function() {
		Event.stopObserving(this.scrollBox.element, "grid:onhcroll", this._eventOnHScroll);
		Event.stopObserving(this.scrollBox.element, "grid:onvcroll", this._eventOnVScroll);
	},
	// event listeners
	_onContentHScroll: function(event) {
		this.grid.adjustScrollPosition(event.memo.pos);
	},
	_onDataReady: function(options) {
		// load rows data		
		window.loadingUpdateTime = (new Date()).getTime();

		this.invalidate(options);
		
		window.loadingInvalidateTime = (new Date()).getTime();
	},
	_onContentVScroll: function(event) {
		this.helpObject1.moveToY(this.sizeBox.element.offsetHeight+ this.defaultRowHeight + 5);
		this.helpObject2.moveToY(this.sizeBox.element.offsetHeight+ this.defaultRowHeight + 5);
		this.setScrollPos(event.memo.pos);
		this.adjustDataPosition(event.memo.pos);
	},
	createControl: function(template) {
		this.scrollInput = $(this.gridId + ":si");
		var childs = template.childNodes;
		for(var i=0; i<childs.length; i++) {
			if(childs[i].id == this.gridId + ":bc") {
				this.container = new ClientUI.common.box.Box(childs[i], null, true);
				this.container.makeAbsolute();
				this.container.setStyle({'z-index' : 20});
				if(!ClientUILib.isIE) this.container.setStyle({overflow: 'hidden'});
				break;
			}
		}
		Event.observe(this.container.getElement(), 'keypress', this.synchronizeScroll.bindAsEventListener(this));
		
		// create scroll box
		this.scrollBox = new ClientUI.common.box.ScrollableBox(this.gridId + ":scb", this.getElement());
		this.sizeBox = new ClientUI.common.box.Box(this.gridId + ":sb", this.scrollBox.getElement());
		
		var normal = null, frozen = null;
		var childs = this.container.getElement().childNodes;
		for(var i=0; i<childs.length; i++) {
			if(childs[i].id && childs[i].id.indexOf("FrozenBox")>=0) {
				frozen = childs[i];
			}
			else if(childs[i].id && childs[i].id.indexOf("NormalBox")>=0){
				normal = childs[i];
			}
		}

		if(!normal || !frozen) {
			errMsg = "Unable to parse template for GridBody. Unable to find FrozenBox or NormalBox.";
			ClientUILib.log(ClientUILogger.ERROR, errMsg);
			throw(errMsg);
		}

		this.contentBox = new ClientUI.common.box.Box(normal);
		Event.observe(this.contentBox.getElement(), 'keypress', this.synchronizeScroll.bindAsEventListener(this));
		this.frozenContentBox = new ClientUI.common.box.Box(frozen);
		
		this.helpObject1 = new ClientUI.common.box.Box(this.gridId + ":nho", this.contentBox.getElement());
		this.helpObject2 = new ClientUI.common.box.Box(this.gridId + ":fho", this.frozenContentBox.getElement());
		
		// create row template
		var ch = this.frozenContentBox.getElement().firstChild;
		while(ch) {
			if(ch.tagName && ch.tagName.toLowerCase()=="table") {
				this.templFrozen = new ClientUI.common.box.Box($(ch), null, true);
				this.templFrozen.makeAbsolute();
				break;
			}
			ch = ch.nextSibling;
		}
		ch = this.contentBox.getElement().firstChild;
		while(ch) {
			if(ch.tagName && ch.tagName.toLowerCase()=="table") {
				this.templNormal = new ClientUI.common.box.Box($(ch), null, true);
				this.templNormal.makeAbsolute();
				break;
			}
			ch = ch.nextSibling;
		}

		this.parseTemplate(this.templFrozen.getElement(), this.templNormal.getElement());

		var gridId = this.grid.getElement().id;
		this.fTable = $(gridId + ":f")
		this.nTable = $(gridId + ":n")
		this.controlCreated = true;		
		this.sizeBox.setHeight(this.templNormal.getElement().offsetHeight);
	},
	parseTemplate: function(templFrozen, templNormal) {
		var result = false;
		if(templNormal && templNormal.rows && templNormal.rows.length != 0) {
			this.rowsCount = Math.min(templNormal.rows.length, this.grid.dataModel.getCount());

			this.helpObj = new ClientUI.common.box.Box(templFrozen, null, true);
			this.countToLoad = 0;
			this.startRow = 0;
			this.startIndex = 0;
			result = true;
		}
		this.currRange = $R(0, this.rowsCount);	
		return result;		
	},
	setScrollPos: function(pos) {
		this.contentBox.getElement().scrollTop = pos;
		this.frozenContentBox.getElement().scrollTop = pos;
		if(ClientUILib.isIE && !ClientUILib.isIE7) {
			this.contentBox.getElement().scrollTop = pos;
			this.frozenContentBox.getElement().scrollTop = pos;
		}
	},
	updateSize: function() {
		
		var row = this.templNormal.getElement().rows[0];
		if(row) {
			this.defaultRowHeight = row.cells[0].offsetHeight;
		} else {
			var defHeight = this._calcDefaultRowHeight();	
			if (isFinite(defHeight)) {
				this.defaultRowHeight = defHeight;
			}			
		}
	},
	updateLayout: function($super) {
		if(!this.controlCreated || !this.grid.controlCreated) {
			return;
		}
		$super();
		if(!this.scrollBox || !this.contentBox || !this.sizeBox) {
			return;			
		}

		var frozenContentWidth = this.fTable.offsetWidth;
		var totalWidth = frozenContentWidth + this.nTable.offsetWidth;
		
		this.scrollBox.moveTo(0, 0);
		var height = this.element.offsetHeight;
		var width = this.element.offsetWidth;
		
		this.scrollBox.setWidth(width);
		this.scrollBox.setHeight(height);		
		
		var scrollLeft = this.grid.getScrollOffset();
		var fixH = this.grid.getFooter() ? this.grid.getFooter().element.offsetHeight : 0;
		if(fixH > height) fixH = 0;
		
		this.frozenContentBox.moveTo(0, 0);
		this.contentBox.moveTo(frozenContentWidth, 0);		
		this.sizeBox.moveTo(0, 0);
		this.sizeBox.setWidth(totalWidth);

		this.scrollBox.setWidth(width);
		this.scrollBox.setHeight(height);
		
		this.defaultRowHeight = this._calcDefaultRowHeight();
		this.sizeBox.setHeight(this.defaultRowHeight * this.grid.dataModel.getCount() + fixH);

		height = this.scrollBox.getElement().clientHeight;
				
		this.contentBox.setHeight(height - fixH);		
		this.frozenContentBox.setWidth(frozenContentWidth);
		this.frozenContentBox.setHeight(height - fixH);
		this.container.setHeight(height - fixH);
		
		//http://jira.jboss.com/jira/browse/RF-2953
		//this.defaultRowHeight = this._calcDefaultRowHeight();
		var scrollTop = this.scrollBox.element.scrollTop;
		this.scrollBox.hide();
		//this.sizeBox.setHeight(this.defaultRowHeight * this.grid.dataModel.getCount() + fixH);
		this.helpObject1.moveToY(this.sizeBox.element.offsetHeight+ this.defaultRowHeight + 5);
		this.helpObject2.moveToY(this.sizeBox.element.offsetHeight+ this.defaultRowHeight + 5);
		
		this.dataVisible = parseInt(this.contentBox.element.offsetHeight / this.defaultRowHeight, 10) + 1;
		this.dataVisible = Math.min(this.dataVisible, this.rowsCount);
		if(height > 0) {
			this.adjustDataPosition(this.currentPos);	
		}
		this.scrollBox.show();
		this.scrollBox.element.scrollTop = scrollTop;
		var viewWidth = this.scrollBox.getViewportWidth();
		this.container.setWidth(viewWidth);
		
		if(ClientUILib.isIE) {
			this.contentBox.setWidth(viewWidth - frozenContentWidth);
		}
		else {
			this.contentBox.setWidth(Math.max(this.getWidth(), totalWidth));
		}
		var scrollPos = Math.min(totalWidth - viewWidth, scrollLeft);
		this.grid.adjustScrollPosition(scrollPos);
	},
	adjustScrollPosition: function(pos) {
		this.templNormal.moveToX(-pos);
	},
	getScrollYPosition: function() {
		return this.contentBox.getElement().scrollTop;
	},
	adjustDataPosition: function (pos) {		
		if(this.currentPos == pos) { 
			return;
		}
		
		// 1. calculate direction and range to load next data
		this.processedPos = pos;
		var forwardDir = (this.currentPos <= pos) ? true : false;
		
		// first visible row index
		var first = parseInt(pos / this.defaultRowHeight) - 1;
		if(first < 0) first = 0;
		
		// check direction and predict some next rows
		var from = Math.max(first - (forwardDir ? 1 : (this.rowsCount - this.dataVisible - 1)), 0);
		var to = Math.min(first + (forwardDir ? this.rowsCount-1 : this.dataVisible + 1), this.grid.dataModel.getCount());

		if(from == 0) {
			to = this.rowsCount;
		}
		else if(to == this.grid.dataModel.getCount()) {
			from = to - this.rowsCount;
			if (from < 0) {
				from = 0;
			}
		}

		var range = $R(from, to);
		
		if(this.currRange.start == from && this.currRange.end == to) {
			return;
		}
		
		if(from >= to) {
			ClientUILib.log(ClientUILogger.WARNING, "!!! GridBody: adjustDataPosition. Pos: " + pos + ", From:" + from + ", To:" + to);
			return;			
		}

		var task = this._getPendingTask();		
		if(to - from > 0) {
			task.timer = null;
			task.from = from;
			task.to = to;
			task.first = first;
			task.pos = pos;
			this._setPendingTask(task);
		}
	},	
	_getPendingTask: function() {
		if(!this.pendingTask) {
			this.pendingTask = {
				timer: null,
				rowsToLoad: [],
				rowsToLoadIdx: [],
				from: 0,
				to: 0,
				first: 0,
				pos: 0
			};
		}
		return this.pendingTask;
	},	
	_setPendingTask: function(task) {
		clearTimeout(this.pendingTask.timer);
		this.pendingTask.timer = null;
		this.pendingTask = task;
	
		// and plan other agjusting over the time
		task.timer = setTimeout(function() {
			this.startLoadData();
		}.bind(this), this.grid.dataModel.getRequestDelay());
	},
	
	startLoadData: function() {
		if(this.updateStarted) {
			this._setPendingTask(this._getPendingTask());
			return;
		}

		this.updateStarted = true;
		var task = this._getPendingTask();
		var range = $R(task.from, task.to);
		var switchType = 5;
		var startIndex = 0;
		var startRowIndx = 0;
		var countToLoad = 0;
		
		var firstIndex = this._getRowIndex(this.templNormal.getElement().rows[0].id);
		this.scrollInput.value = task.pos + "," + this.currRange.start + "," + this.currRange.end+ "," + firstIndex; 

		// if we have intersepted ranges than rearrange rows
		// in other case just move rows table to first position
		if(this.currRange.end < range.start 
			|| this.currRange.start > range.end) {
			switchType = 0;
		}
					
		if(switchType === 0) {
			startRowIndx = firstIndex;
			startIndex = range.start;
			countToLoad = range.end - range.start;
		}
		else {
			var i, row, rownew, cloned;
			countToLoad = 0;
			var normalTbl = this.templNormal.getElement();
			if(range.start > this.currRange.start 
				&& range.start < this.currRange.end) {					
				switchType = 1;
				countToLoad = range.start - this.currRange.start;
				if(countToLoad > 0) {
					startRowIndx = firstIndex;
					startIndex = this.currRange.end;
				}
			}
			else if(range.start == this.currRange.start) {
				switchType = 3;
				countToLoad = range.end - this.currRange.end;
				if(countToLoad > 0) {
					startIndex = this.currRange.end;
					var restCount = this.rowsCount - countToLoad;
					startRowIndx = this._getRowIndex(normalTbl.rows[restCount].id);
				}
			}
			else {
				switchType = 2;
				countToLoad = this.currRange.start - range.start;
				if(countToLoad > 0) {
					startIndex = range.start;
					var restCount = this.rowsCount - countToLoad;
					startRowIndx = this._getRowIndex(normalTbl.rows[restCount].id);
				}
			}
		}

		var process = true;
		if(startIndex > (task.first + this.dataVisible) ||
			(startIndex + countToLoad) < task.first) {
			process = false;
		}
		if(countToLoad > 0 && process) {
			this.updateStarted = true;
			ClientUILib.log(ClientUILogger.WARNING, "Start loading: index: " + startIndex + ", and startRow: " + startRowIndx + ", and count: " + countToLoad);

			this.scrollInput.value = task.pos + "," + range.start + "," + range.end + "," + firstIndex + "," + this.currRange.start; 
			
			this.currRange = range;
			this.currentPos = task.pos;
			
			if (this.grid.options.hideWhenScrolling) {
				this.container.hide();
			}
			
			var options = {
					index: startIndex,
					count: countToLoad,
					startRow: startRowIndx,
					switchType: switchType
			};
			
			// Make timer to handle quick clicks on scrollbar arrows
			setTimeout(function() {
				
				// 4. start data loading				
				this.updateInterval = screen.updateInterval;
				screen.updateInterval = 1000;
								
				this.grid.dataModel.loadRows(options);
			}.bind(this), 10);
		}
		else {
			this.updateStarted = false;
		}
	},
	forceReRender: function() {
		if(ClientUILib.isIE && !ClientUILib.isIE7) {
			var frozenTbl = this.templFrozen.getElement();
			var normalTbl = this.templNormal.getElement();
			// force to rerender table !!!
			var tr = frozenTbl.insertRow();
			frozenTbl.deleteRow(tr.rowIndex);
			tr = normalTbl.insertRow();
			normalTbl.deleteRow(tr.rowIndex);
		}
	},
	rearrangeRows: function(options, updateCash, showContainer) {
		var frozenTbl = this.templFrozen.getElement();
		var normalTbl = this.templNormal.getElement();
	
		if(options.switchType === 0) {
			var visibleRowPos = this.defaultRowHeight * options.index;
			var test = this.contentBox.getElement().scrollTop;
			if(showContainer) this._showContainer();
			this.templFrozen.moveToY(visibleRowPos);
			this.templNormal.moveToY(visibleRowPos);
			this.forceReRender();
		}
		else if(options.switchType === 1 || options.switchType === 2) {
			// store visible row pos to restore after rows reerrange
			var ncount = normalTbl.rows.length;
			var fcount = frozenTbl.rows.length;
			var frows = new Array(fcount), nrows = new Array(ncount);
			var j = 0, i;
			var index = options.index;
			var count = options.count;
			if(options.switchType === 2) {
				count = this.rowsCount - count;
			}
			for(i=count; i<this.rowsCount; i++) {
				if (fcount) {
					frows[j] = frozenTbl.rows[i];
				}
				nrows[j] = normalTbl.rows[i];
				j++;
			}
			for(i=0; i<count; i++) {
				if (fcount) {
					frows[j] = frozenTbl.rows[i];
				}
				nrows[j] = normalTbl.rows[i];
				j++;
			}
			
			// Mozilla is faster when doing the DOM manipulations on
			// an orphaned element. MSIE is not	
			var removeChilds = navigator.product == "Gecko";
			var fbody = frozenTbl.tBodies[0];
			var nbody = normalTbl.tBodies[0];
			var fnextSibling = fbody.nextSibling;
			var nnextSibling = nbody.nextSibling;
			
			if (removeChilds) { // remove all rows
				fp = fbody.parentNode;
				fp.removeChild(fbody);
				np = nbody.parentNode;
				np.removeChild(nbody);
			}
				
			// insert in the new order
			for (i = 0; i < ncount; i++) {
				if (fcount) {
					fbody.appendChild(frows[i]);
				}
				nbody.appendChild(nrows[i]);
			}
		
			if(removeChilds) {
				fp.insertBefore(fbody, fnextSibling);
				np.insertBefore(nbody, nnextSibling);
			}

			var visibleRowPos = (options.switchType == 1) ? this.currRange.start * this.defaultRowHeight : options.index * this.defaultRowHeight;
			if(showContainer) this._showContainer();
			this.templFrozen.moveToY(visibleRowPos);
			this.templNormal.moveToY(visibleRowPos);
		}
		else {
			var visibleRowPos = this.currRange.start * this.defaultRowHeight;
			if(showContainer) this._showContainer();
			this.templFrozen.moveToY(visibleRowPos);
			this.templNormal.moveToY(visibleRowPos);
		}
	},
	_showContainer: function() {
		this.container.show();
		if (ClientUILib.isIE) {
			this.setScrollPos(this.currentPos);	
		}
	},
	/**
	 * show hiden rows after loading them from datasource
	 * @param {Object} options
	 */
	invalidate: function(options) {
		screen.updateInterval = this.updateInterval;
		this.rearrangeRows(options, true, true);
		this.container.show();
		this.updateStarted = false;
	},
	processCashedValues: function(options) {
		return options;
		
		var opt = {switchType: options.switchType };
		var cash = this.getCash();
		var count = options.count;
		var index = options.index;
		var startRow = options.startRow;

		var i = 0;
		var rowC;
		
		while(i<count && (rowC = cash.getRow(index + i))!=null) i++;
		if(i>0) { // there are cashed rows from start
			opt.count = i;
			opt.index = index;
			opt.startRow = startRow;
			this._restoreFromCash(opt);
			
			options.count -= i;
			options.index = index+i;
			options.startRow = startRow+i;
			if(options.startRow >= this.rowsCount) options.startRow -= this.rowsCount;
		}
		
		var cnt = 0;
		while(i<count && !(rowC = cash.getRow(index + i))) { i++; cnt++; }
		if(i<count) { // there are cashed rows at the end of range
			opt.count = options.count - cnt;
			opt.index = index+i;
			opt.startRow = startRow+i;
			if(opt.startRow >= this.rowsCount) opt.startRow -= this.rowsCount;
			this._restoreFromCash(opt);
			
			options.count = cnt;
			options.index = index+(i-cnt);
			options.startRow = startRow+(i-cnt);
			if(options.startRow >= this.rowsCount) options.startRow -= this.rowsCount;
		}
		
		return options;
	},
	ensureVisible: function (index) {
		if(index>=0 && index<this.grid.dataModel.getCount()) {
			
			var visibleRows = parseInt(this.contentBox.element.offsetHeight / this.defaultRowHeight, 10) + 1;
			if(this.grid.dataModel.getCount() > visibleRows) {
				var y = index*this.defaultRowHeight;
				this.scrollBox.getElement().scrollTop = y;
				this.currentPos = 0;
				this._onContentVScroll({memo:{pos:y}});
			}
		}
	},
	reloadData: function() {
		this.currentPos = -(this.rowsCount*this.defaultRowHeight);
		this.scrollBox.getElement().scrollTop = 0;
		this.currRange.start = -this.rowsCount;
		this.currRange.end = -1;
		this._onContentVScroll({memo:{pos:0}});
	},	
	_getRowIndex: function(rowId) {
      	return Number(rowId.split(this.grid.getElement().id)[1].split(":")[2]);
	},
	
	hideColumn: function(index, frozen) {
		var rows;
		if(frozen) {
			rows = this.templFrozen.getElement().rows;
		} else {
			rows = this.templNormal.getElement().rows;
		}
		for(var i=0; i<rows.length; i++) {
			rows[i].removeChild(rows[i].cells[index]);
		}
	},
	
	showRow: function(rowIndex) {
		if(rowIndex == "up") {
			this.scrollBox.getElement().scrollTop = this.scrollBox.getElement().scrollTop - this.nTable.rows[1].offsetTop;
		} else if(rowIndex == "down") {
			this.scrollBox.getElement().scrollTop = this.scrollBox.getElement().scrollTop + this.nTable.rows[1].offsetTop;
		} else {
			var row = $(this.gridId + ":n:" + rowIndex);
			var offsetTop = this.nTable.offsetTop + row.offsetTop;
			if(this.contentBox.getElement().scrollTop > offsetTop) {
				this.scrollBox.getElement().scrollTop = offsetTop;
			} else {
				offsetTop += row.offsetHeight;
				offsetTop -= this.contentBox.getElement().clientHeight;
				if (this.contentBox.getElement().scrollTop < offsetTop){
					this.scrollBox.getElement().scrollTop = offsetTop;
				}
			}
		}
		this.scrollBox.updateScrollPos();
	},
	
	_calcDefaultRowHeight: function() {
		var templNormal = this.templNormal.getElement();
		var length = templNormal.rows.length;
		if (length) {
			return Math.ceil(templNormal.offsetHeight / length);
		} else {
			return 16;
		}
	},
	
	updateScrollState: function() {	
		this.scrollInput = $(this.gridId + ":si");
		var value = this.scrollInput.value;
		if (value !='' && this.sizeBox.getHeight() < Number(value.split(',')[0])) {
			this.restoreScrollState();
		}
		this.scrollInput.value = this.currentPos + "," + this.currRange.start + "," + this.currRange.end+ "," + this._getRowIndex(this.templNormal.getElement().rows[0].id); 
	},
	
	restoreScrollState: function() {	
		var value = this.scrollInput.value;
		if(value !=''){
			var values = value.split(',');
			this.currentPos = values[0];
			this.currRange.start = values[1];
			this.currRange.end = values[2];
			this.scrollBox.getElement().scrollTop = values[0];
			var visibleRowPos = this.currRange.start * this.defaultRowHeight;
			this._showContainer();		
			this.templFrozen.moveToY(visibleRowPos);
			this.templNormal.moveToY(visibleRowPos);
		}
	},
	
	synchronizeScroll: function(event) {
		if(Event.KEY_TAB == event.keyCode || Event.KEY_TAB == event.charCode) {
			Event.stop(event);
		}
	}
});

Object.extend(ClientUI.controls.grid.GridBody.prototype, {
	/**
	 * Count of rows can be viewed in the same time in grid
	 */
	dataVisible: 50,
	
	/**
	 * Count of rows loaded additianally to dataVisible rows
	 */
	dataDelta: 5,

	/**
	 * Current data position
	 */
	currentPos: 0
	
});
