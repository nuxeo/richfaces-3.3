<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tabPanel" prefix="rich" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/message" prefix="msg" %>

<html>
	<head>
		<title>RF-843</title>
	</head>
	<body>
		<f:view>
			<h:form>
				<rich:tabPanel switchType="ajax" immediate="false">
					<rich:tab label="test 1" immediate="false">
						<h:inputText value="value 1" ></h:inputText>
					</rich:tab>
					<rich:tab label="Sample 2" immediate="true">
						<h:inputText required="true"></h:inputText>
					</rich:tab>
				</rich:tabPanel>
				<msg:messages/> 
			</h:form>
			<a4j:log popup="false"></a4j:log> 
		</f:view>
	</body>
</html>				