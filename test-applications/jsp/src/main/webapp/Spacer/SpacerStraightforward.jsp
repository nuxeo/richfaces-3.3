<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="spacerStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{spacer.bTest1}" value="run" reRender="spacerPropertyID,spacerId"></a4j:commandButton>
				<h:outputText value="#{msg.t1Spacer}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{spacer.bTest2}" value="run" reRender="spacerPropertyID,spacerId"></a4j:commandButton>
				<h:outputText value="#{msg.t2Spacer}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{spacer.bTest3}" value="run" reRender="spacerPropertyID,spacerId"></a4j:commandButton>
				<h:outputText value="#{msg.t3Spacer}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{spacer.bTest4}" value="run" reRender="spacerPropertyID,spacerId"></a4j:commandButton>
				<h:outputText value="#{msg.t4Spacer}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{spacer.bTest5}" value="run" reRender="spacerPropertyID,spacerId"></a4j:commandButton>
				<h:outputText value="#{msg.t5Spacer}"/>
			</h:panelGrid>
</f:subview>