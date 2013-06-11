function createX() {
//	alert(window.location);
	return {};
}

var dialogs = createX();

Resizer = Class.create();

Resizer.prototype = {
	initialize: function() {
		this.bound = false;
		this.listeners = {};
	},
	
	doInit: function() {
		if(this.bound) return;
		if(!window.document) return;
		this.bound = true;
		try {
			Event.observe(window, 'resize', this.resize.bind(this), false);
			Event.observe(window.document.body, 'scroll', this.resize.bind(this), false);
			Event.observe(window, 'scroll', this.resize.bind(this), false);
		} catch (e) {
		  alert(e.message);
		}
	},
	
	addListener: function(context) {
		this.doInit();
		this.listeners[context.dialogId] = context._doResize.bind(context);
	},
	
	removeListener: function(context) {
		this.listeners[context.dialogId] = null;
	},
	
	resize: function() {
		for (x in this.listeners) {
			if(this.listeners[x]) this.listeners[x]();
		}
	}
}

function getResizer() {
    if(getTopWindow() != window) return getTopWindow().getResizer();
    if(window.ResizerImpl) return window.ResizerImpl;
    return new Resizer();
}

var ResizerImpl = getResizer();

DialogContext = Class.create();

function getTopWindow() {
	if(window.parent && window.parent != window) {
		return window.parent.getTopWindow();
	}
	if(window.opener && window.opener != window) {
		return window.opener.getTopWindow();
	}
	return window;
}

DialogContext.prototype = {

	initialize: function(dialogId, dialogPath, parentDialogId, options) {
  try {
		this.linkId = dialogId + "_a";
		this.dialogId = dialogId;
		this.divId = dialogId + "_div";
		this.screenId = dialogId + "_screen";
		this.dialogPath = dialogPath;
		this.persistentDialogPath = dialogPath;

		this.options = options;
		this.parentDialogId = parentDialogId;
		this.shape = new DW.Shape(this);
		this.filler = new DW.Filler(this);

	var closeParent = "true" == options.hideParentDialog;
	if(this.parentDialogId && closeParent) {
		var p = getTopWindow().dialogs[this.parentDialogId];
		if(p && p.iframe) {
			p.hidden = true;
			Element.setStyle(p.iframe, {"visibility" : "hidden" });
		}
	}

		if(!options.ajax) {
			Event.observe(window, 'load', this.openDialog.bind(this), false);
		} else {
			this.openDialog();
		}

  } catch (e) {
	alert("initialize error: " + e.message);
  }
	},

	getParentDialogWindow: function() {
		if(this.parentDialogId) {
			this.parentDialogWindow = this.topWindow.dialogs[this.parentDialogId].dialogWindow;
		} else {
			this.parentDialogWindow = this.topWindow;
		}
		return this.parentDialogWindow;
	},

	_destroy: function() {
		try {
			this.parentDialogWindow.hasModalDialog = false;
			ResizerImpl.removeListener(this);

			if(this.dialogWindow && this.onLoadEvent) {
				Event.stopObserving(this.dialogWindow, "load", this.onLoadEvent);
				this.onLoadEvent = null;
			}

			this.enableSelects();
	
			var w = this.topWindow;

			var g = this.parentDialogWindow.document.getElementById(this.linkId);
			var scr = this.parentDialogWindow.document.getElementById(this.screenId);
			if(scr && scr.parentNode) {
				scr.parentNode.removeChild(scr);
			}
			if(!g) {
				alert('Cannot find link ' + this.linkId);
			}
			var div = w.document.getElementById(this.divId);
			if(div && div.parentNode) {
				if(DW.isIE) {
					if (this.options && this.options.spacerImage)
						div.src= this.options.spacerImage;
					else
						div.src = "about:blank";
//					div.src="javascript:void(0)";
				}
				div.parentNode.removeChild(div);
			}
			this.topWindow.dialogs[this.dialogId] = null;
			this.shape.destroy();
		} catch (e) {
			alert('Error in destroy: ' + e.message);
		}
	},
	
	openDialog: function() {
		try {
			disableDocumentFocusElements(this.dialogId);
			this.topWindow = getTopWindow();
			this.topWindow.dialogs[this.dialogId] = this;
			this.topWindow.currentDialog = this;
			if(this.parentDialogId) {
			    var d = this.topWindow.dialogs[this.parentDialogId];
			    if(!d) throw("Cannot find context for parent dialog " + this.parentDialogId);
				this.parentDialogWindow = d.dialogWindow;
				d.shape.borders.setVisible(false);
			} else {
				this.parentDialogWindow = this.topWindow;
			}
			
			this.parentDialogWindow.hasModalDialog = true;
			this.disableSelects();

			var scr = this.getParentDialogWindow().document.createElement("div");
//				scr.frameBorder = "0";
			scr.id = this.screenId;
			scr.style.position = "absolute"; 
			scr.style.top = "0px";
			scr.style.left = "0px";
			Element.setStyle(scr, {"border-style" : "none" });
			Element.setStyle(scr, {"background-color" : "#d0d0d0" });
			Element.setStyle(scr, {"z-index" : "99" });
			scr.style.filter="alpha(opacity=50)";
			scr.style.opacity = "0.5";
			this.parentDialogWindow.document.body.appendChild(scr);

			var iframe = this.topWindow.document.createElement("iframe");
				var div = iframe;
////			var div = this.topWindow.document.createElement("div");
////			this.frameDiv = div;
////			div.id = this.divId;
			div.style.position = "absolute";
			Element.setStyle(div, {"z-index" : "99"});
			Element.setStyle(div, {"border" : "0px", "padding" : "0px 0px 0px 0px", "margin" : "0px 0px 0px 0px" });

			Event.observe(iframe, 'load', this.testOnLoad.bind(this), false);
			this.iframe = iframe;
				iframe.id = this.divId;
			iframe.src = this.dialogPath;
			iframe.frameBorder = "0";
			iframe.scrolling="no";
			Element.setStyle(iframe, {"overflow-y" : "hidden" });
			Element.setStyle(iframe, {"visibility" : "hidden" });
			Element.setStyle(iframe, {"border" : "0px", "padding" : "0px 0px 0px 0px", "margin" : "0px 0px 0px 0px" });

////			div.appendChild(iframe); 
			this.shape.init(div, this.options);
			
			this.topWindow.document.body.appendChild(div);

			ResizerImpl.addListener(this);

///			this._doResize();
    	
		} catch (e) {
		    var mess = (e.message) ? e.message : e;
			alert("Error in open dialog: " + mess);
		}
	},
	
	testOnLoad: function(event) {
	try {
		if(!this.iframe || !this.iframe.parentNode) return;
		var w = this.iframe.contentWindow;
		if(w) {
			// copy of registerInParent();
			if(!w.thisDialogContext) {
				w.thisDialogContext = this;
				w.thisDialogContext.dialogWindow = w;
			}
			w.thisDialogContext._doOnLoad();
		}
	} catch (e) {
		alert("Error in testOnLoad " + e.message);
	}
	},
	
	getDialogPath: function() {
		return this.dialogPath;
	},

	_doResize: function() {
		try {
			if(closed) {
				alert('closed!!!!!');
				return;
			}
			if(!this.loaded) return;
			if(!this.parentDialogWindow) return;
			var parentPositionObject = this.parentDialogWindow.getBase();
			var topPositionObject = this.topWindow.getBase();
			var scr = this.parentDialogWindow.$(this.screenId);
			if(scr) {
				scr.style.top = parentPositionObject.scrollTop + "px";
				scr.style.left = parentPositionObject.scrollLeft + "px";
				scr.style.width = "" + parentPositionObject.clientWidth + "px";
				scr.style.height = "" + parentPositionObject.clientHeight + "px";
			}
			this.shape.resize();
			this.filler.iterate();
		} catch (e) {
			alert('Error in _doResize: ' + e.message);
		}
	},
	
	registerOnLoad: function(_window) {
/*
		this.dialogWindow = _window;
		if(!this.onLoadEvent) {
			this.onLoadEvent = this._doOnLoad.bind(this);
			Event.observe(_window, 'load', this.onLoadEvent, false);
		}
*/
	},
	
	_doOnLoad: function() {
		try {
			this.loaded = true;
			Element.setStyle(this.dialogWindow.document.body, { "margin" : "0px 0px 0px 0px" });
			if("transparent" == Element.getStyle(this.dialogWindow.document.body, "background-color")) {
				this.iframe.allowTransparency = true;
			}
			this.filler.onLoad();
			this._doResize();
			this.filler.enable();
			if(!this.hidden)	{
				Element.setStyle(this.iframe, {"visibility" : "visible" });
			}
		} catch (e) {
			alert('Error in _doOnLoad' + e.message);
		}
	},
	
	disableSelects: function() {
	try {
		this.selects = this.getParentDialogWindow().document.getElementsByTagName("SELECT");
		if(this.selects) {
			for (k in this.selects) {
				this.selects[k]._disabled = this.selects[k].disabled || false;
				this.selects[k].disabled = true;
			}
		}
	} catch (e) {
		alert('Error in disableSelects: ' + e.message);
	}
	},
  
	enableSelects: function() {
	try {
		if(this.selects) {
			for (k in this.selects) {
				this.selects[k].disabled = this.selects[k]._disabled || false;
			}
		}
	} catch (e) {
		alert('Error in disableSelects: ' + e.message);
	}
	},
	
	onOptionsChanged: function() {
		this.shape.init(this.iframe, this.options);
		this._doResize();
	}

}

