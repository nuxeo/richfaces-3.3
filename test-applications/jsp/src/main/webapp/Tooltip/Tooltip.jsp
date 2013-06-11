<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<f:subview id="tooltipSubviewID">
    <h:messages></h:messages>

    <h:inputText value="Text" id="inp1" size="50">
        <rich:toolTip id="toolTipID" value="toolTip for input text"  layout="inline">
            <f:facet name="defaultContent">
                <f:verbatim>DEFAULT VALUE</f:verbatim>
            </f:facet>
        </rich:toolTip>
    </h:inputText>
    <h:panelGrid columns="2">
        <h:outputText value="JavaScript API"></h:outputText>
        <h:column></h:column>
        <a4j:commandLink onclick="$('formID:tooltipSubviewID:toolTipID').component.show(event); return false;" value="doShow"></a4j:commandLink>
        <a4j:commandLink onclick="$('formID:tooltipSubviewID:toolTipID').component.hide(event); return false;" value="doHide"></a4j:commandLink>
        <a4j:commandLink onclick="$('formID:tooltipSubviewID:toolTipID').component.enable(); return false;" value="doEnable"></a4j:commandLink>
        <a4j:commandLink onclick="$('formID:tooltipSubviewID:toolTipID').component.disable(); return false;" value="doDisable"></a4j:commandLink>
    </h:panelGrid>
    <h:selectOneListbox value="1" id="ddl">
        <rich:toolTip value="1231231"  layout="block">
            <f:facet name="defaultContent">
                <f:verbatim>DEFAULT VALUE DropDown</f:verbatim>
            </f:facet>
        </rich:toolTip>
    </h:selectOneListbox>

    <f:verbatim>
        <br/>
        <br/>
    </f:verbatim>

    <h:outputText value=" Test tooltip:"></h:outputText>
    <f:verbatim>
        <br/>
    </f:verbatim>

    <rich:panel style="width:50px; height:50px; background-color: gray">
        <rich:toolTip id="tooltipID" binding="#{tooltip.htmlToolTip}" value="#{tooltip.value}" mode="#{tooltip.mode}" styleClass="#{style.styleClass}" style="#{style.style}" 
                      hideDelay="#{tooltip.hideDelay}" showDelay="#{tooltip.showDelay}" layout="#{tooltip.layout}"
                      horizontalOffset="#{tooltip.horizontalOffset}" verticalOffset="#{tooltip.verticalOffset}" followMouse="#{tooltip.followMouse}"
                      direction="#{tooltip.direction}" disabled="#{tooltip.disabled}" rendered="#{tooltip.rendered}"
					  event="#{tooltip.event}" onclick="#{event.onclick}" oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}" onhide="#{event.onhide}" onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}" onshow="#{event.onshow}">
            <h:graphicImage id="pricsID" value="/pics/ajax_process.gif"></h:graphicImage>
            <h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
        </rich:toolTip>
    </rich:panel>

    <f:verbatim>
        <br/>
    </f:verbatim>
    <h:selectOneMenu value="#{richBean.srcContainer}" onchange="submit();">
        <f:selectItems value="#{richBean.listContainer}"/>
    </h:selectOneMenu>
    <rich:panel style="width:50px; height:50px; background-color: gray">
        <rich:toolTip id="includeToolTIpID" hideDelay="5000" showDelay="0" value="test include">
            <jsp:include flush="true" page="${richBean.pathComponentContainer}"/>
        </rich:toolTip>
    </rich:panel>
</f:subview>
