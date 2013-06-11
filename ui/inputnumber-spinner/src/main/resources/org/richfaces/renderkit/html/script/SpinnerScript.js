if (!window.Richfaces) window.Richfaces = {};


Richfaces.Spinner = Class.create();
Richfaces.Spinner.prototype = {

	initialize: function( containers, options, data ,events, images) {
		this.content	= $ (containers.edit);
		this.controls	= $ (containers.buttons);
		this.fie		= $ (containers.forIE);
		this.ch			= options.chameleon;
		this.items		= new Array();
		this.table		= $ (containers.buttons.substr(containers.buttons.indexOf("buttons")+7));
		if (RichFaces.navigatorType() == RichFaces.FF ||
				RichFaces.navigatorType() == RichFaces.NETSCAPE) {
			if (!this.fie)
			this.table.style.display = "-moz-inline-box";
		}
		this.options	= options;
		if (!options.disabled){
				this.buttonUp = null;
				this.buttonDown = null;
		}
		
		this.cycled		= options.cycled;
		this.edited		= options.edited;
		var edit = this._getDirectChildrenByTag(this.content,'INPUT')[0];
		this.upClick	= new Function(events.onup + ";return true;").bindAsEventListener(edit);
		this.downClick	= new Function(events.ondown + ";return true;").bindAsEventListener(edit);
		this.error		= new Function("event","clientErrorMessage",events.onerr + ";return true;").bind(edit);

		this.data		= data;
		this.max		= null;
		this.min		= null;
		this.delta		= null;
		this.required = options.required;
		this._attachBehaviors();
		this._load();
		
	},

	switchItems: function( e ) {
		var editValue = this.controls.edit.value;
		if (e == 'up'){
			if ("" == editValue) {
				this.controls.edit.value = this.min;
			} else {
				editValue -= this.delta*-1;
				editValue = this.roundFloat(editValue);
				if ( editValue <= this.max && editValue >= this.min){
					this.controls.edit.value = editValue;
				} else {
					if (this.cycled){
						if (this.delta>0){
							this.controls.edit.value = this.min;
						} else {
							this.controls.edit.value = this.max;
						}
					} else {
						this.error(e,this.options.clientErrorMsg);
						this.controls.fireEditEvent("error");
						this.controls.edit.value = this.max;
						return true;
					}
				}
			}
		} else {
			if ("" == editValue) {
				this.controls.edit.value = this.max;
			} else {
				editValue -= this.delta;
				editValue = this.roundFloat(editValue);
				if (editValue >= this.min && editValue <= this.max){
					this.controls.edit.value = editValue;
				} else {
					if (this.cycled){
						if (this.delta<0){
							this.controls.edit.value = this.min;
						} else {
							this.controls.edit.value = this.max;
						}
					} else {
						this.error(e,this.options.clientErrorMsg);
						this.controls.fireEditEvent("error");
						this.controls.edit.value = this.min;
						return true;
					}
				}
			}
		}
		return false;
	},

	roundFloat: function(x){
		var str = this.delta.toString();
		var power = 0;
		if (!/\./.test(str)) {
			if (this.delta >= 1) {
				return x;
			}
			if (/e/.test(str)) {
				power = str.split("-")[1];
			}
		} else {
			power = str.length - str.indexOf(".") - 1;
		}
		var ret = x.toFixed(power);
		return ret;
	},

	_load: function(){
		this.controls.edit.readOnly = this.edited ? "" : "readOnly";
		if (this.options.disabled) {
			this.controls.edit.readOnly = "readOnly";
			Element.setStyle(this.controls.edit, {color: "gray"});
		} else {
			Element.setStyle(this.controls.edit, {color: ""});
		}
	},

	_attachBehaviors: function(){
		this.max	= this.data.max;
		this.min	= this.data.min;
		this.delta	= this.data.delta;
		
		var tbody		= this._getDirectChildrenByTag(this.controls,'TBODY')[0];
		var controls	= this._getDirectChildrenByTag(tbody,'TR');
		var buttonUp	= this._getDirectChildrenByTag(controls[0],'TD')[0];
		var buttonDown	= this._getDirectChildrenByTag(controls[1],'TD')[0];
		var edit		= this._getDirectChildrenByTag(this.content,'INPUT')[0];
		if (this.ch=="false"){
			this.buttonUp = this._getDirectChildrenByTag(buttonUp,'INPUT')[0];
			this.buttonDown = this._getDirectChildrenByTag(buttonDown,'INPUT')[0];
			var upImg		= null;
			var downImg		= null;
		} else {
			var upImg		= this._getDirectChildrenByTag(buttonUp,'INPUT')[0];
			var downImg		= this._getDirectChildrenByTag(buttonDown,'INPUT')[0];

		}
		this.controls 	= new Richfaces.Spinner.Controls( this, {button:buttonUp,img:upImg}, {button:buttonDown,img:downImg}, edit );
	}, 

	_getDirectChildrenByTag: function( e, tagName ) {

		var kids = new Array();
		var allKids = e.childNodes;
		for( var i = 0 ; i < allKids.length ; i++ ){
			if ( allKids[i] && allKids[i].tagName && allKids[i].tagName.toUpperCase() == tagName.toUpperCase() ){
				kids.push(allKids[i]);
			}
		}
		return kids;

	},

	_removePx: function(e){
		return e.substring(0,e.indexOf('px'));
	}
};

