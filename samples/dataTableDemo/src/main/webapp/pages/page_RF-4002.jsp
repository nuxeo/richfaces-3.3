<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<html>
	<head>
		<title>RF-4002</title>
	</head>
	<body>
		<f:view>
			<h:form id="_form">
				<rich:dataTable value="1" id="dataTable">
					<rich:column>
						<h:inputText value="#{testBean.text}"></h:inputText>						
	                    <a4j:commandLink id="lnk" value="Submit" reRender="out"></a4j:commandLink>
						<h:outputText id="out" value="#{testBean.text}"/>
					</rich:column>
				</rich:dataTable>
			</h:form> 
		</f:view>
	</body>
</html>	