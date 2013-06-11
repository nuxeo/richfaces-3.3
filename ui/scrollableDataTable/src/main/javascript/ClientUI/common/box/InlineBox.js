/**
 * InlineBox.js		Date created: 6.04.2007
 * Copyright (c) 2007 Exadel Inc.
 * @author Denis Morozov <dmorozov@exadel.com>
 */
ClientUILib.declarePackage("ClientUI.common.box.InlineBox");

ClientUILib.requireClass("ClientUI.common.box.Box");

/**
 * Base class that wrap work with inline blocks like span
 */
ClientUI.common.box.InlineBox = Class.create(ClientUI.common.box.Box, {

	initialize: function($super, element, parentElement, dontUpdateStyles) {
		if(!element) {
			element = $(document.createElement("span"));
			if($(parentElement)) {
      			$(parentElement).appendChild(element);
			}
      		else {
	      		document.body.appendChild(element);
      		}
      	}		
      	if(!element.id) {
			element.id = "ClientUI_InlineBox" + ClientUI_common_box_InlineBox_idGenerator++;
		}
		
		$super(element, parentElement, dontUpdateStyles);
		
		// additional styles
		if(!dontUpdateStyles) {
			this.element.setStyle({display: 'block'});	
		}
	}
});

if(!ClientUI_common_box_InlineBox_idGenerator) {
	var ClientUI_common_box_InlineBox_idGenerator = 0;
};