<%@ page pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/calendar" prefix="calendar" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
				<a4j:outputPanel ajaxRendered="true">
					<h:messages showDetail="true" showSummary="true"/>
				</a4j:outputPanel>
			<h:form>				
				<calendar:calendar
					id="calendar"
					value="#{calendarBean.selectedDate}"
					mode="ajax"
					currentDateChangeListener="#{calendarBean.dcl}">
				</calendar:calendar>
				<a4j:commandButton value="Press me"/>
			</h:form>
		</f:view>
	</body>	
</html>
