if(!window.LOG){
	window.LOG = {warn:function(){}};
}

if (!window.Richfaces) window.Richfaces={};

Richfaces.Calendar={};
Richfaces.Calendar.setElementPosition = function(element, baseElement, jointPoint, direction, offset)
{
	// parameters:
	// baseElement: Dom element or {left:, top:, width:, height:};
	// jointPoint: {x:,y:} or ('top-left','top-right','bottom'-left,'bottom-right')
	// direction:  ('top-left','top-right','bottom'-left,'bottom-right', 'auto')
	// offset: {dx:,dy:}

	if (!offset) offset = {dx:0,dy:0};

	var elementDim = Richfaces.Calendar.getOffsetDimensions(element);
	var baseElementDim;
	var baseOffset;

	if (baseElement.left!=undefined)
	{
		baseElementDim = {width: baseElement.width, height: baseElement.height};
		baseOffset = [baseElement.left, baseElement.top];

	} else
	{
		baseElementDim = Richfaces.Calendar.getOffsetDimensions(baseElement);
		baseOffset = Position.cumulativeOffset(baseElement);
	}

	var windowRect = Richfaces.Calendar.getWindowViewport();

	// jointPoint
	var ox=baseOffset[0];
	var oy=baseOffset[1];
	var re = /^(top|bottom)-(left|right)$/;
	var match;

	if (typeof jointPoint=='object') {ox = jointPoint.x; oy = jointPoint.y}
	else if ( jointPoint && (match=jointPoint.toLowerCase().match(re))!=null )
	{
		if (match[2]=='right') ox+=baseElementDim.width;
		if (match[1]=='bottom') oy+=baseElementDim.height;
	} else
	{
		// ??? auto
	}

	// direction
	if (direction && (match=direction.toLowerCase().match(re))!=null )
	{
		var d = direction.toLowerCase().split('-');
		if (match[2]=='left') ox-=elementDim.width+offset.dx; else if (match[2]=='right') ox+=offset.dx;
		if (match[1]=='top') oy-=elementDim.height+offset.dy; else if (match[1]=='bottom') oy+=offset.dy;
	} else
	{
		// auto
		var theBest = {square:0};
		// jointPoint: bottom-right, direction: bottom-left
		var basex = baseOffset[0]-offset.dx;
		var basey = baseOffset[1]+offset.dy;
		var rect = {right: basex + baseElementDim.width, top: basey + baseElementDim.height};
		rect.left = rect.right - elementDim.width;
		rect.bottom = rect.top + elementDim.height;
		ox = rect.left; oy = rect.top;
		var s = Richfaces.Calendar.checkCollision(rect, windowRect);
		if (s!=0)
		{
			if (ox>=0 && oy>=0 && theBest.square<s) theBest = {x:ox, y:oy, square:s};
			// jointPoint: top-right, direction: top-left
			basex = baseOffset[0]-offset.dx;
			basey = baseOffset[1]-offset.dy;
			rect = {right: basex + baseElementDim.width, bottom: basey};
			rect.left = rect.right - elementDim.width;
			rect.top = rect.bottom - elementDim.height;
			ox = rect.left; oy = rect.top;
			s = Richfaces.Calendar.checkCollision(rect, windowRect);
			if (s!=0)
			{
				if (ox>=0 && oy>=0 && theBest.square<s) theBest = {x:ox, y:oy, square:s};
				// jointPoint: bottom-left, direction: bottom-right
				basex = baseOffset[0]+offset.dx;
				basey = baseOffset[1]+offset.dy;
				rect = {left: basex, top: basey + baseElementDim.height};
				rect.right = rect.left + elementDim.width;
				rect.bottom = rect.top + elementDim.height;
				ox = rect.left; oy = rect.top;
				s = Richfaces.Calendar.checkCollision(rect, windowRect);
				if (s!=0)
				{
					if (ox>=0 && oy>=0 && theBest.square<s) theBest = {x:ox, y:oy, square:s};
					// jointPoint: top-left, direction: top-right
					basex = baseOffset[0]+offset.dx;
					basey = baseOffset[1]-offset.dy;
					rect = {left: basex, bottom: basey};
					rect.right = rect.left + elementDim.width;
					rect.top = rect.bottom - elementDim.height;
					ox = rect.left; oy = rect.top;
					s = Richfaces.Calendar.checkCollision(rect, windowRect);
					if (s!=0)
					{
						// the best way selection
						if (ox<0 || oy<0 || theBest.square>s) {ox=theBest.x; oy=theBest.y}
					}
				}
			}

		}
	}

	var els = element.style;
	var originalVisibility = els.visibility;
	var originalPosition = els.position;
	var originalDisplay = els.display;
	els.visibility = 'hidden';
	els.position = 'absolute';
	els.display = '';

	if (!window.opera)
	{
   		var parentOffset = element.getOffsetParent().viewportOffset();
   		ox -= parentOffset[0];
		oy -= parentOffset[1];
	} else if (element.offsetParent)
	{
		// for Opera only
		if (element.offsetParent!=document.body)
		{
			var parentOffset=Position.cumulativeOffset(element.offsetParent);
			ox -= parentOffset[0];
			oy -= parentOffset[1];
			ox += element.offsetParent.scrollLeft;
			oy += element.offsetParent.scrollTop;
		} else {
			var parentOffset = Richfaces.Calendar.cumulativeScrollOffset(element);
			ox += parentOffset[0];
			oy += parentOffset[1];
		}
	}

	els.display = originalDisplay;
	els.position = originalPosition;
	els.visibility = originalVisibility;
	element.style.left = ox + 'px';
	element.style.top = oy + 'px';
};

Richfaces.Calendar.cumulativeScrollOffset = function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.scrollTop  || 0;
      valueL += element.scrollLeft || 0;
      element = element.parentNode;
    } while (element && element != document.body);
    return Element._returnOffset(valueL, valueT);
};

Richfaces.Calendar.getOffsetDimensions = function(element) {
	// from prototype 1.5.0 // Pavel Yascenko
    element = $(element);
    var display = $(element).getStyle('display');
    if (display != 'none' && display != null) // Safari bug
      return {width: element.offsetWidth, height: element.offsetHeight};

    // All *Width and *Height properties give 0 on elements with display none,
    // so enable the element temporarily
    var els = element.style;
    var originalVisibility = els.visibility;
    var originalPosition = els.position;
    var originalDisplay = els.display;
    els.visibility = 'hidden';
    els.position = 'absolute';
    els.display = 'block';
    var originalWidth = element.offsetWidth; // was element.clientWidth // Pavel Yascenko
    var originalHeight = element.offsetHeight; // was element.clientHeight // Pavel Yascenko
    els.display = originalDisplay;
    els.position = originalPosition;
    els.visibility = originalVisibility;
    return {width: originalWidth, height: originalHeight};
};

Richfaces.Calendar.checkCollision = function(elementRect, windowRect, windowOffset)
{
	if (elementRect.left >= windowRect.left &&
		elementRect.top >= windowRect.top &&
		elementRect.right <= windowRect.right &&
		elementRect.bottom <= windowRect.bottom)
		return 0;

	var rect = {left:   (elementRect.left>windowRect.left ? elementRect.left : windowRect.left),
				top:    (elementRect.top>windowRect.top ? elementRect.top : windowRect.top),
				right:  (elementRect.right<windowRect.right ? elementRect.right : windowRect.right),
				bottom: (elementRect.bottom<windowRect.bottom ? elementRect.bottom : windowRect.bottom)};
	return (rect.right-rect.left)* (rect.bottom-rect.top);
};


Richfaces.Calendar.getWindowDimensions = function() {
    var w =  self.innerWidth
                || document.documentElement.clientWidth
                || document.body.clientWidth
                || 0;
    var h =  self.innerHeight
                || document.documentElement.clientHeight
                || document.body.clientHeight
                || 0;
	return {width:w, height: h};
};

Richfaces.Calendar.getWindowScrollOffset = function() {
    var dx =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    var dy =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
	return {left:dx, top: dy};
};

Richfaces.Calendar.getWindowViewport = function() {
	var windowDim = Richfaces.Calendar.getWindowDimensions();
	var windowOffset = Richfaces.Calendar.getWindowScrollOffset();
	return {left:windowOffset.left, top:windowOffset.top, right: windowDim.width+windowOffset.left, bottom: windowDim.height+windowOffset.top};
};

Richfaces.Calendar.clonePosition = function (elements, source)
{
		if (!elements.length) elements = [elements];
		var offset = Position.cumulativeOffset(source);
		offset = {left:offset[0], top:offset[1]};
		var offsetTemp;
		if (source.style.position!='absolute')
		{
			offsetTemp = Position.realOffset(source);
			offset.left -= offsetTemp.left;
			offset.top -= offsetTemp.top;
			offsetTemp = Richfaces.Calendar.getWindowScrollOffset();
			offset.left += offsetTemp.left;
			offset.top += offsetTemp.top;
		}

		for (var i=0;i<elements.length;i++)
		{
			offsetTemp = Richfaces.Calendar.getParentOffset(elements[i]);
			elements[i].style.left = (offset.left - offsetTemp.left) + 'px';
			elements[i].style.top = (offset.top - offsetTemp.top) + 'px';
		}
		return offset;
};

Richfaces.Calendar.getParentOffset = function(element)
{
		var offset = {left:0,top:0};
		var els = element.style;
		if (els.display!='none')
		{
			if (element.offsetParent && element.offsetParent!=document.body)
				offset = Position.cumulativeOffset(element.offsetParent);
		}
		else
		{
			var originalVisibility = els.visibility;
			var originalPosition = els.position;
			var originalDisplay = els.display;
			els.visibility = 'hidden';
			els.position = 'absolute';
			els.display = '';
			if (element.offsetParent && element.offsetParent!=document.body)
				offset = Position.cumulativeOffset(element.offsetParent);
			els.display = originalDisplay;
			els.position = originalPosition;
			els.visibility = originalVisibility;
		}

		return offset;
};

Richfaces.Calendar.joinArray = function(array, begin, end, separator)
{
	var value = '';
	if (array.length!=0) value = begin+array.pop()+end;
	while (array.length)
		value = begin+array.pop()+end+separator+value;
	return value;
};

Richfaces.Calendar.getMonthByLabel = function (monthLabel, monthNames)
{
	var i=0;
	while (i<monthNames.length) if (monthNames[i]==monthLabel) return i; else i++;
};

Object.extend(Event, {
	findElementByAttr : function(event, tagName, attribute, value, flag) {
    	var element = Event.findElement(event, tagName);
    	while (!element[attribute] || (flag ? element[attribute].indexOf(value)!=0 : element[attribute]!=value) )
    	{
	      	element = element.parentNode;
	    }
    	return element;
	}
});

Object.extend(Element, {
	replaceClassName : function (element, whichClassName, toClassName) {
		if (!(element = $(element))) return;
	    var e = Element.classNames(element);
	    e.remove(whichClassName);
	    e.add(toClassName);
	    return element;
	}
});

/* Year:
 *	y,yy - 00-99
 *	yyy+ - 1999
 * Month:
 *	M - 1-12
 *	MM - 01-12
 *	MMM - short (Jul)
 *	MMMM+ - long (July)
 * Date:
 *	d - 1-31
 *	dd+ - 01-31 */
Richfaces.Calendar.getDefaultMonthNames = function(shortNames)
{
	return (shortNames
			? ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']
			: ['January','February','March','April','May','June','July','August','September','October','November','December']);
};

