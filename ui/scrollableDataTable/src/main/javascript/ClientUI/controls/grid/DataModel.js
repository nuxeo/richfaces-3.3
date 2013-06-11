/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.DataModel");

/*
 * DataModel.js - Base datamodel class for grid control 
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 */
ClientUI.controls.grid.DataModel = Class.create({
	
	initialize: function() {
		// constructor
	},	
	
	// interface method
	getRow: function(index) {
		return [];
	},
	
	// count of all data rows
	getCount: function() {
		return 0;
	},
		
	// start rows loading
	loadRows: function(options) {
		this.eventDataReady.fire(options);
	},
	
	// regulate requsts frequency
	getRequestDelay: function() {
		return 1000;
	}
});		
