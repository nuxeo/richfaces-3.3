if(!window.RichFaces) window.RichFaces = {};
if(!RichFaces.Menu) RichFaces.Menu = {};

/**
 * Fixes IE bug with incorrect layer width when set to auto
 * @param layer
 */
RichFaces.Menu.fitLayerToContent = function(layer) {
    if (!RichFaces.Menu.Layers.IE)
			return;

    var table = layer.childNodes[0];
    if (table) {
    	if (layer.style.width.indexOf("px")!=-1) {
    		var width = parseFloat(layer.style.width.substring(0,layer.style.width.indexOf('px')));
	        var tmpDims = Element.getDimensions(table);
    		if (tmpDims.width > width) layer.style.width = tmpDims.width + "px";
    	}
        //layer.style.height = dims.height + "px";
    } // if
}

RichFaces.Menu.removePx = function(e) {
	if ((e+"").indexOf("px")!=-1)
		return (e+"").substring(0,e.length-2);
	else
		return e;
}

RichFaces.Menu.Layers = {
	listl: new Array(),
	father: {},
	lwidthDetected:false,
	lwidth:{},
	back: new Array(),
	horizontals: {},
	layers: {},
	levels: ['','','','','','','','','','',''],
	detectWidth: function(){
		this.IE = (navigator.userAgent.indexOf('MSIE') > -1) && (navigator.userAgent.indexOf('Opera') < 0);
		
		if (this.IE) {
			var agentSplit = /MSIE\s+(\d+(?:\.\d+)?)/.exec(navigator.userAgent);
			if (agentSplit) {
				this.IE_VERSION = parseFloat(agentSplit[1]);
			}
		}
		
		this.NS = (navigator.userAgent.indexOf('Netscape') > -1);
    }
	,

	menuTopShift : -11,
	menuRightShift : 11,
	menuLeftShift : 0,
	shadowWidth: 0,
	thresholdY : 0,
	abscissaStep : 180,

	CornerRadius: 0,

	toBeHidden 		: new Array(),
	toBeHiddenLeft	: new Array(),
	toBeHiddenTop	: new Array(),

	layersMoved : 0,
	layerPoppedUp : '',
	layerTop : new Array(),
	layerLeft : new Array(),
	timeoutFlag : 0,
	useTimeouts : 1,
	timeoutLength : 500,
	showTimeOutFlag : 0,
	showTimeoutLength: 0,
	queuedId : '',

	destroy: function () {
		this.listl = null;
		this.father = null;
		var obj;
		for (var name in this.layers) {
			obj = this.layers[name];
			obj.layer = null;
			obj.items = null;
			$A(obj.bindings)
   			.each(
   				function(binding){
   					binding.remove();
   				}
   			);
		}
		this.layers = null;
	},

	LMPopUp:function(menuName, isCurrent, event) {
		if (!this.loaded || ( this.isVisible(menuName) && !isCurrent)) {
			return;
		}
		if (menuName == this.father[this.layerPoppedUp]) {
			this.LMPopUpL(this.layerPoppedUp, false, event);
		} else if (this.father[menuName] == this.layerPoppedUp) {
			this.LMPopUpL(menuName, true, event);
		} else {
			//this.shutdown();
			var foobar = menuName; //PY: var added
			do {
				this.LMPopUpL(foobar, true, event);
				foobar = this.father[foobar];
			} while (foobar);
		}
		this.layerPoppedUp = menuName;
	},

	isVisible: function(layer) {
		return ($(layer).style.display != 'none');
	},

	/**
	 *	@param menuName
	 *	@param visibleFlag
	 */
	LMPopUpL: function(menuName, visibleFlag, event) {
		if (!this.loaded) {
			return;
		}
		this.detectWidth();
		var menu = $(menuName);
		var eventResult = true;
		
		RichFaces.Menu.fitLayerToContent(menu);
		var visible = this.isVisible(menuName);
//		this.setVisibility(menuName, visibleFlag);
//		this.ieSelectWorkAround(menuName, visibleFlag);
		var menuLayer = this.layers[menu.id];
		
		if (visible && !visibleFlag) {
			if (menuLayer) {
				if (menuLayer.eventOnClose) {
					menuLayer.eventOnClose(event);
				}
				if (menuLayer.eventOnCollapse) {
					eventResult = menuLayer.eventOnCollapse(event);
				}
				if (menuLayer.refItem) {
					menuLayer.refItem.highLightGroup(false);
				}
			}
		} else if (!visible && visibleFlag) {
			if (menuLayer) {
				if (menuLayer.eventOnOpen) {
					menuLayer.eventOnOpen(event);
				}

				if (menuLayer.eventOnExpand) {
					eventResult = menuLayer.eventOnExpand(event);
				}
	
				if (menuLayer.level>0) {
					do {
						menuLayer = this.layers[(this.father[menuLayer.id])];
					} while (menuLayer.level > 0);
					
					if (menuLayer && menuLayer.eventOnGroupActivate) { 
						menuLayer.eventOnGroupActivate(event);
					}
				}
			}
		}
				
		if(eventResult != false) {
			this.setVisibility(menuName, visibleFlag);
			this.ieSelectWorkAround(menuName, visibleFlag);
		}
	},
	
	initIFrame: function(layer) {
				var menu = $(layer);			
				var iframe = new Insertion.Before(menu,
				"<iframe src=\"javascript:''\" id=\"" + menu.id + "_iframe\" style=\" position: absolute; z-index: 1;\" frameborder=\"0\" scrolling=\"no\" class=\"underneath_iframe\">" + "</iframe>");
				return iframe; 
	},

	ieSelectWorkAround: function(menuName, on){
		if((this.IE && this.IE_VERSION < 7) || this.NS) {
			var menu = $(menuName);
			menuName = menu.id;
          	var iframe = $(menuName + "_iframe");
           	if(!iframe&&on){
           		this.initIFrame(menu);
           		iframe = $(menuName + "_iframe");
           	}
			var nsfix = (this.NS ? 7 : 0);
			if(on){
				iframe.style.top = menu.style.top;
				iframe.style.left = menu.style.left;
				iframe.style.width = menu.offsetWidth + "px"
				iframe.style.height = menu.offsetHeight + "px"
				iframe.style.visibility = "visible";
			} else if(iframe) {
				iframe.style.visibility = "hidden";
			 }
		}
		
	},

	shutdown: function () {
		var needToResetLayers = false;
		for (var i=0; i<this.listl.length; i++) {
			var layerId = this.listl[i];
			if ($(layerId)) {
				this.LMPopUpL(layerId, false);
			} else {
				needToResetLayers = true;
			}
		}

		if (needToResetLayers) {
			this.resetLayers();
		}

		this.layerPoppedUp = '';
		if (this.Konqueror || this.IE5) {
			this.seeThroughElements(true);
		}
	},
	resetLayers: function() {
		var newList = new Array();
		for (i=0; i<this.listl.length; i++) {
			var layer = this.listl[i];
			if ($(layer)) {
				newList.push(layer);
			}
		}

		this.listl = newList;
	}
	,

	/**
	 * Set visibility
	 * @param	layer the layer to visibility
	 * @param	visible the boolean flag, if true to set visible layer from variable, otherwise - hide this layer
	 */
	setVisibility: function (layer, visible) {
		var tmpLayer = $(layer);

		if (visible) {
			tmpLayer.style.display = '';
		} else {
			if(tmpLayer.getElementsByTagName){
				var inputs = tmpLayer.getElementsByTagName('INPUT');
				if(inputs){
					$A(inputs).each(function(node){node.blur()});
				} // if
			} // if

			tmpLayer.style.display = 'none';
//			tmpLayer.style.left = "-"+tmpLayer.clientWidth;
//			Element.hide(tmpLayer);
		} // else
	},


	clearLMTO: function () {
		if (this.useTimeouts) {
			clearTimeout(this.timeoutFlag);
		}
	},

	setLMTO: function (ratio) {
		if(!ratio){
			ratio = this.timeoutLength;
		}
		if (this.useTimeouts) {
			clearTimeout(this.timeoutFlag);
			this.timeoutFlag = setTimeout('RichFaces.Menu.Layers.shutdown()', ratio);
		}
	},

	loaded:1,

	clearPopUpTO: function(){
		clearTimeout(this.showTimeOutFlag);
		this.iframe=null;
	},
	showMenuLayer: function (layerId, e, delay){
		this.clearPopUpTO();
		this.showTimeOutFlag = setTimeout(new RichFaces.Menu.DelayedPopUp(layerId, e, function(){this.layerId = null;}.bind(this)).show, delay);
		this.layerId = layerId;
	},
	showDropDownLayer: function (layerId, parentId, e, delay){
		this.clearPopUpTO();
		var menu = new RichFaces.Menu.DelayedDropDown(layerId, parentId, e);
		if (menu.show) {
			this.showTimeOutFlag = setTimeout(menu.show, delay);
		}
	},
	showPopUpLayer: function (layer, e){
		this.shutdown();
		this.detectWidth();
        this.LMPopUp(menuName, false);
		this.setLMTO(4);
	}
};

