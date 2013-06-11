/**
* MyFaces compatibility issue
**/
Function.prototype.indexOf = function(){
	return -1;
}

/**
* Clear children
**/

Element.clearChildren = function(element) {
	element = $(element);
    while(element.firstChild) {
		element.removeChild(element.firstChild);
	}
	return element;
};
/**
 * Detect if element is child of another one
 * @param {DomNode} node
 * @param {DomNode} supposedParent
 */
Element.isChildOf = function(node, supposedParent){
	while(node && supposedParent !=node) {
		node = node.parentNode;
	}
	
	return supposedParent == node;
};

if (typeof Node == "undefined") {
	Node = {
		ELEMENT_NODE: 1,
  		ATTRIBUTE_NODE: 2,
  		TEXT_NODE: 3,
  		CDATA_SECTION_NODE: 4,
  		ENTITY_REFERENCE_NODE: 5,
  		ENTITY_NODE: 6,
  		PROCESSING_INSTRUCTION_NODE: 7,
  		COMMENT_NODE: 8,
  		DOCUMENT_NODE: 9,
  		DOCUMENT_TYPE_NODE: 10,
  		DOCUMENT_FRAGMENT_NODE: 11,
  		NOTATION_NODE: 12
	};
};

Element.isUninitialized = function(element) {
	if (element) {
		if (element.nodeType == Node.ELEMENT_NODE) {
		
			if (!element.parentNode || element.document && element.document.readyState == "uninitialized") {
				return true;
			} else
			{
				return !Element.descendantOf(element, document.documentElement);
			}
		
			return false;
		}
	}
};

if (window.RichFaces && window.RichFaces.Memory) {
	window.RichFaces.Memory.addCleaner("prototype", function(node, isAjax) {
		var eventID = node._prototypeEventID ? node._prototypeEventID[0] : undefined;
		if (eventID) {
			var cache = Event.cache[eventID];
	    	
	        for (var eventName in cache) {
	        	var wrappers = cache[eventName];
	        	var domEventName = Event.getDOMEventName(eventName);
	        	
	        	wrappers.each(function(wrapper) {
					if (node.removeEventListener) {
						node.removeEventListener(domEventName, wrapper, false);
					} else {
						node.detachEvent("on" + domEventName, wrapper);
					}
	            });            	
	    		
	            cache[eventName] = null;
	        }
	
	        delete Event.cache[eventID];
		}
	});
}

//
