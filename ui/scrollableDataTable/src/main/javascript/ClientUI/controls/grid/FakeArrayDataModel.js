ClientUILib.declarePackage("ClientUI.controls.grid.FakeArrayDataModel ");
ClientUI.controls.grid.FakeArrayDataModel = Class.create(ClientUI.controls.grid.DataModel, {
			initialize: function($super, rows_count, columns_count, grid_id) {
				$super();
				this.data = [];
				this.count = rows_count;
				this.columns = columns_count;
				this.gridId = grid_id;
				this.curr_options;
			},
			
			getRow: function(index) {
				if(!this.data[index]) {
				  this.data[index] = [];
				  for (var index2 = 0; index2 < 7; index2++) {
				  	this.data[index][index2] = index2 + " : " + index; 
				  }
				  this.data[index][6] = index%2 ? "value 1" : "value 2";					
				}
				
				return this.data[index];
			},
			getCount: function() {
				return this.count;
			},
			getRequestDelay: function() {
				return 150;
			},
			
			getCurrentOptions: function() {
				 if(!this.curr_options) {
					this.curr_options = {
						count: grid.getBody().templFrozen.getElement().rows.length,
						index: 0,
						startRow: 0
					}
			}
				return this.curr_options;
			},
			
			loadRows: function(options) {
				window.loadingStartTime = (new Date()).getTime();
				var state_options = options;
				var state_input = $(this.gridId + "_state_input");
				var submit_input = $(this.gridId + "_submit_input");
//						var options_input = $(this.gridId + "_options_input");
				var submit_values = state_options.count + "," + state_options.index + "," + state_options.startRow;
				state_input.value = submit_values; 
//	 			options_input.value = options;
				this.curr_options = options;
				submit_input.click();
				
			}
			
		});
