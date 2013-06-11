<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<f:subview id="paint2DSubviewID">
		<rich:paint2D id="paint2dID" binding="#{paint2D.htmlPaint2D}" cacheable="#{paint2D.cacheable}" paint="#{paint2D.paint}" data="#{paintData}" width="#{paint2D.width}" height="#{paint2D.height}"
			align="#{paint2D.align}" hspace="#{paint2D.hspace}" vspace="#{paint2D.vspace}" bgcolor="#{paint2D.bgcolor}"
			format="#{paint2D.format}" title="#{paint2D.title}" styleClass="#{paint2D.styleString}" border="#{paint2D.border}"
			rendered="#{paint2D.rendered}" />
</f:subview>
