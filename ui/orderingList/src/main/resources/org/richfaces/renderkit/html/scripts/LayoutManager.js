LayoutManager = function(headerId, contentId) {
	this.headerTable = $(headerId);
	this.contentTable = $(contentId);
	this.headerDiv = (this.headerTable) ? this.headerTable.parentNode : null;
	this.contentDiv = this.contentTable.parentNode;
	
	Event.observe(this.contentDiv, "scroll", this.scrollHandler.bindAsEventListener(this));
	
}

LayoutManager.SCROLL_WIDTH = 17;

LayoutManager.STYLE_CONTENTTD_BORDER = 1;
LayoutManager.STYLE_CONTENTTD_PADDING = 4;

LayoutManager.prototype.widthSynchronization = function() {
	if (Prototype.Browser.IE && this.contentDiv && this.contentTable && this.getScrollWidth()) {
		//IE displays unnecessary horizontal scroll 
		//when vertical scroll's displayed
		if (this.contentTable.offsetWidth && ((this.contentTable.offsetWidth <= this.contentDiv.clientWidth))) {
			this.contentTable.style.width = this.contentDiv.clientWidth + "px";
			if (this.headerTable) {
				this.headerTable.style.width = this.contentDiv.offsetWidth + "px";				
			}
			this.contentDiv.style.overflowX = 'hidden';
		}
	} else {
		this.contentTable.style.width = "100%";
	}
	
	var rows = this.contentTable.tBodies[0].rows;
	if (rows && rows[0]) {
		//table can be empty
		var contentCells = rows[0].cells;
		if (!this.headerTable || !this.headerTable.tHead)
			return ;
		var headerCells = this.headerTable.tHead.rows[0].cells;
		var width;
		for (var i = 0; i < contentCells.length; i++) {
			var curCell = contentCells[i];
			var headCell = headerCells[i];
			
			width = LayoutManager.calculateWidth(curCell, headCell).colWidth;
			if (i == contentCells.length - 1) {
				width = width + this.getScrollWidth();
			}
			headCell.firstChild.style.width = width + "px";
			headCell.style.width = width + "px";
		}
	} else {
		if (this.headerTable && this.headerTable.tHead) {
			this.headerTable.style.width = "100%";
		} 
	}
}

LayoutManager.prototype.getScrollWidth = function() {
	if (this.contentDiv.clientWidth != 0) { 
		return this.contentDiv.offsetWidth - this.contentDiv.clientWidth;
	}
	return 0;
}

LayoutManager.prototype.scrollHandler = function() {
	if (this.headerDiv) {
		this.headerDiv.scrollLeft = this.contentDiv.scrollLeft;
	} 
}

LayoutManager.getHeaderWidth = function(visibleBox, realBox) {
	return realBox.offsetWidth + (visibleBox.offsetWidth - visibleBox.clientWidth);
}

LayoutManager.isIE = function() {
	return (/MSIE/.test(navigator.userAgent) && !window.opera);
}

LayoutManager.getElemXY = function(elem) {
    var x = elem.offsetLeft;
    var y = elem.offsetTop;

       for (var parent = elem.offsetParent; parent; parent = parent.offsetParent) {
          x += parent.offsetLeft;
          y += parent.offsetTop;
       }
   return {left: x, top: y};
}

LayoutManager.calculateWidth = function(srcElem, tgtElem) {
	var srcElemBorderWidth = LayoutManager.getBorderWidth(srcElem, "lr");
	var srcElemPaddingWidth = LayoutManager.getPaddingWidth(srcElem, "lr");
	var srcElemMarginWidth = LayoutManager.getMarginWidth(srcElem, "lr");
	
	var tgtElemBorderWidth = LayoutManager.getBorderWidth(tgtElem, "lr");
	var tgtElemPaddingWidth = LayoutManager.getPaddingWidth(tgtElem, "lr");
	var tgtElemMarginWidth = LayoutManager.getMarginWidth(tgtElem, "lr");
	var srcWidth = srcElem.offsetWidth - srcElemBorderWidth - srcElemPaddingWidth - srcElemMarginWidth;
	var colWidth = srcWidth + (srcElemBorderWidth - tgtElemBorderWidth) 
					 	    + (srcElemPaddingWidth - tgtElemPaddingWidth) 
					 		+ (srcElemMarginWidth - tgtElemMarginWidth);
	colWidth = (colWidth > 0) ? colWidth : 0; 
	return {srcWidth : srcWidth, colWidth : colWidth};
}

LayoutManager.getBorderWidth = function(el, side) {
	return LayoutManager.getStyles(el, side, LayoutManager.borders);
}
      
LayoutManager.getPaddingWidth = function(el, side) {
	return LayoutManager.getStyles(el, side, LayoutManager.paddings);
}

LayoutManager.getMarginWidth = function(el, side) {
	return LayoutManager.getStyles(el, side, LayoutManager.margins);
}
       
LayoutManager.getStyles = function(el, sides, styles) {
   var val = 0;
   for(var i = 0, len = sides.length; i < len; i++){
	//if (el.getStyle) {
		var w = parseInt(Element.getStyle(el, styles[sides.charAt(i)]), 10);
   	 	if(!isNaN(w)) val += w;
	//}
   }
   return val;
}

LayoutManager.borders = {l: 'border-left-width', r: 'border-right-width', t: 'border-top-width', b: 'border-bottom-width'},
LayoutManager.paddings = {l: 'padding-left', r: 'padding-right', t: 'padding-top', b: 'padding-bottom'},
LayoutManager.margins = {l: 'margin-left', r: 'margin-right', t: 'margin-top', b: 'margin-bottom'}
