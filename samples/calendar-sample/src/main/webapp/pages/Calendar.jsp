<%@ page pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/calendar" prefix="calendar" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/modal-panel" prefix="mp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
	<head>
		<title></title>
		<style type="text/css">
			.smallText {
				font-size: xx-small;
			}
			.largeText {
				font-size: xx-large;
			}
			
			.Selecteddayclass {
				background-color: #0087FF;
			}
		</style>
		<script>
			var statusStart = 0;
			var statusStop = 0;
			var supportComplete = 0;
		</script>
	</head>
	<body>
		<f:view>
				<a4j:outputPanel ajaxRendered="true">
					<h:messages showDetail="true" showSummary="true"/>
				</a4j:outputPanel>
			<h:form>
				<a4j:commandLink value="Click" oncomplete="alert('supportComplete: '+(++supportComplete));" />
				<h:panelGroup id="counter">
					<h:outputText value="#{calendarBean.counter}" />
				</h:panelGroup>
				
				<h:panelGrid columns="1">
					<h:selectOneRadio binding="#{skinBean.component}" />
		             <h:commandLink action="#{skinBean.change}" value="set skin" />
		        </h:panelGrid>
		        <br />
		        <br />
		        <calendar:calendar todayControlMode="#{calendarBean.todayControlMode}" datePattern="#{calendarBean.pattern}" showApplyButton="#{calendarBean.showApplyButton}" popup="#{calendarBean.popup}"/>
		        <calendar:calendar cellWidth="5" cellHeight="5"/>
		        <calendar:calendar cellWidth="40" cellHeight="40" popup="#{calendarBean.popup}"/>
				<calendar:calendar 
					id="calendar"
					dataModel="#{calendarDataModel}"
					locale="#{calendarBean.locale}"
					popup="#{calendarBean.popup}"
					datePattern="#{calendarBean.pattern}"
					weekDayLabelsShort="#{calendarBean.weekDayLabelsShort}"
					value="#{calendarBean.selectedDate}"
					currentDate="#{calendarBean.currentDate}"
					jointPoint="#{calendarBean.jointPoint}"
					direction="#{calendarBean.direction}"
					buttonLabel="Select Date"					
					enableManualInput="#{calendarBean.enableManualInput}"
					disabled="#{calendarBean.disabled}"
					showInput="#{calendarBean.showInput}"
					boundaryDatesMode="#{calendarBean.boundary}"
					currentDateChangeListener="#{calendarBean.dcl}"
					valueChangeListener="#{calendarBean.ddd}"
					showHeader="#{calendarBean.showHeader}"
					showFooter="#{calendarBean.showFooter}"
					reRender="counter"
					inputClass="ic"
					buttonClass="bc"
					horizontalOffset="3"
					verticalOffset="3"
					cellHeight="50"
					cellWidth="50"
					mode="ajax"
					showApplyButton="#{calendarBean.showApplyButton}"
					todayControlMode="#{calendarBean.todayControlMode}"
					resetTimeOnDateSelect="#{calendarBean.resetTimeOnDateSelect}">
					<f:facet name="optionalHeader">
						<h:outputText value="optionalHeader Facet" />
					</f:facet>

					<f:facet name="weekDay"><f:verbatim><span style="padding: 2px; font-size: 10px" >{weekDayLabelShort}</span></f:verbatim></f:facet>
					
					<f:validator validatorId="org.richfaces.CalendarValidator" />
					
					<h:panelGrid columns="2">
						<f:verbatim><span style="padding: 2px;" <%-- class="largeText"--%>>{day}</span></f:verbatim>
						<h:panelGrid>
							<h:outputText styleClass="smallText" value="{data.enLabel}" />
							<h:outputText styleClass="smallText" value="{data.frLabel}" />
							<h:outputText styleClass="smallText" value="{data.deLabel}" />
						</h:panelGrid>
					</h:panelGrid>
				</calendar:calendar>
				
				<h:panelGrid columns="2">
					<h:outputText value="Select Locale:" />
					<h:selectOneRadio onchange="submit()" value="en/US" valueChangeListener="#{calendarBean.selectLocale}">
						<f:selectItem itemLabel="US" itemValue="en/US"/>
						<f:selectItem itemLabel="DE" itemValue="de/DE"/>
						<f:selectItem itemLabel="FR" itemValue="fr/FR"/>
						<f:selectItem itemLabel="RU" itemValue="ru/RU"/>
					</h:selectOneRadio>	
					<h:outputText value="Popup Mode:" />
					<h:selectBooleanCheckbox value="#{calendarBean.popup}" onclick="submit()"/>
					<h:outputText value="Custom day labels:" />
					<h:selectBooleanCheckbox value="#{calendarBean.useCustomDayLabels}" onclick="submit()"/>
					<h:outputText value="Show Apply Button:" />
					<h:selectBooleanCheckbox value="#{calendarBean.showApplyButton}" onclick="submit()"/>
					<h:outputText value="Show Header:" />
					<h:selectBooleanCheckbox value="#{calendarBean.showHeader}" onclick="submit()"/>
					<h:outputText value="Show Footer:" />
					<h:selectBooleanCheckbox value="#{calendarBean.showFooter}" onclick="submit()"/>
					<h:outputText value="Select Date Pattern:"/>
					<h:selectOneMenu value="MMM d, yyyy" onchange="submit()" valueChangeListener="#{calendarBean.selectPattern}">
						<f:selectItem itemLabel="yyyyMMdd" itemValue="yyyyMMdd"/>
						<f:selectItem itemLabel="d/M/yy" itemValue="d/M/yy"/>
						<f:selectItem itemLabel="dd/M/yy" itemValue="dd/M/yy"/>
						<f:selectItem itemLabel="d/MMM/y" itemValue="d/MMM/y"/>
						<f:selectItem itemLabel="MMM d, yyyy" itemValue="MMM d, yyyy"/>
						<f:selectItem itemLabel="dd/M/yy HH:mm" itemValue="dd/M/yy HH:mm"/>	
						<f:selectItem itemLabel="MMM d, yyyy h:mm a" itemValue="MMM d, yyyy h:mm a"/>												
					</h:selectOneMenu>
					<h:inputText id="selectdate"/><h:commandButton type="button" value="Select Date" onclick="$(this.form.id+':calendar').component.selectDate(this.form[this.form.id+':selectdate'].value);"/>
					<h:outputText value="Select Popup Joint Point:" />
					<h:selectOneRadio onchange="submit()" value="#{calendarBean.jointPoint}" valueChangeListener="#{calendarBean.selectJointPoint}">
						<f:selectItem itemLabel="bottom-right" itemValue="bottom-right"/>
						<f:selectItem itemLabel="bottom-left" itemValue="bottom-left"/>
						<f:selectItem itemLabel="top-right" itemValue="top-right"/>
						<f:selectItem itemLabel="top-left" itemValue="top-left"/>
					</h:selectOneRadio>
					<h:outputText value="Select Popup Direction:" />
					<h:selectOneRadio onchange="submit()" value="#{calendarBean.direction}" valueChangeListener="#{calendarBean.selectDirection}">
						<f:selectItem itemLabel="bottom-right" itemValue="bottom-right"/>
						<f:selectItem itemLabel="bottom-left" itemValue="bottom-left"/>
						<f:selectItem itemLabel="top-right" itemValue="top-right"/>
						<f:selectItem itemLabel="top-left" itemValue="top-left"/>
						<f:selectItem itemLabel="auto" itemValue="auto"/>
					</h:selectOneRadio>
					<h:outputText value="BoundaryDatesMode:" />
					<h:selectOneRadio onclick="submit()" value="#{calendarBean.boundary}">
						<f:selectItem itemLabel="inactive" itemValue="inactive"/>
						<f:selectItem itemLabel="select" itemValue="select"/>
						<f:selectItem itemLabel="scroll" itemValue="scroll"/>						
					</h:selectOneRadio>
					
					<h:outputText value="TodayControlMode:" />
					<h:selectOneRadio onclick="submit()" value="#{calendarBean.todayControlMode}">
						<f:selectItem itemLabel="hidden" itemValue="hidden"/>
						<f:selectItem itemLabel="select" itemValue="select"/>
						<f:selectItem itemLabel="scroll" itemValue="scroll"/>						
					</h:selectOneRadio>
					<h:outputText value="EnableManualInput:"></h:outputText>
					<h:selectBooleanCheckbox value="#{calendarBean.enableManualInput}"
						onclick="submit()">
					</h:selectBooleanCheckbox>
					<h:outputText value="ShowInput:"></h:outputText>
					<h:selectBooleanCheckbox value="#{calendarBean.showInput}"
						onclick="submit()">
					</h:selectBooleanCheckbox>
					<h:outputText value="Disabled:"></h:outputText>
					<h:selectBooleanCheckbox value="#{calendarBean.disabled}"
						onclick="submit()">
					</h:selectBooleanCheckbox>
					<h:outputText value="resetTimeOnDateSelect:"></h:outputText>
					<h:selectBooleanCheckbox value="#{calendarBean.resetTimeOnDateSelect}" onclick="submit()">
					</h:selectBooleanCheckbox>
								
		

	 			<%-- 	<h:commandButton id="calendarPopup" type="button" value="popup" onclick="$(this.form.id+':calendar').component.doSwitch();"/>														
	--%>			</h:panelGrid>				
				
				<h:outputText value="Current date: "/>
				<h:outputText value="#{calendarBean.currentDateAsText}" />
				<f:verbatim><br /></f:verbatim>
				<h:outputText value="Selected date: "/>
				<h:outputText value="#{calendarBean.selectedDate}" />
				<f:verbatim><br /></f:verbatim>
				
				<h:commandButton value="Submit"/>

				<hr/>
				
				<mp:modalPanel id="_panel" showWhenRendered="false" left="300" top="300" > 
						<f:facet name="header">
							<f:verbatim>
								Header
							</f:verbatim>
						</f:facet>
						
						<f:facet name="controls">
							<f:verbatim>
								<a href="#" onclick="Richfaces.hideModalPanel('_panel');">close</a>
							</f:verbatim>
  						</f:facet>

					<calendar:calendar popup="true" /> 
				</mp:modalPanel> 

				<f:verbatim>
					<input type="button" value="Show Modal Panel Bug" onclick="Richfaces.showModalPanel('_panel');"></input>
				</f:verbatim>
				
			</h:form>
		</f:view>
	</body>	
</html>
