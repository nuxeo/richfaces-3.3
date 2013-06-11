ClientUILib.declarePackage("ClientUI.controls.grid.ScrollableGrid");

ClientUI.controls.grid.ScrollableGrid = Class.create(ClientUI.controls.grid.Grid, {
	initialize: function($super, options) {
		this.startInitTime = (new Date()).getTime();
		
		this.options = options;
		this.client_id = this.options.client_id;
		this.rows_count = $(this.client_id + "_rows_input").value;
		this.columns_count = this.options.columnsCount;
		this.splash_id = this.options.splash_id; 
		this.dataModel = new ClientUI.controls.grid.FakeArrayDataModel(this.rows_count, this.columns_count, this.client_id);
		
		this.templates = [
			{pane: GridLayout_Enum.HEADER,	ref: this.client_id +"_" + "GridHeaderTemplate"},
			{pane: GridLayout_Enum.BODY, 	ref: this.client_id +"_" + "GridBodyTemplate"},
			{pane: GridLayout_Enum.FOOTER,	ref: this.client_id +"_" +  "GridFooterTemplate"}
		];			
		this.startCreateTime = (new Date()).getTime();
		
		$super(this.client_id, this.dataModel, this.templates);
					
		this.endCreateTime = (new Date()).getTime();
				
		Event.observe(this.element, "grid:onsort",  this.onSorted.bindAsEventListener(this));
		if (this.options.selectionInput) {
			this.selectionManager = new ClientUI.controls.grid.SelectionManager(this);
		}
		this.endInitTime = (new Date()).getTime();
		this.rowCallbacks = [];
	},
	
	onSortComplete : function(request, event, data){
		this.dataModel.count = $(this.client_id + "_rows_input").value;
		var options = request.getJSON("options");				
		Utils.AJAX.updateRows(options,request,
								this,this.client_id, 
								[this.updateSelectionCallBack], 
								[function(){
									this.selectionManager.restoreState();
									this.element.fire("grid:onpostsort",{column: options.column, order:options.order});
								}]);
		this.updateLayout();
		this.getBody().restoreScrollState();
	},
	onScrollComplete : function(request, event, data){
		this.dataModel.count = $(this.client_id + "_rows_input").value;
		var options = this.dataModel.getCurrentOptions();
		window.loadingServerTime = (new Date()).getTime();
		Utils.AJAX.updateRows(options,request,
								this,this.client_id, 
								[this.updateSelectionCallBack],
								[function(){
									this.selectionManager.restoreState();
									this.element.fire("grid:onpostscroll",{start:this.getBody().currRange.start});
								}]);								
								
		this.updateLayout();
		this.getBody().updateScrollState();
		window.loadingEndTime = (new Date()).getTime();
		
		// TODO: remove this time statistic logging
		ClientUILib.log(ClientUILogger.ERROR, "Total time of data loading of "+options.count+" rows is: " + (window.loadingEndTime - window.loadingStartTime) + " miliseconds.");
		ClientUILib.log(ClientUILogger.WARNING, "...Server load time: " + (window.loadingServerTime - window.loadingStartTime));
		ClientUILib.log(ClientUILogger.WARNING, "...DOM updated time: " + (window.loadingUpdateTime - window.loadingServerTime));
		ClientUILib.log(ClientUILogger.WARNING, "...Grid invalidation time: " + (window.loadingInvalidateTime - window.loadingUpdateTime));
		ClientUILib.log(ClientUILogger.WARNING, "...Selection mng time: " + (window.loadingEndTime - window.loadingInvalidateTime));
	},
	
	onSorted: function(event) {
		this.options.onSortAjaxUpdate(event.memo);
	},
	
	updateSelectionCallBack: function(argMap) {
		if (this.selectionManager) {
			this.selectionManager.addListener(argMap.row, argMap.index);
		}
	},
	
	setSizes: function(width, height) {
		var style = this.element.style;
		style.width = width +"px";
		style.height = height +"px";
		this.updateLayout();
	},
	
	doCollapse: function(index) {
		var header = this.getHeader();
		var flength = header.headerFrozenRow.getElement().rows[0].cells.length;
		var nlength = header.headerRow.getElement().rows[0].cells.length;
		if(index < flength + nlength - 1) {
			var frozen = true;
			if(index >= flength) {
				index -= flength;
				frozen = false;			
			}
			this.hideColumn(index, frozen);
		}
	},

	collapse: function(index) {
		this.doCollapse(index);
	},
	
	hideColumn: function(index, frozen) {
		this.getHeader().hideColumn(index, frozen);
		this.getBody().hideColumn(index, frozen);
		if(this.getFooter()) {this.getFooter().hideColumn(index, frozen);}				
		this.updateLayout();
	}
});
