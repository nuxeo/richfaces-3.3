//memory-leaks sanitizing code
if (!window.RichFaces) {
	window.RichFaces = {};
}

if (!window.RichFaces.Memory) {
	window.RichFaces.Memory = {

		nodeCleaners: {},
		componentCleaners: {},
		
		addCleaner: function (name, cleaner) {
			this.nodeCleaners[name] = cleaner;
		},

		addComponentCleaner: function (name, cleaner, checker) {
			this.componentCleaners[name] = {cleaner: cleaner, checker: checker};
		},
		
		applyCleaners: function (node, isAjax, componentNodes) {
			for (var name in this.nodeCleaners) {
				this.nodeCleaners[name](node, isAjax);
			}
			for (var name in this.componentCleaners) {
				if (this.componentCleaners[name].checker(node, isAjax))
				componentNodes.push(node);
			}
		},
		
		_clean: function (oldNode, isAjax, componentNodes) {
		    if (oldNode) {
		    	this.applyCleaners(oldNode, isAjax, componentNodes);
			
				//node.all is quicker than recursive traversing
			    //window doesn't have "all" attribute
			    var all = oldNode.all;
			    
			    if (all) {
			        var counter = 0;
			        var length = all.length;
			        
			        for (var counter = 0; counter < length; counter++ ) {
				    	this.applyCleaners(all[counter], isAjax, componentNodes);
			        }
			    } else {
			    	var node = oldNode.firstChild;
			    	while (node) {
			    		this._clean(node, isAjax, componentNodes);
			        	node = node.nextSibling;
			    	}
			    }
		    }
		},
		
		_cleanComponentNodes: function (oldNodes, isAjax) {
			for (var i=0; i<oldNodes.length; i++) {
				var node = oldNodes[i];
				for (var name in this.componentCleaners) {
					this.componentCleaners[name].cleaner(node, isAjax);
				}
			} 
		},
		
		clean: function (oldNode, isAjax) {
			var componentNodes = [];
			this._clean(oldNode, isAjax, componentNodes);
			this._cleanComponentNodes(componentNodes, isAjax);
			componentNodes = null;
		}
	};
	
	window.RichFaces.Memory.addComponentCleaner("richfaces", function(node, isAjax) {
		var component = node.component;
		if (component) {
			var destructorName = component["rich:destructor"];
			//destructor name is required to be back-compatible
			if (destructorName) {
				var destructor = component[destructorName];
				if (destructor) {
					destructor.call(component, isAjax);
				}
			}
		}
	}, function(node, isAjax) {
		return (node.component && node.component["rich:destructor"]);
	});
	
	if (window.attachEvent) {
	    window.attachEvent("onunload", function() {
	    	var memory = window.RichFaces.Memory;
	    	memory.clean(document);
	    	memory.clean(window);
	    });
	}
}

//