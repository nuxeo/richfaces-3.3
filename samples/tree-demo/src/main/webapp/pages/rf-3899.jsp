<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="rich"%>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			Try to expand any node. The whole tree should be expanded.

			<h:panelGroup layout="block" id="imageHidden" style="padding: 10px; border: 1px solid navy;">
				<h:outputLink value="#" onclick="Element.show('imageShown'); Element.hide('imageHidden'); return false;">
					Click here to see the wrong variant
				</h:outputLink>
			</h:panelGroup>
			<h:panelGroup layout="block" id="imageShown" style="padding: 10px; border: 1px solid navy; display: none;">
				<h:graphicImage value="../images/rf-3899.png" /><br />
				<h:outputLink value="#" onclick="Element.hide('imageShown'); Element.show('imageHidden'); return false;">
					Hide
				</h:outputLink>
			</h:panelGroup>


			<a4j:outputPanel ajaxRendered="true">
				<h:messages />
			</a4j:outputPanel>

			<h:form>
				<rich:tree value="#{bean.data}" switchType="server" immediate="true" adviseNodeOpened="#{rf3899.adviseNodeOpened}"></rich:tree>
			</h:form>

			<a4j:status startText="...start..." />

			<a4j:log hotkey="O" />

		</f:view>
	</body>
</html>
