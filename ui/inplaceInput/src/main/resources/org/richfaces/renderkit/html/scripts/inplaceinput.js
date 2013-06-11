if(!window.LOG){
	window.LOG = {warn:function(){}};
}

if (!window.Richfaces) window.Richfaces = {};
Richfaces.InplaceInput = Class.create();


Richfaces.InplaceInput.prototype = {
	//TODO: remove $$$$$
	initialize: function(clientId, temValueKeepId, valueKeepId, tabberId, attributes, events, userStyles, commonStyles, barParams) {
		//TODO: delete tabberId from parameters
		this.inplaceInput = $(clientId);
		this.inplaceInput.component = this;
		
		this.tempValueKeeper = $(temValueKeepId);
		this.valueKeeper = $(valueKeepId);
		this.attributes = attributes;
		this.events = events;
		 
		//TODO: static methods are preferred to be called within constructor
		this.currentText = this.getCurrentText();
		this.value = this.valueKeeper.value;
		
		this.currentState = Richfaces.InplaceInput.STATES[0];
		this.prevState = Richfaces.InplaceInput.STATES[0];
		
		if (this.attributes.showControls) {
			//TODO: Consider passing attributes by name instead of by index
			this.bar = new Richfaces.InplaceInputBar(barParams[0], barParams[1], barParams[2], barParams[3], barParams[4],  
													 this.attributes.verticalPosition, this.attributes.horizontalPosition);
		}
		//TODO: move converting 'on'-event to prototype style to utils
		
		this.editEvent = this.attributes.editEvent.substring(2,this.attributes.editEvent.length);
		
		this.tempValueKeeper.style.top = Richfaces.getBorderWidth(this.tempValueKeeper, "t") + "px";
		
		this.initHandlers();	
		this.initEvents();
		this.classes = Richfaces.mergeStyles(userStyles,commonStyles.getCommonStyles());
		this["rich:destructor"] = "destroy";
		
		this.skipSwitching = false;
	},
	
	//TODO: more cleanup here - remove references to DOM elements
	destroy: function() {
		this.inplaceInput.component = null;
	},
		
	initHandlers : function() {
		this.inplaceInput.observe(this.editEvent, this.switchingStatesHandler.bindAsEventListener(this));
		this.inplaceInput.observe("mouseout", this.inplaceMouseOutHandler.bindAsEventListener(this));
		this.inplaceInput.observe("mouseover", this.inplaceMouseOverHandler.bindAsEventListener(this));
	
		//TODO: do like above - no need to create extra functions		
		this.tempValueKeeper.observe("focus", function(e){this.switchingStatesHandler(e);}.bindAsEventListener(this));
		this.tempValueKeeper.observe("blur", function(e){this.tmpValueBlurHandler(e);}.bindAsEventListener(this));
		this.tempValueKeeper.observe("keydown", function(e){this.tmpValueKeyDownHandler(e);}.bindAsEventListener(this));
		
		if (this.bar) {
			if (this.bar.ok) {
				this.bar.ok.observe("mousedown", function(e){this.okHandler(e);}.bindAsEventListener(this));			
			
				if (Richfaces.browser.isOpera) {
					this.bar.ok.observe("click", Event.stop); 
				}
			}
			
			if (this.bar.cancel) {
				this.bar.cancel.observe("mousedown", function(e){this.cancelHandler(e)}.bindAsEventListener(this));

				if (Richfaces.browser.isOpera) {
					this.bar.cancel.observe("click", Event.stop); 
				}
			}
		}
	},
	
	initEvents : function() {
		for (var e in this.events) {
			if (e) {
				this.inplaceInput.observe("rich:" + e, this.events[e]);
			}
		}
	},
	
	/*
	 *  HANDLERS
	 */
	 
	inplaceMouseOverHandler : function(e) {
		//TODO: Nick recommends regexp
		var className = this.inplaceInput.className; 
		if (this.currentState == Richfaces.InplaceInput.STATES[0]) {
			if (className.indexOf(this.classes.component.view.hovered) == -1) {
				className += " " + this.classes.component.view.hovered;
			}
		} else if (this.currentState == Richfaces.InplaceInput.STATES[2]) {
			if (className.indexOf(this.classes.component.changed.hovered) == -1) { 
				className += " " + this.classes.component.changed.hovered;
			}	 
		}
		this.inplaceInput.className = className;
	},
	
	inplaceMouseOutHandler : function(e) {
		if (this.currentState == Richfaces.InplaceInput.STATES[0]) {
			this.inplaceInput.className = this.classes.component.view.normal;
		} else if (this.currentState == Richfaces.InplaceInput.STATES[2]) {
			this.inplaceInput.className = this.classes.component.changed.normal; 
		}
	},
	
	//TODO: Event.element here?
	switchingStatesHandler : function(e) {
			if (this.skipSwitching) {
				this.skipSwitching = false;
				return;
			}
			var el = (e.srcElement) ? e.srcElement : e.target;
			if (this.currentState != Richfaces.InplaceInput.STATES[1]) {
				this.edit();
			}
			if (el.id == this.inplaceInput.id) {
				this.skipSwitching = true;
				this.tempValueKeeper.focus();
			}
	},
	
	edit: function (){
		if (Richfaces.invokeEvent(this.events.oneditactivation, this.inplaceInput, "rich:oneditactivation", {oldValue : this.valueKeeper.value, value : this.tempValueKeeper.value})) {
			this.startEditableState();
			if (this.events.oneditactivated) {
				this.inplaceInput.fire("rich:oneditactivated", {oldValue : this.valueKeeper.value, value : this.tempValueKeeper.value});
			}
		}
	},
	
	tmpValueBlurHandler : function(e) {
		if (this.skipBlur) {
			this.skipBlur = false;
			return;
		}
		
		if (this.clickOnBar) {
			this.clickOnBar = false;
			return;
		}
		
		if (!this.attributes.showControls) {
			this.save(e);
		}
	},
	
	tmpValueKeyDownHandler : function(e) {
		switch (e.keyCode) {
			case Event.KEY_ESC :
				this.cancel(e);
				if (!this.attributes.showControls) {
					this.skipBlur = true;
					this.tempValueKeeper.blur();
				}
				Event.stop(e); 
				break;
			case Event.KEY_RETURN :
				if (this.attributes.showControls) {
					this.okHandler(e);
				} else {
					Event.stop(e);
					this.tempValueKeeper.blur();
				}
				break;
			case Event.KEY_TAB :
				if (this.attributes.showControls) {
					this.save(e);
				}
				break;
		}
	},
	
	okHandler : function(e) {
		this.save(e);
		Event.stop(e);
	},
	
	cancelHandler : function(e) {
		this.cancel(e);
		Event.stop(e);
	},
	
	/**
	 * STATE'S API
	 */
	
	endEditableState : function() {
		//this.inplaceInput.style.position = "";
		
		if (this.bar) {
			this.bar.hide();
		}
		
		this.tempValueKeeper.style.clip = 'rect(0px 0px 0px 0px)';
		this.tempValueKeeper.blur();
	},
	
	/*endChangedState : function() {
		this.deleteViewArtifacts();
	},*/
	
	startEditableState : function() {
		if (this.currentState == Richfaces.InplaceInput.STATES[1]) {
			return;
		}
		//this.inplaceInput.style.position = "relative";
		this.changeState(Richfaces.InplaceInput.STATES[1]);
		var textWidth= this.inplaceInput.offsetWidth;
		var inputSize = this.setInputWidth(textWidth);
		
		this.tempValueKeeper.style.clip = 'rect(auto auto auto auto)';
		this.inplaceInput.className = this.classes.component.editable;
		
		if (this.bar) {
			this.bar.show(inputSize, this.tempValueKeeper.offsetHeight);
		}
		
		if (this.attributes.selectOnEdit) {
			//TODO: implement Select all method?
			Richfaces.InplaceInput.textboxSelect(this.tempValueKeeper, 0, this.tempValueKeeper.value.length);
		} 
	},
	
	startViewState : function() {
		this.deleteViewArtifacts();
		this.changeState(Richfaces.InplaceInput.STATES[0]);
		
		this.createNewText(this.currentText);
		this.inplaceInput.className = this.classes.component.view.normal;
		//TODO: see above
		this.inplaceInput.observe("mouseover", function(e){this.inplaceMouseOverHandler(e);}.bindAsEventListener(this));
	},
	
	startChangedState : function () {
		this.deleteViewArtifacts();
		
		this.changeState(Richfaces.InplaceInput.STATES[2]);
		
		this.createNewText(this.currentText);
		this.inplaceInput.className = this.classes.component.changed.normal;
	},
	
	/**
	 * UTILITIES
	 */
	 
	//TODO: rename parameter to textWidth?
	setInputWidth : function(textWidth) {
		//TODO: use constants here
		if (this.currentState != 1) {
			return;
		}
		
		
		var width = parseInt(this.attributes.inputWidth);
		
		if (!width) {
			var max = parseInt(this.attributes.maxInputWidth);
			var min = parseInt(this.attributes.minInputWidth);
			if (textWidth > max) {
				width = max;
			} else if (textWidth < min) {
				width = min;
			} else {
				width = textWidth;
			}
		}
		
		this.tempValueKeeper.style.width = width + "px";
		return width;
	},
	
	changeState : function(newState) {
		this.prevState = this.currentState; 	
		this.currentState = newState;
	},
	
	cancel : function(e, value) {
		if (Richfaces.invokeEvent(this.events.onviewactivation, this.inplaceInput, "rich:onviewactivation", {oldValue : this.valueKeeper.value, value : this.tempValueKeeper.value})) {
			this.endEditableState();
			if (!value) {
				value = this.valueKeeper.value;	
			} 
			this.tempValueKeeper.value = value;
			this.currentText = value; 
			if ( this.tempValueKeeper.value == "") {
				this.setDefaultText();
			}
			switch (this.prevState) {
				case Richfaces.InplaceInput.STATES[0] :
					this.startViewState();
					break;
				case Richfaces.InplaceInput.STATES[2] : 
					this.startChangedState();
					break;
			}
			
			if (this.events.onviewactivated) {
				this.inplaceInput.fire("rich:onviewactivated", {oldValue : this.valueKeeper.value, value : this.tempValueKeeper.value});
			}
		}
	},
	
	save : function(e) {
		if (Richfaces.invokeEvent(this.events.onviewactivation, this.inplaceInput, "rich:onviewactivation", {oldValue : this.valueKeeper.value, value : this.tempValueKeeper.value})) {
			var userValue = this.tempValueKeeper.value;
			this.setValue(userValue);
			if (this.events.onviewactivated) {
				this.inplaceInput.fire("rich:onviewactivated", {oldValue : this.valueKeeper.value, value : this.tempValueKeeper.value});
			}
		}	
	},
	
	getParameters : function(event, params, paramName) {
		
		if (!params) {
			params = event;
		}
		if (params && params[paramName]) {
			return params[paramName];
		} 
		return params;
	},
	
	setValue : function(e, param) {
		var value = this.valueKeeper.value;
		var userValue = this.getParameters(e,param,"value");
		var proceed = true;
		
		if(value != userValue) {
			proceed = Richfaces.invokeEvent(this.events.onchange, this.inplaceInput, "onchange", userValue);	
		} 
		
		if (proceed) {
			this.tempValueKeeper.value = userValue;
			this.endEditableState();
			if (userValue.blank()) {
				this.setDefaultText();
				this.valueKeeper.value = "";
			} else {
				this.currentText = userValue;
				this.valueKeeper.value = userValue;
			}
		
			if (userValue != this.value) {
				this.startChangedState();
			} else {
				this.startViewState();
			}
		} else {
			this.cancel();
		}	
	},
	
	getValue : function() {
		return this.valueKeeper.value;
	},

	setDefaultText : function() {
		this.currentText = this.attributes.defaultLabel;
	},
	
	deleteViewArtifacts : function () {
		var text = this.inplaceInput.childNodes[3];
		if (text) {
			this.inplaceInput.removeChild(text);
		}
	},
	//TODO: search within childNodes for textNode.value().trim().length == 0?
	getCurrentText : function() {
		return this.inplaceInput.childNodes[3];
	},
	
	createNewText : function(text) {
		if (!this.getCurrentText()) {
			this.inplaceInput.appendChild(document.createTextNode(text.nodeValue||text));
		}
	}
	
};

