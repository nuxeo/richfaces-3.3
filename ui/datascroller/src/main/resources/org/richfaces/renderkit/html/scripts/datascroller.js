if (!window.Richfaces) {
	window.Richfaces = {};
}

Richfaces.DatascrollerScrollEvent = "rich:datascroller:onscroll";

Richfaces.Datascroller = Class.create({
	initialize: function(clientId, submitFunction) {
		this.element = $(clientId);
		this.element.component = this;
		
		this["rich:destructor"] = "destroy";
		
		Event.observe(this.element, Richfaces.DatascrollerScrollEvent, submitFunction);
	},
	
	destroy: function() {
		this.element.component = undefined;
		this.element = undefined;
	},

	switchToPage: function(page) {
		if (typeof page != 'undefined' && page != null) {
			Event.fire(this.element, Richfaces.DatascrollerScrollEvent, {'page': page});
		}
	},
	
	next: function() {
		this.switchToPage("next");
	},
	
	previous: function() {
		this.switchToPage("previous");
	},
	
	first: function() {
		this.switchToPage("first");
	},
	
	last: function() {
		this.switchToPage("last");
	},
	
	fastForward: function() {
		this.switchToPage("fastforward");
	},
	
	fastRewind: function() {
		this.switchToPage("fastrewind");
	}
});