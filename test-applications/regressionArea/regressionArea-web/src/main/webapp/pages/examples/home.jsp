<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Resression Area : Seam Application</title>
</head>
<body id="pgHome">
<f:view>
    <h:form id="_form">
    	<rich:panel header="Rich Users">
    	<rich:dataTable id="tb" value="#{usersBean.users}" var="us">
    		<rich:column>
    			<f:facet name="header">
    				<h:outputText value="Name"></h:outputText>
    			</f:facet>
    			<h:outputText value="#{us.name}"></h:outputText>
    		</rich:column>
    	</rich:dataTable>
    	</rich:panel>
    </h:form>
</f:view>
</body>
</html>