Richfaces.Calendar.parseDate = function(dateString, pattern, monthNames, monthNamesShort)
{
	var re = /([.*+?^<>=!:${}()[\]\/\\])/g;
	var monthNamesStr
	var monthNamesShortStr;
	if (!monthNames)
	{
		monthNames = Richfaces.Calendar.getDefaultMonthNames();
		monthNamesStr = monthNames.join('|');
	}
	else
	{
		monthNamesStr = monthNames.join('|').replace(re, '\\$1');
	}
	if (!monthNamesShort)
	{
		monthNamesShort = Richfaces.Calendar.getDefaultMonthNames(true);
		monthNamesShortStr = monthNamesShort.join('|');
	}
	else
	{
		monthNamesShortStr = monthNamesShort.join('|').replace(re, '\\$1');
	}

	var counter=1;
	var y,m,d;
	var a,h,min;
	var shortLabel=false;

	pattern = pattern.replace(/([.*+?^<>=!:${}()|[\]\/\\])/g, '\\$1');
	pattern = pattern.replace(/(y+|M+|d+|a|H{1,2}|h{1,2}|m{2})/g,
		function($1) {
			switch ($1) {
	            case 'y'  :
	            case 'yy' : y=counter; counter++; return '(\\d{2})';
	            case 'MM' : m=counter; counter++; return '(\\d{2})';
	            case 'M'  : m=counter; counter++; return '(\\d{1,2})';
	            case 'd'  : d=counter; counter++; return '(\\d{1,2})';
	            case 'MMM': m=counter; counter++; shortLabel=true; return '('+monthNamesShortStr+')';
	            case 'a'  : a=counter; counter++; return '(AM|am|PM|pm)?';
	            case 'HH' :
	            case 'hh' : h=counter; counter++; return '(\\d{2})?';
	            case 'H'  :
	            case 'h'  : h=counter; counter++; return '(\\d{1,2})?';
	            case 'mm' : min=counter; counter++; return '(\\d{2})?';
			}
	        // y+,M+,d+
			var ch = $1.charAt(0);
			if (ch=='y') {y=counter; counter++; return '(\\d{3,4})'};
			if (ch=='M') {m=counter; counter++; return '('+monthNamesStr+')'};
			if (ch=='d') {d=counter; counter++; return '(\\d{2})'};
		}
	);

	var re = new RegExp(pattern,'i');
	var match = dateString.match(re);
	if (match!=null)
	{
		var yy = parseInt(match[y],10); if (isNaN(yy)) return null; else if (yy<70) yy+=2000; else if (yy<100) yy+=1900;
		var mm = parseInt(match[m],10); if (isNaN(mm)) mm = Richfaces.Calendar.getMonthByLabel(match[m], shortLabel ? monthNamesShort : monthNames); else if (--mm<0 || mm>11) return null;
		var dd = parseInt(match[d],10); if (isNaN(dd) || dd<1 || dd>daysInMonth(yy, mm)) return null;

		// time parsing
		if (min!=undefined && h!=undefined)
		{
			var hh,mmin,aa;
			mmin = parseInt(match[min],10); if (isNaN(mmin) || mmin<0 || mmin>59) return null;
			hh = parseInt(match[h],10); if (isNaN(hh)) return null;
			if (a!=undefined)
			{
				aa = match[a];
				if (!aa) return null;
				aa = aa.toLowerCase();
				if ((aa!='am' && aa!='pm') || hh<1 || hh>12) return null;
				if (aa=='pm')
				{
					if (hh!=12) hh+=12;
				} else if (hh==12) hh = 0;
			}
			else if (hh<0 || hh>23) return null;

			return new Date(yy, mm, dd, hh, mmin, 0);
		}

		return new Date(yy, mm, dd);
	}
	return null;
};

Richfaces.Calendar.formatDate = function(date, pattern, monthNames, monthNamesShort) {
	if (!monthNames) monthNames = Richfaces.Calendar.getDefaultMonthNames();
	if (!monthNamesShort) monthNamesShort = Richfaces.Calendar.getDefaultMonthNames(true);
	var mm; var dd; var hh; var min;
    var result = pattern.replace(/(\\\\|\\[yMdaHhm])|(y+|M+|d+|a|H{1,2}|h{1,2}|m{2})/g,
        function($1,$2,$3) {
        	if ($2) return $2.charAt(1);
			switch ($3) {
	            case 'y':
	            case 'yy':  return date.getYear().toString().slice(-2);
	            case 'M':   return (date.getMonth()+1);
	            case 'MM':  return ((mm = date.getMonth()+1)<10 ? '0'+mm : mm);
	            case 'MMM': return monthNamesShort[date.getMonth()];
		        case 'd':   return date.getDate();
	            case 'a'  : return (date.getHours()<12 ? 'AM' : 'PM');
	            case 'HH' : return ((hh = date.getHours())<10 ? '0'+hh : hh);
	            case 'H'  : return date.getHours();
	            case 'hh' : return ((hh = date.getHours())==0 ? '12' : (hh<10 ? '0'+hh : (hh>21 ? hh-12 : (hh>12) ? '0'+(hh-12) : hh)));
	            case 'h'  : return ((hh = date.getHours())==0 ? '12' : (hh>12 ? hh-12 : hh));
	            case 'mm' : return ((min = date.getMinutes())<10 ? '0'+min : min);
			}
	        // y+,M+,d+
			var ch = $3.charAt(0);
			if (ch=='y') return date.getFullYear();
			if (ch=='M') return monthNames[date.getMonth()];
			if (ch=='d') return ((dd = date.getDate())<10 ? '0'+dd : dd);
		}
	);
	return result;
};

Richfaces.Calendar.escape = function (str)
{
	return str.replace(/([yMdaHhm\\])/g,"\\$1");
};

Richfaces.Calendar.unescape = function (str)
{
	return str.replace(/\\([yMdaHhm\\])/g,"$1");
};



function isLeapYear(year) {
	return new Date(year, 1, 29).getDate()==29;
}

function daysInMonth(year,month) {
	return 32 - new Date(year, month, 32).getDate();
}

function daysInMonthByDate(date) {
	return 32 - new Date(date.getFullYear(), date.getMonth(), 32).getDate();
}

function getDay(date, firstWeekDay ) {
	var value = date.getDay() - firstWeekDay;
	if (value < 0) value = 7 + value;
	return value;
}

function getFirstWeek(year, mdifw, fdow) {
	var date = new Date(year,0,1);
	var firstday = getDay(date, fdow);

	var weeknumber = (7-firstday<mdifw) ? 0 : 1;

	return {date:date, firstDay:firstday, weekNumber:weeknumber, mdifw:mdifw, fdow:fdow};
}

function getLastWeekOfPrevYear(o) {
	var year = o.date.getFullYear()-1;
	var days = (isLeapYear(year) ? 366 : 365);
	var obj = getFirstWeek(year, o.mdifw, o.fdow);
	days = (days - 7 + o.firstDay);
	var weeks = Math.floor(days/7)+1;

	return  weeks+obj.weekNumber;
}

function weekNumber(year, month, mdifw, fdow) {

	var o = getFirstWeek(year, mdifw, fdow);

	if (month==0)
	{
		if (o.weekNumber==1) return 1;
		return getLastWeekOfPrevYear(o);
	}
	var	oneweek =  604800000;
	var d = new Date(year, month,1);
		d.setDate( 1+o.firstDay + (getDay(d,fdow)==0?1:0));

	weeknumber = o.weekNumber + Math.floor((d.getTime() - o.date.getTime()) / oneweek);

	return weeknumber;
}

