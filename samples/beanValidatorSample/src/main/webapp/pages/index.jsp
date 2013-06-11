<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib
	uri="http://labs.jboss.com/jbossrichfaces/ui/beanValidator"
	prefix="v"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<html>
<head>
<title>Hibernate validator test</title>
</head>
<body>
<h1>Using Hibernate validator annotations in the JSF beans</h1>
<f:view>
	<h:form id="form">
	<rich:panel>
	  <f:facet name="header">
		<h:outputText>Single input field with label and message. Validated by AJAX on every char.</h:outputText>
	  </f:facet>
				<h:outputLabel for="ltext" value="#{lengthBean.textDescription}" />
				<h:inputText id="ltext" value="#{lengthBean.text}">
					<v:ajaxValidator event="onkeyup" />
				</h:inputText>
				<rich:message for="ltext" showDetail="true" showSummary="true" />
	</rich:panel>
		<h2>Input fields with label and message in the JSF dataTable. Each field validated by AJAX on "onblur" event</h2>
	    <v:graphValidator value="#{data}" >
		<rich:dataTable value="#{data.beans}" var="bean" id="table">
				<f:facet name="header">
					<h:outputText value="Validate values in the data table. Total sum for an all integer values validated for a value less then 20" />
				</f:facet>

			<h:column>
				<f:facet name="header">
					<h:outputText value="text field" />
				</f:facet>
				<h:outputLabel for="text" value="#{bean.textDescription}" />
				<h:inputText id="text" value="#{bean.text}">
					<v:ajaxValidator event="onblur" />
				</h:inputText>
				<rich:message for="text" showDetail="true" showSummary="true" />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="integer field" />
				</f:facet>
				<h:outputLabel for="intValue" value="#{bean.intDescription}" />
				<h:inputText id="intValue" value="#{bean.intValue}">
					<v:ajaxValidator event="onblur" />
				</h:inputText>
				<rich:message for="intValue" showDetail="true" showSummary="true" />
			</h:column>
			<f:facet name="footer">
				<h:panelGroup>
		<h:outputText>in addition to fields validation, total sum for an all integer values validated for a value less then 20.</h:outputText>
	<h:commandButton value="Submit all fields"></h:commandButton>
				</h:panelGroup>
			</f:facet>
		</rich:dataTable>
		</v:graphValidator>
	</h:form>
	<h:form id="form2">
		<h2>Input fields with label and message in the JSF dataTable.
		Same as above, but fields validated by form submit only.</h2>

		<rich:dataTable value="#{data.beans}" var="bean" id="table2">
			<f:facet name="header">
				<h:outputText
					value="Validate values in the data table." />
			</f:facet>

			<h:column>
				<f:facet name="header">
					<h:outputText value="text field" />
				</f:facet>
				<h:outputLabel for="text" value="#{bean.textDescription}" />
				<h:inputText id="text" value="#{bean.text}">
					<v:beanValidator summary="#{bean.textSummary}"/>
				</h:inputText>
				<rich:message for="text"  />
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="integer field" />
				</f:facet>
				<h:outputLabel for="intValue" value="#{bean.intDescription}" />
				<h:inputText id="intValue" value="#{bean.intValue}">
					<v:beanValidator summary="#{bean.intSummary}"/>
				</h:inputText>
				<rich:message for="intValue"  />
			</h:column>
			<f:facet name="footer">
				<h:panelGroup>
					<h:outputText></h:outputText>
					<h:commandButton value="Submit all fields"></h:commandButton>
				</h:panelGroup>
			</f:facet>
		</rich:dataTable>

	</h:form>

	<rich:messages  />
</f:view>
</body>
</html>