Richfaces.Spinner.Controls	= Class.create();
Richfaces.Spinner.Controls.prototype = {

	initialize: function( spinner, up, down, edit ) {
		this.spinner= spinner;
		this.up	= $ (up.button);
		this.upimg	= $ (up.img);
		this.down	= $ (down.button);
		this.downimg= $ (down.img);
		
		this.mousedown = false;
		this.onUpButton = false;
		this.onDownButton = false;
		
		this.fie = this.spinner.fie;
				
		this.edit	= $ (edit);
		this.originalColor = edit.style.color;
		this.prevEditValue = (this.edit.value || !this.spinner.required) ? this.edit.value : this.spinner.min;
		this.edit.value = this.prevEditValue;
		this.previousMU = window.document.onmouseup;
		this.previousMM = window.document.onmousemove;
		if (!spinner.options.disabled){
			this._attachBehaviors();
			this.edit.style.color = this.originalColor;
		} else {
			if (!this.fie){
				this.edit.style.color = "gray";
			}	
		}
	},

	upClick: function(e){
	   	if (e.preventDefault) {
         e.preventDefault();	;
	    }
		var isError = this.spinner.switchItems('up');
		this.spinner.upClick();
		if(!isError){
			window.document.onmouseup		= this.mouseUp.bindAsEventListener(this);
			this.mousedown=true;
			this.timer = setTimeout(this.continueUpClick.bind(this), 750);
		}	
	},

	downClick: function(e){
	   	if (e.preventDefault) {
         e.preventDefault();	;
	    }
		var isError = this.spinner.switchItems('down');
		this.spinner.downClick();
		if(!isError){
			window.document.onmouseup		= this.mouseUp.bindAsEventListener(this);
			this.mousedown=true;
			this.timer = setTimeout(this.continueDownClick.bind(this), 750);
		}	
	},

	continueUpClick: function(){
		if (!this.mousedown) return;
		window.document.onmousemove = this.mouseMoveUp.bindAsEventListener(this);
		this.spinner.switchItems('up');
		if ( this.timer ){
			clearTimeout(this.timer);
		}
		this.timer = setTimeout(this.continueUpClick.bind(this), 100);
	},

	continueDownClick: function(){
		if (!this.mousedown) return;
		window.document.onmousemove = this.mouseMoveDown.bindAsEventListener(this);
		this.spinner.switchItems('down');
		if ( this.timer ){
			clearTimeout(this.timer);
		}
		this.timer = setTimeout(this.continueDownClick.bind(this), 100);
	},

	mouseUp: function(e){	    
		clearTimeout(this.timer);
		if (this.spinner.ch == "true"){
			if (!this.onUpButton)
				this.upUp();
			if (!this.onDownButton)
			this.downUp();
		}
		if (this.mousedown){
			this.mousedown=false;
			this.fireEditEvent("change");
		}
	},	

	mouseMoveDown: function(e){
	   	if (e.preventDefault) {
         e.preventDefault();
	    }		    
	    if ((this.downimg!=Event.element(e)) ){       
		window.document.onmousemove = this.previousMM;
		clearTimeout(this.timer);
		this.mousedown=false;
		if (this.spinner.ch == "true"){
			if (!this.onUpButton)
				this.upUp();
			if (!this.onDownButton)
			this.downUp();
		}
		this.fireEditEvent("change");
		};
	},	

	mouseMoveUp: function(e){
	   	if (e.preventDefault) {
         e.preventDefault();
	    }
	    if (this.upimg!=Event.element(e)){
		window.document.onmousemove = this.previousMM;
		clearTimeout(this.timer);
		this.mousedown=false;
		if (this.spinner.ch == "true"){
			if (!this.onUpButton)
				this.upUp();
			if (!this.onDownButton)
			this.downUp();
		}
		this.fireEditEvent("change");
		};
	},	

	inputChange: function(e) {
		if ((this.edit.value == "" && this.spinner.required) || isNaN(Number(this.edit.value))){
			this.edit.value = this.prevEditValue;
		} else if ("" != this.edit.value) {
			if (this.edit.value > this.spinner.max){
				this.edit.value = this.spinner.max;
			} else if (this.edit.value < this.spinner.min) {
				this.edit.value = this.spinner.min;
			}
		}
		if ("" != this.edit.value)
			this.prevEditValue = this.edit.value;		
		if (this.eventEditOnChange)
			this.eventEditOnChange();

	},

	editChange: function(e) {
		if ((this.edit.value < this.spinner.max) && (this.edit.value > this.spinner.min) && !isNaN(Number(this.edit.value)) && this.edit.value != ""){	
			this.prevEditValue = this.edit.value;		
		}
		if (e.keyCode == 13){
			if (this.spinner.required || "" != this.edit.value)
				this.edit.value = this.getValidValue(this.edit.value);
			if (this.edit.form) {
				this.edit.form.submit();
			}
		}
	},
	
	getValidValue : function(value){
		if (isNaN(value) || value == "")
			return this.prevEditValue;
		if ( value > this.spinner.max)
			return this.spinner.max;
		if (value < this.spinner.min)
			return this.spinner.min;
		return value;
	},

	drag: function() {
	 return false;
	},
	
	_attachBehaviors: function(){
		this.up.onmousedown		= this.upClick.bindAsEventListener(this);
		this.down.onmousedown	= this.downClick.bindAsEventListener(this);
		this.up.onmouseup		= this.mouseUp.bindAsEventListener(this);
		this.down.onmouseup		= this.mouseUp.bindAsEventListener(this);
		this.edit.onkeydown	= this.editChange.bindAsEventListener(this);
		this.eventInputChange= this.inputChange.bindAsEventListener(this);
		if (this.edit.onchange){
			this.eventEditOnChange = this.edit.onchange;
		}
		this.edit.onchange = this.eventInputChange.bindAsEventListener(this.edit);
	},
	
	fireEditEvent: function(e){
		if( document.createEvent ) {
			var evObj = document.createEvent('HTMLEvents');
			evObj.initEvent( e, true, false );
			this.edit.dispatchEvent(evObj);
		} else if( document.createEventObject ) {
			this.edit.fireEvent('on' + e);
		}
	}
};
