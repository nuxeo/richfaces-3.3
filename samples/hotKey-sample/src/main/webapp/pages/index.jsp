<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/hotKey" prefix="sb"%>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/functions" prefix="rich" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/componentControl" prefix="cc" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<f:view>
			<h:form id="_form">
				<h:panelGroup id="panel">
					<h:panelGroup rendered="#{bean.rendered}">
						Alt+A and Alt+L are turned on when page is loaded<br/>
						Set focus to one of 'target' buttons and press Alt+T<br />

						<sb:hotKey id="me" key="alt+a" timing="immediate" handler="alert('alt+A is pressed')" />
						<sb:hotKey id="you" key="alt+l" timing="onload" handler="alert('alt+L is pressed')" />
						<sb:hotKey id="targetted" key="alt+t" timing="onload" handler="alert('alt+T is pressed; event type: ' + event.type); return false;" checkParent="false" 
									selector="#targetButton0, #targetButton1" />
						
						<sb:hotKey id="js" timing="onregistercall"/>
 
						<h:commandButton onclick="return false;" id="targetButton0" value="Target 0" /><br />
						<h:commandButton onclick="return false;" id="targetButton1" value="Target 1" /><br />
						
						<button onclick="${rich:component('me')}.enable(); return false;">Turn Alt-A On</button>
						<button onclick="${rich:component('me')}.disable(); return false;">Turn Alt-A Off</button><br />
						<button onclick="${rich:component('you')}.enable(); return false;">Turn Alt-L On</button>
						<button onclick="${rich:component('you')}.disable(); return false;">Turn Alt-L Off</button><br />

						<br />

						Add handler using JS API, set focus to JS API Button, then press ctrl+c
					
						<br />

						<button id="js_add" onclick="return false;">Add with JS API</button>
						<button id="js_remove" onclick="return false;">Remove with JS API</button>

						<br />

						<cc:componentControl attachTo="js_add" for="js" event="onclick"
							operation="add">

							<f:param name="selector" value="#_form\:jsButton" />
							<f:param name="key" value="ctrl+c" />
							<f:param name="handler" value="alert('ctrl + c pressed')" />
						</cc:componentControl>
						
						<cc:componentControl attachTo="js_remove" for="js" event="onclick" 
							operation="remove">

							<f:param name="selector" value="#_form\:jsButton" />
							<f:param name="key" value="ctrl+c" />
						</cc:componentControl>
					
						<h:commandButton onclick="return false;" id="jsButton" value="JS API Button" /><br />

						<br />

						Add global handler with JS API, then press ctrl+g

						<br />

						<button id="js_global_add" onclick="return false;">Add with JS API globally</button>
						<button id="js_global_remove" onclick="return false;">Remove with JS API globally</button>

						<cc:componentControl attachTo="js_global_add" for="js" event="onclick"
							operation="add">

							<f:param name="key" value="ctrl+g" />
							<f:param name="handler" value="alert('ctrl + g pressed')" />
						</cc:componentControl>
						
						<cc:componentControl attachTo="js_global_remove" for="js" event="onclick" 
							operation="remove">

							<f:param name="key" value="ctrl+g" />
						</cc:componentControl>
					</h:panelGroup>
				</h:panelGroup>

				<h:selectBooleanCheckbox value="#{bean.rendered}">
					<a4j:support event="onclick" reRender="panel" />
				</h:selectBooleanCheckbox>

				<a4j:commandButton value="ReRender" reRender="panel" />
			</h:form>			
		</f:view>
	</body>	
</html>  
