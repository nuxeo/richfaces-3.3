var Utils = {
	
	DOM: {
		copyAttributes : function(target, source, opts) {

			//LOG.debug("copyAttributes");
			var attrs = source.attributes;
			
			var exclusions = (opts && opts.exclude) ? opts.exclude : [];
			
			for(var i = 0 ; i < attrs.length; i++) {
			
				var attributeNode = attrs[i];
				var nodeName = attributeNode.nodeName;
				var nodeValue = attributeNode.nodeValue;
				
				var shouldCheckAttribute = 
					nodeValue && 
					nodeValue.length > 0 && 
					exclusions.indexOf(nodeName) < 0;
				
				if (ClientUILib.isIE) {
					shouldCheckAttribute &= attributeNode.specified;
				}
				
				if(shouldCheckAttribute) {
					
					//LOG.debug("Copying attribute " + attributeNode.nodeName + "=" + attributeNode.nodeValue);
					
					var newAttributeNode = 
						document.createAttribute(nodeName);
					
					newAttributeNode.nodeValue = attributeNode.nodeValue;
					target.setAttributeNode(newAttributeNode);
				}
				
			}
			//LOG.debug("/copyAttributes");
		},
		
		replaceNode : function(id, request) {
			var target = document.getElementById(id);
			var src = request.getElementById(id);
	
			if(target && src) {
				var cells = target.cells;
				for(var i = 0; i < cells.length; i++) {
					Utils.DOM.Event.removeListeners(cells[i]);
				}
				
				if (ClientUILib.isIE) {
					var s = String();
					var newOuterXml = "<table><tbody>" + src.xml + "</tbody></table>";
					var newNode = document.createElement("DIV");

					newNode.innerHTML = newOuterXml;

					var imported = newNode.firstChild.firstChild.firstChild;
					target.parentNode.replaceChild(imported, target);;
					return imported;
					
				} else if (ClientUILib.isGecko){
					//Mozill family
					var theDoc = document;
				
					Utils.DOM._clearAttributes(target);
					Utils.DOM.copyAttributes(target, src);

					target.innerHTML = src.innerHTML;//nnerHTML.join("");
					return target;
				} else {
					//Fall back to DOM, and cross the fingers
					src = document.importNode(src, true);
					target.parentNode.replaceChild(src, target);
					return src; 
				}
			}
			else {
				if(!target)
					ClientUILib.log(ClientUILogger.ERROR, "DOM Element with id " + id + " not found for update.");
				if(!src) {
					ClientUILib.log(ClientUILogger.ERROR, "RESPONSE Element with id " + id + " not found for update.");
					
					// cleanup destination
					if(target) {
						for(var i=0; i<target.cells.length; i++) {
							target.cells[i].innerHTML = "";
						}
					}
				}
			}
		},
		
		_clearAttributes : function(node) {

			var attrs = node.attributes;
			if (node.clearAttributes) {
				node.clearAttributes();
			} else {
				while(node.attributes.length > 0) {
					node.removeAttributeNode(node.attributes[0]);
				}
			}
		},
		
		_formatNode : function(node) {
			
			var sb = new StringBuilder();
			
			sb.append("<").append(node.nodeName);
			for (var i = 0; i < node.attributes.length; i++) {
				var attr = node.attributes[i];
				if (attr.specified) {
					sb
						.append(" ")
						.append(attr.nodeName)
						.append("=\"")
						.append(attr.nodeValue)
						.append("\" ");
				}
			}
			
			sb.append("/>");
			
			return sb.toString();
			
		},
		
		Event: {
			
			/**
			 * cache listeners in element to kill them all on element ajax replace
			 * @param {Object} element
			 * @param {Object} event
			 * @param {Object} handler
			 * @param {Object} useCapture
			 */
			observe : function (element, event, handler, useCapture) {
				if (true) {
					if (!element._listeners) {
						element._listeners = [];
					}
					
					element._listeners[element._listeners.length] = 
						{
							event: event,
							handler: handler,
							useCapture: useCapture
						};
				}
				Event.observe(element, event, handler, useCapture);
			},
			
			stopObserving : function(element, event, handler, useCapture) {
				
				if(element._listeners) {
					element._listeners = 
						element._listeners.reject(
							function(obj) {
								return obj.event == event 
									&& obj.handler == handler 
									&& obj.useCapture == useCapture;
							}
						);
				}
				
				Event.stopObserving(element, event, handler, useCapture);
				
			}, 
			
			removeListeners : function(element) {
				if (element._listeners) {
					var l = element._listeners.length;
					for(var i = 0; i < l; i++) {
						var listener = element._listeners[i];
						Event.stopObserving(
							element, 
							listener.event, 
							listener.handler, 
							listener.useCapture);
					}
					
					element._listeners = null;
				}
			}	
		}
	},
	
	AJAX : {
		updateRows: function(options,request,grid,clientid, callbacks, callbacksPost){
			var localOptions = options;
			var rowCount = grid.getBody().templNormal.getElement().rows.length;
			var startRow = localOptions.startRow;
			var count = localOptions.count;
			var rowindex, i, el;
			var dataModel = grid.dataModel;
			var baseid = clientid;
			var suffixs = [":n:"];
			if (($(baseid+":f")).rows.length) {
				suffixs = [":f:", ":n:"];
			}
			var countForUpdate = 0;
			var rowsForUpdate = [];
			
			for(i=0; i<count; i++) {
				rowindex = startRow + i;
				if(rowindex >= rowCount){
					 rowindex -= rowCount;
				}
				suffixs.unbreakableEach(
					function(suffix) {
						var id = [baseid,suffix,rowindex].join("");
						var row = Utils.DOM.replaceNode(id, request);
						
						if (callbacks) {
							// just suspend operation for future
							if(!rowsForUpdate[i]) rowsForUpdate[i] = {};
							rowsForUpdate[i][suffix] = {index : rowindex, row : row};
							countForUpdate++;
						}
					} 
				);
			}
			if (ClientUILib.isIE7) {
				setTimeout(function()  {
					var scrollElement = grid.getBody().scrollBox.getElement();
					var scrollTop = scrollElement.scrollTop;
					if (scrollTop) {
						scrollElement.scrollTop++;
					} else {
						scrollElement.scrollTop--;
					}
					scrollElement.scrollTop = scrollTop;
				}, 50);
			} 
			
			if (callbacks && countForUpdate>0) {
				// process suspended processing
				setTimeout(function(){
					for(var i=0; i<count; i++) {
						callbacks.unbreakableEach(
							function(callback) {
								if(rowsForUpdate[i]) {
									suffixs.unbreakableEach(
										function(suffix) {
											if(rowsForUpdate[i][suffix].row) {
												callback.call(grid, rowsForUpdate[i][suffix]);
											}
										} 
									);
								}
							}
						);
					}
					
					if(callbacksPost) {
						callbacksPost.unbreakableEach(
							function(callback) {
								callback.call(grid);
							}
						);
					}
				}, 100);
			}
	
			grid.getBody()._onDataReady(localOptions);
		}	
	}
};
/*
var _cAtt = Utils.DOM._clearAttributes;
Utils.DOM._clearAttributes = function() {
	return;
	var d1 = new Date().getTime();
	_cAtt.apply(Utils.DOM, arguments);
	var d2 = new Date().getTime();
	ClientUILib.log(ClientUILogger.INFO, "Utils.DOM._clearAttributes " + (d2 - d1) + "ms");
};
var cAtt = Utils.DOM._clearAttributes;
Utils.DOM.copyAttributes = function() {
	return;
	var d1 = new Date().getTime();
	cAtt.apply(Utils.DOM, arguments);
	var d2 = new Date().getTime();
	ClientUILib.log(ClientUILogger.INFO, "Utils.DOM.copyAttributes " + (d2 - d1) + "ms");
};
*/
Utils.execOnLoad = function(func, condition, timeout) {
	
	if (condition()) {
		func();		
	} else {
		window.setTimeout(
			function() {
				Utils.execOnLoad(func, condition, timeout);
			},
			timeout
		);
	}
};
Utils.Condition = {
	ElementPresent : function(element) {
		return function () {
			var el = $(element);
			return el && el.offsetHeight > 0;
		};
	}
};

Utils.trace = function(s) {
	LOG.info(s + ": " + (new Date().getTime()- this._d) + "    ");
	//window.status = s + ": " + (new Date().getTime()- this._d) + "    " + window.status;
	this._d = new Date().getTime();
};

Utils.getRule = function (className) {
	var rule = null;
	var sheets = document.styleSheets;
	var lcClassName = className.toLowerCase();
	
	for (var j = 0; !rule && j < sheets.length; j++) {
		var rules = sheets[j].cssRules ? sheets[j].cssRules: sheets[j].rules;
		for (var i = 0; !rule && i < rules.length; i++) {
			if (rules[i].selectorText && rules[i].selectorText.toLowerCase() == lcClassName) {
				rule = rules[i];
			}
		}
	}
	return rule;			
};

Array.prototype.unbreakableEach = function(f) {
	for (var i = 0; i < this.length; i++) {
		f(this[i], i);
	}
};