<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/inputnumber-spinner" prefix="sp" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
		
			<h:form>
		        <h:selectOneRadio binding="#{skinBean.component}" />
		        <h:commandLink action="#{skinBean.change}" value="set skin" />			
		
				<h:messages style="color:red"/>
				<h:outputText value="Simple richfaces inputnumber-spinner test web application." style="font: 18px;font-weight: bold;" />
				<br /><br />
			
				<sp:inputNumberSpinner value="5" disabled="#{bean.disabled}" />
				<br />
			
				<h:commandButton value="#{bean.commandButtonCaption}"
					actionListener="#{bean.swichDisabled}" />				
			</h:form>
		</f:view>
	</body>	
</html>  
