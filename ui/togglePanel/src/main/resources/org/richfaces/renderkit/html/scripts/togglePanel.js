<!--

TogglePanel = Class.create();

TogglePanel.prototype = {
	initialize: function(panelId, divs, initialStateId) {
		this.panelId = panelId;
		this.divs = divs;
		this.currentId = initialStateId;
	},
	toggleToState: function(stateId) {
		Element.hide(this.panelId+"_"+this.currentId);
		var currentIndex;
		if(stateId!=null) {
			currentIndex = this.divs.indexOf(stateId);
			this.currentId = this.divs[currentIndex];
		} else {
			currentIndex = this.divs.indexOf(this.currentId);
			if(this.divs.length == (currentIndex + 1)) {
				this.currentId = this.divs[0];
			} else {
				this.currentId = this.divs[currentIndex + 1];
			}
		}
		Element.show(this.panelId+"_"+this.currentId);
		
	    $(this.panelId+"_input").value=this.currentId;
		
	}
}

TogglePanelManager = Class.create();

TogglePanelManager.panels = $H($A({}));

TogglePanelManager.add = function(value) {
    var tmp = new Object();
    tmp[value.panelId] = value;
    this.panels=this.panels.merge(tmp);
}

TogglePanelManager.toggleOnServer = function (formId,clientId,stateId) {
	var parentForm = document.getElementById(formId); 
	//document.forms[formId];
	if(parentForm == null) return;

	var params = {};
	params[clientId] = stateId;
	_JSFFormSubmit(null, parentForm, null, params);

	return false;
}

TogglePanelManager.toggleOnClient = function (panelId,swithToStateId) {
	this.panels.get(panelId).toggleToState(swithToStateId);
	return false;
}
//-->
