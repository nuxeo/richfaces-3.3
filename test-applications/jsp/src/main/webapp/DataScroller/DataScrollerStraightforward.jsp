<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="datascrollerStraightforwardSubviewID">
			<h:panelGrid columns="3">
				<h:outputText value="Test1" />
				<a4j:commandButton action="#{dataScroller.bTest1}" value="run" reRender="dataTableId,dTablePropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t1DataScroller}"/>
				
				<h:outputText value="Test2" />
				<a4j:commandButton action="#{dataScroller.bTest2}" value="run" reRender="dataTableId,dTablePropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t2DataScroller}"/>
				
				<h:outputText value="Test3" />
				<a4j:commandButton action="#{dataScroller.bTest3}" value="run" reRender="dataTableId,dTablePropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t3DataScroller}"/>

				<h:outputText value="Test4" />
				<a4j:commandButton action="#{dataScroller.bTest4}" value="run" reRender="dataTableId,dTablePropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t4DataScroller}"/>

				<h:outputText value="Test5" />
				<a4j:commandButton action="#{dataScroller.bTest5}" value="run" reRender="dataTableId,dTablePropertyID"></a4j:commandButton>
				<h:outputText value="#{msg.t5DataScroller}"/>
			</h:panelGrid>
</f:subview>