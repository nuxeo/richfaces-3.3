<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<html>
	<head>
		<title>Tomahawk extended data table</title>
	</head>
	<body>
		<f:view>
		<h:form>
<t:dataTable id="containers" value="#{bean.containers}"
					var="GIContainers" styleClass="normalText" border="1"
					cellspacing="0" cellpadding="0" rowIndexVar="rowIndex">
					<t:column>
						<f:facet name="header">?????</f:facet>

						<h:inputText id="container"
							styleClass="#{(GIContainers.available) ? ((GIContainers.changed) ? 'redText' : '') : 'strikeText'}"
							size="5" maxlength="17" value="#{GIContainers.containerNumber}">
							<a4j:support event="onchange" ajaxSingle="true"
								reRender="containers"
								actionListener="#{controlResultsController.updateValue}">
								<a4j:actionparam name="rowIndex" value="#{rowIndex}" />
								<a4j:actionparam name="changeTable" value="containers" />
							</a4j:support>
							<f:validateLength maximum="17" />
						</h:inputText>
					</t:column>
					<t:column>
						<f:facet name="header">??? ?? ?????????</f:facet>

						<h:inputText size="5" maxlength="17"
							styleClass="#{(GIContainers.available) ? ((GIContainers.changed) ? 'redText' : '') : 'strikeText'}"
							value="#{GIContainers.containerNumber}">
							<f:validateLength maximum="17" />
						</h:inputText>
					</t:column>
					<t:column>
						<f:facet name="header">
							<a4j:commandLink immediate="true"
								action="#{controlResultsController.addRow}"
								reRender="containers">
								<h:graphicImage style="border: none" value="/images/plus.gif" />
								<a4j:actionparam name="plusTable" value="containers"></a4j:actionparam>
							</a4j:commandLink>
						</f:facet>

						<a4j:commandLink reRender="containers" immediate="true"
							style="text-decoration : none"
							action="#{controlResultsController.removeElement}">
							<h:graphicImage style="border: none;" value="/images/minus.gif" />
							<a4j:actionparam name="rowIndex" value="#{rowIndex}"></a4j:actionparam>
							<a4j:actionparam name="minusTable" value="containers"></a4j:actionparam>
						</a4j:commandLink>
					</t:column>
				</t:dataTable>
		</h:form>			
		</f:view>
	</body>	
</html>  
