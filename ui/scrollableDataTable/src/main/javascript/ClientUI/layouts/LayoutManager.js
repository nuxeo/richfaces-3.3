/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.layouts.LayoutManager");

/*
/* LayoutManager.js - Base class for all layout managers
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 * TODO: description of control 
 */
ClientUI.layouts.LayoutManager = Class.create(ClientUI.common.box.Box, {	
	initialize: function($super, element, parentElement) {
		$super(element, parentElement);
		
		// store container element to look after
		this.container = parentElement;
		if(this.container)
			this.container = new ClientUI.common.box.Box($(this.container));

		// declare event listeners
		this.eventContainerResize = this.containerResize.bindAsEventListener(this);
		
		this.registerEvents();
	},
	registerEvents: function() {
			Event.observe(window, "resize", this.eventContainerResize);
	},
	destroy: function() {
			Event.stopObserving(window, "resize", this.eventContainerResize);
	},
   	containerResize: function(event) {
	  	this.updateLayout();
	},
	updateLayout: function($super) {
		if(this.container) {
			var w = this.container.getWidth();
			var h = this.container.element.offsetHeight;
			if(ClientUILib.isGecko) {
				w -= this.container.getBorderWidth("lr") + this.container.getPadding("lr");
				h -= this.container.getBorderWidth("tb") + this.container.getPadding("tb");
			}			
			this.setWidth(w);
			this.setHeight(h);
		}
		$super();		
	},
	getContainer: function() {
		return this.container;
	}
});