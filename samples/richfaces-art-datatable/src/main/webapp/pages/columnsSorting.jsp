<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<html>
<head>
	<title>RichFaces dataTable Article. Example #6. Sorting Rows.</title>
</head>
<body>
	<f:view>
			<h:outputText value="RichFaces dataTable Article. Example #6. Sorting Rows."/>
			<h:form>
			<rich:dataTable value="#{expenseReport.records}" var="record" id="table" rows="2">
			  	<h:column>
			  	    <f:facet name="header">
			  	    		<a4j:commandLink value="City Name" action="#{expenseReport.sortCityNames}" reRender="table" ajaxSingle="true"/>
			  	    </f:facet>
			  		<h:outputText value="#{record.city}" />
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    		<a4j:commandLink value="Meals" action="#{expenseReport.sortMeals}" reRender="table" ajaxSingle="true"/>
			  	    </f:facet>
			  		<h:outputText value="#{record.totalMeals}">
			  			<f:convertNumber  pattern="$####.00"  />
			  		</h:outputText>
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    		<a4j:commandLink value="Transport" action="#{expenseReport.sortTransport}" reRender="table" ajaxSingle="true"/>
			  	    </f:facet>
			  		<h:outputText value="#{record.totalTransport}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    		<a4j:commandLink value="Hotels" action="#{expenseReport.sortHotels}" reRender="table" ajaxSingle="true"/>
			  	    </f:facet>
			  		<h:outputText value="#{record.totalHotels}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<h:column>
			  	    <f:facet name="header">
			  	    		<a4j:commandLink value="Total" action="#{expenseReport.sortTotal}" reRender="table" ajaxSingle="true"/>
			  	    </f:facet>
			  		<h:outputText value="#{record.total}">
			  			<f:convertNumber  pattern="$####.00"  />
				  	</h:outputText>
				</h:column>
				<f:facet name="footer">
					<rich:datascroller for="table" rendered="true" />
				</f:facet>
			</rich:dataTable>
			<a4j:status startText="Sorting in progress" stopText=" " startStyle="color:red">
			</a4j:status>
			</h:form>
			<h:form>
				<rich:separator lineType="dashed" height="1" style="padding-top:20px" />
				<h:outputText value="Source Code:" />
				<h:outputLink value="http://anonsvn.jboss.org/repos/richfaces/trunk/richfaces-samples/richfaces-art-datatable/src/main/webapp/pages/columnsSorting.jsp">
					<h:outputText value="Page" />
				</h:outputLink>				
				<h:outputLink value="http://anonsvn.jboss.org/repos/richfaces/trunk/richfaces-samples/richfaces-art-datatable/src/main/">
					<h:outputText value="Project" />
				</h:outputLink>				
				<rich:separator lineType="dashed" height="1" style="padding-top:20px" />
				<h:commandLink value="back to example list" action="main"></h:commandLink>
			</h:form>	</f:view>
</body>
</html>