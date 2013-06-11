<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/separator" prefix="rich"%>
<html>
<body>
<f:view>

	<h:form>
        <h:selectOneRadio binding="#{skinBean.component}" />
        <h:commandLink action="#{skinBean.change}" value="set skin" />
	</h:form>

	<h:form>
	<h:messages style="color:red"/>
	<h:outputText value="Simple richfaces separator test web application." style="font: 18px;font-weight: bold;" />
	<br /><br />
	
	<h:outputText value="Beveled type separator." />
	<br />
	<rich:separator id="separator_01" align="#{bean.align}" height="#{bean.height}" width="#{bean.width}" lineType="beveled"
		style="#{bean.style}"/>
	<br /><br />
	
	<h:outputText value="Solid type separator." />
	<br />
	<rich:separator id="separator_02" align="#{bean.align}" height="#{bean.height}" width="#{bean.width}" lineType="solid"
		style="#{bean.style}"/>
	<br /><br />
		
	<h:outputText value="Dotted type separator." />
	<br />
	<rich:separator id="separator_03" align="#{bean.align}" height="#{bean.height}" width="#{bean.width}" lineType="dotted"
		style="#{bean.style}"/>
	<br /><br />
	
	<h:outputText value="Dashed type separator." />
	<br />
	<rich:separator id="separator_04" align="#{bean.align}" height="#{bean.height}" width="#{bean.width}" lineType="dashed"
		style="#{bean.style}"/>
	<br /><br />
	
	<h:outputText value="Double type separator." />
	<br />
	<rich:separator id="separator_05" align="#{bean.align}" height="#{bean.height}" width="#{bean.width}" lineType="double"
		style="#{bean.style}"/>
	<br /><br />
	
	<h:outputText value="Change separators width:" />
	<h:selectOneRadio value="#{bean.width}" onclick="submit()">									
		<f:selectItem itemLabel="100px" itemValue="100px" />									
		<f:selectItem itemLabel="300px" itemValue="300px" />
		<f:selectItem itemLabel="500px" itemValue="500px" />
		<f:selectItem itemLabel="25%" itemValue="25%" />
		<f:selectItem itemLabel="50%" itemValue="50%" />
		<f:selectItem itemLabel="75%" itemValue="75%" />
		<f:selectItem itemLabel="100%" itemValue="100%" />
	</h:selectOneRadio>	
	<br />
	<h:outputText value="Change separators align:" />
	<h:selectOneRadio value="#{bean.align}" onclick="submit()">									
		<f:selectItem itemLabel="left" itemValue="left" />									
		<f:selectItem itemLabel="right" itemValue="right" />
		<f:selectItem itemLabel="center" itemValue="center" />
	</h:selectOneRadio>	
	<br />
	
	<h:outputText value="Change separators height:" />
	<h:inputText value="#{bean.height}" onchange="submit()"/>
	<h:commandButton value="Change!"/>	
	<br /><br />
	
	<h:outputText value="Set some separators style:" />
	<h:inputText value="#{bean.style}" size="100" onchange="submit()"/>
	<h:commandButton value="Change!"/>	
	<br />
	
	</h:form>
</f:view>
</body>
</html>
