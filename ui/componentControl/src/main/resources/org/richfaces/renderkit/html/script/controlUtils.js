if (!window.Richfaces) {
window.Richfaces = {};
}

if (!Richfaces.componentControl) {
	Richfaces.componentControl = {};
}

Richfaces.componentControl.eachComponent = function(forAttr, callback) {
	jQuery(forAttr)
		.each(function() {
			if (this.component) {
				callback(this.component);
			}
		});
	
};

Richfaces.componentControl.applyDecorations = function (element, forAttr, decorationCode) {
	if (decorationCode) {
		decorationCode(element);
	}
	
	Richfaces.componentControl.eachComponent(forAttr, function(component) {
		if (component.applyDecoration) {
			component.applyDecoration(element);
		}
	});
	
};

Richfaces.componentControl.attachEvent = function(attachTo, aevent, forAttr, operation, params, disableDefault) {
	jQuery(attachTo).bind(Richfaces.effectEventOnOut(aevent),function(cevent) {
		Richfaces.componentControl.performOperation(cevent,  forAttr, operation, params, disableDefault);
	}).each(function() {
		Richfaces.componentControl.applyDecorations(this, forAttr, function(element) {
			//TODO: handle component decoration
		});
	});
};

Richfaces.componentControl.performOperation = function( cevent,  forAttr, operation, params, disableDefault) {
	
	// stop event before event isn't extended by prototype  
	if (disableDefault) {
		var event = jQuery.event.fix(cevent);
		event.stopPropagation();
		event.preventDefault();
	}
	
	Richfaces.componentControl.eachComponent(forAttr, function(component) {
		var paramsValue = params;
		if (typeof params == "function") {
			paramsValue = params();
		}
		
		component[operation](cevent, paramsValue);
	});
};


Richfaces.effectEventOnOut = function(ename) {
	return ename.substr(0,2) == 'on' ? ename.substr(2) : ename;
};
