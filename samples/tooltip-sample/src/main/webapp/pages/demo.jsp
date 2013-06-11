<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%--@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j"--%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/tooltip" prefix="rich"%>

<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<h:form>
				<h:commandLink action="index">index.jsf</h:commandLink>
			</h:form>
	<h:panelGrid columns="2">
		<a4j:outputPanel layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<f:verbatim>
				<p>
					Here you can see <b>default client-side</b> tool-tip
				</p>
			</f:verbatim>
		
		<rich:toolTip>
			<f:verbatim>
				<span style="white-space:nowrap">
					This tool-tip content was <strong>pre-rendered</strong> to the page.<br/>
					The look of this tool-tip is 100% defined by skin.
				</span>
			</f:verbatim>
		</rich:toolTip>
		</a4j:outputPanel>
		
		<a4j:outputPanel layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<f:verbatim>
				<p>
					This tool-tip will <b>follow mouse</b>. Also this tool-tip has a <b>delay 0.5 sec</b> and <b>hide delay 0.5 sec</b>, so be patient!
				</p>
			</f:verbatim>
		
		<rich:toolTip followMouse="true" direction="top-right" showDelay="500" hideDelay="500" styleClass="tooltip" style="width:250px">
			<f:verbatim>
				<span>
					This tool-tip content also <strong>pre-rendered</strong> to the page.
					However, the look of this tool-tip is customized by styleClass attribute.
				</span>
			</f:verbatim>
		</rich:toolTip>
		</a4j:outputPanel>
	<h:form>
		<a4j:outputPanel layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<f:verbatim>
				<p>
					This tool-tip rendered on server <b>in separate request</b>.
				</p>
			</f:verbatim>
		
		<rich:toolTip direction="top-right" mode="ajax" styleClass="tooltip" layout="block">
			<f:facet name="defaultContent">
				<f:verbatim>
					<strong>Wait...</strong>
				</f:verbatim>
			</f:facet>
			<f:verbatim>
				<span >This tool-tip content was <strong>rendered on server</strong> </span>
			</f:verbatim>
			<h:panelGrid columns="2">
				<h:outputText value="tooltips requested:" />
				<h:outputText value="#{bean.counter}" styleClass="tooltipData" />
			</h:panelGrid>
		</rich:toolTip>
		</a4j:outputPanel>
	</h:form>
	<h:form>
		<a4j:outputPanel layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<f:verbatim>
				<p>
					This tool-tip will be <b>activated on mouse click</b>. It also has a <b>bottom-left</b> position.
				</p>
			</f:verbatim>
		
		<rich:toolTip event="onclick" direction="bottom-left" mode="ajax" styleClass="tooltip" layout="block">
			<f:facet name="defaultContent">
				<f:verbatim>
					<strong>Wait...</strong>
				</f:verbatim>
			</f:facet>
			<f:verbatim>
				<span >This tool-tip content was <strong>rendered on server</strong> </span>
			</f:verbatim>
			<h:panelGrid columns="2">
				<h:outputText value="tooltips requested:" />
				<h:outputText value="#{bean.counter}" styleClass="tooltipData" />
			</h:panelGrid>
		</rich:toolTip>
		</a4j:outputPanel>
	</h:form>
	<h:form>
		<f:verbatim>
			<div id="justdiv" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
				Simple HTML DIV
			</div>
		</f:verbatim>
		
		<rich:toolTip for="justdiv">
			<f:verbatim>
				<span style="white-space:nowrap">
					This tool-tip content was <strong>pre-rendered</strong> to the page.<br/>
					The look of this tool-tip is 100% defined by skin.
				</span>
			</f:verbatim>
		</rich:toolTip>
	</h:form>
	<h:panelGroup>
		<a4j:outputPanel id="forComponent" layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<f:verbatim>
				<p>
					For pointed to JSF component
				</p>
			</f:verbatim>
		</a4j:outputPanel>
		<a4j:outputPanel layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<rich:toolTip for="forComponent">
				<f:verbatim>
					<span style="white-space:nowrap">
						This tool-tip content was <strong>pre-rendered</strong> to the page.<br/>
						The look of this tool-tip is 100% defined by skin.
					</span>
				</f:verbatim>
			</rich:toolTip>

			<f:verbatim>
				<span style="white-space:nowrap">
					No tooltip here!
				</span>
			</f:verbatim>
		</a4j:outputPanel>
	</h:panelGroup>
</h:panelGrid>

	<h:form>
		<a4j:outputPanel layout="block" style="width:350px;height:75px;cursor:arrow;border-width:2px;text-align:center;border:2px solid red">
			<f:verbatim>
				<p>
					This tool-tip causes problems in richfaces-demo.
				</p>
			</f:verbatim>
		
		<rich:toolTip showDelay="2000" direction="top-right" mode="ajax" styleClass="tooltip" layout="block">
			<f:facet name="defaultContent">
				<f:verbatim>
					<strong>Wait...</strong>
				</f:verbatim>
			</f:facet>
			<f:verbatim>
				<span >This tool-tip content was <strong>rendered on server</strong> </span>
			</f:verbatim>
			<h:panelGrid columns="2">
				<h:outputText value="tooltips requested:" />
				<h:outputText value="#{bean.counter}" styleClass="tooltipData" />
			</h:panelGrid>
		</rich:toolTip>
		</a4j:outputPanel>
	</h:form>
			
			
			
		</f:view>
	</body>	
</html>  

