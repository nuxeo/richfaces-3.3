<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<style>
.facetDiv {
	border: 1px solid red;
}

.layoutDiv {
	border: 1px solid green;
}

.layoutPanelDiv {
	border: 1px solid blue;
	width: 350px;
	height: 100px;
}

/* Footer */
#footer {
	padding: 4px 0 6px 0;
	background: url(pics/img08.gif) repeat-x;
}

#footer p {
	width: 750px;
	font-family: Georgia, "Times New Roman", Times, serif;
	color: #A6C09B;
}

#footer a {
	background: none;
	font-weight: bold;
	color: #A6C09B;
}

#legal {
	margin: 0 auto;
	text-align: right;
	font-size: 12px;
}

#brand {
	margin: -35px auto 0 auto;
	padding: 10px 0 0 35px;
	letter-spacing: -1px;
	font-size: 24px;
}
</style>
<link href="/src/main/webapp/LayoutComponents/default.css"
	rel="stylesheet" type="text/css" />
<f:subview id="layoutComponentsSubviewID">
	<rich:page binding="#{page.htmlPage}" bodyClass="#{page.bodyClass}"
		contentType="#{page.contentType}" footerClass="#{page.footerClass}"
		headerClass="#{page.headerClass}" id="pageID" lang="#{page.lang}"
		markupType="#{page.markupType}" namespace="#{page.namespace}"
		onload="#{event.onload}" onunload="#{event.onunload}"
		pageTitle="#{page.pageTitle}" rendered="#{page.rendered}"
		sidebarClass="#{page.sidebarClass}"
		sidebarPosition="#{page.sidebarPosition}"
		sidebarWidth="#{page.sidebarWidth}" style="#{page.style}"
		styleClass="#{page.styleClass}" theme="#{page.theme}"
		title="#{page.title}" width="#{page.width}">
		<f:facet name="header">
			<h:panelGroup layout="block" styleClass="facetDiv">
				<div id="header">
				<h1>Header facet</h1>
				<h2>By Richfaces team</h2>
				</div>
			</h:panelGroup>
		</f:facet>
		<f:facet name="sidebar">
			<h:form>
				<h:panelGroup>
					<rich:layout id="layoutID" binding="#{layout.htmlLayout}"
						rendered="#{layout.rendered}">
						<div class="layoutDiv" align="center"><rich:layoutPanel
							position="#{layout.panels[0].position}">
							<div class="layoutPanelDiv" align="center"><h:graphicImage
								value="/pics/construction_bucket_16.png" /> <b>LEFT LAYOUT
							PANEL</b> <h:selectOneRadio value="#{layout.tempPanels[0].position}">
								<f:selectItems value="#{layoutPanel.positions}" />
							</h:selectOneRadio></div>
						</rich:layoutPanel> <rich:layoutPanel position="#{layout.panels[1].position}">
							<div class="layoutPanelDiv" align="center"><h:graphicImage
								value="/pics/2.gif" /> <b>RIGHT LAYOUT PANEL</b> <h:selectOneRadio
								value="#{layout.tempPanels[1].position}">
								<f:selectItems value="#{layoutPanel.positions}" />
							</h:selectOneRadio></div>
						</rich:layoutPanel> <rich:layoutPanel position="#{layout.panels[2].position}"
							binding="#{layoutPanel.htmlLayoutPanel}" id="layoutPanelID"
							rendered="#{layoutPanel.rendered}">
							<div class="layoutPanelDiv" align="center"><h:graphicImage
								value="/pics/3.gif" /> <b>CENTER LAYOUT PANEL</b> <h:selectOneRadio
								value="#{layout.tempPanels[2].position}">
								<f:selectItems value="#{layoutPanel.positions}" />
							</h:selectOneRadio></div>
						</rich:layoutPanel> <rich:layoutPanel position="#{layout.panels[3].position}">
							<div class="layoutPanelDiv" align="center"><h:graphicImage
								value="/pics/4.gif" /> <b>TOP LAYOUT PANEL</b> <h:selectOneRadio
								value="#{layout.tempPanels[3].position}">
								<f:selectItems value="#{layoutPanel.positions}" />
							</h:selectOneRadio></div>
						</rich:layoutPanel> <rich:layoutPanel position="#{layout.panels[4].position}">
							<div class="layoutPanelDiv" align="center"><h:graphicImage
								value="/pics/caution_tape_16.png" /> <b>BOTTOM LAYOUT PANEL</b>
							<h:selectOneRadio value="#{layout.tempPanels[4].position}">
								<f:selectItems value="#{layoutPanel.positions}" />
							</h:selectOneRadio></div>
						</rich:layoutPanel></div>
					</rich:layout>
				</h:panelGroup>
			</h:form>
		</f:facet>
		<f:facet name="footer">
			<h:panelGroup layout="block" styleClass="facetDiv">
				<div id="footer">
				<p id="legal">Copyright &copy; 2009 Richfaces. Designed by <a
					href="http://www.jboss.org/">QA</a></p>
				<p id="brand">FOOTER</p>
				</div>
			</h:panelGroup>
		</f:facet>
	</rich:page>
</f:subview>