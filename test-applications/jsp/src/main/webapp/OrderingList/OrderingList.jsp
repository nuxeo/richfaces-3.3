<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="orderingListSubviewID">

	<a4j:outputPanel ajaxRendered="true">
		<h:messages />
	</a4j:outputPanel>

	<rich:orderingList id="orderingListID" binding="#{orderingList.htmlOrderingList}" value="#{orderingList.list}" var="item" listHeight="#{orderingList.listHeight}"
		listWidth="#{orderingList.listWidth}" controlsType="#{orderingList.controlsType}" converter="dataConverter"
		bottomControlLabel="#{orderingList.bottomControlLabel}" captionLabel="#{orderingList.captionLabel}"
		topControlLabel="#{orderingList.topControlLabel}" upControlLabel="#{orderingList.upControlLabel}"
		controlsHorizontalAlign="#{orderingList.controlsHorizontalAlign}" controlsVerticalAlign="#{orderingList.controlsVerticalAlign}"
		downControlLabel="#{orderingList.downControlLabel}"
		orderControlsVisible="#{orderingList.orderControlsVisible}" fastOrderControlsVisible="#{orderingList.fastOrderControlsVisible}"
		rendered="#{orderingList.rendered}" showButtonLabels="#{orderingList.showButtonLabels}" selection="#{orderingList.selection}"
	 	onmousemove="#{event.onmousemove}" onclick="#{event.onclick}" ondblclick="#{event.ondblclick}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onorderchanged="#{event.onorderchanged}" ontopclick="#{event.ontopclick}" onupclick="#{event.onupclick}" ondownclick="#{event.ondownclick}" onbottomclick="#{event.onbottomclick}" onheaderclick="#{event.onheaderclick}">
		<f:facet name="header">
			<h:outputText value="header" />
		</f:facet>
		<f:facet name="footer">
			<h:outputText value="footer" />
		</f:facet>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Number" />
			</f:facet>
			<h:outputText value="#{item.int0}" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Input" />
			</f:facet>
			<h:inputText value="#{item.str0}" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Button" />
			</f:facet>
			<h:commandButton onclick="submit();" action="#{orderingList.cbAction}" value="#{item.str0} submit();" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Input" />
			</f:facet>
			<h:inputText value="#{item.str1}" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Link" />
			</f:facet>
			<a4j:commandLink action="#{orderingList.clAction}" value="#{item.str1} submit()" reRender="orderingListID"></a4j:commandLink>
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="select" />
			</f:facet>
			<h:selectOneMenu value="#{item.str2}" >
				<f:selectItem itemLabel="select0" itemValue="select0" />
				<f:selectItem itemLabel="select1" itemValue="select1" />
				<f:selectItem itemLabel="select2" itemValue="select2" />
				<f:selectItem itemLabel="select3" itemValue="select3" />
				<f:selectItem itemLabel="select4" itemValue="select4" />
				<a4j:support event="onclick" reRender="orderingListID"></a4j:support>
			</h:selectOneMenu>
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="Text" />
			</f:facet>
			<h:outputText value="#{item.str3}" />
		</h:column>

		<h:column>
			<f:facet name="header">
				<h:outputText value="graphicImage" />
			</f:facet>
			<h:graphicImage value="#{item.str3}" />
		</h:column>
		
		<h:column>
			<f:facet name="header">
				<h:outputText value="Link"></h:outputText>
			</f:facet>
			<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
		</h:column>
	</rich:orderingList>
	<h:panelGrid columns="3">
		<h:column></h:column>
		<h:outputText value="JavaScript API" style="FONT-WEIGHT: bold;"></h:outputText>
		<h:column></h:column>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.hide()" value="hide()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.show()" value="show()"></a4j:commandLink>
		<a4j:commandLink onclick="alert($('formID:orderingListSubviewID:orderingListID').component.isShown())" value="isShown()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.ebnable()" value="ebnable()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.disable()" value="disable()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.isEnabled()" value="isEnabled()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.up()" value="up()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.moveUp()" value="moveUp()-old"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.down()" value="down()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.moveDown()" value="moveDown()-old"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.top()" value="top()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.moveTop()" value="moveTop()-old"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.bottom()" value="bottom()"></a4j:commandLink>
		<a4j:commandLink onclick="$('formID:orderingListSubviewID:orderingListID').component.moveBottom()" value="moveBottom()-old"></a4j:commandLink>
		<a4j:commandLink onclick="alert($('formID:orderingListSubviewID:orderingListID').component.getSelection())" value="getSelection()"></a4j:commandLink>
		<a4j:commandLink onclick="alert($('formID:orderingListSubviewID:orderingListID').component.getItems())" value="getItems()"></a4j:commandLink>
	</h:panelGrid>	
	<f:verbatim>
	<h:outputText value="Component Control test" style="FONT-WEIGHT: bold;"></h:outputText>	
	<a href="#" id="hideID">hide()</a>
	<a href="#" id="showID">show()</a>
	<a href="#" id="isShownID">isShown()</a>
	<a href="#" id="enableID">enable()</a>
	<a href="#" id="disableID">disable()</a>
	<a href="#" id="isEnabledID">isEnabled()</a>
	<a href="#" id="upID">up()</a>	
	<a href="#" id="downID">down()</a>
	<a href="#" id="topID">top()</a>	
	<a href="#" id="bottomID">bottom()</a>	
	<a href="#" id="getSelectionID">getSelection()</a>
	<a href="#" id="getItemsID">getItems()</a>
	</f:verbatim>
	<rich:componentControl attachTo="hideID" event="onclick" for="orderingListID" operation="hide"></rich:componentControl>
	<rich:componentControl attachTo="showID" event="onclick" for="orderingListID" operation="show"></rich:componentControl>
	<rich:componentControl attachTo="isShownID" event="onclick" for="orderingListID" operation="alert(isShown)"></rich:componentControl>
	<rich:componentControl attachTo="enableID" event="onclick" for="orderingListID" operation="enable"></rich:componentControl>
	<rich:componentControl attachTo="disableID" event="onclick" for="orderingListID" operation="disable"></rich:componentControl>
	<rich:componentControl attachTo="isEnabledID" event="onclick" for="orderingListID" operation="isEnabled"></rich:componentControl>
	<rich:componentControl attachTo="upID" event="onclick" for="orderingListID" operation="up"></rich:componentControl>
	<rich:componentControl attachTo="downID" event="onclick" for="orderingListID" operation="down"></rich:componentControl>
	<rich:componentControl attachTo="topID" event="onclick" for="orderingListID" operation="top"></rich:componentControl>
	<rich:componentControl attachTo="bottomID" event="onclick" for="orderingListID" operation="bottom"></rich:componentControl>
	<rich:componentControl attachTo="getSelectionID" event="onclick" for="orderingListID" operation="alert(getSelection)"></rich:componentControl>
	<rich:componentControl attachTo="getItemsID" event="onclick" for="orderingListID" operation="alert(getItems)"></rich:componentControl>
	
</f:subview>