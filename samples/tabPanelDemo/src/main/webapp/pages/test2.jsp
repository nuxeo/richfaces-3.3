<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tabPanel" prefix="rich" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/message" prefix="msg" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panel" prefix="panel" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<html>
<head>
    <title>repeater</title>
</head>

<body>
	<f:view>
		
		<h:form id="form">
		
    <panel:panel header="Errors">
		        <msg:messages/>
		    </panel:panel>
		
		    <panel:panel header="Simple Echo">
		        <h:inputText id="in0" size="50" value="#{bean.value1}">
		            <a4j:support event="onkeyup" reRender="rep"/>
		        </h:inputText>
		
		        <h:outputText value="#{bean.value1}" id="rep"/>
		    </panel:panel>
		    
		    <panel:panel header="Simple Echoless">
		        <h:inputText id="in1" size="50" value="#{bean.value2}" required="true"/><br/>
		        <msg:message for="in1"/><br/>
		
		        <h:outputText value="#{bean.value2}" id="nonrep"/>
		        <h:commandButton value="Submit"/>
		    </panel:panel>

			<rich:tabPanel id="tabPanel" selectedTab="t2" switchType="server" immediate="true">
		        <rich:tab id="t1" label="TabOne">
		            <h:outputText value="Content1"/>
		        </rich:tab>
		        <rich:tab id="t2" label="TabTwo">
		            <h:outputText value="Content2"/>
		        </rich:tab>
		    </rich:tabPanel>
		</h:form>
	</f:view>
</body>
</html>