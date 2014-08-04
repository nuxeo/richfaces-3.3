// Global Variables
// var timeout = null;

// TODO - use sarissa for standard XMLHttpRequest Support.


// AJAX-JSF AJAX-like library, for communicate with view Tree on server side.

// Modified by Alexander J. Smirnov to use as JSF AJAX-like components. 

A4J.AJAX = {};

A4J.AJAX.Stub = function() {};

/**
 * XMLHttp transport class - incapsulate most of client-specifiv functions for call server requests.
 */
A4J.AJAX.XMLHttpRequest = function(query){
 	this._query = query;
 	
 	// Store document element, to check page replacement.
 	this._documentElement = window.document.documentElement;
 };

A4J.AJAX.XMLHttpRequest.prototype = {
	_query : null,
	_timeout : 0,
	_timeoutID : null,
	onready : null,
	_parsingStatus : Sarissa.PARSED_EMPTY,
	_errorMessage : "XML Response object not set",
	_contentType : null,
	_onerror : function(req,status,message) {
         // Status not 200 - error !
		// 	window.alert(message);
		if(status !=599 && req.getResponseText()){
			A4J.AJAX.replacePage(req);
		}
        },
	onfinish : null,
	options : {},
	domEvt : null,
	form : null,
	_request : null,
	_aborted : false,
	_documentElement : null,
	setRequestTimeout : function(timeout){
		this._timeout = timeout;
	},
	/**
	 * Send request to server with parameters from query ( POST or GET depend on client type )
	 */
	send : function(){
 	this._request = new XMLHttpRequest();
 	var _this = this;
 	this._request.onreadystatechange =  function(){
 				if(window.document.documentElement != _this._documentElement){
          			LOG.warn("Page for current request have been unloaded - abort processing" );

          			if(!_this._status_stopped) {
          				//end status early
 						A4J.AJAX.status(_this.containerId, _this.options.status, false);
          				_this._status_stopped = true;
          			}
          			
          			_this.abort();
          			
 					return;
 				};
 				
          		LOG.debug("Reqest state : "+_this._request.readyState );
		      	if (_this._request.readyState == 4  ) {
	 				if(_this._aborted){
 					    //this will be true just once for request - no need to track state of status
 						A4J.AJAX.status(_this.containerId, _this.options.status, false);
 						A4J.AJAX.popQueue(_this);
	 					
	 					return;
	 				};

	 				LOG.debug("Reqest end with state 4");
		      		if(_this._timeoutID){
		      			window.clearTimeout(_this._timeoutID);
		      		}
		      		var requestStatus;
		      		var requestStatusText;
		      		try{
		      			requestStatus = _this._request.status;
		      			requestStatusText = _this._request.statusText;
		      		} catch(e){
		      			LOG.error("request don't have status code - network problem, "+e.message);
		      			requestStatus = 599;
		      			requestStatusText = "Network error";
		      		}
		      		if(requestStatus == 200){
						try {
            				LOG.debug("Response  with content-type: "+ _this.getResponseHeader('Content-Type'));
			            	LOG.debug("Full response content: ", _this.getResponseText());
						} catch(e) {
				// IE Can throw exception for any responses
						}
		      			// Prepare XML, if exist.
		      			if(_this._request.responseXML ){
			      			_this._parsingStatus = Sarissa.getParseErrorText(_this._request.responseXML);
			      			if(_this._parsingStatus == Sarissa.PARSED_OK && Sarissa.setXpathNamespaces ){
			      				Sarissa.setXpathNamespaces(_this._request.responseXML,"xmlns='http://www.w3.org/1999/xhtml'");
			      			}
		      			}
		      			if(_this.onready){
		      				_this.onready(_this);
		      			}      			
		      			
		      		} else {
		      			_this._errorMessage = "Reqest error, status : "+requestStatus +" " + requestStatusText ;
		      			LOG.error(_this._errorMessage);
		      			if(typeof(_this._onerror) == "function"){
		      				_this._onerror(_this,requestStatus,_this._errorMessage);
		      			}
			      		if (_this.onfinish)
			      		{
			      			_this.onfinish(_this);
			      		}
		      		}

					_this = undefined;		      		
		      	}
	}; //this._onReady;
    try{
    LOG.debug("Start XmlHttpRequest");
    this._request.open('POST', this._query.getActionUrl("") , true);
    // Query use utf-8 encoding for prepare urlencode data, force request content-type and charset.
    var contentType = "application/x-www-form-urlencoded; charset=UTF-8";
	this._request.setRequestHeader( "Content-Type", contentType); 
    } catch(e){
    	// Opera 7-8 - force get
    	LOG.debug("XmlHttpRequest not support setRequestHeader - use GET instead of POST");
    	this._request.open('GET', this._query.getActionUrl("")+"?"+this._query.getQueryString() , true);
    }
    // send data.
    this._request.send(this._query.getQueryString());
    if(this._timeout > 0){
    	this._timeoutID = window.setTimeout(function(){
   			LOG.warn("request stopped due to timeout");
   			
   			if(!_this._aborted){
//			    A4J.AJAX.status(_this.containerId,_this.options.status,false);
		     if(typeof(A4J.AJAX.onAbort) == "function"){
   				A4J.AJAX.onAbort(_this);
		     }
   			}
			_this._aborted=true;
			
			//IE doesn't call onreadystatechange by abort() invocation as other browsers do, unify this
			_this._request.onreadystatechange = A4J.AJAX.Stub;
    		_this._request.abort();

    		if(_this._onerror){
      			_this._errorMessage = "Request timeout";
		    	_this._onerror(_this,500,_this._errorMessage);
		    }
      		if(_this.onfinish){
      			_this.onfinish(_this);
      		}
		    _this._request=undefined;
	      	_this = undefined;
    	},this._timeout);
    }
	},
	
	abort: function(){
		this._oncomplete_aborted = true;
   			
		if(!this._aborted){
			if(typeof(A4J.AJAX.onAbort) == "function"){
				A4J.AJAX.onAbort(this);
			}
		}
		this._aborted=true;
	},

	getResponseText : function(){
		try {
			return this._request.responseText;
		} catch(e){
			return null;
		}
	},
	getError : function(){
		return this._errorMessage;
	},
	getParserStatus : function(){
		return this._parsingStatus;
	},
	getContentType : function(){
		if(!this._contentType){
			var contentType = this.getResponseHeader('Content-Type');
			if(contentType){
				var i = contentType.indexOf(';');
				if( i >= 0 ){
					this._contentType = contentType.substring(0,i);
				} else {
					this._contentType = contentType;				
				}
			} else {
				this._contentType="text/html";
			}
		}
		return this._contentType;
	},
	getResponseHeader : function(name){
		var result;
		// Different behavior - for non-existing headers, Firefox throws exception,
		// IE return "" , 
		try{
			result = this._request.getResponseHeader(name);
			if(result === ""){
				result = undefined;
			}
		} catch(e) {
		}
		if(!result){
		// Header not exist or Opera <=8.0 error. Try to find <meta > tag with same name.
			LOG.debug("Header "+name+" not found, search in <meta>");
			if(this._parsingStatus == Sarissa.PARSED_OK){
				var metas = this.getElementsByTagName("meta");
				for(var i = 0; i < metas.length;i++){
					var meta = metas[i];
					LOG.debug("Find <meta name='"+meta.getAttribute('name')+"' content='"+meta.getAttribute('content')+"'>");
					if(meta.getAttribute("name") == name){
						result = meta.getAttribute("content");
						break;
					}
				}
			}
			
		}
		return result;
	},
	/**
	 * get elements with elementname in responseXML or, if present - in element.
	 */
	getElementsByTagName : function(elementname,element){
		if(!element){
			element = this._request.responseXML;
		}
		LOG.debug("search for elements by name '"+elementname+"' "+" in element "+element.nodeName);
   		var elements; 
	    try
	    {
	        elements = element.selectNodes(".//*[local-name()=\""+ 
	                                           elementname +"\"]");
	    }
	    catch (ex) {
	    	try {
				elements = element.getElementsByTagName(elementname);
	    	} catch(nf){
				LOG.debug("getElementsByTagName found no elements, "+nf.Message);	    		    		
	    	}
	    }
//	    return document.getElementsByTagName(tagName);
//		elements = element.getElementsByTagNameNS("http://www.w3.org/1999/xhtml",elementname);
//		LOG.debug("getElementsByTagNameNS found "+elements.length);
		return elements;
	},
	/**
	 * Find element in response by ID. Since in IE response not validated, use selectSingleNode instead.
	 */
	getElementById : function(id){
		// first attempt - .getElementById.
		var oDoc = this._request.responseXML;
		if(oDoc){
    	if(typeof(oDoc.getElementById) != 'undefined') {
			LOG.debug("call getElementById for id= "+id);
    		return  oDoc.getElementById(id);
    	} 
    	else if(typeof(oDoc.selectSingleNode) != "undefined") {
			LOG.debug("call selectSingleNode for id= "+id);
    		return oDoc.selectSingleNode("//*[@id='"+id+"']"); /* XPATH istead of ID */
    	}
    	// nodeFromID not worked since XML validation disabled by
    	// default for MS 
    	else if(typeof(oDoc.nodeFromID) != "undefined") {
			LOG.debug("call nodeFromID for id= "+id);
    		return oDoc.nodeFromID(id);
    	} 
		LOG.error("No functions for getElementById found ");
		} else {
			LOG.debug("No parsed XML document in response");
		}
    	return null;
		
	},
	
	getJSON : function(id){
		     	  	var data;
        	  		var dataElement = this.getElementById(id);
        	  		if(dataElement){
        	  			try {
        	  				data = Sarissa.getText(dataElement,true);
        	  				data = window.eval('('+data+')');
        	  			} catch(e){
        	  				LOG.error("Error on parsing JSON data "+e.message,data);
        	  			}
        	  		}
		return data;
	},

	_evaluateScript: function(node) {
		var includeComments = !A4J.AJAX.isXhtmlScriptMode();
		var newscript = A4J.AJAX.getText(node, includeComments) ; // TODO - Mozilla disable innerHTML in XML page ..."";

		try {
			LOG.debug("Evaluate script replaced area in document: ", newscript);
			if (window.execScript) {
				window.execScript( newscript );
			} else {
				window.eval(newscript);
			}
			LOG.debug("Script evaluation succeeded");
		} catch(e){
			LOG.error("ERROR Evaluate script:  Error name: " + e.name + e.message?". Error message: "+e.message:"");
		}
	},
	
	evaluateQueueScript: function() {
	    var queueScript = this.getElementById('org.ajax4jsf.queue_script');
	    if (queueScript) {
	    	this._evaluateScript(queueScript);
	    }
	},
	
	evalScripts : function(node, isLast){
			var newscripts = this.getElementsByTagName("script",node);
	        LOG.debug("Scripts in updated part count : " + newscripts.length);
			if( newscripts.length > 0 ){
		      var _this = this;
			  window.setTimeout(function() {
		        for (var i = 0; i < newscripts.length; i++){
		        	_this._evaluateScript(newscripts[i]);
			    }
			    newscripts = null;
			    if (isLast)
			    {
			    	_this.doFinish();
			    }
			    _this = undefined;
			  }, 0);
		    } else
		    {
			    if (isLast)
			    {
			    	this.doFinish();
			    }
		    }
	},
	
	/**
	 * Update DOM element with given ID by element with same ID in parsed responseXML
	 */
	updatePagePart : function(id, isLast){
		var newnode = this.getElementById(id);
		if( ! newnode ) 
		{ 
			LOG.error("New node for ID "+id+" is not present in response");
			if (isLast) 
			{
				this.doFinish();
			}
			return;
		}
		var oldnode = window.document.getElementById(id);
		if ( oldnode  ) {
			
	   	    // Remove unload prototype events for a removed elements.
			if (window.RichFaces && window.RichFaces.Memory) {
				window.RichFaces.Memory.clean(oldnode, true);
			}        	  
			
			var anchor = oldnode.parentNode;
			if(!window.opera && !A4J.AJAX.isWebkitBreakingAmps() && oldnode.outerHTML && !oldnode.tagName.match( /(tbody|thead|tfoot|tr|th|td)/i ) ){
   		        LOG.debug("Replace content of node by outerHTML()");
   		        if (!Sarissa._SARISSA_IS_IE || oldnode.tagName.toLowerCase()!="table") {
	   		        try {
		   		        oldnode.innerHTML = "";
	   		        } catch(e){    
	   		        	LOG.error("Error to clear node content by innerHTML "+e.message);
						Sarissa.clearChildNodes(oldnode);
	   		        }
   		        }
   		        oldnode.outerHTML = new XMLSerializer().serializeToString(newnode);
			} else {
    // need to check for firstChild due to opera 8 bug with hasChildNodes
				Sarissa.clearChildNodes(oldnode);
    
    			var importednode = window.document.importNode(newnode, true);
	    		//importednode.innerHTML = importednode.innerHTML; 
   		        LOG.debug("Replace content of node by replaceChild()");

				var oldGetElementById = null;
				
				A4J.AJAX.TestReplacedGetElementByIdVisibility();
				if (!A4J.AJAX._testReplacedGetElementByIdVisibility) {
					LOG.debug("Temporarily substituting document.getElementById() to work around WebKit issue");
					oldGetElementById = document.getElementById;
					document.getElementById = function(id) {
						var elt = oldGetElementById.apply(document, arguments);
						if (!elt) {
							var id = arguments[0];

							LOG.debug("Element [@id='" + id + "'] was not found in document, trying to locate XPath match");
							
							try {
								var result = importednode.ownerDocument.evaluate("//*[@id='" + id + "']", 
										importednode, null, XPathResult.ANY_UNORDERED_NODE_TYPE);
								
								if (result) {
									elt = result.singleNodeValue;
								}
								
								LOG.debug("XPath located: " + elt);
							} catch (e) {
								LOG.error("Error locating [@id='" + id + "'] element: " + e.message);
							}
						}
						
						return elt;
					};
				}
				
				try {
					anchor.replaceChild(importednode,oldnode);
				} finally {
					if (oldGetElementById) {
						LOG.debug("Restoring document.getElementById()");
						document.getElementById = oldGetElementById;
					}
				}
			} 
			
	// re-execute all script fragments in imported subtree...
	// TODO - opera 8 run scripts at replace content stage.
			if(!A4J.AJAX._scriptEvaluated){
				this.evalScripts(newnode, isLast);
			}
	        LOG.debug("Update part of page for Id: "+id + " successful");
		} else {
			LOG.warn("Node for replace by response with id "+id+" not found in document");
			if (!A4J.AJAX._scriptEvaluated && isLast) 
			{
				this.doFinish();
			}
		}
		
		if (A4J.AJAX._scriptEvaluated && isLast)
	    {
			this.doFinish();
	    }
		
	},
	
	doFinish: function() {
		if(this.onfinish){
			this.onfinish(this);
		}
	},
	
	appendNewHeadElements : function(callback){
        // Append scripts and styles to head, if not presented in page before.
        var includes = this._appendNewElements("script","src",null,null,["type","language","charset"]);
        
        var _this = this;
        includes.concat( this._appendNewElements("link","href","class",["component","user"],["type","rev","media"],{"class": "className"},
        		function (element, script) {
        			//IE requires to re-set rel or href after insertion to initialize correctly
        			//see http://jira.jboss.com/jira/browse/RF-1627#action_12394642
        			_this._copyAttribute(element,script,"rel");
        		}
        ));
        
        if ( includes.length == 0) {
        	callback();
        	return;
        }
        
		A4J.AJAX.headElementsCounter = includes.length;
        
		var onReadyStateChange = function () {
			if (this.readyState == 'loaded' || this.readyState == 'complete') {
				this.onreadystatechange = null;
				this.onload = null;
				callback();
			}
		};
		var onLoad = function () {
			this.onreadystatechange = null;
			this.onload = null;
			callback();
		};
        for (var i = 0; i<includes.length; i++)
        {
        	includes[i].onreadystatechange = onReadyStateChange;
        	includes[i].onload = onLoad;
        }
	}, 
	
	_appendNewElements : function(tag,href,role,roles,attributes,mappings,callback){
			  var head = document.getElementsByTagName("head")[0]||document.documentElement;
		      var newscripts = this.getElementsByTagName(tag);
        	  var oldscripts = document.getElementsByTagName(tag);
        	  var mappedRole = (mappings && mappings[role]) || role;
        	  var elements = [];
        	  
        	  var roleAnchors = {};
			  if (roles) {
	        	  var i = 0;
	        	  
	        	  for(var j = 0; j < oldscripts.length; j++){
					  var oldscript = oldscripts[j];
					  var scriptRole = oldscript[mappedRole];
	        	  
					  for ( ; i < roles.length && roles[i] != scriptRole; i++) {
						  roleAnchors[roles[i]] = oldscript;
					  }
					  
					  if (i == roles.length) {
						  break;
					  }
	        	  }
			  }
        	  
        	  for(var i=0 ; i<newscripts.length;i++){
        	  	 var element = newscripts[i];
        	  	 var src = element.getAttribute(href);
        	  	 var elementRole;
        	  	 
        	  	 if (roles) {
        	  		 elementRole = element.getAttribute(role);
        	  	 }
        	  	 
        	  	 if(src){
        	  	 	var exist = false;
        	  	 	LOG.debug("<"+tag+"> in response with src="+src);
        	  				for(var j = 0 ; j < oldscripts.length; j++){
        	  					if(this._noSessionHref(src) == this._noSessionHref(oldscripts[j].getAttribute(href))){
        	  						LOG.debug("Such element exist in document");

        	  						if (role) {
        	  							var oldRole = oldscripts[j][mappedRole];
        	  							if ((!elementRole ^ !oldRole) || (elementRole && oldRole && elementRole != oldRole)) {
                	  						LOG.warn("Roles are different");
        	  							}
        	  						}
        	  						
        	  						exist = true;
        	  						break;
        	  					}
        	  				}
        	  		 if(!exist){
        	  		 	// var script = window.document.importNode(element,true); //
        	  		 	var script = document.createElement(tag);
        	  		 	script.setAttribute(href,src);
        	  		 	for(var j = 0 ; j < attributes.length; j++){
        	  		 		this._copyAttribute(element,script,attributes[j]);
        	  		 	}
        	  		 	
        	  		 	if (elementRole) {
        	  		 		script[mappedRole] = elementRole;
        	  		 	}

        	  		 	LOG.debug("append element to document");
        	  		 	
        	  		 	for ( var j = 0; j < A4J.AJAX._headTransformers.length; j++) {
        	  		 		A4J.AJAX._headTransformers[j](script);
						}
        	  		 	
        	  		 	var anchor = roleAnchors[elementRole];
        	  		 	if (anchor && anchor.parentNode) {
            	  		 	anchor.parentNode.insertBefore(script, anchor);
        	  		 	} else {
            	  		 	head.appendChild(script);
        	  		 	}
        	  		 	
        	  		 	if (callback) {
        	  		 		callback(element,script);
        	  		 	}
        	  		 	if (tag!="link" || script.type.toLowerCase()=="text/javascript") elements.push(script);
        	  		 }     	  	 	
        	  	 }
        	  }
		return elements;
	},
	
	_noSessionHref : function(href){
		var cref = href;
		if(href){
		var sessionid = href.lastIndexOf(";jsessionid=");
		if(sessionid>0){
			cref = href.substring(0,sessionid);
			var params = href.lastIndexOf("?");
			if(params>sessionid){
				cref=cref+href.substring(params);
			}
		}
		}
		return cref; 		
	},
	
	_copyAttribute : function(src,dst,attr){
		var value = src.getAttribute(attr);
		if(value){
			dst.setAttribute(attr,value);
		}
	}

};
  
