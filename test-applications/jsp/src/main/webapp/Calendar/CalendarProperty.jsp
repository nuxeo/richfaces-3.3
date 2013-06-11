<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="calendarPropertySubviewID">
	<h:commandButton value="add test" action="#{calendarBean.addHtmlCalendar}"></h:commandButton>

	<h:panelGrid columns="2">
		<h:panelGroup>
			<a4j:commandButton value="reRender"
				reRender="calendarClientID,calendarAjaxID"></a4j:commandButton>
		</h:panelGroup>
		<h:column></h:column>

		<h:outputText value="Mode:" />
		<h:selectOneRadio value="#{calendarBean.mode}" onchange="submit();">
			<f:selectItem itemLabel="client" itemValue="client" />
			<f:selectItem itemLabel="ajax" itemValue="ajax" />
		</h:selectOneRadio>


		<h:outputText value="Select Locale:" />
		<h:selectOneRadio onchange="submit()" value="en/US"
			valueChangeListener="#{calendarBean.selectLocale}">
			<f:selectItem itemLabel="US" itemValue="en/US" />
			<f:selectItem itemLabel="DE" itemValue="de/DE" />
			<f:selectItem itemLabel="FR" itemValue="fr/FR" />
			<f:selectItem itemLabel="RU" itemValue="ru/RU" />
		</h:selectOneRadio>

		<h:outputText value="Popup Mode:" />
		<h:selectBooleanCheckbox id="popupModeID"
			value="#{calendarBean.popup}" onclick="submit();" />

		<h:outputText value="showApplyButton: "></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.showApplyButton}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="showWeeksBar: "></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.showWeeksBar}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="showWeekDaysBar: "></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.showWeekDaysBar}"
			onchange="submit();"></h:selectBooleanCheckbox>

		<h:outputText value="cellHeight: "></h:outputText>
		<h:inputText value="#{calendarBean.cellHeight}" onchange="submit();"></h:inputText>

		<h:outputText value="cellWidth: "></h:outputText>
		<h:inputText value="#{calendarBean.cellWidth}" onchange="submit();"></h:inputText>

		<h:outputText value="firstWeekDay"></h:outputText>
		<h:selectOneRadio value="#{calendarBean.firstWeekDay}"
			onchange="submit();">
			<f:selectItem itemLabel="1" itemValue="1" />
			<f:selectItem itemLabel="2" itemValue="2" />
			<f:selectItem itemLabel="3" itemValue="3" />
			<f:selectItem itemLabel="4" itemValue="4" />
			<f:selectItem itemLabel="5" itemValue="5" />
			<f:selectItem itemLabel="6" itemValue="6" />
			<f:selectItem itemLabel="7" itemValue="7" />
		</h:selectOneRadio>

		<h:outputText value="todayControlMode" />
		<h:selectOneRadio value="#{calendarBean.todayControlMode}"
			onchange="submit();">
			<f:selectItem itemLabel="scroll" itemValue="scroll" />
			<f:selectItem itemLabel="select" itemValue="select" />
			<f:selectItem itemLabel="hidden" itemValue="hidden" />
		</h:selectOneRadio>

		<h:outputText value="Custom day labels" />
		<h:selectOneRadio value="#{calendarBean.weekDay}" onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="day labels" itemValue="long" />
			<f:selectItem itemLabel="day labels short" itemValue="short" />
		</h:selectOneRadio>

		<h:outputText value="Custom month labels" />
		<h:selectOneRadio value="#{calendarBean.month}" onchange="submit();">
			<f:selectItem itemLabel="none" itemValue="none" />
			<f:selectItem itemLabel="day labels" itemValue="long" />
			<f:selectItem itemLabel="day labels short" itemValue="short" />
		</h:selectOneRadio>

		<h:outputText value="Select Date Pattern:" />
		<h:selectOneMenu value="#{calendarBean.pattern}" onchange="submit()">
			<f:selectItem itemLabel="d/M/yy" itemValue="d/M/yy" />
			<f:selectItem itemLabel="dd/M/yy" itemValue="dd/M/yy" />
			<f:selectItem itemLabel="d/MMM/y" itemValue="d/MMM/y" />
			<f:selectItem itemLabel="dd.MM.yyyy" itemValue="dd.MM.yyyy" />
			<f:selectItem itemLabel="MMM d, yyyy" itemValue="MMM d, yyyy" />
			<f:selectItem itemLabel="dd-MM-yyyy" itemValue="dd-MM-yyyy" />
			<f:selectItem itemLabel="dd/M/yy HH:mm" itemValue="dd/M/yy HH:mm" />
			<f:selectItem itemLabel="MMM d, yyyy h:mm a"
				itemValue="MMM d, yyyy h:mm a" />
		</h:selectOneMenu>

		<h:outputText value="Preload date range begin(d.m.y)" />
		<h:inputText value="#{calendarBean.preloadDateRangeBegin}"
			onchange="submit();" />

		<h:outputText value="Preload date range end(d.m.y)" />
		<h:inputText value="#{calendarBean.preloadDateRangeEnd}"
			onchange="submit();" />

		<h:outputText value="z-index:" />
		<h:inputText value="#{calendarBean.zindex}">
			<a4j:support event="onchange"
				reRender="calendarClientID,calendarAjaxID"></a4j:support>
		</h:inputText>

		<h:outputText value="horizontalOffset: "></h:outputText>
		<h:inputText value="#{calendarBean.horizontalOffset}"
			onchange="submit();"></h:inputText>

		<h:outputText value="verticalOffset:"></h:outputText>
		<h:inputText value="#{calendarBean.verticalOffset}"
			onchange="submit();"></h:inputText>

		<h:outputText value="immediate: "></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.immediate}"></h:selectBooleanCheckbox>

		<h:outputText value="reRender (counter):" />
		<h:outputText id="counter" value="#{calendarBean.counter}" />

		<h:outputText value="Required" />
		<h:selectBooleanCheckbox value="#{calendarBean.required}"
			onclick="submit()" />

		<h:outputText value="Select Popup Joint Point:" />
		<h:selectOneRadio onchange="submit()"
			value="#{calendarBean.jointPoint}">
			<f:selectItem itemLabel="bottom-right" itemValue="bottom-right" />
			<f:selectItem itemLabel="bottom-left" itemValue="bottom-left" />
			<f:selectItem itemLabel="top-right" itemValue="top-right" />
			<f:selectItem itemLabel="top-left" itemValue="top-left" />
		</h:selectOneRadio>

		<h:outputText value="Select Popup Direction:" />
		<h:selectOneRadio onchange="submit()"
			value="#{calendarBean.direction}">
			<f:selectItem itemLabel="bottom-right" itemValue="bottom-right" />
			<f:selectItem itemLabel="bottom-left" itemValue="bottom-left" />
			<f:selectItem itemLabel="top-right" itemValue="top-right" />
			<f:selectItem itemLabel="top-left" itemValue="top-left" />
			<f:selectItem itemLabel="auto" itemValue="auto" />
		</h:selectOneRadio>

		<h:outputText value="defaultTime"></h:outputText>
		<h:inputText value="#{calendarBean.defaultTime}" onchange="submit();"></h:inputText>

		<h:outputText value="Time Zone:" />
		<h:selectOneRadio valueChangeListener="#{calendarBean.timeZone}"
			onchange="submit();">
			<f:selectItem itemLabel="Eastern European Time"
				itemValue="Eastern European Time" />
			<f:selectItem itemLabel="Turkmenistan Time"
				itemValue="Turkmenistan Time" />
			<f:selectItem itemLabel="Korea Standard Time"
				itemValue="Korea Standard Time" />
		</h:selectOneRadio>

		<h:outputText value="BoundaryDatesMode:" />
		<h:selectOneRadio onclick="submit()" value="#{calendarBean.boundary}">
			<f:selectItem itemLabel="inactive" itemValue="inactive" />
			<f:selectItem itemLabel="select" itemValue="select" />
			<f:selectItem itemLabel="scroll" itemValue="scroll" />
		</h:selectOneRadio>

		<h:outputText value="EnableManualInput:"></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.enableManualInput}"
			onclick="submit()">
		</h:selectBooleanCheckbox>

		<h:outputText value="ShowInput:"></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.showInput}"
			onclick="submit()">
		</h:selectBooleanCheckbox>

		<h:outputText value="resetTimeOnDateSelect"></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.resetTimeOnDateSelect}"
			onchange="sumit();"></h:selectBooleanCheckbox>

		<h:outputText value="showFooter"></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.showFooter}"
			onchange="sumit();"></h:selectBooleanCheckbox>

		<h:outputText value="showHeader"></h:outputText>
		<h:selectBooleanCheckbox value="#{calendarBean.showHeader}"
			onchange="sumit();"></h:selectBooleanCheckbox>

		<h:outputText value="Disabled:" />
		<h:selectBooleanCheckbox value="#{calendarBean.disabled}"
			onclick="submit()" />
			
		<h:outputText value="Readonly:" />
		<h:selectBooleanCheckbox value="#{calendarBean.readonly}"
			onclick="submit()" />

		<h:outputText value="Rendered:" />
		<h:selectBooleanCheckbox value="#{calendarBean.rendered}"
			onclick="submit()" />

		<f:verbatim></f:verbatim>
		<h:commandButton action="#{calendarBean.changeIcons}"
			value="ChangeIcons" />

		<h:outputText value="Current date: " />
		<h:outputText value="#{calendarBean.currentDateAsText}" />

		<h:outputText value="Selected date: " />
		<h:outputText value="#{calendarBean.selectedDate}" />

		<f:verbatim></f:verbatim>
		<h:commandButton value="Submit" />
	</h:panelGrid>

	<script type="text/javascript">
	var currentTime = new Date()	
	var month = currentTime.getMonth() + 1
	var day = currentTime.getDate()
	var year = currentTime.getFullYear()
	document.write(month + "/" + day + "/" + year)
	</script>

	<h:panelGrid columns="3" border="2">
		<h:column></h:column>
		<h:outputText value="JavaScript API"></h:outputText>
		<h:column></h:column>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.expand(event)"
			value="expand"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.nextMonth()"
			value="nextMonth"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.prevMonth()"
			value="prevMonth"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.nextYear()"
			value="nextYear"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.prevYear()"
			value="prevYear"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.today()"
			value="today"></a4j:commandLink>
		<a4j:commandLink
			onclick="alert($('formID:calendarSubviewID:calendarClientID').component.getSelectedDate())"
			value="getSelectedDate"></a4j:commandLink>
		<a4j:commandLink
			onclick="alert($('formID:calendarSubviewID:calendarClientID').component.isDateEnabled(new Date()))"
			value="isDateEnabled"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.selectDate(new Date())"
			value="selectDate"></a4j:commandLink>
		<a4j:commandLink
			onclick="alert($('formID:calendarSubviewID:calendarClientID').component.getCurrentMonth())"
			value="getCurrentMonth"></a4j:commandLink>
		<a4j:commandLink
			onclick="alert($('formID:calendarSubviewID:calendarClientID').component.getCurrentYear())"
			value="getCurrentYear"></a4j:commandLink>
		<a4j:commandLink
			onclick="alert($('formID:calendarSubviewID:calendarClientID').component.getCurrentDate())"
			value="getCurrentDate"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.enableDate(currentTime)"
			value="enableDate"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.disableDate(new Date)"
			value="disableDate"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.doCollapse(event)"
			value="collapse"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.expand(event)"
			value="expand"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.resetSelectedDate()"
			value="resetSelectedDate"></a4j:commandLink>
		<a4j:commandLink
			onclick="$('formID:calendarSubviewID:calendarClientID').component.doSwitch()"
			value="switch"></a4j:commandLink>
	</h:panelGrid>

	<h:outputText value="JS API with componentControl"></h:outputText>

	<f:verbatim>
		<br />
		<a href="#" id="doExpandCalendarID">Calendar (expand)</a>
		<br />
		<a href="#" id="collapseCalendarID">Calendar (collapse)</a>
		<br />
		<a href="#" id="doNextYearCalendarID">Calendar (nextYear)</a>
		<br />
		<a href="#" id="doPrevYearCalendarID">Calendar (prevYear)</a>
		<br />
		<a href="#" id="doNextMonthCalendarID">Calendar (nextMonth)</a>
		<br />
		<a href="#" id="doPrevMonthCalendarID">Calendar (prevMonth)</a>
		<br />
		<a href="#" id="doTodayCalendarID">Calendar (today)</a>
		<br />
		<a href="#" id="getSelectedDateID">Calendar(getSelectedDate)</a>
		<br />
		<a href="#" id="getCurrentDateID">Calendar(getCurrentDate)</a>
		<br />
		<a href="#" id="getCurrentMonthID">Calendar(getCurrentMonth)</a>
		<br />
		<a href="#" id="getCurrentYearID">Calendar(getCurrentYear)</a>
	</f:verbatim>

	<rich:componentControl attachTo="doExpandCalendarID"
		for="calendarClientID" event="onclick" operation="expand" />
	<rich:componentControl attachTo="collapseCalendarID"
		for="calendarClientID" event="onclick" operation="collapse" />
	<rich:componentControl attachTo="doNextYearCalendarID"
		for="calendarClientID" event="onclick" operation="nextYear" />
	<rich:componentControl attachTo="doPrevYearCalendarID"
		for="calendarClientID" event="onclick" operation="prevYear" />
	<rich:componentControl attachTo="doNextMonthCalendarID"
		for="calendarClientID" event="onclick" operation="nextMonth" />
	<rich:componentControl attachTo="doPrevMonthCalendarID"
		for="calendarClientID" event="onclick" operation="prevMonth" />
	<rich:componentControl attachTo="doTodayCalendarID"
		for="calendarClientID" event="onclick" operation="today" />
	<rich:componentControl attachTo="doTodayCalendarRenderedID"
		for="calendarClientID" event="onclick" operation="today" />
	<rich:componentControl attachTo="getSelectedDateID"
		for="calendarClientID" event="onclick"
		operation="alert(getSelectedDate())" />
	<rich:componentControl attachTo="getCurrentDateID"
		for="calendarClientID" event="onclick"
		operation="alert(getCurrentDate())" />
	<rich:componentControl attachTo="getCurrentMonthID"
		for="calendarClientID" event="onclick"
		operation="alert(getCurrentMonth())" />
	<rich:componentControl attachTo="getCurrentYearID"
		for="calendarClientID" event="onclick"
		operation="alert(getCurrentYear())" />
	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getValue" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('calendarClientID').value}" />
		</rich:column>
	</h:panelGrid>
</f:subview>