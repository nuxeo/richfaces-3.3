if (!window.Richfaces) window.Richfaces = {};

Richfaces.hotKey = function() {
	this.initialize.apply(this, arguments);
};

Richfaces.hotKey.resolveHandler = function(handlerBody) {
	if (handlerBody) {
		if (typeof handlerBody == "function") {
			return handlerBody;
		} else {
			return new Function("event", handlerBody);
		}
	}
};

jQuery.extend(Richfaces.hotKey.prototype, {
	initialize: function(id, key, selector, options, handler) {
		this.id = id;
		this.selector = selector;
		this.key = key;
		this.baseOptions = options;
		this.handler = handler;
		this["rich:destructor"] = "destroy";
		
		this.element = document.getElementById(id);
		this.element.component = this;
		
		if ("immediate" == options.timing) {
			this.enable();
		} else if ("onload" == options.timing) {
			var _this = this;
			jQuery(document).ready(function() {
				_this.enable();
			});
		}
	},
	
	destroy: function() {
		this.disable();
		this.element.component = null;
		this.element = null;
	},

	buildOptions: function(selector, argOptions) {
		var result = new Array();

		var elts;
		
		if (typeof selector == "string") {
			elts = jQuery(selector);
		} else if (selector.constructor == Array) {
			elts = new Array();
			
			for (var i = 0; i < selector.length; i++) {
				var s = selector[i];
				
				if (!s.nodeType) {
					var q = jQuery(s);
					for (var j = 0; j < q.length; j++) {
						elts.push(q[j]);
					}
				} else {
					elts.push(s);
				}
			}
		} else {
			elts = [selector];
		}
		
		for (var i = 0; i < elts.length; i++) {

			var options = jQuery.extend({}, this.baseOptions, argOptions);
			options.target = elts[i];
			
			result.push(options);
		}
		
		return result;
	},
	
	_getComponentControlParameters: function(args) {
		return args.length <= 1 ? args[0] : args[1];
	},
	
	_generalAdd: function(key, options, handler) {
		var _handler = function() {
			if (handler.apply(this, arguments) === false) {
				var event = arguments[0];
	
				event.stopPropagation();
	            event.preventDefault();
			}
		};
		
		for (var i = 0; i < options.length; i++) {
			jQuery.hotkeys.add(key, options[i], _handler);
		}
	},
	
	_generalRemove: function(key, options) {
		for (var i = 0; i < options.length; i++) {
			jQuery.hotkeys.remove(key, options[i]);
		}
	},

	enable: function () {
		if (!this.options) {
			this.options = this.buildOptions(this.selector);
		}

		this._generalAdd(this.key, this.options, this.handler);
	},

	add: function() {
		var parameters = this._getComponentControlParameters(arguments);
		
		var selector = parameters.selector || this.selector; 
		var key = parameters.key || this.key;
		var handler = parameters.handler || this.handler;
		
		var options = this.buildOptions(selector, parameters);
		
		this._generalAdd(key, options, this.constructor.resolveHandler(handler));
	},
	
	disable: function () {
		if (!this.options) {
			this.options = this.buildOptions(this.selector);
		}

		this._generalRemove(this.key, this.options);
	},
	
	remove: function() {
		var parameters = this._getComponentControlParameters(arguments);
	
		var selector = parameters.selector || this.selector; 
		var key = parameters.key || this.key;
		
		var options = this.buildOptions(selector, parameters);
		
		this._generalRemove(key, options);
	}
});