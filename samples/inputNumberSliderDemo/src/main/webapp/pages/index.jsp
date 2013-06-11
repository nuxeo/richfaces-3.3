<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/inputnumber-slider"
           prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<html>
<head>
    <title></title>
    <script type="text/javascript">
        function doEvent() {
            alert("Event");
        }
    </script>
</head>

<body>
<f:view>
    <h:form>
        <h:selectOneRadio binding="#{skinBean.component}"/>
        <h:commandLink action="#{skinBean.change}" value="set skin"/>

        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>
        <rich:inputNumberSlider value="#{bean.value}" onclick="doEvent()"/>

        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>
        <rich:inputNumberSlider value="20.1" minValue="19.1" maxValue="21.1"
                                step="1.0"/>

        <f:verbatim>
            <br/>
            <br/>
            Here is an example of default inputNumberSlider:
        </f:verbatim>
        <rich:inputNumberSlider value="50"/>

        <f:verbatim>
            <br/>
            <br/>
            Here is "minimalistic" inputNumberSlider:
        </f:verbatim>
        <rich:inputNumberSlider value="50" showInput="false"
                                enableManualInput="false"
                                showBoundaryValues="false" showToolTip="false"/>

        <f:verbatim>
            <br/>
            <br/>
            Another variation of input:
        </f:verbatim>
        <rich:inputNumberSlider value="10" minValue="0" maxValue="100" step="5"
                                showToolTip="true"/>

        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>

        <rich:inputNumberSlider showInput="true" showBoundaryValues="true"
                                showToolTip="true"
                                value="2.5" step="0.5" minValue="0.0"
                                maxValue="10.0" inputSize="30"/>

        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>

        <rich:inputNumberSlider showInput="true" showBoundaryValues="true"
                                showToolTip="true"
                                value="2.5" step="0.5" minValue="0"
                                maxValue="10"/>

        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>

        <rich:inputNumberSlider id="SliderId" inputPosition="left" showInput="#{bean.showInput}" showBoundaryValues="#{bean.showBoundaryValues}"
                                showToolTip="true" disabled="#{bean.disabled}" enableManualInput = "#{bean.manualInput}"
                                value="1.4" step="0.1" minValue="1.2"
                                maxValue="2.2"/>
        
        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>
        
        <rich:inputNumberSlider inputPosition="left" showInput="#{bean.showInput}" showBoundaryValues="#{bean.showBoundaryValues}"
                                showToolTip="true" disabled="#{bean.disabled}"
                                value="0" step="1" minValue="-999"
                                maxValue="10"/>
        <f:verbatim>
            <br/>
            <br/>
        </f:verbatim>

        <h:panelGrid columns="2" cellspacing="10px" border="1">
            <h:outputText value="Disabled:"></h:outputText>
            <h:selectBooleanCheckbox value="#{bean.disabled}">
                <a4j:support event="onchange" reRender="SliderId"></a4j:support>
            </h:selectBooleanCheckbox>

            <h:outputText value="Boundary Values:"></h:outputText>
            <h:selectBooleanCheckbox
                    value="#{bean.showBoundaryValues}">
                <a4j:support event="onchange" reRender="SliderId"></a4j:support>
            </h:selectBooleanCheckbox>

            <h:outputText value="Show Input:"></h:outputText>
            <h:selectBooleanCheckbox value="#{bean.showInput}">
                <a4j:support event="onchange" reRender="SliderId"></a4j:support>
            </h:selectBooleanCheckbox>
            <h:outputText value="Enable Manual Input:"></h:outputText>
            <h:selectBooleanCheckbox value="#{bean.manualInput}">
                <a4j:support event="onchange" reRender="SliderId"></a4j:support>
            </h:selectBooleanCheckbox>
        </h:panelGrid>
        <br/>

        <h:commandButton value="Value: #{bean.value}"/>
    </h:form>
</f:view>
</body>
</html>