//Listeners should be notified
A4J.AJAX.Listener = function(onafterajax){
	this.onafterajax = onafterajax;
};

A4J.AJAX.AjaxListener = function(type, callback){
	this[type] = callback;
};

A4J.AJAX._listeners= [];
A4J.AJAX.AddListener = function(listener){
	A4J.AJAX._listeners.push(listener);	
};
A4J.AJAX.removeListeners = function(listener){
	A4J.AJAX._listeners = [];	
};
A4J.AJAX.removeListener = function(listener){
	for (var i=A4J.AJAX._listeners.length-1;i>=0;i--){
		if (A4J.AJAX._listeners[i] == listener){
			A4J.AJAX._listeners.splice(i,1);
		}
	}
};


//head element transformers
A4J.AJAX.HeadElementTransformer = function(elt){
	this.elt = elt;
};

A4J.AJAX._headTransformers = [];
A4J.AJAX.AddHeadElementTransformer = function(listener){
	A4J.AJAX._headTransformers.push(listener);	
};

A4J.AJAX.SetZeroRequestDelay = function(options) {
	if (typeof options.requestDelay == "undefined") {
		options.requestDelay = 0;
	}
};

// pollers timerId's
A4J.AJAX._pollers = {};
/*
 * 
 * 
 */
A4J.AJAX.Poll =  function( containerId, form, options ) {
	A4J.AJAX.StopPoll(options.pollId);
	if(!options.onerror){
	  options.onerror = function(req,status,message){
		if(typeof(A4J.AJAX.onError)== "function"){
			A4J.AJAX.onError(req,status,message);
    	}		
		// For error, re-submit request.
    	A4J.AJAX.Poll(containerId,form,options);
	  };
	}
	
	if (!options.onqueuerequestdrop) {
		options.onqueuerequestdrop = function() {
	    	A4J.AJAX.Poll(containerId,form,options);
		};
	}
	
	A4J.AJAX.SetZeroRequestDelay(options);
	
	A4J.AJAX._pollers[options.pollId] = window.setTimeout(function(){
		A4J.AJAX._pollers[options.pollId]=undefined;
		if((typeof(options.onsubmit) == 'function') && (options.onsubmit()==false)){
			// Onsubmit disable current poll, start next interval.
			A4J.AJAX.Poll(containerId,form,options);			
		} else {
			A4J.AJAX.Submit(containerId,form,null,options);
		}
	},options.pollinterval);
};

