<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="ModalPanelStraightforwardSubviewID">
	<h:panelGrid columns="3">
		<h:outputText value="Test1" />
		<a4j:commandButton action="#{modalPanel.bTest1}" value="run" reRender="modalPanelID,mpPropertyID"></a4j:commandButton>
		<h:outputText value="#{msg.t1ModalPanel}"/>

		<h:outputText value="Test2" />
		<a4j:commandButton action="#{modalPanel.bTest2}" value="run" reRender="modalPanelID,mpPropertyID"></a4j:commandButton>
		<h:outputText value="#{msg.t2ModalPanel}"/>

		<h:outputText value="Test3" />
		<a4j:commandButton action="#{modalPanel.bTest3}" value="run" reRender="modalPanelID,mpPropertyID"></a4j:commandButton>
		<h:outputText value="#{msg.t3ModalPanel}"/>

		<h:outputText value="Test4" />
		<a4j:commandButton action="#{modalPanel.bTest4}" value="run" reRender="modalPanelID,mpPropertyID"></a4j:commandButton>
		<h:outputText value="#{msg.t4ModalPanel}"/>

		<h:outputText value="Test5" />
		<a4j:commandButton action="#{modalPanel.bTest5}" value="run" reRender="modalPanelID,mpPropertyID"></a4j:commandButton>
		<h:outputText value="#{msg.t5ModalPanel}"/>
	</h:panelGrid>
</f:subview>