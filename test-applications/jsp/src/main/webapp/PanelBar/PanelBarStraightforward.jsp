<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="panelBarStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{panelBar.bTest1}" value="run" reRender="pbPropertyID,pBId"></a4j:commandButton>
				<h:outputText value="#{msg.t1PanelBar}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{panelBar.bTest2}" value="run" reRender="pbPropertyID,pBId"></a4j:commandButton>
				<h:outputText value="#{msg.t2PanelBar}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{panelBar.bTest3}" value="run" reRender="pbPropertyID,pBId"></a4j:commandButton>
				<h:outputText value="#{msg.t3PanelBar}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{panelBar.bTest4}" value="run" reRender="pbPropertyID,pBId"></a4j:commandButton>
				<h:outputText value="#{msg.t4PanelBar}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{panelBar.bTest5}" value="run" reRender="pbPropertyID,pBId"></a4j:commandButton>
				<h:outputText value="#{msg.t5PanelBar}"/>
			</h:panelGrid>
</f:subview>