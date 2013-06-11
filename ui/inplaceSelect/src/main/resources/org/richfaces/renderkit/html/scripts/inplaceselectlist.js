if(!window.Richfaces) window.Richfaces = {};
Richfaces.InplaceSelectList = Class.create(Richfaces.ComboBoxList, {
	initialize : function($super, listId, parentListId, selectFirstOnUpdate, userStyles, commonStyles, width, height, itemsText, onlistcall, fieldId, shadowId, showDelay, hideDelay, value) {
		this.classes = Richfaces.mergeStyles(userStyles, commonStyles);
		$super(listId, parentListId, selectFirstOnUpdate, null, this.classes, width, height, itemsText, onlistcall, null /* onlistclose */, fieldId, shadowId, showDelay, hideDelay);
		this.wrappingItems(value);
		this.isListOpened = false;
	},
	
	setPosition : function($super, fieldTop, fieldLeft, fieldHeight) {
		var field = this.fieldElem;
		
		field.show();
		if (Richfaces.browser.isIE6 && !this.iframe) {
			this.createIframe(this.listParent.parentNode, this.width, this.list.id, "");
		}
					
		$super(fieldTop, fieldLeft, field.offsetHeight);
	},
	
	show : function($super) {
		if(!this.listInjected) {
			this.isListOpened = true;
			$super();
		}	
	},
	
	resetState : function() {
		this.activeItem = null;
	},
	
	getEventItem : function(event) {
		return Event.findElement(event, "span");;
	},
	
	setWidth : function(width) {
		var positionElem = this.listParent.childNodes[1];
		var correction = parseInt(width) - Richfaces.getBorderWidth(positionElem.firstChild, "lr") - Richfaces.getPaddingWidth(positionElem.firstChild, "lr") + "px";
		this.list.style.width = correction;
		if (this.iframe) {
			this.iframe.style.width = correction;			
		}
	},
	
	hide : function() {
		if (this.isListOpened) {
			this.isListOpened = false;
			this.listParent.hide();
			this.outjectListFromBody(this.listParentContainer, this.listParent);
			this.resetState();
			if (this.iframe) {
				Element.hide(this.iframe);
			}
			var component = this.listParent.parentNode;
			component.style.zIndex = 0;
			
		}	
	},
	
	outjectListFromBody: function(parentElement, listElement) {
		if (this.listInjected) {
			var child = parentElement.insertBefore(document.body.removeChild(listElement), parentElement.childNodes[3]);
			if (Richfaces.browser.isIE6 && this.iframe) {
				parentElement.insertBefore(document.body.removeChild(this.iframe), child);
			}
			this.listInjected = false;
		}	
	},
	
	wrappingItems : function(value) {
		var len = this.getItems().length;
		for (var i = 0; i < len; i++) {
			var it = this.getItems()[i];
			it.value = this.itemsText[i][1];
			if (it.value == value) {
				this.doSelectItem(it);
			}
		}
	},
	
	resetSelection: function() {
		if (this.activeItem) {
			this.doNormalItem(this.activeItem);
		}
		if (this.selectedItem) {
			this.doNormalItem(this.selectedItem);
		}
	},

	doActiveItem : function($super, item) {
		this.listParent.show();
		$super(item);
		// http://jira.jboss.com/jira/browse/RF-2859
		var listWidth = this.list.scrollWidth; 
		if( listWidth == this.list.offsetWidth) { 
			listWidth = listWidth- Richfaces.getScrollWidth(this.list);
		}	 
	
		listWidth = listWidth  - (Richfaces.getBorderWidth(item,"lr") + Richfaces.getPaddingWidth(item,"lr") + Richfaces.getMarginWidth(item,"lr"));
		item.style.width = listWidth + "px";
	},

	doNormalItem : function($super, item) {
		$super(item);
		item.style.width = "";
	},
	
	findItemByDOMNode : function($super, node) {
		//https://jira.jboss.org/jira/browse/RF-5710
		//need to search for value
		var value = node.value;
		var items = this.getItems();
		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			var itValue = item.value;
			if (value == itValue) {
				return item;
			}
		}
	}
	
});
