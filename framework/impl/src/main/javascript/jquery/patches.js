if (!window.RichFaces) {
	window.RichFaces = {};
}

// calling jQuery(jQuery) makes memory leaks
//if (jQuery(jQuery) != jQuery) {
if (!window.RichFaces.isJQueryWrapped) {
	var oldJQuery = jQuery;
// moved to original jQuery function
//	jQuery = function() {
//		if (arguments[0] == arguments.callee) {
//			return arguments.callee;
//		} else {
//			return oldJQuery.apply(this, arguments);
//		}
//	};
	
	if (window.RichFaces && window.RichFaces.Memory) {
		window.RichFaces.Memory.addCleaner("jquery", function(node, isAjax) {
			if (node && node[oldJQuery.expando]) {
				oldJQuery.event.remove(node);
				oldJQuery.removeData(node);
			}
		});
	}
	window.RichFaces.isJQueryWrapped = true;
};