var thisDialogContext = null;

function registerInParent() {
	if(thisDialogContext) {
		return;
	}
	thisDialogContext = getTopWindow().currentDialog;
	if(thisDialogContext) {
		thisDialogContext.dialogWindow = window;
//		thisDialogContext.registerOnLoad(window);
	}
}

function onDialogLink(dialogPath) {
	if(thisDialogContext) {
		thisDialogContext.topWindow.currentDialog = thisDialogContext;
		thisDialogContext.dialogPath = dialogPath;
	}
}

function createObject() {
	return {};
}

function closeDialog(args) {
	if(!thisDialogContext) return;
	var context = thisDialogContext;
	var id = (!args) ? null : args.id;
	var targetViewId = (!args) ? null : args.targetViewId;
	var container = context.options.container;
	var form = context.options.form;
	thisDialogContext = null;
	if(context.parentDialogId) {
		var p = context.topWindow.dialogs[context.parentDialogId];
		context.topWindow.currentDialog = p;
		context.topWindow.currentDialog.shape.borders.setVisible(true);
		p.hidden = false;
		if(p && p.iframe) {
			Element.setStyle(p.iframe, {"visibility" : "visible" });
		}
	}
	try {
		enableDocumentFocusElements(container.id);
		var pdw = context.getParentDialogWindow();
		var g = pdw.document.getElementById(context.linkId);
		context._destroy();
		var ps = pdw.createObject();
		ps[context.dialogId] = context.dialogId;
///		alert(context.dialogId);
		ps['action'] = 'close';

		if(targetViewId) {
			var ii = targetViewId.indexOf('?');
			if(ii > 0) {
				ps['targetViewId'] = targetViewId.substring(0, ii);
			} else {
				ps['targetViewId'] = targetViewId;
			}
		}
		var gevent = pdw.createObject();
		gevent.target = g;
		gevent.srcElement = g;

		var parameters = pdw.createObject();
		parameters['parameters'] = ps;

		var theForm = pdw.window.document.getElementById(form);
		var action = null;
			
		if (context.persistentDialogPath) {
			var path = context.persistentDialogPath;
			var ii = path.indexOf('?');
			if(ii > 0) {
				action = theForm.action + path.substring(ii);
			}
		}

		if (action) {
			parameters.actionUrl = action;
		}

		pdw.A4J.AJAX.Submit(container,form,gevent,parameters);
		
		return false;
	} catch(e) {
		alert(e.message);
	}
	return false;
}

