<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="separatorSubviewID">
		<h:outputText value="Some text one..." styleClass="text"></h:outputText>
		<rich:separator id="separatorId" binding="#{separator.htmlSeparator}" rendered="#{separator.rendered}" width="#{separator.width}" height="#{separator.height}"
			title="#{separator.title}" lineType="#{separator.lineType}" align="#{separator.align}" 
			style="#{style.style}" styleClass="#{style.styleClass}"
			onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}"></rich:separator>
		<h:outputText value="Some text two..." styleClass="text"></h:outputText>
		<rich:spacer height="20px"></rich:spacer>
</f:subview>
