FileUploadEntry = {};
FileUploadEntry = Class.create();


FileUploadEntry.INITIALIZED = "initialized";
FileUploadEntry.READY = "ready";
FileUploadEntry.UPLOAD_IN_PROGRESS = "progress";

FileUploadEntry.UPLOAD_CANCELED = "canceled";
FileUploadEntry.UPLOAD_SUCCESS = "done";
FileUploadEntry.UPLOAD_TRANSFER_ERROR = "transfer_error";
FileUploadEntry.UPLOAD_SERVER_ERROR = "server_error";
FileUploadEntry.UPLOAD_SIZE_ERROR = "size_error";
FileUploadEntry.UPLOAD_FORBIDDEN = "forbidden";

FileUploadEntry.LABELS = {};
FileUploadEntry.LABELS[FileUploadEntry.INITIALIZED] = '';
FileUploadEntry.LABELS[FileUploadEntry.READY] = '';
FileUploadEntry.LABELS[FileUploadEntry.UPLOAD_IN_PROGRESS] = '';
FileUploadEntry.LABELS[FileUploadEntry.UPLOAD_CANCELED] = '';
FileUploadEntry.LABELS[FileUploadEntry.UPLOAD_FORBIDDEN] = 'Uploading forbidden';

FileUploadEntry.clearControlTemplate =
   [
	new E('a',
			{
				'style':'',
				'onclick': function (context) { return 'var entry = FileUploadEntry.getComponent(this); entry.uploadObject.clear(entry); return false;';},
				'className':function (context) { return 'rich-fileupload-anc ' + Richfaces.evalMacro("className", context); },
				'href':'#'
			},
		    [
		     	new T(function (context) {return Richfaces.evalMacro("controlLink", context);} )
		    ])
   ];

FileUploadEntry.stopControlTemplate =
   [
	new E('a',
			{
				'style':'',
				'onclick': function (context) { return 'FileUploadEntry.getComponent(this).uploadObject.stop(); return false;';},
				'className':function (context) { return 'rich-fileupload-anc ' + Richfaces.evalMacro("className", context); },
				'href':'#'
			},
		    [
		     	new T(function (context) {return Richfaces.evalMacro("controlLink", context);} )
		    ])
	];

FileUploadEntry.cancelControlTemplate =
   [
	new E('a',
			{
				'style':'',
				'onclick': function (context) { return 'var entry = FileUploadEntry.getComponent(this); entry.uploadObject.clear(entry, true); return false;';},
				'className':function (context) { return 'rich-fileupload-anc ' + Richfaces.evalMacro("className", context); },
				'href':'#'
			},
		    [
		     	new T(function (context) {return Richfaces.evalMacro("controlLink", context);} )
		    ])
	];

FileUploadEntry.template =
	[
	 new E('table',
			 {'cellspacing':'0', 'cellpadding':'0', 'border':'0', 'style':'width:100%'},
			 [
			  new E('tbody',{},
					  [
					   new E('tr',{},
							   [
							    new E('td',{'className':function (context) { return 'rich-fileupload-font rich-fileupload-name rich-fileupload-table-td ' + Richfaces.evalMacro("className", context);}},
							    		[
							    		 new E('div',{'className':'rich-fileupload-name-padding','style':function (context) {return 'overflow : hidden; width:' + Richfaces.evalMacro("fileEntryWidth", context);}},
							    				 [
							    				  new ET(function (context) { return Richfaces.evalMacro("fileName", context)})
							    				  ]),
							    		 new E('div',{ }),
							    		 new E('div',{'className':'rich-fileupload-name-padding','style':function (context) {return 'overflow : hidden; width:' + Richfaces.evalMacro("fileEntryWidth", context);}},
							    				[
							    				 new ET(function (context) { return Richfaces.evalMacro("label", context)})
							    				 ])
							    		]),
					    		 new E('td',{'style':'vertical-align: center;', 'className':'rich-fileupload-table-td'},
					    				 [
					    				  new E('div',{'className':'rich-fileupload-font rich-fileupload-del'},
			    								[
		    								    	//FileUploadEntry.clearControlTemplate
		    								    ])
					    				  ]),
			    				  new E('td',{'className':'rich-fileupload-table-td'},
			    						  [
			    						   new E('div',{'className':'rich-fileupload-font rich-fileupload-scroll'},[ new T ('\u00A0') ])
			    						   ])
							    ])
					   ])
			  ])
	 ];



FileUploadEntry.getComponent = function(elt) {
	while (elt) {
		var component = elt.component;
		if (component) {
			return component
		} else {
			elt = elt.parentNode;
		}
	}
};

