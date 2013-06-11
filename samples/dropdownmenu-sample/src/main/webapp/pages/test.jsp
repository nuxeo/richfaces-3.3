<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dropdown-menu" prefix="ddm" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/menu-components" prefix="mc" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>RF-921 Test Page </title>
		<style>
			.layoutTable {
				width: 800px;
				margin-left: 100px;
				margin-top: 10px;
			}
		</style>
	</head>
	<body>
		<f:view>
			<table class="layoutTable">
				<tr>
					<td>
						<h:form>
							<ddm:dropDownMenu value="File" event="onmouseover">
							
								<mc:menuItem submitMode="ajax" value="New"/>
								<mc:menuItem submitMode="ajax" value="Open"/>
								
								<mc:menuGroup value="Save As...">
									<mc:menuItem submitMode="ajax" value="Text File"/>
									<mc:menuItem submitMode="ajax" value="PDF File"/>
								</mc:menuGroup>
			
								<mc:menuItem submitMode="ajax" value="Close"/>
								
								<mc:menuItem submitMode="none">
									<h:outputLink value="www.jboss.org">
										<h:outputText value="jboss"/>
									</h:outputLink>
								</mc:menuItem>
								
								<mc:menuSeparator id="menuSeparator11" />
								<mc:menuItem submitMode="ajax" value="Exit"/>
								
							</ddm:dropDownMenu>
						</h:form>
					</td>
				</tr>
			</table>
		</f:view>
	</body>
</html>