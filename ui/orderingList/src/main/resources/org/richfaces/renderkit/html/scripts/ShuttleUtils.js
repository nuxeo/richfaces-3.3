if (!window.RichShuttleUtils) {
	window.RichShuttleUtils = {};	
}

RichShuttleUtils.execOnLoad = function(func, condition, timeout) {
	
	if (condition()) {
		func();		
	} else {
		window.setTimeout(
			function() {
				RichShuttleUtils.execOnLoad(func, condition, timeout);
			},
			timeout
		);
	}
};
RichShuttleUtils.Condition = {
	ElementPresent : function(el) {
		return function () {
			//var el = $(element);
			return el && el.offsetHeight > 0;
		};
	}
};

Array.prototype.remove = function(object) {
	var index = this.indexOf(object, 0, this.length);
	if (index == -1) return;
	if (index == 0) {
		this.shift();
	} else {
		this.splice(index, 1);
	}
};