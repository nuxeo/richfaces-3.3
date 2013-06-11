<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="panelBarSubviewID">
	<rich:panelBar binding="#{panelBar.panelBar}" id="pBId" height="#{panelBar.height}"
		width="#{panelBar.width}" contentClass="#{panelBar.contentStyle}"
		styleClass="#{panelBar.style}" selectedPanel="pBiId4"
		
		onclick="#{event.onclick}" onitemchange="#{event.onitemchange}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}">
		<rich:panelBarItem rendered="#{panelBar.rendered}" id="pBiId1"
			label="#{panelBar.label[0]}" headerClass="#{panelBar.headerStyle}">
			<h:outputText value="Some text..."></h:outputText>
			<h:outputLink value="http://www.jboss.com/">
				<f:verbatim>Link</f:verbatim>
			</h:outputLink>
		</rich:panelBarItem>

		<rich:panelBarItem id="pBiId2" label="#{panelBar.label[1]}"
			headerClass="#{panelBar.headerStyle}">
			<h:graphicImage value="/pics/masshtaby_01.jpg" width="300"
				height="200"></h:graphicImage>
		</rich:panelBarItem>

		<rich:panelBarItem id="pBiId3" label="#{panelBar.label[2]}"
			headerClass="#{panelBar.headerStyle}">
			<f:facet name="openMarker">
				<h:graphicImage value="/pics/ajax_process.gif"></h:graphicImage>
			</f:facet>
			<f:facet name="closeMarker">
				<h:graphicImage value="/pics/ajax_stoped.gif"></h:graphicImage>
			</f:facet>
			<h:graphicImage value="/pics/podb109_61.jpg" width="300" height="200"></h:graphicImage>
		</rich:panelBarItem>

		<rich:panelBarItem id="pBiId4" label="#{panelBar.label[3]}"
			headerClass="#{panelBar.headerStyle}">
			<h:outputText value="Select Panel"></h:outputText>
		</rich:panelBarItem>
	</rich:panelBar>

	<rich:spacer height="20px"></rich:spacer>
</f:subview>
