<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="PanelStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{panel.bTest1}" value="run" reRender="panelPropertyID,panelId,p1,t1,t2,t3,o1,o2"></a4j:commandButton>
				<h:outputText value="#{msg.t1Panel}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{panel.bTest2}" value="run" reRender="panelPropertyID,panelId,p1,t1,t2,t3,o1,o2"></a4j:commandButton>
				<h:outputText value="#{msg.t2Panel}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{panel.bTest3}" value="run" reRender="panelPropertyID,panelId,p1,t1,t2,t3,o1,o2"></a4j:commandButton>
				<h:outputText value="#{msg.t3Panel}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{panel.bTest4}" value="run" reRender="panelPropertyID,panelId,p1,t1,t2,t3,o1,o2"></a4j:commandButton>
				<h:outputText value="#{msg.t4Panel}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{panel.bTest5}" value="run" reRender="panelPropertyID,panelId,p1,t1,t2,t3,o1,o2"></a4j:commandButton>
				<h:outputText value="#{msg.t5Panel}"/>
			</h:panelGrid>
</f:subview>