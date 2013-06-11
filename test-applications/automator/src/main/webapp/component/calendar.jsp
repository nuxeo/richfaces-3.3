<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="calendarSubview">
	<div>The &lt;rich:calendar&gt; component is used for creating
	monthly calendar elements on a page.</div>
	<rich:spacer height="30"></rich:spacer>

	<h:messages id="calendarMessages" style="color:red;" />
	<rich:calendar id="calendar" onbeforedomupdate="callOnbeforedomupdate('calendar')"
		onchanged="callOnchanged('calendar')"
		oncollapse="callOncollapse('calendar')"
		oncomplete="callOncomplete('calendar')"
		oncurrentdateselect="callOncurrentdateselect('calendar')"
		oncurrentdateselected="callOncurrentdateselected('calendar')"
		ondatemouseout="callOndatemouseout('calendar')"
		ondatemouseover="callOndatemouseover('calendar')"
		ondateselect="callOndateselect('calendar')"
		ondateselected="callOndateselected('calendar')"
		onexpand="callOnexpand('calendar')"
		oninputblur="callOninputblur('calendar')"
		oninputchange="callOninputchange('calendar')"
		oninputclick="callOninputclick('calendar')"
		oninputfocus="callOninputfocus('calendar')"
		oninputkeydown="callOninputkeydown('calendar')"
		oninputkeypress="callOninputkeypress('calendar')"
		oninputkeyup="callOninputkeyup('calendar')"
		oninputselect="callOninputselect('calendar')"
		ontimeselect="callOntimeselect('calendar')"
		ontimeselected="callOntimeselected('calendar')">
	</rich:calendar>

	<a4j:commandButton value="testEventHandlers"
		actionListener="#{calendarHandlers.testEventHandlers}"
		reRender="handlersResult, comboBoxGrid"></a4j:commandButton>

	<h:panelGrid id="handlersResult"
		binding="#{calendarHandlers.panelGrid}" columns="2">		
	</h:panelGrid>
</f:subview>

