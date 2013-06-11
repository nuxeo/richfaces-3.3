
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
			<h:messages showDetail="true" showSummary="true"/>
			<h:form>				
				<calendar:calendar immediate="false" value="#{calendarBean.selectedDate}" datePattern="#{calendarBean.pattern}" enableManualInput="true">
				</calendar:calendar>
				<h:inputText>
				<f:validateLength maximum="2"/>
				</h:inputText>
				<h:commandButton immediate="true"/>
			</h:form>
		</f:view>
	</body>	
</html>