function closeAll(args) {
	if(!thisDialogContext) return;
	var context = thisDialogContext;
	var id = (!args) ? null : args.id;
	var targetViewId = (!args) ? null : args.targetViewId;
	var container = context.options.container;
	var form = context.options.form;
	thisDialogContext = null;
	if(context.parentDialogId) {
		context.topWindow.currentDialog.shape.borders.setVisible(true);
		context.topWindow.currentDialog = context.topWindow.dialogs[context.parentDialogId];
	}
	try {
		context._destroy();
		if(!context.parentDialogId) {
			var pdw = context.parentDialogWindow;
			var g = pdw.document.getElementById(context.linkId);
			var ps = pdw.createObject();
			ps[context.dialogId] = context.dialogId;
			ps['action'] = 'closeall';
			if(targetViewId) {
				ps['targetViewId'] = targetViewId;
			}
			var gevent = pdw.createObject();
			gevent.target = g;
			gevent.srcElement = g;

			var parameters = pdw.createObject();
			parameters['parameters'] = ps;

			var theForm = pdw.window.document.getElementById(form);
			var action = null;
			
			if (context.persistentDialogPath) {
				var path = context.persistentDialogPath;
				var ii = path.indexOf('?');
				if(ii > 0) {
					action = theForm.action + path.substring(ii);
				}
			}

			if (action) {
				parameters.actionUrl = action;
			}
	
			pdw.A4J.AJAX.Submit(container,form,gevent,parameters);
			return false;
		} else {
			return context.parentDialogWindow.closeAll(args);
		}
	} catch(e) {
		alert(e.message);
	}
	return false;
}

DialogContext.submitDialogAction = function(formId, dialogId, options) {
	try {
		var parentForm = document.forms[formId];
		if(parentForm == null) {
			alert('Cannot find form ' + formId);
			return false;
		}

		if(options && options.parameters) {
			for (var x in options.parameters) {
				if(x == dialogId) continue;
				addHiddenToForm(parentForm, "" + x, options.parameters[x]);
			}
		}
		addHiddenToForm(parentForm, dialogId, dialogId);
		parentForm.submit();
		return false;
	} catch (e) {
    	alert('Error in DialogContext.submitDialogAction: ' + e.message);
	}
}

function addHiddenToForm(parentForm, name, value) {
	var fInput = parentForm[name];
	if(fInput==null) {
		fInput = document.createElement("input");
		fInput.type = "hidden";
		fInput.name = name;
		parentForm.appendChild(fInput);
	}
	fInput.value = value;
}

function getBase() {
	return (document.compatMode && document.compatMode.toLowerCase() == "css1compat" && 
					!/Netscape|Opera/.test(navigator.userAgent)) ?
		document.documentElement : (document.body || null);
}

function setImagesToButton(element, images) {
	if (!Exadel) return;
	Exadel.setImages(element, images);
}

DW = {
	isIE: window.navigator.userAgent.indexOf("MSIE") > 0,
	isNS: window.navigator.userAgent.indexOf("Netscape") > 0,
	isFF: window.navigator.userAgent.indexOf("Firefox") > 0
};

DW.close = function() {
	closeDialog();
}

DW.closeAll = function() {
	closeAll();
}

DW.open = function(id, referenceId)  {
	if(!id) return;
	var element = $(id + "_a");
	if(element) {
		if(referenceId) {
			element.posReferenceId = referenceId;
		}
		if(element.click) {
			element.click();
		} else if(element.href && element.onclick) {
			try {
				var gevent = {};
				gevent.target = element;
				gevent.srcElement = element;
				element.onclick.call(element, gevent);
			} catch (e) {
				alert("Error in DW.open: " + e.message);
			}
		}
	}
}

DW.Shape = Class.create();

