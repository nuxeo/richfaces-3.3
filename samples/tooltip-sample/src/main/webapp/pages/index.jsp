<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%--@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"--%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tooltip" prefix="rich"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/componentControl" prefix="cc"%>

<html>
	<head>
		<title></title>
		<style>
        	.abs_div {
        		position: absolute;
        		top: 250px;
        		left: 200px;
        		width: 200px;
        		height: 200px;
        		overflow: auto;
        		color: gray;
        		border: 1px solid red;
        	}
        	
        	.inner_div {
        		display: block;
        		width: 400px;
        		height: 100px;
        		top: 50px;
        		left: 0px;
        		border: 1px solid green;
        	}
    </style>
	</head>
	<body>
		<f:view>
			<h:form>
				<h:commandLink action="demo">demo.jsf</h:commandLink>
			</h:form>
			<h:form>
				<h:selectOneRadio binding="#{skinBean.component}" />
				<h:commandLink action="#{skinBean.change}" value="set skin" />
				<f:verbatim>
					<br/><br/>
					<div style="position:relative; top:100px; left:200px;">
        		</f:verbatim>
        
                <h:panelGroup layout="block" id="asdasdasda"
                    style="width:50px; height:50px;background-color:red;">
                    <rich:toolTip showEvent="onclick" direction="bottom-left"
                        styleClass="tooltip" layout="block">
                        <f:facet name="defaultContent">
                            <strong>Wait...</strong>
                        </f:facet>
                        <span style="white-space: nowrap">This tool-tip
                        content was <strong>rendered on server</strong><br />
                        </span>
                        <h:panelGrid columns="2">
                            <h:outputText style="white-space:nowrap"
                                value="tooltips requested:" />
                            <h:outputText value="Test Test Test"
                                styleClass="tooltipData" />
                        </h:panelGrid>
                    </rich:toolTip>
                </h:panelGroup>
        
                <h:commandButton value="Tooltip with followMouse mode" id="btn">
					<rich:toolTip id="tt" followMouse="true" direction="top-right" mode="ajax" value="#{bean.toolTipContent}" horizontalOffset="5" verticalOffset="5" layout="block">
						<f:facet name="defaultContent"><f:verbatim>DEFAULT CONTENT</f:verbatim>
						</f:facet>
						<h:outputText id="ot" value="#{bean.toolTipContent}"></h:outputText>
						<h:commandLink value="You can click here..."></h:commandLink>
					</rich:toolTip>
				</h:commandButton>
				<f:verbatim>
					</div>
				</f:verbatim>
				<h:commandLink value="Simple Link" id="link">
					<rich:toolTip id="toolTipForLink"
							followMouse="false"
							direction="top-right"
							mode="ajax"
							value="#{bean.toolTipContent}"
							horizontalOffset="5"
							verticalOffset="5"
							layout="block"
							showEvent="oncontextmenu" >
						<f:facet name="defaultContent"><f:verbatim>DEFAULT LINK CONTENT</f:verbatim>
						</f:facet>
						<h:outputText id="outText" value="#{bean.toolTipContent}"></h:outputText>
					</rich:toolTip>
				
				</h:commandLink>
				<h:outputText id="text" value="Text with tooltip">
					<rich:toolTip id="toolTipForText" followMouse="false" direction="top-right" mode="ajax" value="#{bean.toolTipContent}" horizontalOffset="5" verticalOffset="5" layout="inline">
					</rich:toolTip>				
				</h:outputText>
				<%-- h:commandButton value="Tooltip with followMouse mode" id="btn">
					<tt:toolTip value="Tooltip text" followMouse="false" onclick="alert('onlcick');" direction="bottom-left"></tt:toolTip>
				</h:commandButton--%>
				
				<%--a4j:log popup="false" level="ALL"></a4j:log--%>
				
				<a4j:outputPanel layout="block" styleClass="abs_div" >
					<a4j:outputPanel layout="block" styleClass="inner_div" >
						<rich:toolTip id="abs_t" followMouse="true" value="" 
								layout="block" horizontalOffset="0" verticalOffset="0"
								direction="top-right" >
							<f:facet name="defaultContent">
								<f:verbatim>DEFAULT CONTENT</f:verbatim>
							</f:facet>
							<h:outputText value="Tooltip on scrolled absolutly positioned element." />
						</rich:toolTip>
					</a4j:outputPanel>
				</a4j:outputPanel>
				
				<h:outputText id="text1" value="Text with tooltip">
					<rich:toolTip id="controlledTooltip" followMouse="false" direction="top-right" value="#{bean.toolTipContent}" horizontalOffset="5" verticalOffset="5" layout="inline">
					</rich:toolTip>				
				</h:outputText>

				<f:verbatim>
					<br />
					<span id="controlInput">
					Mouse down here
					</span>
				</f:verbatim>

				<cc:componentControl event="mousedown" operation="show" for="controlledTooltip" attachTo="controlInput" />
				<cc:componentControl event="mouseup" operation="hide" for="controlledTooltip" attachTo="controlInput" />
				
				<f:verbatim><br /></f:verbatim>
			
				<h:inputText id="input1">
					<rich:toolTip showEvent="focus" direction="top-right" horizontalOffset="20" verticalOffset="10">
						<h:outputText value="Tooltip" />
					</rich:toolTip>
				</h:inputText>
			
				<f:verbatim><br /></f:verbatim>

				<h:inputText id="input2">
					<rich:toolTip showEvent="onfocus" direction="top-right" horizontalOffset="20" verticalOffset="10">
						<h:outputText value="Tooltip 2" />
					</rich:toolTip>
				</h:inputText>
				
				<f:verbatim>
					<br /><br />
				</f:verbatim>
				
				<h:outputText id="rf_2051_text1" value="Text with tooltip">
					<rich:toolTip id="rf_2051_controlledTooltip" followMouse="false" direction="top-right"
						attached="false" value="#{bean.toolTipContent}" horizontalOffset="5"
						verticalOffset="5" layout="inline">
					</rich:toolTip>				
				</h:outputText>

				<f:verbatim>
					<br /><br />
				</f:verbatim>

				<h:outputText id="rf_2051_text2" value="Another text with tooltip">
				</h:outputText>

				<f:verbatim>
					<br /><br />
					<span id="rf_2051_controlInput">Mouse down here</span>
				</f:verbatim>

				<cc:componentControl event="mouseover" operation="show" for="rf_2051_controlledTooltip" attachTo="rf_2051_text2" />
				<cc:componentControl event="mouseout" operation="hide" for="rf_2051_controlledTooltip" attachTo="rf_2051_text2" />
			</h:form>
			<h:panelGroup style="width: 1000px; height: 10px;" layout="block"></h:panelGroup>
		</f:view>
	</body>	
</html>  