A4J.AJAX.StopPoll =  function( Id ) {
	if(A4J.AJAX._pollers[Id]){
		window.clearTimeout(A4J.AJAX._pollers[Id]);
		A4J.AJAX._pollers[Id] = undefined;
	}
};

/*
 * 
 * 
 */
A4J.AJAX.Push =  function( containerId, form, options ) {
	A4J.AJAX.StopPush(options.pushId);
	options.onerror = function(){
		// For error, re-submit request.
		A4J.AJAX.Push(containerId,form,options);
	};
	
	options.onqueuerequestdrop = function() {
		LOG.debug("Push main request dropped from queue");
	};
	
	A4J.AJAX._pollers[options.pushId] = window.setTimeout(function(){
		var request = new XMLHttpRequest();
		request.onreadystatechange =  function(){
		      	if (request.readyState == 4  ) {
		      		try {
		      		  if(request.status == 200){
		      			if(request.getResponseHeader("Ajax-Push-Status")=="READY"){
		      				A4J.AJAX.SetZeroRequestDelay(options);
		      				A4J.AJAX.Submit(containerId,form||options.dummyForm,null,options);
		      			}
		      		  }
		      		} catch(e){
		      			// Network error.
		      		}
		      		// Clear variables.
		      		request=null;
					A4J.AJAX._pollers[options.pushId] = null;
		      		// Re-send request.
		      		A4J.AJAX.Push( containerId, form, options );
		      	}
		}
		A4J.AJAX.SendPush( request,options );
	},options.pushinterval);
};

