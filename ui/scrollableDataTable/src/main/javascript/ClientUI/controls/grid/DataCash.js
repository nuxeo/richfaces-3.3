/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.DataCash");

/*
 * DataCash.js - Implements client side data cashing 
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 */
ClientUI.controls.grid.DataCash = Class.create({
	
	initialize: function(cashSize) {
		var count = parseInt(cashSize/this.PKG_SIZE + 1);
		this.cash = new Array(count);
		for(var i=0; i<count; i++) {
			this.cash[i] = new Array(this.PKG_SIZE);
		}
	},
	getRow: function(index) {
		var i = parseInt(index/this.PKG_SIZE), j = index%this.PKG_SIZE;
		return this.cash[i][j];
	},
	setRow: function(index, row) {
		var i = parseInt(index/this.PKG_SIZE), j = index%this.PKG_SIZE;
		this.cash[i][j] = row;
	}
});

Object.extend(ClientUI.controls.grid.DataCash.prototype, {
	PKG_SIZE: 20
});