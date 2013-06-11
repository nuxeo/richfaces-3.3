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
                <h2>3</h2>
                <pm:panelMenu selectedChild="one" id="pb3" mode="ajax">
                    <pm:panelMenuGroup expanded="true">
                        <pm:panelMenuItem id="one" label="one"/>
                        <pm:panelMenuItem id="two" label="two"/>
                    </pm:panelMenuGroup>
                </pm:panelMenu>
                <div>
                    In this case if the User.name="one" by default - no selection will happens at all from the beggining because item in collapsed group.
                </div>
            </h:form>
        </f:view>
    </body> 
</html>  