A4J.AJAX.SendPush =  function( request,options ) {
	    var url = options.pushUrl || options.actionUrl;
		request.open('HEAD', url , true);
		request.setRequestHeader( "Ajax-Push-Key", options.pushId);
		if(options.timeout){
			request.setRequestHeader( "Timeout", options.timeout);			
		}
		request.send(null);	
}

A4J.AJAX.StopPush =  function( Id ) {
	if(A4J.AJAX._pollers[Id]){
		window.clearTimeout(A4J.AJAX._pollers[Id]);
		A4J.AJAX._pollers[Id] = null;
	}
};



A4J.AJAX.CloneObject =  function( obj, noFunctions ) {
	var cloned = {};
	for( var n in obj ){
		if(noFunctions && typeof(evt[prop]) == 'function'){
			continue;
		}
		cloned[n]=obj[n];
	}
	return cloned;
}


A4J.AJAX.SubmitForm =  function( containerId, form, options ) {
	var opt = A4J.AJAX.CloneObject(options);
	// Setup active control if form submitted by button.
	if(A4J._formInput){
		LOG.debug("Form submitted by button "+A4J._formInput.id);
		opt.control = A4J._formInput;
		A4J._formInput = null;
		opt.submitByForm=true;
	}
	A4J.AJAX.Submit(containerId,form,null,opt);
}
  
/**
 * This method should be deprecated and maybe even removed?
 */
A4J.AJAX.SubmiteventsQueue =  function( eventsQueue ) {
	eventsQueue.submit();
};

A4J.AJAX.CloneEvent = function(evt) {
	var domEvt;
	evt = evt || window.event || null;
	if(evt){
		// Create copy of event object, since most of properties undefined outside of event capture.
		try {
			domEvt = A4J.AJAX.CloneObject(evt,false);
		} catch(e){
			LOG.warn("Exception on clone event "+e.name +":"+e.message);
		}
		LOG.debug("Have Event "+domEvt+" with properties: target: "+domEvt.target+", srcElement: "+domEvt.srcElement+", type: "+domEvt.type);
	}
	
	return domEvt;
};

A4J.AJAX.PrepareQuery = function(containerId, formId, domEvt, options) {
	// Process listeners.
	for(var li = 0; li < A4J.AJAX._listeners.length; li++){
  	  	var listener = A4J.AJAX._listeners[li];
   	  	if(listener.onbeforeajax){
   	  		listener.onbeforeajax(formId,domEvt,options);
   	  	}
	}
    // First - run onsubmit event for client-side validation.
	LOG.debug("Query preparation for form '" + formId + "' requested");
//	var	form = A4J.AJAX.locateForm(event);
	var form = window.document.getElementById(formId);
	if( (!form || form.nodeName.toUpperCase() != "FORM") && domEvt ) {
		var srcElement = domEvt.target||domEvt.srcElement||null;
		if(srcElement){
			form = A4J.AJAX.locateForm(srcElement);
		};
	};
	// TODO - test for null of form object
    if(!options.submitByForm && form && form.onsubmit) {
		LOG.debug("Form have onsubmit function, call it" );
    	if( form.onsubmit() == false ){
    		return false;
    	};
    };
    var tosend = new A4J.Query(containerId, form);
    tosend.appendFormControls(options.single, options.control);
    //appending options.control moved to appendFormControls
    //if(options.control){
	//	tosend.appendControl(options.control,true);
	//};
    if(options.parameters){
    	tosend.appendParameters(options.parameters);
    }; 
    if(options.actionUrl){
    	tosend.setActionUrl(options.actionUrl);
    };

    return tosend;
};

A4J.AJAX.SubmitQuery = function (query, options, domEvt) {
	// build  xxxHttpRequest. by Sarissa / JSHttpRequest class always defined.
    var req = new A4J.AJAX.XMLHttpRequest(query);
    
    var form = query._form;
    var containerId = query._containerId;
    
    req.options = options;
    req.containerId = containerId;
    req.domEvt = domEvt;
    req.form = form;
    
    if(options.timeout){
    	req.setRequestTimeout(options.timeout);
    };
    
    // Event handler for process response result.
    req.onready = A4J.AJAX.processResponse;
    
    if(options.onerror){
    	req._onerror = options.onerror;
    } else if(typeof(A4J.AJAX.onError)== "function"){
		req._onerror = A4J.AJAX.onError;
    }
    
	var _queueonerror = options.queueonerror;
    if (_queueonerror) {
    	var _onerror = req._onerror;
    	if (_onerror) {
        	req._onerror = function() {
        		_queueonerror.apply(this, arguments);
        		_onerror.apply(this, arguments);
        	};
    	} else {
    		req._onerror = _queueonerror;
    	}
    }
    
	req.onfinish = A4J.AJAX.finishRequest;   

	LOG.debug("NEW AJAX REQUEST !!! with form: " + (form.id || form.name || form));

	A4J.AJAX.status(containerId,options.status,true);
    req.send();
	
    return req;
};

//Submit or put in queue request. It not full queues - framework perform waiting only one request to same queue, new events simple replace last.
//If request for same queue already performed, replace with current parameters.
A4J.AJAX.Submit =  function( containerId, formId, event , options ) {
	A4J.AJAX.IncrementTestRequestsCount();
	var domEvt = A4J.AJAX.CloneEvent(event);
	var query = A4J.AJAX.PrepareQuery(containerId, formId, domEvt, options);
    if (query) {
    	var queue = A4J.AJAX.EventQueue.getOrCreateQueue(options, formId);
    	
    	if (queue) {
			queue.push(query, options, domEvt);
    	} else {
        	A4J.AJAX.SubmitQuery(query, options, domEvt);
    	}
    }

    return false;
};


  // Main request submitting functions.
  // parameters :
  // form - HtmlForm object for submit.
  // control - form element, called request, or, clientID for JSF view.
  // affected - Array of ID's for DOM Objects, updated after request. Override
  // list of updated areas in response.
  // statusID - DOM id request status tags.
  // oncomplete - function for call after complete request.
A4J.AJAX.SubmitRequest = function( containerId, formId, event, options ) {
	var domEvt = A4J.AJAX.CloneEvent(event);
	var query = A4J.AJAX.PrepareQuery(containerId, formId, domEvt, options);
    if (query) {
    	A4J.AJAX.SubmitQuery(query, options, domEvt);
    }

    return false;
};

