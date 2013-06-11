<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="listShuttleSubviewID">
	<rich:listShuttle requiredMessage="Test mesages" id="listShuttleID" immediate="true" rendered="true" var="item"
		binding="#{listShuttle.htmlListShuttle}"
		sourceValue="#{listShuttle.sourceValue}"
		targetValue="#{listShuttle.targetValue}"
		bottomControlLabel="#{listShuttle.bottomControlLabel}"
		copyAllControlLabel="#{listShuttle.copyAllControlLabel}"
		copyControlLabel="#{listShuttle.copyControlLabel}"
		downControlLabel="#{listShuttle.downControlLabel}"
		fastMoveControlsVisible="#{listShuttle.fastMoveControlsVisible}"
		converter="dataConverter"
		fastOrderControlsVisible="#{listShuttle.fastOrderControlsVisible}"		
		moveControlsVisible="#{listShuttle.moveControlsVisible}"
		orderControlsVisible="#{listShuttle.orderControlsVisible}"
		removeAllControlLabel="#{listShuttle.removeAllControlLabel}"
		removeControlLabel="#{listShuttle.removeControlLabel}"
		sourceSelection="#{listShuttle.sourceSelection}"
		targetSelection="#{listShuttle.targetSelection}"
		showButtonLabels="#{listShuttle.showButtonLabels}"
		switchByClick="#{listShuttle.switchByClick}"
		switchByDblClick="#{listShuttle.switchByDblClick}"
		targetListWidth="#{listShuttle.targetListWidth}"
		sourceListWidth="#{listShuttle.sourceListWidth}"
		listsHeight="#{listShuttle.listsHeight}"
		sourceRequired="#{listShuttle.sourceRequired}"
		targetRequired="#{listShuttle.targetRequired}"
		sourceCaptionLabel="#{listShuttle.sourceCaptionLabel}"
		targetCaptionLabel="#{listShuttle.targetCaptionLabel}"
		topControlLabel="#{listShuttle.topControlLabel}"
		upControlLabel="#{listShuttle.upControlLabel}"
		onmousemove="#{event.onmousemove}"
		onclick="#{event.onclick}"
		controlsVerticalAlign="#{listShuttle.controlsVerticalAlign}"
		controlsType="#{listShuttle.controlsType}"
		ondblclick="#{event.ondblclick}"
		onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}"
		onorderchanged="#{event.onorderchanged}"
		onorderchange="#{event.onorderchange}"
		ontopclick="#{event.ontopclick}"
		onupclick="#{event.onupclick}"
		ondownclick="#{event.ondownclick}"
		onbottomclick="#{event.onbottomclick}"
		onremoveclick="#{event.onremoveclick}"
		onremoveallclick="#{event.onremoveallclick}"
		oncopyclick="#{event.oncopyclick}"
		oncopyallclick="#{event.oncopyallclick}"
		onfocus="#{event.onfocus}"
		onlistchange="#{event.onlistchange}"
		onlistchanged="#{event.onlistchanged}"
		onblur="#{event.onblur}">
		<%-- 
		<f:facet name="topControl">
			<h:outputText value="top" />
		</f:facet>
		<f:facet name="bottomControl">
			<h:outputText value="bottom" />
		</f:facet>
		<f:facet name="upControl">
			<h:outputText value="up" />
		</f:facet>
		<f:facet name="downControl">
			<h:outputText value="down" />
		</f:facet>
		<f:facet name="topControlDisabled">
			<h:outputText value="top disabled" />
		</f:facet>
		<f:facet name="bottomControlDisabled">
			<h:outputText value="bottom disabled" />
		</f:facet>
		<f:facet name="upControlDisabled">
			<h:outputText value="up disabled" />
		</f:facet>
		<f:facet name="downControlDisabled">
			<h:outputText value="down disabled" />
		</f:facet>
		
		
		<f:facet name="copyControl">
			<h:outputText value="copy" />
		</f:facet>
		<f:facet name="removeControl">
			<h:outputText value="remove" />
		</f:facet>
		<f:facet name="copyAllControl">
			<h:outputText value="copy all" />
		</f:facet>
		<f:facet name="removeAllControl">
			<h:outputText value="remove all" />
		</f:facet>
		<f:facet name="copyControlDisabled">
			<h:outputText value="copy disabled" />
		</f:facet>
		<f:facet name="removeControlDisabled">
			<h:outputText value="remove disabled" />
		</f:facet>
		<f:facet name="copyAllControlDisabled">
			<h:outputText value="copy all disabled" />
		</f:facet>
		<f:facet name="removeAllControlDisabled">
			<h:outputText value="remove all disabled" />
		</f:facet>
		--%>
		<h:column>
			<f:facet name="header">
				<h:outputText value="Output" />
			</f:facet>
			<h:outputText value="#{item.int0}" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Input" />
			</f:facet>
			<h:inputText value="#{item.str4}" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Button" />
			</f:facet>
			<h:commandButton value="#{item.str0} sbmt();" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="a4jLink" />
			</f:facet>
			<a4j:commandLink action="#{listShuttle.clAction}"
				value="#{item.str1} a4j" reRender="listShuttleID"></a4j:commandLink>
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Select" />
			</f:facet>
			<h:selectOneMenu value="#{item.str2}">
				<f:selectItem itemLabel="select0" itemValue="select0" />
				<f:selectItem itemLabel="select1" itemValue="select1" />
				<f:selectItem itemLabel="select2" itemValue="select2" />
				<f:selectItem itemLabel="select3" itemValue="select3" />
				<f:selectItem itemLabel="select4" itemValue="select4" />
				<a4j:support event="onclick" reRender="listShuttleID"></a4j:support>
			</h:selectOneMenu>
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="GraphicImage" />
			</f:facet>
			<h:graphicImage value="#{item.str3}" />
		</h:column>

		<rich:column>
			<f:facet name="header">
				<h:outputText value="OutputLink"></h:outputText>
			</f:facet>
			<h:outputLink value="http://www.jboss.com/">
				<f:verbatim>outputLink</f:verbatim>
			</h:outputLink>
		</rich:column>
	</rich:listShuttle>
</f:subview>