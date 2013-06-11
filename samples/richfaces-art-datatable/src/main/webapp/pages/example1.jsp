<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%> 

<html>
	<head>
		<title>RichFaces dataTable Article. Example #1</title>
	</head>
	<body>
		<f:view>
			<h:outputText value="Example #1. Simple Table" />
			
			<rich:dataTable value="#{expenseReport.records}" var="record">
			  	<h:column>
			  	    <f:facet name="header">
			  	    	<h:outputText value="City Name" />
			  	    </f:facet>
			  		<h:outputText value="#{record.city}" />
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    	<h:outputText value="Meals" />
			  	    </f:facet>
			  		<h:outputText value="#{record.totalMeals}">
			  			<f:convertNumber  pattern="$####.00"  />
			  		</h:outputText>
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    	<h:outputText value="Transport" />
			  	    </f:facet>
			  		<h:outputText value="#{record.totalTransport}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    	<h:outputText value="Hotels" />
			  	    </f:facet>
			  		<h:outputText value="#{record.totalHotels}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    	<h:outputText value="Total" />
			  	    </f:facet>
			  		<h:outputText value="#{record.total}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
			</rich:dataTable>
			<h:form>
				<rich:separator lineType="dashed" height="1" style="padding-top:20px" />
				<h:outputText value="Source Code:" />
				<h:outputLink value="http://anonsvn.jboss.org/repos/richfaces/trunk/richfaces-samples/richfaces-art-datatable/src/main/webapp/pages/example1.jsp">
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
