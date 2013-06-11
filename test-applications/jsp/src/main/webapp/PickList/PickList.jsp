<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="pickListSubviewID">
	<rich:pickList id="pickListID" value="#{pickList.arrValue}"
		showButtonsLabel="#{pickList.showButtonLabels}"
		valueChangeListener="#{pickList.valueChangeListener}"
		controlClass="#{style.controlClass}" 
		listClass="#{style.listClass}" 
		style="#{style.style}" styleClass="#{style.styleClass}"
		copyAllControlLabel="#{pickList.copyAllControlLabel}"
		copyControlLabel="#{pickList.copyControlLabel}"
		disabled="#{pickList.copyControlLabel}"
		immediate="#{pickList.immediate}"
		listsHeight="#{pickList.listsHeight}"
		moveControlsVerticalAlign="#{pickList.moveControlsVerticalAlign}"
		removeAllControlLabel="#{pickList.removeAllControlLabel}"
		removeControlLabel="#{pickList.removeControlLabel}"
		rendered="#{pickList.rendered}"
		sourceListWidth="#{pickList.sourceListWidth}"
		title="#{pickList.title}" switchByClick="#{pickList.switchByClick}"
		targetListWidth="#{pickList.targetListWidth}"
		required="#{pickList.required}"
		requiredMessage="#{pickList.requiredMessage}"
		moveControlsVisible="#{pickList.moveControlsVisible}"
		fastMoveControlsVisible="#{pickList.fastMoveControlsVisible}"
		
		onclick="#{event.onclick}" 
		ondblclick="#{event.ondblclick}" 
		onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" 
		onkeyup="#{event.onkeyup}" 
		onmousedown="#{event.onmousedown}" 
		onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onlistchanged="#{event.onlistchanged}"
		onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}"
		binding="#{pickList.myPickList}" onblur="#{event.onblur}" onfocus="#{event.onfocus}">
		<f:selectItems value="#{pickList.data}"/>
	</rich:pickList>
	<h:panelGrid columns="2">
		<a4j:commandButton value="refresh" reRender="pickListvalueCLID"></a4j:commandButton>
		<h:outputText id="pickListvalueCLID" value="#{pickList.valueCL}"></h:outputText>
	</h:panelGrid>
</f:subview>