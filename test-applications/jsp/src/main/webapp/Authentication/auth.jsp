<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<f:view>
	<html>
	<head>
	<title>Authentication Page</title>
	</head>
	<body>
	<rich:panel rendered="#{rich:isUserInRole('admin')}">
		<f:facet name="header">
			<h:outputText value="ADMIN panel" />
		</f:facet>
		<h:panelGrid columns="3">
			<h:panelGroup>
				<h:outputText value="ADMIN" rendered="#{rich:isUserInRole('admin')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('admin')}"
					value="ADMIN" onclick="return false;" />
				<br />
				<rich:inplaceInput value="ADMIN"
					rendered="#{rich:isUserInRole('admin')}" />
			</h:panelGroup>
			<h:panelGroup>
				<h:outputText value="USER" rendered="#{rich:isUserInRole('user')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('user')}"
					value="USER" onclick="return false;" />
				<br />
				<rich:inplaceInput value="USER"
					rendered="#{rich:isUserInRole('user')}" />
			</h:panelGroup>
			<h:panelGroup>
				<h:outputText value="USER\ADMIN"
					rendered="#{rich:isUserInRole('admin,user')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('admin,user')}"
					value="ADMIN\USER" onclick="return false;" />
				<br />
				<rich:inplaceInput value="ADMIN\USER"
					rendered="#{rich:isUserInRole('admin,user')}" />
			</h:panelGroup>
		</h:panelGrid>
	</rich:panel>
	<rich:panel rendered="#{rich:isUserInRole('user')}">
		<f:facet name="header">
			<h:outputText value="USER panel" />
		</f:facet>
		<h:panelGrid columns="3">
			<h:panelGroup>
				<h:outputText value="ADMIN" rendered="#{rich:isUserInRole('admin')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('admin')}"
					value="ADMIN" onclick="return false;" />
				<br />
				<rich:inplaceInput value="ADMIN"
					rendered="#{rich:isUserInRole('admin')}" />
			</h:panelGroup>
			<h:panelGroup>
				<h:outputText value="USER" rendered="#{rich:isUserInRole('user')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('user')}"
					value="USER" onclick="return false;" />
				<br />
				<rich:inplaceInput value="USER"
					rendered="#{rich:isUserInRole('user')}" />
			</h:panelGroup>
			<h:panelGroup>
				<h:outputText value="USER\ADMIN"
					rendered="#{rich:isUserInRole('admin,user')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('admin,user')}"
					value="ADMIN\USER" onclick="return false;" />
				<br />
				<rich:inplaceInput value="ADMIN\USER"
					rendered="#{rich:isUserInRole('admin,user')}" />
			</h:panelGroup>
		</h:panelGrid>
	</rich:panel>
	<rich:panel rendered="#{rich:isUserInRole('admin,user')}">
		<f:facet name="header">
			<h:outputText value="ADMIN\USER panel" />
		</f:facet>
		<h:panelGrid columns="3">
			<h:panelGroup>
				<h:outputText value="ADMIN" rendered="#{rich:isUserInRole('admin')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('admin')}"
					value="ADMIN" onclick="return false;" />
				<br />
				<rich:inplaceInput value="ADMIN"
					rendered="#{rich:isUserInRole('admin')}" />
			</h:panelGroup>
			<h:panelGroup>
				<h:outputText value="USER" rendered="#{rich:isUserInRole('user')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('user')}"
					value="USER" onclick="return false;" />
				<br />
				<rich:inplaceInput value="USER"
					rendered="#{rich:isUserInRole('user')}" />
			</h:panelGroup>
			<h:panelGroup>
				<h:outputText value="USER\ADMIN"
					rendered="#{rich:isUserInRole('admin,user')}" />
				<br />
				<a4j:commandButton rendered="#{rich:isUserInRole('admin,user')}"
					value="ADMIN\USER" onclick="return false;" />
				<br />
				<rich:inplaceInput value="ADMIN\USER"
					rendered="#{rich:isUserInRole('admin,user')}" />
			</h:panelGroup>
		</h:panelGrid>
	</rich:panel>
	<rich:panel rendered="#{!rich:isUserInRole('admin,user')}">
		<h1>This user coud not view anything before this text :(</h1>
	</rich:panel>
	<rich:panel>
		<br />
		<h:form>
			<h:commandButton
				onclick="alert(#{rich:isUserInRole('admin')}); return false;"
				value="is User In Role Admin" />
			<h:commandButton
				onclick="alert(#{rich:isUserInRole('user')}); return false;"
				value="is User In Role User" />
			<h:commandButton
				onclick="alert(#{rich:isUserInRole('tomcat')}); return false;"
				value="is User In Role Tomcat" />
		</h:form>
	</rich:panel>
	</body>
	</html>
</f:view>