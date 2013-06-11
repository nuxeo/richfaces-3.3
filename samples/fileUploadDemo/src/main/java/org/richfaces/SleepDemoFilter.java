package org.richfaces;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SleepDemoFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, final ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (servletRequest.getContentType() != null && servletRequest.getContentType().startsWith("multipart/")) {
			chain.doFilter(new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
				@Override
				public ServletInputStream getInputStream() throws IOException {
					final ServletInputStream is = super.getInputStream();
					final int sleepTime = 10;
					return new ServletInputStream() {

						@Override
						public int read() throws IOException {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return is.read();
						}
						
						@Override
						public int read(byte[] b) throws IOException {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// TODO Auto-generated method stub
							return is.read(b);
						}
						
						@Override
						public int read(byte[] b, int off, int len)
								throws IOException {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// TODO Auto-generated method stub
							return is.read(b, off, len);
						}
						
						@Override
						public int readLine(byte[] b, int off, int len)
								throws IOException {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// TODO Auto-generated method stub
							return is.readLine(b, off, len);
						}
					
					};
				}
			}, response);
		} else {
			chain.doFilter(servletRequest, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