Calendar = Class.create();
Object.extend(Calendar.prototype, {
    initialize: function(id,parameters) {
		// dayListTableId, weekNumberBarId, weekDayBarId - 3 tables ids',

		// dayListMarkup - day cell markup
		//		context: {day, date, weekNumber, weekDayNumber, isWeekend, isCurrentMonth,  elementId, component}
		// weekNumberMarkup - week number cell markup
		//		context: {weekNumber, elementId, component}
		// weekDayMarkup - week day cell markup
		//		context: {weekDayLabel, weekDayLabelShort, weekDayNumber, isWeekend, elementId, component}

		// headerMarkup
		// footerMarkup
		// optionalHeaderMarkup - user defined header (optional)
		// optionalFooterMarkup - user defined footer (optional)

		// currentDate - date to show month (day not used) (mm/yyyy)
		// selectedDate - selected date (mm/dd/yyyy)
		// weekDayLabels - collection of week day labels keyed by week day numbers
		// weekDayLabelsShort - collection of week day short labels keyed by week day numbers
		// minDaysInFirstWeek - locale-specific constant defining number of days in the first week
		// firstWeekDay - (0..6) locale-specific constant defining number of the first week day
		// showWeekDaysBar - show WeekDays Bar [default value is true]
		// showWeeksBar - show Weeks numbers bar [default value is true]
		// showApplyButton
		// showHeader
		// showFooter

		// POPUP description
		// direction - [top-left, top-right, bottom-left, bottom-right, auto]
		// jointPoint - [top-left, top-right, bottom-left, bottom-right]
		// popup - true
		// id+PopupButton, id+InputDate,

		// boundaryDatesMode - boundary dates onclick action:
		// 						"inactive" or undefined - no action (default)
		//						"scroll" - change current month
		//						"select" - change current month and select date
		//
		// todayControlMode - today control onclick action:
		//						"scroll"
		//						"select"
		//						"hidden"

		// isDayEnabled - end-developer JS function
		// dayStyleClass - end-developer JS function that provide style class for day's cells.

		// dayCellClass - add div to day cell with class 'rich-calendar-cell-div' and add this class to TD if defined
		// style - table style
		// className - table class

		// disabled
		// readonly

		//var _d = new Date();

		this.id = id;
		this.params = parameters;

		this.showApplyButton = (!this.params.popup) ? false : this.params.showApplyButton;

		if (this.params.showWeekDaysBar==undefined) this.params.showWeekDaysBar = true;
		if (this.params.showWeeksBar==undefined) this.params.showWeeksBar = true;

		if (!this.params.datePattern) this.params.datePattern = "MMM d, y";

		// time
		this.setTimeProperties();

		// markups initialization
		if (!this.params.dayListMarkup)
		{
			this.params.dayListMarkup = CalendarView.dayList;
			this.customDayListMarkup = false;
		}
		else
		{
			this.customDayListMarkup = true;
		}
		if (!this.params.weekNumberMarkup) this.params.weekNumberMarkup = CalendarView.weekNumber;
		if (!this.params.weekDayMarkup) this.params.weekDayMarkup = CalendarView.weekDay;
		if (!this.params.headerMarkup) this.params.headerMarkup = CalendarView.header;
		if (!this.params.footerMarkup) this.params.footerMarkup = CalendarView.footer;

		// popup offset
		this.popupOffset = {dx: (isNaN(this.params.horizontalOffset) ? 0 : parseInt(this.params.horizontalOffset,10)), dy: (isNaN(this.params.verticalOffset) ? 0 : parseInt(this.params.verticalOffset,10))};

		this.currentDate = this.params.currentDate ? this.params.currentDate : (this.params.selectedDate ? this.params.selectedDate : new Date());
		this.currentDate.setDate(1);
		this.selectedDate = this.params.selectedDate;

		if (typeof this.params.boundaryDatesMode=="string") this.params.boundaryDatesMode = this.params.boundaryDatesMode.toLowerCase();
		if (typeof this.params.todayControlMode=="string") this.todayControlMode = this.params.todayControlMode.toLowerCase();

		if (typeof this.params.isDayEnabled != "function") this.params.isDayEnabled = function (context) {return true;};
		if (typeof this.params.dayStyleClass != "function") this.params.dayStyleClass = function (context) {return "";};

		this.todayDate = new Date();

		this.firstWeekendDayNumber = 6-this.params.firstWeekDay;
		this.secondWeekendDayNumber = (this.params.firstWeekDay>0 ? 7-this.params.firstWeekDay : 0);

		this.calendarContext = new CalendarContext(this);

		this.DATE_ELEMENT_ID = this.params.dayListTableId+'Cell';
		this.WEEKNUMBER_ELEMENT_ID = this.params.weekNumberBarId+'Cell';
		this.WEEKDAY_ELEMENT_ID = this.params.weekDayBarId+'Cell';
		this.POPUP_ID = this.id+'Popup';
		this.POPUP_BUTTON_ID = this.id+'PopupButton';
		this.INPUT_DATE_ID = this.id+'InputDate';
		this.IFRAME_ID = this.id+'IFrame';
		this.EDITOR_ID = this.id+'Editor';
		this.EDITOR_SHADOW_ID = this.id+'EditorShadow';

		this.TIME_EDITOR_LAYOUT_ID = this.id+'TimeEditorLayout';
		this.DATE_EDITOR_LAYOUT_ID = this.id+'DateEditorLayout';
		this.EDITOR_LAYOUT_SHADOW_ID = this.id+'EditorLayoutShadow';
		this.TIME_EDITOR_BUTTON_OK = this.id+'TimeEditorButtonOk';
		this.TIME_EDITOR_BUTTON_CANCEL = this.id+'TimeEditorButtonCancel';
		this.DATE_EDITOR_BUTTON_OK = this.id+'DateEditorButtonOk';
		this.DATE_EDITOR_BUTTON_CANCEL = this.id+'DateEditorButtonCancel';


		//this.popupIntervalId=null;

		this.firstDateIndex = 0;

		this.daysData = {startDate:null, days:[]};
		this.days = [];
		this.todayCellId = null;
		this.todayCellColor = "";

		this.selectedDateCellId = null;
		this.selectedDateCellColor = "";

		var popupStyles = "";
		this.isVisible = true;
		if (this.params.popup==true)
		{
			// popup mode initialisation
			popupStyles = "display:none; position:absolute;"
			this.isVisible = false;
		}

		var tempStr = "$('"+this.id+"').component.";

		var htmlTextHeader = '<table id="'+this.id+'" border="0" cellpadding="0" cellspacing="0" class="rich-calendar-exterior rich-calendar-popup'+(this.params.className ? ' '+this.params.className : '')+'" style="'+popupStyles+this.params.style+'" onclick="'+tempStr+'skipEventOnCollapse=true;"><tbody>';
		var colspan = (this.params.showWeeksBar ? "8" : "7");
		var htmlHeaderOptional = (this.params.optionalHeaderMarkup) ? '<tr><td class="rich-calendar-header-optional" colspan="'+colspan+'" id="'+this.id+'HeaderOptional"></td></tr>' : '';
		var htmlFooterOptional = (this.params.optionalFooterMarkup) ? '<tr><td class="rich-calendar-footer-optional" colspan="'+colspan+'" id="'+this.id+'FooterOptional"></td></tr>' : '';
		var htmlControlsHeader = (this.params.showHeader ? '<tr><td class="rich-calendar-header" colspan="'+colspan+'" id="'+this.id+'Header"></td></tr>' : '');
		var htmlControlsFooter = (this.params.showFooter ? '<tr><td class="rich-calendar-footer" colspan="'+colspan+'" id="'+this.id+'Footer"></td></tr>' : '');
		var htmlTextFooter = '</tbody></table>'
		var htmlTextIFrame = '<iframe src="javascript:\'\'" frameborder="0" scrolling="no" id="' + this.IFRAME_ID + '" style="display:none; position: absolute; width: 1px; height: 1px; background-color:white;">'+'</iframe>';

		// days bar creation
		var styleClass;
		var bottomStyleClass;
		var htmlTextWeekDayBar=[];
		var context;

		var eventsStr = this.params.disabled || this.params.readonly ? '' : 'onclick="'+tempStr+'eventCellOnClick(event, this);" onmouseover="'+tempStr+'eventCellOnMouseOver(event, this);" onmouseout="'+tempStr+'eventCellOnMouseOut(event, this);"';
		if (this.params.showWeekDaysBar)
		{
			htmlTextWeekDayBar.push('<tr id="'+this.params.weekDayBarId+'">');
			if (this.params.showWeeksBar) htmlTextWeekDayBar.push('<td class="rich-calendar-days"><br/></td>');
			var weekDayCounter = this.params.firstWeekDay;
			for (var i=0;i<7;i++)
			{
				context = {weekDayLabel: this.params.weekDayLabels[weekDayCounter], weekDayLabelShort: this.params.weekDayLabelsShort[weekDayCounter], weekDayNumber:weekDayCounter, isWeekend:this.isWeekend(i), elementId:this.WEEKDAY_ELEMENT_ID+i, component:this};
				var weekDayHtml = this.evaluateMarkup(this.params.weekDayMarkup, context );
				if (weekDayCounter==6) weekDayCounter=0; else weekDayCounter++;

				styleClass = "rich-calendar-days";
				if (context.isWeekend)
				{
					styleClass += " rich-calendar-weekends";
				}
				if (i==6) styleClass += " rich-right-cell";
				htmlTextWeekDayBar.push('<td class="'+styleClass+'" id="'+context.elementId+'">'+weekDayHtml+'</td>');
			}
			htmlTextWeekDayBar.push('</tr>\n');
		}

		// week & weekNumber creation
		var htmlTextWeek=[];
		var p=0;
		this.dayCellClassName = [];

		for (k=1;k<7;k++)
		{
			bottomStyleClass = (k==6 ? "rich-bottom-cell " : "");
			htmlTextWeek.push('<tr id="'+this.params.weekNumberBarId+k+'">');
			if (this.params.showWeeksBar)
			{
				context = {weekNumber: k, elementId:this.WEEKNUMBER_ELEMENT_ID+k, component:this};
				var weekNumberHtml = this.evaluateMarkup(this.params.weekNumberMarkup, context );
				htmlTextWeek.push('<td class="rich-calendar-week '+bottomStyleClass+'" id="'+context.elementId+'">'+weekNumberHtml+'</td>');
			}

			// day cells creation
			for (var i=0;i<7;i++)
			{
				styleClass = bottomStyleClass+(!this.params.dayCellClass ? "rich-calendar-cell-size" : (!this.customDayListMarkup ? this.params.dayCellClass : ""))+" rich-calendar-cell";
				if (i==this.firstWeekendDayNumber || i==this.secondWeekendDayNumber) styleClass+=" rich-calendar-holly";
				if (i==6) styleClass+=" rich-right-cell";

				this.dayCellClassName.push(styleClass);
				htmlTextWeek.push('<td class="'+styleClass+'" id="'+this.DATE_ELEMENT_ID+p+'" '+
				eventsStr+
				'>'+(this.customDayListMarkup ? '<div class="rich-calendar-cell-div'+(this.params.dayCellClass ? ' '+this.params.dayCellClass : '')+'"></div>' : '')+'</td>');
				p++;
			}
			htmlTextWeek.push('</tr>');
		}

		var obj = $(this.POPUP_ID).nextSibling;
		if (this.params.popup && Richfaces.browser.isIE6)
		{
			do {
				if (obj.id == this.IFRAME_ID)
				{
					var iframe = obj;
					obj = obj.nextSibling;
					Element.replace(iframe, htmlTextIFrame);
					break;
				}
			} while (obj = obj.nextSibling);
		}

		do {
			if (obj.id == id)
			{
				var div = obj;
				obj = obj.previousSibling;
				Element.replace(div, htmlTextHeader+htmlHeaderOptional+htmlControlsHeader+htmlTextWeekDayBar.join('')+htmlTextWeek.join('')+htmlControlsFooter+htmlFooterOptional+htmlTextFooter);
				break;
			}
		} while (obj = obj.nextSibling);

		// set content
		obj=obj.nextSibling;
		obj.component = this;
		obj.richfacesComponent="richfaces:calendar";
		this["rich:destructor"] = "destructor";

		// memory leaks fix
		obj = null;

		if(this.params.submitFunction)	this.submitFunction = this.params.submitFunction.bind(this);
		this.prepareEvents();

		// add onclick event handlers to input field and popup button
		if (this.params.popup && !this.params.disabled)
		{
  		// hack, see NXP-8153
  		var popupEl = document.getElementById(this.POPUP_BUTTON_ID);
  		if (!(popupEl.calendarClickObserved)) {
  		 popupEl.calendarClickObserved = true;
			  var handler = new Function ('event', "$('"+this.id+"').component.doSwitch();").bindAsEventListener();
			  Event.observe(this.POPUP_BUTTON_ID, "click", handler, false);
  			if (!this.params.enableManualInput)
  			{
  				Event.observe(this.INPUT_DATE_ID, "click", handler, false);
  			}
  		}
		}

		this.scrollElements = null;

		//alert(new Date().getTime()-_d.getTime());

	},

	destructor: function()
	{
		if (this.params.popup && this.isVisible)
		{
			Richfaces.removeScrollEventHandlers(this.scrollElements, this.eventOnScroll);
			Event.stopObserving(window.document, "click", this.eventOnCollapse, false);
		}
	},

	dateEditorSelectYear: function(value)
	{
		if (this.dateEditorYearID)
		{
			Element.removeClassName(this.dateEditorYearID, 'rich-calendar-editor-btn-selected');
		}
		this.dateEditorYear = this.dateEditorStartYear + value;
		this.dateEditorYearID = this.DATE_EDITOR_LAYOUT_ID+'Y'+value;
		Element.addClassName(this.dateEditorYearID, 'rich-calendar-editor-btn-selected');
	},

	dateEditorSelectMonth: function(value)
	{
		this.dateEditorMonth = value;
		Element.removeClassName(this.dateEditorMonthID, 'rich-calendar-editor-btn-selected');
		this.dateEditorMonthID = this.DATE_EDITOR_LAYOUT_ID+'M'+value;
		Element.addClassName(this.dateEditorMonthID, 'rich-calendar-editor-btn-selected');
	},

	scrollEditorYear: function(value)
	{
		var element = $(this.DATE_EDITOR_LAYOUT_ID+'TR');

		if (this.dateEditorYearID)
		{
			Element.removeClassName(this.dateEditorYearID, 'rich-calendar-editor-btn-selected');
			this.dateEditorYearID='';
		}

		if (!value)
		{
			// update month selection when open editor (value == 0)
			if (this.dateEditorMonth != this.getCurrentMonth())
			{
				this.dateEditorMonth = this.getCurrentMonth();
				Element.removeClassName(this.dateEditorMonthID, 'rich-calendar-editor-btn-selected');
				this.dateEditorMonthID = this.DATE_EDITOR_LAYOUT_ID+'M'+this.dateEditorMonth;
				Element.addClassName(this.dateEditorMonthID, 'rich-calendar-editor-btn-selected');
			}
		}

		if (element)
		{
			var div;
			var year = this.dateEditorStartYear = this.dateEditorStartYear+value*10;
			for (var i=0;i<5;i++)
			{
				element = element.nextSibling;
				div = element.firstChild.nextSibling.nextSibling;
				div.firstChild.innerHTML=year;
				if (year == this.dateEditorYear)
				{
					Element.addClassName(div.firstChild, 'rich-calendar-editor-btn-selected');
					this.dateEditorYearID = div.firstChild.id;
				}
				div = div.nextSibling;
				div.firstChild.innerHTML=year+5;
				if (year+5  == this.dateEditorYear)
				{
					Element.addClassName(div.firstChild, 'rich-calendar-editor-btn-selected');
					this.dateEditorYearID = div.firstChild.id;
				}
				year++;
			}
		}
	},

	updateDateEditor: function()
	{
		this.dateEditorYear = this.getCurrentYear();
		this.dateEditorStartYear = this.getCurrentYear() - 4;
		this.scrollEditorYear(0);
	},

	updateTimeEditor: function()
	{
		var th=$(this.id+'TimeHours');
		var ts=$(this.id+'TimeSign');
		var tm=$(this.id+'TimeMinutes');

		var h = this.selectedDate.getHours();
		var m = this.selectedDate.getMinutes();
		if (this.timeType==2)
		{
			var a = (h<12 ? 'AM' : 'PM');
			ts.value = a;
			h = (h==0 ? '12' : (h>12 ? h-12 : h));
		}
		th.value = (this.timeHoursDigits==2 && h<10 ? '0'+h : h);
		tm.value = (m<10 ? '0'+m : m);
	},


	createEditor: function()
	{
		var element = $(this.id);
		var htmlBegin = '<div id="'+this.EDITOR_SHADOW_ID+'" class="rich-calendar-editor-shadow" style="position:absolute; display:none;"></div><table border="0" cellpadding="0" cellspacing="0" id="'+this.EDITOR_ID+'" style="position:absolute; display:none;" onclick="$(\''+this.id+'\').component.skipEventOnCollapse=true;"><tbody><tr><td class="rich-calendar-editor-container" align="center"><div style="position:relative; width:100%">';
		var htmlContent = '<div id="'+this.EDITOR_LAYOUT_SHADOW_ID+'" class="rich-calendar-editor-layout-shadow"></div>';

		var htmlEnd = '</div></td></tr></tbody></table>';
		new Insertion.After(element, htmlBegin+htmlContent+htmlEnd);
		//+this.evaluateMarkup(CalendarView.timeEditor, this.calendarContext)+
		var editor_shadow = $(this.EDITOR_SHADOW_ID);
		var editor = $(this.EDITOR_ID);
		var zindex = element.getStyle('z-index');
		editor_shadow.style.zIndex = zindex;
		editor.style.zIndex = parseInt(zindex,10)+1;

		this.isEditorCreated = true;

		return editor;
	},

	createTimeEditorLayout: function(editor)
	{
		Element.insert(this.EDITOR_LAYOUT_SHADOW_ID, {after:this.evaluateMarkup(this.calendarContext.timeEditorLayout, this.calendarContext)});

		var th=$(this.id+'TimeHours');
		var ts;
		var tm=$(this.id+'TimeMinutes');
		if (this.timeType==1)
		{
			sbjQuery(th).SpinButton({digits:this.timeHoursDigits,min:0,max:23});
		}
		else
		{
			sbjQuery(th).SpinButton({digits:this.timeHoursDigits,min:1,max:12});
			ts=$(this.id+'TimeSign');
			sbjQuery(ts).SpinButton({});
		}
		sbjQuery(tm).SpinButton({digits:2,min:0,max:59});

		this.correctEditorButtons(editor, this.TIME_EDITOR_BUTTON_OK, this.TIME_EDITOR_BUTTON_CANCEL);

		this.isTimeEditorLayoutCreated = true;
	},

	correctEditorButtons: function(editor, buttonID1, buttonID2)
	{
		var button1 = $(buttonID1);
		var button2 = $(buttonID2);
		editor.style.visibility = "hidden";
		editor.style.display = "";
		var width1 = Richfaces.Calendar.getOffsetDimensions(button1.firstChild).width;
		var width2 = Richfaces.Calendar.getOffsetDimensions(button2.firstChild).width;
		editor.style.display = "none";
		editor.style.visibility = "";

		var styleWidth = Richfaces.getComputedStyleSize(button1,'width')

		if (width1>styleWidth || width2>styleWidth)
		{
			button1.style.width = button2.style.width = (width1>width2 ? width1 : width2)+"px";
		}
	},

	createDECell: function(id, value, buttonType, param, className)
	{
		if (buttonType==0)
		{
			return '<div id="'+id+'" class="rich-calendar-editor-btn'+(className ? ' '+className : '')+
			                      '" onmouseover="this.className=\'rich-calendar-editor-btn rich-calendar-editor-tool-over\';" onmouseout="this.className=\'rich-calendar-editor-btn\';" onmousedown="this.className=\'rich-calendar-editor-btn rich-calendar-editor-tool-press\';" onmouseup="this.className=\'rich-calendar-editor-btn rich-calendar-editor-tool-over\';" onclick="$(\''+this.id+'\').component.scrollEditorYear('+param+');">'+value+'</div>';
		}
		else
		{
			var onclick = (buttonType==1 ? '$(\''+this.id+'\').component.dateEditorSelectMonth('+param+');':
					   				    '$(\''+this.id+'\').component.dateEditorSelectYear('+param+');' );
			return '<div id="'+id+'" class="rich-calendar-editor-btn'+(className ? ' '+className : '')+
								  '" onmouseover="Element.addClassName(this, \'rich-calendar-editor-btn-over\');" onmouseout="Element.removeClassName(this,\'rich-calendar-editor-btn-over\');" onclick="'+onclick+'">'+value+'</div>';
		}
	},

	createDateEditorLayout: function(editor)
	{
		var htmlBegin = '<table id="'+this.DATE_EDITOR_LAYOUT_ID+'" class="rich-calendar-date-layout" border="0" cellpadding="0" cellspacing="0"><tbody><tr id="'+this.DATE_EDITOR_LAYOUT_ID+'TR">';
		var htmlEnd = '</tr></tbody></table>';
		var month = 0;
		this.dateEditorYear = this.getCurrentYear();
		var year = this.dateEditorStartYear = this.dateEditorYear-4;
		var htmlContent = '<td align="center">'+this.createDECell(this.DATE_EDITOR_LAYOUT_ID+'M'+month, this.params.monthLabelsShort[month], 1, month)+'</td>'
						 +'<td align="center" class="rich-calendar-date-layout-split">'+this.createDECell(this.DATE_EDITOR_LAYOUT_ID+'M'+(month+6), this.params.monthLabelsShort[month+6], 1, month+6)+'</td>'
						 +'<td align="center">'+this.createDECell('','&lt;', 0, -1)+'</td>'
						 +'<td align="center">'+this.createDECell('','&gt;', 0, 1)+'</td>';
			month++;

		for (var i=0;i<5;i++)
		{
			htmlContent+='</tr><tr><td align="center">'+this.createDECell(this.DATE_EDITOR_LAYOUT_ID+'M'+month, this.params.monthLabelsShort[month], 1, month)+'</td>'
						+'<td align="center" class="rich-calendar-date-layout-split">'+this.createDECell(this.DATE_EDITOR_LAYOUT_ID+'M'+(month+6), this.params.monthLabelsShort[month+6], 1, month+6)+'</td>'
						+'<td align="center">'+this.createDECell(this.DATE_EDITOR_LAYOUT_ID+'Y'+i, year, 2, i, (i==4 ? 'rich-calendar-editor-btn-selected' : ''))+'</td>'
						+'<td align="center">'+this.createDECell(this.DATE_EDITOR_LAYOUT_ID+'Y'+(i+5), year+5, 2, i+5)+'</td>';
			month++;
			year++;
		}
		this.dateEditorYearID = this.DATE_EDITOR_LAYOUT_ID+'Y4';
		this.dateEditorMonth = this.getCurrentMonth();
		this.dateEditorMonthID = this.DATE_EDITOR_LAYOUT_ID+'M'+this.dateEditorMonth;

		htmlContent+='</tr><tr><td colspan="2" class="rich-calendar-date-layout-ok">'+
					 '<div id="'+this.DATE_EDITOR_BUTTON_OK+'" class="rich-calendar-time-btn" style="float:right;" onmousedown="Element.addClassName(this, \'rich-calendar-time-btn-press\');" onmouseout="Element.removeClassName(this, \'rich-calendar-time-btn-press\');" onmouseup="Element.removeClassName(this, \'rich-calendar-time-btn-press\');" onclick="$(\''+this.id+'\').component.hideDateEditor(true);"><span>'+this.params.labels.ok+'</span></div>'+
					 '</td><td colspan="2" class="rich-calendar-date-layout-cancel">'+
					 '<div id="'+this.DATE_EDITOR_BUTTON_CANCEL+'" class="rich-calendar-time-btn" style="float:left;" onmousedown="Element.addClassName(this, \'rich-calendar-time-btn-press\');" onmouseout="Element.removeClassName(this, \'rich-calendar-time-btn-press\');" onmouseup="Element.removeClassName(this, \'rich-calendar-time-btn-press\');" onclick="$(\''+this.id+'\').component.hideDateEditor(false);"><span>'+this.params.labels.cancel+'</span></div>'+
					 '</td>';


		Element.insert(this.EDITOR_LAYOUT_SHADOW_ID, {after:htmlBegin+htmlContent+htmlEnd});

		Element.addClassName(this.dateEditorMonthID, 'rich-calendar-editor-btn-selected');

		this.correctEditorButtons(editor, this.DATE_EDITOR_BUTTON_OK, this.DATE_EDITOR_BUTTON_CANCEL);

		this.isDateEditorLayoutCreated = true;
	},

	createSpinnerTable: function(id) {
		return '<table cellspacing="0" cellpadding="0" border="0"><tbody><tr>'+
					'<td class="rich-calendar-spinner-input-container">'+
						'<input id="' + id + '" name="' + id + '" class="rich-calendar-spinner-input" type="text" />'+
					'</td>'+
					'<td class="rich-calendar-spinner-buttons">'+
						'<table border="0" cellspacing="0" cellpadding="0"><tbody>'+
							'<tr><td>'+
								'<div id="'+id+'BtnUp" class="rich-calendar-spinner-up"'+
									' onmousedown="this.className=\'rich-calendar-spinner-up rich-calendar-spinner-pressed\'"'+
									' onmouseup="this.className=\'rich-calendar-spinner-up\'"'+
									' onmouseout="this.className=\'rich-calendar-spinner-up\'"><span></span></div>'+
							'</td></tr>'+
							'<tr><td>'+
								'<div id="'+id+'BtnDown" class="rich-calendar-spinner-down"'+
									' onmousedown="this.className=\'rich-calendar-spinner-down rich-calendar-spinner-pressed\'"'+
									' onmouseup="this.className=\'rich-calendar-spinner-down\'"'+
									' onmouseout="this.className=\'rich-calendar-spinner-down\'"><span></span></div>'+
							'</td></tr>'+
						'</tbody></table>'+
					'</td>'+
				'</tr></tbody></table>';
	},

	setTimeProperties: function() {
		this.timeType = 0;

		var dateTimePattern = this.params.datePattern;
		var pattern = [];
		var re = /(\\\\|\\[yMdaHhm])|(y+|M+|d+|a|H{1,2}|h{1,2}|m{2})/g;
		var r;
		while (r = re.exec(dateTimePattern))
			if (!r[1])
  				pattern.push({str:r[0],marker:r[2],idx:r.index});

  		var datePattern = "";
  		var timePattern = "";

		var digits,h,hh,m,a;
		var id = this.id;

		var getString = function (p) {
			return (p.length==0 ? obj.marker : dateTimePattern.substring(pattern[i-1].str.length+pattern[i-1].idx, obj.idx+obj.str.length));
		};

  		for (var i=0;i<pattern.length;i++)
  		{
  			var obj = pattern[i];
  			var ch = obj.marker.charAt(0);
  			if (ch=='y'||ch=='M'||ch=='d') datePattern+=getString(datePattern);
  			else if (ch=='a')
  			{
  				a=true;
  				timePattern+=getString(timePattern);
  			}
  			else if (ch=='H')
  			{
  				h=true;
  				digits=obj.marker.length;
  				timePattern+=getString(timePattern);
  			}
  			else if (ch=='h')
  			{
  				hh=true;
  				digits=obj.marker.length;
  				timePattern+=getString(timePattern);
  			}
  			else if (ch=='m')
  			{
  				m=true;
  				timePattern+=getString(timePattern);
  			}


  		}
  		this.datePattern = datePattern;
  		this.timePattern = timePattern;

  		var calendar = this;

		this.timePatternHtml = timePattern.replace(/(\\\\|\\[yMdaHhm])|(H{1,2}|h{1,2}|m{2}|a)/g,
			function($1,$2,$3) {
				if ($2) return $2.charAt(1);
				switch ($3) {
		            case 'a'  : return '</td><td>'+calendar.createSpinnerTable(id+'TimeSign')+'</td><td>';
		            case 'H'  :
		            case 'HH' :
		            case 'h'  :
		            case 'hh' : return '</td><td>'+calendar.createSpinnerTable(id+'TimeHours')+'</td><td>';
		            case 'mm' : return '</td><td>'+calendar.createSpinnerTable(id+'TimeMinutes')+'</td><td>';
				}
			}
		);

		this.timePatternHtml = '<table border="0" cellpadding="0"><tbody><tr><td>'+this.timePatternHtml+'</td></tr></tbody></table>';

		if (m && h)
		{
			this.timeType = 1;
		}
		else if (m && hh && a)
		{
			this.timeType = 2;
		}
		this.timeHoursDigits = digits;
	},

	eventOnScroll: function (e) {
		/*this.doCollapse();*/
	},

	doCollapse: function() {

		if (!this.params.popup || !this.isVisible) return;

		if (this.isEditorVisible) this.hideEditor();

		var element = $(this.id);

		if (this.invokeEvent("collapse", element))
		{
			Richfaces.removeScrollEventHandlers(this.scrollElements, this.eventOnScroll);
			Event.stopObserving(window.document, "click", this.eventOnCollapse, false);

			var iframe=null;
			if (Richfaces.browser.isIE6) iframe = $(this.IFRAME_ID);
			if (iframe) Element.hide(iframe);

			Element.hide(element);
			this.isVisible = false;

		}
	},

	collapse: function() {
		this.doCollapse();
	},

	doExpand: function(e) {
		if (!this.isRendered) {
			this.isRendered = true;
			this.render();
		}
		this.skipEventOnCollapse = false;
		if (e && e.type=='click') this.skipEventOnCollapse = true;
		if (!this.params.popup || this.isVisible) return;

		var element = $(this.id);

		if (this.invokeEvent("expand", element, e))
		{
			var iframe=null;
			if (Richfaces.browser.isIE6) iframe = $(this.IFRAME_ID);

			var base = $(this.POPUP_ID)
			var baseInput = base.firstChild;
			var baseButton = baseInput.nextSibling;

			if (baseInput && baseInput.value!=undefined)
			{
				this.selectDate(baseInput.value, false, {event:e, element:element});
			}

			//rect calculation

			var offsetBase = Position.cumulativeOffset(baseButton);

			if (this.params.showInput)
			{
				var offsetBase1 = Position.cumulativeOffset(baseInput);

				offsetBase = [offsetBase[0]<offsetBase1[0] ? offsetBase[0] : offsetBase1[0],
							  offsetBase[1]<offsetBase1[1] ? offsetBase[1] : offsetBase1[1]];
				var offsetDimInput = Richfaces.Calendar.getOffsetDimensions(baseInput);
			}

			var offsetDimBase = Richfaces.Calendar.getOffsetDimensions(base);
			var offsetDimButton = Richfaces.Calendar.getOffsetDimensions(baseButton);
			var offsetTemp = (window.opera ? [0,0] : Position.realOffset(baseButton));
			//alert("offsetBase:"+offsetBase+" offsetTemp:"+offsetTemp+' scrollTop:'+baseButton.offsetParent.scrollTop+" offsetParent:"+baseButton.offsetParent);
			var o = {left: offsetBase[0]-offsetTemp[0],
					 top: offsetBase[1]-offsetTemp[1],
					 width: offsetDimBase.width,
					 height: (offsetDimInput && offsetDimInput.height>offsetDimButton.height ? offsetDimInput.height : offsetDimButton.height)};

			Richfaces.Calendar.setElementPosition(element, o, this.params.jointPoint, this.params.direction, this.popupOffset);

			if (iframe)
			{
				iframe.style.left = element.style.left;
				iframe.style.top = element.style.top;
				var edim = Richfaces.Calendar.getOffsetDimensions(element);
				iframe.style.width = edim.width+'px';
				iframe.style.height = edim.height+'px';
				Element.show(iframe);
			}
			Element.show(element);

			this.isVisible = true;

			Event.observe(window.document, "click", this.eventOnCollapse, false);

			Richfaces.removeScrollEventHandlers(this.scrollElements, this.eventOnScroll);
			this.scrollElements = Richfaces.setupScrollEventHandlers(element, this.eventOnScroll);
		}
	},

	expand: function(e) {
		this.doExpand(e);
	},

	doSwitch: function(e) {
		this.isVisible ? this.doCollapse() : this.doExpand(e);
	},

	switchState: function(e) {
		this.doSwitch(e);
	},

	eventOnCollapse: function (e) {
		if (this.skipEventOnCollapse)
		{
			this.skipEventOnCollapse = false;
			return true;
		}

		if (Event.element(e).id == this.POPUP_BUTTON_ID || (!this.params.enableManualInput && Event.element(e).id == this.INPUT_DATE_ID) ) return true;

		//Position.prepare();
		// TODO: remove line below and check functionality
		if (Position.within($(this.id), Event.pointerX(e), Event.pointerY(e))) return true;
		this.doCollapse();

		return true;
	},

	setInputField: function(dateStr, event)
	{
		var field = $(this.INPUT_DATE_ID);
		if (field.value!=dateStr)
		{
			field.value=dateStr;
			this.invokeEvent("changed",field, event, this.selectedDate);
		}
	},

	getCurrentDate: function() {
		return this.currentDate;
	},
	getSelectedDate: function() {
		if (!this.selectedDate) return null; else return this.selectedDate;
	},
	getSelectedDateString: function(pattern) {
		if (!this.selectedDate) return "";
		if (!pattern) pattern = this.params.datePattern;
		return Richfaces.Calendar.formatDate(this.selectedDate, pattern, this.params.monthLabels, this.params.monthLabelsShort);
	},

	getPrevYear: function() {
		var value = this.currentDate.getFullYear()-1;
		if (value<0) value = 0;
		return value;
	},
	getPrevMonth: function(asMonthLabel) {
		var value = this.currentDate.getMonth()-1;
		if (value < 0 ) value = 11;
		if (asMonthLabel)
		{
			return this.params.monthLabels[value];
		} else return value;
	},
	getCurrentYear: function() {
		return this.currentDate.getFullYear();
	},
	getCurrentMonth: function(asMonthLabel) {
		var value = this.currentDate.getMonth();
		if (asMonthLabel)
		{
			return this.params.monthLabels[value];
		} else return value;
	},
	getNextYear: function() {
		return this.currentDate.getFullYear()+1;
	},
	getNextMonth: function(asMonthLabel) {
		var value = this.currentDate.getMonth()+1;
		if (value > 11 ) value = 0;
		if (asMonthLabel)
		{
			return this.params.monthLabels[value];
		} else return value;
	},

	isWeekend: function(weekday) {
		return (weekday == this.firstWeekendDayNumber || weekday == this.secondWeekendDayNumber);
	},

	prepareEvents: function() {
		this.eventOnCollapse = this.eventOnCollapse.bindAsEventListener(this);
		this.eventOnScroll = this.eventOnScroll.bindAsEventListener(this);
	},

	invokeEvent: function(eventName, element, event, date) {
		var eventFunction = this.params['on'+eventName];
		var result;

		if (eventFunction)
		{
			var eventObj;

			if (event)
			{
				eventObj = event;
			}
			else if( document.createEventObject )
			{
				eventObj = document.createEventObject();
			}
			else if( document.createEvent )
			{
				eventObj = document.createEvent('Events');
				eventObj.initEvent( eventName, true, false );
			}

			eventObj.rich = {component:this};
			eventObj.rich.date = date;

			try
			{
				result = eventFunction.call(element, eventObj);
			}
			catch (e) { LOG.warn("Exception: "+e.Message + "\n[on"+eventName + "]"); }

		}

		if (result!=false) result = true;

		return result;
	},

	setupTimeForDate: function (date) {
		if (this.selectedDate && (!this.params.resetTimeOnDateSelect ||
			(this.selectedDate.getFullYear() == date.getFullYear() &&
			this.selectedDate.getMonth() == date.getMonth() &&
			this.selectedDate.getDate() == date.getDate())))
		{
			date.setHours(this.selectedDate.getHours());
			date.setMinutes(this.selectedDate.getMinutes());
		} else
		{
			date.setHours(this.params.defaultTime.hours);
			date.setMinutes(this.params.defaultTime.minutes);
		}
	},

	eventCellOnClick: function (e, obj) {
		var daydata = this.days[parseInt(obj.id.substr(this.DATE_ELEMENT_ID.length),10)];
		if (daydata.enabled && daydata._month==0)
		{
			var date=new Date(this.currentDate);
			date.setDate(daydata.day);
			if (this.timeType)
			{
				this.setupTimeForDate(date);
			}

			if (this.selectDate(date,true, {event:e, element:obj}) && !this.showApplyButton)
			{
				this.doCollapse();
			}

		} else if (daydata._month!=0){
			if (this.params.boundaryDatesMode == "scroll")
				if (daydata._month==-1) this.prevMonth(); else this.nextMonth();
			else if (this.params.boundaryDatesMode == "select")
			{
				var date = new Date(daydata.date);
				if (this.timeType)
				{
					this.setupTimeForDate(date);
				}

				if (this.selectDate(date, false, {event:e, element:obj}) && !this.showApplyButton)
				{
				 	this.doCollapse();
				}
			}
		}
	},

	eventCellOnMouseOver: function (e, obj) {
		var daydata = this.days[parseInt(obj.id.substr(this.DATE_ELEMENT_ID.length),10)];
		if (this.invokeEvent("datemouseover", obj, e, daydata.date) && daydata.enabled)
		{
			if (daydata._month==0 && obj.id!=this.selectedDateCellId && obj.id!=this.todayCellId) Element.addClassName(obj,'rich-calendar-hover');
		}
	},

	eventCellOnMouseOut: function (e, obj) {
		var daydata = this.days[parseInt(obj.id.substr(this.DATE_ELEMENT_ID.length),10)];
		if (this.invokeEvent("datemouseout", obj, e, daydata.date) && daydata.enabled)
		{
			if (daydata._month==0 && obj.id!=this.selectedDateCellId && obj.id!=this.todayCellId) Element.removeClassName(obj,'rich-calendar-hover');
		}
	},

	load:function(daysData, isAjaxMode)	{
		//	startDate,
		//	daysData:array[]
		//	{
		//			day
		//			enabled boolean
		//			text1: 'Meeting...',
		//			text2: 'Meeting...'
		//			tooltip
		//			hasTooltip
		//			styleClass
		//	}

		//if (!$(this.id).component) return;

		if (daysData) {
			this.daysData = this.indexData(daysData, isAjaxMode);
		} else {
			this.daysData = null;
		}

		this.isRendered = false;
		if (this.isVisible) {
			this.render();
		};

		if (typeof this.afterLoad=='function')
		{
			this.afterLoad();
			this.afterLoad=null;
		}
	},

	indexData:function(daysData, isAjaxMode) {
		var dateYear = daysData.startDate.getFullYear();
		var dateMonth = daysData.startDate.getMonth();

		daysData.index = [];
		daysData.index[dateYear+'-'+dateMonth] = 0;
		if (isAjaxMode)
		{
			this.currentDate = daysData.startDate;
			this.currentDate.setDate(1);
			return daysData;
		}
		var idx = daysInMonthByDate(daysData.startDate)-daysData.startDate.getDate()+1;

		while (daysData.days[idx])
		{
			if (dateMonth==11) {dateYear++; dateMonth=0;} else dateMonth++;
			daysData.index[dateYear+'-'+dateMonth] = idx;
			idx+= (32 - new Date(dateYear, dateMonth, 32).getDate());
		}
		return daysData;
	},

	getCellBackgroundColor: function(element)
	{
		var result;
		if (Richfaces.browser.isSafari && this.params.popup && !this.isVisible)
		{
			// Safari 2.0 fix
			// if [display:none] Element.getStyle() function returns null;
			var els = $(this.id).style;
			var originalVisibility = els.visibility;
			var originalDisplay = els.display;
			els.visibility = 'hidden';
			els.display = '';
			result = Element.getStyle(element, 'background-color').parseColor();
			els.display = originalDisplay;
			els.visibility = originalVisibility;
		} else
		{
			result = Element.getStyle(element, 'background-color').parseColor();
		}

		return result;
	},

	clearEffect: function (element_id, effect, className, className1)
	{
		if (effect)
		{
			effect.cancel();
			effect=null;
		}
		if (element_id)
		{
			var e = $(element_id);
			e.style['backgroundColor'] = '';
			if (className) Element.removeClassName(e, className);
			if (className1) Element.addClassName(e, className1);
		}
		return null;
	},

	render:function() {
		//var _d=new Date();
		this.isRendered = true;
		this.todayDate = new Date();

		var currentYear = this.getCurrentYear();
		var currentMonth = this.getCurrentMonth();

		var todayflag = (currentYear == this.todayDate.getFullYear() && currentMonth == this.todayDate.getMonth());
		var todaydate =  this.todayDate.getDate();

		var selectedflag = this.selectedDate && (currentYear == this.selectedDate.getFullYear() && currentMonth == this.selectedDate.getMonth())
		var selecteddate = this.selectedDate && this.selectedDate.getDate();

		var wd = getDay(this.currentDate, this.params.firstWeekDay);
		var currentMonthDays = daysInMonthByDate(this.currentDate);
		var previousMonthDays = daysInMonth(currentYear, currentMonth-1);

		var p=0;
		var month=-1;
		this.days = [];
		var dayCounter = previousMonthDays  - wd + 1;

		// previuos month days
		if (wd>0) while (dayCounter<=previousMonthDays)
		{
			this.days.push({day:dayCounter, isWeekend: this.isWeekend(p), _month:month}); dayCounter++; p++;
		}

		dayCounter = 1;
		month=0;

		this.firstDateIndex = p;

		// current month days
		if (this.daysData && this.daysData.index[currentYear+'-'+currentMonth]!=undefined)
		{
			var idx = this.daysData.index[currentYear+'-'+currentMonth];
			if (this.daysData.startDate.getFullYear()==currentYear && this.daysData.startDate.getMonth()==currentMonth)
			{
				var firstDay = firstDay=(this.daysData.days[idx].day ? this.daysData.days[idx].day : this.daysData.startDate.getDate());
				while (dayCounter<firstDay)
				{
					this.days.push({day:dayCounter, isWeekend:this.isWeekend(p%7), _month:month});

					dayCounter++;
					p++;
				}
			}

			var len = this.daysData.days.length;
			var obj;
			var flag;
			while (idx<len && dayCounter<=currentMonthDays)
			{
				flag = this.isWeekend(p%7);
				obj = this.daysData.days[idx];
				obj.day = dayCounter;
				obj.isWeekend = flag;
				obj._month = month;
				this.days.push(obj);
				idx++;
				dayCounter++;
				p++;
			}
		}
		while (p<42)
		{
			if (dayCounter>currentMonthDays) {dayCounter=1; month=1;}
			this.days.push({day:dayCounter, isWeekend: this.isWeekend(p%7), _month:month});
			dayCounter++;
			p++;
		}

		// render
		this.renderHF();

		//days render
		p=0;
		var element;
		var dataobj;
		var wn;
		if (this.params.showWeeksBar) wn = weekNumber(currentYear, currentMonth, this.params.minDaysInFirstWeek, this.params.firstWeekDay); /// fix it
		this.selectedDayElement=null;
		var weekflag=true;

		var e;

		var boundaryDatesModeFlag = (this.params.boundaryDatesMode == "scroll" || this.params.boundaryDatesMode == "select");

		this.todayCellId = this.clearEffect(this.todayCellId, this.highlightEffect);
		this.selectedDateCellId = this.clearEffect(this.selectedDateCellId, this.highlightEffect2);

		//var _d=new Date();
		var obj = $(this.params.weekNumberBarId+"1");
		for (var k=1;k<7;k++)
		{
			//
			dataobj = this.days[p];

			element = obj.firstChild;
			var weeknumber;

			// week number update
			if (this.params.showWeeksBar)
			{
				if (weekflag && currentMonth==11 &&
				   (k==5||k==6) &&
				   (dataobj._month==1 || (currentMonthDays-dataobj.day+1)<this.params.minDaysInFirstWeek) )
				{
					wn=1;
					weekflag=false;
				}
				weeknumber = wn;
			    element.innerHTML = this.evaluateMarkup(this.params.weekNumberMarkup, {weekNumber: wn++, elementId:element.id, component:this} );
			    if (k==1&&wn>52) wn=1;
			    element = element.nextSibling;
			}

			var weekdaycounter = this.params.firstWeekDay;
			var contentElement = null;

			while (element)
			{
				dataobj.elementId=element.id;
				dataobj.date=new Date(currentYear, currentMonth+dataobj._month, dataobj.day);
				dataobj.weekNumber = weeknumber;
				dataobj.component = this;
				dataobj.isCurrentMonth = (dataobj._month==0);
				dataobj.weekDayNumber = weekdaycounter;

				// call user function to get day state
				if (dataobj.enabled != false) dataobj.enabled = this.params.isDayEnabled(dataobj);
				// call user function to custom class style
				if (!dataobj.styleClass) dataobj.customStyleClass = this.params.dayStyleClass(dataobj);
				else
				{
					var styleclass = this.params.dayStyleClass(dataobj);
					dataobj.customStyleClass = dataobj.styleClass;
					if (styleclass) dataobj.customStyleClass += " " + styleclass;
				}

				contentElement = (this.customDayListMarkup ? element.firstChild : element);
				contentElement.innerHTML = this.evaluateMarkup(this.params.dayListMarkup, dataobj );

				if (weekdaycounter==6) weekdaycounter=0; else weekdaycounter++;

				var classNames = this.dayCellClassName[p];

				// class styles
				if (dataobj._month!=0)
				{
					classNames+=' rich-calendar-boundary-dates';
					if (!this.params.disabled && !this.params.readonly && boundaryDatesModeFlag)
					{
						classNames+=' rich-calendar-btn';
					}
				}
				else
				{
					if (todayflag && dataobj.day==todaydate)
					{
						this.todayCellId = element.id;
						this.todayCellColor = this.getCellBackgroundColor(element);
						classNames+=" rich-calendar-today";
					}

					if (selectedflag && dataobj.day==selecteddate)
					{
						this.selectedDateCellId = element.id;
						this.selectedDateCellColor = this.getCellBackgroundColor(element);
						classNames+=" rich-calendar-select";
					}
					else if (!this.params.disabled && !this.params.readonly && dataobj.enabled) classNames+=' rich-calendar-btn';

					// add custom style class
					if (dataobj.customStyleClass)
					{
						classNames+=' '+dataobj.customStyleClass;
					}
				}
				element.className = classNames;

				p++;

				dataobj = this.days[p];
				element=element.nextSibling;
			}
			obj = obj.nextSibling;
		}

		//alert(new Date().getTime()-_d.getTime());

		// hack for IE 6.0 //fix 1072 // TODO check this bug again
		/*if (Richfaces.browser.isIE6)
		{
			var element = $(this.id);
			if (element)
			{
				element.style.width = "0px";
				element.style.height = "0px";
			}
		}*/
	},

	renderHF: function()
	{
		if (this.params.showHeader) this.renderMarkup(this.params.headerMarkup, this.id+"Header", this.calendarContext);
		if (this.params.showFooter) this.renderMarkup(this.params.footerMarkup, this.id+"Footer", this.calendarContext);

		this.renderHeaderOptional();
		this.renderFooterOptional();
	},

	renderHeaderOptional: function()
	{
		this.renderMarkup(this.params.optionalHeaderMarkup, this.id+"HeaderOptional", this.calendarContext);
	},

	renderFooterOptional: function()
	{
		this.renderMarkup(this.params.optionalFooterMarkup, this.id+"FooterOptional", this.calendarContext);
	},

	renderMarkup: function (markup, elementId, context)
	{
		if (!markup) return;

		var e = $(elementId);
		if (!e) return;

		e.innerHTML = markup.invoke('getContent', context).join('');
	},

	evaluateMarkup: function(markup, context)
	{
		if (!markup) return "";
		return markup.invoke('getContent', context).join('');
	},

	onUpdate: function()
	{
		var formattedDate = Richfaces.Calendar.formatDate(this.getCurrentDate(),"MM/yyyy");
		$(this.id+'InputCurrentDate').value=formattedDate;

		if (this.submitFunction)
			this.submitFunction(formattedDate);
		else
			this.render();
	},

	nextMonth: function() {
		this.changeCurrentDateOffset(0,1);
	},

	prevMonth: function() {
		this.changeCurrentDateOffset(0,-1);
	},

	nextYear: function() {
		this.changeCurrentDateOffset(1,0);
	},

	prevYear: function() {
		this.changeCurrentDateOffset(-1,0);
	},

	changeCurrentDate: function(year, month, noUpdate) {
		if (this.getCurrentMonth()!=month || this.getCurrentYear()!=year)
		{
			var date = new Date(year, month,1);
			if (this.invokeEvent("currentdateselect", $(this.id), null, date))
			{
				// fix for RF-2450.
				// Additional event is fired: after the hidden input with current date
				// value is updated in function onUpdate() and then
				// the "currentdateselected" Event is fired.
				this.currentDate = date;
				if (noUpdate) this.render(); else this.onUpdate();
				this.invokeEvent("currentdateselected", $(this.id), null, date);
				return true;
			}
		}
		return false;
	},

	changeCurrentDateOffset: function(yearOffset, monthOffset) {
		var date = new Date(this.currentDate.getFullYear()+yearOffset, this.currentDate.getMonth()+monthOffset,1);

		if (this.invokeEvent("currentdateselect", $(this.id), null, date))
		{
			// fix for RF-2450.
			// Additional event is fired: after the hidden input with current date
			// value is updated in function onUpdate() and then
			// the "currentdateselected" Event is fired.
			this.currentDate = date;
			this.onUpdate();
			this.invokeEvent("currentdateselected", $(this.id), null, date);
		}
	},

	today: function(noUpdate, noHighlight) {

			var now = new Date();

			var nowyear = now.getFullYear();
			var nowmonth = now.getMonth();
			var nowdate = now.getDate();
			var updateflag = false;

			if (nowdate!=this.todayDate.getDate()) {updateflag=true; this.todayDate = now;}

			if (nowyear != this.currentDate.getFullYear() || nowmonth != this.currentDate.getMonth() )
			{
				updateflag = true;
				this.currentDate = new Date(nowyear, nowmonth, 1);
			}

			if (this.todayControlMode=='select')
			{
				noHighlight=true;
			}

			if (updateflag)
			{
				if (noUpdate) this.render(); else this.onUpdate();
			}
			else
			{
				// highlight today

				if (this.isVisible && this.todayCellId && !noHighlight)
				{
					this.clearEffect(this.todayCellId, this.highlightEffect);
					if (this.todayCellColor!="transparent")
					{
						this.highlightEffect = new Effect.Highlight($(this.todayCellId), {startcolor: this.todayCellColor, duration:0.3, transition: Effect.Transitions.sinoidal,
						afterFinish: this.onHighlightFinish});
					}
				}
			}

			// todayControl select mode
			if (this.todayControlMode=='select' && !this.params.disabled && !this.params.readonly)
				if (updateflag && !noUpdate && this.submitFunction)
				{
					this.afterLoad = this.selectToday;
				}
				else this.selectToday();

	},

	selectToday: function()
	{
		if (this.todayCellId)
		{
			var daydata = this.days[parseInt($(this.todayCellId).id.substr(this.DATE_ELEMENT_ID.length),10)];
			var today = new Date();
			var date = new Date(today.getFullYear(), today.getMonth(), today.getDate());
			if (this.timeType)
			{
				this.setupTimeForDate(date);
			}
			if (daydata.enabled && this.selectDate(date,true) && !this.showApplyButton)
			{
				this.doCollapse();
			}
		}
	},

	onHighlightFinish: function (object)
	{
		object.element.style['backgroundColor'] = '';
	},

	selectDate: function(date, noUpdate, eventData) {

		if (!eventData)
		{
			eventData = {event: null, element: null};
		}

		var oldSelectedDate = this.selectedDate;
		var newSelectedDate;
		if (date)
		{
			if (typeof date=='string')
			{
				date = Richfaces.Calendar.parseDate(date,this.params.datePattern, this.params.monthLabels, this.params.monthLabelsShort);
			}
			newSelectedDate = date;
		}
		else
		{
			newSelectedDate = null;
		}

		// fire user event
		var flag = true;
		var isDateChange = false;
		if ( (oldSelectedDate - newSelectedDate) && (oldSelectedDate!=null || newSelectedDate!=null) )
		{
			isDateChange = true;
			flag = this.invokeEvent("dateselect", eventData.element, eventData.event, date);
		}

		if (flag)
		{
			if (newSelectedDate!=null)
			{
				if (newSelectedDate.getMonth()==this.currentDate.getMonth() && newSelectedDate.getFullYear()==this.currentDate.getFullYear())
				{
					this.selectedDate = newSelectedDate;
					if (!oldSelectedDate || (oldSelectedDate - this.selectedDate))
					{
						// find cell and change style class
						var e = $(this.DATE_ELEMENT_ID+(this.firstDateIndex + this.selectedDate.getDate()-1));

						this.clearEffect(this.selectedDateCellId, this.highlightEffect2, "rich-calendar-select", (this.params.disabled || this.params.readonly ? null : "rich-calendar-btn"));
						this.selectedDateCellId = e.id;
						this.selectedDateCellColor = this.getCellBackgroundColor(e);

						Element.removeClassName(e, "rich-calendar-btn");
						Element.removeClassName(e, "rich-calendar-hover");
						Element.addClassName(e, "rich-calendar-select");

						this.renderHF();
					}
					else if (this.timeType!=0) this.renderHF();
				}
				else
				{
					//RF-5600
					this.selectedDate = newSelectedDate;

					// change currentDate and call this.onUpdate();
					if (this.changeCurrentDate(newSelectedDate.getFullYear(), newSelectedDate.getMonth(), noUpdate))
					{
						//this.selectedDate = newSelectedDate;
					} else {
						this.selectedDate = oldSelectedDate;
						isDateChange = false;
					}
				}
			}
			else
			{
				this.selectedDate = null;

				this.clearEffect(this.selectedDateCellId, this.highlightEffect2, "rich-calendar-select", (this.params.disabled || this.params.readonly ? null : "rich-calendar-btn"));

				if (this.selectedDateCellId)
				{
					this.selectedDateCellId = null;
					this.renderHF();
				}

				var date = new Date();
				if (this.currentDate.getMonth()==date.getMonth() && this.currentDate.getFullYear()==date.getFullYear())
				{
					this.renderHF();
				}

				var todayControlMode = this.todayControlMode;
				this.todayControlMode = '';
				this.today(noUpdate, true);
				this.todayControlMode = todayControlMode;
			}

			// call user event
			if (isDateChange)
			{
				this.invokeEvent("dateselected", eventData.element, eventData.event, this.selectedDate);
				if (!this.showApplyButton)
				{
					this.setInputField(this.selectedDate!=null ? this.getSelectedDateString(this.params.datePattern) : "", eventData.event);
				}
			}
		}

		return isDateChange;
	},

	resetSelectedDate: function()
	{
		if (!this.selectedDate) return;
		if (this.invokeEvent("dateselect", null, null, null))
		{
			this.selectedDate = null;
			this.invokeEvent("dateselected", null, null, null);

			this.selectedDateCellId = this.clearEffect(this.selectedDateCellId, this.highlightEffect2, "rich-calendar-select", (this.params.disabled || this.params.readonly ? null : "rich-calendar-btn"));

			this.renderHF();
			if (!this.showApplyButton)
			{
				this.setInputField("", null);
				this.doCollapse();
			}
		}
	},

	showSelectedDate: function()
	{
		if (!this.selectedDate) return;
		if (this.currentDate.getMonth()!=this.selectedDate.getMonth() || this.currentDate.getFullYear()!=this.selectedDate.getFullYear())
		{
			this.currentDate = new Date(this.selectedDate);
			this.currentDate.setDate(1);
			this.onUpdate();
		}
		else
		{
			// highlight Selected Date
			if (this.isVisible && this.selectedDateCellId)
			{
				this.clearEffect(this.selectedDateCellId, this.highlightEffect2);
				if (this.selectedDateCellColor!="transparent")
				{
					this.highlightEffect2 = new Effect.Highlight($(this.selectedDateCellId), {startcolor: this.selectedDateCellColor, duration:0.3, transition: Effect.Transitions.sinoidal,
					afterFinish: this.onHighlightFinish});
				}
			}
		}
	},

	close: function(updateDate)
	{
		if (updateDate)
		{
			this.setInputField(this.getSelectedDateString(this.params.datePattern), null);
		}
		this.doCollapse();
	},

	setEditorPosition: function (element, editor, shadow)
	{
		element;

		var dim = Richfaces.Calendar.getOffsetDimensions(element);
		editor.style.width = shadow.style.width = dim.width + 'px';
		editor.style.height = shadow.style.height = dim.height + 'px';

		Richfaces.Calendar.clonePosition([editor,shadow], element);
	},

	showTimeEditor: function()
	{
		var editor;
		if (this.timeType==0) return;
		if (!this.isEditorCreated) editor = this.createEditor();
		else editor = $(this.EDITOR_ID);
		if (!this.isTimeEditorLayoutCreated) this.createTimeEditorLayout(editor);

		$(this.TIME_EDITOR_LAYOUT_ID).show();

		var editor_shadow = $(this.EDITOR_SHADOW_ID);

		this.setEditorPosition($(this.id), editor, editor_shadow);

		this.updateTimeEditor();

		editor_shadow.show();

		editor.show();

		Element.clonePosition(this.EDITOR_LAYOUT_SHADOW_ID, this.TIME_EDITOR_LAYOUT_ID, {offsetLeft: 3, offsetTop: 3});
		this.isEditorVisible = true;
	},

	hideEditor: function()
	{
		if (this.isTimeEditorLayoutCreated) $(this.TIME_EDITOR_LAYOUT_ID).hide();
		if (this.isDateEditorLayoutCreated) $(this.DATE_EDITOR_LAYOUT_ID).hide();
		$(this.EDITOR_ID).hide();
		$(this.EDITOR_SHADOW_ID).hide();
		this.isEditorVisible = false;
	},

	hideTimeEditor: function(updateTime)
	{
		this.hideEditor();
		if (updateTime && this.selectedDate)
		{
			var m = parseInt($(this.id+'TimeMinutes').value,10);
			var h=parseInt($(this.id+'TimeHours').value,10);
			if (this.timeType==2)
			{
				if ($(this.id+'TimeSign').value.toLowerCase()=="am")
				{
					if (h==12) h = 0;
				}
				else
				{
					if (h!=12) h+=12;
				}
			}
			var date = new Date(this.selectedDate.getFullYear(), this.selectedDate.getMonth(), this.selectedDate.getDate(), h, m, 0);
			if (date-this.selectedDate && this.invokeEvent("timeselect",null, null, date))
			{
				this.selectedDate = date;
				this.renderHF();
				if (!this.params.popup || !this.showApplyButton) this.setInputField(this.getSelectedDateString(this.params.datePattern), null);
				this.invokeEvent("timeselected",null, null, this.selectedDate);
			}
		}
		if (this.params.popup && !this.showApplyButton) this.close(false);
	},

	showDateEditor: function()
	{
		var editor;
		if (!this.isEditorCreated) editor = this.createEditor();
		else editor = $(this.EDITOR_ID);
		if (!this.isDateEditorLayoutCreated) this.createDateEditorLayout(editor);
		else this.updateDateEditor();

		$(this.DATE_EDITOR_LAYOUT_ID).show();

		var editor_shadow = $(this.EDITOR_SHADOW_ID);

		this.setEditorPosition($(this.id), editor, editor_shadow);

		editor_shadow.show();
		editor.show();

		Element.clonePosition(this.EDITOR_LAYOUT_SHADOW_ID, this.DATE_EDITOR_LAYOUT_ID, {offsetLeft: 3, offsetTop: 3});

		this.isEditorVisible = true;
	},

	hideDateEditor: function(updateCurrentDate)
	{
		this.hideEditor();
		if (updateCurrentDate)
		{
			this.changeCurrentDate(this.dateEditorYear, this.dateEditorMonth);
		}
	}

});

