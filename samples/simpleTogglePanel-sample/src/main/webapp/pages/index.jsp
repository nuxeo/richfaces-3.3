<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/simpleTogglePanel" prefix="stp" %>
<html>
<head>
    <title></title>

    <style type="text/css">
    .customHeader {
        color: #000000;
        background-image: url();
        background-color: #32CD32;
    }
    </style>

</head>

<body>
<f:view>
<h:form id="SimpleToglePanel_form">

    <h:selectOneRadio binding="#{skinBean.component}" />
    <h:commandLink action="#{skinBean.change}" value="set skin" />
    <br/><br/>

    <h:outputText value="Simple richfaces simpleTogglePanel test web application."
                  style="font: 18px;font-weight: bold;"/>
    <stp:simpleTogglePanel switchType="client" id="panel1" label="client switchType"
    	width="#{bean.width}" opened="false"  height="#{bean.height}" style="#{bean.style}">
    	<f:facet name="header">
    	          <h:outputText value="client switchType from facet" />
    	</f:facet>
    	<f:facet name="closeMarker">
    	          <h:outputText value="Close It" />
    	</f:facet>
    	<f:facet name="openMarker">
    	          <h:outputText value="Open It" />
    	</f:facet>
        <h:panelGrid columns="2" border="1">
            <h:graphicImage url="/images/Canon.jpg" alt=""/>
            <h:panelGroup>
                <h:outputText style="font: 18px;font-weight: bold;"
                              value="Canon EOS Digital Rebel XT"/>
                <f:verbatim>
                    <br/>
                    8.2 Megapixels - SLR / Large Digital Camera - 1.8 in LCD Screen -
                    Storage: Compact Flash, Compact Flash Type II - Built In Flash
                    <br/>
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
    </stp:simpleTogglePanel>
    <br/><br/>
    
    <h:panelGrid columns="3" border="1">
				
		<h:outputText value="Set panel width:" />
		<h:inputText value="#{bean.width}" size="5" onchange="submit()"/>
		<h:commandButton value="Set!"/>		
				
		<h:outputText value="Set panel height:" />
		<h:inputText value="#{bean.height}" size="5" onchange="submit()"/>
		<h:commandButton value="Set!"/>
		
		<h:outputText value="Set panel style:" />
		<h:inputText value="#{bean.style}" size="50" onchange="submit()"/>
		<h:commandButton value="Set!"/>		
		
	</h:panelGrid>
				
    <br/><br/>
    <stp:simpleTogglePanel opened="false" switchType="ajax" id="panel2" label="ajax switchType">
        <h:panelGrid columns="2" border="1">
            <h:graphicImage url="/images/Nikon.jpg" alt=""/>
            <h:panelGroup>
                <h:outputText style="font: 18px;font-weight: bold;" value="Nikon D70s"/>
                <f:verbatim>
                    <br/>
                    6.1 Megapixels - SLR / Large Digital Camera - 2 in LCD Screen -
                    Storage: Compact Flash, Microdrive Compatible, Compact Flash Type II - Built In
                    Flash
                    <br/>
                    Revolutionize every digital photography experience with the Nikon D70s digital
                    camera. Designed for amateurs and professionals alike, this Nikon digital camera
                    features a high resolution of 6.1 megapixels and a large 2.0'' LCD screen.
                    Offering
                    i-TTL speedlight, 5-point autofocus, and lens compatibility with AF and AF-S
                    Nikkor
                    lenses, this digital SLR camera comes with a rechargeable lithium-ion battery
                    for
                    continual performance. With seven shooting modes, including auto, portrait,
                    night
                    portrait, landscape, night landscape, sports, and close-up, this impressive
                    Nikon
                    digital camera delivers professional quality results with every use.
                </f:verbatim>
            </h:panelGroup>
        </h:panelGrid>

    </stp:simpleTogglePanel>
    <br/><br/>
    <stp:simpleTogglePanel opened="false" switchType="server" id="panel3" label="server switchType">
        <h:panelGrid columns="2" border="1">
            <h:graphicImage url="/images/Olympus.jpg" alt=""/>
            <h:panelGroup>
                <h:outputText style="font: 18px;font-weight: bold;" value="Olympus EVOLT E-500"/>
                <f:verbatim>
                    <br/>
                    8 Megapixels - SLR / Large Digital Camera - 2.5 in LCD Screen -
                    Storage: Compact Flash, xD-Picture Card, Compact Flash Type II - Built In Flash
                    <br/>
                    Perfect for producing elaborate photography from the professional or the
                    beginner,
                    this Olympus digital camera packs tons of features into its compact body.
                    Delivering SLR performance at an affordable price, this digital camera offers a
                    Dust Reduction System to clean photos of unwanted spots. With 8 megapixel
                    resolution
                    and a TruePic TURBO Image Processor, this Olympus EVOLT definitely stands out
                    from
                    competing digital cameras. Compatible with different Olympus Zuiko Digital
                    Specific
                    Lenses, this digital camera boasts a 2.5'' HyperCrystal LCD screen. For a
                    digital
                    photography experience unmatched by any other camera, bring home this Olympus
                    EVOLT E-500.
                </f:verbatim>
            </h:panelGroup>
        </h:panelGrid>

    </stp:simpleTogglePanel>
    <br/><br/>

    <stp:simpleTogglePanel opened="true" switchType="client" id="p1" label="panel i panel" headerClass="customHeader">
        <stp:simpleTogglePanel opened="true" switchType="client" id="p2" label="panel1">
            <stp:simpleTogglePanel opened="true" switchType="client" id="p3" label="panel2">
                <h:outputText style="font: 18px;font-weight: bold;" value="Test"/>
            </stp:simpleTogglePanel>
        </stp:simpleTogglePanel>
    </stp:simpleTogglePanel>
    <h:commandLink value="apply!"/>
