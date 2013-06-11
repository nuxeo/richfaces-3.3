if(!window.Richfaces) window.Richfaces = {};

Richfaces.PickList = Class.create(Richfaces.ListShuttle, {
		initialize : function($super, targetList, sourceList, clientId, controlIds, switchByClick, switchByDblClick, events, valueKeeperId) {
			$super(targetList, sourceList, clientId, controlIds, switchByClick, switchByDblClick, events);
			this.valueKeeper = $(valueKeeperId);
			//this.controlListManager();
		},
		
		moveItems : function($super, sourceComponent, targetComponent, items) {
			this.saveState(items, this.isAdd(sourceComponent));
			$super(sourceComponent, targetComponent, items);
		},
		
		moveItemByClick : function($super, event, sourceComponent, targetComponent, layoutManager) {
			this.saveState([this.sourceList.getEventTargetRow(event)], this.isAdd(sourceComponent));
			$super(event, sourceComponent, targetComponent, layoutManager);
		},
		
		saveState : function(items, isAdd) {
			var items = this.getSIAsLabels(items);
			if (isAdd) {
				var valuesAsString = this.valueKeeper.value;
				if (valuesAsString.length != 0) {
					valuesAsString += ",";
				}
				this.valueKeeper.value = valuesAsString + items.join(",");
			} else {
				var values = this.valueKeeper.value.split(",");
				for (var i = 0; i < items.length; i++) {
					values.remove(items[i]);
				}
				values.without(items);
				this.valueKeeper.value = values.join(",");
			}
		},
		
		isAdd : function(sourceComponent) {
			if (this.targetList.shuttleTable.id == sourceComponent.shuttleTable.id) {
					return false;
			}
			return true;
		},
		
		getSIAsLabels : function(items) {
			if ((items == null) || (items.length == 0)) {
				return;
			}
			var result = new Array();
			for (var i = 0; i < items.length; i++) {
				var item = items[i];
				if (!item.input) {
					item = item.item;
				}
				result[i] = item.input.value;
			}
			return result;
		}
});

Richfaces.PickList.Source = Class.create(Richfaces.ListBase);
Richfaces.PickList.Target = Class.create(Richfaces.ListBase);

Richfaces.PickList.Source.SelectItem = Class.create(Richfaces.PickListSI);
Richfaces.PickList.Source.SelectItem.prototype.CLASSES = {
	ROW : {
		ACTIVE   : "rich-picklist-source-row-active",
		SELECTED : "rich-picklist-source-row-selected",
		DISABLED : "rich-picklist-source-row-disabled",
		NORMAL   : "rich-picklist-source-row"
	},
	CELL : {
		ACTIVE   : "rich-picklist-source-cell-active",
		SELECTED : "rich-picklist-source-cell-selected",
		DISABLED : "rich-picklist-source-cell-disabled",
		NORMAL   : "rich-picklist-source-cell",
		BEGIN:	" rich-picklist-source-cell-first",
		END:	" rich-picklist-source-cell-last"
	}
};

Richfaces.PickList.Target.SelectItem = Class.create(Richfaces.PickListSI);
Richfaces.PickList.Target.SelectItem.prototype.CLASSES = {
	ROW : {
		ACTIVE   : "rich-picklist-target-row-active",
		SELECTED : "rich-picklist-target-row-selected",
		DISABLED : "rich-picklist-target-row-disabled",
		NORMAL   : "rich-picklist-target-row"
	},
	CELL : {
		ACTIVE   : "rich-picklist-target-cell-active",
		SELECTED : "rich-picklist-target-cell-selected",
		DISABLED : "rich-picklist-target-cell-disabled",
		NORMAL   : "rich-picklist-target-cell",
		BEGIN:	" rich-picklist-target-cell-first",
		END:	" rich-picklist-target-cell-last"
	}
};

Richfaces.PickList.HANDLERS = {
	copy:      function (e) { this.moveItems(this.sourceList, this.targetList, this.sourceList.selectedItems); return false; },
	copyAll:   function (e) { this.moveItems(this.sourceList, this.targetList, this.sourceList.shuttleItems); return false; },
	remove:    function (e) { this.moveItems(this.targetList, this.sourceList, this.targetList.selectedItems); return false; },
	removeAll: function (e) { this.moveItems(this.targetList, this.sourceList, this.targetList.shuttleItems); return false; }
};