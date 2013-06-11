if (!window.Richfaces) { window.Richfaces = {}; }

Richfaces.PickListSI = Class.create(Richfaces.SelectItem, {
	initialize : function($super, label, id, node) {
		$super(label, id, node);
		this.selected = false;
		this.active = false;
	},
	
	saveState : function() {}	
});