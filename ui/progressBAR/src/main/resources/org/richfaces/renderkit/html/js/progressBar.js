ProgressBar = {};
ProgressBar = Class.create();
Object.extend(ProgressBar.prototype, {
    initialize: function(id, containerId, formId, mode, minValue, maxValue, context, markup, options, progressVar, state, value) {
		this.id = id;
				
		this.containerId = containerId;
		if (formId != '') {
			this.formId = formId;
		}else {
			var f = this.getForm();
			this.formId = (f) ? f.id : null;
		}
		this.mode = mode;
		this.state = state;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value = value;
		this.disabled = false;
	
		this.context = context;
		this.markup = markup;
		this.options = options || {};
		
		this.onbeforedomupdate = this.options.onbeforedomupdate;
		
		this.options.onbeforedomupdate = function(request, event, data) {
			if (this.onbeforedomupdate) this.onbeforedomupdate(request, event, data);
			this.onComplete(data);
		}.bind(this);
		
		this.progressVar = progressVar;
		$(this.id).component = this;
    },
    
    getForm: function () {
		var parentForm = $(this.id);
		while (parentForm.tagName && parentForm.tagName.toLowerCase() != 'form') {
			parentForm = parentForm.parentNode;
		}
		return parentForm;
	},

	getValue: function () {
		return this.value;
	},
	
	getParameter: function (ev, params, paramName) {
		if (!params) {
			params = ev;
		}
		if (params && params[paramName]) {
			return params[paramName];
		} 
		return params;
	},
	
	onComplete: function (data) {
	  	if (!$(this.id) || this.disabled) { return; } 
		if (data) {
			this.value = data['value'];
			if (this.state == "progressState") {
				if (this.value > this.getMaxValue()) {
					this.forceState("complete",null);
					return;
				}
				this.updateComponent(data);
				this.renderLabel(data['markup'], data['context']);
			} else if (this.state == "initialState" && this.value > this.getMinValue()) {
				this.state = "progressState";
				this.forceState("progressState", function () { $(this.id).component.enable(); }.bind(this));
				return;
			}
			this.poll(); 
		}
		
	},
	poll: function () {
			A4J.AJAX.Poll(this.containerId, this.formId, this.options);
	},
	interpolate: function (placeholders) {
		for(var k in this.context) {
			var v = this.context[k];
			var regexp = new RegExp("\\{" + k + "\\}", "g");
			placeholders = placeholders.replace(regexp, v);
		}
		return placeholders;
	},
	updateComponent: function (data) {
		this.updateStyle(data['style']);
		this.setValue(this.value);
		if (!data['enabled']) { this.disable(); }
		this.updateClassName($(this.id + ":complete"), data['completeClass']);
		this.updateClassName($(this.id + ":remain"), data['remainClass']);
		this.updateClassName($(this.id), data['styleClass']);
		
		if (this.options.pollinterval != data['interval']) {
			this.options.pollinterval = data['interval'];
		}
	},
	updateStyle: function (style) {
	if (!style)  { return; }
		var d = $(this.id);
		if (d.style)
		  if (d.style.cssText != style) {
			d.style.cssText = style;
			d = $(this.id + ":remain");
			if (d) d.style.cssText = style;
			d = $(this.id + ":complete");
			if (d) d.style.cssText = style; 
			d = $(this.id + ":upload");
			if (d) d.style.cssText = style;
		}
	},
	updateClassName: function (o, newName) {
	if (!newName) return;
		if (o && o.className) {
			if (o.className.indexOf(newName) < 0){
				o.className = o.className + " " + newName;
			}
		}
	},
	getContext: function () {
		var context = this.context;
		if (!context) { context = {}; }
			context['minValue'] = (this.minValue == 0 ? "0" : this.minValue);
			context['maxValue'] = (this.maxValue == 0 ? "0" : this.maxValue);
			context['value'] = (this.value == 0 ? "0" : this.value);
			if (this.progressVar) {
				context[this.progressVar] = context['value'];
			}
		return context;
	},
	renderLabel: function (markup, context) {
		if (!markup || this.state != "progressState") {
			return;
		}
		if (!context) {
			context = this.getContext();
		}
		var html = markup.invoke('getContent', context).join('');
		$(this.id + ":remain").innerHTML = $(this.id + ":complete").innerHTML = html;
	},
	interpolate: function (placeholders, context) {
		for(var k in context) {
			var v = context[k];
			var regexp = new RegExp("\\{" + k + "\\}", "g");
			placeholders = placeholders.replace(regexp, v);
		}
		return placeholders;
	},
	setLabel: function (ev, str) {
		str = this.getParameter(ev, str, "label");
		if (this.state != "progressState") { return; }
		var d = $(this.id + ":remain");
		if (!d) { return; } 
		var lbl = this.interpolate(str + "", this.getContext());
		if (lbl)
			d.innerHTML = $(this.id + ":complete").innerHTML = lbl;
		this.markup = null;
	},
	getMode: function () {
		return this.mode;
	},
	getMaxValue: function () {
		return this.maxValue;	
	},
	getMinValue: function () {
		return this.minValue;
	},
	isAjaxMode: function () {
		return (this.getMode() == "ajax");
	}, 
	calculatePercent: function(v) {
		var min = parseFloat(this.getMinValue());
		var max = parseFloat(this.getMaxValue());
		var value = parseFloat(v);
		if (value > min && value < max) {
			return (100*(value - min))/(max - min); 
		} else if (value <= min) {
			return 0;
		} else if (value >= max) {
			return 100;
		}
	},
	setValue: function (ev, val) {
		val = this.getParameter(ev, val, "value");
		this.value = val;
		if (!this.isAjaxMode()) {
			if (parseFloat(val) <=  parseFloat(this.getMinValue())) {
				this.switchState("initialState");
			}else if (parseFloat(val) > parseFloat(this.getMaxValue())) {
				this.switchState("completeState");
			}else {
				this.switchState("progressState");
			}
		}
		if (!this.isAjaxMode() && this.state != "progressState") return;
		
		if (this.markup) {
			this.renderLabel(this.markup, this.getContext());
		} else {
			//this.setLabel("{value}%");
		}
		
		var p = this.calculatePercent(val);
		var d = $(this.id + ":upload");
		if (d != null) d.style.width = (p + "%");
	
	},
	enable: function (ev) {
		if (!this.isAjaxMode()) {
			this.switchState("progressState");
			this.setValue(this.getMinValue() + 1);
		}else if (!(this.value > this.getMaxValue())) {
			this.disable();
			this.poll();			
		}
	this.disabled = false;
	},
	disable: function () {
		this.disabled = true;
		A4J.AJAX.StopPoll(this.id);
	},
	finish: function () {
		if (!this.isAjaxMode()) {
			this.switchState("completeState");
		}else {
			this.disable();
			this.forceState("complete");
		}
	},
	hideAll: function () {
		Element.hide($(this.id + ":progressState"));
		Element.hide($(this.id + ":completeState"));
		Element.hide($(this.id + ":initialState"));
	},
	switchState: function (state) {
		this.state = state;
		this.hideAll();
		Element.show($(this.id + ":" + state));
	},
	renderState: function (state) {
		this.state = state;
		this.hideAll();
		Element.show($(this.id + ":" + state));
	},
	forceState: function (state, oncomplete) {
		var options = {};
		options['parameters'] = {};
		options['parameters'][this.id] = this.id;
		options['parameters']['forcePercent'] = state;
		options['parameters']['ajaxSingle'] = this.id;;
		options['actionUrl'] = this.options.actionUrl;
		if (oncomplete) {
			options['oncomplete'] = oncomplete;
		}
		A4J.AJAX.SubmitRequest(this.containerId, this.formId, null, options);
	}
	});