Object.extend(FileUploadEntry.prototype, {

	fileInput: null,

	fileName: null,

	uploadObject: null,

	state: FileUploadEntry.INITIALIZED,

	initialize: function(fileInput, uploadObject, size, type, creator, creationDate, modificationDate) {
		this.fileInput = fileInput;
		this.uploadObject = uploadObject;

		this.size = size;
		this.type = type;
		this.creator = creator;
		this.creationDate = creationDate;
		this.modificationDate  = modificationDate;

		var fileName = JSNode.prototype.xmlEscape($F(this.fileInput));
		this.fileName = fileName;
		var content = FileUploadEntry.template.invoke('getContent', {fileName: fileName, fileEntryWidth: uploadObject.getFileEntryWidth(), className : this.uploadObject.classes.FILE_ENTRY.ENABLED }).join('');

		Element.insert(this.uploadObject.items, content);

		this.entryElement = this.uploadObject.items.childNodes[this.uploadObject.items.childNodes.length - 1];
		this.entryElement.component = this;
		this.statusLabel = this.entryElement.rows[0].cells[0].lastChild;
		this.controlArea = this.entryElement.rows[0].cells[1].firstChild;
		this.progressArea = this.entryElement.rows[0].cells[0].childNodes[1];
	},

	upload: function() {
		this.setState(FileUploadEntry.UPLOAD_IN_PROGRESS);
		this.setupProgressBar();
		if (this.uploadObject.isFlash) {
			this.uploadObject._flashSubmitForm(this);
		} else {
			this.uploadObject.createFrame();
			setTimeout(function(){this.uploadObject.submitForm(this)}.bind(this), 0);
		}
	},

	setupProgressBar: function () {
		this.progressArea.appendChild(this.uploadObject._progressBar);
		this.uploadObject.prepareProgressBar();
	},

	setupLabelUpdate: function () {
		this.updateLabel();
		this.labelUpdateInterval = setInterval(function () { this.updateLabel(); }.bind(this), this.uploadObject.progressBar.options['pollinterval']);
	},

	updateLabel: function () {
		if (this.state != FileUploadEntry.UPLOAD_IN_PROGRESS) {
			clearInterval(this.labelUpdateInterval);
		}else {
			var p = this.uploadObject.progressBar.getValue();
			if (p) {
				var content = this.uploadObject.labelMarkup.invoke('getContent', this.uploadObject.progressData.getContext(p)).join('');
				this.statusLabel.innerHTML = content;

			}
		}
	},

	finishProgressBar: function () {
		this.uploadObject.finishProgressBar();
	},

	stop: function() {
		this.uploadObject.stopScript(this.uid, this.uploadObject.formId);
	},

	_clearInput: function() {
		Richfaces.removeNode(this.fileInput);
		this.fileInput = null;
	},

	_clearEntry: function() {
		Richfaces.removeNode(this.entryElement);
		this.entryElement = null;
	},

	clear: function() {
			this._clearInput();
		this._clearEntry();
	},

	setState: function(newState) {
		var oldState = this.state;
		this.state = newState;

		Element.clearChildren(this.statusLabel);
		Element.clearChildren(this.controlArea);

		Element.insert(this.statusLabel, FileUploadEntry.LABELS[newState]);

		if (newState == FileUploadEntry.UPLOAD_IN_PROGRESS) {
			Element.update(this.controlArea, FileUploadEntry.stopControlTemplate.invoke('getContent',{'controlLink': FileUploadEntry.LABELS['entry_stop'],'className': this.uploadObject.classes.FILE_ENTRY_CONTROL.ENABLED}).join(''));
		} else if (newState == FileUploadEntry.UPLOAD_SUCCESS) {
			Element.update(this.controlArea, FileUploadEntry.clearControlTemplate.invoke('getContent',{'controlLink': FileUploadEntry.LABELS['entry_clear'],'className': this.uploadObject.classes.FILE_ENTRY_CONTROL.ENABLED}).join(''));
		} else {
			Element.update(this.controlArea, FileUploadEntry.cancelControlTemplate.invoke('getContent',{'controlLink': FileUploadEntry.LABELS['entry_cancel'],'className': this.uploadObject.classes.FILE_ENTRY_CONTROL.ENABLED}).join(''));
		}

		if (newState == FileUploadEntry.UPLOAD_SUCCESS) {
			this._clearInput();
		}

		this.uploadObject.notifyStateChange(this, oldState);
	}

});

ProgressData = Class.create();
Object.extend(ProgressData.prototype, {
	size: null,

	startTime: null,

	initialize: function(size) {
		this.size = size;
		this.startTime = parseInt((new Date().getTime())/1000);
	},

	ss: function () {
		return parseInt((this.time - this.startTime) % 60) + "";
	},

	mm: function () {
		return parseInt((this.time - this.startTime)/60)+ "";
	},

	hh: function () {
		return parseInt((this.time - this.startTime)/3600) + "";
	},

	B: function () {
		return this.size;
	},

	KB: function () {
		return parseInt(this.size/1024);
	},

	MB: function () {
		return parseInt(this.size/(1024*1024));
	},

	getContext: function  (p) {
		var context = {};
		this.time = parseInt((new Date().getTime())/1000);
		context['B'] = this.B();
		context['KB'] = this.KB();
		context['MB'] = this.MB();
		context['ss'] = this.ss();
		context['mm'] = this.mm();
		context['hh'] = this.hh();
		var s = this.size;
		this.size = (this.size * p)/100;
		context['_B'] = this.B();
		context['_KB'] = this.KB();
		context['_MB'] = this.MB();
		this.size = s;
		return context;
	}

});