A4J.AJAX.processResponseAfterUpdateHeadElements = function (req, ids)
{
	req.evaluateQueueScript();
	
	for ( var k =0; k < ids.length ; k++ ) {
		var id = ids[k];
		LOG.debug("Update page part from call parameter for ID " + id);
		req.updatePagePart(id, k==ids.length-1);
	};
}
        
A4J.AJAX.headElementsCounter = 0;

A4J.AJAX.processResponse = function(req) {
			A4J.AJAX.TestScriptEvaluation();
    	    var options = req.options;
			var ajaxResponse = req.getResponseHeader('Ajax-Response');
			// If view is expired, check user-defined handler.
			var expiredMsg = req.getResponseHeader('Ajax-Expired');
			if(expiredMsg && typeof(A4J.AJAX.onExpired) == 'function' ){
				var loc = A4J.AJAX.onExpired(window.location,expiredMsg);
				if(loc){
	         			window.location = loc;
	         			return;					
				}
			}
			if( ajaxResponse != "true"){
          	  	// NO Ajax header - new page.
          	  	LOG.warn("No ajax response header ");
         		var loc = req.getResponseHeader("Location");
	         	try{
	         		if(ajaxResponse == 'redirect' && loc){
	         			window.location = loc;
	         		} else if(ajaxResponse == "reload"){
       					window.location.reload(true);
	         		} else {
	         			A4J.AJAX.replacePage(req);
	         		}
	         	} catch(e){
	         		LOG.error("Error redirect to new location ");
	         	}
          	} else {
			  if(req.getParserStatus() == Sarissa.PARSED_OK){

				// perform beforeupdate if exists			  	
				if(options.onbeforedomupdate || options.queueonbeforedomupdate){
   					var event = req.domEvt;
   					var data = req.getJSON('_ajax:data');
					
					LOG.debug( "Call local onbeforedomupdate function before replacing elemements" );

					if (options.onbeforedomupdate) {
						options.onbeforedomupdate(req, event, data);
	     			}

	     			if (options.queueonbeforedomupdate) {
						options.queueonbeforedomupdate(req, event, data);
	     			}
				}
				
				var idsFromResponse = req.getResponseHeader("Ajax-Update-Ids");
				var ids;

        	    var callback = function () {
        	    	if (A4J.AJAX.headElementsCounter!=0) {
        	    		LOG.debug("Script "+A4J.AJAX.headElementsCounter+" was loaded");
        	    		--A4J.AJAX.headElementsCounter;
        	    	}
        	    	if (A4J.AJAX.headElementsCounter==0) {
        	  			A4J.AJAX.processResponseAfterUpdateHeadElements(req, ids);
        	  		}
       	    	};

        // 3 strategy for replace :
        // if setted affected parameters - replace its
        	  	if( options.affected ) {
        	  		ids = options.affected;
	        	  	req.appendNewHeadElements(callback);
		// if resopnce contains element with ID "ajax:update" get id's from
		// child text element . like :
		// <div id="ajax:update" style="display none" >
		//   <span>_id1:1234</span>
		//    .........
		// </div>
		//
        	  } else if( idsFromResponse && idsFromResponse != "" ) {
				LOG.debug("Update page by list of rendered areas from response " + idsFromResponse );
        	  // Append scripts and styles to head, if not presented in page before.
        	  	ids = idsFromResponse.split(",");
        	  	req.appendNewHeadElements(callback);
        	  } else {
        			// if none of above - error ?
					// A4J.AJAX.replace(form.id,A4J.AJAX.findElement(form.id,xmlDoc));
					LOG.warn("No information in response about elements to replace");
					req.doFinish();
        	  }
        	  // Replace client-side hidden inputs for JSF View state.
        	  var idsSpan = req.getElementById("ajax-view-state");
	          LOG.debug("Hidden JSF state fields: ");//+idsSpan);
        	  if(idsSpan != null){
        	  	// For a portal case, replace content in the current window only.
			        var namespace = options.parameters['org.ajax4jsf.portlet.NAMESPACE'];
			        LOG.debug("Namespace for hidden view-state input fields is "+namespace);
			        var anchor = namespace?window.document.getElementById(namespace):window.document;        	  	    
        	  		var inputs = anchor.getElementsByTagName("input");
        	  		try {
        	  		   var newinputs = req.getElementsByTagName("input",idsSpan);
        	  		   A4J.AJAX.replaceViewState(inputs,newinputs);
        	  		} catch(e){
        	  			LOG.warn("No elements 'input' in response");
        	  		}
        	  		// For any cases, new state can be in uppercase element
        	  		try {
        	  		   var newinputs = req.getElementsByTagName("INPUT",idsSpan);
        	  		   A4J.AJAX.replaceViewState(inputs,newinputs);
        	  		} catch(e){
        	  			LOG.warn("No elements 'INPUT' in response");
        	  		}
        	  }
        	  
        	  // Process listeners.
        	  for(var li = 0; li < A4J.AJAX._listeners.length; li++){
        	  	var listener = A4J.AJAX._listeners[li];
        	  	if(listener.onafterajax){
        	  		// Evaluate data as JSON String.
        	  		var data = req.getJSON('_ajax:data');
        	  		listener.onafterajax(req,req.domEvt,data);
        	  	}
        	  }
        	  // Set focus, if nessesary.
        	  var focusId = req.getJSON("_A4J.AJAX.focus");
        	  if(focusId){
        	  	LOG.debug("focus must be set to control "+focusId);
        	  	var focusElement=false;
        	  	if(req.form){
        	  		// Attempt to get form control for name. By Richfaces naming convensions, 
        	  		// complex component must set clientId as DOM id for a root element ,
        	  		// and as input element name.
        	  		focusElement = req.form.elements[focusId];
        	  	}
        	  	if(!focusElement){
        	  		// If not found as control element, search in DOM.
        	  		LOG.debug("No control element "+focusId+" in submitted form");
        	  		focusElement = document.getElementById(focusId);
        	  	}
        	  	if(focusElement){
        	  		LOG.debug("Set focus to control ");
        	  		focusElement.focus();
        	  		if (focusElement.select) focusElement.select();
        	  	} else {
        	  		LOG.warn("Element for set focus not found");
        	  	}
        	  } else {
        	  	LOG.debug("No focus information in response");        	  	
        	  }
           } else {
           // No response XML
   			LOG.error( "Error parsing XML" );
			LOG.error("Parse Error: " + req.getParserStatus());
           }
          }
         }; 
         
         