CalendarView = {};
CalendarView.getControl = function(text, attributes, functionName, paramsStr) {
	var attr = Object.extend({
			onclick: (functionName ? "Richfaces.getComponent('calendar',this)."+functionName+"("+(paramsStr ? paramsStr : "")+");" : "")+"return true;"
		}, attributes);
	return new E('div',attr,[new T(text)]);
};

CalendarView.getSelectedDateControl = function(calendar) {

	if (!calendar.selectedDate || calendar.showApplyButton) return "";

	var text = Richfaces.Calendar.formatDate(calendar.selectedDate,(calendar.timeType ? calendar.datePattern : calendar.params.datePattern), calendar.params.monthLabels, calendar.params.monthLabelsShort);
	var onclick = "Richfaces.getComponent('calendar',this).showSelectedDate(); return true;"
	var markup = ( calendar.params.disabled ?
					new E('div', {'class': 'rich-calendar-tool-btn-disabled'}, [new ET(text)]) :
					new E('div', {'class': 'rich-calendar-tool-btn', 'onclick': onclick}, [new ET(text)]) );

	return markup;
};

CalendarView.getTimeControl = function(calendar) {

	if (!calendar.selectedDate || !calendar.timeType) return "";

	var text = Richfaces.Calendar.formatDate(calendar.selectedDate, calendar.timePattern, calendar.params.monthLabels, calendar.params.monthLabelsShort);

	var onmouseover = "Element.removeClassName(this, 'rich-calendar-tool-btn-press');";
	var onmouseout = "Element.addClassName(this, 'rich-calendar-tool-btn-press');";
	var onclick = "Richfaces.getComponent('calendar',this).showTimeEditor();return true;";
	var markup = calendar.params.disabled || calendar.params.readonly ?
				new E('div', {'class': 'rich-calendar-tool-btn-disabled'}, [new ET(text)]) :
				new E('div', {'class': 'rich-calendar-tool-btn rich-calendar-tool-btn-hover rich-calendar-tool-btn-press', 'onclick': onclick,
						'onmouseover': + onmouseover ,
						'onmouseout' : + onmouseout}, [new ET(text)]);

	return markup;
};

