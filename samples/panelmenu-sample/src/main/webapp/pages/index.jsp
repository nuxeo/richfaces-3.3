<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panelmenu" prefix="pm"%>

<html>
	<head>
	<style type="text/css">
	</style>	
	</head>
	<body>
		<f:view>
			<h:form>
				<h:selectOneRadio binding="#{skinBean.component}" />
				<h:commandLink action="#{skinBean.change}" value="set skin" />
	
				<pm:panelMenu selectedChild="thisChild">
						<pm:panelMenuGroup label="Group 1" iconExpanded="disc" iconCollapsed="chevron">
							<pm:panelMenuItem label="Item 1" icon="chevronUp" name="thisChild">
							</pm:panelMenuItem>
							<pm:panelMenuItem label="Item" mode="server" action="ddd"/>
							<pm:panelMenuGroup label="Group 2" expandMode="server" action="ddwd">
								<pm:panelMenuItem icon="triangleDown">
									<f:verbatim>Item 3</f:verbatim>
								</pm:panelMenuItem>
								<pm:panelMenuGroup label="Group 33" expanded="true">
									<pm:panelMenuItem icon="triangleDown">
										<f:verbatim>Item 3333</f:verbatim>
									</pm:panelMenuItem>
								</pm:panelMenuGroup>
							</pm:panelMenuGroup>
						</pm:panelMenuGroup>
						<pm:panelMenuGroup label="Group 3">
							<pm:panelMenuItem label="Item 4" >
							</pm:panelMenuItem>
						</pm:panelMenuGroup>
						<pm:panelMenuGroup label="Group 4" disabled="true">
							<pm:panelMenuItem label="Item 5" disabled="true"/>
							<pm:panelMenuItem label="Item 66" mode="server" />
						</pm:panelMenuGroup>
						<pm:panelMenuItem label="Top item"/>
						<pm:panelMenuItem label="Top item2" disabled="true"/>
					</pm:panelMenu>
			</h:form>			
		</f:view>
		
		
		
	</body>	
</html>  