if (window.attachEvent) {
    window.attachEvent("onunload", function() {
    	var layers = RichFaces.Menu.Layers;
    	layers.destroy();
	});
}

/**
 * return true if defined document element or document body, otherwise return false
 */
RichFaces.Menu.getWindowElement = function() {
	return (document.documentElement || document.body);
}

RichFaces.Menu.getWindowDimensions = function() {
	var x,y;
	if (self.innerHeight) // all except Explorer
	{
		x = self.innerWidth;
		y = self.innerHeight;
	}
	else if (document.documentElement && document.documentElement.clientHeight)
	// Explorer 6 Strict Mode
	{
		x = document.documentElement.clientWidth;
		y = document.documentElement.clientHeight;
	}
	else if (document.body) // other Explorers
	{
		x = document.body.clientWidth;
		y = document.body.clientHeight;
	}
	return {width:x, height:y};
}

RichFaces.Menu.getWindowScrollOffset = function() {
	var x,y;
	if (typeof pageYOffset != "undefined") // all except Explorer
	{
		x = window.pageXOffset;
		y = window.pageYOffset;
	}
	else if (document.documentElement && document.documentElement.scrollTop)
	// Explorer 6 Strict
	{
		x = document.documentElement.scrollLeft;
		y = document.documentElement.scrollTop;
	}
	else if (document.body) // all other Explorers
	{
		x = document.body.scrollLeft;
		y = document.body.scrollTop;
	}

	return {top:y, left: x};
}

RichFaces.Menu.getPageDimensions = function() {
	var x,y;
	var test1 = document.body.scrollHeight;
	var test2 = document.body.offsetHeight;
	if (test1 > test2) {
		// all but Explorer Mac
		x = document.body.scrollWidth;
		y = document.body.scrollHeight;
	}
	else  {
		// Explorer Mac;
		// would also work in Explorer 6 Strict, Mozilla and Safari

		x = document.body.offsetWidth;
		y = document.body.offsetHeight;
	}

	return {width:x, height:y};
}


RichFaces.Menu.DelayedContextMenu = function(layer, e) {
    if (!e) {
        e = window.event;
    }
	//Event.stop(e);
    this.event = Object.clone(e);
    this.element = Event.element(e);
    this.layer = $(layer);
    this.show = function() {
		RichFaces.Menu.Layers.shutdown();

		var layer_display = this.layer.style.display;
		if (layer_display=='none') {
			this.layer.style.visibility='hidden';
			this.layer.style.display='';
		}
	        
		var cursorRect = Richfaces.jQuery.getPointerRectangle(this.event);
		Richfaces.jQuery.position(cursorRect, this.layer);

		this.layer.style.display=layer_display;
		this.layer.style.visibility='';
		
		RichFaces.Menu.Layers.LMPopUp(this.layer.id, false,e);
        RichFaces.Menu.Layers.clearLMTO();
    }.bind(this);
}


/**
 * Calculates for DROPDOWN
 */
