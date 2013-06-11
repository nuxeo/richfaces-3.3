<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="messageSubviewID">
		<rich:panel>
			<h:outputText value="Rich Message Demo:" />

			<f:verbatim>
				<br />
			</f:verbatim>

			<rich:message for="#{message.msgs}" binding="#{message.htmlMessage}" tooltip="#{message.tooltip}" showDetail="#{message.showDetail}"
				showSummary="#{message.showSummary}" passedLabel="No Error" errorLabelClass="errorLabel" fatalLabelClass="warnLabel"
				infoLabelClass="infoLabel" warnLabelClass="fatalLabel" title="#{message.title}" id="messageID">


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
			</rich:message>

			<rich:separator></rich:separator>

			<h:outputText value="Rich Messages Demo" />
			<rich:messages binding="#{message.htmlMessages}" layout="#{message.layout}" id="messagesID" tooltip="#{message.tooltip}" showDetail="#{message.showDetail}"
				showSummary="#{message.showSummary}" passedLabel="No Error" errorLabelClass="errorLabel" fatalLabelClass="warnLabel"
				infoLabelClass="infoLabel" warnLabelClass="fatalLabel" warnMarkerClass="markerWarn" infoMarkerClass="markerInfo"
				errorMarkerClass="markerError" fatalMarkerClass="markerFatal" errorClass="errorClass" fatalClass="fatalClass"
				warnClass="warnClass" infoClass="infoClass" labelClass="labelClass" styleClass="class" title="#{message.title}">
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
				<h:outputText value="#{messages.summary}">
				</h:outputText>
			</rich:messages>
		</rich:panel>
</f:subview>
