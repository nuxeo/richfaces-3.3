if (!window.Richfaces) window.Richfaces = {};
tinymce.richfaces = Richfaces;
Richfaces.Editor = {};
Richfaces.Editor.REGEXP_CSS = /(\/tiny_mce\/(?:themes|plugins)\/[\w\.\\\/]*[\w\.]+\.)(c|C)[sS]{2}$/;

RichEditor = Class.create();
Object.extend(RichEditor.prototype, {
    initialize: function(id, params, tinyparams) {

    	this.id = id;
    	this.editorTextAreaId = this.id +'TextArea';
    	this.params = params;
		this.tinyparams = tinyparams;
		
		var obj = $(this.id);
		obj.component = this;
		obj.richfacesComponent="richfaces:editor";
    	this["rich:destructor"] = "destruct";
    	
    	Richfaces.Editor.extScriptSuffix = params.extScriptSuffix;
    	Richfaces.Editor.extCssSuffix = params.extCssSuffix;
    	
    	this.synchronizeConfiguration();
    	this.setDialogType();
    	this.redefineCallbacks();
    	
    	this.tinyMCE_editor = null;
    	    	
    	this.tinyparams.mode = 'exact';
    	this.tinyparams.elements = this.editorTextAreaId;
    	this.tinyparams.editor_selector = null;
    	this.tinyparams.editor_deselector = null;
    	
    	tinyMCE.init(this.tinyparams);
        	
        this.onBeforeAjaxListener = new A4J.AJAX.AjaxListener("onbeforeajax", this.onBeforeAjax.bind(this));
		A4J.AJAX.AddListener(this.onBeforeAjaxListener);
        
    },
    destruct: function(isAjax) { 
    	A4J.AJAX.removeListener(this.onBeforeAjaxListener);
		
		if(isAjax){
			this.tinyMCE_editor.remove();
		}
		this.onInitInstanceCallbackFunction = null;
		this.onInitCallbackFunction = null;
		this.onChangeCallbackFunction = null;
		this.onSaveCallbackFunction = null;
		this.onSetupCallbackFunction = null;
		this.tinyMCE_editor = null;
	},
	
	redefineCallbacks: function() {
		this.onInitInstanceCallbackFunction =  this.tinyparams.init_instance_callback;
		this.tinyparams.init_instance_callback = this.onInitInstanceCallback.bind(this);
		
		if(this.tinyparams.onchange_callback && typeof this.tinyparams.onchange_callback != 'string'){
    		this.onChangeCallbackFunction =  this.tinyparams.onchange_callback;
    		this.tinyparams.onchange_callback = this.onChangeCallback.bind(this);
		}
		
		if(this.tinyparams.onchange_callback && typeof this.tinyparams.oninit != 'string'){
    		this.onInitCallbackFunction =  this.tinyparams.oninit;
    		this.tinyparams.oninit = this.onInitCallback.bind(this);
		}
    	
    	if(this.tinyparams.onchange_callback && typeof this.tinyparams.save_callback != 'string'){
    		this.onSaveCallbackFunction =  this.tinyparams.save_callback;
    		this.tinyparams.save_callback = this.onSaveCallback.bind(this);
    	}
    	
    	if(this.tinyparams.onchange_callback && typeof this.tinyparams.setup != 'string'){
    		this.onSetupCallbackFunction =  this.tinyparams.setup;
    		this.tinyparams.setup = this.onSetupCallback.bind(this);
    	}
	},
	
	onBeforeAjax: function() {
        this.tinyMCE_editor.save();
	},
	
	onInitInstanceCallback: function(inst) {		
        this.tinyMCE_editor = tinyMCE.get(this.editorTextAreaId);
        if (this.onInitInstanceCallbackFunction) this.onInitInstanceCallbackFunction.call(this, inst);
	},
	
	onChangeCallback: function(inst) {		        
        this.invokeEvent("Change", $A(arguments));
	},
	
	onInitCallback: function() {		        
        this.invokeEvent("Init", $A(arguments));
	},
	
	onSaveCallback: function(element_id, html, body) {
		if(this.onSaveCallbackFunction){	        
        	return this.invokeEvent("Save", $A(arguments));
		}else{
			return html;
		}
	},
	
	onSetupCallback: function(ed) {		        
        this.invokeEvent("Setup", $A(arguments));
	},
	
	synchronizeConfiguration: function(){		
		if(this.params.useSeamText){
			//this.tinyparams.plugins = Richfaces.Editor.SeamTextConfiguration.plugins;
			this.tinyparams.convert_fonts_to_spans = false;
			this.tinyparams.inline_styles = false;
			this.tinyparams.verify_html = false;
		}
	},
	
	invokeEvent: function(eventName, args){
		callback = this["on"+eventName+"CallbackFunction"];
		if (!callback) return;
		var eventObj;
		var result;
		
		if( document.createEventObject ) {
			eventObj = document.createEventObject();
		} else if( document.createEvent ) {
			eventObj = document.createEvent('Events');
			eventObj.initEvent( eventName, true, false );
		}
		
		eventObj.rich = {component:this, tinyMceInstance: this.tinyMCE_editor};		
		args.unshift(eventObj);
		
		try {
			result = callback.apply(this, args);
		} catch (e) { 
			LOG.warn("Exception: " + e.Message + "\n[on " + eventName + " ]"); 
		}
		return result;
	},
	
	setDialogType: function(){
		var plugins = this.tinyparams.plugins;
		if(this.tinyparams.dialog_type && this.tinyparams.dialog_type == 'modal'){
			if(plugins && plugins.length > 0){
				if(plugins.indexOf('inlinepopups') == -1){
					plugins += ',inlinepopups';
				}
			}else{
				plugins = 'inlinepopups';
			}
		}else if(this.tinyparams.dialog_type && this.tinyparams.dialog_type == 'window'){
			if(plugins && plugins.length > 0 && plugins.indexOf('inlinepopups') != -1){
				if(plugins.indexOf('inlinepopups') != -1){
					var wordIndex = plugins.indexOf('inlinepopups');
					var firstCommaIndex = -1;
					var secondCommaIndex = -1;
					if(wordIndex > 0){
						firstCommaIndex = plugins.lastIndexOf(',', wordIndex);
					}
					if(wordIndex < plugins.length - 1){
						secondCommaIndex = plugins.indexOf(',', wordIndex);
					}
					
					if(firstCommaIndex != -1 && secondCommaIndex != -1){
						plugins = plugins.replace(plugins.substring(firstCommaIndex, secondCommaIndex + 1), ",");
					}else if(firstCommaIndex != -1){
						plugins = plugins.replace(plugins.substring(firstCommaIndex, wordIndex + 'inlinepopups'.length), "");
					}else if(secondCommaIndex != -1){
						plugins = plugins.replace(plugins.substring(wordIndex, secondCommaIndex + 1), "");
					}
					
				}
			}
		}
		this.tinyparams.plugins = plugins;
	}
});
    