RichFaces.Menu.DelayedDropDown = function(layer, elementId, e) {
	if (!e) {
		e = window.event;
	}

	//bugs RF-2102, RF-2119, RF-3639
	var node = (e.target || e.srcElement);
	var isLabel = false;
	while (node && node.id != elementId.id) {
		if (node.className == 'dr-label-text-decor rich-label-text-decor') {//TODO: replace magic 
			isLabel = true;
		}
		node = node.parentNode;
	}
	
	if (!isLabel) return;
	 
	this.event = e;
	this.element = $(elementId) || Event.element(e);
	this.layer = $(layer);
	Event.stop(e);
	
	this.listPositions = function(jp, dir) {
		var poss = new Array(new Array(2,1,4),new Array(1,2,3),new Array(4,3,2),new Array(3,4,1));
		var list = new Array();
		if (jp>0 && dir>0) {
	      	list.push({jointPoint: jp, direction: dir });
		} else if (jp>0 && dir==0) {
			for(var i=0;i<3;i++) {
				list.push({jointPoint: jp, direction: poss[jp-1][i] });
			}
		} else if (jp==0 && dir>0) {
			for(var i=0;i<3;i++) {
				list.push({jointPoint: poss[dir-1][i], direction: dir });
			}
		} else if (jp==0 && dir==0) {
	      	list.push({jointPoint: 4, direction: 3 });
	      	list.push({jointPoint: 1, direction: 2 });
	      	list.push({jointPoint: 3, direction: 4 });
	      	list.push({jointPoint: 2, direction: 1 });
		}
		return list;
	}.bind(this);

	this.calcPosition = function(jp, dir) {
		var layerLeft;
		var layerTop;
		switch (jp) {
			case 1:
				layerLeft = this.left;
				layerTop = this.top;
				break;
			case 2:
				layerLeft = this.right;
				layerTop = this.top;
				break;
			case 3:
				layerLeft = this.right;
				layerTop = this.bottom;
				break;
			case 4:
				layerLeft = this.left;
				layerTop = this.bottom;
				break;
		}
		switch (dir) {
			case 1:
				layerLeft -= this.layerdim.width;
				layerTop -= this.layerdim.height;
				break;
			case 2:
				layerTop -= this.layerdim.height;
				break;
			case 4:
				layerLeft -= this.layerdim.width;
		}
		return {left: layerLeft, top: layerTop};
	}.bind(this);

	this.show = function() {
		RichFaces.Menu.Layers.shutdown();
		
		var layer_display = this.layer.style.display;
		if (layer_display=='none')
		{
			this.layer.style.visibility='hidden';
			this.layer.style.display='';
		}

		var winOffset	= RichFaces.Menu.getWindowScrollOffset();
		var win			= RichFaces.Menu.getWindowDimensions();
		var pageDims	= RichFaces.Menu.getPageDimensions();


		var windowHeight = win.height;
		var windowWidth = win.width;

//		var screenOffset = Position.cumulativeOffset(this.element);
//		if (Element.getStyle(this.element, 'position') == 'absolute') {
//			screenOffset[0] = 0;
//			screenOffset[1] = 0;
//		}
		var screenOffset = Position.positionedOffset(this.element);
		var innerDiv = this.element.lastChild;
		var dim = Element.getDimensions(this.element);

		var parOffset = Position.cumulativeOffset(this.element);
		var divOffset = Position.cumulativeOffset(innerDiv);
		var deltaX = divOffset[0] - parOffset[0];
		var deltaY = divOffset[1] - parOffset[1];

		// parent element
		this.top	= screenOffset[1];
		this.left	= screenOffset[0];

		this.bottom = this.top + dim.height;
		this.right = this.left + dim.width;

		this.layerdim = Element.getDimensions(this.layer);

		var options = RichFaces.Menu.Layers.layers[this.layer.id].options;

		var jointPoint = 0;
		if (options.jointPoint) {
			var sJp = options.jointPoint.toUpperCase();
			jointPoint = sJp.indexOf('TL') != -1?1:jointPoint;
			jointPoint = sJp.indexOf('TR') != -1?2:jointPoint;
			jointPoint = sJp.indexOf('BR') != -1?3:jointPoint;
			jointPoint = sJp.indexOf('BL') != -1?4:jointPoint;
		}

		var direction = 0;
		if (options.direction) {
			var sDir = options.direction.toUpperCase();
			direction = sDir.indexOf('TOP-LEFT')    != -1?1:direction;
			direction = sDir.indexOf('TOP-RIGHT')   != -1?2:direction;
			direction = sDir.indexOf('BOTTOM-RIGHT')!= -1?3:direction;
			direction = sDir.indexOf('BOTTOM-LEFT') != -1?4:direction;
		}
		var hOffset = options.horizontalOffset;
		var vOffset = options.verticalOffset;

		var listPos = this.listPositions(jointPoint, direction);
		var layerPos;
		var foundPos = false;
		for (var i=0;i<listPos.length;i++) {
			layerPos = this.calcPosition(listPos[i].jointPoint, listPos[i].direction)
			if ((layerPos.left + hOffset >= winOffset.left) &&
				(layerPos.left + hOffset + this.layerdim.width - winOffset.left <= windowWidth) &&
				(layerPos.top + vOffset >= winOffset.top) &&
				(layerPos.top + vOffset + this.layerdim.height - winOffset.top <= windowHeight)) {
				foundPos = true;
				break;
			}
		}
		if (!foundPos) {
			layerPos = this.calcPosition(listPos[0].jointPoint, listPos[0].direction)
		}
		this.layer.style.left = layerPos.left + hOffset - deltaX - this.left + "px";
		this.layer.style.top = layerPos.top + vOffset - deltaY - this.top + "px";

	    this.layer.style.width = this.layer.clientWidth + "px";

		this.layer.style.display=layer_display;
		this.layer.style.visibility='';
		
		RichFaces.Menu.Layers.LMPopUp(this.layer.id, false);
		RichFaces.Menu.Layers.clearLMTO();
	}.bind(this);
}

RichFaces.Menu.DelayedPopUp = function(layer, e) {
	if (!e) {
		e = window.event;
	}

	this.event = e;

	var elt = Event.element(e);
	while (elt && (!elt.tagName || elt.tagName.toLowerCase() != 'div')) {
		elt = elt.parentNode;
	} //Event.findElement(e, 'div');

	this.element = elt;
	if (this.element.id.indexOf(":folder") == (this.element.id.length -7) ) {
		this.element = this.element.parentNode;
	}
	this.layer = $(layer);

    this.show = function() {
        if (!RichFaces.Menu.Layers.isVisible(this.layer) &&
        	RichFaces.Menu.Layers.isVisible(RichFaces.Menu.Layers.father[this.layer.id])) {
			
			var layer_display = this.layer.style.display;
			if (layer_display=='none')
			{
				this.layer.style.visibility='hidden';
				this.layer.style.display='';
			}
        	
	        this.reposition();
			
			this.layer.style.display=layer_display;
			this.layer.style.visibility='';
    	    
    	    RichFaces.Menu.Layers.LMPopUp(this.layer, false);
        }
    }.bind(this);
}

