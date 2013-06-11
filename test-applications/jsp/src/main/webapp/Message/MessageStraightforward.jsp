<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="MessageStraightforwardSubviewID">
		<rich:simpleTogglePanel switchType="client" opened="true" label="modalPanel straightforward">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{message.bTest1}" value="run" onclick="submit()"></a4j:commandButton>
				<h:outputText value="#{msg.t1Message}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{message.bTest2}" value="run" onclick="submit()"></a4j:commandButton>
				<h:outputText value="#{msg.t2Message}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{message.bTest3}" value="run" onclick="submit()"></a4j:commandButton>
				<h:outputText value="#{msg.t3Message}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{message.bTest4}" value="run" onclick="submit()"></a4j:commandButton>
				<h:outputText value="#{msg.t4Message}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{message.bTest5}" value="run" onclick="submit()"></a4j:commandButton>
				<h:outputText value="#{msg.t5Message}"/>
			</h:panelGrid>
		</rich:simpleTogglePanel>
</f:subview>