DW.Shape.prototype = {
	
	initialize: function(context) {
		this.context = context;
		this.posx = new DW.Unit(this, 'posx');
		this.posy = new DW.Unit(this, 'posy');
		this.width = new DW.Unit(this, 'width');
		this.height = new DW.Unit(this, 'height');
		this.borders = new DW.Borders(this);
		
		this.maxWidth = new DW.Unit(this, 'maxWidth');
		this.maxHeight = new DW.Unit(this, 'maxHeight');
		this.posFunction = null;
		this.posReferenceId = null;
	},
	
	init: function(div, options) {
		this.div = div;
		this.posx.init(div, options);
		this.posy.init(div, options);
		this.width.init(div, options);
		this.height.init(div, options);
		this.maxWidth.init(div, options);
		this.maxHeight.init(div, options);

		this.posFunction = options["posFunction"];
		this.posReferenceId = options["posReferenceId"];
		
		this.dialogWidth = 10;
		this.dialogHeight = 10;

		this.borders.init(options);
	},
	
	resize: function() {
		if(this.context && this.context.dragging) return;
		if(this.width.value < 0 || this.height.value < 0) {
			this.setDefaultSize();
			return;
		}
		
		var parentPositionObject = this.context.parentDialogWindow.getBase();
		var topPositionObject = this.context.topWindow.getBase();

		this.computeSize(parentPositionObject, topPositionObject);
		this.computePosition(parentPositionObject, topPositionObject);
		this.borders.resize();
	},
	
	computeSize: function(parentPositionObject, topPositionObject) {
		this.computeMaximumSize(parentPositionObject, topPositionObject);

		if(this.width.isRelative) {
			this.width.pixelValue = topPositionObject.clientWidth * this.width.value / 100;
		} else {
			this.width.pixelValue = this.width.value;
		}
		this.dialogWidth = this.width.pixelValue + this.width.delta;
		if(this.maxWidth.pixelValue > 0 && this.maxWidth.pixelValue < this.dialogWidth) {
			this.width.pixelValue += this.maxWidth.pixelValue - this.dialogWidth;
			this.dialogWidth = this.maxWidth.pixelValue;
		}
		if(this.dialogWidth < 10) this.dialogWidth = 10;
		this.div.style.width = "" + this.dialogWidth + "px";
		
		if(this.height.isRelative) {
			this.height.pixelValue = topPositionObject.clientHeight * this.height.value / 100;
		} else {
			this.height.pixelValue = this.height.value;
		}
		this.dialogHeight = this.height.pixelValue + this.height.delta;
		if(this.maxHeight.pixelValue > 0 && this.maxHeight.pixelValue < this.dialogHeight) {
			this.height.pixelValue += this.maxHeight.pixelValue - this.dialogHeight;
			this.dialogHeight = this.maxHeight.pixelValue;
		}
		if(this.dialogHeight < 10) this.dialogHeight = 10;
		this.div.style.height = "" + this.dialogHeight + "px";
		this.kick();
	},
	
	computeMaximumSize: function(parentPositionObject, topPositionObject) {
		if(this.maxWidth.value <= 0) {
			this.maxWidth.pixelValue = -1;
		} else if(this.maxWidth.isRelative) {
			this.maxWidth.pixelValue = topPositionObject.clientWidth * this.maxWidth.value / 100;
		} else {
			this.maxWidth.pixelValue = this.maxWidth.value;
		}
			
		if(this.maxHeight.value <= 0) {
			this.maxHeight.pixelValue = -1;
		} else if(this.maxHeight.isRelative) {
			this.maxHeight.pixelValue = topPositionObject.clientHeight * this.maxHeight.value / 100;
		} else {
			this.maxHeight.pixelValue = this.maxHeight.value;
		}
	},

	computePosition: function(parentPositionObject, topPositionObject) {
		if(this.posFunction) {
			if(!this.functionApplied) {
				this.functionApplied = true
			try {
				var _posRefId = (this.posReferenceId) ? "'" + this.posReferenceId + "'" : "'" + this.context.linkId + "'";
				eval(this.posFunction + "('" + this.div.id + "', " + _posRefId + ");");
					var a = this.div.offsetLeft;
					var b = this.div.offsetTop;
				if(this.div.style.left) {
					this.posx.pixelValue = parseInt(this.div.style.left);
				} else {
					this.posx.pixelValue = 0;
				}
				if(this.div.style.top) {
					this.posy.pixelValue = parseInt(this.div.style.top);
				} else {
					this.posy.pixelValue = 0;
				}
			} catch (e) {
				alert('Error in calling posFunction=' + this.posFunction + ": " + e.message);
				this.computePositionInternal(parentPositionObject, topPositionObject);
			}
			}
		} else {
			this.computePositionInternal(parentPositionObject, topPositionObject);
		}
		this.kick();
	},
	
	computePositionInternal: function(parentPositionObject, topPositionObject) {
		if(this.posx.isRelative) {
			this.posx.pixelValue = (topPositionObject.clientWidth * this.posx.value / 100 + topPositionObject.scrollLeft - this.width.pixelValue * this.posx.value / 100);
		} else {
			this.posx.pixelValue = topPositionObject.scrollLeft + this.posx.value;
		}
		this.div.style.left = "" + (this.posx.pixelValue + this.posx.delta) + "px";

		if(this.posy.isRelative) {
			this.posy.pixelValue = (topPositionObject.clientHeight * this.posy.value / 100 + topPositionObject.scrollTop - this.height.pixelValue * this.posy.value / 100);
		} else {
			this.posy.pixelValue = topPositionObject.scrollTop + this.posy.value;
		}
		this.div.style.top = "" + (this.posy.pixelValue + this.posy.delta) + "px";
		this.kick();
	},
	
	setDefaultSize: function() {
		if(!this.context.dialogWindow) return;
		var parentPositionObject = this.context.parentDialogWindow.getBase();
		var topPositionObject = this.context.topWindow.getBase();

		var dw = this.width.value < 0;
		var dh = this.height.value < 0;
		if(!dw && !dh) return;
		this.isSettingDefaultSize = true;
		if(dw) {
			this.width.value = 50;
			this.width.isRelative = true;
		}
		if(dh) {
			this.height.value = 50;
			this.height.isRelative = false;
		}
		this.computeSize(parentPositionObject, topPositionObject);
			var _d = this.context.dialogWindow.document.documentElement;
			var _b = this.context.dialogWindow.document.body;
		var base = (DW.isNS) ? _d :
				   (DW.isFF && _b.offsetWidth == _b.scrollWidth) ? _d
							 : _b;
		if(dw) {
			var a = base.offsetWidth;
			var b = base.clientWidth;
			var c = base.scrollWidth;
			var res = c;
			if(DW.isIE) {
				res += a - b;
			}			
			this.width.value = res;
			this.width.isRelative = false;
			this.computeSize(parentPositionObject, topPositionObject);
		}
		if(dh) {
			this.height.value = base.scrollHeight;
			this.computeSize(parentPositionObject, topPositionObject);
		}
		this.isSettingDefaultSize = false;

		this.computePosition(parentPositionObject, topPositionObject);
		this.borders.resize();
	},
	
	kick: function() {
		var scr_w = this.context.dialogWindow.document.body.scrollWidth;
		var offw = this.context.dialogWindow.document.body.offsetWidth;
		var cl_w = this.context.dialogWindow.document.body.clientWidth;

		var off_d_w = this.context.dialogWindow.document.documentElement.offsetWidth;
		var cli_d_w = this.context.dialogWindow.document.documentElement.clientWidth;
		var scr_d_w = this.context.dialogWindow.document.documentElement.scrollWidth;

		var scr_h = this.context.dialogWindow.document.body.scrollHeight;
		var offh = this.context.dialogWindow.document.body.offsetHeight;
		var cl_h = this.context.dialogWindow.document.body.clientHeight;

		var off_d_h = this.context.dialogWindow.document.documentElement.offsetHeight;
		var cli_d_h = this.context.dialogWindow.document.documentElement.clientHeight;
		var scr_d_h = this.context.dialogWindow.document.documentElement.scrollHeight;
/*
		alert(
			  "scrollWidth=" + this.context.dialogWindow.document.body.scrollWidth 
			+ "\nde_scrollWidth=" + this.context.dialogWindow.document.documentElement.scrollWidth 
			+ "\noffsetWidth=" + this.context.dialogWindow.document.body.offsetWidth 
			+ "\nde_offsetWidth=" + this.context.dialogWindow.document.documentElement.offsetWidth 
			+ "\nclientWidth=" + this.context.dialogWindow.document.body.clientWidth 
			+ "\nde_clientWidth=" + this.context.dialogWindow.document.documentElement.clientWidth 

			+ "scrollHeight=" + this.context.dialogWindow.document.body.scrollHeight 
			+ "\nde_scrollHeight=" + this.context.dialogWindow.document.documentElement.scrollHeight 
			+ "\noffsetHeight=" + this.context.dialogWindow.document.body.offsetHeight
			+ "\nde_offsetHeight=" + this.context.dialogWindow.document.documentElement.offsetHeight 
			+ "\nclientHeight=" + this.context.dialogWindow.document.body.clientHeight 
			+ "\nde_clientHeight=" + this.context.dialogWindow.document.documentElement.clientHeight 

			+ "\ncw=" + this.width.pixelValue + "\n" + 
			+ "agent=" + window.navigator.userAgent
			);
*/
	},
	
	destroy: function() {
		this.borders.destroy();
	}
	
}

