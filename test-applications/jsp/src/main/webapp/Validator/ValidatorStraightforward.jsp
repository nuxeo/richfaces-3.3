<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="validatorStraightforwardSubviewID">
<a4j:region>
	<rich:dataTable value="#{dataValidator.data}" var="dataBean" id="SizeValidationID">		
			showSummary="true" />
		<rich:column>
			<f:facet name="header">
				<h:outputText value="#1" />
			</f:facet>
			<a4j:commandLink value="Select" reRender="SizeValidationID"></a4j:commandLink>
			<f:facet name="footer">
				<h:outputText value="#1" />
			</f:facet>
		</rich:column>

		<rich:column>
			<f:facet name="header">
				<h:outputText value="#4" />
			</f:facet>
			<h:outputText value="#{dataBean.int2}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#4" />
			</f:facet>
		</rich:column>

		<rich:column>
			<f:facet name="header">
				<h:outputText value="#5" />
			</f:facet>
			<h:outputText value="#{dataBean.str1}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#5" />
			</f:facet>
		</rich:column>

		<rich:column>
			<f:facet name="header">
				<h:outputText value="#7" />
			</f:facet>
			<h:graphicImage value="#{dataBean.str2}"></h:graphicImage>
			<h:outputText value="#{dataBean.str2}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="#7" />
			</f:facet>
		</rich:column>

		<rich:column>
			<f:facet name="header">
				<h:outputText value="#9" />
			</f:facet>
			<h:selectBooleanCheckbox value="#{dataBean.bool0}"></h:selectBooleanCheckbox>
			<f:facet name="footer">
				<h:outputText value="#9" />
			</f:facet>
		</rich:column>
	</rich:dataTable>
	</a4j:region>
	<br />
	<a4j:region>
	<h:outputText value="Enter quantity of lines [2-8]" />
	<h:panelGroup>
		<h:inputText value="#{dataValidator.length}"/>
		<a4j:commandButton action="#{dataValidator.addNewItem}" value="ok"
			reRender="SizeValidationID"></a4j:commandButton>
	</h:panelGroup>
	</a4j:region>
	<br/>
	<a4j:region>
	<h:panelGrid columns="2">
		
	<h:inputText value="#{validator.newValue}" id="beanValidatorInputID">
	<rich:beanValidator></rich:beanValidator>	
	</h:inputText>
	
	<a4j:commandButton value="reRender"	onclick="submit();" reRender="beanValidatorInputID"></a4j:commandButton>
	</h:panelGrid>
	</a4j:region>
</f:subview>