if (!window.Richfaces) window.Richfaces = {};
Richfaces.InplaceInputStyles = Class.create();

Richfaces.InplaceInputStyles.prototype = {
	
	initialize : function() {
		this.commonStyles = {
			component:{
				view :{
					normal:"rich-inplace rich-inplace-input rich-inplace-view", 
					hovered:"rich-inplace-input-view-hover"
				},
				changed:{
					normal:"rich-inplace rich-inplace-input rich-inplace-changed",
					hovered:"rich-inplace-input-changed-hover"
				},
				editable:"rich-inplace rich-inplace-input rich-inplace-edit"
			}		
		}
	},
	
	getCommonStyles: function() {
		return this.commonStyles;
	}

};







