/**
 * Box.js		Date created: 6.04.2007
 * Copyright (c) 2007 Exadel Inc.
 * @author Denis Morozov <dmorozov@exadel.com>
 */
ClientUILib.declarePackage("ClientUI.common.box.Box");


/*
 * Base class for all ui controls 
 *
 * TODO: description of control 
 *
 * TODO: usage description
 * Usage: 
 *  ClientUILib.declarePackage("ClientUI.common");
 *  ClientUILib.requireClass("ClientUI.common.box.Box");
 * 	var ClientUI.MyControl = Class.create({
 * 		CLASSDEF : {
 * 			name:  'ClientUI.MyControl',
 * 			parent: ClientUI.common.box.Box
 * 		}, 		
 *		initialize:function() {
 *			this.parentClass().constructor().call(this)
 *			alert("A new " + this.getClass().className + " was created")
 *		}				
 *	})
 */
ClientUI.common.box.Box = Class.create({

	initialize: function(element, parentElement, dontUpdateStyles) {
		this.element = $(element);
		if(!this.element) {
			this.element = $(document.createElement("div"));
			if($(parentElement)) {
      			$(parentElement).appendChild(this.element);
			}
      		else {
	      		document.body.appendChild(this.element);
      		}
      	}
		//http://jira.jboss.com/jira/browse/RF-2068
		//this.element.wrapper = this;
		if(!this.element.parentNode && $(parentElement)) {
			$(parentElement).appendChild(this.element);
		}

      	if(!this.element.id) {
			this.element.id = "ClientUI_Box" + ClientUI_common_box_Box_idGenerator++;
		}
		if(!dontUpdateStyles) {
	      	this.element.setStyle({overflow: 'hidden'});
	      	this.element.setStyle({whiteSpace: 'nowrap'});
		}
	},
	
	setParent: function(newParent) {
		if(this.element.parentNode) {
			this.element.parentNode.removeChild(this.element);
		}
		if(newParent) {
			if(newParent.getElement) {
				newParent = newParent.getElement();
			}
			$(newParent).appendChild(this.element);			
		}
		return this;
	},
	getElement: function() {
		return this.element;
	},
	getHeight: function() {
		var el = this.getElement();
		if(el.tagName.toLowerCase() != "body") {
			var h = el.offsetHeight;
			return h>0 ? h : (this.element.boxHeight ? parseInt(this.element.boxHeight) : 0);
		}

		if (self.innerHeight) { // all except Explorer
			return self.innerHeight;
		}
		else if (document.documentElement && document.documentElement.clientHeight) {
			// Explorer 6 Strict Mode
			return document.documentElement.clientHeight;
		}
		else if (document.body) { // other Explorers
			return document.body.clientHeight;
		}
	},
	isModified: false,
	setHeight: function(newHeight) {
		this.element.boxHeight = newHeight;
		if(Validators.IsNumber(newHeight)) {
			if(newHeight<0) newHeight = 0;
			newHeight += "px";	
		}
		this.element.setStyle({height: newHeight});
		isModified = true;
		return this;
	},
	getWidth: function() {
		var el = this.getElement();
		if(el.tagName.toLowerCase() != "body") {
			var w = el.offsetWidth;
			return w>0 ? w : (this.element.boxWidth ? parseInt(this.element.boxWidth) : 0);
		}
			
		if (self.innerHeight) {// all except Explorer
			return self.innerWidth;
		}
		else if (document.documentElement && document.documentElement.clientHeight) {
			// Explorer 6 Strict Mode
			return document.documentElement.clientWidth;
		}
		else if (document.body) { // other Explorers
			return document.body.clientWidth;
		}			
	},
	setWidth: function(newWidth) {
		this.element.boxWidth = newWidth;
		if(Validators.IsNumber(newWidth)) {
			if(newWidth<0) newWidth = 0;
			newWidth += "px";	
		}
		this.element.setStyle({width: newWidth});
		isModified = true;
		return this;
	},
	moveToX: function(x) {
		if(Validators.IsNumber(x)) {x += "px";}
		this.getElement().setStyle({left: x});
		isModified = true;
		return this;		
	},
	moveToY: function(y) {
		if(Validators.IsNumber(y)) {y += "px";}
		this.getElement().setStyle({top: y});
		isModified = true;
		return this;
	},
	moveTo: function(x, y) {
		this.moveToX(x);
		this.moveToY(y);
		return this;
	},
	hide: function() {
		Element.hide(this.element);
		isModified = true;
		return this;
	},
	show: function() {
		Element.show(this.element);
		isModified = true;
		return this;
	},
	updateLayout: function() {
		isModified = false;
		return this;
	},
	getViewportWidth: function() {
		if(this.getElement().tagName.toLowerCase() != "body") {
			var width = 0;
			if( this.getElement().clientWidth ) {
			    width = this.getElement().clientWidth;
			}
			else if( this.getElement().innerWidth ) {
			    width = this.getElement().innerWidth - getScrollerWidth();
			}
			  
			if(ClientUILib.isGecko) {
			  	width -= this.getPadding("lr");
			}
			return width;
		}
		
		return this.getWidth();
	},
	getViewportHeight: function() {
		if(this.getElement().tagName.toLowerCase() != "body") {
			var height = 0;
			if( this.getElement().clientHeight ) {
			    height = this.getElement().clientHeight;
			}
			else if( this.getElement().innerHeight ) {
			    height = this.getElement().innerHeight - getScrollerWidth();
			}
			  
			if(ClientUILib.isGecko) {
			  	height -= this.getPadding("tb");
			}
			return height;
		}
		return this.getHeight();
	},	
	/**
     * Gets the width of the border(s) for the specified side(s)
     * @param {String} side Can be t, l, r, b or any combination of those to add multiple values. For example, 
     * passing lr would get the border (l)eft width + the border (r)ight width.
     * @return {Number} The width of the sides passed added together
     */
    getBorderWidth : function(side){
        return this.getStyles(side, this.borders);
    },
    
    /**
     * Gets the width of the padding(s) for the specified side(s)
     * @param {String} side Can be t, l, r, b or any combination of those to add multiple values. For example, 
     * passing lr would get the padding (l)eft + the padding (r)ight.
     * @return {Number} The padding of the sides passed added together
     */
    getPadding : function(side){
        return this.getStyles(side, this.paddings);
    },	
	getStyles : function(sides, styles){
        var val = 0;
        for(var i = 0, len = sides.length; i < len; i++){
             var w = parseInt(this.getElement().getStyle(styles[sides.charAt(i)]), 10);
             if(!isNaN(w)) val += w;
        }
        return val;
    },
	makeAbsolute: function(keepPos) {
		if(keepPos) {
			Position.absolutize(this.getElement());	
		}
		else {
			this.getElement().setStyle({position: 'absolute'});
		}
		return this;
	},
	getX: function() {
		return this.getElement().offsetLeft;
	},
	getY: function() {
		return this.getElement().offsetTop;
	},
	setStyle: function(style) {
		this.getElement().setStyle(style);
		return this;
	},
	
	borders: {l: 'border-left-width', r: 'border-right-width', t: 'border-top-width', b: 'border-bottom-width'},
	paddings: {l: 'padding-left', r: 'padding-right', t: 'padding-top', b: 'padding-bottom'},
	margins: {l: 'margin-left', r: 'margin-right', t: 'margin-top', b: 'margin-bottom'}
	
});

if(!ClientUI_common_box_Box_idGenerator) {
var ClientUI_common_box_Box_idGenerator = 0;
};