<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="inputNumberSliderStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{inputNumberSlider.bTest1}" value="run" reRender="SliderId,iNSliderPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1INSlider}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{inputNumberSlider.bTest2}" value="run" reRender="SliderId,iNSliderPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2INSlider}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{inputNumberSlider.bTest3}" value="run" reRender="SliderId,iNSliderPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3INSlider}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{inputNumberSlider.bTest4}" value="run" reRender="SliderId,iNSliderPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4INSlider}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{inputNumberSlider.bTest5}" value="run" reRender="SliderId,iNSliderPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5INSlider}"/>
			</h:panelGrid>
</f:subview>