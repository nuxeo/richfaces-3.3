<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="columnsSubviewID">

	<h:selectBooleanCheckbox value="#{columns.dataTableRendered}">
		<a4j:support event="onchange" reRender="richGridID"></a4j:support>
	</h:selectBooleanCheckbox>
	<h:outputText value="rich:dataTable:" rendered="#{columns.dataTableRendered}"></h:outputText>
	<h:outputText value="h:dataTable:" rendered="#{!columns.dataTableRendered}"></h:outputText>
	<br/>
	<h:outputText value="#{columns.text}"/>

	<rich:dataTable id="richColumnsID" value="#{columns.data1}" var="d1" rendered="#{columns.dataTableRendered}">
		<h:column>
			<f:facet name="header">
				<h:outputText value="header (h)"></h:outputText>
			</f:facet>
			<h:outputText value="h: #{d1.int0}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="footer (h)"></h:outputText>
			</f:facet>
		</h:column>
		
		<rich:column>
			<f:facet name="header">
				<h:outputText value="header (rich)"></h:outputText>
			</f:facet>
			<h:outputText value="rich: #{d1.int0}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="footer (rich)"></h:outputText>
			</f:facet>
		</rich:column>


		<rich:columns value="#{columns.data2}" var="d2" footerClass="#{style.footerClass}" headerClass="#{style.headerClass}" breakBefore="#{columns.breakBefore}" colspan="#{columns.colspan}"
			columns="#{columns.columns}" index="index" rowspan="#{columns.rowspan}" begin="#{columns.begin}" end="#{columns.end}" width="#{columns.width}" style="#{style.style}" styleClass="#{style.styleClass}" 
			sortOrder="#{columns.orderings[index]}" sortBy="#{d1.str0}" 
			filterBy="#{d1.str0}" 
			filterValue="#{columns.filterValue[index]}" filterMethod="#{columns.filterMethod}">
			<f:facet name="header">
				<h:outputText value="header #{d2.int0}"></h:outputText>
			</f:facet>
			<h:outputText value="#{index}. "></h:outputText>
			<h:outputText value="#{d1.str0}, "></h:outputText>
			<h:outputText value="#{d2.str0}"></h:outputText>
			<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
			<f:facet name="footer">
				<h:outputText value="footer #{d2.int0}"></h:outputText>
			</f:facet>
		</rich:columns>
	</rich:dataTable>

	<h:dataTable id="hColumnsID" value="#{columns.data1}" var="d1" rendered="#{!columns.dataTableRendered}" border="1">
		<h:column>
			<f:facet name="header">
				<h:outputText value="header (h)"></h:outputText>
			</f:facet>
			<h:outputText value="h: #{d1.int0}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="footer (h)"></h:outputText>
			</f:facet>
		</h:column>
		
		<rich:column>
			<f:facet name="header">
				<h:outputText value="header (rich)"></h:outputText>
			</f:facet>
			<h:outputText value="rich: #{d1.int0}"></h:outputText>
			<f:facet name="footer">
				<h:outputText value="footer (rich)"></h:outputText>
			</f:facet>
		</rich:column>

		<rich:columns value="#{columns.data2}" var="d2" breakBefore="#{columns.breakBefore}" colspan="#{columns.colspan}"
			columns="#{columns.columns}" index="index" rowspan="#{columns.rowspan}" sortable="#{columns.sortable}" 
			begin="#{columns.begin}" end="#{columns.end}" width="#{columns.width}">
			<f:facet name="header">
				<h:outputText value="header #{d2.int0}"></h:outputText>
			</f:facet>
			<h:outputText value="#{index}. "></h:outputText>
			<h:outputText value="#{d1.str0}, "></h:outputText>
			<h:outputText value="#{d2.str0}"></h:outputText>
			<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
			<f:facet name="footer">
				<h:outputText value="footer #{d2.int0}"></h:outputText>
			</f:facet>
		</rich:columns>
	</h:dataTable>
	
	<f:verbatim><br/></f:verbatim>
	<h:outputText value="test columns (*) "></h:outputText>
	
	<rich:dataTable value="#{columns.data1}" var="data">
		<rich:columns columns="#{columns.columns}">
			<h:outputText value="#{data.str0}"></h:outputText>
		</rich:columns>
	</rich:dataTable>
	<rich:spacer height="20" width="150"></rich:spacer>
	
	<rich:dataTable id="latsTable" value="#{columns.data1}" var="cust" >
       <f:facet name="header">
		<rich:columnGroup>
			<rich:column>
				<h:outputText value="Data" />
			</rich:column>
		</rich:columnGroup>
          </f:facet>
          <rich:column sortBy="#{cust.str0}" selfSorted="true"> 
              <h:outputText value="#{cust.str0}"/>
              <f:facet name="header">
              <h:outputText value="#1"></h:outputText>
              </f:facet>
          </rich:column> 
</rich:dataTable>
<a4j:commandButton value="reRender"
				reRender="lastTable"></a4j:commandButton>

</f:subview>