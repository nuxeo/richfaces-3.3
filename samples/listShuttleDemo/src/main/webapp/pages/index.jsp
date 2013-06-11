<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dataTable" prefix="rich" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/listShuttle" prefix="ls" %>

<html>
	<head>
		<title></title>

		<style type="text/css">
			.zebraCell1 {
				background-color: yellow;
			}
			
			.zebraCell2 {
				background-color: fuchsia;			
			}

			.zebraRow1 * {
				color: white;
			}
			
			.zebraRow2 * {
				color: navy;
			}
			.downControlClass {
				font-weight: bold;
			}
		</style>
	</head>
	<body>
		<f:view>
			<a4j:outputPanel ajaxRendered="true">
				<h:messages />
			</a4j:outputPanel>

			<h:form id="skinForm" >
				<h:selectOneRadio binding="#{skinBean.component}" />
				<h:commandLink action="#{skinBean.change}" value="set skin" />
				<h:outputText value=" Current skin: #{skinBean.skin}" /><br />
			</h:form>
			<h:form id="form" >
				<ls:listShuttle id="listShuttle" var="item" sourceValue="#{listShuttleDemoBean.source}" targetValue="#{listShuttleDemoBean.target}"
					orderControlsVisible="#{listShuttleDemoBean.orderControlsVisible}"
					fastOrderControlsVisible="#{listShuttleDemoBean.fastOrderControlsVisible}"
					moveControlsVisible="#{listShuttleDemoBean.moveControlsVisible}"
					fastMoveControlsVisible="#{listShuttleDemoBean.fastMoveControlsVisible}"
					converter="#{converter}"
					onorderchanged="orderChanged(event)"
					onorderchange="return false;"
					onlistchange="return false;"
				
					sourceSelection="#{listShuttleDemoBean.sourceSelection}"
					targetSelection="#{listShuttleDemoBean.targetSelection}"
					switchByClick="#{listShuttleDemoBean.switchByClick}"
					switchByDblClick="#{listShuttleDemoBean.switchByDblClick}"
          			
                    bottomControlLabel="test bottomControlLabel"
                    bottomTitle="test bottomTitle"
                    
                    topControlLabel="test topControlLabel"
                    topTitle="test topTitle"
                    
                    upControlLabel="test upControlLabel"
                    upTitle="test upTitle"
                    
                    downControlLabel="test downControlLabel"
                    downTitle="test downTitle"
                    >
                    
					<h:column><h:outputText value="#{item.name}" /></h:column>
					<h:column><h:outputText value="#{item.price}" /></h:column>
					<h:column>
						<f:facet name="header">
							<h:outputText value="Action Links" />
						</f:facet>
						<h:commandLink value="Server Action" action="#{item.action}" />

						<h:outputText binding="#{listShuttleDemoRequestBean.eventsBouncer}" />
					</h:column>
				</ls:listShuttle>
			
				<f:verbatim>
					<div id="cdiv" style="width: 600px; height: 200px; overflow: auto;"></div>
	
					<script>
						function orderChanged(event) {
							var cdiv = $('cdiv');
							Element.clearChildren(cdiv);
							cdiv.appendChild(document.createTextNode(Object.inspect($H(event))));
						}
					</script>
				</f:verbatim>
				
				<h:panelGrid columns="4">
					<h:outputText value="Order controls visible:" />
					<h:selectBooleanCheckbox value="#{listShuttleDemoBean.orderControlsVisible}" />

					<h:outputText value="Fast order controls visible:" />
					<h:selectBooleanCheckbox value="#{listShuttleDemoBean.fastOrderControlsVisible}" />

					<h:outputText value="Move controls visible:" />
					<h:selectBooleanCheckbox value="#{listShuttleDemoBean.moveControlsVisible}" />

					<h:outputText value="Fast move controls visible:" />
					<h:selectBooleanCheckbox value="#{listShuttleDemoBean.fastMoveControlsVisible}" />

					<h:outputText value="Switch by click:" />
					<h:selectBooleanCheckbox value="#{listShuttleDemoBean.switchByClick}" />
					
					<h:outputText value="Switch by double click:" />
					<h:selectBooleanCheckbox value="#{listShuttleDemoBean.switchByDblClick}" />

				</h:panelGrid>

				<a4j:status startText="...start..." />
				<a4j:commandButton value="Ajax Submit" reRender="listShuttle" />
			
				<h:commandButton value="Submit" />
				<h:commandButton value="Submit Immediate" immediate="true" />
				<h:commandButton value="Start over" action="#{listShuttleDemoBean.startOver}" immediate="true" rendered="false"/>


				<ls:listShuttle onlistchanged="alert(event.type)" id="listShuttle2" var="item" sourceValue="#{listShuttleDemoBean.zebraItems}" 
						columnClasses="zebraCell1, zebraCell2" rowClasses="zebraRow1, zebraRow2" downControlClass="downControlClass"
						switchByClick="#{listShuttleDemoBean.switchByClick}" switchByDblClick="#{listShuttleDemoBean.switchByDblClick}"						>
					<rich:column width="10px">
						<f:facet name="header">
							<h:outputText value="Name" />
						</f:facet>
						<h:outputText value="#{item}" />
					</rich:column>
					<rich:column width="10px">
						<f:facet name="header">
							<h:outputText value="Name" />
						</f:facet>
						<h:outputText value="#{item}" />
					</rich:column>
					<rich:column width="10px">
						<f:facet name="header">
							<h:outputText value="Name" />
						</f:facet>
						<h:outputText value="#{item}" />
					</rich:column>
					<h:column>
						<f:facet name="header">
							<h:outputText value="Name" />
						</f:facet>
						<h:outputText value="#{item}" />
					</h:column>
				</ls:listShuttle>
			</h:form>			
		</f:view>
	</body>	
</html>  
