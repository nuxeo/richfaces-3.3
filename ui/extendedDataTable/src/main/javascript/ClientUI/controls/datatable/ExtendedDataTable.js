if (!window.ExtendedDataTable) window.ExtendedDataTable = {};

ExtendedDataTable.DataTable = Class.create({
	initialize : function(id, options) {
		this.id = id;
		$(this.id).component = this; 
		this["rich:destructor"] = "destroy";
		this.groups = [];
		this.ratios = [];
		
		// register event handlers
		this.options = options;
		this.selectionManager = new ExtendedDataTable.SelectionManager(options, this);
		
		if (this.options.sortFunction) {
			this.sortFct = this.options.sortFunction;
		}
		
		if (this.options.groupFunction) {
			this.groupFct = this.options.groupFunction;
		}
		
		this.onGroupToggleFct = this.options.onGroupToggleFunction;
		if (this.options.onColumnResize != null){
			this.onColumnResize = this.options.onColumnResize;
			this.columnWidths = "";
		}
		this.eventContainerResize = this.OnWindowResize.bindAsEventListener(this);
		this.eventGroupRowClicked = this.OnGroupRowMouseClicked.bindAsEventListener(this);
		Event.observe(window, "resize", this.eventContainerResize);
		this.minColumnWidth = this.options.minColumnWidth;
		
		var grid = this;

		Utils.execOnLoad(
			function(){
				grid.update(true);
			},
			Utils.Condition.ElementPresent(id+':od'), 100);
	},
	
	destroy: function() {
        
        //remove listeners
        this.selectionManager.removeListeners();
        if (this.header) {
            this.header.removeListeners();
        }
        if (this.groupRows) {
	        var l = this.groupRows.length;
	        for(var i = 0; i < l; i++) {        
	            Utils.DOM.Event.removeListeners(this.groupRows[i]);
	        }
        } 
        //null all references to DOM elements
        delete this.selectionManager;
        delete this.header;
        delete this.footer;
        
        $(this.id).component = null;
        this.table = null;
        this.splashScreen = null;
		this.mainDiv = null;
		this.outerDiv = null;
		this.tableB = null;
		this.fakeIeRow = null;
		this.fakeIeBodyRow = null;
		this.cols = null;
		this.scrollingDiv = null;
		this.groupRows = null;
		this.groups = null;
		Event.stopObserving(window, 'resize', this.eventContainerResize);
	},
	
    /**
     * Changes the scroll position of the table to show row of specified index
     */
    showRow: function(rowIndex) {
        var row = $(this.id+":n:"+rowIndex);
        var offsetTop = this.tableB.offsetTop + row.offsetTop;
        var scrollTop = this.scrollingDiv.getElement().scrollTop;
        
        var dS = offsetTop - scrollTop;
        
        if (dS < 0) {
            this.scrollingDiv.getElement().scrollTop = scrollTop + dS;
        }else{
            var scrollDivHeight = this.scrollingDiv.getHeight();
            var rowHeight = row.getHeight();
            dS = dS + rowHeight - scrollDivHeight;
            if (dS > 0) {
                this.scrollingDiv.getElement().scrollTop = scrollTop + dS;
            }
        }
	},
	
    setColumnWidth: function(columnIndex, newWidth) {
        if (columnIndex >= this.getColumnsNumber) {
            return false;
        }else{
            //TODO IE fails here, need to find a workaround
            this.getColumns()[columnIndex].width = newWidth;
        }
    },
    	
	_findParentElement: function(event, element) {
		var el = null;
		if(ClientUILib.isSafari) {
			var targetCell = event.currentTarget;
			if(targetCell && targetCell.tagName.toLowerCase() == element) {
				el = targetCell;
			} else {
				var e = (event.target || event.srcElement);
				while((e != null) && (e.tagName.toLowerCase() != element) && (e != document)){
					e = e.parentNode;
				}//while
				if ((e) && (e.tagName.toLowerCase() == element)){
					el = e;
				}
			}
		} else {
			el = Event.findElement(event, element);
		}
		return el;
	},
	
	preSendAjaxRequest: function(){
		//remove listeners
		Event.stopObserving(window, 'resize', this.eventContainerResize);
		//show splash screen
		this.showSplashScreen();
	},
		
	showSplashScreen: function(){
        /**
            Opera 95 is not drawing additional
            element, and so I am commenting this out.
        if (ClientUILib.isOpera) {
            this.mainDiv.setStyle({display:'none'});
        }
        */
        //this.table.setStyle({visibility:'hidden'});
        var splshscr = this.splashScreen;
        splshscr.className = 'extdt-ss-vsbl';
	},
	
	hideSplashScreen: function(){
        /**
            Opera 95 is not drawing additional
            element, and so I am commenting this out.
        if (ClientUILib.isOpera) {
            this.mainDiv.setStyle({display:''});
        }
        */
		//this.table.setStyle({visibility:''});
		this.splashScreen.className = 'extdt-ss-hdn';		
	},
	
	sortBy: function(columnId, ascending, event){
		if (this.sortFct){
			this.preSendAjaxRequest();
			if (!columnId){
				columnId = "";
			}
			else{
				//prepend table id if necessary
				if (columnId.indexOf(":") == -1){
					columnId = this.id + ":" + columnId;
				}
			}
			this.sortFct(event, columnId, ascending);
		}
	},
	
	groupBy: function(columnId, event){
		if (this.groupFct){
			this.preSendAjaxRequest();
			if (!columnId){
				columnId = "";
			}
			else{
				//prepend table id if necessary
				if (columnId.indexOf(":") == -1){
					columnId = this.id + ":" + columnId;
				}
			}
			this.groupFct(event, columnId);
		}
	},
	
	OnWindowResize: function(event) {
        if (this.table) {
            this.calculateWidthsFromRatios();
            this.updateLayout();
            //this.correctColumns();
        }
	},
	getColumnsNumber: function() {
		return this.columnsNumber;
	},
	getColWidth: function(columnNumber) {
		
	},
	getColumns: function() {
		return this.cols;
	},
	OnGroupRowMouseClicked: function(event) {
		
		var groupRow = this._findParentElement(event, "tr");
		var bExpanded = !(groupRow.getAttribute('expanded') == 'true');
		var sExpanded = bExpanded ? 'true' : 'false';
		var groupIndex = parseInt(groupRow.getAttribute('groupindex'));
		if (this.onGroupToggleFct){
			this.onGroupToggleFct(event, groupIndex);
		}
		groupRow.setAttribute('expanded', sExpanded);
		var imageDiv = groupRow.firstChild.firstChild.firstChild;
		this.toggleImageSource(imageDiv);
		this.setGroupExpanded(groupIndex, bExpanded);
		Event.stop(event);
	},
	
	toggleImageSource: function(imageDiv) {
		var src = imageDiv.getAttribute('src');
		var alternateSrc = imageDiv.getAttribute('alternatesrc');
		imageDiv.setAttribute('src', alternateSrc);
		imageDiv.setAttribute('alternatesrc', src);		
	},
	
    getColumnWidth: function(columnNumber) {
        if ((columnNumber < this.getColumnsNumber()) && (columnNumber >=0)) {
            var col = this.getColumns()[columnNumber];
            if (col.offsetWidth != null) {
                if (ClientUILib.isOpera) {
                    return parseInt(col.width);
                }else{
                    return col.offsetWidth;
                }
            }else{
                return parseInt(col.width);
            }
        }else{
            return null;
        }
    },
	
	setGroupExpanded: function(iGroupIndex, bValue) {
		var group = this.groups[iGroupIndex];
		
		var sVisibility;
		var sBorder;
		var sEmptyCells;
		
		if (bValue) {
			sVisibility = '';
			sBorderStyle = '';
		}else{
			sVisibility = 'none';
			sBorderStyle = 'none';
		}
		var size = group.size();
		for (var i=0; i<size; i++) {
			group[i].style.display = sVisibility;
			if (ClientUILib.isIE){
				//prevent IE from showing borders of cells
				//which parents have been hidden :|
				var cells = group[i].childNodes;
				var l = cells.length;
				for (var j = 0; j < l; j++) {
					cells[j].style.borderStyle = sBorderStyle;
				}
			}
		}
	},
	
	createControls: function() {
		var id = this.id;
		this.table = new ClientUI.common.box.Box(this.id +":tu",null,true);
		var table = this.table;
		this.splashScreen = $(this.id+":splashscreen");
		this.mainDiv = new ClientUI.common.box.Box(this.id,null,true);
		this.outerDiv = new ClientUI.common.box.Box(this.id +":od",null,true);
		this.tableB = $(this.id +":n");
		this.fakeIeRow = $(this.id +":fakeIeRow");
		this.fakeIeBodyRow = $(this.id +":body:fakeIeRow");
		this.header = new ExtendedDataTable.DataTable.header(this.id +":header",this);
		this.header.minColumnWidth = this.minColumnWidth;
		this.header.addListeners();
		var colgroup = $(this.id +":colgroup:body");
		this.cols = colgroup.getElementsByTagName("col");
		this.columnsNumber = this.cols.length;
		this.scrollingDiv = new ClientUI.common.box.Box(this.id +":sd",null,true);
		this.groupRows = [];
		var tfoot = table.getElement().getElementsByTagName('tfoot');
        this.footer = $(this.id +":footer");
		if (ClientUILib.isOpera) {
			//no overflow-x nor overflow-y in Opera
			this.scrollingDiv.setStyle({overflow: 'scroll',
				width: this.mainDiv.getWidth()
			});
			this.table.setStyle({width: this.mainDiv.getWidth()});
		};
		
		var i = 0;
		var groupRow = $(id+':group-row:'+i);
		while (groupRow != null) {
			this.groupRows[i] = groupRow;
			Utils.DOM.Event.removeListeners(groupRow);
			Utils.DOM.Event.observe(groupRow,'click',this.eventGroupRowClicked);
			i++;
			groupRow = $(id+':group-row:'+i);
		}
		this.saveRatios();
	},
	
	getScrollbarWidth: function() {
		var sd = this.scrollingDiv.getElement();
		LOG.debug("Scrolling Div offsetWidth: " + sd.offsetWidth);
		LOG.debug("Scrolling Div clientWidth: " + sd.clientWidth);
		return sd.offsetWidth - sd.clientWidth;
	},
	validateColumnsWidth: function(columns,excessWidth) { 
	    LOG.debug('firing validateColumnsWidth');
		var i=0;
		var endIndex = columns.length-1;
		while ((i < endIndex) && (excessWidth > 0)) {
            if (ClientUILib.isIE) {
                var colWidth = parseInt(this.getColumns()[i].width) - 1;
            }else{
                var colWidth = this.header.getColumnWidth(i);
            }
            var spareWidth = colWidth - this.minColumnWidth;
            var dW;
            if (spareWidth >= excessWidth) {
                dW = excessWidth; 
            }else{
                dW = spareWidth;
            }
            this.setColumnWidth(i, colWidth - dW);
            this.header.setColumnWidth(i, colWidth - dW);
            excessWidth -= dW;
            i++;
		/*
			var col = columns[columns.length-i-1];
			var spareWidth = col.offsetWidth - this.minColumnWidth;
			if (spareWidth > excessWidth) {
				col.width = col.offsetWidth - excessWidth;
				columns[endIndex - i].width = col.width;
				excessWidth = 0;
			}else{
				col.width = col.offsetWidth - spareWidth;
				columns[endIndex - i].width = col.width;
				excessWidth -= spareWidth;
			}
		*/
		}
	},
	
	getFooterHeight: function() {
        if (this.footer) {
            return this.footer.getHeight();
        }else{
            return 0;
        }
	},
	
	updateLayout: function() {
	    ClientUILib.log(ClientUILogger.INFO, "updateLayout");
		var table = this.table.getElement();
		var outerDiv = this.outerDiv.getElement();
		var cols = this.getColumns();
		var header = this.header;
		var scrollingDiv = this.scrollingDiv;
		var mainDivHeight = this.mainDiv.getHeight();
		var mainDivWidth = this.mainDiv.getWidth();
		var headerChildren = header.getColumnCells();
		
		var footers = table.getElementsByTagName('tfoot');
		var footerHeight = this.getFooterHeight();

		var columnsNumber = this.getColumnsNumber();		
		var visibleHeaderWidth = this.header.getVisibleWidth();
        //var scrollbarWidth = scrollingDiv.getElement().offsetWidth - scrollingDiv.getElement().clientWidth;
        var scrollbarWidth = this.getScrollbarWidth();
        var maxAllowedWidth = mainDivWidth - scrollbarWidth;
        for (var i=0; i < columnsNumber-1; i++) {
            if (this.header.isColumnWidthPercentage(i)) {
                //change percents into pixels
                var width = this._percentsToPixels(this.header.getColumn(i).width, maxAllowedWidth);
                this.header.setColumnWidth(i, width);
                this.setColumnWidth(i, width);
            }
        }
        //var excessWidth = this.header.getVisibleWidth() - maxAllowedWidth - 1;
		//if (excessWidth > 0) {
		//    this.validateColumnsWidth(cols,excessWidth);
		//};
        cols[columnsNumber-1].width = null;
        cols[cols.length-1].width = null;
		var newHeight = mainDivHeight - header.getHeight() - footerHeight - 2;
		newHeight -= this.header.getCaptionHeight();
		scrollingDiv.setStyle('height:'+ newHeight +'px;');
		this._redrawTable(table);
		header.adjustSeparators();
		this._redrawTable(this.tableB);
		this.saveRatios();
		this.hideSplashScreen();
	},
	
	/**
    *  Calculates ratios of column width to total table width. 
    */
    saveRatios: function() {
        LOG.debug('saveRatios');
        var c = this.getColumns(); //table columns
        var scrollbarWidth;
        if(!this._scrollbarWidth) {
            scrollbarWidth = this.getScrollbarWidth(); //width of the scrollbar
            LOG.debug('Scrollbar: ' + scrollbarWidth);
        } else {
            scrollbarWidth = this._scrollbarWidth;
            LOG.debug('Scrollbar (cache): ' + this._scrollbarWidth);
        }
        var mainDivWidth = this.mainDiv.getWidth(); //width of the whole div with table
        LOG.debug('Main DIV: ' + mainDivWidth);
        var maxWidth = mainDivWidth - scrollbarWidth; //max width of the table
        LOG.debug('Width to spread: ' + maxWidth);
        //generate ratio for each column
        for(i = 0;i < c.length - 1;i++) {
            var w = c[i].width;
            if(this.header.isColumnWidthPercentage(i)) {//width in percents
               //convert to pixels
               w = this._percentsToPixels(w, maxWidth);
            }
            this.ratios[i] = w / maxWidth;
            LOG.debug('Column[' + i + '] ratio: ' + this.ratios[i]);
        }
    },
	
	calculateWidthsFromRatios: function() {
	    LOG.debug('firing calculateWidthsFromRatios');
	    var c = this.getColumns(); //table columns
        var scrollbarWidth = this.getScrollbarWidth(); //width of the scrollbar
        this._scrollbarWidth = scrollbarWidth;
        LOG.debug('Scrollbar: ' + scrollbarWidth);
        var mainDivWidth = this.mainDiv.getWidth(); //width of the whole div with table
        LOG.debug('Main DIV: ' + mainDivWidth);
        var maxWidth = mainDivWidth - scrollbarWidth; //max width of the table
        LOG.debug('Width to spread: ' + maxWidth);
        var totalWidth = 0;
        //set widths according to each column's width ratio
	    for(i = 0;i < c.length - 1;i++) {
	        LOG.debug('Column[' + i + '] ratio: ' + this.ratios[i]);
	        var w = Math.round(this.ratios[i] * maxWidth);
	        if(w < parseInt(this.minColumnWidth)) {
	           w = parseInt(this.minColumnWidth);
	        }
	        LOG.debug('setting column ' + i + ' to width: ' + w);
	        this.setColumnWidth(i, w);
            this.header.setColumnWidth(i, w);
            totalWidth += w;
	    }
	    //Compensate for rounding inaccuracy
	    if(totalWidth > maxWidth) {
	        //reduce width of the last column
	        c[c.length - 2].width -= (totalWidth - maxWidth);
	    }
	},
	
    update: function(refreshEvents) {
        this.createControls();
        if ( !ClientUILib.isIE ) {
            if (this.fakeIeRow) {
				this.table.getElement().deleteRow(this.fakeIeRow.rowIndex);	
				this.fakeIeRow = null;
			}
			if (this.fakeIeBodyRow) {
				this.tableB.deleteRow(this.fakeIeBodyRow.rowIndex);
				this.fakeIeBodyRow = null;
			}
		};
		this.selectionManager.refreshEvents();
		this.updateLayout();
		this.selectionManager.restoreState();
	},
	
	_percentsToPixels: function(percents, maxAllowedWidth) {
	   var val = (percents.substr(0, percents.length-1)*1)/100;
       return maxAllowedWidth*val;
	},
	
	_redrawTable: function(table) {
    	table.hide(); //this is for opera < 9.5
    	if (ClientUILib.isSafari){
			var tr = table.insertRow(0);
	        var td = tr.insertCell(0);
	        td.setAttribute("colspan", 5);
	        td.innerHTML = "safari-must-have-something-inserted-to-redraw-table";
	        table.deleteRow(tr.rowIndex);
		}
        table.show(); 
	}

});