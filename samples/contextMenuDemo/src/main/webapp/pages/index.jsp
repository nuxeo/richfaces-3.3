<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable"
	prefix="dt"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/contextMenu"
	prefix="cm"%>
<%@ taglib
	uri="http://labs.jboss.com/jbossrichfaces/ui/componentControl"
	prefix="cc"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/menu-components"
	prefix="mc"%>
<html>
<head>
<title></title>
<style>
<!--
.header {
	background-color: graytext;
}

.footer {
	background-color: gray;
}

.rich-cm-attached {
	cursor: help;
}
-->
</style>
</head>
<body>
<f:view>
			<h:form id="skinForm" >
       				<h:selectOneRadio binding="#{skinBean.component}"/>
        			<h:commandLink action="#{skinBean.change}" value="Set Skin"/>
				<h:outputText value=" Current skin: #{skinBean.skin}" />
			</h:form>
	<div id="sgs" style="height: 50px"><a4j:outputPanel
		ajaxRendered="true">
		<h:messages></h:messages>
	</a4j:outputPanel></div>
	<h:form id="f">
		<cm:contextMenu submitMode="server" id="m" attached="false">
			<mc:menuItem action="#{bean.edit}"
				value="Edit package : {param}" submitMode="ajax"
				actionListener="#{bean.actionListener}" icon="/icons/ico1.gif">
				<a4j:actionparam assignTo="#{bean.param}" name="param"
					value="{param}"></a4j:actionparam>
			</mc:menuItem>
			
			<mc:menuSeparator id="menuSeparator11" />
			
			<mc:menuItem action="#{bean.delete}"
				value="Delete package {param}" submitMode="ajax"
				actionListener="#{bean.actionListener}" icon="/icons/ico2.gif">
				<a4j:actionparam assignTo="#{bean.param}" name="param"
					value="{param}"></a4j:actionparam>
			</mc:menuItem>
		</cm:contextMenu>
		<dt:dataTable id="table" value="#{bean.model}" var="row"
			style="width:300px" first="0" rows="40" width="100%">
			<dt:column headerClass="header" footerClass="footer" id="c1">
				<f:facet name="header">
					<h:outputText value="Package"></h:outputText>
				</f:facet>
				<f:facet name="footer">
					<h:outputText value="Package"></h:outputText>
				</f:facet>
				<h:outputText value="#{row.first}">
				</h:outputText>
			</dt:column>
			<dt:column headerClass="header" footerClass="footer" id="x">
				<f:facet name="header">
					<h:outputText value="Description"></h:outputText>
				</f:facet>
				<f:facet name="footer">
					<h:outputText value="Description"></h:outputText>
				</f:facet>
				<h:outputText value="#{row.second}" />
				<cc:componentControl event="oncontextmenu" attachTo="c1,x" for=":f:m"
					operation="doShow">
					<f:param name="param" value="#{row.first}"></f:param>
				</cc:componentControl>
			</dt:column>
		</dt:dataTable>
	</h:form>

</f:view>
</body>
</html>
