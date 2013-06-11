<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="insertSubviewID">
		<h:messages />
		
		<rich:panel id="panelID" header="Highlight: #{insert.highlight}; File: #{insert.src}">
			<rich:insert id="insertID" binding="#{insert.htmlInsert}" highlight="#{insert.highlight}" 
				rendered="#{insert.rendered}" src="#{insert.src}"></rich:insert>
		</rich:panel>
</f:subview>