DW.Unit = Class.create();

DW.Unit.prototype = {

	initialize: function(shape, name) {
		this.shape = shape;
		this.name = name;
		this.value = 50;
		this.isRelative = true;
		this.pixelValue = 0;
		this.delta = 0;
	},
	
	init: function(div, options) {
		this.div = div;
		var v = options[this.name];
//		alert(name + ":" + v);
		if(!v) v = "50%";
		i = v.indexOf("%");
		if(i >= 0) {
			this.isRelative = true;
			v = v.substring(0, i);
		} else {
			this.isRelative = false;
		}
		this.value = parseInt(v);
	}
}

DW.Borders = Class.create();

DW.Borders.prototype = {
	initialize: function(shape) {
		this.shape = shape;	
		this.W = new DW.Border_W(shape);
		this.E = new DW.Border_E(shape);
		this.N = new DW.Border_N(shape);
		this.S = new DW.Border_S(shape);
		this.NW = new DW.Border_NW(shape);
		this.NE = new DW.Border_NE(shape);
		this.SW = new DW.Border_SW(shape);
		this.SE = new DW.Border_SE(shape);
		this.Move = new DW.Border_Move(shape);
		this.all = [];
	},
	
	init: function(options) {
		var _float = options._float;
		if(_float && _float == 'false') _float = false;
		this.shape.headerHeight = options.headerHeight;
		var resizable = options.resizable;
		if(resizable && resizable == 'false') resizable = false;

		this.destroy();

		if(!_float && !resizable) {
			this.all = [];
		} else if(_float && resizable) {
			this.all = [this.W, this.N, this.E, this.S, this.NW, this.NE, this.SW, this.SE, this.Move];
		} else if(_float) {
			this.all = [this.Move];
		} else {
			this.all = [this.W, this.N, this.E, this.S, this.NW, this.NE, this.SW, this.SE    ];
		}

		for (var k = 0; k < this.all.length; k++) {
			this.all[k].init();
		}
	},
	
	resize: function() {
		var b = (!this.shape.context) ? true : false;
		if(b || !this.shape.context.dragging) {
			for (var k = 0; k < this.all.length; k++) {
				this.all[k].resize();
			}
		}
	},

	setVisible: function(b) {
		for (var k = 0; k < this.all.length; k++) {
			this.all[k].setVisible(b);
		}
	},
	
	destroy: function() {
		for (var k = 0; k < this.all.length; k++) {
			this.all[k].destroy();
		}
	}
	
}

