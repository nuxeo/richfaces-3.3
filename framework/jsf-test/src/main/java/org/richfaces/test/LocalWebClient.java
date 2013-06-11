/**
 * 
 */
package org.richfaces.test;

import org.richfaces.test.staging.StagingServer;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebConnection;

/**
 * Modified version of the HtmlUnit {@link WebClient}. This subclass uses {@link LocalWebConnection} by default,
 * to perform requests to the local saging server {@link StagingServer} instead of real network request.
 * It is also setup synchonous ajax controller {@link WebClient#setAjaxController(com.gargoylesoftware.htmlunit.AjaxController)},
 * to avoid thread syncronisation problem. 
 * @author asmirnov
 *
 */
@SuppressWarnings("serial")
public class LocalWebClient extends WebClient {
	
	private final StagingServer server;
	
	private transient WebConnection webConnection;

	/**
	 * Create WebConnection instance for the given {@link StagingServer}
	 * @param server test server instance.
	 */
	public LocalWebClient(StagingServer server) {
		super();
		this.server = server;
		setAjaxController(new NicelyResynchronizingAjaxController());
	}

	/**
	 * Create WebConnection instance for the given {@link StagingServer} and browser version.
	 * @param server test server instance.
	 * @param browserVersion
	 */
	public LocalWebClient(StagingServer server,BrowserVersion browserVersion) {
		super(browserVersion);
		this.server = server;
		setAjaxController(new NicelyResynchronizingAjaxController());
	}

	/**
	 * Overwride default webConnection.
	 * @return the webConnection
	 */
	@Override
	public WebConnection getWebConnection() {
		if (this.webConnection == null) {
			this.webConnection = new LocalWebConnection(server);			
		}

		return this.webConnection;
	}

	/**
	 * @param webConnection the webConnection to set
	 */
	@Override
	public void setWebConnection(WebConnection webConnection) {
		this.webConnection = webConnection;
	}

}