RichFaces.Menu.DelayedPopUp.prototype.reposition = function() {
	var windowShift = RichFaces.Menu.getWindowScrollOffset();
	var body = RichFaces.Menu.getWindowDimensions();
    var windowHeight = body.height;
    var windowWidth = body.width;
    var scrolls = {top:0, left:0};
    var screenOffset = Position.positionedOffset(this.element);
    var leftPx = RichFaces.Menu.removePx(this.element.parentNode.parentNode.style.left);
    var topPx = RichFaces.Menu.removePx(this.element.parentNode.parentNode.style.top);
    screenOffset[0]+=Number(leftPx);
    screenOffset[1]+=Number(topPx);
    var cumulativeOffset = Position.cumulativeOffset(this.element);
    var labelOffset = [cumulativeOffset[0] - screenOffset[0], cumulativeOffset[1] - screenOffset[1]];
    var dim = Element.getDimensions(this.element);
    var top = screenOffset[1] + scrolls.top;
    var bottom = top + dim.height;
    var left = screenOffset[0] + scrolls.left;
    var right = left + dim.width;
    var layerdim = Element.getDimensions(this.layer);

	var options = RichFaces.Menu.Layers.layers[this.layer.id].options;
	var dir = 0;
	var vDir = 0;
	if (options.direction) {
		strDirection = options.direction.toUpperCase();
		dir = strDirection.indexOf('LEFT')!=-1?1:dir;
		dir = strDirection.indexOf('RIGHT')!=-1?2:dir;
		if (dir>0) {
			if (strDirection.indexOf('LEFT-UP')!=-1 || 
				strDirection.indexOf('RIGHT-UP')!=-1) vDir = 1;
			if (strDirection.indexOf('LEFT-DOWN')!=-1 || 
				strDirection.indexOf('RIGHT-DOWN')!=-1) vDir = 2;
		}
	}

    var layerLeft = right;
    var layerTop = top - this.layer.firstChild.firstChild.offsetTop;

    if (dir == 0) {
	    if (layerLeft + layerdim.width + labelOffset[0] - windowShift.left >= windowWidth) {
	        var invisibleRight = layerLeft + layerdim.width + labelOffset[0] - windowShift.left - windowWidth;
	        layerLeft = left - layerdim.width;
	    }

	    if (layerLeft  + labelOffset[0] < 0) {
	        if (Math.abs(layerLeft + labelOffset[0]) > invisibleRight) {
	        	layerLeft = right;
	        }
	    }

    } else if (dir == 1) {
        layerLeft = left - layerdim.width;
    }

	if (vDir != 2) {
	    if (layerTop + layerdim.height + labelOffset[1] - windowShift.top >= windowHeight 
	    	|| vDir == 1) {
	    	var invisibleBottom = layerTop + layerdim.height + labelOffset[1] - windowShift.top - windowHeight;
	    	var items = this.layer.firstChild.childNodes;
	    	if (items.length > 1) {
	    		var lastItem = items[items.length-2];
	//    		    var layerOffset = Position.cumulativeOffset(this.layer);
		   		var itemOffset = Position.positionedOffset(lastItem);
	//			    layerTop = top -(itemOffset[1]-layerOffset[1]);
				layerTop = top - itemOffset[1];
				if (vDir == 0) {
				    if (layerTop < 0) {
				    	if (Math.abs(layerTop) > invisibleBottom) layerTop = top;
				    }
				}
	    	}
	    }
	}

/*    if (layerLeft + layerdim.width >= windowWidth) {
        layerLeft = left - layerdim.width + RichFaces.Menu.Layers.shadowWidth;
    }

    if (layerLeft < 0) {
        layerLeft = 0;
    }

	// search offsetTop (ch-1351)
    var layerOffset = Position.cumulativeOffset(this.layer);
  	var items = document.getElementsByClassName("exadel_menuItem",this.layer);
   	var nodes = document.getElementsByClassName("exadel_menuNode",this.layer);
   	var firstItem = (items.length>0) ? items[0] : ((nodes.length>0) ? nodes[0] : null);
   	if (firstItem) {
	   	if (nodes.length>0 && firstItem.offsetTop>nodes[0].offsetTop) firstItem = nodes[0];
	   	var itemOffset = Position.cumulativeOffset(firstItem);
	    layertop = top -(itemOffset[1]-layerOffset[1]);
	    if (layertop + layerdim.height >= windowHeight) {
		   	var lastItem = (items.length>0) ? items[items.length-1] : nodes[nodes.length-1];
	    	if (nodes.length>0 && lastItem.offsetTop<nodes[nodes.length-1].offsetTop) lastItem = nodes[nodes.length-1];
	   		itemOffset = Position.cumulativeOffset(lastItem);
		    layertop = top -(itemOffset[1]-layerOffset[1]);
	    }
    } else layertop = top - RichFaces.Menu.Layers.CornerRadius;

    if (layertop < 0) {
        layertop = 0;
    } */

    this.layer.style.left = layerLeft + "px";
    this.layer.style.top = layerTop + "px";

    this.layer.style.width = this.layer.clientWidth + "px";

}
/**
 * set to true when a dropdown box inside menu receives focus
 */
RichFaces.Menu.selectOpen = false;
RichFaces.Menu.MouseIn = false;


