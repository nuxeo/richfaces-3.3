<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<f:subview id="panelSubviewID">
<h:panelGrid columnClasses="panel" border="0" columns="3">
    <rich:panel styleClass="top" headerClass="hea" bodyClass="bo">
        <f:facet name="header">
            <h:outputText value="Header of the Panel"/>
        </f:facet>
        <f:verbatim>This panel contains custom headerClass and bodyClass</f:verbatim>
    </rich:panel>
    <rich:panel styleClass="top2" headerClass="hea2" bodyClass="bo2">
        <f:facet name="header">
            <h:outputText value="Header of the Panel"/>
        </f:facet>
        <f:verbatim> This panel also contains custom headerClass and bodyClass. The background is not a 3D anymore.</f:verbatim>
    </rich:panel>

    <h:panelGroup>
        <h:form>
            <rich:panel 
                    onmouseover="document.getElementById(this.id+'_header').style.background='#60BA01';document.getElementById(this.id+'_body').style.background='#F4FFF8'"
                    onmouseout="document.getElementById(this.id+'_header').style.background='#4C9600';document.getElementById(this.id+'_body').style.background='#E4FFC8'"
                    style="width:200px;" headerClass="hea2" bodyClass="bo3">
                <f:facet name="header">
                    <h:outputText value="Header of the Panel"/>
                </f:facet>
                <f:verbatim>Base on the previous layout, but form element and javascript behaviour are added</f:verbatim>
                <br/>
                <h:inputText/>
            </rich:panel>
        </h:form>
    </h:panelGroup>

    <rich:panel style="width:200px;" headerClass="hea" bodyClass="bo3">
        <f:facet name="header">
            <h:outputText value="Scrolling Text Panel"/>
        </f:facet>
        <f:verbatim>
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
            Long Text Long Text Long Text
        </f:verbatim>
    </rich:panel>

    <rich:panel styleClass="top">
        <f:verbatim>This is a panel without the header</f:verbatim>
    </rich:panel>
</h:panelGrid>
<rich:panel style="position: absolute; top: 116px; right: 100px; "
            styleClass="top">
    <f:facet name="header">
        <h:outputText value="Header of the Panel"/>
    </f:facet>
    <f:verbatim>
        This is a panel with absolute position on the screen.<br />
        Resize the browser's window for fun.
    </f:verbatim>
    <h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
</rich:panel>
<h:outputText value="Panel 1, rendered: #{!panel.rendered}; Panel 2, rendered #{panel.rendered};"></h:outputText>

<rich:panel binding="#{panel.htmlPanel}" rendered="#{!panel.rendered}" id="p1" style="width:#{panel.width};height:#{panel.height};overflow:auto;"
            ondblclick="showEvent('ondblclickInputID', 'ondblclick work!')" onkeydown="showEvent('onkeydownInputID', 'onkeydown work!')" onclick="showEvent('onclickInputID', 'onclick work!')" onkeypress="showEvent('onkeypressInputID', 'onkeypress work!')"
            onkeyup="showEvent('onkeyupInputID', 'onkeyup work!')" onmousedown="showEvent('onmousedownInputID', 'onmousedown work!')" onmousemove="showEvent('onmousemoveInputID', 'onmousemove work!')" onmouseout="showEvent('onmouseoutInputID', 'onmouseout work!')"
            onmouseover="showEvent('onmouseoverInputID', 'onmouseover work!')" onmouseup="showEvent('onmouseupInputID', 'onmouseup work!')">
    <f:facet name="header">
        <h:outputText id="t1" value="#{panel.title[0]} (Panel 1)"/>
    </f:facet>

    <rich:panel>
        <f:facet name="header">
            <h:outputText id="t2" value="#{panel.title[1]}"/>
        </f:facet>
        <h:panelGrid columns="2">
            <h:outputText id="o1" value="width #{panel.title[2]}, eg. 250px"></h:outputText>
            <h:inputText value="#{w}">
                <a4j:support event="onchange" reRender="p3"></a4j:support>
            </h:inputText>

            <h:outputText id="o2" value="height #{panel.title[2]}, eg. 200px"></h:outputText>
            <h:inputText value="#{h}">
                <a4j:support event="onchange" reRender="p3"></a4j:support>
            </h:inputText>
        </h:panelGrid>

        <rich:panel id="p3" style="width:#{w};height:#{h};overflow:auto;">
            <f:facet name="header">
                <h:outputText id="t3" value="#{panel.title[2]}"/>
            </f:facet>
            <h:graphicImage value="/pics/asus.jpg" width="150" height="100"></h:graphicImage>

        </rich:panel>
    </rich:panel>
    <h:selectOneMenu value="#{richBean.srcContainer}" onchange="submit();">
        <f:selectItems value="#{richBean.listContainer}"/>
    </h:selectOneMenu>
    <jsp:include flush="true" page="${richBean.pathComponentContainer}"/>
</rich:panel>

<rich:panel id="panelId" rendered="#{panel.rendered}"
            style="width:#{panel.width};height:#{panel.height}"
            onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}">
    <f:verbatim>This is panel 2 example...(Test events)</f:verbatim>
</rich:panel>
</f:subview>

