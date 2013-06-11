<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="effectSubviewID">

	<rich:panel id="indexID">
		<a4j:commandLink value="Hide all"
			onclick="hideFrm1(),hideFrm2(),hideFrm3(),hideFrm4(),hideFrm5()"></a4j:commandLink>

		<f:verbatim>
			<br />
		</f:verbatim>

		<h:outputText value="Menu:" />
		<h:panelGrid columns="2">
			<h:outputText value="1." />
			<f:verbatim>
				<span onclick="showFrm1(),hideIndexID()"><font color="blue">JSF
				Components</font></span>
			</f:verbatim>

			<h:outputText value="2." />
			<f:verbatim>
				<span onclick="showFrm2(),hideIndexID()"><font color="blue">JSF
				Component with Event and non-jsf target</font></span>
			</f:verbatim>

			<h:outputText value="3." />
			<f:verbatim>
				<span onclick="showFrm3(),hideIndexID()"><font color="blue">JSF
				Component with Event and jsf target</font></span>
			</f:verbatim>

			<h:outputText value="4." />
			<f:verbatim>
				<span onclick="showFrm4(),hideIndexID()"><font color="blue">JSF
				Component with Event.</font></span>
			</f:verbatim>

			<h:outputText value="5." />
			<f:verbatim>
				<span onclick="showFrm5(),hideIndexID()"><font color="blue">RichFace
				Components.</font></span>
			</f:verbatim>
		</h:panelGrid>
	</rich:panel>

	<rich:panel id="frm1">
		<h:outputText value="JSF Components:" />
		
		<h:commandButton value="add test" action="#{effect.addHtmlEffect}"></h:commandButton>
		
		<h:panelGrid id="panGrID" columns="2">
			<h:outputText value="Time:" />
			<h:inputText value="#{effect.time}" />

			<f:verbatim>
				<span onclick="hidePanel1(), hideImage1()"><font color="blue">Hide
				Panel</font> </span>
			</f:verbatim>
			<h:graphicImage value="/pics/fatal.gif"
				onclick="hidePanel1(), hideImage1()" />

			<f:verbatim>
				<span onclick="showPanel1(), showImage1()"><font color="blue">Show</font>
				</span>
			</f:verbatim>
			<h:graphicImage value="/pics/warn.gif"
				onclick="showPanel1(), showImage1()" />


			<h:outputText value="Event (onclick):" />

			<f:verbatim></f:verbatim>

			<h:outputText value="No" />
			<h:outputText value="Yes" />

			<h:panelGroup id="form_1a_ID">
				<h:panelGrid id="panel_1_ID" border="1"
					style="background-color:#696969">
					<f:facet name="header">
						<h:outputText value="Panel Header" />
					</f:facet>

					<h:outputText value="Panel Content" />
				</h:panelGrid>
			</h:panelGroup>

			<h:graphicImage id="asusID" value="/pics/asus.jpg" height="100px"
				width="125px" onclick="hideImage1()" />

			<h:panelGroup id="form_1b_ID">
				<h:inputText value="onmouse and onclick">
					<rich:effect binding="#{effect.htmlEffect}" event="onclick" type="Fold"
						params="duration:0.5,from:0.4,to:1.0" />
					<rich:effect event="onmouseout" type="Highlight"
						params="duration:0.5,from:1.0,to:0.4" />
				</h:inputText>
			</h:panelGroup>
		</h:panelGrid>

		<rich:effect for="panel_1_ID" name="hidePanel1" type="Fade"
			params="duration:#{effect.time}" id="effectID"/>
		<rich:effect for="panel_1_ID" name="showPanel1" type="Appear" />

		<rich:effect for="asusID" name="hideImage1" type="Fold"
			params="duration:#{effect.time}" />
		<rich:effect for="asusID" name="showImage1" type="Grow" />

		<f:verbatim>
			<br />
			<span onclick="hideFrm1(),showIndexID()"><font color="blue">Close</font></span>
		</f:verbatim>
	</rich:panel>

	<rich:panel id="frm2">
		<h:outputText
			value="JSF Component with Event and non-jsf target (onclick, onmouseout)" />

		<h:panelGrid columns="2">
			<h:graphicImage id="imageID" value="/pics/podb109_61.jpg" width="100"
				height="50">
				<rich:effect event="onclick" targetId="divID" type="Opacity"
					params="duration:0.5,from:0.4,to:1.0" />
				<rich:effect event="onmouseout" type="Opacity"
					params="targetId:'divID',duration:0.5,from:1.0,to:0.4" />
			</h:graphicImage>

			<f:verbatim>
				<div id="divID"
					style="width: 100px; height: 50px; background-color: red"><rich:effect
					event="onclick" targetId="imageID" type="Opacity"
					params="duration:0.5,from:0.4,to:1.0" /> <rich:effect
					event="onmouseout" type="Opacity"
					params="targetId:'imageID',duration:0.5,from:1.0,to:0.4" /></div>
			</f:verbatim>
		</h:panelGrid>

		<f:verbatim>
			<br />
			<span onclick="hideFrm2(),showIndexID()"><font color="blue">Close</font></span>
		</f:verbatim>
	</rich:panel>

	<rich:panel id="frm3">
		<h:outputText
			value="JSF Component with Event and jsf target (onclick, onmouseout)" />

		<h:panelGrid id="gridID" border="1" style="background-color:green">
			<h:outputText value="Panel Content" />
			<rich:effect event="onclick" targetId="imgID" type="Opacity"
				params="duration:0.5,from:0.4,to:1.0" />
			<rich:effect event="onmouseout" targetId="imgID" type="Opacity"
				params="duration:0.5,from:1.0,to:0.4" />
		</h:panelGrid>

		<h:graphicImage id="imgID" value="/pics/podb109_61.jpg" width="93"
			height="30px">
			<rich:effect event="onmouseout" targetId="gridID" type="Opacity"
				params="duration:0.5,from:0.4,to:1.0" />
			<rich:effect event="onclick" targetId="gridID" type="Opacity"
				params="duration:0.5,from:1.0,to:0.4" />
		</h:graphicImage>

		<f:verbatim>
			<br />
			<span onclick="hideFrm3(),showIndexID()"><font color="blue">Close</font></span>
		</f:verbatim>
	</rich:panel>

	<rich:panel id="frm4">
		<h:outputText value="1. (Event 2)" />
		<h:graphicImage id="img_1_ID" value="/pics/asus.jpg" width="200px"
			height="150px" />
		<f:verbatim>
			<br />
		</f:verbatim>
		<h:outputText value="2. (Hide 1)" />
		<h:graphicImage id="img_2_ID" value="/pics/benq.jpg" width="200px"
			height="150px" />
		<f:verbatim>
			<br />
		</f:verbatim>
		<h:outputText value="3. (Pulsate 3, Show 1)" />
		<h:graphicImage id="img_3_ID" value="/pics/toshiba.jpg" width="200px"
			height="150px">
			<rich:effect event="onclick" type="Pulsate" />
		</h:graphicImage>

		<rich:effect event="onclick" for="img_1_ID" targetId="img_2_ID"
			type="BlindDown" />
		<rich:effect event="onclick" for="img_2_ID" targetId="img_1_ID"
			type="Puff" />
		<rich:effect event="onclick" for="img_3_ID" targetId="img_1_ID"
			type="Grow" params="duration:0.8" />

		<f:verbatim>
			<br />
			<span onclick="hideFrm4(),showIndexID()"><font color="blue">Close</font></span>
		</f:verbatim>
	</rich:panel>

	<rich:panel id="frm5">
		<h:panelGrid id="panelGrdID" columns="2">
			<f:verbatim>
				<span onclick="hideRichPanel()"><font color="blue">Hide
				Panel</font> </span>
			</f:verbatim>
			<h:graphicImage value="/pics/fatal.gif" onclick="hideRichPanel()" />

			<f:verbatim>
				<span onclick="showRichPanel()"><font color="blue">Show
				Panel</font> </span>
			</f:verbatim>
			<h:graphicImage value="/pics/warn.gif" onclick="showRichPanel()" />

			<f:verbatim>
				<span onclick="hideRichTabPanel()"><font color="blue">Hide
				Tab Panel</font> </span>
			</f:verbatim>
			<h:graphicImage value="/pics/fatal.gif" onclick="hideRichTabPanel()" />

			<f:verbatim>
				<span onclick="showRichTabPanel()"><font color="blue">Show
				Tab Panel</font> </span>
			</f:verbatim>
			<h:graphicImage value="/pics/warn.gif" onclick="showRichTabPanel()" />
		</h:panelGrid>

		<rich:panel id="richPanelID">
			<f:facet name="header">
				<h:outputText value="Header of the Panel" />
			</f:facet>
			<f:verbatim>
			    This is a panel. This is a panel. This is a panel. This is a panel. <br />
			    This is a panel. This is a panel. This is a panel. This is a panel. 
		    </f:verbatim>
			<rich:effect event="onclick" type="Opacity"
				params="duration:0.6,from:0.3,to:1.0" />
			<rich:effect event="onmouseout" type="Opacity"
				params="duration:0.6,from:1.0,to:0.3" />
		</rich:panel>

		<rich:tabPanel id="tabPanelID"
			headerAlignment="Header of the tabPanel" height="200px"
			switchType="ajax" rendered="true" title="Title">

			<rich:tab id="tab1" label="label 1">
				<h:outputText value="This is tab panel 1" styleClass="text1"></h:outputText>
			</rich:tab>

			<rich:tab id="tab2" label="label 2">
				<h:outputText value="This is tab panel 2" styleClass="text1"></h:outputText>
			</rich:tab>

			<rich:tab id="tab3" label="label 3">
				<h:outputText value="This is tab panel 3" styleClass="text1"></h:outputText>
			</rich:tab>

			<rich:effect event="onclick" type="Opacity"
				params="duration:0.4,from:1.0,to:0.4" />
			<rich:effect event="onmouseout" type="Opacity"
				params="duration:0.4,from:0.4,to:1.0" />
		</rich:tabPanel>

		<rich:effect for="richPanelID" event="" name="hideRichPanel"
			type="Fade" />
		<rich:effect for="richPanelID" event="" name="showRichPanel"
			type="Appear" />

		<rich:effect for="tabPanelID" event="" name="hideRichTabPanel"
			type="Fade" />
		<rich:effect for="tabPanelID" event="" name="showRichTabPanel"
			type="Appear" />

		<f:verbatim>
			<br />
			<span onclick="hideFrm5(),showIndexID()"><font color="blue">Close</font></span>
		</f:verbatim>
	</rich:panel>


	<rich:effect for="indexID" name="hideIndexID" type="SlideUp" />
	<rich:effect for="indexID" name="showIndexID" type="SlideDown" />

	<rich:effect for="frm1" name="hideFrm1" type="Fade" />
	<rich:effect for="frm1" name="showFrm1" type="Appear" />

	<rich:effect for="frm2" name="hideFrm2" type="Fade" />
	<rich:effect for="frm2" name="showFrm2" type="Appear" />

	<rich:effect for="frm3" name="hideFrm3" type="Fade" />
	<rich:effect for="frm3" name="showFrm3" type="Appear" />

	<rich:effect for="frm4" name="hideFrm4" type="Fade" />
	<rich:effect for="frm4" name="showFrm4" type="Appear" />

	<rich:effect for="frm5" name="hideFrm5" type="Fade" />
	<rich:effect for="frm5" name="showFrm5" type="Appear" />

	<br />
	<br />
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getParams" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('effectID').params}" />
		</rich:column>
	</h:panelGrid>

</f:subview>
