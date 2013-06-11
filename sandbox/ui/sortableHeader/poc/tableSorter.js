QuickSort = function() {}

QuickSort.prototype.partition = function(array, begin, end, pivot) {
	var piv = array[pivot][0];
	array.swap(pivot, end - 1);
	var store = begin;
	var ix;
	for(ix = begin; ix < end-1; ++ix) {
		if(array[ix][0] <= piv) {
			array.swap(store, ix);
			++store;
		}
	}
	array.swap(end - 1, store);

	return store;
}

QuickSort.prototype.quickSort = function(array, begin, end) {
	if(end - 1 > begin) {
		var pivot = begin + Math.floor(Math.random() * (end - begin));

		pivot = this.partition(array, begin, end, pivot);

		this.quickSort(array, begin, pivot);
		this.quickSort(array, pivot + 1, end);
	}
}

/**
 * Sorting
 */
QuickSort.prototype.sorting = function(array) {
	return this.quickSort(array, 0, array.length);
}


JSSort = function() {}

JSSort.prototype.compare = function(obj1, obj2) {
	var obj1 = obj1[0];
	var obj2 = obj2[0];
	return ((obj1 == obj2) ? 0 : ((obj1 < obj2) ? -1 : 1));
}

/**
 * Sorting
 */
JSSort.prototype.sorting = function(array) {
	return array.sort(this.compare);
}

DataTable = function(tableId, defaultColumnIndex) {
	this.table = document.getElementById(tableId); 
	this.sortedColumnIndex = defaultColumnIndex;
	this.oldSortedColumnIndex = -1; //default value
	this.sortedArray = null;
	this.tbody = this.table.tBodies[0];
	
	this.sortStrategy = new JSSort();
	
	this.sortOrder = DataTable.ASCENDING;
	
}

DataTable.ASCENDING = "asc";
DataTable.DESCENDING = "desc";

/**
 * Returns the cell text
 */
DataTable.prototype.getCellValue = function(targetTd) {
	return targetTd.innerHTML;
}


DataTable.prototype.initTargetCollection = function(sortedColumnIndex) {
	var targetCollection = new Array();
	var currentRow = null;
	
	for (var i = 0; i < this.tbody.rows.length; i++) {
		targetCollection[i] = new Array();
		
		currentRow = this.tbody.rows[i];
		targetCollection[i][0] = this.getCellValue(currentRow.cells[this.sortedColumnIndex]);
		targetCollection[i][1] = this.tbody.rows[i];
	}
	
	return targetCollection;
}


DataTable.prototype.sort = function(targetCollection) {
	this.sortedArray = this.sortStrategy.sorting(targetCollection);
}

DataTable.prototype.reverse = function(targetCollection) {
	this.sortedArray = targetCollection.reverse();
}

DataTable.prototype.singleSorting = function() {
	var array = this.initTargetCollection(table.sortedColumnIndex);
	
	if ((this.oldSortedColumnIndex == this.sortedColumnIndex)
		&& (this.sortOrder == DataTable.ASCENDING)) {
		this.reverse(array);
		this.sortOrder = DataTable.DESCENDING;
	} else {
		this.sort(array);
		this.sortOrder = DataTable.ASCENDING;
	}
}

DataTable.prototype.rebuildTable = function() {
	var newTbody = document.createElement("tbody");
	for (var i = 0; i < this.sortedArray.length; i++) {
		newTbody.appendChild(this.sortedArray[i][1]);
	}
	//this.table.appendChild(newTbody);
	
	this.tbody.parentNode.removeChild(this.tbody);
	this.table.appendChild(newTbody);
	this.tbody = this.table.tBodies[0];
	//this.table.appendChild(newTbody);
	
	this.oldSortedColumnIndex = this.sortedColumnIndex;
}

/*DataTable.prototype.rebuildTable1 = function() {
	var newTbody = new StringBuilder();
	for (var i = 0; i < this.sortedArray.length; i++) {
		newTbody.append("<tr>");
		newTbody.append(this.sortedArray[i][1].innerHTML);
		newTbody.append("</tr>");
	}
	var content = newTbody.toString();
	this.table.tBodies[0].innerHTML = content;
	this.oldSortedColumnIndex = this.sortedColumnIndex;
}*/