A4J.AJAX.replacePage = function(req){
						if(!req.getResponseText()){
							LOG.warn("No content in response for replace current page");
							return;							
						}
						LOG.debug("replace all page content with response");
	         			var isIE = Sarissa._SARISSA_IS_IE;
						// maksimkaszynski
						//Prevent "Permission denied in IE7"
						//Reset calling principal
						var oldDocOpen = window.document.open;
						if (isIE) {
							LOG.debug("setup custom document.open method");							
							window.document.open = function(sUrl, sName, sFeatures, bReplace) {
								oldDocOpen(sUrl, sName, sFeatures, bReplace);
							}
						}
						// /maksimkaszynski
						window.setTimeout(function() {
							var isDocOpen=false;
							try {  	
								var contentType = req.getContentType();
		          				var responseText = 	(isIE && !Sarissa._SARISSA_IS_IE9)  ? 
		          					req.getResponseText().replace(/(<script(?!\s+src=))/igm, "$1 defer ") : 
		          					req.getResponseText();

		          				window.document.open(contentType, "replace");
		          				if (window.LOG) {
			          				LOG.debug("window.document has opened for writing");
		          				}
		          				isDocOpen=true;
		          				
		          				window.document.write(responseText);
		          				
		          				if (window.LOG) {
			          				LOG.debug("window.document has been writed");
		          				}
		          				window.document.close();
		          				if (window.LOG) {
			          				LOG.debug("window.document has been closed for writing");
		          				}
	          				if(isIE){
	          			// For Ie , scripts on page not activated.
	          					window.location.reload(false);
	          				}
							} catch(e) {
		          				if (window.LOG) {
			          				LOG.debug("exception during write page content "+e.Message);
		          				}
								if(isDocOpen){
		          					window.document.close();
								}
								// Firefox/Mozilla in XHTML case don't support document.write()
								// Use dom manipulation instead.
								var	oDomDoc = (new DOMParser()).parseFromString(req.getResponseText(), "text/xml");
								if(Sarissa.getParseErrorText(oDomDoc) == Sarissa.PARSED_OK){  
		          				  if (window.LOG) {
								  	LOG.debug("response has parsed as DOM documnet.");
							  	  }
						    	  Sarissa.clearChildNodes(window.document.documentElement);
								  var docNodes = oDomDoc.documentElement.childNodes;
								  for(var i = 0;i<docNodes.length;i++){
									if(docNodes[i].nodeType == 1){
				          				if (window.LOG) {
					          				LOG.debug("append new node in document");
				          				}
							    		var node = window.document.importNode(docNodes[i], true);
						    			window.document.documentElement.appendChild(node);
									}
								 }
								  //TODO - unloading cached observers?
								  //if (window.RichFaces && window.RichFaces.Memory) {
								  //	  window.RichFaces.Memory.clean(oldnode);
								  //}        	  
								} else {
			          				if (window.LOG) {
										LOG.error("Error parsing response",Sarissa.getParseErrorText(oDomDoc));
									}
								}
								// TODO - scripts reloading ?
							} finally {
								window.document.open = oldDocOpen;								
							}
	          				if (window.LOG) {
		          				LOG.debug("page content has been replaced");
	          				}
	          			},0);	
}


A4J.AJAX.replaceViewState = function(inputs,newinputs){
	      	  		LOG.debug("Replace value for inputs: "+inputs.length + " by new values: "+ newinputs.length);
        	  		if( (newinputs.length > 0) && (inputs.length > 0) ){
        	  			for(var i = 0 ; i < newinputs.length; i++){
        	  				var newinput = newinputs[i];
        	  				LOG.debug("Input in response: "+newinput.getAttribute("name"));
        	  				for(var j = 0 ; j < inputs.length; j++){
        	  					var input = inputs[j];
        	  					if(input.name == newinput.getAttribute("name")){
	        	  				LOG.debug("Found same input on page with type: "+input.type);
        	  						input.value = newinput.getAttribute("value");
        	  					}
        	  				}
        	  			}
        	  		}
	
};
/**
 * 
 */
A4J.AJAX.finishRequest = function(request){
	var options = request.options;

	if (!request._oncomplete_aborted) {
		// we can set listener for complete request - for example,
		// it can shedule next request for update page.
		var oncomp;

		//FIXME: IE doesn't allow to read data from request if it's been aborted
		//so it's possible that different browsers will follow different branches 
		//of execution for the identical request conditions
		try {
			oncomp = request.getElementById('org.ajax4jsf.oncomplete');
		} catch (e) {
			LOG.warn("Error reading oncomplete from request " + e.message);
		}

		if(oncomp) {
			LOG.debug( "Call request oncomplete function after processing updates" );
			window.setTimeout(function(){
				var event = request.domEvt;

				var data;
				try {
					data = request.getJSON('_ajax:data');
				} catch (e) {
					LOG.warn("Error reading data from request " + e.message);
				}

				try {
					var target = null;
					if (event) {
						target = event.target ? event.target : event.srcElement;
					}

					var newscript = Sarissa.getText(oncomp,true);
					var oncomplete = new Function("request","event","data",newscript);
					oncomplete.call(target,request,event,data);					

					if (options.queueoncomplete) {
						options.queueoncomplete.call(target, request, event, data);
					}

				} catch (e) {
					LOG.error('Error evaluate oncomplete function '+e.Message);
				}

				// mark status object ( if any ) for complete request ;
				A4J.AJAX.status(request.containerId,options.status,false);},
				0);	     	
		} else if (options.oncomplete || options.queueoncomplete){
			LOG.debug( "Call local oncomplete function after processing updates" );
			window.setTimeout(function(){
				var event = request.domEvt;

				var data;
				try {
					data = request.getJSON('_ajax:data');
				} catch (e) {
					LOG.warn("Error reading data from request " + e.message);
				}

				if (options.oncomplete) {
					options.oncomplete(request, event, data);
				}

				if (options.queueoncomplete) {
					options.queueoncomplete(request, event, data);
				}

				// mark status object ( if any ) for complete request ;
				A4J.AJAX.status(request.containerId,options.status,false);},
				0);

		} else {
			LOG.debug( "Processing updates finished, no oncomplete function to call" );

			setTimeout(function() {
				// mark status object ( if any ) for complete request ;
				A4J.AJAX.status(request.containerId,options.status,false);
			}, 0)
		}
	} else {
		LOG.debug("Aborted request, won't call oncomplete at all" );

		setTimeout(function() {
			// mark status object ( if any ) for complete request ;
			A4J.AJAX.status(request.containerId,options.status,false);
		}, 0)
	}

	A4J.AJAX.popQueue(request);
	A4J.AJAX.DecrementTestRequestsCount();
};

A4J.AJAX.popQueue = function(request) {
	if (request.shouldNotifyQueue && request.queue) {
		request.queue.pop();
	}
};    
    
A4J.AJAX.getCursorPos =	function(inp){

		   if(inp.selectionEnd != null)
		     return inp.selectionEnd;
		
		   // IE specific code
		   var range = document.selection.createRange();
		   var isCollapsed = range.compareEndPoints("StartToEnd", range) == 0;
		   if (!isCollapsed)
		     range.collapse(false);
		   var b = range.getBookmark();
		   return b.charCodeAt(2) - 2;
		}
          
	// Locate enclosing form for object.
A4J.AJAX.locateForm = function(obj){
		
		var parent = obj;
		 while(parent && parent.nodeName.toLowerCase() != 'form'){
			parent = parent.parentNode;
		};
		return parent;
	
	};
	
A4J.AJAX.getElementById = function(id,options){
	var namespace = options['org.ajax4jsf.portlet.NAMESPACE'];
	var anchor = namespace?window.document.getElementById(namespace):window.document;
	var element;
	if(anchor){
		element = anchor.getElementById(id);
	} else {
		LOG.error("No root element for portlet namespace "+namespace+" on page");
	}
	return element;
}
    
    // hash for requests count for all ID's
A4J.AJAX._requestsCounts = {};
    // Change status object on start/stop request.
    // on start, document object with targetID+".start" make visible,
    // document object with targetID+".stop" make invisible.
    // on stop - inverse.
