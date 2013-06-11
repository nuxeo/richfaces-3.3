<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%> 

<html>
	<head>
		<title>RichFaces dataTable Article. Example #2</title>
	</head>
	<body>
		<f:view>
			
			
			<rich:dataTable width="400" value="#{expenseReport.records}" var="record">
				<f:facet name="caption">
					<h:outputText value="Example #2. Using rich:column and rich:columnGroup" />
				</f:facet>
				<f:facet name="header">
					<rich:columnGroup>
						<rich:column rowspan="2">
							<h:outputText value="City Name" />
						</rich:column>

						<rich:column colspan="3">
							<h:outputText value="Expenses" />
						</rich:column>
					
						<rich:column rowspan="2">
							<h:outputText value="Total" />
						</rich:column>
					
						<rich:column breakBefore="true">
							<h:outputText value="Meals" />
						</rich:column>

						<rich:column>
							<h:outputText value="Transport" />
						</rich:column>
					
						<rich:column>
							<h:outputText value="Hotels" />
						</rich:column>
					
					</rich:columnGroup>
				</f:facet>
				
				<f:facet name="footer">
					<rich:columnGroup>
						<rich:column><h:outputText value="Grand Total" /></rich:column>
						<rich:column>
							<h:outputText value="#{expenseReport.totalMeals}" >
					  			<f:convertNumber  pattern="$####.00"  />
							</h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="#{expenseReport.totalTransport}" >
					  			<f:convertNumber  pattern="$####.00"  />
							</h:outputText>
						</rich:column>
						<rich:column>
							<h:outputText value="#{expenseReport.totalHotels}" >
					  			<f:convertNumber  pattern="$####.00"  />
							</h:outputText>
						</rich:column>
						<rich:column><h:outputText value="#{expenseReport.grandTotal}" /></rich:column>
					</rich:columnGroup>
				</f:facet>
				
			  	<h:column>
			  		<h:outputText value="#{record.city}" />
				</h:column>
				<h:column>
			  		<h:outputText value="#{record.totalMeals}">
			  			<f:convertNumber  pattern="$####.00"  />
			  		</h:outputText>
				</h:column>
				<h:column>
			  		<h:outputText value="#{record.totalTransport}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<h:column>
			  		<h:outputText value="#{record.totalHotels}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<h:column>
			  		<h:outputText value="#{record.total}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
			</rich:dataTable>
			
			<h:form>
				<h:outputText value="Change Skin:" />
				<h:commandLink action="changeSkin" value="Classic">
					<a4j:actionparam name="skin" value="classic" assignTo="#{skinBean.name}" />
				</h:commandLink>
				<h:commandLink action="changeSkin" value="BlueSky">
					<a4j:actionparam name="skin" value="blueSky" assignTo="#{skinBean.name}" />
				</h:commandLink>
				<h:commandLink action="changeSkin" value="DeepMarine">
					<a4j:actionparam name="skin" value="deepMarine" assignTo="#{skinBean.name}" />
				</h:commandLink>
				<h:commandLink action="changeSkin" value="EmeraldTown">
					<a4j:actionparam name="skin" value="emeraldTown" assignTo="#{skinBean.name}" />
				</h:commandLink>
				<h:commandLink action="changeSkin" value="Wine">
					<a4j:actionparam name="skin" value="wine" assignTo="#{skinBean.name}" />
				</h:commandLink>
			</h:form>

			<h:form>
				<rich:separator lineType="dashed" height="1" style="padding-top:20px" />
				<h:outputText value="Source Code:" />
				<h:outputLink value="http://anonsvn.jboss.org/repos/richfaces/trunk/richfaces-samples/richfaces-art-datatable/src/main/webapp/pages/example2.jsp">
					<h:outputText value="Page" />
				</h:outputLink>				
				<h:outputLink value="http://anonsvn.jboss.org/repos/richfaces/trunk/richfaces-samples/richfaces-art-datatable/src/main/">
					<h:outputText value="Project" />
				</h:outputLink>				
				<rich:separator lineType="dashed" height="1" style="padding-top:20px" />
				<h:commandLink value="back to example list" action="main"></h:commandLink>
			</h:form>
			
		</f:view>
	</body>	
</html>  