RichFaces.Menu.Layer = Class.create();
RichFaces.Menu.Layer.prototype = {
	initialize: function(id,delay, hideDelay, selectedClass){
		RichFaces.Menu.Layers.listl.push(id);
   		this.id = id;
   		this.layer = $(id);
   		this.level = 0;
   		this.delay = delay;
   		if (hideDelay){
   		 this.hideDelay=hideDelay;
   		}
   		else{
   		 this.hideDelay=hideDelay;
   		}
        RichFaces.Menu.fitLayerToContent(this.layer);
        this.items = new Array();
   		RichFaces.Menu.Layers.layers[id] = this;
   		this.bindings = new Array();

		//Usually set on DD menu to true
		this.highlightParent = true;


        this.mouseover =
	       function(e){
	             RichFaces.Menu.MouseIn=true;
		         RichFaces.Menu.Layers.clearLMTO();
				if (this.shouldHighlightParent() && !this.isWithin(e)) {
			      this.highlightLabel();
				}

		         Event.stop(e);
	       }.bindAsEventListener(this);

         this.mouseout =
	         function(e){
 	              RichFaces.Menu.MouseIn = false;
		          if (!RichFaces.Menu.selectOpen) {
			         RichFaces.Menu.Layers.setLMTO(this.hideDelay);
		          }
				if (this.shouldHighlightParent() && !this.isWithin(e)) {
			      this.unHighlightLabel();
				}
		          Event.stop(e);
	         }.bindAsEventListener(this);



 		var binding = new RichFaces.Menu.Layer.Binding (
 				this.id,
 				"mouseover",
 				this.mouseover);

 		this.bindings.push(binding);
 		binding.refresh();
 		binding = new RichFaces.Menu.Layer.Binding (
 				this.id,
 				"mouseout",
 				this.mouseout);
 		this.bindings.push(binding);
 		binding.refresh();

        var arrayinp=$A(this.layer.getElementsByTagName("select")); // PY: var added
        for(i=0; i<arrayinp.length; i++){
					var openSelectb = this.openSelect.bindAsEventListener(this);
					var closeSelectb = this.closeSelect.bindAsEventListener(this);
					Event.observe(arrayinp[i], "focus", openSelectb);
					Event.observe(arrayinp[i], "blur", closeSelectb);
				    //var MouseoverInInputb = RichFaces.Menu.Layer.MouseoverInInput.bindAsEventListener(this);
				    var MouseoverInInputb = this.MouseoverInInput.bindAsEventListener(this);
                    //var MouseoutInInputb = RichFaces.Menu.Layer.MouseoutInInput.bindAsEventListener(this);
                    var MouseoutInInputb = this.MouseoutInInput.bindAsEventListener(this);
					Event.observe(arrayinp[i], "mouseover", MouseoverInInputb);
					Event.observe(arrayinp[i], "mouseout", MouseoutInInputb);

					//var OnKeyPressb = RichFaces.Menu.Layer.OnKeyPress.bindAsEventListener(this);
					var OnKeyPressb = this.OnKeyPress.bindAsEventListener(this);
					Event.observe(arrayinp[i], "keypress", OnKeyPressb);
        }

        arrayinp=$A(this.layer.getElementsByTagName("input"));
        for(i=0; i<arrayinp.length; i++){
					var openSelectb = this.openSelect.bindAsEventListener(this);
					var closeSelectb = this.closeSelect.bindAsEventListener(this);
					Event.observe(arrayinp[i], "focus", openSelectb);
					Event.observe(arrayinp[i], "blur", closeSelectb);
				    //var MouseoverInInputb = RichFaces.Menu.Layer.MouseoverInInput.bindAsEventListener(this);
				    var MouseoverInInputb = this.MouseoverInInput.bindAsEventListener(this);
                    //var MouseoutInInputb = RichFaces.Menu.Layer.MouseoutInInput.bindAsEventListener(this);
                    var MouseoutInInputb = this.MouseoutInInput.bindAsEventListener(this);
					Event.observe(arrayinp[i], "mouseover", MouseoverInInputb);
					Event.observe(arrayinp[i], "mouseout", MouseoutInInputb);
					var OnKeyPressb = this.OnKeyPress.bindAsEventListener(this);
					Event.observe(arrayinp[i], "keypress", OnKeyPressb);
        }

        arrayinp=$A(this.layer.getElementsByTagName("textarea"));
        for(i=0; i<arrayinp.length; i++){
					var openSelectb = this.openSelect.bindAsEventListener(this);
					var closeSelectb = this.closeSelect.bindAsEventListener(this);
					Event.observe(arrayinp[i], "focus", openSelectb);
					Event.observe(arrayinp[i], "blur", closeSelectb);
				    //var MouseoverInInputb = RichFaces.Menu.Layer.MouseoverInInput.bindAsEventListener(this);
				    var MouseoverInInputb = this.MouseoverInInput.bindAsEventListener(this);
                    //var MouseoutInInputb = RichFaces.Menu.Layer.MouseoutInInput.bindAsEventListener(this);
                    var MouseoutInInputb = this.MouseoutInInput.bindAsEventListener(this);
					Event.observe(arrayinp[i], "mouseover", MouseoverInInputb);
					Event.observe(arrayinp[i], "mouseout", MouseoutInInputb);
        }

/*		if(window.A4J && A4J.AJAX ){
			var listener = new A4J.AJAX.Listener(this.rebind.bindAsEventListener(this));
			A4J.AJAX.AddListener(listener);
		}		*/
		
		if (selectedClass) {
			this.selectedClass = selectedClass;
		}
 	},

	getLabel : function() {
		return RichFaces.Menu.Layers.layers[this.layer.id].layer.parentNode.parentNode;
	},
	
	highlightLabel: function() {
		var label1 = $(this.getLabel());
		RichFaces.Menu.Items.replaceClasses(label1, 
			['dr-menu-label-unselect', 'rich-ddmenu-label-unselect'], 
			['dr-menu-label-select','rich-ddmenu-label-select']);
		if (this.selectedClass) {
			Element.addClassName(label1, this.selectedClass);
		}
	},

	unHighlightLabel: function() {
		var label1 = $(this.getLabel());
		RichFaces.Menu.Items.replaceClasses(label1, 
			['dr-menu-label-select','rich-ddmenu-label-select'],
			['dr-menu-label-unselect', 'rich-ddmenu-label-unselect']);
		if (this.selectedClass) {
			Element.removeClassName(label1, this.selectedClass);
		}
	},
	
	shouldHighlightParent : function() {
		var result = this.highlightParent;
		var parent = null;
		if (result && (parent = this.getParentLayer())) {
			result &= parent.shouldHighlightParent();
		}
		return result;
	},
	
	getParentLayer: function() {
		return this.level > 0 ? RichFaces.Menu.Layers.layers[(RichFaces.Menu.Layers.father[this.id])] : null;
	},
	
	isWithin : function(event){
		//RF-2745 explicitly extend event to make DOM methods work
		event = Event.extend(event);
		
		var within = true;
		var targetElement = event.relatedTarget;

		try {
			if (targetElement && targetElement.className=="anonymous-div")
			 return false;
		} catch (e) {
			return false;
		}
		
		while (targetElement && targetElement.nodeType!=1) targetElement = targetElement.parentNode;		
		
		var srcElement = event.target;
		var layer = $(this.id);
		if (targetElement) {
			within = $(targetElement).descendantOf(layer);
		}
		
		within &= $(srcElement).descendantOf(layer);
		
		return within;
	},

     openSelect:  function(event){
	       RichFaces.Menu.selectOpen = true;
	       var ClickInputb = this.ClickInput.bindAsEventListener(this);
           Event.observe(Event.element(event), "click", this.ClickInput);

     },



     closeSelect: function(event){
	   RichFaces.Menu.selectOpen = false;
       var ClickInputb = this.ClickInput.bindAsEventListener(this);
       Event.stopObserving(Event.element(event), "click", this.ClickInput);
       if (RichFaces.Menu.MouseIn == false){
	     RichFaces.Menu.Layers.setLMTO(this.hideDelay);
	   }
     },



     OnKeyPress: function(event){

      if(event.keyCode==13){
        RichFaces.Menu.Layers.setLMTO(this.hideDelay);
      }
    },


    MouseoverInInput: function(event){
      //alert("event rabotaet "+ event.target);
      var ClickInputb = this.ClickInput.bindAsEventListener(this);
      Event.observe(Event.element(event), "click", this.ClickInput);
    },


    ClickInput: function(event){
         //alert("event rabotaet dsds ");
         Event.stop(event || window.event);
        return false;
    },


    MouseoutInInput: function(event){
          var ClickInputb = this.ClickInput.bindAsEventListener(this);
          Event.stopObserving(Event.element(event), "click", this.ClickInput);

    },

 	rebind:function(){
   		$A(this.bindings)
   			.each(
   				function(binding){
   					binding.refresh();
   				}
   			);
 	},
	showMe: function(e){
   		this.closeSiblings(e);
   		//LOG.a4j_debug('show me ' + this.id +' ' +this.level);
   		RichFaces.Menu.Layers.showMenuLayer(this.id, e, this.delay);
   		RichFaces.Menu.Layers.levels[this.level] = this;
//		if (this.eventOnOpen) this.eventOnOpen();
	},
	closeSiblings: function(e){
		//LOG.a4j_debug('closeASiblins ' + this.id +' ' +this.level);
		if(RichFaces.Menu.Layers.levels[this.level] && RichFaces.Menu.Layers.levels[this.level].id != this.id){
   			for(var i = this.level; i < RichFaces.Menu.Layers.levels.length; i++){
   				if(RichFaces.Menu.Layers.levels[i]) {
   					RichFaces.Menu.Layers.levels[i].hideMe();
   					//RichFaces.Menu.Layers.levels[i] = '';
   				}
   			}
		}
	},
	closeMinors: function(id){
		//LOG.a4j_debug('closeMinors ' + this.id +' ' +this.level);
		var item = this.items[id];
//		if(!item.childMenu){
			//LOG.a4j_debug('hiding menus ' + this.id +' ' +this.level);
			for(var i = this.level + (!item.childMenu?1:2); i < RichFaces.Menu.Layers.levels.length; i++){
				if(RichFaces.Menu.Layers.levels[i]) {
					//LOG.a4j_debug('hide ' +RichFaces.Menu.Layers.levels[i]);
					RichFaces.Menu.Layers.levels[i].hideMe();
				}
			}
//		}
		if (item.menu.refItem) {
			item.menu.refItem.highLightGroup(true);
		}

	},
	//addItem: function(itemId, hoverClass, plainClass, hoverStyle, plainStyle){
	addItem: function(itemId, options) {
		var dis = this;
		var item = new RichFaces.Menu.Item(itemId, this, options || {});
		//item.menu = this;
		this.items[itemId] = item;
		return this;
	},
	hideMe: function(e){
		RichFaces.Menu.Layers.clearPopUpTO();
		RichFaces.Menu.Layers.levels[this.level] = null;
		RichFaces.Menu.Layers.LMPopUpL(this.id, false,e);
//		if (this.eventOnClose) this.eventOnClose();
	},
	asDropDown: function(topLevel, bindElementId, onEvt, offEvt, options){
		this.options = options || {};
		if (this.options.ongroupactivate){
			this.eventOnGroupActivate = this.options.ongroupactivate.bindAsEventListener(this);
		}
		if (this.options.onitemselect){
			this.eventOnItemSelect = this.options.onitemselect.bindAsEventListener(this);
		}
		if (this.options.oncollapse){
			this.eventOnCollapse = this.options.oncollapse.bindAsEventListener(this);
		}
		if (this.options.onexpand){
			this.eventOnExpand = this.options.onexpand.bindAsEventListener(this);
		}

//		if($(topLevel)){  CH-1518
			var menuOn = function(e) {
                RichFaces.Menu.Layers.showDropDownLayer(this.id, topLevel, e,this.delay);
			};
			
			var mouseover = function(e) {
                if (!options.disabled && !RichFaces.Menu.isWithin(e, $(topLevel))) {
                	this.highlightLabel();
                }
			};

			var menuOff = function(e) {
				RichFaces.Menu.Layers.setLMTO(this.hideDelay);
				RichFaces.Menu.Layers.clearPopUpTO();
			};
			
			var mouseout = function(e){
                if (!options.disabled && !RichFaces.Menu.isWithin(e, $(topLevel))) {
	               	this.unHighlightLabel();
	            }
			};

			if(!onEvt){
				onEvt = 'onmouseover';
			}
			onEvt = this.eventJsToPrototype(onEvt);
			if(!offEvt){
				offEvt = 'onmouseout';
			}
			offEvt = this.eventJsToPrototype(offEvt);

			var addBinding = function(elementId, eventName, handler) {
		 		var binding = new RichFaces.Menu.Layer.Binding(elementId, eventName, handler);
		 		this.bindings.push(binding);
		 		binding.refresh();
			}.bind(this);

			//if (onEvt == 'mouseover') {
				addBinding(topLevel, onEvt, function(e) {
					menuOn.call(this, e);
					mouseover.call(this, e);
				}.bindAsEventListener(this));
			//} else {
			//	addBinding(bindElementId, onEvt, menuOn.bindAsEventListener(this));
			//	addBinding(topLevel, 'mouseover', mouseover.bindAsEventListener(this));
			//}
			
			if (offEvt == 'mouseout') {
				addBinding(topLevel, offEvt, function(e) {
					menuOff.call(this, e);
					mouseout.call(this, e);
				}.bindAsEventListener(this));
			} else {
				addBinding(bindElementId, offEvt, menuOff.bindAsEventListener(this));
				addBinding(topLevel, 'mouseout', mouseout.bindAsEventListener(this));
			}
			
	 		RichFaces.Menu.Layers.horizontals[this.id] = topLevel;
//		}
		return this;
	},

	asSubMenu: function(parentv, refLayerName, evtName, options){
		this.options = options || {};
		if (this.options.onclose){
			this.eventOnClose = this.options.onclose.bindAsEventListener(this);
		}
		if (this.options.onopen){
			this.eventOnOpen = this.options.onopen.bindAsEventListener(this);
		}

		if(!evtName){
			evtName = 'onmouseover';
		}
		evtName = this.eventJsToPrototype(evtName);
		this.level = RichFaces.Menu.Layers.layers[parentv].level + 1;
   		RichFaces.Menu.Layers.father[this.id] = parentv;
   		if(!refLayerName){
   			refLayerName = parentv;
   		}
   		var refLayer = $(refLayerName);
   		this.refItem = RichFaces.Menu.Layers.layers[parentv].items[refLayerName];
   		this.refItem.childMenu = this;
 		var binding = new RichFaces.Menu.Layer.Binding(refLayerName, evtName,	this.showMe.bindAsEventListener(this));
 		this.bindings.push(binding);
 		binding.refresh();


        // set  parents hideDelay
        var menuLayer=this;
		while (menuLayer.level > 0) {
					menuLayer = RichFaces.Menu.Layers.layers[(RichFaces.Menu.Layers.father[menuLayer.id])];
		}
		if (menuLayer && menuLayer.hideDelay){
			this.hideDelay=menuLayer.hideDelay;
		}


		return this;
	},
	asContextMenu: function(options){
   		this.highlightParent = false;
 		this.options = options || {};
 		
 		if (this.options.ongroupactivate){
			this.eventOnGroupActivate = this.options.ongroupactivate.bindAsEventListener(this);
		}

		if (this.options.onitemselect){
			this.eventOnItemSelect = this.options.onitemselect.bindAsEventListener(this);
		}
//  	see RF-4592 for details		
//		if (this.options.oncollapse){
//			this.eventOnCollapse = this.options.oncollapse.bindAsEventListener(this);
//		}
		
		if (this.options.oncollapse){
			this.eventOnCollapse = this.options.oncollapse.bindAsEventListener(this,"collapse");
		}
		
//		if (this.options.onexpand){
//			this.eventOnExpand = this.options.onexpand.bindAsEventListener(this);
//		}

 		if (this.options.onexpand){
 			this.eventOnExpand = this.invokeEvent.bindAsEventListener(this,"expand");
 		}
 		  		
   		//TODO: clarify
 		return this;
	},
	
	invokeEvent : function (event, eventName) {
		var eventFunction = this.options['on'+eventName];
		var result;
		
		if (eventFunction) {
			var eventObj;
			if (event) {
				eventObj = event;
			} else if( document.createEventObject ) {
				eventObj = document.createEventObject();
			} else if( document.createEvent )	{
				eventObj = document.createEvent('Events');
				eventObj.initEvent(eventName, true, false );
			}
			result = eventFunction.call(this, eventObj);
		}
		if (result!=false) result = true;
		return result;
	},
	
	eventJsToPrototype: function(evtName){
		var indexof = evtName.indexOf('on');
		if(indexof  >= 0){
			evtName = evtName.substr(indexof + 2);
		}
		return evtName;
	}

};

