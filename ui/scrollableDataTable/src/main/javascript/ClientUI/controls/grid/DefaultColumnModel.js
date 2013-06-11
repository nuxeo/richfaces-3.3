/*
 * TODO: Copyright (c) 2007 Denis Morozov <dmorozov@exadel.com>
 *
 * ...
 */
ClientUILib.declarePackage("ClientUI.controls.grid.DefaultColumnModel");


ClientUI.controls.grid.DefaultColumnModel = Class.create({
	initialize: function(config) {
		this.config = config;
		
		// default properties
    	this.defaultWidth = 100;
    	this.defaultSortable = false;		
	},
	
	// generic events arised from columns
	onWidthChange: function(){},
    onHeaderChange: function(){},
	onHiddenChange: function(){},
	
	// fire event
    fireWidthChange : function(colIndex, newWidth){
		this.onWidthChange(this, colIndex, newWidth);
	},
	
	fireHeaderChange : function(colIndex, newHeader){
		this.onHeaderChange(this, colIndex, newHeader);
	},
	
	fireHiddenChange : function(colIndex, hidden){
		this.onHiddenChange(this, colIndex, hidden);
	},

	/**
	 * Returns the number of columns.
	 * @return {Number}
	 */
    getColumnCount : function(){
        return this.config.length;
    },
    
    /**
     * Returns true if the specified column is sortable.
     * @param {Number} col The column index
     * @return {Boolean}
     */
    isSortable : function(col){
        if(typeof this.config[col].sortable == 'undefined'){
            return this.defaultSortable;
        }
        return this.config[col].sortable;
    },
    
    /**
     * Interface method - Returns true if the specified column is hidden.
     * @param {Number} col The column index
     * @return {Boolean}
     */
    isHidden : function(col){
        return false;
    },
        
    /**
     * Returns the rendering (formatting) function defined for the column.
     * @param {Number} col The column index
     * @return {Function}
     */
    getRenderer : function(col){
        if(!this.config[col].renderer){
            return ClientUI.controls.grid.DefaultColumnModel.defaultRenderer;
        }
        return this.config[col].renderer;
    },
        
    /**
     * Sets the rendering (formatting) function for a column.
     * @param {Number} col The column index
     * @param {Function} fn
     */
    setRenderer : function(col, fn){
        this.config[col].renderer = fn;
    },
        
    /**
     * Returns the width for the specified column.
     * @param {Number} col The column index
     * @return {Number}
     */
    getColumnWidth : function(col){
        return this.config[col].width || this.defaultWidth;
    },
        
    /**
     * Sets the width for a column.
     * @param {Number} col The column index
     * @param {Number} width The new width
     */
    setColumnWidth : function(col, width, suppressEvent){
        this.config[col].width = width;
        this.totalWidth = null;
        if(!suppressEvent){
             this.fireWidthChange(col, width);
        }
    },
                
    /**
     * Returns the total width of all columns.
     * @param {Boolean} includeHidden True to include hidden column widths
     * @return {Number}
     */
    getTotalWidth : function(includeHidden){
        if(!this.totalWidth){
            this.totalWidth = 0;
            for(var i = 0; i < this.config.length; i++){
                if(includeHidden || !this.isHidden(i)){
                    this.totalWidth += this.getColumnWidth(i);
                }
            }
        }
        return this.totalWidth;
    },
    
    /**
     * Returns the header for the specified column.
     * @param {Number} col The column index
     * @return {String}
     */
    getColumnHeader : function(col){
        return this.config[col].header;
    },
         
    /**
     * Sets the header for a column.
     * @param {Number} col The column index
     * @param {String} header The new header
     */
    setColumnHeader : function(col, header){
        this.config[col].header = header;
        this.fireHeaderChange(col, header);
    },
    
    /**
     * Returns the tooltip for the specified column.
     * @param {Number} col The column index
     * @return {String}
     */
    getColumnTooltip : function(col){
            return this.config[col].tooltip;
    },
    /**
     * Sets the tooltip for a column.
     * @param {Number} col The column index
     * @param {String} tooltip The new tooltip
     */
    setColumnTooltip : function(col, header){
            this.config[col].tooltip = tooltip;
    },
        
    /**
     * Returns the dataIndex for the specified column.
     * @param {Number} col The column index
     * @return {Number}
     */
    getDataIndex : function(col){
        if(typeof this.config[col].dataIndex != 'number'){
            return col;
        }
        return this.config[col].dataIndex;
    },
         
    /**
     * Sets the dataIndex for a column.
     * @param {Number} col The column index
     * @param {Number} dataIndex The new dataIndex
     */
    setDataIndex : function(col, dataIndex){
        this.config[col].dataIndex = dataIndex;
    },
    /**
     * Returns true if the cell is editable.
     * @param {Number} colIndex The column index
     * @param {Number} rowIndex The row index
     * @return {Boolean}
     */
    isCellEditable : function(colIndex, rowIndex){
        return this.config[colIndex].editable || (typeof this.config[colIndex].editable == 'undefined' && this.config[colIndex].editor);
    },
    
    /**
     * Returns the editor defined for the cell/column.
     * @param {Number} colIndex The column index
     * @param {Number} rowIndex The row index
     * @return {Object}
     */
    getCellEditor : function(colIndex, rowIndex){
        return this.config[colIndex].editor;
    },
       
    /**
     * Sets if a column is editable.
     * @param {Number} col The column index
     * @param {Boolean} editable True if the column is editable
     */
    setEditable : function(col, editable){
        this.config[col].editable = editable;
    },
        
    /**
     * Returns true if the column is hidden.
     * @param {Number} colIndex The column index
     * @return {Boolean}
     */
    isHidden : function(colIndex){
        return this.config[colIndex].hidden;
    },    
    
    /**
     * Returns true if the column width cannot be changed
     */
    isFixed : function(colIndex){
        return this.config[colIndex].fixed;
    },
    
    /**
     * Returns true if the column cannot be resized
     * @return {Boolean}
     */
    isResizable : function(colIndex){
        return this.config[colIndex].resizable !== false;
    },
    /**
     * Sets if a column is hidden.
     * @param {Number} colIndex The column index
     */
    setHidden : function(colIndex, hidden){
        this.config[colIndex].hidden = hidden;
        this.totalWidth = null;
        this.fireHiddenChange(colIndex, hidden);
    },
    
    /**
     * Sets the editor for a column.
     * @param {Number} col The column index
     * @param {Object} editor The editor object
     */
    setEditor : function(col, editor){
        this.config[col].editor = editor;
    }
});

ClientUI.controls.grid.DefaultColumnModel.defaultRenderer = function(value){
	if(typeof value == 'string' && value.length < 1){
	    return '&#160;';
	}
	return value;
}