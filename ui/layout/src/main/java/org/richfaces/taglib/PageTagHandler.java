/**
 * 
 */
package org.richfaces.taglib;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;
import com.sun.facelets.tag.jsf.ComponentSupport;
import com.sun.facelets.tag.jsf.core.ViewHandler;

/**
 * This handler does same work as Facelet's {@link ViewHandler} does to setup page encoding and content type.
 * 
 * @author asmirnov
 *
 */
public class PageTagHandler extends ComponentHandler {

	private TagAttribute locale;
	private TagAttribute contentType;
	private TagAttribute encoding;

	public PageTagHandler(ComponentConfig config) {
		super(config);
        this.locale = this.getAttribute("locale");
        this.contentType = this.getAttribute("contentType");
        this.encoding = this.getAttribute("encoding");
	}
	
	@Override
	protected void onComponentPopulated(FaceletContext ctx, UIComponent c,
			UIComponent parent) {
		super.onComponentPopulated(ctx, c, parent);
        UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
        if (root != null) {
            if (this.locale != null) {
                root.setLocale(ComponentSupport.getLocale(ctx,
                        this.locale));
            }
            if (this.contentType != null) {
                String v = this.contentType.getValue(ctx);
                ctx.getFacesContext().getExternalContext().getRequestMap().put("facelets.ContentType", v);
                root.getAttributes().put("contentType", v);
            }
            if (this.encoding != null) {
                String v = this.encoding.getValue(ctx);
                ctx.getFacesContext().getExternalContext().getRequestMap().put("facelets.Encoding", v);
                root.getAttributes().put("encoding", v);
            }
        }
	}

}
