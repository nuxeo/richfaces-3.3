<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/orderingList" prefix="ol" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<html>
	<head>
		<style type="text/css">
			.columnClass {
				vertical-align: top;
			}
			.eventDiv {
				background-color: green;
				color: white;
				width: 200px;
				height: 50px;
				font-weight: bold;
			}
			
			.zebraCell1 {
				background-color: yellow;
			}
			
			.zebraCell2 {
				background-color: fuchsia;			
			}

			.zebraRow1 * {
				color: white;
			}
			
			.zebraRow2 * {
				color: navy;
			}
		</style>
		
		<title>Ordering List Demo.</title>
	</head>
	<body>
		<f:view>
			<a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/scriptaculous/effects.js" />
			<a4j:outputPanel ajaxRendered="true">
				<h:messages />
			</a4j:outputPanel>
			
			<h:form id="skinForm" >
				<h:selectOneRadio binding="#{skinBean.component}" />
				<h:commandLink action="#{skinBean.change}" value="set skin" />
				<h:outputText value=" Current skin: #{skinBean.skin}" /><br />
			</h:form>
			<h:form id="form" >
				<a4j:status startText="START" startStyle="color: red;"
					stopText="STOP" stopStyle="color: green;" />
				<br />
				<h:panelGrid columns="2" columnClasses="columnClass">
					<h:panelGroup>
						<ol:orderingList id="orderingList1" 
								converter="#{converter}"
								value="#{demoBean.items}" var="item" 
								captionLabel="#{demoBean.captionLabel}"
								controlsType="#{demoBean.controlsType}"
								controlsHorizontalAlign="#{demoBean.controlsHorizontalAlign}"
								controlsVerticalAlign="#{demoBean.controlsVerticalAlign}"
								orderControlsVisible="#{demoBean.orderControlsVisible}"
								fastOrderControlsVisible="#{demoBean.fastOrderControlsVisible}"
								
								selection="#{bean.selection}" 
								activeItem="#{bean.activeItem}"

								ontopclick="#{demoBean.ontopclick}"
								onbottomclick="#{demoBean.onbottomclick}"
								onorderchanged="orderChanged(event); new Effect.Highlight('form:onorderchangedDiv', {startcolor:'#FF0000', endcolor:'#FF0000', restorecolor: 'green'});"
								onorderchange="orderChanged(event); new Effect.Highlight('form:onorderchangedDiv', {startcolor:'#FF0000', endcolor:'#FF0000', restorecolor: 'green'});return false;"
								ondownclick="new Effect.Highlight('form:ondownclickDiv', {startcolor:'#FF0000', endcolor:'#FF0000', restorecolor: 'green'});"
								onheaderclick="new Effect.Highlight('form:onheaderclickDiv', {startcolor:'#FF0000', endcolor:'#FF0000', restorecolor: 'green'});"
								onupclick="new Effect.Highlight('form:onupclickDiv', {startcolor:'#FF0000', endcolor:'#FF0000', restorecolor: 'green'});" >
							
							<h:column>
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:inputText value="#{item.name}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Price" />
								</f:facet>
								<h:outputText value="#{item.price}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Ajax Action" />
								</f:facet>
								<a4j:commandButton value="Ajax Action" reRender="actionResult"
									action="#{item.action}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Server Action" />
								</f:facet>
								<h:commandLink value="Server Action" action="#{item.action}" />
							</h:column>
							<h:column>
								<h:outputText binding="#{bean.eventsBouncer}"></h:outputText>
							</h:column>
						</ol:orderingList>
						
						<h:panelGrid columns="2">
							<h:panelGroup styleClass="eventDiv" id="ontopclickDiv" >
								<h:outputText value="ontopclick" />
							</h:panelGroup>
							<h:panelGroup styleClass="eventDiv" id="onbottomclickDiv" >
								<h:outputText value="onbottomclick" />
							</h:panelGroup>
							<h:panelGroup styleClass="eventDiv" id="onorderchangedDiv" >
								<h:outputText value="onorderchanged" />
							</h:panelGroup>
							<h:panelGroup styleClass="eventDiv" id="ondownclickDiv" >
								<h:outputText value="ondownclick" />
							</h:panelGroup>
							<h:panelGroup styleClass="eventDiv" id="onupclickDiv">
								<h:outputText value="onupclick" />
							</h:panelGroup>
							<h:panelGroup styleClass="eventDiv" id="onheaderclickDiv" >
								<h:outputText value="onheaderclick" />
							</h:panelGroup>
						</h:panelGrid>
						
						<h:panelGrid columns="2">
							<h:outputText value="Item Clicked: " />
							<h:outputText value="#{demoBean.actionResult}" id="actionResult" />
						
							<h:outputText value="Caption Label" />
							<h:inputText value="#{demoBean.captionLabel}" >
								<a4j:support event="onchange" reRender="orderingList1" />
							</h:inputText>
							
							<h:outputText value="Controls Type" />
							<h:selectOneRadio value="#{demoBean.controlsType}" >
								<f:selectItem itemValue="button" itemLabel="button" />
								<f:selectItem itemValue="link" itemLabel="link" />
								<f:selectItem itemValue="none" itemLabel="none" />
								<a4j:support event="onchange" reRender="orderingList1" />
							</h:selectOneRadio>
							
							<h:outputText value="Controls Horizontal Align" />
							<h:selectOneRadio value="#{demoBean.controlsHorizontalAlign}" >
								<f:selectItem itemValue="left" itemLabel="left" />
								<f:selectItem itemValue="right" itemLabel="right" />
								<a4j:support event="onchange" reRender="orderingList1" ajaxSingle="true" />
							</h:selectOneRadio>
							
							<h:outputText value="Controls Vertical Align" />
							<h:selectOneRadio value="#{demoBean.controlsVerticalAlign}" >
								<f:selectItem itemValue="top" itemLabel="top" />
								<f:selectItem itemValue="bottom" itemLabel="bottom" />
								<f:selectItem itemValue="middle" itemLabel="middle" />
								<a4j:support event="onchange" reRender="orderingList1" ajaxSingle="true" />
							</h:selectOneRadio>
							
							<h:outputText value="Order Controls Visible" />
							<h:selectOneRadio value="#{demoBean.orderControlsVisible}" >
								<f:selectItem itemValue="#{true}" itemLabel="true" />
								<f:selectItem itemValue="#{false}" itemLabel="false" />
								<a4j:support event="onchange" reRender="orderingList1" />
							</h:selectOneRadio>
							
							<h:outputText value="Fast Order Controls Visible" />
							<h:selectOneRadio value="#{demoBean.fastOrderControlsVisible}" >
								<f:selectItem itemValue="#{true}" itemLabel="true" />
								<f:selectItem itemValue="#{false}" itemLabel="false" />
								<a4j:support event="onchange" reRender="orderingList1" />
							</h:selectOneRadio>
							
							<h:outputText value="ontopclick JavaScript" />
							<h:inputText value="#{demoBean.ontopclick}" >
								<a4j:support event="onchange" reRender="orderingList1" />
							</h:inputText>
							
							<h:outputText value="onbottomclick JavaScript" />
							<h:inputText value="#{demoBean.onbottomclick}" >
								<a4j:support event="onchange" reRender="orderingList1" />
							</h:inputText>
							
						</h:panelGrid>
					</h:panelGroup>
					<h:panelGroup>
						<ol:orderingList value="#{bean.simpleItems}" var="item"
								controlsType="link"
                                topControlLabel="test topControlLabel"
                                topTitle="test topTitle"
                                
                                downControlLabel="test downControlLabel"
                                downTitle="test downTitle"
                                
                                upControlLabel="test upControlLabel"
                                upTitle="test upTitle"
                                
                                bottomControlLabel="test bottomControlLabel"
                                bottomTitle="test bottomTitle"
                                >
							<h:column>
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:outputText value="#{item}" />
							</h:column>
						</ol:orderingList>
					</h:panelGroup>

					<h:panelGroup>
						<ol:orderingList value="#{bean.zebraItems}" var="item" 
								controlsType="link" columnClasses="zebraCell1, zebraCell2" rowClasses="zebraRow1, zebraRow2">
							<rich:column width="10px">
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:outputText value="#{item}" />
							</rich:column>
							<rich:column width="10px">
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:outputText value="#{item}" />
							</rich:column>
							<rich:column width="10px">
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:outputText value="#{item}" />
							</rich:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:outputText value="#{item}" />
							</h:column>
						</ol:orderingList>
					</h:panelGroup>

					<h:panelGroup>
						<ol:orderingList value="#{bean.genericList}" var="var">
							<h:column><h:outputText value="#{var.name}" /></h:column>
							<h:column><h:outputText value="#{var.value}" /></h:column>
						</ol:orderingList>
					</h:panelGroup>
				</h:panelGrid>
				<a4j:commandButton value="Ajax Submit" reRender="orderingList1" />
				<h:commandButton value="Add item" action="#{demoBean.addItem}" />
				<h:commandButton value="Submit" />
				<h:commandButton value="Submit immediate" immediate="true" />

				<f:verbatim>
					<div id="cdiv" style="width: 600px; height: 200px; overflow: auto;"></div>
	
					<script>
						function orderChanged(event) {
							var cdiv = $('cdiv');
							Element.clearChildren(cdiv);
							cdiv.appendChild(document.createTextNode(Object.inspect($H(event))));
						}
					</script>
				</f:verbatim>
				
				<h3>Ordering lists within a4j:repeat</h3>
				<a4j:commandButton reRender="repeat" value="Rerender a4j:repeat" />
				<h:commandButton value="Clear list" action="#{demoBean.clear}"></h:commandButton>
				<h:panelGrid columns="3" >
					<a4j:repeat value="#{bean.lists}" var="list" id="repeat">
						<ol:orderingList value="#{list.items}"  var="item" converter="#{converter}">
							<h:column>
								<f:facet name="header">
									<h:outputText value="Name" />
								</f:facet>
								<h:inputText value="#{item.name}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Price" />
								</f:facet>
								<h:outputText value="#{item.price}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Ajax Action" />
								</f:facet>
								<a4j:commandButton value="Ajax Action" reRender="actionResult"
									action="#{item.action}" />
							</h:column>
							<h:column>
								<f:facet name="header">
									<h:outputText value="Server Action" />
								</f:facet>
								<h:commandLink value="Server Action" action="#{item.action}" />
							</h:column>
						</ol:orderingList>
					</a4j:repeat>
				</h:panelGrid>
			</h:form>
		</f:view>
	</body>	
</html>  