CalendarView.toolButtonAttributes = {className: "rich-calendar-tool-btn", onmouseover:"this.className='rich-calendar-tool-btn rich-calendar-tool-btn-hover'", onmouseout:"this.className='rich-calendar-tool-btn'", onmousedown:"this.className='rich-calendar-tool-btn rich-calendar-tool-btn-hover rich-calendar-tool-btn-press'", onmouseup:"this.className='rich-calendar-tool-btn rich-calendar-tool-btn-hover'"};
CalendarView.nextYearControl = function (context) {
	return (!context.calendar.params.disabled ? CalendarView.getControl(">>", CalendarView.toolButtonAttributes, "nextYear") : "");
};
CalendarView.previousYearControl = function (context) {
	return (!context.calendar.params.disabled ? CalendarView.getControl("<<", CalendarView.toolButtonAttributes, "prevYear") : "");
};
CalendarView.nextMonthControl = function (context) {
	return (!context.calendar.params.disabled ? CalendarView.getControl(">", CalendarView.toolButtonAttributes, "nextMonth") : "");
};
CalendarView.previousMonthControl = function (context) {
	return (!context.calendar.params.disabled ? CalendarView.getControl("<", CalendarView.toolButtonAttributes, "prevMonth") : "");
};
CalendarView.currentMonthControl = function (context) {
	var text = Richfaces.Calendar.formatDate(context.calendar.getCurrentDate(), "MMMM, yyyy", context.monthLabels, context.monthLabelsShort);
	var markup = context.calendar.params.disabled ?
				 new E('div',{className: "rich-calendar-tool-btn-disabled"},[new T(text)]) :
				 CalendarView.getControl(text, CalendarView.toolButtonAttributes, "showDateEditor");
	return markup;
};
CalendarView.todayControl = function (context) {
	return (!context.calendar.params.disabled && context.calendar.todayControlMode!='hidden' ? CalendarView.getControl(context.controlLabels.today, CalendarView.toolButtonAttributes, "today") : "");
};
CalendarView.closeControl = function (context) {
	return (context.calendar.params.popup ? CalendarView.getControl(context.controlLabels.close, CalendarView.toolButtonAttributes, "close", "false") : "");
};
CalendarView.applyControl = function (context) {
	return (!context.calendar.params.disabled && !context.calendar.params.readonly && context.calendar.showApplyButton ? CalendarView.getControl(context.controlLabels.apply, CalendarView.toolButtonAttributes, "close", "true") : "");
};
CalendarView.cleanControl = function (context) {
	return (!context.calendar.params.disabled && !context.calendar.params.readonly && context.calendar.selectedDate ? CalendarView.getControl(context.controlLabels.clean, CalendarView.toolButtonAttributes, "resetSelectedDate") : "");
};

