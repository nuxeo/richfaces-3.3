<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/togglePanel" prefix="rich"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/panel" prefix="panel"%>
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
        <rich:togglePanel id="panel2" switchType="ajax" initialState="blank" stateOrder="blank,nikon">
            <f:facet name="blank">
                <panel:panel>
                    <f:facet name="header">
                        <h:panelGroup>
                            <h:outputText value="#{bean.nikon}" style="font-weight: bold;" />
                            <rich:toggleControl id="toggleControl_nikon" for="togglePanel_form:panel2" 
                            ajaxSingle="true" process="my_first" actionListener="#{bean.action}">
                            
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
                            <rich:toggleControl id="toggleControl_nikon_b" for="togglePanel_form:panel2" 
                            ajaxSingle="true" process="my_first" actionListener="#{bean.action}">
                            
                                <h:graphicImage value="/images/collapse.gif" style="border-width: 0px;" />
                                <h:outputText value="#{bean.textValue1}" style="font-weight: bold;" />
                            </rich:toggleControl>
                        </h:panelGroup>
                    </f:facet>
                    <h:panelGrid columns="2" border="0" style="width: 100%;background-color: white;">
                        <h:graphicImage url="/images/Nikon.jpg" alt="" />
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
        <br />
        <br />
        
        <h:inputText value="" id="my_first" required="true"/>
    </h:form>

    <a4j:log />
    <f:verbatim>
        <div id="logConsole" />
    </f:verbatim>
</f:view>
</body>
</html>