LoadWatcher = Class.create();
Object.extend(LoadWatcher.prototype, {
	initialize: function(iframe, callback, viewStateUpdater) {
		this.iframe = iframe;
		this.callback = callback;
		this.viewStateUpdater = viewStateUpdater;


		this.loadObserver = function() {
			if (!this.stopped) {
				this.stop();
				this.onload();
			}
			return false;
		}.bind(this);

		Event.observe(this.iframe, 'load', this.loadObserver);

		this.isError = function() {
			try {
				if (this.iframe.contentWindow && this.iframe.contentWindow.document) {
					this.iframe.contentWindow.document.readyState;
				}
			}catch(e) {
				return true;
			}
			return false;
		}.bind(this);

		this.interval = window.setInterval(function() {
			if (!this.stopped) {
				var loaded = false;
				var error = null;

				try {
					if (!Prototype.Browser.Opera && !Prototype.Browser.WebKit && this.iframe.contentWindow && this.iframe.contentWindow.document) {
						loaded = /complete/.test(this.iframe.contentWindow.document.readyState);
					}
				} catch (e) {
					error = e;
				}

				if (error) {
					this.stop();
					this.onerror();
				}
			}
			return false;
		}.bind(this),200);

	},

	oncancel: function() {
		if (!this.stopped) {
			this.stop();
			this.callback(FileUploadEntry.UPLOAD_CANCELED);
		}
	},

	onerror: function() {
		this.callback(FileUploadEntry.UPLOAD_TRANSFER_ERROR);
	},

	onload: function() {
		if (this.isError()) {
			this.callback(FileUploadEntry.UPLOAD_TRANSFER_ERROR);
			return;
		}
		var iframeDocument = this.iframe.contentWindow.document;
		var elt = iframeDocument.getElementById('_richfaces_file_upload_stopped');
		var restr = iframeDocument.getElementById('_richfaces_file_upload_size_restricted');
		var forb = iframeDocument.getElementById('_richfaces_file_upload_forbidden');
		var state = this.findViewState(iframeDocument);

		if (elt) {
			this.callback(FileUploadEntry.UPLOAD_CANCELED);
		} else if (restr) {
			this.callback(FileUploadEntry.UPLOAD_SIZE_ERROR);
		} else if (forb) {
			this.callback(FileUploadEntry.UPLOAD_SIZE_ERROR);
		} else if (state) {
			this.viewStateUpdater(state.value);
			this.callback(FileUploadEntry.UPLOAD_SUCCESS);
		} else {
			this.callback(FileUploadEntry.UPLOAD_TRANSFER_ERROR);
		}
	},

	findViewState: function(d) {
		var s = 'javax.faces.ViewState';
		var input = d.getElementsByTagName("input");
		for (var i in input) {
			if (input[i].name == s) {
				return input[i];
			}
		}
		return d.getElementById(s);
	},

	stop: function() {
		this.stopped = true;

		if (this.interval) {
			window.clearInterval(this.interval);
			this.interval = null;
		}

		if (this.loadObserver) {
			Event.stopObserving(this.iframe, 'load', this.loadObserver);
			this.loadObserver = null;
		}
	}
});


FileUpload = {};
FileUpload = Class.create();