</h:form>
    <stp:simpleTogglePanel opened="true" switchType="client" id="p11" label="panel 11 panel" headerClass="customHeader">
        <stp:simpleTogglePanel opened="true" switchType="client" id="p22" label="panel1">
            <h:outputText style="font: 18px;font-weight: bold;" value="Test"/>
            <stp:simpleTogglePanel opened="true" switchType="client" id="p3" label="panel2">
                <h:outputText style="font: 18px;font-weight: bold;" value="Test"/>
            </stp:simpleTogglePanel>
        </stp:simpleTogglePanel>
    </stp:simpleTogglePanel>


    <stp:simpleTogglePanel opened="true" switchType="client" label="Text only">
	<h:outputText value="Just text!" />
    </stp:simpleTogglePanel>

	<h:form>
		<a4j:repeat value="#{bean.items}" var="item" rowKeyVar="rowKey">
			<stp:simpleTogglePanel opened="#{bean.openedData[rowKey]}" switchType="ajax" label="Ajax - #{item}">
				<a4j:commandButton action="#{bean.action}" value="action" />
			</stp:simpleTogglePanel>
		</a4j:repeat>
	</h:form>

	<h:form>
		<a4j:repeat value="#{bean.items}" var="item" rowKeyVar="rowKey">
			<stp:simpleTogglePanel opened="#{bean.clientOpenedData[rowKey]}" switchType="client" label="Client - #{item}">
				<a4j:commandButton action="#{bean.action}" value="action" />
			</stp:simpleTogglePanel>
		</a4j:repeat>

		<h:commandLink value="Submit" />
	</h:form>
	
	<a4j:outputPanel ajaxRendered="true">
		<h:messages />
	</a4j:outputPanel>
	
	<h:form>
		<stp:simpleTogglePanel id="immediatePanel" immediate="true" switchType="ajax" label="Immediate">
			Immediate: <h:inputText value="" required="true" label="Immediate input inside immediate panel" immediate="true" /><br />
			<h:inputText value="" required="true" label="Non-immediate input inside immediate panel" />
		</stp:simpleTogglePanel>
	</h:form>

	<h:form>
		<stp:simpleTogglePanel id="nonImmediatePanel" immediate="false" switchType="ajax" label="Not immediate">
			Immediate: <h:inputText value="" required="true" label="Immediate input" immediate="true" /><br />
			<h:inputText value="" required="true" label="Non-immediate input" />
		</stp:simpleTogglePanel>
	</h:form>

	<h:form>
		<stp:simpleTogglePanel value="#{bean.opened}"
			switchType="ajax" label="Panel">
			Panel
		</stp:simpleTogglePanel>
	</h:form>

	<h:form>
		<stp:simpleTogglePanel opened="#{bean.opened}"
			switchType="ajax" label="Panel">
			Panel
		</stp:simpleTogglePanel>
	</h:form>
</f:view>
</body>
</html>
