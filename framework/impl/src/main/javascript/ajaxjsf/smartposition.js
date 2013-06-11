if (!window.Richfaces) { window.Richfaces = {};}
if (!Richfaces.SmartPosition )
Richfaces.SmartPosition = {
	options: $H({"positionX": ["right","left","center"], "positionY": ["bottom","top"], positionFloat: true}),
	getBase: function() {
		return (document.compatMode && document.compatMode.toLowerCase() == "css1compat" && 
						!/Netscape|Opera/.test(navigator.userAgent)) ?
			document.documentElement : (document.body || null);
	},

	screenOffset: function (element, debug) {
		element = $(element);
//		var offset = Position.page(element);

		var base = this.getBase();
		var isOpera = /Opera/.test(navigator.userAgent);
		var valueT = 0, valueL = 0;

var str = "";
		var _element = element;
		do {
str += "element: " + _element.tagName + ", offsetTop = " + _element.offsetTop + ", offsetLeft = " + _element.offsetLeft + "\n";
			valueT += _element.offsetTop  || 0; valueL += _element.offsetLeft || 0;
str += "valueT = " + valueT + ", valueL = " + valueL + "\n";

//			if (_element.offsetParent==document.body && Element.getStyle(_element,'position')=='absolute') break;
			if (_element.offsetParent == base && Element.getStyle(_element,'position')=='absolute') break;

		} while (_element = _element.offsetParent);

str += "\n\n";
		_element = element;
		do {
str += "element: " + _element.tagName + ", scrollTop = " + _element.scrollTop + ", scrollLeft = " + _element.scrollLeft + "\n";
			if (!isOpera || 
				(_element.tagName != undefined && (_element.tagName.toLowerCase() != "tr" && _element != element && _element != element.parentNode)				 )
				) {
				valueT -= _element.scrollTop  || 0; valueL -= _element.scrollLeft || 0;
			}
str += "valueT = " + valueT + ", valueL = " + valueL + "\n";
			if (_element == base) break;
		} while (_element = _element.parentNode);

		var offset = [valueL, valueT];
if (arguments.length > 1 && debug) alert(str + "\n\n" + "offset = " + offset);

		element = $(element);
		if (!isOpera && element.tagName.toLowerCase() == "input") offset[0] += element.scrollLeft;

		return offset;
	},

	getVSpaces: function (element) {
		var base = this.getBase();
		if (this.isElement(element)) {
			var screenOffset = this.screenOffset(element);
			var dimensions = Element.getDimensions(element);
			var top = screenOffset[1];
			var bottom = base.clientHeight - screenOffset[1] - dimensions.height;
		} else {
			var top = element[1] - base.scrollTop;
			var bottom = base.clientHeight - (element[1] - base.scrollTop);
		}
		return {top: top, bottom: bottom};
	},

	getHSpaces: function (element) {
		var base = this.getBase();
		if (this.isElement(element)) {
			var screenOffset = this.screenOffset(element);
			var dimensions = Element.getDimensions(element);
			var left = screenOffset[0] + dimensions.width;
			var right = base.clientWidth - screenOffset[0];
		} else {
			var left = element[0] - base.scrollLeft;
			var right = base.clientWidth - (element[0] - base.scrollLeft);
		}
		return {left: left, right: right};
	},

	getPosition: function (element, popup, options) {
		var base = this.getBase();
		var popupDimensions = [$(popup).offsetWidth, $(popup).offsetHeight];
		var hSpaces = this.getHSpaces(element);
		var vSpaces = this.getVSpaces(element);

		if (this.isElement(element)) {
			var screenOffset = this.screenOffset(element);
			var elementDimensions = {width: $(element).offsetWidth, height: $(element).offsetHeight};
			var pos = [screenOffset[0], screenOffset[1] + elementDimensions.height];
		} else {
			var pos = [element[0], element[1]];
		}
		if (hSpaces.right < popupDimensions[0] &&
			hSpaces.left >= popupDimensions[0]) {
			if (this.isElement(element)) {
				pos[0] = screenOffset[0] + elementDimensions.width - popupDimensions[0];
			} else {
				pos[0] = element[0] - popupDimensions[0];
			}
		}
		if (this.isElement(element)) pos[0] += base.scrollLeft; 
		if (vSpaces.bottom < popupDimensions[1] &&
			vSpaces.top >= popupDimensions[1]) {
			if (this.isElement(element)) {
				pos[1] = screenOffset[1] - popupDimensions[1];
			} else {
				pos[1] = pos[1] - popupDimensions[1];
			}
		}
		if (this.isElement(element)) pos[1] += base.scrollTop;
		return pos;
	},

	getOption: function (options) {
		if (options) {
			return $H(options);
		} else {
			return this.options;
		}
	},

	calcSizes: function(content) {
		if (content.tagName.toLowerCase() == "table") {
			content.style.width = "0px";
			content.style.height = "0px";
			return {width: content.offsetWidth, height: content.offsetHeight};
		}
	},

	isElement: function(element) {
		return (element.length == undefined);
	}
}
