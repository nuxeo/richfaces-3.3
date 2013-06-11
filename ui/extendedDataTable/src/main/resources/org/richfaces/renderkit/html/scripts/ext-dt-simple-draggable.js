DnD.ExtSimpleDraggable  = Class.create();

Object.extend(DnD.ExtSimpleDraggable.prototype, DnD.Draggable.prototype);
Object.extend(DnD.ExtSimpleDraggable.prototype, {
	initialize: function(id, options) {
		this.id = $(id);
		
		if (!this.id) {
			alert("drag: Element with [" + id + "] ID was not found in the DOM tree. Probably element has no client ID or client ID hasn't been written. DnD's disabled. Check please!");
			return ;
		}
		
		this.options = options;

		this.dragIndicatorId = this.options.dragIndicator;

        this.eventMouseDown = this.initDrag.bindAsEventListener(this);
			
		Event.observe(this.id, "mousedown", this.eventMouseDown);
		
		this.form = this.id;
		while (this.form && !/^form$/i.test(this.form.tagName)) {
			this.form = this.form.parentNode;
		}
		
		this.enableDraggableCursors(this.options.grab, this.options.grabbing);
	},

	getDnDDragParams: function() {
		if (this.options.dndParams) {
			return this.options.dndParams.parseJSON(EventHandlersWalk);
		}
		
		return null;
	},

    getIndicator: function() {    	
    	var dragIndicator = $(this.dragIndicatorId);
        if (!dragIndicator) {
            dragIndicator = this.getOrCreateDefaultIndicator();
        }

        return dragIndicator;
    },

    ondragstart : function(event, drag) {
    	this.showDropTargets();
		drag.params = this.options.parameters;
		drag.params[this.id] = this.id;

		this.setIndicator(event);
		
		this.getIndicator().leave();

		//this.dragEnter(event);
		
		if (this.form) {
			drag.params[this.form.id] = this.form.id;
		}
	},
	
	ondragend: function (event, drag) {
		this.hideDropTargets();
	},

	getContentType: function() {
		return this.options.dragType;
	},

	getDraggableOptions: function() {
		return this.options;
	},

	initDrag: function(event) {
		if (Event.isLeftClick(event)) {
		  var src = Event.element(event);
		  if(src.tagName && /^INPUT|SELECT|OPTION|BUTTON|TEXTAREA$/i.test(src.tagName)) 
				return;

			Event.stop(event);

			this.startDrag(event);
			//Event.observe(document, "mousemove", this.listenDragBound);
			//Event.observe(document, "mouseup", this.stopListenDragBound);
		}
	},
	
	showDropTargets: function(){
		var idStr = this.id.id;
		var ind = idStr.lastIndexOf('_');
		var prefix = idStr.substring(0,ind).replace("hdrag", "hdrop");
		var spans = document.getElementsByTagName("span");
		for(i=0;i<spans.length;i++){
			var s = spans[i];
			if (s.id.indexOf(prefix) == 0){
				//s.style.zIndex = '70';
				s.style.visibility = "visible";
				//change dropzone style
				s.childNodes[0].style.visibility="hidden";//top element
				s.childNodes[1].style.visibility="hidden";//bottom element
			}
		}//for
	},
	
	hideDropTargets: function(){
		var idStr = this.id.id;
		var ind = idStr.lastIndexOf('_');
		var prefix = idStr.substring(0,ind).replace("hdrag", "hdrop");
		var spans = document.getElementsByTagName("span");
		for (i=0;i<spans.length;i++){
			var s = spans[i];
			if (s.id.indexOf(prefix) == 0){
				//s.style.zIndex = '59';
				s.style.visibility = "hidden";
				//change dropzone style
				s.childNodes[0].style.visibility="hidden";//top element
				s.childNodes[1].style.visibility="hidden";//bottom element
			}
		}//for
	}
});