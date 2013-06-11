LOG.LEVEL = LOG.INFO;
LOG._logToConsole = function(message, level) { 
	//TODO deal with this nasty message
	if (message != "No information in response about elements to replace") {
		LOG.out.println(level.name + ': ' + message) 
	}
};

var Timer = {

	_eventCounter: 0,	
		
	currentTime: 0,
	
	maxTime: 10000000,
	
	events: new Array(),

	addEventToTimer: function(callback, delay) {
		var eventTime = this.currentTime + delay;
		
		var i = 0;
		
		while (this.events[i] && (this.events[i].eventTime <= eventTime)) {
			i++;
		}
		
		var eventId = this._eventCounter++;
		
		this.events.splice(i, 0, {eventTime: eventTime, callback: callback, eventId: eventId});
	
		return eventId;
	},

	removeEventFromTimer: function(eventId) {
		for ( var i = 0; i < this.events.length; i++) {
			if (this.events[i].eventId == eventId) {
				this.events.splice(i, 1);
				
				break;
			}
		}
	},
	
	execute: function() {
		while (this.events.length > 0) {
			
			var eventData = this.events.shift();
			
			this.currentTime = eventData.eventTime;
			if (this.currentTime > this.maxTime) {
				throw "Maximum execution time reached, aborting timer";
			}
			
			try {
			
				eventData.callback();
			} catch (e) {
				alert(e.message);
			}
		}
	},
	
	isEmpty: function() {
		return this.events.length == 0;
	}
};

window.setTimeout = function(callback, delay) {
	return Timer.addEventToTimer(callback, delay);
}

window.clearTimeout = function(timerId) {
	Timer.removeEventFromTimer(timerId);
}

var SimulationContext = function(submitFunction) {
	this.results = new Array();

	this.submitFunction = submitFunction;
};

SimulationContext.prototype.ajax = function() {
	var args = new Array();
	for (var i = 1; i < arguments.length; i++) {
		args.push(arguments[i]);
	}
	var _this = this;
	
	Timer.addEventToTimer(function() {
		_this.submitFunction.apply(this, args);
	}, arguments[0]);
};

SimulationContext.prototype.executeOnTime = function(time, code) {
	Timer.addEventToTimer(function() {
		code();
	}, time);
};

window.simulationContext = undefined;

window.XMLHttpRequest = function() {
	this.requestTime = XMLHttpRequest.requestTime || 0;
	this.data = XMLHttpRequest.data;
	
	this.responseText = null;
	this.responseXML = null;
	
	this.readyState = 0;
}

window.XMLHttpRequest.UNSENT = 0;
window.XMLHttpRequest.OPENED = 1;
window.XMLHttpRequest.HEADERS_RECEIVED = 2;
window.XMLHttpRequest.LOADING = 3;
window.XMLHttpRequest.DONE = 4;

XMLHttpRequest.prototype.abort = function() {
	if (this.timerId) {
		clearTimeout(this.timerId);
	}

	window.simulationContext.results[this.requestId].endTime = Timer.currentTime;
	window.simulationContext.results[this.requestId].aborted = true;
};

XMLHttpRequest.prototype.getAllResponseHeaders = function() {
	return "";
};
XMLHttpRequest.prototype.getResponseHeader = function(name) {
	if ("Ajax-Response" == name) {
		return "true";
	}
	
	return "";
};
XMLHttpRequest.prototype.open = function(method, url, async, user, password) {
	this.readyState = XMLHttpRequest.OPENED;
};

XMLHttpRequest.prototype.send = function(contentType) {
	var length = window.simulationContext.results.push({data: this.data, startTime: Timer.currentTime});
	this.requestId = length - 1;

	var _this = this; 
	this.timerId = setTimeout(function() {
		_this.status = 200;
		_this.statusText = "Success";
		_this.readyState = window.XMLHttpRequest.DONE;
		
		var responseTextArray = new Array();
		responseTextArray.push("<html><head></head><body>");
		if (_this.data) {

			responseTextArray.push("<span id='_ajax:data'>");
			if (typeof _this.data == 'string') {
				responseTextArray.push("'");
				responseTextArray.push(_this.data);
				responseTextArray.push("'");
			} else {
				responseTextArray.push(_this.data);
			}
			responseTextArray.push("</span>");
		}
		responseTextArray.push("</body></html>");
		
		_this.responseText = responseTextArray.join('');
		
		var doc = new ActiveXObject("MSXML.DOMDocument");
		doc.loadXML(_this.responseText);
		
		//hack to make it work with current HTMLUnit
		delete doc.getElementById;
		doc.getElementById = function(id){
			var nodes = this.selectNodes("//*");
			for (var i = 0; i < nodes.length; i++) {
				if (nodes[i].getAttribute("id") == id) {
					return nodes[i];
				}
			}
		};
		
		_this.responseXML = doc;
		
		window.simulationContext.results[_this.requestId].endTime = Timer.currentTime;
		_this.onreadystatechange();
	}, this.requestTime);
};

XMLHttpRequest.prototype.setRequestHeader = function(name, value) {
	
};

var oldSubmitQuery = A4J.AJAX.SubmitQuery;

var DEFAULT_REQUEST_TIME;

A4J.AJAX.SubmitQuery = function(query, options, event) {
	var defaultRequestTime = XMLHttpRequest.defaultRequestTime;
	if (typeof defaultRequestTime == "function") {
		defaultRequestTime = defaultRequestTime(query, options, event);
	}
	
	XMLHttpRequest.requestTime = options.requestTime || defaultRequestTime || DEFAULT_REQUEST_TIME;
	XMLHttpRequest.data = options.data || (event && event.srcElement.id) || options.pollId;

	try {
		var req = oldSubmitQuery.apply(this, arguments);
		
		return req;
	} finally {
		XMLHttpRequest.requestTime = undefined;
		XMLHttpRequest.data = undefined;
	}
}	

