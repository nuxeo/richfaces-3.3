<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<f:subview id="treeSwingSubviewID">
	<h3>Click tree node to delete it. Only leaf node could be deleted. </h3>
	<rich:tree id="tree" switchType="#{treeSwing.switchType}"
		value="#{treeSwing.top}" var="data"
		showConnectingLines="#{treeSwing.showConnectingLines}"
		nodeSelectListener="#{treeSwing.selectionListener}"
		rendered="#{treeSwing.rendered}" binding="#{treeSwing.tree}"
		ajaxSubmitSelection="true"
		onbeforedomupdate="#{event.onbeforedomupdate}"
		onclick="#{event.onclick}" oncollapse="#{event.oncollapse}"
		oncomplete="#{event.oncomplete}" ondblclick="#{event.ondblclick}"
		ondragend="#{event.ondragend}" ondragenter="#{event.ondragenter}"
		ondragexit="#{event.ondragexit}" ondragstart="#{event.ondragstart}"
		ondrop="#{event.ondrop}" ondropend="#{event.ondropend}"
		ondropout="#{event.ondropout}" ondropover="#{event.ondropover}"
		onexpand="#{event.onexpand}" onkeydown="#{event.onkeydown}"
		onkeypress="#{event.onkeypress}" onkeyup="#{event.onkeyup}"
		onmousedown="#{event.onmousedown}" onmousemove="#{event.onmousemove}"
		onmouseout="#{event.onmouseout}" onmouseover="#{event.onmouseover}"
		onmouseup="#{event.onmouseup}" rowKeyVar="row"
		onselected="return confirm('Do you want to delete the row you select?');"
		oncontextmenu="#{event.oncontextmenu}"
		disableKeyboardNavigation="#{treeSwing.disableKeyboardNavigation}">
	</rich:tree>
</f:subview>