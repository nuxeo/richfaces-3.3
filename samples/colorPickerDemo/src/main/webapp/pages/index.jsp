<%@ page contentType="application/xhtml+xml; charset=ISO-8859-1" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="rich" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/colorPicker" prefix="colorPicker"%>
<html>
    <head>
        <title>ColorPicker sample page</title> 
    </head>
    <body>
        <f:view>
            <h:form id="skinForm" >
                <h:selectOneRadio binding="#{skinBean.component}" />
                <h:commandLink action="#{skinBean.change}" value="set skin" />
                <h:outputText value=" Current skin: #{skinBean.skin}" /><br />
            </h:form>
        
            <h:form>
                <rich:dataTable value="1" id="Selenium_Test_DataTable" rowKeyVar="row">
                    <rich:column>
                        <f:facet name="header">
                            <h:outputText value="Data Table" />
                        </f:facet>

                        <colorPicker:colorPicker id="cp" value="#ff0000" />
                        
                        <h:outputText value="#{row}"/>
                    </rich:column>
                </rich:dataTable>
            </h:form>
        </f:view>
    </body>
</html>