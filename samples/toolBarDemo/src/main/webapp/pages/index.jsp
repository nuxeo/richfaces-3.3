<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://labs.jboss.com/jbossrichfaces/ui/toolBar" prefix="rich"%>
<html>
<style>
.toolbar_sep{
	padding : 0px 5px 0px 5px;
	}
</style>

<body>
<f:view>

    <h:form>
        <h:selectOneRadio binding="#{skinBean.component}" />
        <h:commandLink action="#{skinBean.change}" value="set skin" />
    </h:form>

    <h:form>
        <rich:toolBar itemSeparator="line">
            <rich:toolBarGroup itemSeparator="square">
                <h:commandButton value="button -1-" />
                <h:commandButton value="button -2-" rendered="false" />
                <h:commandButton value="button -3-" rendered="false" />
            </rich:toolBarGroup>
        </rich:toolBar>


        <h:messages style="color:red" />
        <h:outputText value="Simple richfaces toolBar and toolBarGroup test web application."
                      style="font: 18px;font-weight: bold;" />

        <rich:toolBar id="toolBar_01" width="#{bean.width}" height="#{bean.height}" itemSeparator="line">

            <h:outputText value="Exadel style tool bar." />

            <rich:toolBarGroup id="toolBarGroup_01" location="left"
                itemSeparator="square">
                <h:outputText value="Set width" />
                <h:inputText value="#{bean.width}" />
                <h:commandButton value="Change!" />
            </rich:toolBarGroup>

            <rich:toolBarGroup id="toolBarGroup_02" location="left"
                itemSeparator="square">
                <h:outputText value="Set height" />
                <h:inputText value="#{bean.height}" />
                <h:commandButton value="Change!" />
            </rich:toolBarGroup>

            <rich:toolBarGroup id="toolBarGroup_03" location="right"
                itemSeparator="grid">
                <h:graphicImage url="/images/exadel.gif" style="border-width: 0px;" />
                <h:outputLink value="http://exadel.com">
                    <h:outputText value="exadel.com" />
                </h:outputLink>
            </rich:toolBarGroup>

        </rich:toolBar>
			<br /><br /><br />			
			
			<rich:toolBar id="toolBar_02" width="#{bean.width}" height="#{bean.height}" itemSeparator="line"
				separatorClass="toolbar_sep" styleClass="user_style" style="#{bean.style}" contentStyle="#{bean.contentStyle}">
					
					<h:outputText value="Exadel tool bar with user styles" />
					<h:outputText value="#{bean.label}" />						
										
					<rich:toolBarGroup id="toolBarGroup_05" location="left" itemSeparator="square">
							<h:outputText value="Change label" />							
							<h:inputText value="#{bean.label}" style="height: 18px; font-size : 12px;" />
							<h:commandButton value="Change!" style="height: 18px; font-size : 10px;"/>							
					</rich:toolBarGroup>
					

					<rich:toolBarGroup id="toolBarGroup_06" location="right" itemSeparator="grid">
							<h:graphicImage url="/images/exadel.gif" style="border-width: 0px;" />
							<h:outputLink value="http://exadel.com">
								<h:outputText value="exadel.com" style="color: gold; font: 14px; font-weight: bold;" />
							</h:outputLink>							
					</rich:toolBarGroup>
					
			</rich:toolBar>
			<br />
	
			<h:outputText value="Set some style for this Tool Bar content:" />
			<h:inputText value="#{bean.contentStyle}" size="100" onchange="submit()"/>
			<h:commandButton value="Change!"/>
			<br /><br />
	
			<h:outputText value="Set some style for this Tool Bar borders:" />
			<h:inputText value="#{bean.style}" size="100" onchange="submit()"/>
			<h:commandButton value="Change!"/>
			<br /><br /><br />			
			
			<rich:toolBar id="toolBar_03" width="#{bean.width}" height="30px" itemSeparator="#{bean.separator_type}"
				separatorClass="toolbar_sep">

					<h:outputText value="Third toolbar" />												

					<rich:toolBarGroup id="toolBarGroup_07" location="left" itemSeparator="#{bean.separator_type}">
						<h:outputText value="Various separator types." />												
					</rich:toolBarGroup>
					
					<rich:toolBarGroup id="toolBarGroup_08" location="left">
							<h:outputText value="Separator type:" />
							<h:selectOneMenu  value="#{bean.separator_type}" onchange="submit()">
								<f:selectItem itemLabel="line" itemValue="line" />
								<f:selectItem itemLabel="square" itemValue="square" />
								<f:selectItem itemLabel="disc" itemValue="disc" />
								<f:selectItem itemLabel="grid" itemValue="grid" />								
							</h:selectOneMenu >						
					</rich:toolBarGroup>					

					<rich:toolBarGroup id="toolBarGroup_09" itemSeparator="#{bean.separator_type == 'disc' ? 'grid' : 'disc'}" location="right">
							<h:outputLink value="http://blog.exadel.com" style="color: white;" >
								<h:outputText value="blog" />
							</h:outputLink>							

							<h:outputLink value="http://forum.exadel.com" style="color: white;" >
								<h:outputText value="forum" />
							</h:outputLink>							

							<h:outputLink value="http://exadel.com" style="color: white;" >
								<h:outputText value="exadel.com" />
							</h:outputLink>							
					</rich:toolBarGroup>
					
			</rich:toolBar>		
		

	</h:form>
</f:view>
</body>
</html>
