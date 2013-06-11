<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="toolBarSubviewID">
		<h:messages></h:messages>

		<rich:toolBar id="toolBarId" 
			width="#{toolBar.width}" 
			binding="#{toolBar.htmlToolBar}"
			height="#{toolBar.height}" 
			rendered="#{toolBar.rendered}"
			itemSeparator="#{toolBar.itemSeparator}"
			contentClass="#{toolBar.contentClass}"
			separatorClass="#{toolBar.separatorClass}"  
			contentStyle="font-style: italic" 
			style="" 
			styleClass="" 
			onitemclick="#{event.onitemclick}"
			onitemdblclick="#{event.onitemdblclick}"
			onitemkeydown="#{event.onitemkeydown}"
			onitemkeypress="#{event.onitemkeypress}"
			onitemkeyup="#{event.onitemkeyup}"
			onitemmousedown="#{event.onitemmousedown}"
			onitemmousemove="#{event.onitemmousemove}"
			onitemmouseout="#{event.onitemmouseout}"
			onitemmouseover="#{event.onitemmouseover}"
			onitemmouseup="#{event.onitemmouseup}">
			
			<h:outputText value="ToolBar" ></h:outputText>

			<rich:toolBarGroup>
				<h:outputText value="Width:"></h:outputText>
				<h:inputText value="#{toolBar.width}">
					<a4j:support event="onchange" reRender="toolBarId"></a4j:support>
				</h:inputText>
			</rich:toolBarGroup>

			<rich:toolBarGroup>
				<h:outputText value="Height:"></h:outputText>
				<h:inputText value="#{toolBar.height}">
					<a4j:support event="onchange" reRender="toolBarId"></a4j:support>
				</h:inputText>
			</rich:toolBarGroup>

			<rich:toolBarGroup>
				<rich:comboBox>
					<f:selectItem itemValue="select_1"/>
					<f:selectItem itemValue="select_2"/>
				</rich:comboBox>	
			</rich:toolBarGroup>
			
			<rich:toolBarGroup location="#{toolBar.location}">
				<h:graphicImage value="/pics/ajax_process.gif"></h:graphicImage>
			</rich:toolBarGroup>
			
		</rich:toolBar>

</f:subview>
