<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/suggestionbox"
           prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<html>
<head>
    <title></title>
</head>

<body>
<f:view>

<a4j:outputPanel ajaxRendered="true">
	<h:messages />
</a4j:outputPanel>

<h:form id="suggestionbox_form">
<h:selectOneRadio binding="#{skinBean.component}" />
<h:commandLink action="#{skinBean.change}" value="set skin" />

<br/>
<br/>

<h:outputText
        value="Simple richfaces Suggestion Box test web application."
        style="font: 18px;font-weight: bold;"/>
<br/>
<br/>

<h:panelGrid columns="4" border="0" style="border: 1px solid black;">
    <f:verbatim>Suggestion Box will suggest you Town's names if it's started with the "a" or
        "A" letter</f:verbatim>

    <f:facet name="header">
        <f:verbatim>Input With Suggestion Feature</f:verbatim>
    </f:facet>


    <f:verbatim>&#160;</f:verbatim>
    <h:panelGroup>
	    <h:inputText value="#{suggestionBox.property}" id="text"/>
	    <rich:suggestionbox id="suggestionBoxId" for="text" tokens=","
	                        rules="#{suggestionBox.rules}"
	                        suggestionAction="#{suggestionBox.autocomplete}" var="result"
	                        fetchValue="#{result.text}"
	                        first="#{suggestionBox.intFirst}"
	                        minChars="0"
	                        shadowOpacity="#{suggestionBox.shadowOpacity}"
	                        border="#{suggestionBox.border}"
	                        width="#{suggestionBox.width}"
	                        height="#{suggestionBox.height}"
	                        shadowDepth="#{suggestionBox.shadowDepth}"
	                        cellpadding="#{suggestionBox.cellpadding}"
	                        usingSuggestObjects="#{suggestionBox.usingSuggestObjects}">
	
	            <a4j:ajaxListener type="org.richfaces.AjaxListener"/>
	        <h:column>
	            <h:outputText value="#{result.text}"/>
	        </h:column>
	        <h:column>
	            <h:outputText value="#{result.price}">
	                <f:convertNumber pattern="#,##.00" type="currency"/>
	            </h:outputText>
	        </h:column>
	    </rich:suggestionbox>
    </h:panelGroup>

    <h:inputText value="#{suggestionBox.property}" id="text1">
	    <rich:suggestionbox id="suggestionBoxId1" tokens=",["
	                        rules="#{suggestionBox.rules}"
	                        suggestionAction="#{suggestionBox.autocomplete}" var="result"
	                        fetchValue="#{result.text}"
	                        first="#{suggestionBox.intFirst}"
	                        minChars="#{suggestionBox.minchars}"
	                        shadowOpacity="#{suggestionBox.shadowOpacity}"
	                        border="#{suggestionBox.border}"
	                        width="#{suggestionBox.width}"
	                        height="#{suggestionBox.height}"
	                        shadowDepth="#{suggestionBox.shadowDepth}"
	                        cellpadding="#{suggestionBox.cellpadding}"
	                        requestDelay="2000"
	                        nothingLabel="nothing"
	                        usingSuggestObjects="#{suggestionBox.usingSuggestObjects}">
	
	            <a4j:ajaxListener type="org.richfaces.AjaxListener"/>
	        <h:column>
	            <h:outputText value="#{result.text}"/>
	        </h:column>
	        <h:column>
	            <h:outputText value="#{result.price}">
	                <f:convertNumber pattern="#,##.00" type="currency"/>
	            </h:outputText>
	        </h:column>
	    </rich:suggestionbox>
    </h:inputText>
</h:panelGrid>

<br/>
<br/>

<h:panelGrid columns="3" border="0" style="border: 1px solid black;">

    <f:verbatim>Border</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:selectOneRadio value="#{suggestionBox.border}" onchange="submit()">
        <f:selectItem itemLabel="1" itemValue="1"/>
        <f:selectItem itemLabel="2" itemValue="2"/>
        <f:selectItem itemLabel="3" itemValue="3"/>
    </h:selectOneRadio>

    <f:verbatim>Width</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:selectOneRadio value="#{suggestionBox.width}" onchange="submit()">
        <f:selectItem itemLabel="150" itemValue="150"/>
        <f:selectItem itemLabel="200" itemValue="200"/>
        <f:selectItem itemLabel="250" itemValue="250"/>
        <f:selectItem itemLabel="300" itemValue="300"/>
        <f:selectItem itemLabel="350" itemValue="350"/>
    </h:selectOneRadio>

    <f:verbatim>Height</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:selectOneRadio value="#{suggestionBox.height}" onchange="submit()">
        <f:selectItem itemLabel="100" itemValue="100"/>
        <f:selectItem itemLabel="150" itemValue="150"/>
        <f:selectItem itemLabel="200" itemValue="200"/>
        <f:selectItem itemLabel="250" itemValue="250"/>
        <f:selectItem itemLabel="300" itemValue="300"/>
    </h:selectOneRadio>

    <f:verbatim>Shadow Depth</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:selectOneRadio value="#{suggestionBox.shadowDepth}" onchange="submit()">
        <f:selectItem itemLabel="3" itemValue="3"/>
        <f:selectItem itemLabel="4" itemValue="4"/>
        <f:selectItem itemLabel="5" itemValue="5"/>
        <f:selectItem itemLabel="6" itemValue="6"/>
    </h:selectOneRadio>

    <f:verbatim>Shadow Opacity</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:selectOneRadio value="#{suggestionBox.shadowOpacity}" onchange="submit()">
        <f:selectItem itemLabel="1" itemValue="1"/>
        <f:selectItem itemLabel="2" itemValue="2"/>
        <f:selectItem itemLabel="3" itemValue="3"/>
        <f:selectItem itemLabel="4" itemValue="4"/>
        <f:selectItem itemLabel="5" itemValue="5"/>
        <f:selectItem itemLabel="6" itemValue="6"/>
        <f:selectItem itemLabel="7" itemValue="7"/>
        <f:selectItem itemLabel="8" itemValue="8"/>
        <f:selectItem itemLabel="9" itemValue="9"/>
    </h:selectOneRadio>

    <f:verbatim>Cellpadding</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:inputText value="#{suggestionBox.cellpadding}" onchange="submit()" />

    <f:verbatim>First Value</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:inputText value="#{suggestionBox.first}" onchange="submit()"/>

    <f:verbatim>Onsubmit function will return</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
    <h:selectOneRadio value="#{suggestionBox.onsubmit}" onchange="submit()">
        <f:selectItem itemLabel="true" itemValue="true"/>
        <f:selectItem itemLabel="false" itemValue="false"/>
        <f:selectItem itemLabel="no function" itemValue="none"/>
    </h:selectOneRadio>

	<f:verbatim>Using Suggestion Objects</f:verbatim>
    <f:verbatim>&#160;</f:verbatim>
	<h:selectBooleanCheckbox value="#{suggestionBox.usingSuggestObjects}" onchange="submit()" />

</h:panelGrid>
</h:form>
</f:view>
</body>
</html>
