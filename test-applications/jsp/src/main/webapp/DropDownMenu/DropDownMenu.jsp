<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="DropDownMenuSubviewID">
		<h:panelGrid columns="2">
			<rich:spacer width="400px" height="400px"></rich:spacer>
			<rich:panel>
				<rich:dropDownMenu id="ddmId" binding="#{dDMenu.htmlDropDownMenu}" disabled="#{dDMenu.disabledDDM}" value="DropDownMenu" submitMode="#{dDMenu.mode}" hideDelay="#{dDMenu.hideDelay}"
					direction="#{dDMenu.direction}" horizontalOffset="#{dDMenu.horizontalOffset}" jointPoint="#{dDMenu.jointPoint}"
					popupWidth="#{dDMenu.popupWidth}" showDelay="#{dDMenu.showDelay}" rendered="#{dDMenu.rendered}"
					verticalOffset="#{dDMenu.verticalOffset}" styleClass="#{style.styleClass}" style="#{style.style}" event="#{dDMenu.event}"  
					oncollapse="#{event.oncollapse}" onexpand="#{event.onexpand}" ongroupactivate="#{event.ongroupactivate}" onitemselect="#{event.onitemselect}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}">
					<rich:menuItem icon="#{dDMenu.icon}" onbeforedomupdate="#{event.onbeforedomupdate}" onclick="#{event.onclick}" oncomplete="#{event.oncomplete}" onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}" onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}" onmouseup="#{event.onmouseup}" onselect="#{event.onselect}">
						<h:outputText value="Item1(test events)" />
					</rich:menuItem>
					<rich:menuSeparator />
					<rich:menuItem icon="#{dDMenu.icon}" onmousedown="alert('OnMouseDown')" selectClass="mousemove">
						<h:outputText value="OnMouseDown" />
					</rich:menuItem>
					<rich:menuSeparator />
					<rich:menuItem>
						<h:outputText value="Item2" />
					</rich:menuItem>
					<rich:menuItem icon="#{dDMenu.icon}">
						<h:outputText value="Item3" />
					</rich:menuItem>
					<rich:menuItem>
						<h:outputLink value="http://www.jboss.com/"><f:verbatim>Link</f:verbatim></h:outputLink>
					</rich:menuItem>
					<rich:menuSeparator/>
					<rich:menuItem icon="/pics/info.gif" value="action" action="#{dDMenu.act}" reRender="cmInfoID">
					</rich:menuItem>
					<rich:menuItem icon="/pics/info.gif" value="actionListener" actionListener="#{dDMenu.actListener}" reRender="cmInfoID">
					</rich:menuItem>
					<rich:menuSeparator />
					<rich:menuItem icon="/pics/ajax_process.gif" iconDisabled="/pics/ajax_stoped.gif" disabled="#{dDMenu.disabled}">
						<h:outputText value="Image"></h:outputText>
					</rich:menuItem>
					<rich:menuItem disabled="#{dDMenu.disabled}" icon="#{dDMenu.icon}">
						<h:outputText value="Image:" />
						<h:graphicImage value="/pics/ajax_process.gif" />
					</rich:menuItem>
					<rich:menuSeparator />
					<rich:menuGroup value="Second level" direction="#{dDMenu.groupDirection}" disabled="#{dDMenu.disabled}" icon="#{dDMenu.icon}"
						iconFolder="#{dDMenu.iconFolder}">
						<rich:menuItem icon="#{dDMenu.icon}">
							<h:outputText value="Item3" />
						</rich:menuItem>
						<rich:menuItem value="Item4">
							<h:selectOneMenu value="#{dDMenu.selectMenu}">
								<f:selectItem itemLabel="Honda Accord" itemValue="accord" />
								<f:selectItem itemLabel="Toyota 4Runner" itemValue="4runner" />
								<f:selectItem itemLabel="Nissan Z350" itemValue="nissan-z" />
							</h:selectOneMenu>
						</rich:menuItem>
						<rich:menuSeparator />
						<rich:menuItem icon="#{dDMenu.icon}">
							<h:outputText value="CheckBox " />
							<h:selectBooleanCheckbox value="#{dDMenu.check}" onclick="submit()" />
						</rich:menuItem>
					</rich:menuGroup>
				</rich:dropDownMenu>
			</rich:panel>
		</h:panelGrid>
		<h:panelGrid id="dndActionID" columns="1">
			<a4j:commandButton value="Show action" reRender="dndActionID" style=" width : 95px;"></a4j:commandButton>
				<h:outputText value="#{dDMenu.action}" />
				<h:outputText value="#{dDMenu.actionListener}" />
		</h:panelGrid>			
</f:subview>
