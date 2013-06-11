<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="StraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{dataTable.bTest1}" value="run" reRender="dtPropertyID,dataTableID"></a4j:commandButton>
				<h:outputText value="#{msg.t1DataTable}"/>

				<h:outputText value="Test2" />
				<a4j:commandButton action="#{dataTable.bTest2}" value="run" reRender="dtPropertyID,dataTableID"></a4j:commandButton>
				<h:outputText value="#{msg.t2DataTable}"/>

				<h:outputText value="Test3" />
				<a4j:commandButton action="#{dataTable.bTest3}" value="run" reRender="dtPropertyID,dataTableID"></a4j:commandButton>
				<h:outputText value="#{msg.t3DataTable}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{dataTable.bTest4}" value="run" reRender="dtPropertyID,dataTableID"></a4j:commandButton>
				<h:outputText value="#{msg.t4DataTable}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{dataTable.bTest5}" value="run" reRender="dtPropertyID,dataTableID"></a4j:commandButton>
				<h:outputText value="#{msg.t5DataTable}"/>
			</h:panelGrid>
</f:subview>