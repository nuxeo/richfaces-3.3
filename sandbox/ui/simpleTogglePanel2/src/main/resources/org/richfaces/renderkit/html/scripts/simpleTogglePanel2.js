
SimpleTogglePanel = Class.create();

SimpleTogglePanel.prototype = {
	initialize: function(panelId, status, openSign, closeSign) {
	
		this.panelId = panelId;
       this.panelId_head = panelId+"_header";
        this.status = status;
        if (!this.status){
          this.status="true";
        }

        this.openSign = openSign;
        if (!this.openSign) {
            this.openSign = "&#187;";
        }

        this.closeSign = closeSign;
        if (!this.closeSign) {
            this.closeSign = "&#171;";
        }

        //this.divs = divs;
		//this.currentId = initialStateId;
		//this.toggleToState();
//		this.windowOnLoad();
		this.timer = setTimeout(this.windowOnLoad.bind(this), 100);

	},
	
	windowOnLoad: function(){
	  if (RichFaces.navigatorType() == "MSIE"){
		//var body = $(this.panelId+"_body");
		//if (body && body.style.display!="none") body.firstChild.style.width=body.clientWidth;
/*    	    if ($(this.panelId_head).clientWidth<$(this.panelId).clientWidth){
               $(this.panelId_head).style.width=$(this.panelId).clientWidth-2+"px";
	    }*/
	  }
	},
	
	toggleToState: function() {
		var body = $(this.panelId+"_body");
		if (this.status=="false"){
		 	 Element.show(body);
	         this.status="true";
	         $(this.panelId+"_switch").innerHTML = this.closeSign;
			 this.timer = setTimeout(this.windowOnLoad.bind(this), 100);
//		 	 body.firstChild.style.width=body.clientWidth;
	    }

	    else{
		 		 Element.hide(body);
			 	 body.firstChild.style.width="100%";
                 this.status="false";
                 $(this.panelId+"_switch").innerHTML = this.openSign;
                 
		}
	    if (RichFaces.navigatorType() == "MSIE"){
/*    	      if ($(this.panelId_head).clientWidth<$(this.panelId).clientWidth){
                 $(this.panelId_head).style.width=$(this.panelId).clientWidth-2+"px";
	      }*/
	    }
		$(this.panelId+"_input").value=this.status;
	}
}

SimpleTogglePanelManager = Class.create();

SimpleTogglePanelManager.panels = $H($A({}));

SimpleTogglePanelManager.add = function(value) {
    var tmp = new Object();
    tmp[value.panelId] = value;
    this.panels=this.panels.merge(tmp);
}

SimpleTogglePanelManager.toggleOnServer = function (clientId) {
	var parentForm = A4J.findForm($(clientId + "_header"));
	if(!parentForm || !parentForm.appendChild /* findForm returns surrogate form object */) return;

	var fInput = parentForm[clientId];
	if(!fInput) {
		fInput = document.createElement("input");
		fInput.type = "hidden";
		fInput.name = clientId;
		parentForm.appendChild(fInput);
	}

		if (this.panels[clientId].status==0){
	         this.panels[clientId].status=1;
	        } 	
	        else{
                 this.panels[clientId].status=0;
		}
	
	fInput.value = this.panels[clientId].status;
	parentForm.submit();
	return false;
}

SimpleTogglePanelManager.toggleOnClient = function (panelId) {
	this.panels[panelId].toggleToState();
	return false;	
}
