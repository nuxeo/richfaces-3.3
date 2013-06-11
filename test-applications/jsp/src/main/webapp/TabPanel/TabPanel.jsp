<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="tabPanelSubviewID">
	<rich:tabPanel id="tabPanelId" binding="#{tabPanel.htmlTabPanel}"
		headerAlignment="#{tabPanel.headerAlignment}"
		width="#{tabPanel.width}" contentStyle="#{style.contentStyle}"
		headerClass="#{style.headerClass}" style="#{style.style}"
		styleClass="#{style.styleClass}" tabClass="#{style.tabClass}"
		height="#{tabPanel.height}" rendered="#{tabPanel.rendered}"
		title="#{tabPanel.title}" switchType="#{tabPanel.switchType}"
		headerSpacing="#{tabPanel.headerSpacing}"
		selectedTab="#{tabPanel.selectedTab}"
		activeTabClass="#{tabPanel.activeTabStyle}"
		disabledTabClass="#{tabPanel.disabledTabStyle}"
		inactiveTabClass="#{tabPanel.inactiveTabStyle}"
		contentClass="#{tabPanel.contentStyle}" onclick="#{event.onclick}"
		ondblclick="#{event.ondblclick}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}">

		<rich:tab id="tabOne" binding="#{tabPanel.htmlTab}" labelWidth="#{tabPanel.labelWidth}"
			label="#{tabPanel.label}" onclick="#{event.onclick}"
			oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}"
			onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
			onkeyup="#{event.onkeyup}" onmousemove="#{event.onmousemove}"
			onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
			onmouseup="#{event.onmouseup}" ontabenter="#{event.ontabenter}"
			ontableave="#{event.ontableave}">
			<h:outputText value="This is tab panel test example"
				styleClass="text1"></h:outputText>
			<h:outputLink value="http://www.jboss.com/">
				<f:verbatim>Link</f:verbatim>
			</h:outputLink>
		</rich:tab>
		<rich:tab id="tabTwo" label="Tab with image"
			disabled="#{tabPanel.disabledTab}" onclick="#{event.onclick}"
			oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}"
			onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
			onkeyup="#{event.onkeyup}" onmousemove="#{event.onmousemove}"
			onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
			onmouseup="#{event.onmouseup}" ontabenter="#{event.ontabenter}"
			ontableave="#{event.ontableave}">
			<f:facet name="header">
				<h:outputText value="client switchType from facet" />
			</f:facet>
			<h:graphicImage value="/pics/masshtaby_01.jpg" width="560"
				height="383"></h:graphicImage>
		</rich:tab>
		<rich:tab id="tabThree" label="Tab with some text"
			disabled="#{tabPanel.disabledTab}" onclick="#{event.onclick}"
			oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}"
			onkeydown="#{event.onkeydown}" onkeypress="#{event.onkeypress}"
			onkeyup="#{event.onkeyup}" onmousemove="#{event.onmousemove}"
			onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
			onmouseup="#{event.onmouseup}" ontabenter="#{event.ontabenter}"
			ontableave="#{event.ontableave}">
			<h:outputText
				value="   Some text... Some text...  Some text... Some text... Some text... Some text... Some text... Some text...
				     Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text... 
				     Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
				     Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
				     Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text...
				     Some text... Some text... Some text... Some text... Some text... Some text... Some text... Some text..." />
		</rich:tab>
	</rich:tabPanel>

	<rich:spacer height="20px"></rich:spacer>
	<rich:tabPanel switchType="ajax" id="test">


		<rich:tab label="Prescription">
			<a4j:outputPanel id="first">
				<a4j:commandButton value="ajaxTest"
					oncomplete="Richfaces.showModalPanel('wizardSimplePresc',{})" />
			</a4j:outputPanel>
		</rich:tab>



		<rich:tab label="Recueil">

			<a4j:outputPanel id="second">
				<a4j:commandButton value="ajaxTest"
					oncomplete="Richfaces.showModalPanel('wizardSimplePresc',{})" />
			</a4j:outputPanel>

		</rich:tab>

	</rich:tabPanel>

	<rich:modalPanel id="wizardSimplePresc" minHeight="200" minWidth="50"
		height="350" width="900" zindex="2000">
		<f:facet name="header">
			<h:outputText value="Assistant Prescription" />
		</f:facet>
		<f:facet name="controls">
			<h:graphicImage value="../../image/calendar/close.gif"
				style="border:0px"
				onclick="Richfaces.hideModalPanel('wizardSimplePresc')" />
		</f:facet>
		<a4j:outputPanel id="wizard" layout="block"
			style="position:absolute; top:22px; bottom:0px; left:1px; right:1px; overflow:auto;">
			<a4j:region id="wizardRegion">
				<a4j:status id="wizardStatus" for="wizardRegion"
					startText="In progress..." stopText="Complete" />
				<a4j:commandButton reRender="first,second" value="Executer!"
					oncomplete="Richfaces.hideModalPanel('wizardSimplePresc')" />
			</a4j:region>
		</a4j:outputPanel>
	</rich:modalPanel>
</f:subview>
