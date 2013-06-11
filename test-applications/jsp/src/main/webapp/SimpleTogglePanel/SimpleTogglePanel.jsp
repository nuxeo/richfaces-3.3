<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="simpleTogglePanelSubviewID">

	<rich:simpleTogglePanel id="stpIncludeID" switchType="client">
		<f:facet name="closeMarker">
			<h:outputText value="Close it" />
		</f:facet>

		<f:facet name="openMarker">
			<h:outputText value="Open it" />
		</f:facet>
		<h:selectOneMenu value="#{richBean.srcContainer}" onchange="submit();">
			<f:selectItems value="#{richBean.listContainer}" />
		</h:selectOneMenu>
		<jsp:include flush="true" page="${richBean.pathComponentContainer}" />
	</rich:simpleTogglePanel>
	<rich:simpleTogglePanel
		binding="#{simpleTogglePanel.htmlSimpleTogglePanel}" id="sTP"
		bodyClass="body" headerClass="head"
		label="simpleTogglePanel with some text"
		action="#{simpleTogglePanel.act}"
		actionListener="#{simpleTogglePanel.actListener}"
		width="#{simpleTogglePanel.width}"
		height="#{simpleTogglePanel.height}"
		switchType="#{simpleTogglePanel.switchType}" style="#{style.style}"
		styleClass="#{style.styleClass}" oncollapse="#{event.oncollapse}"
		onbeforedomupdate="#{event.onbeforedomupdate}"
		onexpand="#{event.onexpand}" opened="#{event.opened}"
		onclick="#{event.onclick}" oncomplete="#{event.oncomplete}"
		ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}" immediate="true">
		<f:facet name="closeMarker">
			<h:outputText value="Close It" />
		</f:facet>
		<f:facet name="openMarker">
			<h:outputText value="Open It" />
		</f:facet>
		<f:verbatim>
            Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
            Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
            Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
            Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
            Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
            Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
        </f:verbatim>
		<h:outputLink value="http://www.jboss.com/">
			<f:verbatim>Link</f:verbatim>
		</h:outputLink>
	</rich:simpleTogglePanel>

	<rich:simpleTogglePanel id="sTP1" headerClass="head"
		label="simpleTogglePanel wiht image"
		width="#{simpleTogglePanel.width}" action="#{simpleTogglePanel.act}"
		actionListener="#{simpleTogglePanel.actListener}"
		height="#{simpleTogglePanel.height}"
		rendered="#{simpleTogglePanel.rendered}"
		switchType="#{simpleTogglePanel.switchType}" opened="false"
		onclick="#{event.onclick}" oncomplete="#{event.oncomplete}"
		ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}">
		<h:graphicImage value="/pics/podb109_61.jpg" width="500" height="300"></h:graphicImage>
	</rich:simpleTogglePanel>

	<rich:simpleTogglePanel id="sTP2" label="Focus simpleTogglePanle"
		width="#{simpleTogglePanel.width}" ignoreDupResponses="true"
		action="#{simpleTogglePanel.act}"
		actionListener="#{simpleTogglePanel.actListener}"
		focus="#{simpleTogglePanel.focus}" onclick="#{event.onclick}"
		oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}"
		onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
		onkeyup="#{event.onkeyup}" onmousedown="#{event.onmousedown}"
		onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}"
		onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}">
		<f:facet name="closeMarker">
			<h:graphicImage value="/pics/ajax_stoped.gif"></h:graphicImage>
		</f:facet>
		<f:facet name="openMarker">
			<h:graphicImage value="/pics/ajax_process.gif"></h:graphicImage>
		</f:facet>
		<rich:simpleTogglePanel id="INsTP">
			<h:panelGrid columns="2">
				<h:graphicImage value="/pics/podb109_61.jpg" width="250px"
					height="200px"></h:graphicImage>
				<f:verbatim>
                    Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
                    Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
                    Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
                    Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
                    Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
                    Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
                </f:verbatim>
			</h:panelGrid>
		</rich:simpleTogglePanel>
	</rich:simpleTogglePanel>
	<h:panelGrid id="simpleTogglePanelActionID" columns="1">
		<a4j:commandButton value="Show action"
			reRender="simpleTogglePanelActionID" style=" width : 95px;"></a4j:commandButton>
		<h:outputText value="#{simpleTogglePanel.action}" />
		<h:outputText value="#{simpleTogglePanel.actionListener}" />
	</h:panelGrid>
	<rich:spacer height="20px"></rich:spacer>
</f:subview>
