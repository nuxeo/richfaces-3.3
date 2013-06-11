<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="InsertPropertySubviewID">
	<h:commandButton value="add test" action="#{insert.addHtmlInsert}"></h:commandButton>
 		<h:panelGrid columns="2">
			<h:outputText value="Highlight:" />
			<h:selectOneMenu value="#{insert.highlight}">
				<f:selectItem itemValue="HTML" itemLabel="HTML" />
				<f:selectItem itemValue="GROOVY" itemLabel="GROOVY" />
				<f:selectItem itemValue="JAVA" itemLabel="JAVA" />
				<f:selectItem itemValue="BEANSHELL" itemLabel="BEANSHELL" />
				<f:selectItem itemValue="BSH" itemLabel="BSH" />
				<f:selectItem itemValue="XML" itemLabel="XML" />
				<f:selectItem itemValue="XHTML" itemLabel="XHTML" />
				<f:selectItem itemValue="LZX" itemLabel="LZX" />
				<f:selectItem itemValue="CPP" itemLabel="CPP" />
				<f:selectItem itemValue="CXX" itemLabel="CXX" />
				<a4j:support event="onclick" reRender="panelID,insertID"></a4j:support>
			</h:selectOneMenu>
			
			<h:outputText value="Select file:" />
			<h:selectOneMenu value="#{insert.src}">
				<f:selectItem itemValue="/Insert/src/test.html" itemLabel="HTML" />
				<f:selectItem itemValue="/Insert/src/test.java" itemLabel="JAVA" />
				<f:selectItem itemValue="/Insert/src/test.groovy" itemLabel="GROOVY" />
				<f:selectItem itemValue="/Insert/src/test.bsh" itemLabel="BEANSHELL,BSH" />
				<f:selectItem itemValue="/Insert/src/test.xml" itemLabel="XML" />
				<f:selectItem itemValue="/Insert/Insert.xhtml" itemLabel="XHTML" />
				<f:selectItem itemValue="/Insert/src/test.lzx" itemLabel="LZX" />
				<f:selectItem itemValue="/Insert/src/test.cpp" itemLabel="CPP,CXX" />
				<a4j:support event="onclick" reRender="panelID,insertID"></a4j:support>
			</h:selectOneMenu>
			
			<h:outputText value="Rendered" />
			<h:selectBooleanCheckbox value="#{insert.rendered}"  onchange="submit();"/>
		</h:panelGrid>
</f:subview>