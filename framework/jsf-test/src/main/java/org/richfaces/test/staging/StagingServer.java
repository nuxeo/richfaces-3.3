package org.richfaces.test.staging;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.naming.NamingException;
import javax.naming.spi.NamingManager;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspFactory;

import org.richfaces.test.TestException;

/**
 * This class implements limited Http servlet container 2.5 functionality. It is
 * designed for a test purposes only ,so that has a limitations:
 * <ul>
 * <li>supports local calls only.</li>
 * <li>java code only configuration ( no xml files processed ).</li>
 * <li>just one web application, 'deployed' in the root context.</li>
 * <li>only one client session</li>
 * <li>communicates by the local java calls only, no network connection</li>
 * <li>no JSP compilator support ( but it is possible to register pre-compiled
 * pages as servlets)</li>
 * <li>...</li>
 * </ul>
 * It is main part of the test framework.
 * 
 */
public class StagingServer {

	private static final Class<ServletRequestListener> REQUEST_LISTENER_CLASS = ServletRequestListener.class;

	private static final Class<ServletRequestAttributeListener> REQUEST_ATTRIBUTE_LISTENER_CLASS = ServletRequestAttributeListener.class;

	private static final Class<ServletContextListener> CONTEXT_LISTENER_CLASS = ServletContextListener.class;

	private static final Class<HttpSessionListener> SESSION_LISTENER_CLASS = HttpSessionListener.class;

	private static final Class<HttpSessionAttributeListener> SESSION_ATTRIBUTE_LISTENER_CLASS = HttpSessionAttributeListener.class;

	private static final Logger log = ServerLogger.SERVER.getLogger();

	private final List<RequestChain> servlets = new ArrayList<RequestChain>();

	private RequestChain defaultServlet = new ServletContainer(null,
			new StaticServlet());

	private final List<EventListener> contextListeners = new ArrayList<EventListener>();

	private final Map<String, String> initParameters = new HashMap<String, String>();

	private final ServerResource serverRoot = new ServerResourcesDirectory();

	private final Map<String, String> mimeTypes = new HashMap<String, String>();

	private InvocationListener invocationListener;

	private final StagingServletContext context = new LocalContext();

	private ServletContext contextProxy;

	private HttpSession currentSession = null;
	
	private ThreadLocal<HttpSession> sessions = new ThreadLocal<HttpSession>();
	
	private List<ServerHttpSession> sessionInstances = new ArrayList<ServerHttpSession>();
	
	private boolean sessionPerThread = false;


	private boolean initialised = false;

