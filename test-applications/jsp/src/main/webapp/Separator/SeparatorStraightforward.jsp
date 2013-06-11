<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="SeparatorStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{separator.bTest1}" value="run" reRender="separatorId,separatorPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1Separator}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{separator.bTest2}" value="run" reRender="separatorId,separatorPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2Separator}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{separator.bTest3}" value="run" reRender="separatorId,separatorPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3Separator}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{separator.bTest4}" value="run" reRender="separatorId,separatorPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4Separator}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{separator.bTest5}" value="run" reRender="separatorId,separatorPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5Separator}"/>
			</h:panelGrid>
</f:subview>