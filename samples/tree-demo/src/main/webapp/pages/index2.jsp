<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tree" prefix="rich" %>
<html>
<head>
    <title></title>
</head>

<body>
<f:view>

<h:form>

				<f:verbatim>
					<script>
						window.time = new Date();
					</script>
				</f:verbatim>


    <rich:tree switchType="client" style="width:300px"
               value="#{pathwayBean.pathwayTree}" var="item" treeNodeVar="treeNode"
               nodeFace="#{item.type}">
        <rich:treeNode type="library">
            <h:outputText value="#{item.type}"/>
            <h:outputText value="_" />
			<h:outputText value="#{treeNode.data.type}" />
        </rich:treeNode>
        <rich:treeNode type="pathway">
            <h:outputText value="#{item.name}"/>
            <h:outputText value="-" />
			<h:outputText value="#{treeNode.data.name}" />
        </rich:treeNode>
        <rich:treeNode type="organism">
            <h:outputText value="#{item.name}"/>
            <h:outputText value="+" />
			<h:outputText value="#{treeNode.data.name}" />
        </rich:treeNode>
    </rich:tree>

				<f:verbatim>
					<script>
						alert(new Date() - window.time);
					</script>
				</f:verbatim>


</h:form>

<a4j:log hotkey="O"/>

</f:view>
</body>
</html>