Object.extend(FileUpload.prototype, {

	idCounter: 0,

	progressBar: null,

	iframe: null,

	element: null,

	entries: new Array(),

	activeEntry: null,

	options: null,

	runUpload: false,

	classes: null,

	events: null,

	maxFileBatchSize: null,

	uploadedCount: 0,

	initialize: function(id, formId, actionUrl, stopScript, getFileSizeScript, progressBarId, classes, label, maxFiles, events, disabled, acceptedTypes, options, labels, parameters, sessionId) {
		this.id = id;
		this.element = $(this.id);
		if (formId != '') {
			this.formId = formId;
			this.form = $(formId);
		}else {
			var f = this._getForm();
			this.formId = (f) ? f.id : null;
			this.form = f;
		}
		this._progressBar = $(progressBarId);
		this.progressBar = this._progressBar.component;
		this.entries = new Array();

		this.labelMarkup = label;
		this.disabled = disabled;

		this.element.component = this;
		this.acceptedTypes = acceptedTypes;

		this.stopScript = stopScript;
		this.getFileSizeScript = getFileSizeScript;

		this.items = $(this.id + ":fileItems");
		this.classes = classes;
		this.events = events;
		this.parameters = parameters;
		this.sessionId = sessionId;

		this.maxFileBatchSize = maxFiles;
		this.currentInput = $(this.id + ":file");

		this.actionUrl = actionUrl;
		this.options = options || {};
		this.initFlashModule();
		this.initEvents();
		this.setupAutoUpload();
		this.checkFrame();
		//this.initFileEntryWidth();
		this.initLabels(labels);
		this.processButtons();
		this.initFileInput();
	},

	cancelUpload: function(uid) {
		if (this.activeEntry && this.activeEntry.uid == uid) {
			if (this.watcher) {
				this.watcher.oncancel();
			}
			if (this.iframe) {
				try {
					//call order is critical for IE 6
					this.iframe.contentWindow.location.href = "javascript:''";
					this.iframe.contentWindow.stop();
				} catch (e) {
					//TODO - ?
				}
			}
		}
	},

	initLabels: function (labels) {
		if (labels) {
			for (var l in labels) {
				FileUploadEntry.LABELS[l] = labels[l];
			}
		}
	},

	initFileInput: function () {
		var o = this.currentInput;
		var p = o.parentNode.parentNode;
		p = $(p);
		if (p.getWidth() != 0) {
			var style = o.parentNode.style;
			style.width = p.getWidth() + "px";
			style.height = p.getHeight() + "px";

			Event.stopObserving(p,'mouseover', this.inputHandler);

			if (Richfaces.browser.isIE && this.flashComponent) {
				this.flashComponent.style.width = style.width;
				this.flashComponent.style.height = style.height;
			}
		}else {
			this.inputHandler = this.initFileInput.bindAsEventListener(this);
			Event.observe(p,'mouseover', this.inputHandler);
		}
	},

	getFileEntryWidth: function () {
		if (this.fileEntryWidth) {
			return this.fileEntryWidth;
		}
		var w;
		w = this.element.offsetWidth - 122;
		if (w<0) w = 0;
		this.fileEntryWidth = w;
		var progressW = this._progressBar.style.width;
		if (progressW == "") { progressW = 200; }
		if (progressW > this.fileEntryWidth) {
			w = (w - 2);
			if (w<0) w=0;
			w += "px";
			this._progressBar.style.width = w;
			var r  = $(this._progressBar.id + ":remain");
			if (r) {
				r.style.width = w;
				$(this._progressBar.id + ":complete").style.width = w;
			}
		}
		this.fileEntryWidth = this.fileEntryWidth + "px";
		return this.fileEntryWidth;
	},

	createFrame: function () {
		if (this.iframe) return;
		var div = document.createElement("div");
		div.style.display = 'none';
		var child = "<iframe name='"+this.id+"_iframe' id='"+this.id+"_iframe'"+ (!Richfaces.browser.isOpera ? " src=\"javascript:''\"" : "")+"></iframe>";
			div.innerHTML = child;
		document.body.appendChild(div);
		this.iframe = $(this.id + "_iframe");
	},

	checkFrame: function () {
		this.iframe = $(this.id + "_iframe");
		if (this.iframe) {
			this.deleteFrame();
		}
	},

	deleteFrame: function() {
		this.resetFrame();
		if (this.iframe) {
			document.body.removeChild(this.iframe.parentNode);
		}
		this.iframe = null;
	},

	resetFrame: function () {
		if (window.opera && this.iframe && this.iframe.contentWindow.document && this.iframe.contentWindow.document.body) {
			this.iframe.contentWindow.document.body.innerHTML = "";
		} else {
			this.iframe.src = "javascript:''";
		}
	},

	initEvents : function() {
		for (var e in this.events) {
			if (e && this.events[e]) {
				if(e == 'onupload') {
					this.element.observe("rich:onupload", function(event) {
						if(this.events['onupload'](event) !== false) {
							event.memo.entry.upload();
						}
					}.bindAsEventListener(this));
				} else {
					this.element.observe("rich:" + e, this.events[e]);
				}
			}
		}
	},

	getFileSize: function (data) {
		if (data) {

			if (!this.isFlash) {
				this.progressBar.enable();
			}

			if (this.labelMarkup) {
				var progressData = new ProgressData(data);
				this.progressData = progressData;
				if (this.activeEntry) {
					this.activeEntry.setupLabelUpdate();
				}
			}
		}else {
			if (this.activeEntry) {
				this._fileSizeScriptTimeoutId = setTimeout(function() {
					this._fileSizeScriptTimeoutId = undefined;
					this.getFileSizeScript(this.activeEntry.uid, this.formId);
				}.bind(this), this.progressBar.options['pollinterval'] || 500);
			}
		}
	},

	prepareProgressBar: function () {
		this.progressBar.setValue(0);
		Element.show(this._progressBar);
		this.progressBar.options.onerror = function (e) {
				this.errorHandler(e);
		}.bind(this);
	},

	errorHandler: function (e) {
		if (this.watcher) {
			this.watcher.stop();
			this.watcher.onerror();
			this.watcher = null;
		}
	},

	finishProgressBar: function () {

		if (this._fileSizeScriptTimeoutId) {
			clearTimeout(this._fileSizeScriptTimeoutId);
			this._fileSizeScriptTimeoutId = undefined;
		}

		this.progressBar.disable();
		this.progressBar.setValue(100);
		Element.hide(this._progressBar);
	},

	setupAutoUpload: function() {
		this.runUpload = this.options.autoUpload;
	},

	checkFileType: function (fileName) {
	if (!this.acceptedTypes || this.acceptedTypes['*']) { return true; }
		if (/(?:\S+)\.(\S+)$/.test(fileName)) {
			var type = RegExp.$1;
			type = type.toLowerCase();
			if (this.acceptedTypes[type]) {
				return true;
			}
		}
		return false;
	},

	checkDuplicated: function (elt) {
		if (!this.options.noDuplicate) return true;
		var fileName = elt.value;
		for (var i = 0; i < this.entries.length; i++) {
			if (fileName == this.entries[i].fileName) {
				return false;
			}
		}
		return true;
	},

	add: function(elt) {
		if (this.disabled) return;
		if (!elt.value) return;
		if (!this.checkFileType(elt.value) || !this.checkDuplicated(elt)) {
			var fileName = elt.value;

			//clear input value
			var newUpload = elt.cloneNode(true);
			newUpload.value = '';
			elt.parentNode.replaceChild(newUpload, elt);
			this.currentInput = newUpload;

			if(this.events.ontyperejected) {
				this.element.fire("rich:ontyperejected", { fileName : fileName });
			}

			return;
		}

		var newEntry = new FileUploadEntry(elt, this);
		this.entries.push(newEntry);

		if (this.runUpload) {
			newEntry.setState(FileUploadEntry.READY);
		} else {
			newEntry.setState(FileUploadEntry.INITIALIZED);
		}

		var newUpload = elt.cloneNode(true);
		elt.onchange = null;
		elt.style.cssText = "position: absolute; right: 0px; top: 0px; display: none; visibility: hidden;";
		newUpload.id = this.id + ":file" + (this.idCounter++);
		newUpload.value = '';
		this.currentInput = newUpload;
		elt.parentNode.appendChild(newUpload);

		if (this.events.onadd) {
			var filesArray = [];
			filesArray.push(newEntry);
			this.element.fire("rich:onadd", { entries : filesArray });
		}

		if (this.runUpload) {
			this.upload();
		}
	},

	remove: function(entry) {
		entry.clear();
		if (this.isFlash) this._flashRemoveFile(this.entries.indexOf(entry));
		this.entries = this.entries.without(entry);

	},

	_selectEntryForUpload: function() {
		this.uploadIndex = -1;
		var l = this.entries.length;
		for (var i = 0; i < l; i++) {
			var entry = this.entries[i];
			if (entry.state == FileUploadEntry.READY || entry.state == FileUploadEntry.INITIALIZED || entry.state == FileUploadEntry.UPLOAD_CANCELED) {
				this.uploadIndex = i;
				return entry;
			}
		}

		return null;
	},

	upload: function() {
		if (this.disabled) return;

		this.runUpload = true;

		if (!this.activeEntry) {
			//no upload is being run now

			var entry2upload = this._selectEntryForUpload();
			if (entry2upload) {
				if(this.events.onupload) {
					this.element.fire("rich:onupload", {entry : entry2upload});
				} else {
					entry2upload.upload();
				}
			}
		}
		return false;
	},

	stop: function() {
	if (this.disabled) return;
		this.runUpload = false;

		if (this.activeEntry) {
			if(!this.isFlash) {
				this.activeEntry.stop();
			}else {
				this._flashStop();
			}
		}
		return false;
	},

	clear: function(entry, isCancelButton) {
	if (this.disabled) return;
		if (entry) {
			if (isCancelButton || entry.state == FileUploadEntry.UPLOAD_SUCCESS || entry.state == FileUploadEntry.INITIALIZED) {
			  if (entry.state == FileUploadEntry.UPLOAD_SUCCESS){
          this.uploadedCount--;
			  }
				this.remove(entry);
				if(this.events.onclear) {
		   			this.element.fire("rich:onclear", {entry : entry});
				}
			}
		} else {
			//this.entries.length should be evaluated every time!
			var i = 0;
			while (i < this.entries.length) {
				var entry = this.entries[i];
				if (entry.state == FileUploadEntry.UPLOAD_SUCCESS) {
				  this.uploadedCount--;
					this.remove(entry);
				} else {
					i++;
				}
			}
			if(this.events.onclear) {
		   	   this.element.fire("rich:onclear", {});
		    }
		}

		if (this.entries.length == 0) {
			this.setupAutoUpload();
		}
		this.processButtons();
		return false;
	},

	processButtons: function () {
		this.disableAddButton();
		this.disableCleanButton();
		this.disableUploadButton();

	},

	cleanAllDisabled: function () {
	if (this.options['autoclear']) return true;
		var c = this.getFileEntriesSumByState(FileUploadEntry.UPLOAD_SUCCESS);
		return (c == 0);
	},

	uploadAllDisabled: function () {
		if (this.runUpload && this.activeEntry) {
				return false;
		} else {
			var c = this.getFileEntriesSumByState(FileUploadEntry.READY, FileUploadEntry.INITIALIZED, FileUploadEntry.UPLOAD_CANCELED);
			return (c == 0);
		}
	},

	getFileEntriesSumByState: function () {
		var statuses = {}
		var s = 0;
		for (var i = 0; i < arguments.length; i++) {
			statuses[arguments[i]] = true;
		}
		for (var i = 0; i < this.entries.length; i++) {
			if (statuses[this.entries[i].state]) {
				s++;
			}
		}
		return s;
	},

	disableCleanButton: function() {
		var disabled = this.cleanAllDisabled();
		var d1 = $(this.id + ":clean1");
		var d2 = $(this.id + ":clean2");
		if (disabled) {
			Element.hide(d1.parentNode);
			return;
		} else {
			Element.show(d1.parentNode);
		}

	  	if(this.disabled) {
    		d1.onclick = function() {return false;};
    	} else {
    		d1.onclick = function() {return this.clear();}.bind(this);
    	}
    	this._updateClassNames(d1, d2, this.classes.CLEAN, this.classes.CLEAN_CONTENT);

    },

    disableAddButton: function() {
    	var disabled = ((this.getFileEntriesSumByState(FileUploadEntry.READY, FileUploadEntry.INITIALIZED, FileUploadEntry.UPLOAD_CANCELED) + this.uploadedCount + this.getFileEntriesSumByState(FileUploadEntry.UPLOAD_IN_PROGRESS)) >= this.maxFileBatchSize);
    	this.currentInput.disabled = disabled || this.disabled;
    	var _disabled = this.disabled;
    	var d1 = $(this.id+":add1");
    	var d2 = $(this.id+":add2");
    	if (disabled) {
    		this.disabled = true;
    	}
    	this._updateClassNames(d1, d2, this.classes.ADD, this.classes.ADD_CONTENT);
    	this.disabled = _disabled;
    	this._flashDisableAdd(disabled || this.disabled);
    },

    disableUploadButton: function () {
    	var disabled = this.uploadAllDisabled();
    	var d1 = $(this.id + ":upload1");
    	var d2 = $(this.id + ":upload2");
    	if(disabled) {
    		Element.hide(d1.parentNode);
    	} else {
    		Element.show(d1.parentNode);
    	}
    	if (this.disabled) {
    		d1.onclick = function() {return false;};
    	}else {
    		if (!this.runUpload) {
    			d1.onclick = function() { return this.upload();}.bind(this);
    			d2.innerHTML = FileUploadEntry.LABELS['upload'];
    		}else {
    			d1.onclick = function() { return this.stop();}.bind(this);
    			d2.innerHTML = FileUploadEntry.LABELS['stop'];
    		}
    	}
    	this._updateClassNames(d1, d2, (this.runUpload) ? this.classes.STOP : this.classes.UPDATE, (this.runUpload) ? this.classes.STOP_CONTENT : this.classes.UPDATE_CONTENT);
    },

    _updateClassNames: function (d1,d2,buttonClass,buttonContentClass) {
    	if (this.disabled) {
    		d1.className = buttonClass.DISABLED;
    		d2.className = buttonContentClass.DISABLED;
    		d1.onmouseover = d1.onmouseout = d1.onmousedown = d1.onmouseup = null;
    	}else {
    		d1.className = buttonClass.ENABLED;
    		d2.className = buttonContentClass.ENABLED;
    		d1.onmouseover = function () {this.className='rich-fileupload-button-light rich-fileupload-font';};
    		d1.onmouseout = function () {this.className='rich-fileupload-button rich-fileupload-font';};
    		d1.onmousedown = function () {this.className='rich-fileupload-button-press rich-fileupload-font';};
    		d1.onmouseup = function () {this.className='rich-fileupload-button rich-fileupload-font';};
    	}
    },

    disable: function () {
    	this.disabled = true;
    	this.items.className = "rich-fileupload-list-overflow " + this.classes.UPLOAD_LIST.DISABLED;
    	for (var i = 0; i < this.entries.length; i++) {
    		var entry = this.entries[i];
    		entry.entryElement.rows[0].cells[0].className = "rich-fileupload-font rich-fileupload-name rich-fileupload-table-td " + this.classes.FILE_ENTRY.DISABLED;
    		entry.controlArea.firstChild.className = "rich-fileupload-anc " + this.classes.FILE_ENTRY_CONTROL.DISABLED;
    	}
    	this.processButtons();
    },

    enable: function () {
    	this.disabled = false;
    	this.items.className = "rich-fileupload-list-overflow " + this.classes.UPLOAD_LIST.ENABLED;
    	for (var i = 0; i < this.entries.length; i++) {
    		var entry = this.entries[i];
    		entry.entryElement.rows[0].cells[0].className = "rich-fileupload-font rich-fileupload-name rich-fileupload-table-td " + this.classes.FILE_ENTRY.ENABLED;
    		entry.controlArea.firstChild.className = "rich-fileupload-anc " + this.classes.FILE_ENTRY_CONTROL.ENABLED;
    	}
    	this.processButtons();
    },

	_endUpload: function() {
		if (this.options['autoclear']) {
			this.clear(this.activeEntry);
		}
		this.activeEntry = null;
		//this.resetFrame();
	},

	updateViewState: function (state) {
		if (!state) return;
		var form = this.getForm();
		var viewStateE  = form['javax.faces.ViewState'];
		if (viewStateE) {
			viewStateE.value = state;
		}
	},

	_updateEntriesState: function() {
		var l = this.entries.length;

		var oldState;
		var newState;

		if (this.runUpload) {
			oldState = FileUploadEntry.INITIALIZED;
			newState = FileUploadEntry.READY;
		} else {
			oldState = FileUploadEntry.READY;
			newState = FileUploadEntry.INITIALIZED;
		}

		for (var i = 0; i < l; i++) {
			var entry = this.entries[i];
			if (entry.state == oldState) {
				entry.setState(newState);
			}
		}
	},

	notifyStateChange: function(entry, oldState) {
		var newState = entry.state;

		if (newState == FileUploadEntry.UPLOAD_SUCCESS || newState == FileUploadEntry.UPLOAD_SIZE_ERROR) {
			//todo clear completed

			if (newState == FileUploadEntry.UPLOAD_SIZE_ERROR) {
				if(this.events.onsizerejected) {
					this.element.fire("rich:onsizerejected", { entry : entry});
				}
			}

			if (newState == FileUploadEntry.UPLOAD_SUCCESS) {
				this.uploadedCount++;
			}

			this._endUpload();

			var entry = this._selectEntryForUpload();
			if (entry) {
				if (this.runUpload) {
					entry.upload();
				}
			} else {
				//we've uploaded all files sucessfully
				//but this.runUpload can be false if upload
				//has been requested to stop by user
				this.setupAutoUpload();
				if(this.events.onuploadcomplete) {
					this.element.fire("rich:onuploadcomplete", {});
				}
				this.resetFrame();
			}

			this._updateEntriesState();

		} else if (newState == FileUploadEntry.UPLOAD_CANCELED ||
				newState == FileUploadEntry.UPLOAD_TRANSFER_ERROR ||
				newState == FileUploadEntry.UPLOAD_SERVER_ERROR) {


			this._endUpload();

			this.runUpload = false;

			this._updateEntriesState();

			if(newState == FileUploadEntry.UPLOAD_CANCELED) {
				if(this.events.onuploadcanceled) {
					this.element.fire("rich:onuploadcanceled", {entry : entry});
				}
			} else {
				if(this.events.onerror) {
					this.element.fire("rich:onerror", {entry : entry});
				}
			}

		} else if (newState == FileUploadEntry.UPLOAD_IN_PROGRESS) {

			this.activeEntry = entry;

			this._updateEntriesState();

		}
		this.processButtons();
	},

	getForm: function () {
		return this.form;
	},

	_getForm: function () {
		var parentForm = this.element;
		while (parentForm.tagName && parentForm.tagName.toLowerCase() != 'form') {
			parentForm = parentForm.firstChild;
		}
		return parentForm;
	},

	submitForm: function(entry) {
		var parentForm = this.getForm();

		if (!parentForm) {
			throw "No parent form found!";
		}
		var formUpload = !(entry && entry instanceof FileUploadEntry);

		var oldTarget = Richfaces.readAttribute(parentForm, "target");
		var oldEnctype = Richfaces.readAttribute(parentForm, "enctype");
		var oldEncoding = Richfaces.readAttribute(parentForm, "encoding");
		var oldAction = Richfaces.readAttribute(parentForm, "action");

		try {
			if (!formUpload) {
				entry.uid = Math.random().toString();

				Richfaces.writeAttribute(parentForm, "encoding", "multipart/form-data");
				Richfaces.writeAttribute(parentForm, "enctype", "multipart/form-data");

				Richfaces.writeAttribute(parentForm, "action",
						this.actionUrl + (/\?/.test(this.actionUrl) ? '&_richfaces_upload_uid' : '?_richfaces_upload_uid') + '=' + encodeURI(entry.uid) + "&" + this.id + "=" + this.id + "&_richfaces_upload_file_indicator=true"+"&AJAXREQUEST="+this.progressBar.containerId);

				Richfaces.writeAttribute(parentForm, "target", this.id + "_iframe");

				var inputs = parentForm.elements;
				var entryInput = entry.fileInput;

				entryInput.name = this.id + ":file";
				entryInput.disabled = false;

				var l = inputs.length;
				for (var i = 0; i < l; i++) {
					var input = inputs[i];
					if (input != entryInput) {
							if ('file' == input.type) {
								input._disabled = input.disabled;
								input.disabled = true;
							}
					}
				}
			}else {
				this.beforeSubmit();
			}

			if (!parentForm.onsubmit || parentForm.onsubmit()) {
				if (!formUpload) {
					this.watcher = new LoadWatcher(this.iframe, function(newState) {
						this.finishProgressBar();
						this.setState(newState);
						// call onFileUploadComplete event handler
						if(this.uploadObject.events.onfileuploadcomplete) {
							this.uploadObject.element.fire("rich:onfileuploadcomplete", { state: newState });
						}
					}.bind(entry),
					function (state) {
						this.updateViewState(state);
					}.bind(this));
				}

				_JSFFormSubmit(null, parentForm.id, null, this.parameters);
			}

			if (!formUpload) {
				for (var i = 0; i < l; i++) {
					var input = inputs[i];
						if ('file' == input.type) {
							input.disabled = input._disabled;
							input._disabled = undefined;
						}
				}
			}else {
				this._enableEntries(true);
			}
		} finally {
			Richfaces.writeAttribute(parentForm, "action", oldAction);
			Richfaces.writeAttribute(parentForm, "encoding", oldEncoding);
			Richfaces.writeAttribute(parentForm, "enctype", oldEnctype);
			if (formUpload) {
				this.currentInput.disabled = true;
			}else {
				Richfaces.writeAttribute(parentForm, "target", oldTarget);
				this.getFileSizeScript(entry.uid, this.formId)
			}
		}
	},

	_enableEntries: function (b) {
		for (var i = 0; i < this.entries.length; i++) {
				var e = this.entries[i];
				e.fileInput.name = (!b) ? (this.id + ":file" + i) : '';
				e.fileInput.disabled = b;
			}
	},

	beforeSubmit: function () {
		var f = this.getForm();
		if (!f) {
			throw "No parent form found!";
		}
		Richfaces.writeAttribute(f, "encoding", "multipart/form-data");
		Richfaces.writeAttribute(f, "enctype", "multipart/form-data");
		Richfaces.writeAttribute(f, "action",
				this.actionUrl + (/\?/.test(this.actionUrl) ? '&_richfaces_upload_uid' : '?_richfaces_upload_uid') + '=_richfaces_form_upload' + "&" + this.id + "=" + this.id + "&_richfaces_upload_file_indicator=true");

		this.currentInput.disabled = true;
		this._enableEntries(false);
		return true;
	},

	initFlashModule: function ()
	{
		var allowFlash = this.options.allowFlash;
		this.oldDisabled = this.disabled;
		if (allowFlash=="auto" || allowFlash=="true")
		{
			var httpsSuffix = window.location.href.substr(0,5).toLowerCase()=="https" ? "s" : "";
			var div = document.createElement('div');
			div.innerHTML = '<a href="http'+httpsSuffix+'://www.adobe.com/go/getflashplayer"><img style="border:0px; margin:2px" src="http'+httpsSuffix+'://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" /></a>';
			var oid = this.id+":flashContainer";
			var object = document.getElementById(oid);
			this.isFlash = swfobject.hasFlashPlayerVersion("9.0.28");
			if (this.isFlash)
			{
				this.disable();
				var flashvars = {fileUploadId:this.id};
				var params = {allowscriptaccess:true, menu: "false", wmode: "transparent", salign: "TL", scale: "noscale"};

				var attributes = {style:"position:absolute; top:0px; left:0px;"};
				swfobject.embedSWF(this.options.flashComponentUrl, oid, "100%", "100%", "9.0.28", false, flashvars, params, attributes);

				this.currentInput.parentNode.innerHTML = '<input type="text" style="cursor: pointer; right: 0px; top: 0px; font-size: 10em; position: absolute; padding: 0px" class="rich-fileupload-hidden" id="'+this.id+':file" name="'+this.id+':file"></input>'
				this.currentInput = $(this.id + ":file");

				this.currentInput.style.display = "none";
				this.currentInput.onmousedown=(function (){this.createFrame();}).bind(this);
			} else if (allowFlash=="true")
			{
				this.items.appendChild(div);
				this.disable();
			}
		}
	},

	_flashClearFocus: function()
	{
		//this.flashComponent.style.display = "none";
		//this.flashComponent.style.display = "";
	},

	_flashSetComponent: function() {
		var flashId = this.id+":flashContainer";
		this.flashComponent = (document[flashId]) ? document[flashId] : (window[flashId] ? window[flashId] : $(flashId));
		this.flashComponent.setProperties({
											acceptedTypes: this.acceptedTypes,
			 								noDuplicate: this.options.noDuplicate,
			 								maxFiles: this.maxFileBatchSize });
		if (Richfaces.browser.isIE)
		{
			this.flashComponent.style.width = this.currentInput.parentNode.style.width;
			this.flashComponent.style.height = this.currentInput.parentNode.style.height;
		}
		if (!this.oldDisabled) this.enable();
	},

	_flashDisableAdd: function (isDisabled)
	{
		if (this.flashComponent)
			this.flashComponent.disableAdd(isDisabled);
	},

	_flashAdd: function(files) {
		if (this.disabled) return;

		var filesArray = [];
		for (var i=this.entries.length; i<files.length;i++)
		{
			var file = files[i];
			this.currentInput.value = files[i].name;
			this.createFrame();
			var newEntry = new FileUploadEntry(this.currentInput, this, file.size, file.type, file.creator, file.creationDate, file.modificationDate);
			this.entries.push(newEntry);
			filesArray.push(newEntry);


			if (this.runUpload) {
				newEntry.setState(FileUploadEntry.READY);
			} else {
				newEntry.setState(FileUploadEntry.INITIALIZED);
			}

			var newUpload = this.currentInput.cloneNode(true);
			this.currentInput.style.cssText = "position: absolute; right: 0px; top: 0px; display: none; visibility: hidden;";
			newUpload.id = this.id + ":file" + (this.idCounter++);
			newUpload.value = '';
			this.currentInput.parentNode.appendChild(newUpload);
			this.currentInput = newUpload;
		}

		if (this.events.onadd) {
			this.element.fire("rich:onadd", { entries : filesArray });
		}

		if (this.runUpload) {
			this.upload();
		}
	},

	_flashRemoveFile: function(index)
	{
		//this.flashComponent.removeFile(index);
		this.uploadIndex = this.flashComponent.removeFile(index);
	},

	_flashFireEvent: function(eventName, object)
	{
		if (this.events[eventName])
		{
			this.element.fire("rich:"+eventName, object);
		}
	},

	_flashGetActionUrl: function (url, entry) {
		var getParams = "_richfaces_upload_uid="+ encodeURI(entry.uid) + "&" + this.id + "=" + this.id + "&_richfaces_upload_file_indicator=true&_richfaces_size="+entry.size+"&_richfaces_send_http_error=true";
		if (/\?/.test(url)) {
			var i = url.indexOf("?");
			url = url.substring(0, i) + ";jsessionid=" + this.sessionId + url.substring(i) + "&" + getParams;
		} else {
			url = url + ";jsessionid=" + this.sessionId + "?" + getParams;
		}
		return url;
	},

	_flashGetPostParams: function () {
		var query = new A4J.Query(this.progressBar.containerId, this.form);
		if (query) {
			query.appendFormControls();
			var qStr = query.getQueryString();
			if (this.parameters) {
				qStr = (/&$/.test(qStr)) ? qStr : qStr + "&";
				for (var p in this.parameters) {
					qStr = qStr + p + "=" + this.parameters[p] + "&";
				}
			}
			return qStr;
		}
		return '';
	},

	_flashSubmitForm: function(entry) {

		entry.uid = encodeURIComponent(Math.random().toString());
		var action = this._flashGetActionUrl(this.actionUrl, entry);
		var size = this.flashComponent.uploadFile(this.uploadIndex, action, this._flashGetPostParams());
		if (this.labelMarkup) {
			this.progressData = new ProgressData(size);
		}

	},

	_flashStop: function() {
		if (this.uploadIndex>=0) {
			this.flashComponent.cancelUploadFile(this.uploadIndex);
			//this.activeEntry.stop();
			this._flashError(FileUploadEntry.UPLOAD_CANCELED);
		}
	},

	_flashOnProgress: function (bytesLoaded, bytesTotal)
	{
		var entry = this.entries[this.uploadIndex];
		if (entry) {
			var p = bytesLoaded*100/bytesTotal;
			this.progressBar.setValue(p);
			if (entry.state == FileUploadEntry.UPLOAD_IN_PROGRESS) {
				var content = this.labelMarkup.invoke('getContent', this.progressData.getContext(p)).join('');
				entry.statusLabel.innerHTML = content;
			}
		}
	},

	_flashOnComplete: function () {
		this.finishProgressBar();
		this._flashSetEntryState(this.uploadIndex, (this.upload_stopped ? FileUploadEntry.UPLOAD_CANCELED : FileUploadEntry.UPLOAD_SUCCESS));
		this.upload_stopped=false;
	},

	_flashHTTPError: function (httpErrorNumber) {
		if (httpErrorNumber==413) this._flashError(FileUploadEntry.UPLOAD_SIZE_ERROR);
		else this._flashError();
	},

	_flashIOError: function () {
		this._flashError();
	},

	_flashOnSecurityError: function (errorString) {
		this._flashError();
	},

	_flashError: function (error)
	{
		this.finishProgressBar();
		this._flashSetEntryState(this.uploadIndex, (error==undefined ? FileUploadEntry.UPLOAD_TRANSFER_ERROR : error));
	},

	_flashSetEntryState: function (entryIndex, state) {
		var entry = this.entries[entryIndex];
		if (entry) {
			entry.setState(state);
			// call onFileUploadComplete event handler
			if(this.events.onfileuploadcomplete) {
				this.element.fire("rich:onfileuploadcomplete", { state: state });
			}
		}
	}

});

FlashFileUpload = {
	getComponent: function(componentId) {
		return $(componentId).component;
	},
	ASTrace: function (msg)
	{
		console.log(msg);
	},
	ASAlert: function (msg)
	{
		alert(msg);
	}
}