<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="calendarStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{calendarBean.bTest1}" value="run" reRender="calendarClientID,calendarAjaxID,calendarPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1Calendar}" />
				
				<h:outputText value="Test2" />
				<a4j:commandButton action="#{calendarBean.bTest2}" value="run" reRender="calendarClientID,calendarAjaxID,calendarPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2Calendar}" />

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{calendarBean.bTest3}" value="run" reRender="calendarClientID,calendarAjaxID,calendarPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2Calendar}" />

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{calendarBean.bTest4}" value="run" reRender="calendarClientID,calendarAjaxID,calendarPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4Calendar}" />

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{calendarBean.bTest5}" value="run" reRender="calendarClientID,calendarAjaxID,calendarPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5Calendar}" />
			</h:panelGrid>
</f:subview>