A4J.AJAX.status = function(regionID,targetID,start){
	try {
    	targetID = targetID || regionID +":status";
	    A4J.AJAX._requestsCounts[targetID]=(A4J.AJAX._requestsCounts[targetID]||0)+(start?1:-1);
	    
	    var startElem = document.getElementById(targetID + ".start");
	    var stopElem = document.getElementById(targetID + ".stop");
	    
	    if(A4J.AJAX._requestsCounts[targetID] > 0){
	    	if (stopElem) { 
	    		stopElem.style.display = "none";
	    	}

	    	if (startElem) {
	    		startElem.style.display = "";
	    	}
	    } else {
	    	if (startElem) {
	    		startElem.style.display = "none";
	    	}

	    	if (stopElem) { 
	    		stopElem.style.display = "";
	    	}
	    }
	    
	    if (start) {
	    	if (startElem && (typeof(startElem.onstart) == 'function')) {
	    		startElem.onstart();
	    	}  
	    } else {
	    	if (stopElem && (typeof(stopElem.onstop) == 'function')) {
	    		stopElem.onstop();
	    	}
	    }
    } catch(e){
    	LOG.error("Exception on status change: ");
    }
};
    
	

  
// Class for build query string.
A4J.Query = function(containerId, form){ 
	// For detect AJAX Request.
	 this._query = {AJAXREQUEST : containerId};
	 this._oldSubmit = null ;	
	 this._form = form;
	 this._containerId = containerId;
	 this._actionUrl = ( this._form.action)?this._form.action:this._form;
	};

A4J.Query.prototype = {
	 _form : null,
	 _actionUrl : null,
	 _ext	: "",
	 _query : {},
	 _oldSubmit : null,
 // init at loading time - script can change location at run time ? ...
	 _pageBase : window.location.protocol+"//"+window.location.host,
 // hash for control elements query string functions
 	 
 	 hidden : function(control){
 	 		this._value_query(control);
 	 		// TODO - configurable mask for hidden command scripts.
 	 		if( (control.name.length > 4) && (control.name.lastIndexOf("_idcl") ==  (control.name.length-5)) ){
 	 			control.value="";
 	 		// MYfaces version ...	
 	 		} else if( (control.name.length > 12) && (control.name.lastIndexOf("_link_hidden_") ==  (control.name.length-13)) ){
 	 			control.value="";
 	 		} 
 	 },
 	 
 	 text : function(control){
 	 		this._value_query(control);
 	 },

 	 textarea : function(control){
 	 		this._value_query(control);
 	 },

 	 'select-one' : function(control){
 	 	// If none options selected, don't include parameter.
 	 	if (control.selectedIndex != -1) {
    		this._value_query(control);
		} 
//	 	 	for( var i =0; i< control.childNodes.length; i++ ){
//	 	 		var child=control.childNodes[i];
//	 	 		if( child.selected ){
//		 	 		this._value_query(control); 		
//		 	 		break;
//	 	 		}
//	 	 	}
 	 },

 	 password : function(control){
 	 		this._value_query(control);
 	 },

 	 file : function(control){
 	 		this._value_query(control);
 	 },

 	 radio : function(control){
 	 		this._radio_query(control);
 	 },

 	 checkbox : function(control){
 	 		this._check_query(control);
 	 },

 	 
 	 'select-multiple' : function(control){
 		var cname = control.name;
 	   	var options = control.options;
		for( var i=0 ;i< control.length;i++ ){
			var option = options[i];
			this._addOption(cname, option);
		}
 	},
 	
 	_addOption : function(cname,option){
		if ( option.selected ){
			if( ! this._query[cname] ){
				this._query[cname]=[];
			}
			this._query[cname][this._query[cname].length]=option.value;
		}
 		
 	},
// command inputs

 	 image : function( control, action ){ 	 	
 	 		if(action) this._value_query(control);
 	 },
 	 button : function( control, action ){ 	 	
 	 		if(action) this._value_query(control);
 	 },
 	 
 	 submit : function( control, action ){ 	 	
 	 		if(action) { 
 	 			this._value_query(control);
 	 		}
 	 },
 	 
 	 // Anchor link pseudo-control.
 	 link : function(control, action ){
 	 		if(action) {
 	 			this._value_query(control);
 	 			if(control.parameters){
 	 				this.appendParameters(control.parameters);
 	 			}
 	 		}
 	 },
 	 
	// same as link, but have additional field - control, for input submit.
 	 input : function(control, action ){
 	 	if(action) {
 	 		this.link(control, action );
 	 		// append original control.
			if( control.control ) {
        		this.appendControl(control.control,action);
        	}
 	 	}
 	 },
 	 
	 // Append one control to query.
	 appendControl : function(control,action){
			if( this[control.type] ) {
        		this[control.type](control,action);
        	} else {
        		this._appendById(control.id||control);
          }
	 
	 },
	 
	 // Append all non-hidden controls from form to query.
	 appendFormControls : function(hiddenOnly, actionControl){
	 	try {
	 	 var elems = this._form.elements;
	 	 if(elems){
		 var k = 0;
		   for ( k=0;k<elems.length;k++ ) {
		          var element=elems[k];
		          
		          //skip actionControl, we're going to add it later
		          if (element == actionControl) {
		        	  continue;
		          }
		          
				  try {  
				    if(  !hiddenOnly || element.type == "hidden") {
		          		this.appendControl(element,false) ;
		            }
		   		  } catch( ee ) {
			        	 LOG.error("exception in building query ( append form control ) " + ee );
			      }
		    }
		  }
	 	} catch(e) {
	 		LOG.warn("Error with append form controls to query "+e)
	 	}
	 	
	 	if (actionControl) {
      		this.appendControl(actionControl, true);
	 	}
	 },

	// append map of parameters to query.
	 appendParameters : function(parameters){
		for( k in parameters ){
 	 	  if(typeof Object.prototype[k] == 'undefined'){
 	 	    LOG.debug( "parameter " + k  + " with value "+parameters[k]);
		  	this.appendParameter(k,parameters[k]);
		  }
		}	
	 },
	 
	 setActionUrl : function(actionUrl){
	 	this._actionUrl = actionUrl;
	 },
// Return action URL ( append extention, if present )
 	 getActionUrl : function( ext ) {
 	 	var actionUrl = this._actionUrl ;
 	 	var ask = actionUrl.indexOf('?');
 	 	// create absolute reference - for Firefox XMLHttpRequest base url can vary
 	 	if( actionUrl.substring(0,1) == '/' ) {
 	 		actionUrl = this._pageBase+actionUrl;
 	 	}
 	 	if ( ! ext ) ext = this._ext ;
 	 	if( ask >=0 )
 	 		{
 	 		return actionUrl.substring(0,ask) + ext + actionUrl.substring(ask); 	 		
 	 		}
 	 	else return actionUrl + ext;
 	 },
 	 
 	 
// Build query string for send to server.
 	 getQueryString : function() {
 	 	var qs = "";
 	 	var iname ;
 	 	var querySegments = [];
 	 	var paramName;
 	 	for ( var k in this._query ){
 	 	  if(typeof Object.prototype[k] == 'undefined'){
 	 		iname = this._query[k];
 	 		paramName = this._encode(k);
 	 		if( iname instanceof Object ){
 	 			for ( var l=0; l< iname.length; l++ ) {
 	 		 	 	querySegments.push(paramName);
 	 		 	 	querySegments.push("=");
 	 		 	 	querySegments.push(this._encode(iname[l]));
 	 		 	 	querySegments.push("&");
	 	 		}
 	 		} else {
 	 	 	 	querySegments.push(paramName);
 	 	 	 	querySegments.push("=");
 	 	 	 	querySegments.push(this._encode(iname));
 	 	 	 	querySegments.push("&");
 	 		}
 	 	  }
 	 	}

 	 	qs = querySegments.join("");
 	 	
 	 	LOG.debug("QueryString: "+qs);
 	 	return qs;
 	 },
 	 // private methods
 	 
	 _appendById : function( id ) {
	 	this.appendParameter(this._form.id + "_link_hidden_", id);
	 	// JSF-ri version ...
	 	// this._query[this._form.id + "_idcl"]=id;
	 },
	 

 	 _value_query : function(control){
			if (control.name) {
		 	 	LOG.debug("Append "+control.type+" control "+control.name+" with value ["+control.value+"] and value attribute ["+control.getAttribute('value')+"]");
				if(null != control.value){
			 	 	this.appendParameter(control.name, control.value);
				}
			} else {
		 	 	LOG.debug("Ignored "+control.type+" no-name control with value ["+control.value+"] and value attribute ["+control.getAttribute('value')+"]");
			}
	 },
 	 
 	 _check_query : function(control){
 	 	if( control.checked ) {
 	 		this.appendParameter(control.name, control.value?control.value:"on");
 	 	}
 	 },
 	 
 	 _radio_query : function(control) {
  	 	if( control.checked ) {
 	 		this.appendParameter(control.name, control.value?control.value:"");
 	 	}
 	 },
 	 
 	 // Append parameter to query. if name exist, append to array of parameters
 	 appendParameter: function(cname,value){
 	 			if( ! this._query[cname] ){
 	 				this._query[cname]=value;
 	 				return;
 	 			} else if( !(this._query[cname] instanceof Object) ){
 	 				this._query[cname] = [this._query[cname]];
 	 			}
 	 			this._query[cname][this._query[cname].length]=value;
 	 },
 	 
    // Encode data string for request string
    _encode : function(string) {
	    try {
	    	return encodeURIComponent(string);
	    } catch(e) {
	    var str = escape(string);
	    // escape don't encode +. but form replace  ' ' in fields by '+'
		return str.split('+').join('%2B');
	    }
    }
 	 
 	 
  }
  	