	/**
	 * This inner class links ServletContext calls to the server instance.
	 * 
	 * @author asmirnov
	 * 
	 */
	private class LocalContext extends StagingServletContext {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.ServletContext#getMimeType(java.lang.String)
		 */
		public String getMimeType(String file) {
			int indexOfDot = file.lastIndexOf('.');
			// get extension.
			if (indexOfDot >= 0) {
				file = file.substring(indexOfDot);
			}
			return mimeTypes.get(file);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingServletContext#valueBound(javax
		 * .servlet.ServletContextAttributeEvent)
		 */
		@Override
		protected void valueBound(ServletContextAttributeEvent event) {
			// inform listeners.
			for (EventListener listener : contextListeners) {
				if (listener instanceof ServletContextAttributeListener) {
					ServletContextAttributeListener contextListener = (ServletContextAttributeListener) listener;
					contextListener.attributeAdded(event);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingServletContext#valueReplaced(javax
		 * .servlet.ServletContextAttributeEvent)
		 */
		@Override
		protected void valueReplaced(ServletContextAttributeEvent event) {
			// inform listeners.
			for (EventListener listener : contextListeners) {
				if (listener instanceof ServletContextAttributeListener) {
					ServletContextAttributeListener contextListener = (ServletContextAttributeListener) listener;
					contextListener.attributeReplaced(event);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingServletContext#valueUnbound(javax
		 * .servlet.ServletContextAttributeEvent)
		 */
		@Override
		protected void valueUnbound(ServletContextAttributeEvent event) {
			// inform listeners.
			for (EventListener listener : contextListeners) {
				if (listener instanceof ServletContextAttributeListener) {
					ServletContextAttributeListener contextListener = (ServletContextAttributeListener) listener;
					contextListener.attributeRemoved(event);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingServletContext#getServerResource
		 * (java.lang.String)
		 */
		@Override
		protected ServerResource getServerResource(String path) {
			return serverRoot.getResource(new ServerResourcePath(path));
		}

	}

	/**
	 * This inner class links session object calls to the server instance.
	 * 
	 * @author asmirnov
	 * 
	 */
	private class ServerHttpSession extends StagingHttpSession {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.servlet.http.HttpSession#getServletContext()
		 */
		public ServletContext getServletContext() {
			return context;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingHttpSession#valueBound(javax.servlet
		 * .http.HttpSessionBindingEvent)
		 */
		@Override
		protected void valueBound(
				final HttpSessionBindingEvent sessionBindingEvent) {
			// inform session listeners.
			fireEvent(SESSION_ATTRIBUTE_LISTENER_CLASS,
					new EventInvoker<HttpSessionAttributeListener>() {
						public void invoke(HttpSessionAttributeListener listener) {
							listener.attributeAdded(sessionBindingEvent);
						}
					});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingHttpSession#valueUnbound(javax.
		 * servlet.http.HttpSessionBindingEvent)
		 */
		@Override
		protected void valueUnbound(
				final HttpSessionBindingEvent sessionBindingEvent) {
			// inform session listeners.
			fireEvent(SESSION_ATTRIBUTE_LISTENER_CLASS,
					new EventInvoker<HttpSessionAttributeListener>() {
						public void invoke(HttpSessionAttributeListener listener) {
							listener.attributeRemoved(sessionBindingEvent);
						}
					});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.richfaces.test.staging.StagingHttpSession#valueReplaced(javax
		 * .servlet.http.HttpSessionBindingEvent)
		 */
		@Override
		protected void valueReplaced(
				final HttpSessionBindingEvent sessionBindingEvent) {
			// inform session listeners.
			fireEvent(SESSION_ATTRIBUTE_LISTENER_CLASS,
					new EventInvoker<HttpSessionAttributeListener>() {
						public void invoke(HttpSessionAttributeListener listener) {
							listener.attributeReplaced(sessionBindingEvent);
						}
					});
		}
		
		@Override
		public void invalidate() {
			super.invalidate();
			setCurrentSession(null);
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	private <T extends EventListener> void fireEvent(Class<T> listenerClass,
			EventInvoker<T> invoker) {
		for (EventListener listener : contextListeners) {
			if (listenerClass.isInstance(listener)) {
				invoker.invoke((T) listener);
			}
		}
		
		
	}

	/**
	 * Append executable server object ( {@link Filter} or {@link Servlet} to
	 * the server.
	 * 
	 * @param servlet
	 */
	public void addServlet(RequestChain servlet) {
		servlets.add(servlet);
	}

	/**
	 * Add servlet to the server.
	 * 
	 * @param mapping
	 *            servlet mapping
	 * @param servlet
	 *            {@link Servlet} instance.
	 */
	public void addServlet(String mapping, Servlet servlet) {
		servlets.add(new ServletContainer(mapping, servlet));
	}

	/**
	 * Get appropriate object ( Filter or Servlet ) for a given path.
	 * 
	 * @param path
	 *            request path relative to web application context.
	 * @return Appropriate Filter or Servlet executable object to serve given
	 *         request. If no servlet was registered for the given path, try to
	 *         send requested object directly.
	 */
	public RequestChain getServlet(String path) {
		RequestChain result = null;
		for (RequestChain servlet : servlets) {
			if (servlet.isApplicable(path)) {
				result = servlet;
				break;
			}
		}
		if (null == result) {
			// Is requested object exist in the virtual content ?
			try {
				URL resource = context.getResource(path);
				if (null != resource) {
					// Serve it directly.
					result = defaultServlet;
				}
			} catch (MalformedURLException e) {
				log.warning("Mailformed request URL " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * Add web application init parameter.
	 * 
	 * @param name
	 * @param value
	 */
	public void addInitParameter(String name, String value) {
		initParameters.put(name, value);
	}

	/**
	 * Add default mime type for serve files with given extension.
	 * 
	 * @param extension
	 * @param mimeType
	 */
	public void addMimeType(String extension, String mimeType) {
		mimeTypes.put(extension, mimeType);
	}

	/**
	 * Add java resource to the virtual web application content. This method
	 * makes all parent directories as needed.
	 * 
	 * @param path
	 *            path to the file in the virtual web server.
	 * @param resource
	 *            path to the resource in the classpath, as required by the
	 *            {@link ClassLoader#getResource(String)}.
	 */
	public void addResource(String path, String resource) {
		ServerResourcePath resourcePath = new ServerResourcePath(path);
		serverRoot.addResource(resourcePath, new ClasspathServerResource(
				resource));
	}

	/**
	 * Add resource to the virtual veb application content. This method makes
	 * all parent directories as needed.
	 * 
	 * @param path
	 *            path to the file in the virtual web server.
	 * @param resource
	 *            {@code URL} to the file content.
	 */
	public void addResource(String path, URL resource) {
		serverRoot.addResource(new ServerResourcePath(path),
				new UrlServerResource(resource));
	}

	/**
	 * Add all resources from the directory to the virtual web application
	 * content.
	 * 
	 * @param path
	 *            name of the target directory in the virtual web application.
	 *            If no such directory exists, it will be created, as well as
	 *            all parent directories as needed.
	 * @param resource
	 *            {@code URL} to the source directory or any file in the source
	 *            directory. Only 'file' or 'jar' protocols are supported. If
	 *            this parameter points to a file, it will be converted to a
	 *            enclosing directory.
	 */
	public void addResourcesFromDirectory(String path, URL resource) {
		ServerResourcePath resourcePath = new ServerResourcePath(path);
		ServerResource baseDirectory = serverRoot.getResource(resourcePath);
		if (null == baseDirectory) {
			// Create target directory.
			baseDirectory = new ServerResourcesDirectory();
			serverRoot.addResource(resourcePath, baseDirectory);
		}
		String protocol = resource.getProtocol();
		if ("jar".equals(protocol)) {
			addResourcesFromJar(resource, baseDirectory);
		} else if ("file".equals(protocol)) {
			addResourcesFromFile(resource, baseDirectory);
		} else {
			throw new TestException("Unsupported protocol " + protocol);
		}
	}

	/**
	 * Add all files from the directory to the virtual web application
	 * content.
	 * 
	 * @param path
	 *            name of the target directory in the virtual web application.
	 *            If no such directory exists, it will be created, as well as
	 *            all parent directories as needed.
	 * @param resource
	 *            {@code File} of the source directory or any file in the source
	 *            directory. If this parameter points to a file, it will be converted to a
	 *            enclosing directory.
	 */
	public void addResourcesFromDirectory(String path, File directory) {
		ServerResourcePath resourcePath = new ServerResourcePath(path);
		ServerResource baseDirectory = serverRoot.getResource(resourcePath);
		if (null == baseDirectory) {
			// Create target directory.
			baseDirectory = new ServerResourcesDirectory();
			serverRoot.addResource(resourcePath, baseDirectory);
		}
		if (!directory.isDirectory()) {
			directory = directory.getParentFile();
		}
		if(!directory.exists()){
			throw new TestException("directory does not exist:"+directory.getAbsolutePath());
		}
		try {
			addFiles(baseDirectory, directory);
		} catch (MalformedURLException e) {
			throw new TestException(e);
		}
	}

	/**
	 * Internal method used by the
	 * {@link #addResourcesFromDirectory(String, URL)} to process 'file'
	 * protocol.
	 * 
	 * @param resource
	 *            source directory.
	 * @param baseDirectory
	 *            target virtual directory.
	 */
	protected void addResourcesFromFile(URL resource,
			ServerResource baseDirectory) {
		File file = new File(resource.getPath());
		if (!file.isDirectory()) {
			file = file.getParentFile();
		}
		try {
			addFiles(baseDirectory, file);
		} catch (MalformedURLException e) {
			throw new TestException(e);
		}
	}

	/**
	 * Internal method used by the
	 * {@link #addResourcesFromDirectory(String, URL)} to process 'jar'
	 * protocol.
	 * 
	 * @param resource
	 *            URL to the any object in the source directory.
	 * @param baseDirectory
	 *            target virtual directory.
	 */
	protected void addResourcesFromJar(URL resource,
			ServerResource baseDirectory) {
		try {
			String jarPath = resource.getPath();
			String entry = jarPath.substring(jarPath.indexOf('!') + 2);
			jarPath = jarPath.substring(0, jarPath.indexOf('!'));
			File file = new File(new URI(jarPath));
			ZipFile zip = new ZipFile(file);
			Enumeration<? extends ZipEntry> entries = zip.entries();
			entry = entry.substring(0, entry.lastIndexOf('/') + 1);
			while (entries.hasMoreElements()) {
				ZipEntry zzz = (ZipEntry) entries.nextElement();
				if (zzz.getName().startsWith(entry) && !zzz.isDirectory()) {
					String relativePath = zzz.getName().substring(
							entry.length());
					URL relativeResource = new URL(resource, relativePath);
					baseDirectory.addResource(new ServerResourcePath("/"
							+ relativePath), new UrlServerResource(
							relativeResource));
				}
			}

		} catch (IOException e) {
			throw new TestException("Error read Jar content", e);
		} catch (URISyntaxException e) {
			throw new TestException(e);
		}
	}

	/**
	 * Internal reccursive method process directory content and all
	 * subdirectories.
	 * 
	 * @param baseDirectory
	 * @param file
	 * @throws MalformedURLException
	 */
	protected void addFiles(ServerResource baseDirectory, File file)
			throws MalformedURLException {
		File[] files = file.listFiles();
		for (File subfile : files) {
			if (subfile.isDirectory()) {
				ServerResourcePath serverResourcePath = new ServerResourcePath("/"
						+ subfile.getName()+"/");
				ServerResourcesDirectory subDir = new ServerResourcesDirectory();
				baseDirectory.addResource(serverResourcePath, subDir);
				addFiles(subDir, subfile);
			} else {
				ServerResourcePath serverResourcePath = new ServerResourcePath("/"
						+ subfile.getName());
				UrlServerResource resource = new UrlServerResource(subfile
						.toURL());
				baseDirectory.addResource(serverResourcePath, resource);

			}
		}
	}

	/**
	 * Add web-application wide listenes, same as it is defined by the
	 * &lt;listener&gt; element in the web.xml file for a real server. Supported
	 * listener types:
	 * <ul>
	 * <li>{@link ServletContextListener}</li>
	 * <li>{@link ServletContextAttributeListener}</li>
	 * <li>{@link HttpSessionListener}</li>
	 * <li>{@link HttpSessionAttributeListener}</li>
	 * <li>{@link ServletRequestListener}</li>
	 * <li>{@link ServletRequestAttributeListener}</li>
	 * </ul>
	 * 
	 * @param listener
	 *            web listener instance.
	 */
	public void addWebListener(EventListener listener) {
		contextListeners.add(listener);
	}

	/**
	 * Getter method for 'interceptor' events listener.
	 * 
	 * @return the invocationListener
	 */
	public InvocationListener getInvocationListener() {
		return invocationListener;
	}

	/**
	 * Set listener which gets events on all calls to any methods of the
	 * {@link ServletContext}, {@link HttpSession}, {@link HttpServletRequest},
	 * {@link HttpServletResponse} instances in the virtual server. this
	 * interceptor can be used to check internal calls in the tests .
	 * 
	 * @param invocationListener
	 *            the invocationListener to set
	 */
	public void setInvocationListener(InvocationListener invocationListener) {
		this.invocationListener = invocationListener;
	}

	/**
	 * Create instance of the {@link InvocationHandler} for the proxy objects.
	 * This handler fire events to the registered {@link InvocationListener} (
	 * if present ) after target object method call.
	 * 
	 * @return the invocationHandler
	 */
	InvocationHandler getInvocationHandler(final Object target) {
		return new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				InvocationListener listener = getInvocationListener();
				try {
					Object result = method.invoke(target, args);
					if (null != listener) {
						listener.afterInvoke(new InvocationEvent(target,
								method, args, result));
					}
					return result;
				} catch (Throwable e) {
					if (null != listener) {
						listener.processException(new InvocationErrorEvent(
								target, method, args, e));
					}
					throw e;
				}
			}

		};

	}

	public boolean isSessionPerThread() {
		return sessionPerThread;
	}

	public void setSessionPerThread(boolean sessionPerThread) {
		this.sessionPerThread = sessionPerThread;
	}
	
	
	HttpSession getCurrentSession() {
		if (isSessionPerThread()) {
			return sessions.get();
		} else {
			return currentSession;
		}
	}
	
	void setCurrentSession(HttpSession session) {
		if (isSessionPerThread()) {
			sessions.set(session);
		} else {
			this.currentSession=session;
		}
		
	}

	/**
	 * Get virtual server session object. Create new one if necessary.
	 * 
	 * @return instance of the virtual server session.
	 */
	public HttpSession getSession() {
		return getSession(true);
	}

	/**
	 * 
	 * Returns the current <code>HttpSession</code> associated with this server
	 * or, if there is no current session and <code>create</code> is true,
	 * returns a new session. Staging server supports only one session per
	 * instance, different clients for the same server instance does not
	 * supported.
	 * 
	 * <p>
	 * If <code>create</code> is <code>false</code> and the request has no valid
	 * <code>HttpSession</code>, this method returns <code>null</code>.
	 * 
	 * 
	 * @param create
	 *            <code>true</code> to create a new session for this request if
	 *            necessary; <code>false</code> to return <code>null</code> if
	 *            there's no current session
	 * 
	 * 
	 * @return the <code>HttpSession</code> associated with this server instance
	 *         or <code>null</code> if <code>create</code> is <code>false</code>
	 *         and the server has no session
	 * 
	 */
	public synchronized HttpSession getSession(boolean create) {
		if (!initialised) {
			throw new TestException("Staging server have not been initialised");
		}
		HttpSession httpSession = this.getCurrentSession();
		if (null == httpSession && create) {
			ServerHttpSession sessionImpl = new ServerHttpSession();
			// Create proxy objects.
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			if (null == loader) {
				loader = this.getClass().getClassLoader();
			}
			httpSession = (HttpSession) Proxy.newProxyInstance(loader,
					new Class[] { HttpSession.class },
					getInvocationHandler(sessionImpl));
			setCurrentSession(httpSession);
			// inform session listeners.
			final HttpSessionEvent event = new HttpSessionEvent(httpSession);
			fireEvent(SESSION_LISTENER_CLASS,
					new EventInvoker<HttpSessionListener>() {
						public void invoke(HttpSessionListener listener) {
							listener.sessionCreated(event);
						}
					});
			sessionInstances.add(sessionImpl);
		}
		return httpSession;
	}

	/**
	 * Virtual server initialization. This method creates instances of the
	 * {@link ServletContext}, {@link JspFactory}, informs
	 * {@link ServletContextListener} ind inits all {@link Filter} and
	 * {@link Servlet} instances. It should be called from test setUp method to
	 * prepare testing environment.
	 */
	public void init() {
		log.info("Init staging server");
		// Create Jsp factory
		JspFactory.setDefaultFactory(new StaggingJspFactory(this.context));
		// Create init parameters
		context.addInitParameters(initParameters);
		// Inform listeners
		// Create proxy objects.
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (null == loader) {
			loader = this.getClass().getClassLoader();
		}
		this.contextProxy = (ServletContext) Proxy.newProxyInstance(loader,
				new Class[] { ServletContext.class },
				getInvocationHandler(context));
		// Create default servlet
		final ServletContextEvent event = new ServletContextEvent(context);
		fireEvent(CONTEXT_LISTENER_CLASS,
				new EventInvoker<ServletContextListener>() {
					public void invoke(ServletContextListener listener) {
						listener.contextInitialized(event);
					}
				});
		// Init servlets
		try {
			for (RequestChain servlet : servlets) {
				// init servlet
				servlet.init(context);
			}
			defaultServlet.init(context);
		} catch (ServletException e) {
			throw new TestException("Servlet initialisation error ", e);
		}
		try {
			NamingManager.setInitialContextFactoryBuilder(new StagingInitialContextFactoryBuilder());
		} catch (NamingException e) {
			log.warning("Error set initial context factory builder.");
		} catch (IllegalStateException e) {
			log.warning("Initial context factory builder already set.");
		}
		this.initialised = true;
	}

	/**
	 * Stop wirtual server. This method informs {@link ServletContextListener}
	 * ind inits all {@link Filter} and {@link Servlet} instances, as well
	 * remove all internal objects. It should be called from the testt thearDown
	 * method to clean up testing environment.
	 * 
	 */
	public void destroy() {
		if (!initialised) {
			throw new TestException("Staging server have not been initialised");
		}
		this.initialised = false;
		// Destroy session
		// TODO - destroy all threads.
		for (Iterator<ServerHttpSession> sessionIterator = sessionInstances.iterator(); sessionIterator.hasNext();) {
			ServerHttpSession session = sessionIterator.next();
			// inform session listeners.
			final HttpSessionEvent event = new HttpSessionEvent(session);
			fireEvent(SESSION_LISTENER_CLASS,
					new EventInvoker<HttpSessionListener>() {
						public void invoke(HttpSessionListener listener) {
							listener.sessionDestroyed(event);
						}
					});
			session.invalidate();
			sessionIterator.remove();
		}
		setCurrentSession(null);
		// Inform listeners
		final ServletContextEvent event = new ServletContextEvent(context);
		fireEvent(CONTEXT_LISTENER_CLASS,
				new EventInvoker<ServletContextListener>() {
					public void invoke(ServletContextListener listener) {
						listener.contextDestroyed(event);
					}
				});
		// Destroy servlets
		for (RequestChain servlet : servlets) {
			servlet.destroy();
		}
		defaultServlet.destroy();
		// Create Jsp factory
		JspFactory.setDefaultFactory(null);
		this.contextProxy = null;
		log.info("Staging server have been destroyed");
	}

	/**
	 * Get virtual connection to the given URL. Even thought for an http request
	 * to the external servers, only local connection to the virtual server will
	 * be created.
	 * 
	 * @param url
	 *            request url.
	 * @return local connection to the appropriate servlet in the virtual
	 *         server.
	 * @throws {@link TestException} if no servlet found to process given URL.
	 */
	public StagingConnection getConnection(URL url) {
		if (!initialised) {
			throw new TestException("Staging server have not been initialised");
		}
		return new StagingConnection(this, url);
	}

	/**
	 * Get instance of virtual web application context.
	 * 
	 * @return context instance.
	 */
	public ServletContext getContext() {
		if (!initialised) {
			throw new TestException("Staging server have not been initialised");
		}
		return contextProxy;
	}

	/**
	 * Inform {@link ServletRequestListener} instances. For internal use only.
	 * 
	 * @param request
	 *            started request.
	 */
	void requestStarted(ServletRequest request) {
		final ServletRequestEvent event = new ServletRequestEvent(context,
				request);
		fireEvent(REQUEST_LISTENER_CLASS,
				new EventInvoker<ServletRequestListener>() {
					public void invoke(ServletRequestListener listener) {
						listener.requestInitialized(event);

					}
				});
	}

	/**
	 * Inform {@link ServletRequestListener} instances. For internal use only.
	 * 
	 * @param request
	 *            finished request.
	 */
	void requestFinished(ServletRequest request) {
		final ServletRequestEvent event = new ServletRequestEvent(context,
				request);
		fireEvent(REQUEST_LISTENER_CLASS,
				new EventInvoker<ServletRequestListener>() {
					public void invoke(ServletRequestListener listener) {
						listener.requestDestroyed(event);
					}
				});
	}

	void requestAttributeAdded(ServletRequest request, String name, Object o) {
		final ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(
				context, request, name, o);
		fireEvent(REQUEST_ATTRIBUTE_LISTENER_CLASS,
				new EventInvoker<ServletRequestAttributeListener>() {
					public void invoke(ServletRequestAttributeListener listener) {
						listener.attributeAdded(event);
					}
				});
	}

	void requestAttributeRemoved(ServletRequest request, String name,
			Object removed) {
		final ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(
				context, request, name, removed);
		fireEvent(REQUEST_ATTRIBUTE_LISTENER_CLASS,
				new EventInvoker<ServletRequestAttributeListener>() {
					public void invoke(ServletRequestAttributeListener listener) {
						listener.attributeRemoved(event);
					}
				});
	}

	void requestAttributeReplaced(ServletRequest request, String name,
			Object value) {
		final ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(
				context, request, name, value);
		fireEvent(REQUEST_ATTRIBUTE_LISTENER_CLASS,
				new EventInvoker<ServletRequestAttributeListener>() {
					public void invoke(ServletRequestAttributeListener listener) {
						listener.attributeReplaced(event);
					}
				});
	}
}
