<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>

<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/togglePanel" prefix="rich" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panel" prefix="panel" %>
<html>
	<head>
		<title></title>
	</head>
	<body>	
		<f:view>
		
		<h:form>
            <h:selectOneRadio binding="#{skinBean.component}" />
            <h:commandLink action="#{skinBean.change}" value="set skin" />
		</h:form>
		
		<h:outputText value="Simple richfaces togglePanel and toggleControl test web application." style="font: 18px;font-weight: bold;" />
		
			<h:form id="togglePanel_form">
			
				<rich:togglePanel id="panel1" switchType="client" initialState="canon" 
					stateOrder="blank,canon">
					
						<f:facet name="blank">
							<panel:panel>
								<f:facet name="header">
									<h:panelGroup>
										<h:outputText value="#{bean.canon}" style="font-weight: bold;" />
										<rich:toggleControl id="toggleControl_canon" for="togglePanel_form:panel1"  >
											<h:graphicImage url="/images/expand.gif" style="border-width: 0px;" />					
											<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
										</rich:toggleControl>
									</h:panelGroup>
								</f:facet>
							</panel:panel>			
						</f:facet>
						
						<f:facet name="canon">
							<panel:panel>
								<f:facet name="header">							
									<h:panelGroup>
										<h:outputText value="#{bean.canon}" style="font-weight: bold;" />
										<rich:toggleControl id="toggleControl_canon_b" for="togglePanel_form:panel1" >
											<h:graphicImage value="/images/collapse.gif" style="border-width: 0px;" />
											<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
										</rich:toggleControl>															
									</h:panelGroup>
								</f:facet>
								<h:panelGrid columns="2" border="0">
									<h:graphicImage url="/images/Canon.jpg" alt=""/>
									<h:panelGroup>
										<h:outputText style="font: 18px;font-weight: bold;" value="Canon EOS Digital Rebel XT" />										
											<f:verbatim>
												<br />
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
										</h:panelGroup>
								</h:panelGrid>							
							</panel:panel>
						</f:facet>						
				</rich:togglePanel>	
				<br /><br />
				
				<rich:togglePanel id="panel2" switchType="ajax" initialState="blank" 
					stateOrder="blank,nikon">
						<f:facet name="blank">
							<panel:panel>
								<f:facet name="header">
									<h:panelGroup>
										<h:outputText value="#{bean.nikon}" style="font-weight: bold;" />
										<rich:toggleControl id="toggleControl_nikon" for="togglePanel_form:panel2"  >
											<h:graphicImage url="/images/expand.gif" style="border-width: 0px;" />					
											<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
										</rich:toggleControl>
									</h:panelGroup>	
								</f:facet>
							</panel:panel>											
						</f:facet>
						
						<f:facet name="nikon">
							<panel:panel>
								<f:facet name="header">	
									<h:panelGroup>
										<h:outputText value="#{bean.nikon}" style="font-weight: bold;" />
										<rich:toggleControl id="toggleControl_nikon_b" for="togglePanel_form:panel2" >
											<h:graphicImage value="/images/collapse.gif" style="border-width: 0px;" />
											<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
										</rich:toggleControl>															
									</h:panelGroup>
								</f:facet>								
								<h:panelGrid columns="2" border="0" style="width: 100%;background-color: white;">
									<h:graphicImage url="/images/Nikon.jpg" alt=""/>
									<h:panelGroup>
										<h:outputText style="font: 18px;font-weight: bold;" value="Nikon D70s" />										
											<f:verbatim>
												<br />
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
										</h:panelGroup>
								</h:panelGrid>
							</panel:panel>
						</f:facet>						
				</rich:togglePanel>	
				<br /><br />
				
				<rich:togglePanel id="panel3" switchType="server" initialState="blank" 
					stateOrder="blank,olympus">
						<f:facet name="blank">
							<panel:panel>
								<f:facet name="header">
									<h:panelGroup>
										<h:outputText value="#{bean.olympus}" style="font-weight: bold;" />
										<rich:toggleControl id="toggleControl_olympus" for="togglePanel_form:panel3" >
											<h:graphicImage url="/images/expand.gif" style="border-width: 0px;" />					
											<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
										</rich:toggleControl>
									</h:panelGroup>
								</f:facet>
							</panel:panel>			
						</f:facet>
						
						<f:facet name="olympus">
							<panel:panel>
								<f:facet name="header">
									<h:panelGroup>
										<h:outputText value="#{bean.olympus}" style="font-weight: bold;" />
										<rich:toggleControl id="toggleControl_olympus_b" for="togglePanel_form:panel3" >
											<h:graphicImage value="/images/collapse.gif" style="border-width: 0px;" />
											<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
										</rich:toggleControl>															
									</h:panelGroup>
								</f:facet>
								<h:panelGrid columns="2" border="0" style="width: 100%;background-color: white;">
									<h:graphicImage url="/images/Olympus.jpg" alt=""/>
									<h:panelGroup>
										<h:outputText style="font: 18px;font-weight: bold;" value="Olympus EVOLT E-500" />										
											<f:verbatim>
												<br />
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
							</panel:panel>
						</f:facet>						
				</rich:togglePanel>	
				<br /><br />
				
				<rich:togglePanel id="panel4" switchType="#{bean.switchType}" initialState="#{bean.initialState}" 
					stateOrder="#{bean.stateOrder}">			
					<f:facet name="canon">
						<panel:panel>
							<f:facet name="header">
								<h:panelGroup>
									<h:outputText value="Customizable toggle panel (initialState=olympus)" style="font-weight: bold;" />
									<rich:toggleControl id="toggleControl_panel4_2" for="togglePanel_form:panel4">
										<h:graphicImage url="/images/expand.gif" style="border-width: 0px;" />					
										<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
									</rich:toggleControl>
								</h:panelGroup>
							</f:facet>							
								<h:panelGrid columns="2" border="0" style="width: 100%;background-color: white;">
									<h:graphicImage url="/images/Canon.jpg" alt=""/>
									<h:panelGroup>
										<h:outputText style="font: 18px;font-weight: bold;" value="Canon EOS Digital Rebel XT" />										
											<f:verbatim>
												<br />
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
										</h:panelGroup>
								</h:panelGrid>
							</panel:panel>
						</f:facet>
						
					<f:facet name="nikon">
						<panel:panel>
							<f:facet name="header">
								<h:panelGroup>
									<h:outputText value="Customizable toggle panel" style="font-weight: bold;" />
									<rich:toggleControl id="toggleControl_panel4_3" for="togglePanel_form:panel4">
										<h:graphicImage url="/images/expand.gif" style="border-width: 0px;" />					
										<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
									</rich:toggleControl>
								</h:panelGroup>
							</f:facet>							
								<h:panelGrid columns="2" border="0">
									<h:graphicImage url="/images/Nikon.jpg" alt=""/>
									<h:panelGroup>
										<h:outputText style="font: 18px;font-weight: bold;" value="Nikon D70s" />										
											<f:verbatim>
												<br />
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
										</h:panelGroup>
								</h:panelGrid>
							</panel:panel>
						</f:facet>
						
					<f:facet name="olympus">
						<panel:panel>
							<f:facet name="header">
								<h:panelGroup>
									<h:outputText value="Customizable toggle panel" style="font-weight: bold;" />
									<rich:toggleControl id="toggleControl_panel4_4" for="togglePanel_form:panel4">
										<h:graphicImage url="/images/expand.gif" style="border-width: 0px;" />					
										<h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
									</rich:toggleControl>								
								</h:panelGroup>
							</f:facet>
								<h:panelGrid columns="2" border="0">
									<h:graphicImage url="/images/Olympus.jpg" alt=""/>
									<h:panelGroup>
										<h:outputText style="font: 18px;font-weight: bold;" value="Olympus EVOLT E-500" />										
											<f:verbatim>
												<br />
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
							</panel:panel>
						</f:facet>					
				</rich:togglePanel>	
				<br /><br />
			
			<panel:panel>
				<f:facet name="header">	
					<h:outputText value="Options for last toggle panel." style="font: 18px;font-weight: bold;" />
				</f:facet>
				<h:panelGrid columns="2" border="0">								
					<h:outputText value="Select switch type" style="font-weight: bold;" />
					<h:selectOneRadio id="switchType_radio1" value="#{bean.switchType}" onclick="submit()">									
						<f:selectItem itemLabel="client" itemValue="client" />									
						<f:selectItem itemLabel="server" itemValue="server" />
						<f:selectItem itemLabel="ajax" itemValue="ajax" />									
					</h:selectOneRadio>					
					<h:outputText value="Select state order" style="font-weight: bold;" />
					<h:selectOneRadio id="stateOrder_radio1" value="#{bean.stateOrder}" onclick="submit()">									
						<f:selectItem itemLabel="straight" itemValue="canon,nikon,olympus" />									
						<f:selectItem itemLabel="reverse" itemValue="olympus,nikon,canon" />																		
					</h:selectOneRadio>
					<h:commandButton value="Submit"/>
					<h:outputText value="#{bean.beanState}"/>
				</h:panelGrid>	
			</panel:panel>
						
			</h:form>
			
			<a4j:log />
			<f:verbatim>
				<div id="logConsole" />
			</f:verbatim>
		</f:view>
	</body>	
</html>  
