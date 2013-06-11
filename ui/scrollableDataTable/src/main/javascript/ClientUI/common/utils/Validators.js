/**
 * Validators.js		Date created: 14.04.2007
 * Copyright (c) 2007 Exadel Inc.
 * @author Denis Morozov <dmorozov@exadel.com>
 */
 
var Validators = {
	/**
	 * Internet Explorer holds references to objects that are not actually javascript objects. So if we use the 
	 * objects in javascript it will give error. But the typeof operator identifies them as javascript objects( problem!!!). 
	 * Here we can use the isIEObject() function to identify those objects.
	 */
	isIEObject: function(a) {
		return this.isObject(a) && typeof a.constructor != 'function';
	},

	/**
	 * This function returns true if a is an array, meaning that it was produced by the Array constructor or by 
	 * the [ ] array literal notation.
	 */
	isArray: function(a) {
		return this.isObject(a) && a.constructor == Array;
	},
 
	/**
	 * This function returns true if a is one of the Boolean values, true or false.
	 */
	isBoolean: function(a) {
		return typeof a == 'boolean';
	},
	
	getBoolean: function(val, defVal) {
		if(this.isBoolean(val))
			return val;
		if(val == "true") return true;
		else if(val == "false") return false;
		
		return defVal;
	},

	/**
	 * This function returns true if a is an object or array or function containing no enumerable members.
	 */
	isEmpty: function(o) {
		if (this.isObject(o)) {
           for (var i in o) {
                return false;
           }
		}
		else if(this.isString(o) && o.length > 0) {
			return false;
		}
		
		return !this.IsNumber(o);
	},

	/**
	 * This function returns true if a is a function. Beware that some native functions in IE were made to look 
	 * like objects instead of functions. This function does not detect that.Netscape is better behaved in this regard.
	 */
	isFunction: function(a) {
		return typeof a == 'function';
	},

	/**
	 * This function returns true if a is the null value.
	 */
	isNull: function(a) {
		return typeof a == 'object' && !a;
	},

	/**
	 * This function returns true if <code>data</code> is a finite number. It returns false if <code>data</code> is NaN or Infinite.
	 */
	IsNumber: function(data) {
		if(typeof data == 'number' && isFinite(data)) {
			return true;
		}
		
		// if it is a string that contains a number
		var re = /(^-?[1-9](\d{1,2}(\,\d{3})*|\d*)|^0{1})$/;
		if ( re.test(data) ) {
			return true;
		}
		return false;
	},
	IsFormattedNumber: function(data) {
		// Regular expression should match number with commas or not
		//1. ^-? <-- '-' is optional at the beginning
		//2. \d{1,3} <-- with or without comma, first 3 digits
		//3. \d{1,3}(\,\d{3})* <-- with comma, at least one digit with max of three before repeating like ',ddd'
		//4. \d+ <-- without comma, match any number of integer(shouldn't be though)
		var re = /^-?(\d{1,3}|\d{1,3}(\,\d{3})*|\d*)$/g;
		if ( ! re.test(data) ) {
			return false;
		}
		return true;		
	},

	/**
	 * This function returns true if a is an object, array, or function. It returns false if a is a string, 
	 * number, Boolean, null, or undefined.
	 */
	isObject: function(a) {
		return (typeof a == 'object' && !!a) || this.isFunction(a);
	},

	/**
	 * This function returns true if a is a string.
	 */
	isString: function(a) {
		return typeof a == 'string';
	},

	/**
	 * This function returns true if a is the undefined value. You can get the undefined value from an uninitialized 
	 * variable or from an object's missing member.
	 */
	isUndefined: function(a) {
		return typeof a == 'undefined';
	}
};