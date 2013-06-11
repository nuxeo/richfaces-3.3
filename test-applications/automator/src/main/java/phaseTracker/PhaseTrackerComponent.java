/**
 * 
 */
package phaseTracker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.component.UIPanel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * @author user
 *
 */
public class PhaseTrackerComponent extends UIPanel{

	@Override
	public void encodeAll(FacesContext context) throws IOException {
		ResponseWriter responseWriter = context.getResponseWriter();
		StringWriter stringWriter = new StringWriter();
		ResponseWriter clonedWriter = responseWriter.cloneWithWriter(stringWriter);
		context.setResponseWriter(clonedWriter);
		
		super.encodeAll(context);
		clonedWriter.flush();
		context.setResponseWriter(responseWriter);
		
		String string = stringWriter.toString();
		responseWriter.write(string);
		
		ExternalContext externalContext = context.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		requestMap.put("pttc", string);
	}

	@Override
	public String getRendererType() {
		return null;
	}
	
	
}
