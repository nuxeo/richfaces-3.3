<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@page contentType="text/html; charset=UTF-8" %>

<html>
<f:view>
    <head>
        <f:loadBundle basename="message" var="msg"/>
        <title></title>

        <script type="text/javascript">
            function showEvent(elementID, value) {
                var oldObject = window.document.getElementById(elementID);
                if (oldObject == null) return;
                var newObject = window.document.createElement('input');
                if (oldObject.type) newObject.type = oldObject.type;
                if (oldObject.size) newObject.size = oldObject.size;
                if (oldObject.value) newObject.value = value;
                if (oldObject.id) newObject.id = oldObject.id;
                oldObject.parentNode.replaceChild(newObject, oldObject);
            }
        </script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/styles.css" type="text/css"/>
    </head>
    <body>
    <h:form id="formID">
        <div id="divOpthID" align="right" style="z-index: 200">
            <jsp:include page="/pages/RichMenu/RichMenu.jsp"/>
        </div>
        	<div id="divID" style="position: absolute; top:60px; left: 10px; z-index: -1">
	 	<jsp:include page="SelectOneMenuPage.jsp" />
	</div>
		<rich:messages id="richMessagesID" ajaxRendered="true" showSummary="true">
				<f:facet name="errorMarker">
					<h:graphicImage url="/pics/error.gif" />
				</f:facet>

				<f:facet name="fatalMarker">
					<h:graphicImage url="/pics/fatal.gif" />
				</f:facet>

				<f:facet name="infoMarker">
					<h:graphicImage url="/pics/info.gif" />
				</f:facet>

				<f:facet name="warnMarker">
					<h:graphicImage url="/pics/warn.gif" />
				</f:facet>

				<f:facet name="passedMarker">
					<h:graphicImage url="/pics/passed.gif" />
				</f:facet>
		</rich:messages>
        <h:messages id="hMessagesID" showDetail="true" showSummary="true"></h:messages>
       
        <h:panelGrid id="richGridID" columns="1" width="100%" rendered="true">
            <h:panelGroup rendered="#{option.reComponent}">
                    <jsp:include page="${richBean.pathComponent}"/>
            </h:panelGroup>
            <a4j:log popup="false" rendered="#{option.log}"></a4j:log>
            <h:panelGroup rendered="#{option.reStraightforward}">
                <jsp:include page="${richBean.pathStraightforward}"/>
            </h:panelGroup>

            <h:panelGroup rendered="#{option.reProperty}">
                <jsp:include page="${richBean.pathProperty}"/>
            </h:panelGroup>
        </h:panelGrid>
    </h:form>
	<h:form id="infoFormID">
	            <jsp:include page="/pages/Info/Info.jsp"/>
	</h:form>
    </body>
</f:view>
</html>
