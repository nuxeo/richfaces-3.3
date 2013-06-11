/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.ArrayDataModel");

ClientUILib.requireClass("ClientUI.controls.grid.DataModel");
/*
 * ArrayDataModel.js - Array datamodel for grid control 
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 */
ClientUI.controls.grid.ArrayDataModel = Class.create(ClientUI.controls.grid.DataModel, {
	initialize: function($super, data) {
		$super();
		this.data = $A(data || []);
	},
	getRow: function(index) {
		return this.data[index];
	},
	getCount: function() {
		return this.data.length;
	},
	getRequestDelay: function() {
		return 50;
	}
});