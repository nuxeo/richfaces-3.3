/**
 * 
 */
package org.richfaces.test.staging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author asmirnov
 * 
 */
abstract class StagingHttpResponse implements HttpServletResponse {


	private static final Logger log = ServerLogger.CONNECTION.getLogger();

	
	private int status = 200;

	private String redirectLocation = null;

	private String errorMessage = null;

	private int bufferSize = 8196;

	private StringWriter outputWriter;

	private ByteArrayOutputStream outputStream;

	private PrintWriter printWriter;

	private ServletOutputStream servletOutputStream;

	private Locale locale = Locale.US;
	
	private String contentType="text";
	
	private int contentLength = Integer.MIN_VALUE;
	
	private String encoding = StagingHttpRequest.UTF8;


	private final Map<String, String[]> headers= new HashMap<String, String[]>();


	/**
	 * @return the headers
	 */
	Map<String, String[]> getHeaders() {
		return headers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#addDateHeader(java.lang.String,
	 * long)
	 */
	public void addDateHeader(String name, long date) {
		// TODO - locale support ?
		addHeader(name, new Date(date).toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#addHeader(java.lang.String,
	 * java.lang.String)
	 */
	public void addHeader(String name, String value) {
		String[] values = headers.get(name);
		if (null == values) {
			values = new String[1];
		} else {
			String[] newValues = new String[values.length + 1];
			System.arraycopy(values, 0, newValues, 0, values.length);
			values = newValues;
		}
		values[values.length - 1] = value;
		headers.put(name, values);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#addIntHeader(java.lang.String,
	 * int)
	 */
	public void addIntHeader(String name, int value) {
		addHeader(name, String.valueOf(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#containsHeader(java.lang.String)
	 */
	public boolean containsHeader(String name) {
		return headers.containsKey(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#encodeRedirectURL(java.lang.String
	 * )
	 */
	public String encodeRedirectURL(String url) {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#encodeRedirectUrl(java.lang.String
	 * )
	 */
	public String encodeRedirectUrl(String url) {
		return encodeRedirectURL(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#encodeURL(java.lang.String)
	 */
	public String encodeURL(String url) {
		return url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#encodeUrl(java.lang.String)
	 */
	public String encodeUrl(String url) {
		return encodeURL(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#sendError(int)
	 */
	public void sendError(int sc) throws IOException {
		status = sc;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#sendError(int,
	 * java.lang.String)
	 */
	public void sendError(int sc, String msg) throws IOException {
		status = sc;
		errorMessage = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#sendRedirect(java.lang.String)
	 */
	public void sendRedirect(String location) throws IOException {
		redirectLocation = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#setDateHeader(java.lang.String,
	 * long)
	 */
	public void setDateHeader(String name, long date) {
		// TODO - locale support ?
		setHeader(name, new Date(date).toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#setHeader(java.lang.String,
	 * java.lang.String)
	 */
	public void setHeader(String name, String value) {
		headers.put(name, new String[]{value});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponse#setIntHeader(java.lang.String,
	 * int)
	 */
	public void setIntHeader(String name, int value) {
		setHeader(name, String.valueOf(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int)
	 */
	public void setStatus(int sc) {
		status = sc;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponse#setStatus(int,
	 * java.lang.String)
	 */
	public void setStatus(int sc, String sm) {
		status = sc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#flushBuffer()
	 */
	public void flushBuffer() throws IOException {
		// do nothing
		log.info("unimplemented response method flushBuffer");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getBufferSize()
	 */
	public int getBufferSize() {
		return bufferSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getCharacterEncoding()
	 */
	public String getCharacterEncoding() {
		return encoding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getContentType()
	 */
	public String getContentType() {
		return contentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getLocale()
	 */
	public Locale getLocale() {
		return locale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getOutputStream()
	 */
	public ServletOutputStream getOutputStream() throws IOException {
		if (null != this.outputWriter) {
			throw new IllegalStateException();
		}
		if (this.outputStream == null) {
			this.outputStream = new ByteArrayOutputStream(getBufferSize());
			servletOutputStream = new ServletOutputStream(){

				@Override
				public void write(int b) throws IOException {
					StagingHttpResponse.this.outputStream.write(b);				
				}
				
			};			
		}
		return servletOutputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#getWriter()
	 */
	public PrintWriter getWriter() throws IOException {
		if (null != this.outputStream) {
			throw new IllegalStateException();
		}
		if (this.outputWriter == null) {
			this.outputWriter = new StringWriter(getBufferSize());
			printWriter = new PrintWriter(outputWriter);
		}
		return printWriter;
	}

	
	public String getWriterContent() {
		if(null != this.outputWriter){
			return this.outputWriter.toString();
		}
		return null;
	}
	
	public byte[] getStreamContent() {
		if(null!=outputStream){
			return outputStream.toByteArray();
		}
		return null;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#isCommitted()
	 */
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		log.info("unimplemented response method isCommited");
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#reset()
	 */
	public void reset() {
		if(isCommitted()){
			throw new IllegalStateException();
		}
		resetBuffer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#resetBuffer()
	 */
	public void resetBuffer() {
		if(isCommitted()){
			throw new IllegalStateException();
		}
		this.servletOutputStream = null;
		this.outputStream = null;
		this.printWriter = null;
		this.outputWriter = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#setBufferSize(int)
	 */
	public void setBufferSize(int size) {
		this.bufferSize = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#setCharacterEncoding(java.lang.String)
	 */
	public void setCharacterEncoding(String charset) {
		encoding = charset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#setContentLength(int)
	 */
	public void setContentLength(int len) {
		this.contentLength = len;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#setContentType(java.lang.String)
	 */
	public void setContentType(String type) {
		if(null == type){
			throw new NullPointerException();
		}
		int i = type.indexOf(';');
		if(i>=0){
			setHeader("Content-Type", type);
			contentType = type.substring(0, i).trim();
			i=type.lastIndexOf('=');
			if(i>=0){
				setCharacterEncoding(type.substring(i+1).trim());
			}
		} else {
			setHeader("Content-Type", type+";charset="+getCharacterEncoding());
			contentType = type;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponse#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale loc) {
		this.locale  = loc;

	}

	/**
	 * @return the status
	 */
	int getStatus() {
		return status;
	}

	/**
	 * @return the contentLength
	 */
	int getContentLength() {
		return contentLength;
	}

	/**
	 * @return the redirectLocation
	 */
	String getRedirectLocation() {
		return redirectLocation;
	}

	/**
	 * @return the errorMessage
	 */
	String getErrorMessage() {
		return errorMessage;
	}

}