RichFaces.Menu.Layer.Binding = Class.create();
RichFaces.Menu.Layer.Binding.prototype = {
	initialize:function(objectId, eventname, handler){
		this.objectId = objectId;
		this.eventname = eventname;
		this.handler = handler;
	},
	refresh:function(){
		/*LOG.a4j_debug("rebinding " + $H(this).inspect());*/
		var obj = $(this.objectId);
		if(obj){
			Event.stopObserving(obj, this.eventname, this.handler);
			Event.observe(obj, this.eventname, this.handler);
			return true;
		}
		return false;
	},
	remove:function(){
		var obj = $(this.objectId);
		if (obj) {
			Event.stopObserving(obj, this.eventname, this.handler);
			this.handler=null;
		}
	}
};
RichFaces.Menu.Items = {
	itemClassNames: ['rich-menu-item-enabled'],
	groupClassNames: ['rich-menu-group-enabled'],
	itemHoverClassNames: ['rich-menu-item-hover'],
	groupHoverClassNames: ['rich-menu-group-hover'],
	iconClassNames : [],
	hoverIconClassNames: ['dr-menu-icon-selected', 'rich-menu-item-icon-selected'],
	labelClassNames: [],
	hoverLabelClassNames: ['rich-menu-item-label-selected'],
	
	replaceClasses: function(element, toRemove, toAdd) {
		var e = $(element);
		$A(toRemove).each(function(className) {e.removeClassName(className)});
		$A(toAdd).each(function(className) {e.addClassName(className)});
	},
	
	getHoverClassNames: function (item) {
		if (item.options.flagGroup == 1) {
			return this.groupHoverClassNames;
		} else {
			return this.itemHoverClassNames;
		}
	},
	
	getClassNames: function (item) {
		if (item.options.flagGroup == 1) {
			return this.groupClassNames;
		} else {
			return this.itemClassNames;
		}
	},
	
	onmouseover: function(item) {
		var element = item.getElement();
		var icon = item.getIcon();
		var labl = item.getLabel();

		var hoverClass = item.getHoverClasses();
		var iconHoverClass = item.getIconHoverClasses();
		var labelHoverClass = item.getLabelHoverClasses();
		
		var inlineStyle = item.getInlineStyle();
		var hoverStyle = item.getHoverStyle();
		element.style.cssText = inlineStyle.concat(hoverStyle);
				
		this.replaceClasses(element, this.getClassNames(item), this.getHoverClassNames(item).concat(hoverClass));
		this.replaceClasses(icon, this.iconClassNames, this.hoverIconClassNames.concat(iconHoverClass));
		this.replaceClasses(labl, this.labelClassNames, this.hoverLabelClassNames.concat(labelHoverClass));
	},
	onmouseout : function(item) {
		var element = item.getElement();
		var icon = item.getIcon();
		var labl = item.getLabel();

		var hoverClass = item.getHoverClasses();
		var iconHoverClass = item.getIconHoverClasses();
		var labelHoverClass = item.getLabelHoverClasses();

		var inlineStyle = item.getInlineStyle();
		element.style.cssText = inlineStyle;

		this.replaceClasses(element, this.getHoverClassNames(item).concat(hoverClass), this.getClassNames(item));
		this.replaceClasses(icon, this.hoverIconClassNames.concat(iconHoverClass), this.iconClassNames);
		this.replaceClasses(labl, this.hoverLabelClassNames.concat(labelHoverClass), this.labelClassNames);
					}

};
RichFaces.Menu.isWithin = function (event, element) {
	var within = false;
	Event.extend(event);

	var targetElement = event.relatedTarget;

	try {
		if (targetElement && targetElement.className=="anonymous-div")
		 return false;
	} catch (e) {
		return false;
	}
	
	while (targetElement && targetElement.nodeType!=1) 
	{
		targetElement = targetElement.parentNode;
	}
	
	if (targetElement) {
		within = targetElement == element || 
			$(targetElement).descendantOf(element);
		}

	return within;
};

