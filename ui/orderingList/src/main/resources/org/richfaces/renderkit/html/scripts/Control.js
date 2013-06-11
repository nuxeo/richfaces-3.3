if(!window.Richfaces) window.Richfaces = {};

Richfaces.Control = Class.create();

Richfaces.Control.eventStub = function() {
	return false;
}

Richfaces.Control.onfocus = function(element) {
	element.hasFocus = true;
}

Richfaces.Control.onblur = function(element) {
	element.hasFocus = undefined;
}

Richfaces.Control.prototype.initialize = function(eNode, dNode, isShown, isEnabled, action) {
	this.disabledNode = dNode;
	this.disabledNode.onselectstart = Richfaces.Control.eventStub;
	
	this.enabledNode = eNode
	this.enabledNode.onselectstart = Richfaces.Control.eventStub;
	
	this.isShown = isShown;
	this.isEnabled = isEnabled;
	this.action = action;
	//this.isEnabled ? this.doEnable() : this.doDisable();
	//this.isShown ? this.doShow() : this.doHide();
}

/*Control.CLASSES = {
	first : {hidden : "ol_button_border ol_control_hidden", shown : "ol_button_border ol_control_shown"},
	down : {hidden : "ol_button_border ol_control_hidden", shown : "ol_button_border ol_control_shown"},
	up : {hidden : "ol_button_border ol_control_hidden", shown : "ol_button_border ol_control_shown"},
	last : {hidden : "ol_button_border ol_control_hidden", shown : "ol_button_border ol_control_shown"}
};*/

Richfaces.Control.prototype.doShow = function() {
	this.isShown = true;
	if (this.isEnabled) {
		this.doHideNode(this.disabledNode);
		this.doShowNode(this.enabledNode);
	} else {
		this.doHideNode(this.enabledNode);
		this.doShowNode(this.disabledNode);
	}
}

Richfaces.Control.prototype.doHide = function() {
	this.isShown = false;
	this.doHideNode(this.disabledNode);
	this.doHideNode(this.enabledNode);
}

Richfaces.Control.prototype.doEnable = function() {
	this.isEnabled = true;
	this.doHideNode(this.disabledNode);
	this.doShowNode(this.enabledNode);
}

Richfaces.Control.prototype.doDisable = function() {
	this.isEnabled = false;
	
	var nodes = this.enabledNode.select("a[id='" + this.enabledNode.id + "link']");
	
	var newFocusNode = undefined;
	
	if (nodes && nodes[0]) {
		var link = nodes[0];
		if (link.hasFocus) {
			var disNodes = this.disabledNode.select("a[id='" + this.disabledNode.id + "link']");
			if (disNodes && disNodes[0]) {
				newFocusNode = disNodes[0];
			}
		}
	}
	
	this.doHideNode(this.enabledNode);
	this.doShowNode(this.disabledNode);
	if (newFocusNode && newFocusNode.focus) {
		//For IE
		newFocusNode.disabled = false;
		newFocusNode.focus();
		//For IE
		newFocusNode.disabled = true;
	}
}

Richfaces.Control.prototype.doHideNode = function(node) {
	//node.className = Richfaces.Control.CLASSES[this.action].hidden;
	node.hide();
}

Richfaces.Control.prototype.doShowNode = function(node) {
	//node.className = Richfaces.Control.CLASSES[this.action].shown;
	node.show();
}


