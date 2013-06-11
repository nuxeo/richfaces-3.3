<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tabPanel" prefix="tabs" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panel" prefix="panel" %>
<html>
	<head>
		<title></title>
		<style type="text/css">
			.italic {
				font-style: italic;
			}
			
			.tabClass { 
				font-size: 16px; 
				font-family: fantasy; 
				color:red; 
			}
			
			.rich-tab-active {
				font-size: 16px; 
				font-family: fantasy; 
				color:purple;
			}
			
			.rich-tab-inactive {
				font-size: 14px; 
				font-family: fantasy; 
				color:green;
			}
			
			.rich-tab-disabled {
				font-size: 12px; 
				font-family: fantasy; 
				color:gold;
			}
			
			.rich-tabhdr-cell-inactive, .rich-tabhdr-cell-disabled {
				padding-bottom: 2px;
			}
			
			.rich-tabpanel-content-position {
				top: -2px;
			}
			
			.rich-tabpanel-content {
				border-width: 2px; 
				border-color:red;
			}
		</style>
	</head>
	<body bgcolor="white">
		<f:view>
		
		<h:form>
            <h:selectOneRadio binding="#{skinBean.component}" />
            <h:commandLink action="#{skinBean.change}" value="set skin" />
		</h:form>
		
			<a4j:outputPanel ajaxRendered="true">
				<h:messages />
			</a4j:outputPanel>
			
			<h:form>
				<h:selectOneMenu value="#{bean.tabPanel.value}">
					<f:selectItem itemLabel="canon" itemValue="canon" />
					<f:selectItem itemLabel="olympus" itemValue="olympus" />
					<f:selectItem itemLabel="nikon" itemValue="nikon" />
				</h:selectOneMenu>
				
				<h:commandLink value="apply" />
			</h:form>
			
				<tabs:tabPanel headerSpacing="5px" immediate="false" tabClass="tabClass" activeTabClass="italic" width="100%" switchType="client" binding="#{bean.tabPanel}" value="#{bean.currentTab}" id="tab_panel">
					<tabs:tab disabled="#{bean.disabledTabName == 'canon'}" name="canon" label="Canon" switchType="server">
							  <panel:panel>
							  <f:facet name="header">
								<h:outputText value="Canon EOS Digital Rebel XT" />
							  </f:facet>
   							<h:graphicImage value="/pages/Canon_EOS_Digital_Rebel_XT.jpg" alt=""/>
   						      <h:form>
								<f:verbatim>
									8.2 Megapixels - SLR / Large Digital Camera - 1.8 in LCD Screen - 
									Storage: Compact Flash, Compact Flash Type II - Built In Flash 
									<br />
									Achieve the same professional results as film cameras in a flexible, 
									digital format with the Canon EOS Digital Rebel XT. The EOS digital 
									Rebel XT flawlessly combines ease of use with unequalled SLR performance. 
									This compact digital camera features not only lightweight design and 
									compatibility with over 50 EF lenses, but also an 8.0 megapixel CMOS 
									sensor and DIGIC II image processor. Boasting a 7-point wide area AF 
									system and one battery pack, this compact camera comes with digital storage 
									media (CF card Type I and II). Take advantage of the 10 second self-timer 
									delay offered by this Canon camera. Correct the color of any image with these 
									white balance settings: auto, preset, daylight, shad, cloudy, twilight, sunset, 
									Tungsten light, White fluorescent light, flash, and custom. With USB 2.0 
									connector and PictBridge printer compatibility, this digital camera also 
									features a 1,8'' TFT color monitor. Enjoy the ease of use, affordability, 
									and powerful performance of the Canon EOS Digital Rebel XT.
								</f:verbatim>
								</h:form>
								</panel:panel>
					</tabs:tab>
					<tabs:tab disabled="#{bean.disabledTabName == 'nikon'}" name="nikon" label="Nikon" labelWidth="200px">
						<h:form>
							<h:graphicImage value="/pages/Nikon_D70s.jpg" alt=""/>
								<h:outputText value="Nikon D70s" />
								<h:inputText value="#{bean.value2}" required="true" />
								<f:verbatim>
									6.1 Megapixels - SLR / Large Digital Camera - 2 in LCD Screen - 
									Storage: Compact Flash, Microdrive Compatible, Compact Flash Type II - Built In Flash 
									<br />
									Revolutionize every digital photography experience with the Nikon D70s digital 
									camera. Designed for amateurs and professionals alike, this Nikon digital camera 
									features a high resolution of 6.1 megapixels and a large 2.0'' LCD screen. Offering 
									i-TTL speedlight, 5-point autofocus, and lens compatibility with AF and AF-S Nikkor 
									lenses, this digital SLR camera comes with a rechargeable lithium-ion battery for 
									continual performance. With seven shooting modes, including auto, portrait, night 
									portrait, landscape, night landscape, sports, and close-up, this impressive Nikon 
									digital camera delivers professional quality results with every use. 
								</f:verbatim>
							</h:form>
					</tabs:tab>
					<tabs:tab disabled="#{bean.disabledTabName == 'olympus'}" name="olympus" label="Olympus Olympus" labelWidth="60px">
						<h:panelGrid columns="2" width="100%">
							<h:graphicImage value="/pages/Olympus_EVOLT_E-500.jpg" alt=""/>
							<h:panelGroup>
								<h:outputText value="Olympus EVOLT E-500" />
								<f:verbatim>
									8 Megapixels - SLR / Large Digital Camera - 2.5 in LCD Screen - 
									Storage: Compact Flash, xD-Picture Card, Compact Flash Type II - Built In Flash 
									<br />
									Perfect for producing elaborate photography from the professional or the beginner, 
									this Olympus digital camera packs tons of features into its compact body. 
									Delivering SLR performance at an affordable price, this digital camera offers a 
									Dust Reduction System to clean photos of unwanted spots. With 8 megapixel resolution 
									and a TruePic TURBO Image Processor, this Olympus EVOLT definitely stands out from 
									competing digital cameras. Compatible with different Olympus Zuiko Digital Specific 
									Lenses, this digital camera boasts a 2.5'' HyperCrystal LCD screen. For a digital 
									photography experience unmatched by any other camera, bring home this Olympus 
									EVOLT E-500. 
								</f:verbatim>
							</h:panelGroup>
						</h:panelGrid>	
					</tabs:tab>
					<tabs:tab disabled="true" name="disabled" label="Disabled">
					</tabs:tab>
				</tabs:tabPanel>
				<br /><br />
		</f:view>
	</body>	
</html>  
