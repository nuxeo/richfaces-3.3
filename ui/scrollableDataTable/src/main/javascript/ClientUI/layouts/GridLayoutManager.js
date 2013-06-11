/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.layouts.GridLayoutManager");

ClientUILib.requireClass("ClientUI.common.box.Box");
ClientUILib.requireClass("ClientUI.layouts.VLayoutManager");
ClientUILib.requireClass("ClientUI.layouts.LayoutManager");

var GridLayout_Enum = {
	HEADER: 	1,
	BODY: 		2,
	FOOTER: 	3
};

/*
 * LayoutManager.js - Base class for all layout managers
 * by Denis Morozov <dmorozov@exadel.com> distributed under the BSD license. 
 *
 * TODO: description of control 
 */
ClientUI.layouts.GridLayoutManager = Class.create(ClientUI.layouts.VLayoutManager, {
	initialize: function($super, element, parentElement, config) {
		$super(element, parentElement);
	},
	updateLayout: function() {
		ClientUI.layouts.LayoutManager.prototype.updateLayout.call(this);
		
		var parentBox = this.getContainer();
		if(parentBox==null) parentBox = this;
		var height = parentBox.element.offsetHeight;
		var width = parentBox.getViewportWidth();
		
		// NOTE: not implemented in this class
	  	if(this.panels) {
	  		var headerHeight = 0;
	  		var footerHeight = 0;
			var bodyBottom = 0;
	  		
	  		// first get size of header
	  		if(this.panels[GridLayout_Enum.HEADER]) {
	  			headerHeight = this.panels[GridLayout_Enum.HEADER].element.offsetHeight;
	  			this.panels[GridLayout_Enum.HEADER].moveTo(0, 0);
				this.panels[GridLayout_Enum.HEADER].setWidth(width);
	  			this.panels[GridLayout_Enum.HEADER].updateLayout();
	  		}	  		
	  		// than calculate size of body
	  		if(this.panels[GridLayout_Enum.BODY]) {
				var body = this.panels[GridLayout_Enum.BODY];
	  			body.setWidth(width);
	  			var bodyHeight = height - headerHeight;
	  			body.setHeight(bodyHeight);
	  			body.moveTo(0, headerHeight);
	  			body.updateLayout();
				bodyBottom = body.getY() + body.contentBox.getY() + body.scrollBox.getViewportHeight();
	  		}
			
	  		if(this.panels[GridLayout_Enum.FOOTER]) {
	  			footerHeight = this.panels[GridLayout_Enum.FOOTER].element.offsetHeight;
	  			this.panels[GridLayout_Enum.FOOTER].moveTo(0, bodyBottom - footerHeight);
				this.panels[GridLayout_Enum.FOOTER].setWidth(width);
	  			this.panels[GridLayout_Enum.FOOTER].updateLayout();
	  		}
			
	  	}
	}
});