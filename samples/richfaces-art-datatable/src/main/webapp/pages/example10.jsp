<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%> 

<html>
	<head>
		<title>RichFaces dataTable Article. Example #4</title>
		<style>

		</style>
	</head>
	<body>
		
		<f:view>
		
		<h:form id="form">
			<rich:dataTable value="#{salesReport.items}" var="item">
				<f:facet name="header">
					<rich:columnGroup>
						<h:column>
							<h:outputText value="Product Code" />
						</h:column>
						<h:column>
							<h:outputText value="Proposed Price" />
						</h:column>
						<h:column>
							<h:outputText value="Sales Cost" />
						</h:column>
						<rich:column style="width:150px">
							<h:outputText value="Reason" />
						</rich:column>
						<h:column>
							<h:outputText value="Proposed Gross Margin" />
						</h:column>
					</rich:columnGroup>
				</f:facet>
				<h:column>
					<h:outputText value="#{item.productCode}" />
				</h:column>
				<h:column>
					<h:inputText binding="#{updateBean.priceRef}" immediate="false" value="#{item.proposedPrice}" size="7">
						<a4j:support immediate="true" action="#{updateBean.change}" event="onchange" reRender="margin,reason" />
					</h:inputText>
				</h:column>
				<h:column>
					<h:outputText value="#{item.salesCost}" />
				</h:column>
				<h:column>
					<h:selectOneMenu id="reason" required="true" value="#{item.reason}">
						<f:selectItems value="#{item.reasons}" />
					</h:selectOneMenu>
				</h:column>
				<h:column>
					<h:outputText id="margin" value="#{item.proposedGrossMargin}">
						<f:convertNumber  pattern="$###0.000"  />
					</h:outputText>
				</h:column>
			</rich:dataTable>
			<rich:messages />
		</h:form>
		</f:view>
	</body>	
</html>  
