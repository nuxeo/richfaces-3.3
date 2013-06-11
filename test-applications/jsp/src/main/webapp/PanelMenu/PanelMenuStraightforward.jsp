<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="panelMenuStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{panelMenu.bTest1}" value="run" reRender="pmPropertyID,panelMenuID,panelMenuID2,info"></a4j:commandButton>
				<h:outputText value="#{msg.t1PanelMenu}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{panelMenu.bTest2}" value="run" reRender="pmPropertyID,panelMenuID,panelMenuID2,info"></a4j:commandButton>
				<h:outputText value="#{msg.t2PanelMenu}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{panelMenu.bTest3}" value="run" reRender="pmPropertyID,panelMenuID,panelMenuID2,info"></a4j:commandButton>
				<h:outputText value="#{msg.t3PanelMenu}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{panelMenu.bTest4}" value="run" reRender="pmPropertyID,panelMenuID,panelMenuID2,info"></a4j:commandButton>
				<h:outputText value="#{msg.t4PanelMenu}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{panelMenu.bTest5}" value="run" reRender="pmPropertyID,panelMenuID,panelMenuID2,info"></a4j:commandButton>
				<h:outputText value="#{msg.t5PanelMenu}"/>
			</h:panelGrid>
</f:subview>