DW.Border_Abstract = {

	initialize: function(shape) {
		this.shape = shape;
		this.div = null;
	},

	init: function() {
		var doc = this.shape.context.topWindow.document;

		if(this.div && this.div.parentNode) {
			//reuse
		} else if(DW.isIE && this.narrow) {
			this.div = doc.createElement("table");
			this.div.cellSpacing = 0;
			this.div.cellPadding = 0;
			this.div.border = 0;
			var b = doc.createElement("tbody");
			var r = doc.createElement("tr");
			this.td = doc.createElement("td");
			Element.setStyle(this.td, {"border" : "0px" });
			Element.setStyle(this.td, {"background-color" : "black" });
			r.appendChild(this.td);
			b.appendChild(r);
			this.div.appendChild(b);
		} else {
			this.div = doc.createElement("div");
			this.td = this.div;
		}

		this.div.id = this.shape.context.divId + "_" + this.getDirectionID();
		this.div.style.position = "absolute";
		Element.setStyle(this.div, {"z-index" : "100" });
		Element.setStyle(this.td, {"z-index" : "100" });
		Element.setStyle(this.div, {"cursor" : this.getCursorName() });
		Element.setStyle(this.div, {"background-color" : "#00d000" });
		this.div.style.filter="alpha(opacity=1)";
		this.div.style.opacity = "0.01";
		this.setDimensions();

		doc.body.appendChild(this.div);
		this.createDragRecognizer();
	},

	setCursor: function() {
		Element.setStyle(this.div, {"cursor" : this.getCursorName() });
		this.shape.context.topWindow.status = "Cursor set";
	},
	
	setTop: function(value) {
		this.div.style.top = "" + value + "px";
	},
	
	setLeft: function(value) {
		this.div.style.left = "" + value + "px";
	},
	
	setWidth: function(value) {
		if(value < 0) value = 0;
		this.td.style.width = "" + value + "px";
	},
	
	setHeight: function(value) {
		if(value < 0) value = 0;
		this.td.style.height = "" + value + "px";
	},
	
	createDragRecognizer: function() {
	 	this.eventMouseDown = this.recognizeDrag.bindAsEventListener(this);
		Event.observe(this.div, "mousedown", this.eventMouseDown);
		this.eventMouseMove = this.drag.bindAsEventListener(this);
		Event.observe(this.div, "mousemove", this.eventMouseMove);
		this.eventMouseUp = this.dragEnd.bindAsEventListener(this);
		Event.observe(this.div, "mouseup", this.eventMouseUp);
	},

	recognizeDrag: function(event) {
	try {
		this.shape.context.dragging = {};
		this.x0 = event.clientX;
		this.y0 = event.clientY;
		this.dx = 0;
		this.dy = 0;
		var po = this.shape.context.topWindow.getBase();
		this.setLeft(0);
		this.setTop(0);
		this.setWidth(po.scrollLeft + po.clientWidth);
		this.setHeight(po.scrollTop + po.clientHeight);
//		this.cursor = this.shape.context.topWindow.document.body.style.cursor;
//		this.shape.context.topWindow.document.body.style.cursor = this.div.style.cursor;
		Element.setStyle(this.div, {"z-index" : "101" });
		Element.setStyle(this.td, {"z-index" : "101" });
	} catch (e) {
		alert('Error in recognizeDrag' + e.message);
	}
	},
	
	drag: function(event) {
		if(!this.shape.context.dragging) return false;
		this.dx = event.clientX - this.x0;
		this.dy = event.clientY - this.y0;
		this.doDrag(event);
		this.shape.context.filler.iterate();
//		this.setCursor();
	},
	
	dragEnd: function(event) {
		if(!this.shape.context.dragging) return false;
		this.shape.context.dragging = null;
		this.setDimensions();
		this.doDragEnd();
		this.shape.context._doResize();
		Element.setStyle(this.div, {"z-index" : "100" });
		Element.setStyle(this.td, {"z-index" : "100" });
//		this.shape.context.topWindow.document.body.style.cursor = this.cursor;
	},

	setVisible: function(b) {
		Element.setStyle(this.div, {"visibility" : ((b) ? "visible" : "hidden") });
	},

	destroy: function() {
		if(this.div && this.div.parentNode) {
			if(this.eventMouseDown) {
				Event.stopObserving(this.div, "mousedown", this.eventMouseDown);
				Event.stopObserving(this.div, "mousemove", this.eventMouseMove);
				Event.stopObserving(this.div, "mouseup", this.eventMouseUp);
				this.eventMouseDown = null;
				this.eventMouseMove = null;
				this.eventMouseUp = null;
			}
			this.div.parentNode.removeChild(this.div);
			this.div = null;
		}
	},
	
	getNewDimension: function(sx, sy) {
		return [this.getNewWidth(sx), this.getNewHeight(sy)];
	},
	
	getNewWidth: function(sx) {
		if(sx == 0) return -1;
		var newWidth = (this.shape.width.pixelValue + this.shape.width.delta + sx * this.dx);
		var delta = 0;
		if(newWidth < 20) {
			delta = 20 - newWidth;
		} else if(this.shape.maxWidth.pixelValue > 0 && newWidth > this.shape.maxWidth.pixelValue) {
			delta = newWidth - this.shape.maxWidth.pixelValue;
		}
		if(delta == 0) return newWidth;
		this.dx += sx * delta;
		return -1;
	},
	
	getNewHeight: function(sy) {
		if(sy == 0) return -1;
		var newHeight = (this.shape.height.pixelValue + this.shape.height.delta + sy * this.dy);
		var delta = 0;
		if(newHeight < 20) {
			delta = 20 - newHeight;
		} else if(this.shape.maxHeight.pixelValue > 0 && newHeight > this.shape.maxHeight.pixelValue) {
			delta = newHeight - this.shape.maxHeight.pixelValue;
		}
		if(delta == 0) return newHeight;
		this.dy += sy * delta;
		return -1;
	}
	
}

DW.Border_W = Class.create();

DW.Border_W.prototype = {

	getDirectionID: function() {
		return "w";
	},
	getCursorName: function() {
		return "w-resize";
	},
	
	setDimensions: function() {
		this.setWidth(4);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta + 4);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta - 2);
		this.setHeight(this.shape.dialogHeight - 8);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(-1, 0);
		if(dim[0] > 0) {
			this.shape.div.style.width = "" + dim[0] + "px";
			this.shape.div.style.left = "" + (this.shape.posx.pixelValue + this.shape.posx.delta + this.dx) + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.posx.delta += this.dx;
		this.shape.width.delta -= this.dx;
	}
	
}

Object.extend(DW.Border_W.prototype, DW.Border_Abstract);

