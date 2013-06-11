<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="hotKeySubviewID">
	<rich:calendar id="hotKeyCalendarID" popup="false" />

	<h:commandButton id="commandButtonID" onclick="return false;"
		value="test select"></h:commandButton>
		
	<rich:hotKey id="hotKeyID" binding="#{hotKey.htmlHotKey}"
		disableInInput="#{hotKey.disableInInput}" handler="#{hotKey.handler}"
		key="#{hotKey.key}" rendered="#{hotKey.rendered}"
		selector="#{hotKey.selector}" timing="#{hotKey.timing}"
		type="#{hotKey.type}"
		disableInInputTypes="#{hotKey.disableInInputTypes}" />

	<h:panelGrid columns="2">
		<h:outputText value="type"></h:outputText>
		<h:selectOneMenu value="#{hotKey.type}" onchange="submit();">
			<f:selectItem itemLabel="onkeydown" itemValue="onkeydown" />
			<f:selectItem itemLabel="onkeypress" itemValue="onkeypress" />
			<f:selectItem itemLabel="onkeyup" itemValue="onkeyup" />
		</h:selectOneMenu>

		<h:outputText value="key"></h:outputText>
		<h:inputText value="#{hotKey.key}" onchange="submit();">
		</h:inputText>

		<h:outputText value="timing"></h:outputText>
		<h:selectOneMenu value="#{hotKey.timing}" onchange="submit();">
			<f:selectItem itemLabel="onload" itemValue="onload" />
			<f:selectItem itemLabel="immediate" itemValue="immediate" />
		</h:selectOneMenu>

		<h:outputText value="selector"></h:outputText>
		<h:selectOneMenu value="#{hotKey.selector}" onchange="submit();">
			<f:selectItem itemLabel="" itemValue="" />
			<f:selectItem itemLabel="Button" itemValue="#commandButtonID" />
		</h:selectOneMenu>

		<h:outputText value="handler"></h:outputText>
		<h:selectOneMenu value="#{hotKey.handler}" onchange="submit();">
			<f:selectItem
				itemValue="$('formID:hotKeySubviewID:hotKeyCalendarID').component.nextYear();"
				itemLabel="nextYear(Calendar)" />
			<f:selectItem itemValue="alert('work')" itemLabel="alert('work')" />
		</h:selectOneMenu>

		<h:outputText value="disableInInput"></h:outputText>
		<h:selectBooleanCheckbox value="#{hotKey.disableInInput}" onchange="submit();" />

		<h:outputText value="disableInInput"></h:outputText>
		<h:selectOneRadio value="#{hotKey.disableInInputTypes}" onchange="submit();">
			<f:selectItem itemValue="all" itemLabel="all" />
			<f:selectItem itemValue="buttons" itemLabel="buttons" />
			<f:selectItem itemValue="texts" itemLabel="texts" />
		</h:selectOneRadio>

		<%--<h:outputText value="checkParent"></h:outputText>
		<h:selectBooleanCheckbox value="#{hotKey.checkParent}">
			<a4j:support reRender="hotKeyID" event="onchange"></a4j:support>
		</h:selectBooleanCheckbox>--%>

		<h:outputText value="rendered"></h:outputText>
		<h:selectBooleanCheckbox value="#{hotKey.rendered}" onchange="submit();" />

		<h:outputText value="test binding:"></h:outputText>
		<h:outputText value="#{hotKey.bindingInfo}"></h:outputText>

		<h:commandButton
			onclick="$('formID:hotKeySubviewID:hotKeyID').component.enable(); return false;"
			value="enable"></h:commandButton>
		<h:commandButton
			onclick="$('formID:hotKeySubviewID:hotKeyID').component.disable(); return false;"
			value="disable"></h:commandButton>
	</h:panelGrid>
	
	<f:verbatim>
		<hr />
	</f:verbatim>

	<rich:hotKey id="jsID" timing="onregistercall" />

	<h:panelGrid columns="2">
		<h:commandButton id="jsAddCCID" value="add hotkey: shift+d"
			onclick="return false;"></h:commandButton>
		<h:commandButton id="jsRemoveCCID" value="remove hotkey: shift+d"
			onclick="return false;"></h:commandButton>

		<rich:componentControl disableDefault="true" attachTo="jsAddCCID"
			for="jsID" event="onclick" operation="add">
			<f:param name="key" value="shift+d" />
			<f:param name="handler" value="alert('shift+d')" />
		</rich:componentControl>

		<rich:componentControl disableDefault="true" attachTo="jsRemoveCCID"
			for="jsID" event="onclick" operation="remove">
			<f:param name="key" value="shift+d" />
		</rich:componentControl>
	</h:panelGrid>
	
	<f:verbatim>
		<hr />
	</f:verbatim>
	
	<h:commandButton action="#{hotKey.add}" value="add test" />
	
	<%--
	<div style="FONT-WEIGHT: bold;">rich:findComponent</div>
	<h:panelGrid columns="2">
		<rich:column>
			<a4j:commandLink value="getRendererType" reRender="findID"></a4j:commandLink>
		</rich:column>
		<rich:column id="findID">
			<h:outputText value="#{rich:findComponent('hotKeyID').rendererType}" />
		</rich:column>
	</h:panelGrid>
	--%>
</f:subview>