RichFaces.Menu.Item = Class.create({
	initialize: function(id, menu, options) {
		this.options = options;
		this.id = id;
		this.menu = menu;
		this.mouseOver = false;
		


		var binding; 

		binding = new RichFaces.Menu.Layer.Binding (
 				id,
 				"mouseover",
 				this.onmouseover.bindAsEventListener(this));
 		menu.bindings.push(binding);
 		binding.refresh();
 		
 		binding = new RichFaces.Menu.Layer.Binding (
 				id,
 				"mouseout",
 				this.onmouseout.bindAsEventListener(this));
 		menu.bindings.push(binding);
 		binding.refresh();

 		binding = new RichFaces.Menu.Layer.Binding (
 				id,
 				"click",
 				this.onclick.bindAsEventListener(this));
 		menu.bindings.push(binding);
 		binding.refresh();
	},


	onclick: function(e){
		if (this.options.closeOnClick) {
			var menuLayer = this.menu;
			while (menuLayer.level > 0) {
				menuLayer = RichFaces.Menu.Layers.layers[(RichFaces.Menu.Layers.father[menuLayer.id])];
 			}
			if (menuLayer && menuLayer.eventOnItemSelect) menuLayer.eventOnItemSelect();
            RichFaces.Menu.Layers.shutdown();
 		}
 		if(!this.options.disabled) {
			RichFaces.Menu.Items.onmouseout(this);
 		}
	},
	getElement: function() {
		return $(this.id);
	},
	getIcon: function() {
		return $(this.id + ":icon");
	},
	getLabel: function() {
		return $(this.id + ":anchor");
	},
	getInlineStyle: function() {
		return this.options.style || "";
	},
	getHoverStyle: function() {
		return this.options.selectStyle || "";
	},
	getHoverClasses: function() {
		return $A(this.options.selectClass).compact();
	},
	getIconHoverClasses : function() {
		return $A(this.options.iconHoverClass).compact();
	},
	getLabelHoverClasses : function() {
		return $A(this.options.labelHoverClass).compact();
	},

	isDisabled : function() {
		//console.log(this.id + (!!this.options.disabled));
		return this.options.disabled || false;
	},
	onmouseover : function(event) {
		var element = this.getElement();
		
		if (this.options.onmouseover && !this.options.disabled) {
			if (this.options.onmouseover.call(element, event) == false) {
				Event.stop(event);
				return;
			};
		}
		if (RichFaces.Menu.isWithin(event, element)) {
			return;
		}		
		this.menu.closeMinors(this.id);
		if (this.isDisabled()) {
			return;
		}
		if (this.options.flagGroup == 1) {
			this.mouseOver = true;
			this.highLightGroup(true);
		}
		RichFaces.Menu.Items.onmouseover(this);
	},
	onmouseout : function(event) {
		Event.extend(event);
		//window.status = $(event.relatedTarget).inspect();
		if (this.options.onmouseout && !this.options.disabled) {
			if (this.options.onmouseout.call(element, event) == false) {
				Event.stop(event);
				return;
			};
 			}
		var element = this.getElement();
		if (RichFaces.Menu.isWithin(event, element)) {
			return;
		}
		if (this.isDisabled()) {
			return;
		}
		if (this.options.flagGroup == 1) {
					this.mouseOver = false;
					this.highLightGroup(false);
 		}
		RichFaces.Menu.Items.onmouseout(this);
	},
	highLightGroup: function(light)  {
		if (light) {
			Element.removeClassName(this.id,"rich-menu-group-enabled");

			Element.addClassName(this.id,"rich-menu-group-hover");
			if (this.options.selectClass) {
				Element.addClassName(this.id, this.options.selectClass);
			}

			Element.addClassName(this.id+":icon","rich-menu-item-icon-selected");
			Element.addClassName(this.id+":anchor","rich-menu-item-label-selected");
			Element.addClassName(this.id+":icon","rich-menu-group-icon-selected");
			Element.addClassName(this.id+":anchor","rich-menu-group-label-selected");
		} else {
			if (!this.mouseOver) {
				Element.removeClassName(this.id,"rich-menu-group-hover");
				
				Element.addClassName(this.id,"rich-menu-group-enabled");
				
				if (this.options.selectClass) {
					Element.removeClassName(this.id, this.options.selectClass);
				}
				Element.removeClassName(this.id+":icon","rich-menu-item-icon-selected");
				Element.removeClassName(this.id+":anchor","rich-menu-item-label-selected");
				Element.removeClassName(this.id+":icon","rich-menu-group-icon-selected");
				Element.removeClassName(this.id+":anchor","rich-menu-group-label-selected");
			}
		}
}

});