A4J.AJAX.getText = function(oNode, includeComment) {
    var s = "";
    var nodes = oNode.childNodes;
    for(var i=0; i < nodes.length; i++){
        var node = nodes[i];
        var nodeType = node.nodeType;
        
        if(nodeType == Node.TEXT_NODE || nodeType == Node.CDATA_SECTION_NODE || 
        		(includeComment && nodeType == Node.COMMENT_NODE)){
            
        	s += node.data;
        } else if(nodeType == Node.ELEMENT_NODE || nodeType == Node.DOCUMENT_NODE || nodeType == Node.DOCUMENT_FRAGMENT_NODE){
            s += arguments.callee(node, includeComment);
        }
    }
    return s;
}

A4J.AJAX.isWebkitBreakingAmps = function() {
	//RF-4429
	if (!this._webkitBreakingAmps) {
		var elt = document.createElement("div");
		elt.innerHTML = "<a href='#a=a&#38;b=b'>link</a>";

		var link = elt.firstChild;
		if (link && link.getAttribute && /&#38;b=b$/.test(link.getAttribute('href'))) {
			this._webkitBreakingAmps = 2; 
		} else {
			this._webkitBreakingAmps = 1; 
		}
	}
	
	return this._webkitBreakingAmps > 1;
};

A4J.AJAX.isXhtmlScriptMode = function() {
	if (!this._xhtmlScriptMode) {
		var elt = document.createElement("div");
		elt.innerHTML = "<script type='text/javascript'><!--\r\n/**/\r\n//--></script>";
		
		var commentFound = false;
		var s = elt.firstChild;
		
		while (s) {
			if (s.nodeType == Node.ELEMENT_NODE) {
				var c = s.firstChild;
				
				while (c) {
					if (c.nodeType == Node.COMMENT_NODE) {
						commentFound = true;
						break;
					}
		
					c = c.nextSibling;
				}

				break;
			}
			
			s = s.nextSibling;
		}
		
		if (commentFound) {
			this._xhtmlScriptMode = 2;
		} else {
			this._xhtmlScriptMode = 1;
		}
	}

	return this._xhtmlScriptMode > 1;
}

//Test for re-evaluate Scripts in updated part. Opera & Safari do it.
A4J.AJAX._scriptEvaluated=false;
A4J.AJAX.TestScriptEvaluation = function () {
if ((!document.all || window.opera) && !A4J.AJAX._scriptTested){
 		try{	
			// Simulate same calls as on XmlHttp
			var oDomDoc = Sarissa.getDomDocument();
			var _span;
			if (Sarissa._SARISSA_IS_IE10_or_IE11) {
				span = document.createElement("script");
			} else {
				span = document.createElement("span");
			}
			document.body.appendChild(_span);
			// If script evaluated with used replace method, variable will be set to true
			var xmlString = "<html xmlns='http://www.w3.org/1999/xhtml'><sc"+"ript>A4J.AJAX._scriptEvaluated=true;</scr"+"ipt></html>";
			oDomDoc = (new DOMParser()).parseFromString(xmlString, "text/xml");
			var _script=oDomDoc.getElementsByTagName("script")[0];
			if (!window.opera && !A4J.AJAX.isWebkitBreakingAmps() && _span.outerHTML) {
				if (Sarissa._SARISSA_IS_IE10_or_IE11) {
					_span.innerHTML = "A4J.AJAX._scriptEvaluated=true;";
				} else {
					span.outerHTML = new XMLSerializer().serializeToString(_script);
				}
			} else {
		    	var importednode ;
		   		importednode = window.document.importNode(_script, true);
				document.body.replaceChild(importednode,_span);
			}
			
		} catch(e){ /* Mozilla in XHTML mode not have innerHTML */ };
}
A4J.AJAX._scriptTested = true;
}

A4J.AJAX.TestReplacedGetElementByIdVisibility = function() {
	if (!A4J.AJAX._replacedGetElementByIdVisibilityTested) {
		A4J.AJAX._replacedGetElementByIdVisibilityTested = true;

		A4J.AJAX.TestScriptEvaluation();
		if (A4J.AJAX._scriptEvaluated) {
			try {
				A4J.AJAX._testReplacedGetElementByIdVisibility = true;
				
				var _span = document.createElement("span");
				document.body.appendChild(_span);

				var xmlString = "<html xmlns='http://www.w3.org/1999/xhtml'><span id='_A4J_AJAX_TestReplacedGetElementByIdVisibility'><sc"+"ript>A4J.AJAX._testReplacedGetElementByIdVisibility = !!(document.getElementById('_A4J_AJAX_TestReplacedGetElementByIdVisibility'));</scr"+"ipt></span></html>";
				oDomDoc = (new DOMParser()).parseFromString(xmlString, "text/xml");
				var _newSpan = oDomDoc.getElementsByTagName("span")[0];

				var importednode;
		   		importednode = window.document.importNode(_newSpan, true);
				document.body.replaceChild(importednode,_span);
				document.body.removeChild(importednode);
			} catch (e) {
				LOG.error("Error testing replaced elements getElementById() visibility: " + e.message);
			}
		}
	}
};

//Test helpers added by Nuxeo
//@since 3.3.1.GA-NX9 (5.7.3)
A4J.AJAX.TestStarted = false;
A4J.AJAX.TestRequestsCount = 0;

A4J.AJAX.startTest = function() {
	A4J.AJAX.TestStarted = false;
	A4J.AJAX.TestRequestsCount = 0;
};

A4J.AJAX.IncrementTestRequestsCount = function() {
	A4J.AJAX.TestStarted = true;
	A4J.AJAX.TestRequestsCount++;
};

A4J.AJAX.DecrementTestRequestsCount = function() {
	A4J.AJAX.TestRequestsCount--;
};

A4J.AJAX.isTestFinished = function() {
	if (A4J.AJAX.TestStarted == true && A4J.AJAX.TestRequestsCount == 0) {
		return true;
	}
	return false;
};