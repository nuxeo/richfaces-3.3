/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.layouts.VLayoutManager");

ClientUILib.requireClass("ClientUI.common.box.Box");
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
ClientUI.layouts.VLayoutManager = Class.create(ClientUI.layouts.LayoutManager, {	
	initialize: function($super, element, parentElement, config) {
		$super(element, parentElement);		
		this.registerEvents();
	},
	registerEvents: function($super) {
		$super();
	},
	destroy: function($super) {
		$super();
	},
   	/*containerResize: function(event) {
   		ClientUI.layouts.VLayoutManager.parentClass.method("containerResize").call(this, event);
   		this.updateLayout();
	},*/
	addPane: function(align, pane) {
		if(!this.panels) { this.panels = []; }

		this.panels[align] = pane;
		this.panels[align].makeAbsolute();
		//this.panels[align].setParent(this);
	},
	getPane: function(align) {
		return this.panels[align];
	},
	updateLayout: function($super) {
		$super();
		
		var parentBox = this.getContainer();
		if(!parentBox) parentBox = this;
		var height = parentBox.element.offsetHeight;
		var width = parentBox.getViewportWidth();
		if(ClientUILib.isGecko) {
			width -= parentBox.getBorderWidth("lr") + parentBox.getPadding("lr");
			height -= parentBox.getBorderWidth("tb") + parentBox.getPadding("tb");
		}			
		
		// NOTE: not implemented in this class
	  	if(this.panels) {
	  		var headerHeight = 0;
	  		var footerHeight = 0;
	  		
	  		// first get size of header
	  		if(this.panels[GridLayout_Enum.HEADER]) {
	  			headerHeight = this.panels[GridLayout_Enum.HEADER].element.offsetHeight;
	  			this.panels[GridLayout_Enum.HEADER].moveTo(0, 0);
				this.panels[GridLayout_Enum.HEADER].setWidth(width);
	  			this.panels[GridLayout_Enum.HEADER].updateLayout();
	  		}
	  		if(this.panels[GridLayout_Enum.FOOTER]) {
	  			footerHeight = this.panels[GridLayout_Enum.FOOTER].element.offsetHeight;
	  			this.panels[GridLayout_Enum.FOOTER].moveTo(0, height - footerHeight);
				this.panels[GridLayout_Enum.FOOTER].setWidth(width);
	  			this.panels[GridLayout_Enum.FOOTER].updateLayout();
	  		}
	  		// than calculate size of body
	  		if(this.panels[GridLayout_Enum.BODY]) {
				var body = this.panels[GridLayout_Enum.BODY];
	  			body.setWidth(width);
	  			var bodyHeight = height - (headerHeight + footerHeight);
	  			body.setHeight(bodyHeight);
	  			body.moveTo(0, headerHeight);
	  			body.updateLayout();
	  		}			
	  	}
	}	
});

if(!ClientUI_layouts_VLayoutManager_idGenerator) {
	var ClientUI_layouts_VLayoutManager_idGenerator = 0;
};