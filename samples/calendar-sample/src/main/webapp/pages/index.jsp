<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/ui/calendar" prefix="calendar" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<h:form>
				<h:panelGrid columns="1">
					<h:selectOneRadio binding="#{skinBean.component}" />
		            <h:commandLink action="#{skinBean.change}" value="set skin" />
		        </h:panelGrid>
		        <br />
		        <br />
			<calendar:calendar id="calendar" dataModel="#{calendarDataModel}" locale="#{calendarBean.locale}">
				<f:facet name="header">
					<f:verbatim>
						{previousMonthControl} | {nextMonthControl}					
					</f:verbatim>
				</f:facet>
				<f:facet name="weekNumber">
					<h:outputText style="font-weight: bold;" value="{weekNumber}" />
				</f:facet>
				<f:facet name="weekDay">
					<h:outputText style="font-style: italic;" value="{weekDayLabel}" />
				</f:facet>

				<h:panelGrid>
					<h:outputText value="{data.enLabel}" />
					<h:outputText value="{data.frLabel}" />
					<h:outputText value="{data.deLabel}" />
				</h:panelGrid>
			
				<h:outputText value="{day}" />
				<h:inputText value="{test}" />
				<h:outputText escape="false" value="<br xmlns:test='urn:test' test:aaa='sss'>&nbsp;&amp;<a href='#' class='{test}'>end<div xmlns:prefix='urn:urn'><p value='aaa'><p value='bbb'></p></div>" />
			</calendar:calendar>


				<calendar:calendar 
					id="calendar1"
					locale="#{calendarBean.locale}"
					popup="#{calendarBean.popup}"
					datePattern="#{calendarBean.pattern}">
				</calendar:calendar>
				
				<h:panelGrid columns="2">
					<h:outputText value="Select Locale:" />
					<h:selectOneRadio onchange="submit()" valueChangeListener="#{calendarBean.selectLocale}">
						<f:selectItem itemLabel="US" itemValue="en/US"/>
						<f:selectItem itemLabel="EN" itemValue="en/GB"/>
						<f:selectItem itemLabel="FR" itemValue="fr/FR"/>
					</h:selectOneRadio>	
					<h:outputText value="Popup Mode:" />
					<h:selectBooleanCheckbox value="#{calendarBean.locale}" onclick="submit()"/>
					<h:outputText value="Select Date Pattern:"/>
					<h:selectOneMenu value="#{calendarBean.pattern}" onchange="submit()">
						<f:selectItem itemLabel="d/M/yy" itemValue="d/M/yy"/>
						<f:selectItem itemLabel="dd/MM/yy" itemValue="dd/MM/yy"/>
						<f:selectItem itemLabel="d/MMM/y" itemValue="d/MMM/y"/>
						<f:selectItem itemLabel="MMM d, yyyy" itemValue="d/MMM/y"/>												
					</h:selectOneMenu>														
				</h:panelGrid>	
			</h:form>
		</f:view>
	</body>	
</html>