RichFaces.Menu.findMenuItem = function (itemId) {
	var layer;
	var menuItem = null;
	for (var id in RichFaces.Menu.Layers.layers) {
		layer = RichFaces.Menu.Layers.layers[id];
		menuItem = layer.items[itemId];
		if (menuItem) break;
	}
	return menuItem;
}

RichFaces.Menu.updateItem = function (event, element, attr) {
	var menuItem = RichFaces.Menu.findMenuItem(element.id);
	var classes = 'rich-menu-item rich-menu-item-enabled';
	if (menuItem) {
		if (menuItem.options.styleClass) classes =+ ' '+menuItem.options.styleClass;
		element.className = classes;
 		if (menuItem.options.onselect) menuItem.options.onselect(event);		
	} else if (attr){
		if (attr.styleClass) classes += ' '+attr.styleClass;
		element.className = classes;
 		if (attr.onselect) attr.onselect(event);
	}
}

RichFaces.Menu.submitForm = function (event, element, params, target, attr) {
	RichFaces.Menu.updateItem(event, element, attr);
	var form=A4J.findForm(element);
	if (!params) params={};
	if (!target) target='';
	params[element.id+':hidden']=element.id;
	Richfaces.jsFormSubmit(element.id,form.id,target,params);
	return false;	
}

RichFaces.Menu.groupMouseOut = function(event, element, menuGroupClass, menuGroupStyle) {
	if (RichFaces.Menu.isWithin(event, element)) {
		return; 
	}
	
	element.className = 'rich-menu-group rich-menu-group-enabled ' + (menuGroupClass ? menuGroupClass : '');
	element.style.cssText = menuGroupStyle;
}

RichFaces.Menu.itemMouseOut = function(event, element, menuItemClass, menuItemStyle, iconClass) {
	if (RichFaces.Menu.isWithin(event, element)) {
		return;
	}
	
	element.className = 'rich-menu-item rich-menu-item-enabled ' + (menuItemClass ? menuItemClass : '');
	element.style.cssText = menuItemStyle;
	$(element.id + ':icon').className='dr-menu-icon rich-menu-item-icon ' + (iconClass ? iconClass : '');
	Element.removeClassName($(element.id + ':anchor'), 'rich-menu-item-label-selected');
	
}

RichFaces.Menu.groupMouseOver = function(event, element, menuGroupHoverClass, menuGroupStyle) {
	if (RichFaces.Menu.isWithin(event, element)) {
		return;
	}
	
	element.className = 'rich-menu-group rich-menu-group-enabled ' + (menuGroupHoverClass ? menuGroupHoverClass : '');
	element.style.cssText = menuGroupStyle;
}

RichFaces.Menu.itemMouseOver = function(event, element, menuItemHoverClass, menuItemStyle, iconClass) {
	if (RichFaces.Menu.isWithin(event, element)) {
		return;
	}
	
	element.className = 'rich-menu-item rich-menu-item-hover ' + (menuItemHoverClass ? menuItemHoverClass : '');
	element.style.cssText = menuItemStyle;
	
	$(element.id + ':icon').className='dr-menu-icon dr-menu-icon-selected rich-menu-item-icon rich-menu-item-icon-selected ' + (iconClass ? iconClass : '');
	Element.addClassName($(element.id + ':anchor'), 'rich-menu-item-label-selected');
}
