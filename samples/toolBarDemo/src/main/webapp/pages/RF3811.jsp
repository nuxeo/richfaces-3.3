<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/toolBar" prefix="rich"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panel" prefix="p"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>



<html>
	<style>
	.toolbar_sep{
		padding : 0px 5px 0px 5px;
		}
	</style>

	<body>
		<f:view>
			<rich:toolBar onitemmouseover="window.status='over'" onitemmouseout="window.status='out'" styleClass="toolBar" contentClass="contentClass" rendered="true" height="20px">
				<rich:toolBarGroup id="toolBarGroup_01" location="left" itemSeparator="square" styleClass="toolBarGroup" >
					<h:outputText value="set width" />
					<h:outputText value="set width" />							
				</rich:toolBarGroup>
					<h:inputText value="increase" onclick="window.status = 'increase'" />							
					<h:inputText value="decrease" onclick="window.status = 'decrease'"/>							
			</rich:toolBar>
			<p:panel>
				<h:outputText value="" id="pic"/>
			</p:panel>
		</f:view>
	</body>
</html>
	

