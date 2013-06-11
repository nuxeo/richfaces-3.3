<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<f:view>
	<html>
	<head>
	<title>Pre Authentication Page</title>
	</head>
	<body>
	<rich:panel>
		<h1>If you see a SEPARATOR under the panel - this is a bug of
		rich:isUserInRole function (applied to the 1th visit on the page)</h1>
		<a href="auth.jsf" style="font-size: xx-large">Log In</a>
		<br />
		There are following users present (all without passwords):
		<ol>
			<li>admin in role admin</li>
			<li>user in role user</li>
			<li>tomcat in role tomcat</li>
			<li>super in roles admin and user</li>
		</ol>
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
	<rich:spacer height="10"></rich:spacer>
	<rich:separator height="200"
		rendered="#{rich:isUserInRole('user, admin')}"></rich:separator>
	</body>
	</html>
</f:view>