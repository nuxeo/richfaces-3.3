/**
 * 
 */
package org.richfaces.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.httpclient.NameValuePair;
import org.richfaces.test.staging.StagingConnection;

import com.gargoylesoftware.htmlunit.WebRequestSettings;
import com.gargoylesoftware.htmlunit.WebResponse;

/**
 * This implementation realise WebResponse wrapper for a staging server
 * connection. This class is used by the {@link LocalWebClient}, but also can be used to analise response rendering:
 * <pre>
 * ............
 * @Test 
 * public void testRender() {
 * setupFacesRequest();
 * // Prepare view etc
 * ..................
 * lifecycle.render(facesContext);
 * WebClient webClient = new LocalWebClient(facesServer);
 * HtmlPage page = (HtmlPage) webClient.loadWebResponseInto(new LocalWebResponse(connection), webClient.getCurrentWindow());
 * // analyse response
 * assertTrue(....)
 * }
 * </pre>
 * 
 * @author asmirnov
 * 
 */
public class LocalWebResponse implements WebResponse {
	
	private WebRequestSettings settings;
	
	private final StagingConnection serverConnection;
	
	private final long loadTime;

	public LocalWebResponse(StagingConnection serverConnection,long l) {
		this.serverConnection = serverConnection;
		this.loadTime = l;
	}

	public LocalWebResponse(WebRequestSettings settings,
			StagingConnection connection, long l) {
		this(connection,l);
		this.settings = settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getContentAsStream()
	 */
	public InputStream getContentAsStream() throws IOException {
		return new ByteArrayInputStream(getResponseBody());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getContentAsString()
	 */
	public String getContentAsString() {
		return serverConnection.getContentAsString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getContentCharSet()
	 */
	public String getContentCharSet() {
		return serverConnection.getResponseCharacterEncoding();
	}

	public String getContentType() {
		return serverConnection.getResponseContentType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gargoylesoftware.htmlunit.WebResponse#getLoadTimeInMilliSeconds()
	 */
	public long getLoadTimeInMilliSeconds() {
		return loadTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getRequestMethod()
	 */
	public com.gargoylesoftware.htmlunit.HttpMethod getRequestMethod() {
		return com.gargoylesoftware.htmlunit.HttpMethod
				.valueOf(serverConnection.getRequestMethod().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getRequestSettings()
	 */
	public WebRequestSettings getRequestSettings() {
		if (settings == null) {
			settings = new WebRequestSettings(this.getUrl(), getRequestMethod());
		}
		return settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getResponseBody()
	 */
	public byte[] getResponseBody() {
		return serverConnection.getResponseBody();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gargoylesoftware.htmlunit.WebResponse#getResponseHeaderValue(java
	 * .lang.String)
	 */
	public String getResponseHeaderValue(String headerName) {
		String[] values = serverConnection.getResponseHeaders().get(headerName);
		if(null != values && values.length >0){
			return values[0];
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getStatusCode()
	 */
	public int getStatusCode() {
		return serverConnection.getResponseStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getStatusMessage()
	 */
	public String getStatusMessage() {
		return serverConnection.getErrorMessage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getUrl()
	 */
	public URL getUrl() {
		return serverConnection.getUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getResponseHeaders()
	 */
	public List<NameValuePair> getResponseHeaders() {
		ArrayList<NameValuePair> headers = new ArrayList<NameValuePair>(10);
		for (Entry<String, String[]> entry : serverConnection
				.getResponseHeaders().entrySet()) {
			for (String value : entry.getValue()) {
				headers.add(new NameValuePair(entry.getKey(), value));
			}
		}
		int contentLength = serverConnection.getResponseContentLength();
		if(contentLength>=0){
			headers.add(new NameValuePair("Content-Length", String.valueOf(contentLength)));
		}
		return headers;
	}

	/* (non-Javadoc)
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getContentAsBytes()
	 */
	public byte[] getContentAsBytes() {
		return serverConnection.getResponseBody();
	}

	/* (non-Javadoc)
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getContentAsString(java.lang.String)
	 */
	public String getContentAsString(String encoding) {
		byte[] body = serverConnection.getResponseBody();
		String content;
		try {
			content = new String(body,encoding);
		} catch (UnsupportedEncodingException e) {
			content = new String(body);
		}
		return content;
	}

	/* (non-Javadoc)
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getLoadTime()
	 */
	public long getLoadTime() {
		return loadTime;
	}

	/* (non-Javadoc)
	 * @see com.gargoylesoftware.htmlunit.WebResponse#getRequestUrl()
	 */
	public URL getRequestUrl() {
		// TODO Auto-generated method stub
		return serverConnection.getUrl();
	}
}