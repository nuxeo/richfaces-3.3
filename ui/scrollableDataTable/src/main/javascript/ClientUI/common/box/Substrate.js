/**
 * Substrate.js		Date created: 21.04.2007
 * Copyright (c) 2007 Exadel Inc.
 * @author Denis Morozov <dmorozov@exadel.com>
 */
ClientUILib.declarePackage("ClientUI.common.box.Substrate");

ClientUILib.requireClass("ClientUI.common.box.Box");

/**
 * Base class that wrap work with inline blocks like span
 */
ClientUI.common.box.Substrate = Class.create(ClientUI.common.box.Box, {

	initialize: function($super, element, parentElement, dontUpdateStyles) {
		if(!element) {			
			var fakeElement = $(document.createElement("div"));
			fakeElement.innerHTML = '<iframe id="'+'ClientUI_Substrate' + (ClientUI_common_box_Substrate_idGenerator++) +'" src="javascript:\'\'" scrolling="no" frameborder="0" style="filter:Alpha(opacity=0);position:absolute;top:0px;left:0px;display:block"></iframe>';
			element = $(fakeElement.getElementsByTagName("iframe")[0]);
			fakeElement.removeChild(element);
      	}		
		
		$super(element, parentElement, dontUpdateStyles);
		
		// additional styles
		if(!dontUpdateStyles) {
		}
	}
});

if(!ClientUI_common_box_Substrate_idGenerator) {
	var ClientUI_common_box_Substrate_idGenerator = 0;
};