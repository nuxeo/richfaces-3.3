<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<!-- DOCTYPE html 
     PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" -->
     
<!-- !doctype html public "-//w3c//dtd html 4.0 transitional//en"-->

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/modal-panel" prefix="mp" %>

<html>
	<head>
		<title></title>
		
		<style type="text/css">
			.pointerCursor {
				cursor: pointer;
			}
		</style>
	</head>
	<body style="text-align: center;">
		<f:view>
			<h:form id="_form">
			
			<h:selectOneMenu>
				<f:selectItem itemLabel="item1" itemValue="item1" />
				<f:selectItem itemLabel="item2" itemValue="item2" />
				<f:selectItem itemLabel="item3" itemValue="item3" />
			</h:selectOneMenu>

			<h:selectOneMenu disabled="true" style="filter: alpha(opacity=50); background-color: #d0d0d0;">
				<f:selectItem itemLabel="item1" itemValue="item1" />
				<f:selectItem itemLabel="item2" itemValue="item2" />
				<f:selectItem itemLabel="item3" itemValue="item3" />
			</h:selectOneMenu>

			<a4j:outputPanel ajaxRendered="true">
	            
	            <h:selectOneRadio binding="#{skinBean.component}" />
    	        <h:commandLink action="#{skinBean.change}" value="set skin" />

				<h:panelGrid columns="3" cellspacing="20">
					<h:selectBooleanCheckbox value="#{bean.containerRendered}">
						<f:verbatim>
							containerRendered
						</f:verbatim>
						<a4j:support event="onclick" />
					</h:selectBooleanCheckbox>

					<h:selectBooleanCheckbox value="#{bean.resizeable}">
						<f:verbatim>
							resizeable
						</f:verbatim>
						<a4j:support event="onclick" />
					</h:selectBooleanCheckbox>
	
					<h:selectBooleanCheckbox value="#{bean.moveable}">
						<f:verbatim>
							moveable
						</f:verbatim>
						<a4j:support event="onclick" />
					</h:selectBooleanCheckbox>
				</h:panelGrid>

				<f:verbatim>
					<a href="#" onclick="Element.hide(this);Element.show('a_off');$('fatDiv').style.width = '2000px';$('fatDiv').style.height = '2000px';" id="a_on">scroll ON</a>
					<a href="#" onclick="Element.hide(this);Element.show('a_on');$('fatDiv').style.width = '20px';$('fatDiv').style.height = '20px';" style="display: none;" id="a_off">scroll OFF</a>
				</f:verbatim>
				
				
				<h:panelGrid rendered="#{bean.containerRendered}" columns="1" style="position: relative; top: 550px; left: 100px;">
					<mp:modalPanel style="border: 1px solid navy;" resizeable="#{bean.resizeable}" moveable="#{bean.moveable}" minHeight="100" top="10" width="450" id="_panel" controlsClass="pointerCursor">
						<f:facet name="header">
							<f:verbatim>
								Header
							</f:verbatim>
						</f:facet>
						
						<f:facet name="controls">
							<h:graphicImage value="/images/ico_close.gif" onclick="Richfaces.hideModalPanel('_panel');"/>
						</f:facet>
		
						<f:verbatim>
							Modal panel is here!
						</f:verbatim>

						<h:inputText value="name"/>
		
						<h:selectOneMenu>
							<f:selectItem itemLabel="item1" itemValue="item1" />
							<f:selectItem itemLabel="item2" itemValue="item2" />
							<f:selectItem itemLabel="item3" itemValue="item3" />
						</h:selectOneMenu>
		
						<h:selectOneMenu disabled="true" style="filter: alpha(opacity=50); background-color: #d0d0d0;">
							<f:selectItem itemLabel="item1" itemValue="item1" />
							<f:selectItem itemLabel="item2" itemValue="item2" />
							<f:selectItem itemLabel="item3" itemValue="item3" />
						</h:selectOneMenu>

						<f:verbatim>
							<a href="javascript:Richfaces.showModalPanel('_form:_panel2');">Show 2nd panel</a>
						</f:verbatim>

						<mp:modalPanel height="200" top="100" width="450" id="_panel2" controlsClass="pointerCursor" zindex="500">
							<f:facet name="header">
								<f:verbatim>
									Header
								</f:verbatim>
							</f:facet>
							
							<f:facet name="controls">
								<h:graphicImage value="/images/ico_close.gif" onclick="Richfaces.hideModalPanel('_form:_panel2');"/>
							</f:facet>
			
							<h:panelGrid columns="2">
								<h:selectOneMenu>
									<f:selectItem itemLabel="item1" itemValue="item1" />
									<f:selectItem itemLabel="item2" itemValue="item2" />
									<f:selectItem itemLabel="item3" itemValue="item3" />
								</h:selectOneMenu>
				
								<h:selectOneMenu disabled="true" style="filter: alpha(opacity=50); background-color: #d0d0d0;">
									<f:selectItem itemLabel="item1" itemValue="item1" />
									<f:selectItem itemLabel="item2" itemValue="item2" />
									<f:selectItem itemLabel="item3" itemValue="item3" />
								</h:selectOneMenu>
	
								<f:verbatim>
									containerRendered
								</f:verbatim>

								<h:selectBooleanCheckbox value="#{bean.containerRendered}">
									<a4j:support event="onchange" />
								</h:selectBooleanCheckbox>
							</h:panelGrid>
			
						</mp:modalPanel>


					</mp:modalPanel>
					<f:verbatim>
						<a href="javascript:Richfaces.showModalPanel(':_panel');">Show</a>
						<a href="javascript:Richfaces.showModalPanel(':_panel', {left: '120', top: 'auto'});">Show: left = 120; top = auto</a>
						<a href="javascript:Richfaces.showModalPanel(':_panel', {top: 'auto', width: 800});">Show: width = 800; top = auto</a>
						<a href="javascript:Richfaces.showModalPanel(':_panel', {width: 800, height: 600, minWidth: 700, minHeight: 500});">Show: minWidth = 700; minHeight = 500; width: 800; height: 600;</a>
						<a href="javascript:Richfaces.showModalPanel(':_panel', {width: 1});">Show: width = 1</a>
						<a href="javascript:Richfaces.showModalPanel('eventInfoID');">Show autosized panel</a>
						<a href="javascript:Richfaces.showModalPanel('eventInfoID', {width: 1100, height: 700});">Show big autosized panel</a>	
				</f:verbatim>


					<mp:modalPanel id="eventInfoID" autosized="true" minHeight="250" minWidth="200" moveable="true" style="overflow: true;"> 
						<f:facet name="header"> 
							<h:outputText value="Events..." /> 
						</f:facet> 
		
						<f:facet name="controls"> 
							<h:commandLink value="C" onclick="Richfaces.hideModalPanel('eventInfoID'); return false;" /> 
						</f:facet> 
					
					
						<h:outputText value="Eventsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa ..." /> 
					
					</mp:modalPanel>
				</h:panelGrid>
				
				<%-- 
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				<mp:modalPanel>
					<f:verbatim>
						Modal panel is here!
					</f:verbatim>
				</mp:modalPanel>
				--%>
				
				<f:verbatim>
					<div id="fatDiv" style="width: 20px; height: 20px;">div div div</div>
				</f:verbatim>
			</a4j:outputPanel></h:form>
		</f:view>
	</body>	
</html>  