DW.Border_E = Class.create();

DW.Border_E.prototype = {

	getDirectionID: function() {
		return "e";
	},
	getCursorName: function() {
		return "e-resize";
	},
	
	setDimensions: function() {
		this.setWidth(4);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta + 4);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta +
					 this.shape.width.pixelValue + this.shape.width.delta - 2);
		this.setHeight(this.shape.dialogHeight - 8);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(1, 0);
		if(dim[0] > 0) {
			this.shape.div.style.width = "" + dim[0] + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.width.delta += this.dx;
	}
	
}

Object.extend(DW.Border_E.prototype, DW.Border_Abstract);

DW.Border_N = Class.create();

DW.Border_N.prototype = {
	narrow : true,

	getDirectionID: function() {
		return "n";
	},
	getCursorName: function() {
		return "n-resize";
	},
	
	setDimensions: function() {
		this.setHeight(4);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta - 2);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta + 4);
		this.setWidth(this.shape.dialogWidth - 8);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(0, -1);
		if(dim[1] > 0) {
			this.shape.div.style.height = "" + dim[1] + "px";
			this.shape.div.style.top = "" + (this.shape.posy.pixelValue + this.shape.posy.delta + this.dy) + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.posy.delta += this.dy;
		this.shape.height.delta -= this.dy;
	}
	
}

Object.extend(DW.Border_N.prototype, DW.Border_Abstract);

DW.Border_S = Class.create();

DW.Border_S.prototype = {
	narrow : true,

	getDirectionID: function() {
		return "s";
	},
	getCursorName: function() {
		return "s-resize";
	},
	
	setDimensions: function() {
		this.setHeight(4);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta +
					this.shape.height.pixelValue + this.shape.height.delta - 2);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta + 4);
		this.setWidth(this.shape.dialogWidth - 8);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(0, 1);
		if(dim[1] > 0) {
			this.shape.div.style.height = "" + dim[1] + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.height.delta += this.dy;
	}
	
}

Object.extend(DW.Border_S.prototype, DW.Border_Abstract);

DW.Border_NW = Class.create();

DW.Border_NW.prototype = {

	getDirectionID: function() {
		return "nw";
	},
	getCursorName: function() {
		return "nw-resize";
	},
	
	setDimensions: function() {
		this.setWidth(8);
		this.setHeight(8);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta - 2);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta - 2);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(-1, -1);
		if(dim[1] > 0) {
			this.shape.div.style.height = "" + dim[1] + "px";
			this.shape.div.style.top = "" + (this.shape.posy.pixelValue + this.shape.posy.delta + this.dy) + "px";
		}
		if(dim[0] > 0) {
			this.shape.div.style.width = "" + dim[0] + "px";
			this.shape.div.style.left = "" + (this.shape.posx.pixelValue + this.shape.posx.delta + this.dx) + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.posy.delta += this.dy;
		this.shape.height.delta -= this.dy;
		this.shape.posx.delta += this.dx;
		this.shape.width.delta -= this.dx;
	}
	
}

Object.extend(DW.Border_NW.prototype, DW.Border_Abstract);

DW.Border_NE = Class.create();

DW.Border_NE.prototype = {

	getDirectionID: function() {
		return "ne";
	},
	getCursorName: function() {
		return "ne-resize";
	},
	
	setDimensions: function() {
		this.setWidth(8);
		this.setHeight(8);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta - 2);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta +
					 this.shape.width.pixelValue + this.shape.width.delta - 6);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(1, -1);
		if(dim[1] > 0) {
			this.shape.div.style.height = "" + dim[1] + "px";
			this.shape.div.style.top = "" + (this.shape.posy.pixelValue + this.shape.posy.delta + this.dy) + "px";
		}
		if(dim[0] > 0) {
			this.shape.div.style.width = "" + dim[0] + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.posy.delta += this.dy;
		this.shape.height.delta -= this.dy;
		this.shape.width.delta += this.dx;
	}
	
}

Object.extend(DW.Border_NE.prototype, DW.Border_Abstract);

DW.Border_SW = Class.create();

DW.Border_SW.prototype = {
	narrow : true,

	getDirectionID: function() {
		return "sw";
	},
	getCursorName: function() {
		return "sw-resize";
	},
	
	setDimensions: function() {
		this.setWidth(8);
		this.setHeight(8);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta +
					this.shape.height.pixelValue + this.shape.height.delta - 6);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta - 2);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(-1, 1);
		if(dim[1] > 0) {
			this.shape.div.style.height = "" + dim[1] + "px";
		}
		if(dim[0] > 0) {
			this.shape.div.style.width = "" + dim[0] + "px";
			this.shape.div.style.left = "" + (this.shape.posx.pixelValue + this.shape.posx.delta + this.dx) + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.height.delta += this.dy;
		this.shape.posx.delta += this.dx;
		this.shape.width.delta -= this.dx;
	}
	
}

Object.extend(DW.Border_SW.prototype, DW.Border_Abstract);

DW.Border_SE = Class.create();

DW.Border_SE.prototype = {
	narrow : true,

	getDirectionID: function() {
		return "se";
	},
	getCursorName: function() {
		return "se-resize";
	},
	
	setDimensions: function() {
		this.setWidth(8);
		this.setHeight(8);
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta +
					this.shape.height.pixelValue + this.shape.height.delta - 6);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta +
					this.shape.width.pixelValue + this.shape.width.delta - 6);
	},

	doDrag: function(event) {
		var dim = this.getNewDimension(1, 1);
		if(dim[1] > 0) {
			this.shape.div.style.height = "" + dim[1] + "px";
		}
		if(dim[0] > 0) {
			this.shape.div.style.width = "" + dim[0] + "px";
		}
	},
	
	doDragEnd: function(event) {
		this.shape.height.delta += this.dy;
		this.shape.width.delta += this.dx;
	}
	
}

