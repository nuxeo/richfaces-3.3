<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
    <rich:simpleTogglePanel switchType="client">
        <f:facet name="closeMarker">
            <h:outputText value="Close it"/>
        </f:facet>

        <f:facet name="openMarker">
            <h:outputText value="Open it"/>
        </f:facet>
        <h:outputText value="default simpleTogglePanel" />
    </rich:simpleTogglePanel>