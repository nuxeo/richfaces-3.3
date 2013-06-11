<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panelbar" prefix="rich" %>
<html>

<head>
    <style type="text/css">
        html, body, form {
            margin: 0;
            padding: 0;
            height: 100%;
            width: 100%;
        }
    </style>
</head>

<body>
<f:view>
    <h:form>
        <h:messages style="color:red"/>
        <h:selectOneRadio binding="#{skinBean.component}"/>
        <h:commandLink action="#{skinBean.change}" value="set skin"/>

        <rich:panelBar id="panelBarId" width="#{bean.width}" height="#{bean.height}">

            <rich:panelBarItem id="item_01" label="#{bean.label}">
                <rich:panelBar>
                    <rich:panelBarItem label="1">
                        <h:commandButton value="knopochka"/>
                    </rich:panelBarItem>
                    <rich:panelBarItem>
                        <f:facet name="label">
                                  <h:outputText value="2" />
                        </f:facet>

                        <h:inputTextarea value="null"></h:inputTextarea>
                    </rich:panelBarItem>
                </rich:panelBar>

            </rich:panelBarItem>

            <rich:panelBarItem id="item_02" label="#{bean.label}">
                <h:outputText value="Width: #{bean.width}"/>
            </rich:panelBarItem>

            <rich:panelBarItem id="item_03" label="#{bean.label}">
                <h:outputText value="Heigth: #{bean.height}"/>
            </rich:panelBarItem>
        </rich:panelBar>

        <h:panelGrid columns="3">
            <h:outputText value="Label:"/>
            <h:inputText value="#{bean.label}"/>
            <h:commandButton value="Change!"/>
        </h:panelGrid>

        <h:panelGroup>
            <h:outputText value="Width:"/>
            <h:selectOneRadio value="#{bean.width}" onclick="submit()">
                <f:selectItem itemLabel="50" itemValue="50px"/>
                <f:selectItem itemLabel="50%" itemValue="50%"/>
                <f:selectItem itemLabel="100%" itemValue="100%"/>
                <f:selectItem itemLabel="100" itemValue="100px"/>
                <f:selectItem itemLabel="200" itemValue="200px"/>
                <f:selectItem itemLabel="300" itemValue="300px"/>
                <f:selectItem itemLabel="500" itemValue="500px"/>
            </h:selectOneRadio>
        </h:panelGroup>
        <h:panelGroup>
            <h:outputText value="Heigth:"/>
            <h:selectOneRadio value="#{bean.height}" onclick="submit()">
                <f:selectItem itemLabel="50" itemValue="50px"/>
                <f:selectItem itemLabel="50%" itemValue="50%"/>
                <f:selectItem itemLabel="100%" itemValue="100%"/>
                <f:selectItem itemLabel="100" itemValue="100px"/>
                <f:selectItem itemLabel="200" itemValue="200px"/>
                <f:selectItem itemLabel="300" itemValue="300px"/>
                <f:selectItem itemLabel="500" itemValue="500px"/>
            </h:selectOneRadio>
        </h:panelGroup>

    </h:form>
</f:view>
</body>
</html>
