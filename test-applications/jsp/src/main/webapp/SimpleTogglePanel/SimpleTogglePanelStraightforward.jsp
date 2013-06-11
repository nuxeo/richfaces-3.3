<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="stpStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{simpleTogglePanel.bTest1}" value="run" reRender="sTP,sTP1,sTP2,stpPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1SimpleTP}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{simpleTogglePanel.bTest2}" value="run" reRender="sTP,sTP1,sTP2,stpPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2SimpleTP}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{simpleTogglePanel.bTest3}" value="run" reRender="sTP,sTP1,sTP2,stpPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3SimpleTP}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{simpleTogglePanel.bTest4}" value="run" reRender="sTP,sTP1,sTP2,stpPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4SimpleTP}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{simpleTogglePanel.bTest5}" value="run" reRender="sTP,sTP1,sTP2,stpPropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5SimpleTP}"/>
			</h:panelGrid>
</f:subview>