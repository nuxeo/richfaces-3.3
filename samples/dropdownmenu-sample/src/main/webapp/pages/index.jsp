<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/dropdown-menu" prefix="ddm" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/menu-components" prefix="mc" %>
<html>

<head>
    <style type="text/css">
    	.disabled_item {
    		border: 1px solid blue;
    	}
    	.select_item {
    		border: 1px dashed red;
    	}
    </style>
</head>

<body>
<f:view>
    <h:form id="frm">
        <h:messages style="color:red"/>
        <h:selectOneRadio binding="#{skinBean.component}"/>
        <h:commandLink action="#{skinBean.change}" value="set skin"/>

        <h:panelGrid id="grid" columns="2" cellspacing="4">
            <ddm:dropDownMenu  event="#{bean.event}" id="MenuItem1"
            		verticalOffset="#{bean.verticalOffset}"
            		horizontalOffset="#{bean.horizontalOffset}"
            		popupWidth="#{bean.width}"
            		value="Item One"
            		jointPoint="#{bean.jointPoint}"
            		direction="#{bean.direction}"
            		 
            		disabledItemStyle="color: green;"
            		disabledItemClass="disabled_item"
            		selectItemClass="select_item"
            		itemStyle="font-weight: bold;">
                <mc:menuItem id="menuItem11" value="Active11: ajax" 
                	submitMode="ajax" actionListener="#{bean.actionListener}"
                	disabled="true" />
                <mc:menuItem id="menuItem12" value="Active12" actionListener="#{bean.actionListener}" immediate="true"/>
                <mc:menuItem id="menuItem13" value="Active13" action="#{bean.action}" icon="/images/ico1.gif"/>
                <mc:menuItem id="menuItem14" value="Active14"/>
                <mc:menuGroup id="menuGroup1" value="Group1" direction="#{bean.groupDirection}" icon="/images/ico1.gif">
                    <mc:menuItem id="menuGroup1Item1" value="Active"/>
                    <mc:menuItem id="menuGroup1Item2" value="Active" icon="/images/ico1.gif"/>
                    <mc:menuItem id="menuGroup1Item3" value="Active"/>
                </mc:menuGroup>
                <mc:menuGroup id="menuGroup1Dsbld" value="Group1 dsbld." direction="#{bean.groupDirection}" icon="/images/ico1.gif" iconDisabled="/images/ico2.gif" disabled="true" />


                <mc:menuGroup id="menuGroup11" value="Group11" direction="#{bean.groupDirection}" iconFolder="/images/ico1.gif">
                    <mc:menuItem id="menuGroup11Item1" value="Active"/>
                    <mc:menuItem id="menuGroup11Item2" value="Active" icon="/images/ico1.gif"/>
                    <mc:menuItem id="menuGroup11Item3" value="Active"/>
                </mc:menuGroup>
                <mc:menuGroup id="menuGroup11Dsbld" value="Group11 dsbld." direction="#{bean.groupDirection}" iconFolder="/images/ico1.gif" iconFolderDisabled="/images/ico2.gif" disabled="true" />

                <mc:menuGroup id="menuGroup12" value="Group12" direction="#{bean.groupDirection}">
                    <f:facet name="iconFolder">
                    	<h:graphicImage value="/images/ico1.gif" />
                    </f:facet>
                    <mc:menuItem id="menuGroup12Item1" value="Active"/>
                    <mc:menuItem id="menuGroup12Item2" value="Active" icon="/images/ico1.gif"/>
                    <mc:menuItem id="menuGroup12Item3" value="Active"/>
                </mc:menuGroup>
                <mc:menuGroup id="menuGroup12Dsbld" value="Group12 dsbld." direction="#{bean.groupDirection}" disabled="true">
                   <f:facet name="iconFolderDisabled">
                    	<h:graphicImage value="/images/ico2.gif" />
                    </f:facet>
                 </mc:menuGroup>




                <mc:menuItem id="menuItem15" value="Active15" icon="/images/ico2.gif"/>

                <mc:menuSeparator id="menuSeparator11"/>

                <mc:menuItem id="menuItem16" value="Disable16" disabled="true"/>
                <mc:menuItem id="menuItem17" value="">
                    <h:inputText value="#{bean.width}"/>
                </mc:menuItem>
            </ddm:dropDownMenu>

            <ddm:dropDownMenu event="#{bean.event}" verticalOffset="#{bean.verticalOffset}" horizontalOffset="#{bean.horizontalOffset}" id="MenuItem2" popupWidth="#{bean.width}" jointPoint="#{bean.jointPoint}" direction="#{bean.direction}">
                <f:facet name="label">
                    <f:verbatim>Item2</f:verbatim>
                </f:facet>

                <mc:menuItem id="menuItem21" value="Active21: none" submitMode="none"/>
                <mc:menuItem id="menuItem22" value="Active22"/>
                <mc:menuItem id="menuItem23" value="Active23" icon="/images/node.gif">
                    <f:facet name="icon">
                        <h:graphicImage value="/images/ico1.gif"/>
                    </f:facet>
                </mc:menuItem>

                <mc:menuItem id="menuItem24" value="Active24"/>
                <mc:menuGroup id="menuGroup2" value="Group2" direction="#{bean.groupDirection}">
                    <mc:menuItem id="menuGroup2Item1" value="Active"/>
                    <mc:menuItem id="menuGroup2Item2" value="Active" icon="/images/ico1.gif"/>
                    <mc:menuItem id="menuGroup2Item3" value="Active"/>
                </mc:menuGroup>

                <mc:menuItem id="menuItem25" value="Active25">
                    <f:facet name="icon">
                        <h:graphicImage value="/images/ico2.gif"/>
                    </f:facet>
                </mc:menuItem>

                <mc:menuSeparator id="menuSeparator21"/>

                <mc:menuItem id="menuItem26" value="Disable26" disabled="true"/>
                <mc:menuItem id="menuItem27" value="Active27"/>
            </ddm:dropDownMenu>
        </h:panelGrid>

        <br/>
        <br/>
            <ddm:dropDownMenu event="#{bean.event}" verticalOffset="#{bean.verticalOffset}" horizontalOffset="#{bean.horizontalOffset}" style="position: absolute; left: 300px; top: 70px; font-size:14px" id="MenuItemAbs1" popupWidth="#{bean.width}" value="Absolute"  jointPoint="#{bean.jointPoint}" direction="#{bean.direction}">
                <mc:menuItem id="menuItemAbs11" value="Active11: ajax" submitMode="ajax" actionListener="#{bean.actionListener}"/>
                <mc:menuItem id="menuItemAbs12" value="Active12" actionListener="#{bean.actionListener}" immediate="true"/>
                <mc:menuItem id="menuItemAbs13" value="Active13" action="#{bean.action}" icon="/images/ico1.gif"/>
                <mc:menuItem id="menuItemAbs14" value="Active14"/>
                <mc:menuGroup id="menuGroupAbs1" value="Group1" direction="#{bean.groupDirection}">
                    <mc:menuItem id="menuGroupAbs1Item1" value="Active"/>
                    <mc:menuItem id="menuGroupAbs1Item2" value="Active" icon="/images/ico1.gif"/>
                    <mc:menuGroup id="menuGroupAbs2" value="Group2" direction="#{bean.groupDirection}">
                           <mc:menuItem id="menuGroupAbs2Item1" value="Active"/>
                           <mc:menuItem id="menuGroupAbs2Item2" value="Active" icon="/images/ico1.gif"/>
                           <mc:menuGroup id="menuGroupAbs4" value="Group3" direction="#{bean.groupDirection}">
                             <mc:menuItem id="menuGroupAbs4Item1" value="Active" icon="/images/ico1.gif"/>
                             <mc:menuItem id="menuGroupAbs4Item2" value="Active" icon="/images/ico1.gif"/>
                             <mc:menuGroup id="menuGroupAbs5" value="Group3" direction="#{bean.groupDirection}">
                               <mc:menuItem id="menuGroupAbs5Item1" value="Active" icon="/images/ico1.gif"/>
                               <mc:menuItem id="menuGroupAbs5Item2" value="Active" icon="/images/ico1.gif"/>
                             </mc:menuGroup>
                           </mc:menuGroup>
                    </mc:menuGroup>
                    <mc:menuItem id="menuGroupAbs1Item3" value="Active"/>
                </mc:menuGroup>
                <mc:menuItem id="menuItemAbs15" value="Active15" icon="/images/ico2.gif"/>
                
                <mc:menuSeparator id="menuSeparator1111"/>
                
                <mc:menuItem id="menuItemAbs16" value="Disable16" disabled="true"/>
                <mc:menuItem id="menuItemAbs17" value="">
                    <h:inputText value="#{bean.width}"/>
                </mc:menuItem>
            </ddm:dropDownMenu>
        



        <h:panelGroup>
            <h:outputText value="Width:"/>
            <h:selectOneRadio value="#{bean.width}" onclick="submit()">
                <f:selectItem itemLabel="auto" itemValue="auto"/>
                <f:selectItem itemLabel="100" itemValue="100px"/>
                <f:selectItem itemLabel="200" itemValue="200px"/>
                <f:selectItem itemLabel="300" itemValue="300px"/>
                <f:selectItem itemLabel="400" itemValue="400px"/>
            </h:selectOneRadio>
        </h:panelGroup>

        <h:panelGroup>
            <h:outputText value="JointPoint:"/>
            <h:selectOneRadio value="#{bean.jointPoint}" onclick="submit()">
                <f:selectItem itemLabel="auto" itemValue="auto"/>
                <f:selectItem itemLabel="tl" itemValue="tl"/>
                <f:selectItem itemLabel="tr" itemValue="tr"/>
                <f:selectItem itemLabel="bl" itemValue="bl"/>
                <f:selectItem itemLabel="br" itemValue="br"/>
            </h:selectOneRadio>
        </h:panelGroup>

        <h:panelGroup>
            <h:outputText value="Menu direction:"/>
            <h:selectOneRadio value="#{bean.direction}" onclick="submit()">
                <f:selectItem itemLabel="auto" itemValue="auto"/>
                <f:selectItem itemLabel="top-left" itemValue="top-left"/>
                <f:selectItem itemLabel="top-right" itemValue="top-right"/>
                <f:selectItem itemLabel="bottom-left" itemValue="bottom-left"/>
                <f:selectItem itemLabel="bottom-right" itemValue="bottom-right"/>
            </h:selectOneRadio>
        </h:panelGroup>

        <h:panelGroup>
            <h:outputText value="MenuGroup direction:"/>
            <h:selectOneRadio value="#{bean.groupDirection}" onclick="submit()">
                <f:selectItem itemLabel="auto" itemValue="auto"/>
                <f:selectItem itemLabel="left" itemValue="left"/>
                <f:selectItem itemLabel="right" itemValue="right"/>
            </h:selectOneRadio>
        </h:panelGroup>

        <h:panelGroup>
            <h:outputText value="Menu appearance event:"/>
            <h:selectOneRadio value="#{bean.event}" onclick="submit()">
                <f:selectItem itemLabel="onclick" itemValue="onclick"/>
                <f:selectItem itemLabel="onmouseover" itemValue="onmouseover"/>
            </h:selectOneRadio>
        </h:panelGroup>
        
        <h:panelGroup>               
            <h:outputText value="Horizontal offset: "/>
  	    <h:inputText value="#{bean.horizontalOffsets}" onchange="submit()"/>	  	
        </h:panelGroup>

        <h:panelGroup>        	
            <h:outputText value="Vertical offset: "/>
  	    <h:inputText value="#{bean.verticalOffsets}" onchange="submit()"/>	  		    
        </h:panelGroup>

        <br/>
        <br/>


            <ddm:dropDownMenu 
                      oncollapse="$('oncollapsedd').innerHTML='+'" 
                      onexpand="$('onexpanddd').innerHTML='+'" 
                      onmouseout="$('onmouseoutdd').innerHTML='+'" 
                      onmousemove="$('onmousemovedd').innerHTML='+'" 
                      onmouseover="$('onmouseoverdd').innerHTML='+'" 
                      onitemselect="$('onitemselectdd').innerHTML='+'" 
                      ongroupactivate="$('ongroupactivatedd').innerHTML='+'" 
                      event="#{bean.event}"
                      verticalOffset="#{bean.verticalOffset}" 
                      horizontalOffset="#{bean.horizontalOffset}" 
                      id="MenuItemTest1" 
                      popupWidth="#{bean.width}" 
                      value="Test event menu"
                      jointPoint="#{bean.jointPoint}" 
                      direction="#{bean.direction}">
                <mc:menuItem 
                      onclick="$('onclicki').innerHTML='+'" 
                      onmouseout="$('onmouseouti').innerHTML='+'" 
                      onmousedown="$('onmousedowni').innerHTML='+'" 
                      onmouseup="$('onmouseupi').innerHTML='+'" 
                      onmousemove="$('onmousemovei').innerHTML='+'" 
                      onmouseover="$('onmouseoveri').innerHTML='+'"
                      onselect ="$('onselecti').innerHTML='+'"
                      id="menuItemTest11" value="Active11: ajax" submitMode="ajax" actionListener="#{bean.actionListener}"/>
                <mc:menuGroup id="menuGroupTest1" value="Group" direction="#{bean.groupDirection}"
                      onmouseout="$('onmouseoutmg').innerHTML='+'"
                      onmousemove="$('onmousemovemg').innerHTML='+'"
                      onmouseover="$('onmouseovermg').innerHTML='+'"
                      onopen="$('onopenmg').innerHTML='+'"
                      onclose="$('onclosemg').innerHTML='+'">
                    <mc:menuItem id="menuGroupTest1Item1" value="Active"/>
                    <mc:menuItem id="menuGroupTest1Item2" value="Active" icon="/images/ico1.gif"/>
                    <mc:menuGroup id="menuGroupTest2" value="Group2" direction="#{bean.groupDirection}">
                           <mc:menuItem id="menuGroupTest2Item1" value="Active"/>
                           <mc:menuItem id="menuGroupTest2Item2" value="Active" icon="/images/ico1.gif"/>
                    </mc:menuGroup>
                    <mc:menuItem id="menuGroupTest1Item3" value="Active"/>
                </mc:menuGroup>
                <mc:menuSeparator id="menuSeparatorTest"/>                
                <mc:menuItem id="menuItemTest15" value="Disable" disabled="true"/>
            </ddm:dropDownMenu>
