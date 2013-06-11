/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.common.utils.StringBuilder");

/*
/* sbuilder.js - Helper class to improve strings concatenation perfomance 
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 * Usage:
 * var sb = new StringBuilder();
 * sb.append("String 1").append("String 2");
 * sb.append("String 3");
 * var str = sb.toString();
 */
StringBuilder = Class.create({
	
	initialize: function(str) {
		this._string = null;
		this._current = 0;
		this._parts = [];
		this.length = 0;
		
		if(str != null)
			this.append(str);
	},
	append: function (str) {
		// append argument
		//this.length += (this._parts[this._current++] = String(str)).length;
		this._parts.push(String(str));
		
		// reset cache
		this._string = null;
		return this;
	},
	
	toString: function () {
		if (this._string != null)
			return this._string;
		
		var s = this._parts.join("");
		this._parts = [s];
		this._current = 1;
		this.length = s.length;
		
		return this._string = s;
	},
	
	clean: function(str) {
		this.initialize();
	}
});

Object.extend(StringBuilder.prototype, {
	length: 	0,
	
	// private
	_current:	0,
	_parts:		[],
	_string: 	null	// used to cache the string
});
