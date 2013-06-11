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
                <pm:panelMenu>
                    <pm:panelMenuGroup label="group 1">
                        <pm:panelMenuItem label="it 1.1" action="#{menu.item1Clicked}" />
                        <pm:panelMenuItem label="it 1.2" action="#{menu.item1Clicked}" />
                        <pm:panelMenuItem label="it 1.3" action="#{menu.item1Clicked}" />
                    </pm:panelMenuGroup>
                    <pm:panelMenuGroup label="group 2">
                        <pm:panelMenuItem label="it 2.1" action="#{menu.item1Clicked}" />
                        <pm:panelMenuItem label="it 2.2" action="#{menu.item1Clicked}" />
                        <pm:panelMenuItem label="it 2.3" action="#{menu.item1Clicked}" />
                    </pm:panelMenuGroup>
                </pm:panelMenu>
            </h:form>
        </f:view>
    </body> 
</html>  