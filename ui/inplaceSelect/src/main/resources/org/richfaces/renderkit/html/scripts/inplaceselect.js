if(!window.Richfaces) window.Richfaces = {};
Richfaces.InplaceSelect = Class.create(Richfaces.InplaceInput, {
	initialize : function($super, listObj, clientId, temValueKeepId, valueKeepId, tabberId, attributes, events, userStyles, commonStyles, barParams, buttonId) {
		this.button = $(buttonId);
		this.comboList = listObj;
		this.showValueInView = attributes.showValueInView;
		$super(clientId, temValueKeepId, valueKeepId, tabberId, attributes, events, userStyles, commonStyles, barParams);
		this.clickOnBar = false;
		this.inplaceSelect = $(clientId);		
		this.inplaceSelect.component = this;
				
		this.currentItemValue = this.value;
		this.button.style.top = Richfaces.getBorderWidth(this.tempValueKeeper, "tb") + "px";
		this["rich:destructor"] = "destroy";
	},
	
	destroy: function() {
		this.inplaceSelect.component = null;
	},
	
	
	initHandlers : function($super) {
		$super();
		this.tempValueKeeper.observe("click", function(e){this.tempKeeperClickHandler(e);}.bindAsEventListener(this));
		this.button.observe("click", function(e){this.tempKeeperClickHandler(e);}.bindAsEventListener(this));
		this.button.observe("blur", function(e){this.tmpValueBlurHandler(e);}.bindAsEventListener(this));
		
		this.comboList.listParent.observe("mousedown", function(e){this.listMousedownHandler(e);}.bindAsEventListener(this));
		this.comboList.listParent.observe("mousemove", function(e){this.listMouseMoveHandler(e)}.bindAsEventListener(this));
		this.comboList.listParent.observe("click", function(e){this.listClickHandler(e);}.bindAsEventListener(this));
		this.comboList.listParent.observe("mouseup", function(e){this.listMouseUpHandler(e);}.bindAsEventListener(this));
	},
	
	setInputWidth : function($super, textSize) {
		$super(textSize);
		this.button.show();
		var width = parseInt(this.tempValueKeeper.style.width);
		this.button.style.left = ( width - this.button.offsetWidth) + "px";
		return width;
	},
	
	startEditableState : function($super) {
		$super();
		this.button.show();
		if (this.attributes.openOnEdit) {
			this.comboList.showWithDelay();
		}
	},
	
	endEditableState : function($super) {
		$super();
		//this.button.style.display = 'none';
		this.button.hide();
	},
	
	tempKeeperClickHandler : function() {
		this.comboList.isList = false;
		this.comboList.showWithDelay();
	},
	
	buttonClickHandler : function(e) {
		this.button.isClicked = true;
		this.tempKeeperClickHandler();
		Event.stop(e);
	},
	
	tmpValueBlurHandler : function($super, event) {
		if (this.clickOnBar || (Richfaces.browser.isIE && this.button.isClicked)) {
			//tab navigation&clickOnbar handler
			this.clickOnBar = false;
			this.button.isClicked = false;
			return;
		}
		
		if (!this.comboList.isList) {
			//click somewhere out of the component area 
			if (this.comboList.activeItem) {
				this.comboList.doNormalItem(this.comboList.activeItem);
			}
			this.comboList.hideWithDelay();
				
		}
		
		if (this.clickOnScroll) {
			//click on scroll under IE 
			this.clickOnScroll = false;
			this.tempValueKeeper.focus();
			this.comboList.isList = false;
			return;
		}
		
		if (!this.attributes.showControls)  {
			this.save(event);
		} else {
			this.applyTmpValue();
		}
		this.comboList.isList = false;
	},
	
	listClickHandler : function(event) {
		this.comboList.hideWithDelay();
	},
	
	listMouseMoveHandler : function(event) {
		//changes item's decoration
		var item = this.comboList.getEventItem(event);
		if (item) {
			this.comboList.doActiveItem(this.comboList.getEventItem(event));			
		}
	},
	
	listMousedownHandler : function(e) {
		//TODO:fix it
		if (Prototype.Browser.Gecko) {
			if (this.comboList.getEventItem(e)) {
				this.comboList.isList = true;			
			}
		} else {
			if (!this.comboList.getEventItem(e)) {
				this.clickOnScroll = true;
			}
			this.comboList.isList = true;
		}
	},
	
	listMouseUpHandler : function(e) {
		if (window.getSelection) {
			if (window.getSelection().getRangeAt(0).toString() != '') {
				this.tempValueKeeper.focus();	
				this.comboList.isList = false;
			}
		}
	},
	
	tmpValueKeyDownHandler : function(event) {
		switch (event.keyCode) {
			case Event.KEY_RETURN :
				this.comboList.isList = false; 
				this.save(event);
				if (!this.attributes.showControls) {
					this.tempValueKeeper.blur();
				}
				Event.stop(event);
				break;
			case Event.KEY_DOWN : 
				this.comboList.moveActiveItem(event);
				Event.stop(event);
				break;
			case Event.KEY_UP :
				this.comboList.moveActiveItem(event);
				Event.stop(event);
				break; 
			case Event.KEY_ESC : 
				this.comboList.resetSelection();
				this.comboList.hideWithDelay();
				this.cancel(event);
				if (!this.attributes.showControls) {
					this.tempValueKeeper.blur();
				}
				break;
			case Event.KEY_TAB :
				//https://jira.jboss.org/jira/browse/RF-4422
				/*if (this.attributes.showControls) {
					this.save(event);
				}*/
				break;
		}
	},
	
	save : function($super,e) {
		this.applyTmpValue();
		this.comboList.hide();
		if (((this.attributes.closeOnSelect && !this.attributes.showControls) && this.comboList.isList) 
			|| (this.clickOnBar || !this.comboList.isList)) {
				var unescapedValue = this.currentItemValue;
				this.setValue(unescapedValue);
		}
	},
	
	findLabel : function(lookupItems, value) {
		var items = lookupItems;  
		for(var i=0; i < items.length; i++) {
			if(items[i][1] == value) {
				return items[i][0]; 
			}
		}
	}, 
		
	setValue : function(e, param) {
		var item = {};
		item.itemValue =  this.getParameters(e,param,"value");
		
		if(this.showValueInView) {
			item.itemLabel = item.itemValue;
		} else {
			item.itemLabel = this.findLabel(this.comboList.itemsText,item.itemValue);
		}	
		
		if (!item.itemLabel) {
			item.itemValue = this.currentItemValue;
			item.itemLabel = this.tempValueKeeper.value; 		
		}
		 
		var value = this.valueKeeper.value;
		if (Richfaces.invokeEvent(this.events.onviewactivation, this.inplaceInput, "rich:onviewactivation", {oldValue : this.valueKeeper.value, value : item.itemValue})) {
			var proceed = true;
		
			if(item.itemLabel && value != item.itemValue) {
				proceed = Richfaces.invokeEvent(this.events.onchange, this.inplaceSelect, "onchange", item.itemValue);
			}
			
			if(proceed) {
				this.endEditableState();
				
				if (!item.itemValue || item.itemValue.blank()) {
					item.itemValue = "";
				} 
								
				if (!item.itemLabel || item.itemLabel.blank()) {
					item.itemLabel = this.attributes.defaultLabel; 				
				}

				this.valueKeeper.value = item.itemValue;
				this.currentText = item.itemLabel;
				this.tempValueKeeper.value = item.itemLabel;				
				
				if (item.itemValue != this.value) {
					this.startChangedState();
				} else {
					this.startViewState();
				}
				
				if (this.events.onviewactivated) {
					this.inplaceInput.fire("rich:onviewactivated", {oldValue : value, value : this.valueKeeper.value});
				}
				
			} else {
				this.cancel();
			}
					
		}
		
	},
	
	applyTmpValue : function() {
		if (this.comboList.activeItem) {
			var userLabel = this.comboList.activeItem.innerHTML.unescapeHTML();
			this.currentItemValue = this.comboList.activeItem.value;
			if(this.showValueInView) {
				userLabel = this.currentItemValue;
			} 
			this.tempValueKeeper.value = userLabel;
			this.comboList.selectedItem = this.comboList.activeItem;
		}
	},
	
	deleteViewArtifacts : function () {
		var text;
		// IE6 support TODO: remove???? 
		if (this.comboList.iframe) {
			text = this.inplaceInput.childNodes[6];
		} else {
			text = this.inplaceInput.childNodes[5];	
		}	
		
		if (text) {
			this.inplaceInput.removeChild(text);
		}
	},
	
	getCurrentText : function() {
		var currentText; 
		// IE6 support	
		if (this.comboList.iframe) {
			currentText = this.inplaceInput.childNodes[6];
		} else {
			currentText = this.inplaceInput.childNodes[5];
		}
		
		return currentText;
	},
	
	getLabelItem : function(value) {
		var length = this.comboList.getItems().length;
		for (var i = 0; i < length; i++) {
			var item = this.comboList.getItems()[i];
			if (item.value == value) {
				return item;
			}
		}
	},
	
	cancel : function($super, e) {
		var item = this.getLabelItem(this.valueKeeper.value);
		this.comboList.resetSelection();
		this.comboList.hide();
		if (item) {
			this.comboList.doSelectItem(item);
			var value = this.showValueInView ?  this.valueKeeper.value : item.innerHTML.unescapeHTML(); 
			$super(e, value);
		} else {
			$super(e, "");
		}
	}
	
});
	

