// ClientUILib base.js v1.0.0, Fri Jan 19 19:16:36 CET 2007

// TODO: Copyright (c) 2007, Denis Morozov (dmorozov@exadel.com)
// ...

if(!ClientUILib) {

var ClientUILib = {
	Version: '1.0.0',
	Name: 'ClientUILib',
	LibraryPath: './',	
	packages: [],
	load: function(showLog) {
	  // Check for Prototype JavaScript framework
	  
	  if((typeof Prototype=='undefined') || 
	     (typeof Element == 'undefined') || 
	     (typeof Element.Methods=='undefined') ||
	     parseFloat(Prototype.Version.split(".")[0] + "." +
	                Prototype.Version.split(".")[1]) < 1.5)
	     throw("ClientUILib requires the Prototype JavaScript framework >= 1.5.0");
	  	
	  // Check for Extend JavaScript library
//	  if((typeof Extend=='undefined') ||
//	  	Extend.VERSION < 1.1)
//	     throw("ClientUILib requires the Extend JavaScript library >= 1.1");

	  $A(document.getElementsByTagName("script")).findAll( function(s) {
	    return (s.src && s.src.match(/ClientUILib\.js(\?.*)?$/))
	  }).each( function(s) {
	    LibraryPath = s.src.replace(/ClientUILib\.js(\?.*)?$/,'');
	  });
	  
	  if(showLog) {
		  ClientUILogger.create("ClientUILogger");
		  this.startTime = (new Date()).getTime();
	  }
	  
	  this.initBrowser();
	},
 	include: function(libraryPackageName) {
		if(!this.packages)
			this.packages=[];
		if(!this.packages[libraryPackageName]) {
			this.packages[libraryPackageName] = true;
			var re = /\./g; // Replace all '.' in package name
			var packagePath = LibraryPath + libraryPackageName.replace(re, "/");
			document.write('<script type="text/javascript" src="' + packagePath + '.js"></script>');
		}
	},
	include2: function(libraryPackageName) {
		if(!this.packages)
			this.packages=[];
		if(!this.packages[libraryPackageName]) {
			this.packages[libraryPackageName] = true;
			var re = /\./g; // Replace all '.' in package name
			var packagePath = LibraryPath + libraryPackageName.replace(re, "/");
			var e = document.createElement("script");
		   	e.src = packagePath+".js";
		   	e.type="text/javascript";
		   	document.getElementsByTagName("head")[0].appendChild(e);		
		}
	},
	requireClass: function(libName) {
		// required class not included before
		if(!this.packages[libName]) {
			//this.include2(libName);
			ClientUILib.log(ClientUILogger.ERROR, "Library '" + libName + "' required!!!");
			throw("Package '" + libName + "' is required!");
		}
	},
	declarePackage: function(libName) {
		var pckg = null;
		var packages = $A(libName.split("."));
		packages.each( function(s) {
			if(pckg == null)
				pckg = eval(s);
			else {
				if(!pckg[s]) pckg[s] = {};
				pckg = pckg[s];
			}
	  	});
	  	this.packages[libName] = true;
	},
	log: function(level, infoText) {
		if(ClientUILogger.isCreated){
			ClientUILogger.log(level, infoText);
		} else {
			switch(level) {
				case ClientUILogger.INFO: LOG.info(infoText); break; 	
				case ClientUILogger.ERROR: LOG.error(infoText); break; 	
				case ClientUILogger.WARNING: LOG.warn(infoText); break; 	
				default: LOG.a4jDebug(infoText);; 	
			}
		}
	},
	
	initBrowser: function() {
		var ua = navigator.userAgent.toLowerCase();
		/** @type Boolean */
		this.isOpera = (ua.indexOf('opera') > -1);
	   	/** @type Boolean */
		this.isSafari = (ua.indexOf('webkit') > -1);
	   	/** @type Boolean */
		this.isIE = (window.ActiveXObject);
	   	/** @type Boolean */
		this.isIE7 = (ua.indexOf('msie 7') > -1);
	   	/** @type Boolean */
		this.isGecko = !this.isSafari && (ua.indexOf('gecko') > -1);
		
		if(ua.indexOf("windows") != -1 || ua.indexOf("win32") != -1){
		    /** @type Boolean */
		    this.isWindows = true;
		}else if(ua.indexOf("macintosh") != -1){
			/** @type Boolean */
		    this.isMac = true;
		}
		if(this.isIE && !this.isIE7){
	        try{
	            document.execCommand("BackgroundImageCache", false, true);
	        }catch(e){}
	    }
	}	
};

var ClientUILogger = {
	// log level
	INFO: 		1,
	WARNING: 	2,
	ERROR: 		3,
	EVENT:		4, //KAW EVENT level added to trace events
	ALERT:		5, //KAW ALERT level to stop executing script
	hEnabledLevels: {
		1: true,
		2: true,
		3: true,
		4: true,
		5: false
	},	
	// flag logger is initialized
	isCreated: false,
	width: 460,
	height: 600,
	top: 0,
	left: 750,
	bLoggingEnabled: true,
	create: function() {
		this.mainDiv = $(document.createElement("div"));
		this.mainDiv.setStyle({border: '1px black solid',
			position: 'absolute',padding: '1px'});
		this.logElement = $(document.createElement("div"));
		this.logElement.setStyle({overflow: 'auto', whiteSpace: 'nowrap'});
		this.buttonsContainer = $(document.createElement("div"));
		
		var clearDiv = this.buttonClear = $(document.createElement('div'));
		clearDiv.setStyle({width: 120 + 'px', height: 25 + 'px',
			border: '1px black solid'});
		clearDiv.innerHTML = 'Clear';
		
		var toggleLoggingDiv = this.buttonToggleLogging = $(document.createElement('div'));
		toggleLoggingDiv.setStyle({width: 120 + 'px', height: 25 + 'px',
			border: '1px black solid', position: 'relative',
			top: '-27px', left: '122px'
		});
		toggleLoggingDiv.innerHTML = 'Logging '+this.isLoggingEnabled();
		
		var toggleAlertDiv = this.buttonToggleAlert = $(document.createElement('div'));
		toggleAlertDiv.setStyle({width: 120 + 'px', height: 25 + 'px',
			border: '1px black solid', position: 'relative',
			top: '-54px', left: '244px'
		});
		toggleAlertDiv.innerHTML = 'Alert '+this.isLevelEnabled(ClientUILogger.ALERT);		
		
		this.buttonsContainer.appendChild(clearDiv);
		this.buttonsContainer.appendChild(toggleLoggingDiv);
		this.buttonsContainer.appendChild(toggleAlertDiv);
		this.mainDiv.appendChild(this.logElement);
		this.mainDiv.appendChild(this.buttonsContainer);		
		
		this.eventClearClicked = this.onClearClick.bindAsEventListener(this);
		this.eventToggleLoggingClicked = this.onToggleLoggingClick.bindAsEventListener(this);
		this.eventToggleAlertClicked = this.onToggleAlertClick.bindAsEventListener(this);
		Event.observe(toggleLoggingDiv, 'click', ClientUILogger.eventToggleLoggingClicked);
		Event.observe(toggleAlertDiv, 'click', ClientUILogger.eventToggleAlertClicked);
		Event.observe(clearDiv, 'click', ClientUILogger.eventClearClicked);
		Event.observe(window, 'load', ClientUILogger.init);
		Event.observe(window, 'resize', ClientUILogger.resizeWindow);
		
		this.isCreated = true;
	},
	onToggleAlertClick: function() {
		this.toggleLevel(ClientUILogger.ALERT);
		this.buttonToggleAlert.innerHTML = 'Alert '+this.isLevelEnabled(ClientUILogger.ALERT);
	},
	onToggleLoggingClick: function(event) {
		this.toggleLogging();
		this.buttonToggleLogging.innerHTML = 'Logging '+this.isLoggingEnabled();
	},
	onClearClick: function(event) {
		Event.stop(event);
		this.logElement.innerHTML = '';
	},
	init: function() {
		if(ClientUILogger.mainDiv)
			document.body.appendChild(ClientUILogger.mainDiv);
		ClientUILogger.show();
	},
	resizeWindow: function() {
		ClientUILogger.show();
	},
	show: function() {
		if(this.logElement) {
			Element.show(this.mainDiv);
			this.mainDiv.setStyle({width: this.width + 'px',
				height: this.height + 'px', 
				top: this.top + 'px',
				left: this.left+ 'px',
				zIndex: '1000'});
			this.logElement.setStyle({width: '100%', height: '90%'});
			this.buttonsContainer.setStyle({width: '100%', height: '10%'});			
			//this.logElement.setStyle({top: (this.getWindowHeight() - this.height - 10) + 'px'});
			//this.logElement.setStyle({top: 10 + 'px'});
			//this.logElement.setStyle({left: (this.getWindowWidth() - this.width - 10) + 'px'});
			//KAW changed logger display place
		}
	},
	isLevelEnabled: function(level) {
		return this.hEnabledLevels[level];
	},
	isLoggingEnabled: function() {
		return this.bLoggingEnabled;
	},
	toggleLogging: function() {
		this.bLoggingEnabled = !this.bLoggingEnabled;
	},
	toggleLevel: function(level) {
		this.hEnabledLevels[level] = !this.hEnabledLevels[level];
	},	
	log: function(level, infoText) {
		var bIgnoreLog = !this.isLoggingEnabled() || !this.isLevelEnabled(level);
		if (bIgnoreLog) { 
			//PREMATURE RETURN no logging required
			return;
		}
		
		if (level == ClientUILogger.ALERT) {
			alert(infoText);
		}else{
			var msg = $(document.createElement("div"));
			this.logElement.appendChild(msg);
			msg.setStyle({width: '100%'});
			
			var font = "bold normal bold 10pt Arial";
			var fontColor = "red";

			switch(level) {
				case ClientUILogger.INFO: 
					fontColor = "black";
					font = "normal normal normal 10pt Arial";
					break;
				case ClientUILogger.WARNING: 
					fontColor = "blue";
					font = "italic normal normal 10pt Arial";
					break;
				case ClientUILogger.ERROR: 
					fontColor = "red";
					font = "normal normal bold 10pt Arial";
					break;
				case ClientUILogger.EVENT: 
					fontColor = "green";
					font = "normal normal bold 10pt Arial";
					break;
				default:
					infoText = "UNRESOLVED: level=" + level + ", msg=" + infoText;
			}
			msg.setStyle({font: font});
			msg.setStyle({color: fontColor});
			msg.appendChild(document.createTextNode("> " + infoText));
			
			this.logElement.scrollTop = this.logElement.scrollHeight;			
		}
	},
	getWindowWidth: function(){
	    var innerWidth;
		  if (navigator.appVersion.indexOf('MSIE')>0) {
			  innerWidth = document.body.clientWidth;
	    } else {
			  innerWidth = window.innerWidth;
	    }
	    return innerWidth;	
	},
	getWindowHeight: function(){
	    var innerHeight;
		  if (navigator.appVersion.indexOf('MSIE')>0) {
			  innerHeight = document.body.clientHeight;
	    } else {
			  innerHeight = window.innerHeight;
	    }
	    return innerHeight;	
	}	
};

ClientUILib.load(false); //KAW debugging OFF

// declare predefined packages
var ClientUI = {
	controls: {},
	layouts: {}
};

// Some helper functions\
if(!ClientUILib.isIE){
	HTMLElement.prototype.click = function() {
		var evt = this.ownerDocument.createEvent('MouseEvents');
		evt.initMouseEvent('click', true, true, this.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
		this.dispatchEvent(evt);
	}
};

// Usage: Event.onReady(callbackFunction);
Object.extend(Event, {
	_domReady : function() {
		if (arguments.callee.done) return;
		arguments.callee.done = true;
		 
		if (Event._timer) clearInterval(Event._timer);
		
		Event._readyCallbacks.each(function(f) { f() });
		Event._readyCallbacks = null;
	},
	onReady : function(f) {
		if (!this._readyCallbacks) {
			var domReady = this._domReady;
		
			if (domReady.done) return f();
		
			if (document.addEventListener)
				document.addEventListener("DOMContentLoaded", domReady, false);
			if (/WebKit/i.test(navigator.userAgent)) {
				this._timer = setInterval(function() {
					if (/loaded|complete/.test(document.readyState)) domReady();
				}, 10);
			}
			Event.observe(window, 'load', domReady);
			Event._readyCallbacks = [];
		}
		Event._readyCallbacks.push(f);
	}
});

};
