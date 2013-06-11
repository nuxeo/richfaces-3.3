<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:view>
<html>
<head>
<title></title>
	<script type="text/javascript">
		function showEvent(elementID, value) {
			var oldObject = window.document.getElementById(elementID);
			var newObject = window.document.createElement('input');
			newObject.type = "text";
			newObject.size = oldObject.size;
			newObject.value = value;
			newObject.id = oldObject.id;
			//if(oldObject.size) newObject.size = oldObject.size;
			//if(oldObject.value) newObject.value = value;
			//if(oldObject.id) newObject.id = oldObject.id;
			//if(oldObject.name) newObject.name = oldObject.name;
			//if(oldObject.className) newObject.className = oldObject.className;
			oldObject.parentNode.replaceChild(newObject,oldObject);
		}
	</script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/styles/styles.css" type="text/css" />
</head>
<body>
	<jsp:include page="/pages/RichMenu/RichMenu.jsp" />
	<div id="div_1_ID" style="position: relative; left:400px; top:400px; border-color: red, 2px;">
		<div id="div_2_ID" style="position: absolute; left:-380px; top:-380px; color: blue, 2px;">
			<jsp:include page="${richBean.pathComponent}" />
		</div>
	</div>
	<h:form>
		<jsp:include page="${richBean.pathStraightforward}" />
		<jsp:include page="${richBean.pathProperty}" />
	</h:form>
	<rich:modalPanel id="eventInfoID" minHeight="550" minWidth="200" moveable="true" resizeable="true" style="overflow: true;">
		<f:facet name="header">
			<h:outputText value="Events ..." />
		</f:facet>
		<f:facet name="controls">
			<h:graphicImage value="/pics/error.gif"  onclick="Richfaces.hideModalPanel('eventInfoID');"/>
		</f:facet>
		
		<jsp:include page="/pages/Action/EventInfo.jsp" />

	</rich:modalPanel>	
	<a4j:commandButton value="Show event" onclick="Richfaces.showModalPanel('eventInfoID');return false;"></a4j:commandButton>
</body>
</html>
</f:view>