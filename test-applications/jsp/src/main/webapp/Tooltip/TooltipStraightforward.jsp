<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="toolTipPropertySubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{tooltip.bTest1}" value="run" reRender="tooltipID,toolTipPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1Tooltip}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{tooltip.bTest2}" value="run" reRender="tooltipID,toolTipPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2Tooltip}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{tooltip.bTest3}" value="run" reRender="tooltipID,toolTipPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3Tooltip}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{tooltip.bTest4}" value="run" reRender="tooltipID,toolTipPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4Tooltip}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{tooltip.bTest5}" value="run" reRender="tooltipID,toolTipPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5Tooltip}"/>
			</h:panelGrid>
</f:subview>