
/**
 * Provide client side logging capabilities to AJAX applications.
 *
 * @author <a href="mailto:thespiegs@users.sourceforge.net">Eric Spiegelberg</a>
 * @see <a href="http://sourceforge.net/projects/log4ajax">Log4Ajax</a>
 */
if (!window.LOG) { 
	window.LOG = {};
}

LOG.Level = function(name, priority, color){
	this.name = name;
	this.priority = priority;
	if(color){
		this.color = color;
	}
}

LOG.OFF = new LOG.Level("off", 1000);
LOG.FATAL  = new LOG.Level("fatal", 900, "red");
LOG.ERROR = new LOG.Level("error", 800, "red");
LOG.WARN = new LOG.Level("warn", 500, "yellow");
LOG.INFO = new LOG.Level("info", 400, "blue");
LOG.DEBUG = new LOG.Level("debug", 300, "darkblue");
LOG.ALL = new LOG.Level("all", 100);
LOG.A4J_DEBUG = new LOG.Level("a4j_debug", 0, "green");

LOG.LEVEL = LOG.OFF;

LOG._window = null;
LOG.transmitToServer = true;
LOG.consoleDivId = "logConsole";
LOG.styles = {
a4j_debug: "green",
debug : "darkblue",
info : "blue",
warn : "yellow",
error : "red",
fatal : "red"
};

LOG.a4j_debug = function(msg,pre)
{
	LOG._log(msg, LOG.A4J_DEBUG ,pre);
}

LOG.debug = function(msg,pre)
{
	LOG._log(msg, LOG.DEBUG ,pre);
}

LOG.info = function(msg,pre)
{
	LOG._log(msg, LOG.INFO ,pre);
}

LOG.warn = function(msg,pre)
{
	LOG._log(msg, LOG.WARN ,pre);
}

LOG.error = function(msg,pre)
{
	LOG._log(msg, LOG.ERROR ,pre);
}

LOG.fatal = function(msg,pre)
{
	LOG._log(msg, LOG.FATAL ,pre);
}

LOG.registerPopup = function(hotkey,name,width,height,level){
	if(!LOG._onkeydown){
		LOG._onkeydown = document.onkeydown;
	}
	var key = hotkey.toUpperCase();
	document.onkeydown = function(e){
		if (window.event){ e = window.event;};
		if (String.fromCharCode(e.keyCode) == key & e.shiftKey & e.ctrlKey){ 
			LOG.LEVEL = level;
			LOG.openWindow(name,'width='+width+',height='+height+',toolbar=no,scrollbars=yes,location=no,statusbar=no,menubar=no,resizable=yes,left = '+((screen.width - width) / 2)+',top ='+((screen.height - height) / 2));
		} else {
	      if(LOG._onkeydown) LOG._onkeydown(e);
		}; 
	}
}

LOG.clear = function() {
	if(LOG._window && LOG._window.document){
		consoleDiv = LOG._window.document.body;
	} else {
		consoleDiv = window.document.getElementById(LOG.consoleDivId);
	}

	consoleDiv.innerHTML = '<button onclick="LOG.clear()">Clear</button><br />';
}

LOG.openWindow = function(name,features){
	if(LOG._window){
		LOG._window.focus();
	} else {
		LOG._window = window.open("",name,features);

		LOG._window.LOG = LOG;
		LOG.clear();
		
		var _LOG = LOG;
		LOG._window.onunload = function(){
			_LOG._window.LOG = null;
			_LOG._window = null;
			_LOG.LEVEL = _LOG.OFF;
			_LOG=undefined;
		}
	}
}

LOG._log = function(msg, logLevel,pre)
{
	if(logLevel.priority >= LOG.LEVEL.priority){
		LOG._logToConsole(msg, logLevel,pre);
	
		if (LOG.transmitToServer)
		{
			LOG._logToServer(msg, logLevel);
		}
	}
}

LOG._time = function(){
	var currentTime = new Date();
	var hours = currentTime.getHours();
	var minutes = currentTime.getMinutes();
	if (minutes < 10){
		minutes = "0" + minutes;
	}
	var seconds = currentTime.getSeconds();
	if (seconds < 10){
		seconds = "0" + seconds;
	}
	var millisec = currentTime.getTime()%1000;
	if(millisec<100){
		millisec = "0"+millisec;
	}
	if(millisec<10){
		millisec = "0"+millisec;
	}
	return hours+":"+minutes+":"+seconds+","+millisec;
}

LOG._logToConsole = function(msg, logLevel,preformat)
{
	var consoleDiv ;
	var doc;
	if(LOG._window && LOG._window.document){
		doc = LOG._window.document;
		consoleDiv = LOG._window.document.body;
	} else {
		doc = window.document;
		consoleDiv = window.document.getElementById(LOG.consoleDivId);
	}
	if (consoleDiv)
	{
		var span = doc.createElement("span");
		span.style.color=logLevel.color;
		span.appendChild(doc.createTextNode(logLevel.name+"["+LOG._time()+"]: "));
	 	var div = doc.createElement("div");
   		var textnode = doc.createTextNode(msg);
        div.appendChild(span);
   		div.appendChild(textnode);
   		// preformatted - for example, html
   		if(preformat){
		 	var pre = doc.createElement("span");
   			textnode = doc.createTextNode(preformat);
   			pre.appendChild(textnode);
	   		div.appendChild(pre);
   		}
        consoleDiv.appendChild(div);
/*	
		consoleDiv.innerHTML = "<span style='" + LOG.styles[logLevel] + "'>" + 
							   logLevel + "</span>: " + msg + "<br/>" + 
							   consoleDiv.innerHTML;*/
	}
	else
	{
		// If the consoleDiv is not available, you could create a 
		// new div or open a new window.
	}
}

LOG._logToServer = function(msg, logLevel)
{
	var data = logLevel.name.substring(0, 1) + msg;
	// TODO - use sarissa-enabled request.
	// Use request.js to make an AJAX transmission to the server
//	Http.get({
//		url: "log",
//		method: "POST",
//		body: data,
//		callback: LOG._requestCallBack
//	});
}

LOG._requestCallBack = function()
{
	// Handle callback functionality here; if appropriate
}