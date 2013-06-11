<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="suggestionboxStraightforwardSubviewID">
		<h:panelGrid columns="3">
			<h:outputText value="Test1" />
			<a4j:commandButton action="#{sb.bTest1}" value="run" reRender="sbPropertyID,suggestionBoxId"></a4j:commandButton>
			<h:outputText value="#{msg.t1SB}"/>

			<h:outputText value="Test2" />
			<a4j:commandButton action="#{sb.bTest2}" value="run" reRender="sbPropertyID,suggestionBoxId"></a4j:commandButton>
			<h:outputText value="#{msg.t2SB}"/>

			<h:outputText value="Test3" />
			<a4j:commandButton action="#{sb.bTest3}" value="run" reRender="sbPropertyID,suggestionBoxId"></a4j:commandButton>
			<h:outputText value="#{msg.t3SB}"/>

			<h:outputText value="Test4" />
			<a4j:commandButton action="#{sb.bTest4}" value="run" reRender="sbPropertyID,suggestionBoxId"></a4j:commandButton>
			<h:outputText value="#{msg.t4SB}"/>

			<h:outputText value="Test5" />
			<a4j:commandButton action="#{sb.bTest5}" value="run" reRender="sbPropertyID,suggestionBoxId"></a4j:commandButton>
			<h:outputText value="#{msg.t5SB}"/>
		</h:panelGrid>
</f:subview>