Richfaces.InplaceInputBar = Class.create();
Richfaces.InplaceInputBar.prototype = {
	initialize : function(barId, okId, cancelId, buttonsPanelId, buttonsShadowId, verticalPosition, horizontalPosition) {
		this.bar = $(barId);
		this.ok = $(okId);
		this.cancel = $(cancelId);
		this.bsPanel = $(buttonsPanelId);
		this.buttonsShadow = $(buttonsShadowId);
		
		this.verticalPosition = verticalPosition;
		this.horizontalPosition = horizontalPosition;
		this.initDimensions();
	},
	
	initDimensions : function() {
		//TODO: Crap code magic numbers - I propose 325 and 675
		
		this.BAR_WIDTH = 26;
		this.BAR_HEIGHT = 12;	
				
	},
	
	show : function(inpWidth, inpHeight) {
		this.positioning(inpWidth, inpHeight);
		if (Richfaces.browser.isIE6 && this.buttonsShadow) {
			this.buttonsShadow.style.visibility = "hidden";
		}
		this.bar.show();
	},
	
	hide : function() {
		this.bar.hide();
	},
	
	positioning : function(inpWidth, inpHeight) {
		if (this.bsPanel) {
			this.bsPanel.style.height = inpHeight + "px";
		}	
		
		
		this.bar.style.position = "absolute";
		var bs = this.bar.style;
		
		if (this.verticalPosition == "top" || this.verticalPosition == "bottom") {
			switch (this.horizontalPosition) {
				case "left" : 
					bs.left = "0px"; 
					break;
				case "right" : 
					bs.left = inpWidth + "px"; 
					break;
				case "center" : 
					bs.left = parseInt(inpWidth/2 - this.BAR_WIDTH/2) + "px"; 
					break; 
			}
			
			if (this.verticalPosition == "top") {
				bs.top = -this.BAR_HEIGHT + "px";
			} else {
				bs.top = inpHeight + "px";
			}
		} else if (this.verticalPosition == "center") {
			bs.top = "0px";
			switch (this.horizontalPosition) {
				case "left" : 
					bs.left = -this.BAR_WIDTH + "px";
					break;
				case "right" :
					bs.left = inpWidth + "px"; 
					break;
				case "center" :	
					bs.left = inpWidth + "px"; 
					break; 	
			}
		}
	}
};

Richfaces.InplaceInput.STATES = [0, 1, 2];// 0 - view, 1- editable, 2 - changed

Richfaces.InplaceInput.textboxSelect = function(oTextbox, iStart, iEnd) {
   if (Prototype.Browser.IE) {
	   //TODO: dont check 4 browser. Check for capability instead.
       var oRange = oTextbox.createTextRange();
       oRange.moveStart("character", iStart);
       oRange.moveEnd("character", -oTextbox.value.length + iEnd);      
       oRange.select();                                              
   } else if (Prototype.Browser.Gecko) {
       oTextbox.setSelectionRange(iStart, iEnd);
   } else {
   		//FIXME
   		oTextbox.setSelectionRange(iStart, iEnd);
   }                    
};