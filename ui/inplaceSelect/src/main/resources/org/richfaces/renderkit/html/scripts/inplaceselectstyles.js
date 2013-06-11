if (!window.Richfaces) window.Richfaces = {};
Richfaces.InplaceSelectStyles = Class.create();

Richfaces.InplaceSelectStyles.prototype = {
	initialize : function() {
		this.commonStyles = {
			combolist : {
					list :{ 
						classes :{
							active : "rich-inplace-select-list-scroll rich-inplace-select-list-decoration rich-inplace-select-list-position"
						}
				 	}, 		 	  
					item : {
						normal : "rich-inplace-select-item rich-inplace-select-font", 
						selected : 'rich-inplace-select-item rich-inplace-select-font rich-inplace-select-selected-item'
					}
				},				
			component : {
					changed : {normal : 'rich-inplace-select rich-inplace-select-view rich-inplace-select-changed', hovered : 'rich-inplace-select-changed-hover'},  
					view : {normal : 'rich-inplace-select rich-inplace-select-view', hovered : 'rich-inplace-select-view-hover'}, 
					editable : 'rich-inplace-select rich-inplace-select-view rich-inplace-select-edit'
			}
		}
	},
	
	getCommonStyles : function() {
		return this.commonStyles;
	}

};