Object.extend(DW.Border_SE.prototype, DW.Border_Abstract);


DW.Border_Move = Class.create();

DW.Border_Move.prototype = {

	getDirectionID: function() {
		return "m";
	},
	getCursorName: function() {
		return "move";
	},
	
	setDimensions: function() {
		try {
			this.td.style.height = this.shape.headerHeight;
		} catch (e) {
			alert("Invalid headerHeight: " + this.shape.headerHeight);
			this.shape.headerHeight = "16px";
			this.td.style.height = this.shape.headerHeight;
		}
	},

	resize : function() {
		this.setTop(this.shape.posy.pixelValue + this.shape.posy.delta + 4);
		this.setLeft(this.shape.posx.pixelValue + this.shape.posx.delta + 4);
		this.setWidth(this.shape.dialogWidth - 8);
	},

	doDrag: function(event) {
		this.shape.div.style.left = "" + (this.shape.posx.pixelValue + this.shape.posx.delta + this.dx) + "px";
		this.shape.div.style.top = "" + (this.shape.posy.pixelValue + this.shape.posy.delta + this.dy) + "px";
	},
	
	doDragEnd: function(event) {
		this.shape.posx.delta += this.dx;
		this.shape.posy.delta += this.dy;
	}
	
}

Object.extend(DW.Border_Move.prototype, DW.Border_Abstract);


DW.Filler = Class.create();

DW.Filler.prototype = {
	initialize: function(context) {
		this.context = context;
		this.ok = false;
	},

	onLoad: function() {
		var w = this.context.dialogWindow;
		if(!w || !w.document.getElementsByClassName) return;
		this.fillers = w.document.getElementsByClassName('filler');
		if(!this.fillers) return;
		if(this.fillers.length == 0) return;
	},
	
	enable: function() {
		this.ok = true;
	},

	iterate: function() {
		if(!this.ok) return;
		this.context.shape.kick();
		var delta = this.getDelta();
		var iterations = 0;
		while(delta != 0 && iterations < 20) {		
			this.apply(delta);
			this.context.shape.kick();
			var d = this.getDelta();
			if(d == delta) break;
			delta = d;
			iterations++;
		}
	},

	getDelta: function() {
		var w = this.context.dialogWindow;
		var a = w.document.body.offsetHeight;
		var b = w.document.body.clientHeight;
		var c = w.document.body.scrollHeight;
		var wH = this.context.iframe.clientHeight;
		var sH = 0;
		if(DW.isIE) {
			sH = c + (a - b); //IE
		} else if(b > a) {
			sH = a; //FF
		} else {
			sH = c; //IE & NS
		}
		if(sH == 0) sH = c;
	
		return (wH - sH);
	},
	
	apply: function(delta) {
		var l = this.fillers.length;
		for (var k = 0; k < l; k++) {
			var delta_k = delta / l;
			var v = this.fillers[k].offsetHeight;
			if(v == 0) {
				try {
					var v0 = parseInt(this.fillers[k].style.height);
					if(v0) v = v0;
				} catch (e) {
				}
			}
			if(v + delta_k < 0) delta_k = -v;
			v += delta_k;
			Element.setStyle(this.fillers[k], { "height" : "" + v + "px" });
		}
	}

}

DW.showJustBelowAndCentered = function(dialogId, referenceId) {
	var dialog = $(dialogId);
	if(!dialog) return;
	var reference = (!referenceId) ? dialog : $(referenceId);
	if(!reference) reference = dialog;
	var pos = Position.cumulativeOffset(reference);
	var referenceLeft = pos[0];
	var referenceTop = pos[1];
	var top = referenceTop + reference.offsetHeight;
	var left = referenceLeft - (dialog.offsetWidth - reference.offsetWidth) / 2;

	dialog.style.left = "" + left + "px";
	dialog.style.top = "" + top + "px";
}

DW.getDialogContext = function() {
	return thisDialogContext;
}

function disableDocumentFocusElements(id){
	//this function removes all links and inputs from tabbing navigation
	//if element have tabindex it value saves in component
	var links = document.links;
	var forms = document.forms;
	if(links)
		for (var i=0; i<links.length; i++)
			if(!this.isInMP(links[i],id)){
				if (links[i].tabIndex)
					links[i].prevTabIndex = document.links[i].tabIndex;
				links[i].tabIndex = -1;
			}
	if (forms)
		for (var i=0; i<forms.length; i++)
			for (var j=0; j<forms[i].length; j++)
				if(!this.isInMP(forms[i][j],id)){
					if (forms[i][j].tabIndex)
						forms[i][j].prevTabIndex = forms[i][j].tabIndex;
					forms[i][j].tabIndex = -1;
				}
}

function enableDocumentFocusElements(id){
		//this function restores state of elements tabindex
		var links = top.document.links;
		var forms = top.document.forms;
		if(links)
			for (var i=0; i<links.length; i++)
				if(!this.isInMP(links[i],id))
					if (links[i].prevTabIndex)
						links[i].tabIndex = links[i].prevTabIndex;
					else 
						links[i].tabIndex = 0;
		if (forms)
			for (var i=0; i<document.forms.length; i++)
				for (var j=0; j<document.forms[i].length; j++)
					if(!this.isInMP(document.forms[i][j],id))
						if (forms[i][j].prevTabIndex)
							forms[i][j].tabIndex = forms[i][j].prevTabIndex;
						else
							forms[i][j].tabIndex = 0;
}

function isInMP (elem,id){
		while (elem.parentNode.tagName.toLowerCase() != "body")
			if (elem.parentNode.id == id)
				return true;
			else
				elem = elem.parentNode;
		return false;
}