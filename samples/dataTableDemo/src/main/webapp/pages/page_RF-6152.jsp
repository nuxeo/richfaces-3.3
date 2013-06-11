<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="data" %>
<html>
    <head>
        <title></title>
    </head>
    <body>
        <f:view >
            <a4j:form>
                <data:dataTable value="#{data.mounths}">
                    <data:column id="row1">
                        <f:facet name="header">
                            <h:outputText value="A"/>
                        </f:facet>
                        <h:outputText value="1"></h:outputText>
                    </data:column>
                    <data:column id="row3">
                        <f:facet name="header">
                            <h:outputText value="B"/>
                        </f:facet>
                        <h:outputText value="2"></h:outputText>
                    </data:column>
                    <data:columnGroup>
                        <data:column id="row2" colspan="2">
                            <h:outputText value="3"></h:outputText>
                        </data:column>
                    </data:columnGroup>
                </data:dataTable>
            </a4j:form>
        </f:view>
    </body> 
</html>  
