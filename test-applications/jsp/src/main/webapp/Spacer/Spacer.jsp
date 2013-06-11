<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="spacerSubviewID">
		<style type="text/css">
.text {
	font-size: 20px;
}

.spacer {
	background-color: aqua;
}
</style>
		<h:outputText value="Some text one..." styleClass="text"></h:outputText>
		
		<rich:spacer id="spacerId" binding="#{spacer.htmlSpacer}" title="#{spacer.title}" width="#{spacer.width}" height="#{spacer.height}" rendered="#{spacer.rendered}"
			styleClass="#{spacer.style}" style="#{style.style}" 
			onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}">
		</rich:spacer>
		<h:outputText value="Some text two..." styleClass="text"></h:outputText>
</f:subview>