CalendarView.selectedDateControl = function (context) {	return CalendarView.getSelectedDateControl(context.calendar);};
CalendarView.timeControl = function (context) {	return CalendarView.getTimeControl(context.calendar);};
CalendarView.timeEditorFields = function (context) {return context.calendar.timePatternHtml;};

CalendarView.header = [
	new E('table',{'border': '0', 'cellpadding': '0', 'cellspacing': '0', 'width': '100%'},
		[
			new E('tbody',{},
			[
				new E('tr',{},
				[
					new E('td',{'class': 'rich-calendar-tool'},
					[
						new ET(function (context) { return Richfaces.evalMacro("previousYearControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-tool'},
					[
						new ET(function (context) { return Richfaces.evalMacro("previousMonthControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-month'},
					[
						new ET(function (context) { return Richfaces.evalMacro("currentMonthControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-tool'},
					[
						new ET(function (context) { return Richfaces.evalMacro("nextMonthControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-tool'},
					[
						new ET(function (context) { return Richfaces.evalMacro("nextYearControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-tool rich-calendar-tool-close', 'style':function(context){return (this.isEmpty ? 'display:none;' : '');}},
					[
						new ET(function (context) { return Richfaces.evalMacro("closeControl", context)})
					])
				])
			])
		]
	)];

CalendarView.footer = [
	new E('table',{'border': '0', 'cellpadding': '0', 'cellspacing': '0', 'width': '100%'},
		[
			new E('tbody',{},
			[
				new E('tr',{},
				[
					new E('td',{'class': 'rich-calendar-toolfooter', 'style':function(context){return (this.isEmpty ? 'display:none;' : '');}},
					[
						new ET(function (context) { return Richfaces.evalMacro("selectedDateControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-toolfooter', 'style':function(context){return (this.isEmpty ? 'display:none;' : '');}},
					[
						new ET(function (context) { return Richfaces.evalMacro("cleanControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-toolfooter', 'style':function(context){return (this.isEmpty ? 'display:none;' : '');}},
					[
						new ET(function (context) { return Richfaces.evalMacro("timeControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-toolfooter', 'style': 'background-image:none;', 'width': '100%'}, []),
					new E('td',{'class': 'rich-calendar-toolfooter', 'style':function(context){return (this.isEmpty ? 'display:none;' : '')+(context.calendar.params.disabled || context.calendar.params.readonly || !context.calendar.showApplyButton ? 'background-image:none;' : '');}},
					[
						new ET(function (context) { return Richfaces.evalMacro("todayControl", context)})
					]),
					new E('td',{'class': 'rich-calendar-toolfooter', 'style':function(context){return (this.isEmpty ? 'display:none;' : '')+'background-image:none;';}},
					[
						new ET(function (context) { return Richfaces.evalMacro("applyControl", context)})
					])
				])
			])
		]
	)];

CalendarView.timeEditorLayout = [

        new E('table',{'id': function(context){return context.calendar.TIME_EDITOR_LAYOUT_ID}, 'border': '0', 'cellpadding': '0', 'cellspacing': '0', 'class': 'rich-calendar-time-layout'},
		[
			new E('tbody',{},
			[
				new E('tr',{},
				[
					new E('td',{'class': 'rich-calendar-time-layout-fields', 'colspan': '2', 'align': 'center'},
					[
						new ET(function (context) { return Richfaces.evalMacro("timeEditorFields", context)})
					])
				]),
				new E('tr',{},
				[
					new E('td',{'class': 'rich-calendar-time-layout-ok'},
					[
						new E('div',{'id': function(context){return context.calendar.TIME_EDITOR_BUTTON_OK}, 'class': 'rich-calendar-time-btn', 'style': 'float:right;', 'onmousedown': "Element.addClassName(this, 'rich-calendar-time-btn-press');", 'onmouseout': "Element.removeClassName(this, 'rich-calendar-time-btn-press');", 'onmouseup': "Element.removeClassName(this, 'rich-calendar-time-btn-press');", 'onclick': function(context){return "$('"+context.calendar.id+"').component.hideTimeEditor(true)";}},
						[
							new E('span',{},
							[
								new ET(function (context) { return context.controlLabels.ok; })
							])
						])
					])
					,
					new E('td',{'class': 'rich-calendar-time-layout-cancel'},
					[
						new E('div',{'id': function(context){return context.calendar.TIME_EDITOR_BUTTON_CANCEL}, 'class': 'rich-calendar-time-btn', 'style': 'float:left;', 'onmousedown': "Element.addClassName(this, 'rich-calendar-time-btn-press');", 'onmouseout': "Element.removeClassName(this, 'rich-calendar-time-btn-press');", 'onmouseup': "Element.removeClassName(this, 'rich-calendar-time-btn-press');", 'onclick': function(context){return "$('"+context.calendar.id+"').component.hideTimeEditor(false)";}},
						[
							new E('span',{},
							[
								new ET(function (context) { return context.controlLabels.cancel; })
							])
						])
					])
				])
			])
		]
	)];

CalendarView.dayList = [new ET(function (context) { return context.day})];
CalendarView.weekNumber = [new ET(function (context) { return context.weekNumber})];
CalendarView.weekDay = [new ET(function (context) { return context.weekDayLabelShort})];

CalendarContext = Class.create();
Object.extend(CalendarContext.prototype, {
    initialize: function(calendar) {
    	this.calendar=calendar;
		this.monthLabels=calendar.params.monthLabels;
		this.monthLabelsShort=calendar.params.monthLabelsShort;
		this.weekDayLabels=calendar.params.weekDayLabels;
		this.weekDayLabelsShort=calendar.params.weekDayLabelsShort;
		this.controlLabels=calendar.params.labels;
	},
	nextYearControl: CalendarView.nextYearControl,
	previousYearControl: CalendarView.previousYearControl,
	nextMonthControl: CalendarView.nextMonthControl,
	previousMonthControl: CalendarView.previousMonthControl,
	currentMonthControl: CalendarView.currentMonthControl,
	selectedDateControl: CalendarView.selectedDateControl,
	cleanControl: CalendarView.cleanControl,
	timeControl: CalendarView.timeControl,
	todayControl: CalendarView.todayControl,
	closeControl: CalendarView.closeControl,
	applyControl: CalendarView.applyControl,
	timeEditorFields: CalendarView.timeEditorFields,
	timeEditorLayout: CalendarView.timeEditorLayout
});
