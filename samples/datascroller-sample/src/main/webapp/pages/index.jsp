<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/datascroller" prefix="ds"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data Table test</title>
</head>
<body>
	<f:view>
        <h:form>
        
        	<a4j:outputPanel ajaxRendered="true">
        		<h:messages />
        	</a4j:outputPanel>

    <h:selectOneRadio binding="#{skinBean.component}" />
    <h:commandLink action="#{skinBean.change}" value="set skin" />
    <br/><br/>



		<h:outputText value="Data Table test" />
		<br />

		<ds:datascroller onpagechange="return confirm('Do you want to go to: ' + event.memo.page + '?')" page="2" for="master" reRender="actionCount, eventCount" rendered="true" fastStep="2" actionListener="#{testBean.onAction}" renderIfSinglePage="#{testBean.renderIfSinglePage}" scrollerListener="#{testBean.doScroll}" maxPages="#{testBean.maxpage}"/>

		<h:dataTable  rows="#{testBean.rows}" id="master" border="1" value="#{testBean.data}" var="data">
			<f:facet name="header">
					<h:outputText value="My table" />
			</f:facet>
			<h:column>
				<f:facet name="header">
					<a4j:commandLink value="Number 1" reRender="master" actionListener="#{testBean.doSort}"/>
				</f:facet>
				<h:outputText value="#{data.number1}" />
				<f:facet name="footer">
					<h:outputText value="Number 1 end" />
				</f:facet>
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="Number 2" />
				</f:facet>
				<h:outputText value="#{data.number2}" />
				<f:facet name="footer">
					<h:outputText value="Number 2 end" />
				</f:facet>
			</h:column>
			<h:column>
				<f:facet name="header">
					<h:outputText value="Number 3" />
				</f:facet>
				<h:outputText value="#{data.number3}" />
				<f:facet name="footer">
					<h:outputText value="Number 3 end" />
				</f:facet>
			</h:column>
			<f:facet name="footer">
			</f:facet>
		</h:dataTable>

        <br />
        <h:outputText value="Actions:" style="font-weight: bold;" />
        <h:outputText id="actionCount" value="#{testBean.actionCount}" />
        <br />
        <h:outputText value="Events:" style="font-weight: bold;" />
        <h:outputText id="eventCount" value="#{testBean.eventCount}" />
        <br />


                <h:panelGrid columns="2" border="0">

				        <h:outputText value="render if single page" style="font-weight: bold;" />
                                        <h:inputText value="#{testBean.renderIfSinglePage}"/>

					<h:outputText value="Rows" style="font-weight: bold;" />
	                                <h:inputText value="#{testBean.rows}"/>

					<h:outputText value="Max page" style="font-weight: bold;" />
	                                <h:inputText value="#{testBean.maxpage}"/>


				</h:panelGrid>

				<h:commandLink value="apply" />
				<br />
				<h:commandLink value="Add Entries" action="#{testBean.addEntries}" />
				<br />
				<h:commandLink value="Remove Entries" action="#{testBean.removeEntries}" />
				<br />


        </h:form>
        
        <h:form id="form">
        	Datascroller controlled via JS API:

        	<h:dataTable id="jsAPIDt" rows="5" border="1" value="#{testBean.jsAPIScrollerData}" var="data">
				<h:column>
					<h:outputText value="#{data}" />
				</h:column>
        	</h:dataTable>
        	
        	<ds:datascroller fastStep="2" for="jsAPIDt" id="jsAPIDs"/>
        	
        	<h:panelGrid columns="1">
        		<h:outputLink onclick="$('form:jsAPIDs').component.first(); return false;">first</h:outputLink>
        		<h:outputLink onclick="$('form:jsAPIDs').component.previous(); return false;">previous</h:outputLink>
        		<h:outputLink onclick="$('form:jsAPIDs').component.fastRewind(); return false;">fastRewind</h:outputLink>
        		<h:outputLink onclick="$('form:jsAPIDs').component.switchToPage(2); return false;">switchToPage(2)</h:outputLink>
        		<h:outputLink onclick="$('form:jsAPIDs').component.fastForward(); return false;">fastForward</h:outputLink>
        		<h:outputLink onclick="$('form:jsAPIDs').component.next(); return false;">next</h:outputLink>
        		<h:outputLink onclick="$('form:jsAPIDs').component.last(); return false;">last</h:outputLink>
        	</h:panelGrid>
        	
        </h:form>
	</f:view>
</body>
</html>