<br/>
<br/>
<br/>
        <h:panelGroup>        	
        <h:outputText value="dropDownMenu event:"/>
        <h:panelGrid id="grid1" columns="7" cellspacing="0" border="1">
                      <h:outputText value="oncollapse"/>
                      <h:outputText value="onexpand"/>
                      <h:outputText value="onmouseout"/>
                      <h:outputText value="onmousemove"/>
                      <h:outputText value="onmouseover"/>
                      <h:outputText value="onitemselect"/>
                      <h:outputText value="ongroupactivate"/>
                      <f:verbatim>
                      <span id="oncollapsedd">&nbsp;</span>
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onexpanddd">&nbsp;</span>
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmouseoutdd">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmousemovedd">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmouseoverdd">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onitemselectdd">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="ongroupactivatedd">&nbsp;</span>
                      </f:verbatim>
        </h:panelGrid>
        <h:outputText value="menuGroup event:"/>
        <h:panelGrid id="grid2" columns="5" border="1" cellspacing="0">
                      <h:outputText value="onmouseout"/>
                      <h:outputText value="onmousemove"/>
                      <h:outputText value="onmouseover"/>
                      <h:outputText value="onopen"/>
                      <h:outputText value="onclose"/>
                      <f:verbatim>
                      <span id="onmouseoutmg">&nbsp;</span>
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmousemovemg">&nbsp;</span>
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmouseovermg">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onopenmg">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onclosemg">&nbsp;</span>                      
                      </f:verbatim>
        </h:panelGrid> 

        <h:outputText value="menuItem event:"/>
        <h:panelGrid id="grid3" columns="7"   border="1" cellspacing="0">
                      <h:outputText value="onmouseout"/>
                      <h:outputText value="onmousedown"/>
                      <h:outputText value="onmouseup"/>
                      <h:outputText value="onmousemove"/>
                      <h:outputText value="onmouseover"/>
                      <h:outputText value="onitemselect"/>
                      <h:outputText value="onselect"/>
                      <f:verbatim>
                      <span id="onclicki">&nbsp;</span>
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmouseouti">&nbsp;</span>
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmousedowni">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmouseupi">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmousemovei">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onmouseoveri">&nbsp;</span>                      
                      </f:verbatim>
                      <f:verbatim>
                      <span id="onselecti">&nbsp;</span>
                      </f:verbatim>
        </h:panelGrid> 
        </h:panelGroup>        	
     <mc:menuItem id="mymenuGroup11Item" value="Active" />

    </h:form>
</f:view>
</body>
</html>
