if(!window.Richfaces) window.Richfaces = {};
Richfaces.Slider = Class.create();
Richfaces.Slider.prototype = {
	initialize: function(handle, track, tip, table, handleSelectedClass, increaseSelectedClass, decreaseSelectedClass, options) {
		var slider = this;
		this.handle = $( handle );
		this.tip	= $( tip );
		this.track	= $( track );
		this.mainTable	= $( table );
		
		//this.optionsInputId = options.optionsInputId;
		
		//this.optionInput = $(this.optionsInputId) || document.getElementById(this.optionsInputId);
				
		this.input	= $( options.inputId ) || document.getElementsByName(options.inputId)[0];
		if(options.showArrows){
			this.arrowInc = $( options.arrowInc ) || document.getElementsByName(options.arrowInc)[0];
	        this.arrowDec = $( options.arrowDec ) || document.getElementsByName(options.arrowDec)[0];
	        this.tipArrowInc = $( options.tipArrowInc ) || document.getElementsByName(options.tipArrowInc)[0];
	        this.tipArrowDec = $( options.tipArrowDec ) || document.getElementsByName(options.tipArrowDec)[0];
		}
		this.options= options || {};
		
		this.orientation = this.options.orientation;
		
		this.classes = {};
		this.classes.arrow = "dr-insldr-handler rich-inslider-handler";
		this.classes.arrowSelected = "dr-insldr-handler-sel rich-inslider-handler-selected";
		this.classes.temp = this.handle.className;
		this.classes.base = " " + this.trim(this.classes.temp.replace("dr-insldr-handler rich-inslider-handler",""));
		
		if(this.orientation=="vertical"){
			  this.classes.arrow = "dr-insldr-handler-vertical rich-inslider-handler-vertical";
			  this.classes.arrowSelected = "dr-insldr-handler-sel-vertical rich-inslider-handler-selected-vertical";
			  this.classes.base = " " + this.trim(this.classes.temp.replace("dr-insldr-handler-vertical rich-inslider-handler-vertical",""));
			}
		
		this.classes.handleSelected = " " + handleSelectedClass;

		this.table = this.findTableForTrack(this.track);
		
		this.input.value = this.options.sliderValue;
		this.prevInputValue = this.input.value;
		this.graggedImageOn = false;
		this.range	 = this.options.range || $R(0,1);
		this.value	 = 0;
		this.minimum = this.options.minimum || this.range.start;
		this.maximum = this.options.maximum || this.range.end;
		this.digCount = 0;
		this.delay = this.options.delay;
		if("" == this.input.value){
			  this.input.value = this.options.minimum;
		}

		this.step = this.options.step;
		if ( (this.step+"").indexOf(".")!=-1 ){
			var stepStr = (this.step+"");
			this.digCount = (stepStr.substring(stepStr.indexOf(".")+1,stepStr.length)).length;
		}
		this.availableValues = this.calculateAvailableValues();

		this.tip.maxlength = (this.maximum + "").length + (this.digCount != 0 ? this.digCount + 1 : 0);
		if(this.options.showArrows){
			this.tipArrowInc.maxlength = this.tip.maxlength;
	        this.tipArrowDec.maxlength = this.tip.maxlength;
		}
		
		this.handleLength = 9;

		this.active	 = false;
		this.dragging = false;
		this.editInFocus = false;

		this.disabled = this.options.disabled ? true : false;

		var tr = this.track.childNodes[0];

		this.prevMouseUp = window.document.onmouseup;
		this.prevMouseMove = window.document.onmousemove;

		this.documentBodyOload	= this.load.bindAsEventListener(this);
		Event.observe(window, "load", this.documentBodyOload);

		this.eventWindowResized = this.windowResized.bindAsEventListener(this);
		Event.observe(window, "resize", this.eventWindowResized);
		
		this.period = "";

		if(!this.options.disabled){
			//this.eventMouseUp		= this.endDrag.bindAsEventListener(this);
			this.eventMouseUp		= this.processMouseUp.bindAsEventListener(this);
			this.eventMouseMove		= this.update.bindAsEventListener(this);
			this.eventMouseDown		= this.startDrag.bindAsEventListener(this);
			this.eventEditFocus		= this.editFocus.bindAsEventListener(this);
			this.eventEditBlur		= this.editBlur.bindAsEventListener(this);
			this.eventEditChange	= this.editChange.bindAsEventListener(this);
			this.eventEditValidate	= this.inputValidate.bindAsEventListener(this);
			this.eventInputChange	= this.inputChange.bindAsEventListener(this);
			this.eventWindowMouseOut= this.windowMouseOut.bindAsEventListener(this);
			this.eventIncrease      = this.increase.bindAsEventListener(this);
			this.eventDecrease      = this.decrease.bindAsEventListener(this);
			this.eventIncreaseDown  = this.increaseDown.bindAsEventListener(this);
            this.eventDecreaseDown  = this.decreaseDown.bindAsEventListener(this);
			this.eventIncreaseUp    = this.increaseUp.bindAsEventListener(this);
            this.eventDecreaseUp    = this.decreaseUp.bindAsEventListener(this);

			if (this.options.onerr) {
				this.eventError = new Function("event","clientErrorMessage",this.options.onerr);
			}
			
			if (this.options.onchange != ""){
				this.eventChanged = new Function("event",this.options.onchange).bindAsEventListener(this);
			}
				
			Event.observe(this.track, "mousedown", this.eventMouseDown);
			Event.observe(tr, "mousedown", this.eventMouseDown);
			Event.observe(this.input, "keydown", this.eventEditValidate);
			Event.observe(this.input, "keyup", this.eventEditChange);
			Event.observe(this.input, "focus", this.eventEditFocus);
			Event.observe(this.input, "blur", this.eventEditBlur);
			if(this.input.onchange){
				this.eventInputOnChange = this.input.onchange.bindAsEventListener(this.input);
				this.input.onchange = null;
			}
			Event.observe(this.input, "change", this.eventInputChange);
			if(this.options.showArrows){
				Event.observe(this.arrowInc, "mousedown", this.eventIncreaseDown);
				Event.observe(this.arrowDec, "mousedown", this.eventDecreaseDown);
				Event.observe(this.arrowInc, "mouseup", this.eventIncreaseUp);
	            Event.observe(this.arrowDec, "mouseup", this.eventDecreaseUp);
			}
		}
		this.initialized = true;

        this.setInitialValue(); 
        //Event.observe(window, "load", this.setInitialValue.bindAsEventListener(this)); //FIX RFA-190
		//Event.observe($(input), "propertychange", this.setInitialValue.bindAsEventListener(this));
			
		this.required = options.required;
		
		this.mainTable.component = this;
		this["rich:destructor"] = "destroy";
		
	},
	
	destroy: function ()
	{
		this.handle = null;
		this.tip = null;
		this.tipArrowInc = null;
		this.tipArrowDec = null;
		this.arrowInc = null;
        this.arrowDec = null;
		this.track = null;
		this.mainTable.component = null;
		this.mainTable = null;
		this.input = null;
		this.table = null;
		window.document.onmouseup = this.prevMouseUp;
		window.document.onmousemove = this.prevMouseMove;
		this.prevMouseUp = null;
		this.prevMouseMove = null;
		Event.stopObserving(window, "load", this.documentBodyOload);
		Event.stopObserving(window, "resize", this.eventWindowResized);
	},

	setInitialValue: function(){
		this.setValue(parseFloat(this.options.sliderValue || this.range.start));
        this.handle.style.visibility="visible";
		this.prevValue = this.value;
		this.valueChanged = false;
		if(this.options.showArrows){
			if(this.orientation=="vertical"){
	            this.tipArrowInc.style.left = (this.arrowInc.offsetWidth) + "px";
	            this.tipArrowDec.style.left = (this.arrowDec.offsetWidth) + "px";
	            //this.tipArrowDec.style.top = "-" + (this.arrowDec.offsetHeight) + "px";
	        } else {
	            this.tipArrowInc.style.top = "-" + (this.arrowInc.offsetHeight + 3) + "px";
	            this.tipArrowDec.style.top = "-" + (this.arrowDec.offsetHeight + 3) + "px";
	        }
		}
	},

   calculateAvailableValues : function(){
        var values = new Array();
        var value = this.roundFloat(this.minimum);
        var i = 0;
        while (value < this.maximum){
            values[i] = value;
            value = this.roundFloat(value + parseFloat(this.step));
            i++;
        }
        values[i] = this.roundFloat(this.maximum);

        return values;
    },

	roundFloat: function(x){
		if (!this.digCount)
			return Math.round(x);

		return parseFloat(Number(x).toFixed(this.digCount));
	},

	windowMouseOut : function(evt){
		var elt = null;
		if (evt.srcElement){
			elt = evt.toElement;
		} else {
			elt = evt.relatedTarget;
		}
		if (elt == null) {
			this.endDrag(evt);
		}
	},

	windowResized : function(evt){
		this.setValue(this.value);
	},

	findTableForTrack: function(elem) {
		var parent = elem.parentElement || elem.parentNode;
		if (parent.tagName.toUpperCase()=="TABLE") {
			return parent;
		} else {
			return this.findTableForTrack(parent);
		}
	},

    getNearestValue: function(value){
        var pos;
        pos = this.binsearch(this.availableValues, value);
        if (pos>0) {
        	var prevPos = pos-1;
        	if ( Math.abs(value-this.availableValues[prevPos])<
        		 Math.abs(this.availableValues[pos]-value) ) {
        		pos = prevPos;
        	}
        }
        return this.roundFloat(this.availableValues[pos]);
    },

    binsearch: function(v, t) {
        var i = 0;
        var j = v.length - 1;
        var k;
        while (i < j) {
            k = Math.round((i + j) / 2 + 0.5) - 1;
            if (t <= v[k]) j = k;
            else i = k + 1;
        }

        return i;
    },


    setValue: function(sliderValue){
	    if (isNaN(sliderValue)){
	     sliderValue=0;
	    }
		var newValue = this.getNearestValue(sliderValue);
		this.value = newValue;
				
		if ((!this.editInFocus || newValue==sliderValue) && (this.required || "" != this.input.value || this.updating)){
			this.input.value = this.value;
	//		this.optionInput.value = this.value;
			if(this.options.orientation == "vertical"){
                this.handle.style.top = this.translateToPx(this.value);
            } else {
			    this.handle.style.left = this.translateToPx(this.value);
			}
		} else 
		{
			if(this.options.orientation == "vertical"){
                this.handle.style.top = "-9px";
            } else {
			    this.handle.style.left = "0px";
			}
		}
		if (!this.tip.firstChild) {
			this.tip.appendChild(window.document.createTextNode(this.value));
		}
		if(this.options.showArrows){
			if (!this.tipArrowInc.firstChild) {
	            this.tipArrowInc.appendChild(window.document.createTextNode(this.value));
	        }
	        if (!this.tipArrowDec.firstChild) {
	            this.tipArrowDec.appendChild(window.document.createTextNode(this.value));
	        }
	        this.tipArrowInc.firstChild.nodeValue= this.value;
			this.tipArrowDec.firstChild.nodeValue= this.value;
		}
		
		this.tip.firstChild.nodeValue= this.value;		
		if(this.options.orientation == "vertical"){
		  this.tip.style.top = (this.handle.offsetTop) + "px";
		} else {
 		  this.tip.style.left = this.handle.offsetLeft /*+ this.handle.offsetWidth*/ + "px";
 		}
	},
	


	translateToPx: function(value) {
		if(this.options.orientation == "vertical"){
		    return Math.round(
	            ((this.maximumOffset() - this.handleLength)/(this.range.end-this.range.start)) *
	            (this.range.end - value) - this.maximumOffset()) + "px";
	    }
		return Math.round(
			((this.maximumOffset() - this.handleLength)/(this.range.end-this.range.start)) *
			(value - this.range.start)) + "px";
	},

	translateToValue: function(offset) {
		if(this.options.orientation == "vertical"){
		    return (this.range.end -((offset/(this.maximumOffset() - this.handleLength) *
	            (this.range.end-this.range.start))));
		}
		return ((offset/(this.maximumOffset() - this.handleLength) *
			(this.range.end-this.range.start)) + this.range.start);
	},

	maximumOffset: function(){
		if(this.options.orientation == "vertical"){
			   return this.removePx(this.track.style.height || this.track.offsetHeight || this.options.height);
			}
		return this.removePx(this.track.style.width || this.track.offsetWidth || this.options.width);
	},

	removePx: function(e){
		if ((e+"").indexOf("px")!=-1)
			return (e+"").substring(0,e.length-2);
		else
			return e;
	},

	startDrag: function(event) {
		if (this.editInFocus)
			this.input.blur();
		window.document.onmouseup		= this.eventMouseUp.bindAsEventListener(this);
		window.document.onmousemove		= this.eventMouseMove.bindAsEventListener(this);
		Event.observe(document, "mouseout", this.eventWindowMouseOut);
		this.editBlur();
		this.prevMouseDownEvent = event;

		if(Event.isLeftClick(event)) {
			if(!this.disabled){
				this.handle.className = this.classes.arrowSelected + this.classes.base + this.classes.handleSelected;
				if (this.options.currValue){
					Element.show(this.tip);
					Element.setStyle(this.tip, {top: '-' + (this.tip.offsetHeight+2) + 'px'});
				}
				Richfaces.createEvent("mousedown", this.mainTable, null, null).fire();
				this.active = true;
				var handle = Event.element(event);
				
				var pointer;
				if(this.orientation=="vertical"){
				    pointer = Event.pointerY(event);
				} else {
				    pointer = Event.pointerX(event);
				}
				var offsets	= Position.cumulativeOffset(this.track);
				this.updating = true;
				
				var value;
				if(this.orientation=="vertical"){
                    value = this.translateToValue( ( pointer - offsets[1] ) -(this.handleLength/2));
                } else {
                    value = this.translateToValue( ( pointer - offsets[0] ) -(this.handleLength/2));
                }
				if(this.invokeEvent("slide",event,this.getNearestValue(value),this.input)){				
					this.setValue(value);
				}
				
				this.updating = false;
				var offsets	= Position.cumulativeOffset(this.handle);
				if(this.orientation=="vertical"){
                    this.offsetX = pointer - offsets[1];
                } else {
                    this.offsetX = pointer - offsets[0];
                }
			}
			Event.stop(event);
		}
	},

	update: function(event) {
		this.updating = true;
		if(this.active) {
			if(!this.dragging) this.dragging = true;
			this.draw(event);
			Event.stop(event);
		}
		this.updating = false;
	},

	draw: function(event) {
		if(this.orientation=="vertical"){
	        var pointer = Event.pointerY(event);
            var offsets = Position.cumulativeOffset(this.track);
            pointer -= this.offsetX + offsets[1];
            this.setValue(this.translateToValue( pointer ));
	    } else{
			var pointer = Event.pointerX(event);
			var offsets = Position.cumulativeOffset(this.track);
			pointer -= this.offsetX + offsets[0];
			this.setValue(this.translateToValue( pointer ));
		}
	},

	processMouseUp: function(event) {
		this.endDrag(event);
		this.fireClickIfNeeded(event);
	},

	endDrag: function(event) {
		window.document.onmouseup = this.prevMouseUp;
		window.document.onmousemove = this.prevMouseMove;
		Event.stopObserving(document, "mouseout", this.eventWindowMouseOut, false);
		if (this.options.currValue){
			Element.hide(this.tip);
		}
		if (this.eventChanged && this.isValueChanged()){
			this.eventChanged(event);
		}
		this.handle.className = this.classes.arrow + this.classes.base;
		if(this.active && this.dragging) {
			this.active = false;
			this.dragging = false;
			Richfaces.createEvent("mouseup", this.mainTable, null, null).fire();
			Event.stop(event);
		}
		if (RichFaces.navigatorType() != RichFaces.MSIE)
			Richfaces.createEvent("change", this.input, null, null).fire();
	},

	fireClickIfNeeded: function(event){
		if ((this.prevMouseDownEvent.target != event.target
			&& RichFaces.navigatorType() == RichFaces.FF)
			|| (RichFaces.getOperaVersion()
			&& RichFaces.getOperaVersion() < 9.0
			&& event.target.tagName.toLowerCase() != "div")) {
				Richfaces.createEvent("click", this.mainTable, null, null).fire();
		}
	},

	isValueChanged : function(){
		var ret =this.prevValue != this.value
		this.prevValue = this.value;
		return ret;
	},

	increase : function(event){
	    var v = parseFloat(this.value) + parseFloat(this.step);
        this.setValue(Number( v < this.maximum ? v : this.maximum));
        this.input.value = this.value;
        if (this.eventChanged && this.isValueChanged()){
            this.eventChanged(event);
        }
	},
	
	decrease : function(event){
	    var v = parseFloat(this.value) - parseFloat(this.step);
	    this.setValue(Number(v > this.minimum ? v : this.minimum));
        this.input.value = this.value;
        if (this.eventChanged && this.isValueChanged()){
            this.eventChanged(event);
        }
    },
    
    increaseDown : function(event){
        this.arrowButton = $(event.target);
        this.arrowButton.className = this.arrowButton.className.replace("Class","SelectedClass").replace("al","al-sel");
        window.document.onmouseup = this.eventIncreaseUp.bindAsEventListener(this);
        if(!this.disabled){
            if (this.options.currValue){
                Element.show(this.tipArrowInc);
            }
        }
        this.eventIncrease(event);
        this._periodicalExecuter = new PeriodicalExecuter(this.eventIncrease,this.delay/1000);
    },
    
    decreaseDown : function(event){
        this.arrowButton = $(event.target);
        this.arrowButton.className = this.arrowButton.className.replace("Class","SelectedClass").replace("al","al-sel");
        window.document.onmouseup = this.eventDecreaseUp.bindAsEventListener(this);
        if(!this.disabled){
            if (this.options.currValue){
                Element.show(this.tipArrowDec);
            }
        }
        this.eventDecrease(event);
        this._periodicalExecuter = new PeriodicalExecuter(this.eventDecrease,this.delay/1000);
    },
    
    increaseUp : function(event){
        this._periodicalExecuter.stop();
        if (this.options.currValue){
            Element.hide(this.tipArrowInc);
        }
        this.arrowButton.className = this.arrowButton.className.replace("SelectedClass","Class").replace("al-sel","al");
        window.document.onmouseup = this.prevMouseUp;
    },
    
    decreaseUp : function(event){
	    this._periodicalExecuter.stop();
        if (this.options.currValue){
            Element.hide(this.tipArrowDec);
        }
        this.arrowButton.className = this.arrowButton.className.replace("SelectedClass","Class").replace("al-sel","al");
        window.document.onmouseup = this.prevMouseUp;
    },

	inputChange: function(e) {
		this.editInFocus = false;
		if (isNaN(Number(this.input.value))){
			this.setValue(this.value);
		} else {
			if (this.outOfRange)
				if (this.eventError)
					this.eventError(e,this.options.clientErrorMessage);
			this.setValue(Number(this.input.value));
		}
		this.value = this.input.value ? this.input.value : this.value ;
		if(this.eventInputOnChange){
			this.eventInputOnChange();
		}
		if (this.eventChanged && this.isValueChanged()){
			this.eventChanged(e);
		}
	},

	inputValidate: function(e) {
		if ( e.keyCode == 13 ){
			if (isNaN(Number(this.input.value))){
				this.input.value = this.value;
				this.editBlur();
				this.setValue(this.value);
			}
		}
	},

	editChange: function(e) {
		if (this.input.value=='-') return;
		if (isNaN(Number(this.input.value))){
			this.setValue(Number(this.value));
			this.input.value = this.value;
			
			if (this.eventError){
				this.eventError(e,this.options.clientErrorMsg);
			}
		} else {
			if (!( e.keyCode >= 37 && e.keyCode <= 40 )){
				this.setValue(Number(this.input.value));
			}
		}

		if (e.keyCode == 13) {
			if (this.required || "" != this.input.value) {
				this.setValue(Number(this.value));
			    this.input.value = this.value;
			}
			if (this.input.form) {
				this.input.form.submit();
			}
		}
		if (this.eventChanged && this.isValueChanged()){
			this.eventChanged(e);
		}

	},

	editFocus: function(){
		this.editInFocus = true;
	},

	editBlur: function(){
		this.editInFocus = false;
	    if ((this.input.value+"").indexOf(this.value) != 0){
    		 this.setValue(this.input.value);
             this.eventInputChange();
	    }
	    else{
		     this.setValue(this.input.value);
		}
	},

	load: function(){
		// fix RF-895
		if(this.input.value){
			this.options.sliderValue = this.input.value;
		}
	
		this.setInitialValue();
		//this.setValue( this.value );
	},

	trim : function(str){
		return str.replace(/^\s+|\s+$/, '');
	},
	
	invokeEvent: function(eventName, event, value, element) {
	
		var eventFunction = this.options['on'+eventName];
		var result;

		if (eventFunction)
		{
			var eventObj;

			if (event)
			{
				eventObj = event;
			}
			else if( document.createEventObject ) 
			{
				eventObj = document.createEventObject();
			}
			else if( document.createEvent )
			{
				eventObj = document.createEvent('Events');
				eventObj.initEvent( eventName, true, false );
			}
			
			eventObj.rich = {component:this};
			eventObj.rich.value = value;

			try
			{
				result = eventFunction.call(element, eventObj);
			}
			catch (e) { LOG.warn("Exception: "+e.Message + "\n[on"+eventName + "]"); }

		}
		
		if (result!=false) result = true;
		
		return result;
	}	
	
}
