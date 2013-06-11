<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="iNSpinnerStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{inputNumberSpinner.bTest1}" value="run" reRender="SpinnerID,iNSpinnerPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1INSpinner}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{inputNumberSpinner.bTest2}" value="run" reRender="SpinnerID,iNSpinnerPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2INSpinner}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{inputNumberSpinner.bTest3}" value="run" reRender="SpinnerID,iNSpinnerPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3INSpinner}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{inputNumberSpinner.bTest4}" value="run" reRender="SpinnerID,iNSpinnerPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4INSpinner}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{inputNumberSpinner.bTest5}" value="run" reRender="SpinnerID,iNSpinnerPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5INSpinner}"/>
			</h:panelGrid>
</f:subview>