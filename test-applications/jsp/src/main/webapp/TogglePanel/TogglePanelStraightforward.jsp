<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="togglePanelStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{togglePanel.bTest1}" value="run" reRender="panel1,panel2,tPanelPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1TogglePanel}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{togglePanel.bTest2}" value="run" reRender="panel1,panel2,tPanelPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2TogglePanel}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{togglePanel.bTest3}" value="run" reRender="panel1,panel2,tPanelPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3TogglePanel}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{togglePanel.bTest4}" value="run" reRender="panel1,panel2,tPanelPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4TogglePanel}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{togglePanel.bTest5}" value="run" reRender="panel1,panel2,tPanelPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5TogglePanel}"/>
			</h:panelGrid>
</f:subview>