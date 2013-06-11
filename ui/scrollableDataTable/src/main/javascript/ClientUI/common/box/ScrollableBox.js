/**
 * ScrollableBox.js		Date created: 6.04.2007
 * Copyright (c) 2007 Exadel Inc.
 * @author Denis Morozov <dmorozov@exadel.com>
 */
ClientUILib.declarePackage("ClientUI.common.box.ScrollableBox");

ClientUILib.requireClass("ClientUI.common.box.Box");

/**
 * This class target to manage scrollable box object.
 */
ClientUI.common.box.ScrollableBox = Class.create(ClientUI.common.box.Box, {

	//Constructor
	initialize: function($super, element, parentElement) {
		$super(element, parentElement);
		this.element.setStyle({overflow: 'auto'});
		
		this.eventOnScroll = this.scrollContent.bindAsEventListener(this);
		Event.observe(this.element, 'scroll', this.eventOnScroll);
	},
	scrollContent: function(event) {
		this.updateScrollPos();
	},
	updateScrollPos: function() {
		this.timer = null;
		
		// process horizontal scrolling
		if(this.scrollLeft!==this.getViewportScrollX()) {
			this.scrollLeft = this.getViewportScrollX();
			this.element.fire("grid:onhcroll", {pos:this.getViewportScrollX()});
		}
		
		// process vertical scrolling		
		if(this.scrollTop!==this.getViewportScrollY()) {
			this.scrollTop = this.getViewportScrollY();
			this.element.fire("grid:onvcroll", {pos:this.getViewportScrollY()});
		}
	},
	updateLayout: function($super) {
		// NOTE: not implemented in this class
		$super();
	},
	getViewportScrollX: function() {
		var scrollX = 0;
		if( this.getElement().scrollLeft ) {
			scrollX = this.getElement().scrollLeft;
		}
		else if( this.getElement().pageXOffset ) {
			scrollX = this.getElement().pageXOffset;
		}
		else if( this.getElement().scrollX ) {
			scrollX = this.getElement().scrollX;
		}
		return scrollX;
	},
	getViewportScrollY: function() {
		var scrollY = 0;
		if( this.getElement().scrollTop ) {
			scrollY = this.getElement().scrollTop;
		}
		else if( this.getElement().pageYOffset ) {
			scrollY = this.getElement().pageYOffset;
		}
		else if( this.getElement().scrollY ) {
			scrollY = this.getElement().scrollY;
		}
		return scrollY;
	},
	getScrollerWidth: function() {
		if(this.scrollerWidth && this.scrollerWidth > 0)
			return this.scrollerWidth;
			
	    var scr = null;
	    var inn = null;
	    var wNoScroll = 0;
	    var wScroll = 0;
	
	    // Outer scrolling div
	    scr = document.createElement('div');
	    scr.style.position = 'absolute';
	    scr.style.top = '-1000px';
	    scr.style.left = '-1000px';
	    scr.style.width = '100px';
	    scr.style.height = '50px';
	    // Start with no scrollbar
	    scr.style.overflow = 'hidden';
	
	    // Inner content div
	    inn = document.createElement('div');
	    inn.style.width = '100%';
	    inn.style.height = '200px';
	
	    // Put the inner div in the scrolling div
	    scr.appendChild(inn);
	    // Append the scrolling div to the doc
	    document.body.appendChild(scr);
	
	    // Width of the inner div sans scrollbar
	    wNoScroll = inn.offsetWidth;
	    // Add the scrollbar
	    scr.style.overflow = 'auto';
	    // Width of the inner div width scrollbar
	    wScroll = inn.offsetWidth;
	
	    // Remove the scrolling div from the doc
	    document.body.removeChild(
	        document.body.lastChild);
	
	    // Pixel width of the scroller
	    this.scrollerWidth = (wNoScroll - wScroll);
	    return this.scrollerWidth || 0;
	}	
});