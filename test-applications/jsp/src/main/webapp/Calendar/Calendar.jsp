<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="calendarSubviewID">

	<h:messages />
	<rich:messages showDetail="true"></rich:messages>

	<h:panelGrid columns="2">
		<h:outputText value="Client mode"
			rendered="#{calendarBean.renderedClient}" />
		<h:outputText value="Ajax mode"
			rendered="#{calendarBean.renderedAjax}" />

		<rich:calendar id="calendarClientID"
		
			binding="#{calendarBean.htmlCalendar}"
			firstWeekDay="#{calendarBean.firstWeekDay}"
			defaultTime="#{calendarBean.defaultTime}"
			resetTimeOnDateSelect="#{calendarBean.resetTimeOnDateSelect}"
			showFooter="#{calendarBean.showFooter}"
			showHeader="#{calendarBean.showHeader}"
			horizontalOffset="#{calendarBean.horizontalOffset}"
			verticalOffset="#{calendarBean.verticalOffset}"
			ignoreDupResponses="#{calendarBean.ignoreDupResponses}"
			inputSize="#{calendarBean.inputSize}" eventsQueue="myEventsQueue"
			label="calendarLabel" limitToList="#{calendarBean.limitToList}"
			dataModel="#{calendarDataModel}" style="#{style.style}"
			styleClass="#{style.styleClass}" inputStyle="#{style.inputStyle}"
			locale="#{calendarBean.locale}" popup="#{calendarBean.popup}"
			preloadDateRangeBegin="#{calendarBean.prDateRangeBegin}"
			preloadDateRangeEnd="#{calendarBean.prDateRangeEnd}"
			datePattern="#{calendarBean.pattern}"
			weekDayLabels="#{calendarBean.weekDayLabels}"
			weekDayLabelsShort="#{calendarBean.weekDayLabelsShort}"
			monthLabels="#{calendarBean.monthLabels}"
			monthLabelsShort="#{calendarBean.monthLabelsShort}"
			value="#{calendarBean.selectedDate}"
			currentDate="#{calendarBean.currentDate}"
			jointPoint="#{calendarBean.jointPoint}"
			direction="#{calendarBean.direction}"
			enableManualInput="#{calendarBean.enableManualInput}"
			showInput="#{calendarBean.showInput}"
			buttonLabel="#{calendarBean.label}"
			boundaryDatesMode="#{calendarBean.boundary}"
			currentDateChangeListener="#{calendarBean.dcl}"
			valueChangeListener="#{calendarBean.ddd}"
			reRender="calendarPropertyID,counter" inputClass="ic"
			buttonClass="bc" ajaxSingle="#{calendarBean.ajaxSingle}"
			buttonIcon="#{calendarBean.icon}"
			buttonIconDisabled="#{icon.iconFileManagerReject}"
			disabled="#{calendarBean.disabled}"
			readonly="#{calendarBean.readonly}"
			bypassUpdates="#{calendarBean.bypassUpdates}"
			zindex="#{calendarBean.zindex}"			
			rendered="#{calendarBean.renderedClient}" focus="popupModeID"
			mode="client" required="#{calendarBean.required}"
			requiredMessage="Required Message" timeZone="#{calendarBean.tmZone}"
			cellHeight="#{calendarBean.cellHeight}"
			cellWidth="#{calendarBean.cellWidth}"
			showApplyButton="#{calendarBean.showApplyButton}"
			showWeekDaysBar="#{calendarBean.showWeekDaysBar}"
			showWeeksBar="#{calendarBean.showWeeksBar}"
			todayControlMode="#{calendarBean.todayControlMode}"
			immediate="#{calendarBean.immediate}"
			onbeforedomupdate="#{event.onbeforedomupdate}"
			onchanged="#{event.onchanged}" oncollapse="#{event.oncollapse}"
			oncomplete="#{event.oncomplete}"
			oncurrentdateselect="#{event.oncurrentdateselect}"
			ondatemouseout="#{event.ondatemouseout}"
			ondatemouseover="#{event.ondatemouseover}"
			ondateselect="#{event.ondateselect}"
			ondateselected="#{event.ondateselected}" onexpand="#{event.onexpand}"
			oninputblur="#{event.oninputblur}"
			oninputchange="#{event.oninputchange}"
			oninputclick="#{event.oninputclick}"
			oninputfocus="#{event.oninputfocus}"
			oninputkeydown="#{event.oninputkeydown}"
			oninputkeypress="#{event.oninputkeypress}"
			oninputkeyup="#{event.oninputkeyup}"
			oninputselect="#{event.oninputselect}"
			ontimeselect="#{event.ontimeselect}"
			ontimeselected="#{event.ontimeselected}"
			>
			<f:facet name="weekDay">
				<f:verbatim>
					<span style="padding: 2px; font-size: 4">{weekDayLabel +
					weekDayLabelShort}</span>
				</f:verbatim>
			</f:facet>

			<f:facet name="optionalHeader">
				<h:outputText value="optionalHeader Facet" />
			</f:facet>

			<f:facet name="optionalFooter">
				<h:outputText value="optionalFooter Facet" />
			</f:facet>

			<f:validator validatorId="org.richfaces.CalendarValidator" />

			<h:panelGrid columns="2">
				<f:verbatim>
					<span style="padding: 2px;">{day}</span>
				</f:verbatim>
				<h:panelGrid>
					<h:outputText styleClass="smallText" value="{data.enLabel}" />
					<h:outputText styleClass="smallText" value="{data.frLabel}" />
					<h:outputText styleClass="smallText" value="{data.deLabel}" />
				</h:panelGrid>
			</h:panelGrid>
		</rich:calendar>

		<rich:calendar id="calendarAjaxID" 
			dataModel="#{calendarDataModel}" locale="#{calendarBean.locale}"
			popup="#{calendarBean.popup}" datePattern="#{calendarBean.pattern}"
			weekDayLabels="#{calendarBean.weekDayLabels}"
			weekDayLabelsShort="#{calendarBean.weekDayLabelsShort}"
			monthLabels="#{calendarBean.monthLabels}"
			monthLabelsShort="#{calendarBean.monthLabelsShort}"
			todayControlMode="#{calendarBean.todayControlMode}"
			value="#{calendarBean.selectedDate}"
			currentDate="#{calendarBean.currentDate}"
			cellHeight="#{calendarBean.cellHeight}"
			cellWidth="#{calendarBean.cellWidth}"
			jointPoint="#{calendarBean.jointPoint}"
			direction="#{calendarBean.direction}"
			enableManualInput="#{calendarBean.enableManualInput}"
			showInput="#{calendarBean.showInput}"
			buttonLabel="#{calendarBean.label}"
			boundaryDatesMode="#{calendarBean.boundary}"
			currentDateChangeListener="#{calendarBean.dcl}"
			valueChangeListener="#{calendarBean.ddd}"
			reRender="calendarPropertyID,counter" inputClass="ic"
			buttonClass="bc" ajaxSingle="#{calendarBean.ajaxSingle}"
			buttonIcon="#{calendarBean.icon}"
			buttonIconDisabled="#{icon.iconFileManagerReject}"
			disabled="#{calendarBean.disabled}"
			readonly="#{calendarBean.readonly}"
			bypassUpdates="#{calendarBean.bypassUpdates}"
			zindex="#{calendarBean.zindex}"			
			rendered="#{calendarBean.renderedAjax}" focus="popupModeID"
			mode="ajax" required="#{calendarBean.required}"
			requiredMessage="Required Message" timeZone="#{calendarBean.tmZone}"
			immediate="#{calendarBean.immediate}"
			onbeforedomupdate="#{event.onbeforedomupdate}"
			onchanged="#{event.onchanged}" oncollapse="#{event.oncollapse}"
			oncomplete="#{event.oncomplete}"
			oncurrentdateselect="#{event.oncurrentdateselect}"
			ondatemouseout="#{event.ondatemouseout}"
			ondatemouseover="#{event.ondatemouseover}"
			ondateselect="#{event.ondateselect}"
			ondateselected="#{event.ondateselected}" onexpand="#{event.onexpand}"
			oninputblur="#{event.oninputblur}"
			oninputchange="#{event.oninputchange}"
			oninputclick="#{event.oninputclick}"
			oninputfocus="#{event.oninputfocus}"
			oninputkeydown="#{event.oninputkeydown}"
			oninputkeypress="#{event.oninputkeypress}"
			oninputkeyup="#{event.oninputkeyup}"
			oninputselect="#{event.oninputselect}"
			ontimeselect="#{event.ontimeselect}"
			ontimeselected="#{event.ontimeselected}"
								
			firstWeekDay="#{calendarBean.firstWeekDay}"
			defaultTime="#{calendarBean.defaultTime}"
			resetTimeOnDateSelect="#{calendarBean.resetTimeOnDateSelect}"
			horizontalOffset="#{calendarBean.horizontalOffset}"
			verticalOffset="#{calendarBean.verticalOffset}"
			inputSize="#{calendarBean.inputSize}"
			showApplyButton="#{calendarBean.showApplyButton}"
			showWeekDaysBar="#{calendarBean.showWeekDaysBar}"
			showWeeksBar="#{calendarBean.showWeeksBar}"
			>

			<f:facet name="weekDay">
				<f:verbatim>
					<span style="padding: 2px; font-size: 4">{weekDayLabel +
					weekDayLabelShort}</span>
				</f:verbatim>
			</f:facet>

			<f:facet name="optionalHeader">
				<h:outputText value="optionalHeader Facet" />
			</f:facet>

			<f:facet name="optionalFooter">
				<h:outputText value="optionalFooter Facet" />
			</f:facet>

			<f:validator validatorId="org.richfaces.CalendarValidator" />

			<h:panelGrid columns="2">
				<f:verbatim>
					<span style="padding: 2px;">{day}</span>
					<h:outputText value="#{day}"></h:outputText>
				</f:verbatim>
				<h:panelGrid>
					<h:outputText styleClass="smallText" value="{data.enLabel}" />
					<h:outputText styleClass="smallText" value="{data.frLabel}" />
					<h:outputText styleClass="smallText" value="{data.deLabel}" />
				</h:panelGrid>
			</h:panelGrid>
		</rich:calendar>
	</h:panelGrid>
	<h:outputLink
		value="http://www.jboss.com/index.html"></h:outputLink>
</f:subview>
