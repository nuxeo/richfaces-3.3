/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.ajax4jsf.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.StateManager;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.context.ContextInitParameters;
import org.ajax4jsf.model.KeepAlive;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author shura
 * 
 */
public class AjaxStateManager extends StateManager {


	public static final String CAPTURED_VIEW_STATE = "org.ajax4jsf.captured_view_state";

	private final class SeamStateManagerWrapper extends StateManager {
		protected Object getComponentStateToSave(FacesContext arg0) {
			// do nothing
			return null;
		}

		protected Object getTreeStructureToSave(FacesContext arg0) {
			// do nothing
			return null;
		}

		protected void restoreComponentState(FacesContext arg0,
				UIViewRoot arg1, String arg2) {
			// do nothing

		}

		protected UIViewRoot restoreTreeStructure(FacesContext arg0,
				String arg1, String arg2) {
			// do nothing
			return null;
		}

		public UIViewRoot restoreView(FacesContext arg0, String arg1,
				String arg2) {
			// do nothing
			return null;
		}

		@SuppressWarnings("deprecation")
		public SerializedView saveSerializedView(FacesContext context) {
			// delegate to enclosed class method.
			Object[] viewState = buildViewState(context);
			return new SerializedView(viewState[0],viewState[1]);
		}

		@Override
		public Object saveView(FacesContext context) {
			// TODO Auto-generated method stub
			return buildViewState(context);
		}
		@SuppressWarnings("deprecation")
		public void writeState(FacesContext arg0, SerializedView arg1)
				throws IOException {
			// do nothing
		}
	}

	private static final Class<StateManager> STATE_MANAGER_ARGUMENTS = StateManager.class;

	public static final int DEFAULT_NUMBER_OF_VIEWS = 16;

	public static final String AJAX_VIEW_SEQUENCE = AjaxStateManager.class.getName()
			+ ".AJAX_VIEW_SEQUENCE";
	public static final String VIEW_SEQUENCE = AjaxStateManager.class.getName()
	+ ".VIEW_SEQUENCE";

	private final StateManager parent;

	private StateManager seamStateManager;

	private final ComponentsLoader componentLoader;

	private static final Log _log = LogFactory.getLog(AjaxStateManager.class);

	public static final String VIEW_SEQUENCE_ATTRIBUTE = AjaxStateManager.class
			.getName()
			+ ".view_sequence";

	/**
	 * @param parent
	 */
	public AjaxStateManager(StateManager parent) {
		super();
		this.parent = parent;
		componentLoader = new ComponentsLoaderImpl();
		// HACK - Seam perform significant operations before save tree state.
		// Try to create it instance by reflection,
		// to call in real state saving operations.
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (null == classLoader) {
			classLoader = AjaxStateManager.class.getClassLoader();
		}
		try {
			Class<? extends StateManager> seamStateManagerClass = classLoader
					.loadClass("org.jboss.seam.jsf.SeamStateManager")
					.asSubclass(StateManager.class);
			Constructor<? extends StateManager> constructor = seamStateManagerClass
					.getConstructor(STATE_MANAGER_ARGUMENTS);
			seamStateManager = constructor
					.newInstance(new Object[] { new SeamStateManagerWrapper() });
			if (_log.isDebugEnabled()) {
				_log.debug("Create instance of the SeamStateManager");
			}
		} catch (Exception e) {
			seamStateManager = null;
			if (_log.isDebugEnabled()) {
				_log.debug("SeamStateManager is not present");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#getComponentStateToSave(javax.faces.context.FacesContext)
	 */
	protected Object getComponentStateToSave(FacesContext context) {
		Object treeState = context.getViewRoot().processSaveState(context);
		Object state[] = { treeState, getAdditionalState(context) };
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#getTreeStructureToSave(javax.faces.context.FacesContext)
	 */
	protected Object getTreeStructureToSave(FacesContext context) {
		TreeStructureNode treeStructure = new TreeStructureNode();
		treeStructure.apply(context, context.getViewRoot(),
				new HashSet<String>());
		return treeStructure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#restoreComponentState(javax.faces.context.FacesContext,
	 *      javax.faces.component.UIViewRoot, java.lang.String)
	 */
	protected void restoreComponentState(FacesContext context,
			UIViewRoot viewRoot, String renderKitId) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#restoreTreeStructure(javax.faces.context.FacesContext,
	 *      java.lang.String, java.lang.String)
	 */
	protected UIViewRoot restoreTreeStructure(FacesContext context,
			String viewId, String renderKitId) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#writeState(javax.faces.context.FacesContext,
	 *      javax.faces.application.StateManager.SerializedView)
	 */
	public void writeState(FacesContext context, Object state)
			throws IOException {
		RenderKit renderKit = getRenderKit(context);
		ResponseStateManager responseStateManager = renderKit
				.getResponseStateManager();
		Object[] stateArray = getStateArray( state );
		if(null == stateArray[0] && null == stateArray[1]){
			// Myfaces https://issues.apache.org/jira/browse/MYFACES-1753 hack.
			stateArray=new Object[]{getLogicalViewId(context),null};
		}
			writeState(context, responseStateManager, stateArray);
		if (_log.isDebugEnabled()) {
			_log.debug("Write view state to the response");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#writeState(javax.faces.context.FacesContext,
	 *      javax.faces.application.StateManager.SerializedView)
	 */
	@SuppressWarnings("deprecation")
	public void writeState(FacesContext context, SerializedView state)
			throws IOException {
		RenderKit renderKit = getRenderKit(context);
		ResponseStateManager responseStateManager = renderKit
				.getResponseStateManager();
		Object[] stateArray;
		if(null == state.getState() && null == state.getStructure()){
			// MyFaces https://issues.apache.org/jira/browse/MYFACES-1753 hack
			stateArray = new Object[]{getLogicalViewId(context),null};
		} else {
			stateArray = new Object[] {
				state.getStructure(),state.getState() };
		}
			writeState(context, responseStateManager, stateArray);
		if (_log.isDebugEnabled()) {
			_log.debug("Write view state to the response");
		}
	}

	/**
	 * @param context
	 * @param state
	 * @param responseStateManager
	 * @throws IOException
	 * @throws FacesException
	 */
	private Object[] getStateArray(Object state) throws IOException,
			FacesException {
		if (null != state && state.getClass().isArray()
				&& state.getClass().getComponentType().equals(Object.class)) {
			Object stateArray[] = (Object[]) state;
			if (2 == stateArray.length) {
				return stateArray;
			} else {
				throw new FacesException("Unexpected length of the state object array "+stateArray.length);
			}
		}  else {
			throw new FacesException("Unexpected type of the state "+state.getClass().getName());
		}
	}

	private void writeState(FacesContext context,
			ResponseStateManager responseStateManager, Object[] stateArray)
			throws IOException {
		// Capture writed state into string.
		ResponseWriter originalWriter = context.getResponseWriter();
		StringWriter buff = new StringWriter(128);
		try {
			ResponseWriter stateResponseWriter = originalWriter
					.cloneWithWriter(buff);
			context.setResponseWriter(stateResponseWriter);
			responseStateManager.writeState(context, stateArray);
			stateResponseWriter.flush();
			String stateString = buff.toString();
			originalWriter.write(stateString);
			String stateValue = getStateValue(stateString);
			context.getExternalContext().getRequestMap().put(CAPTURED_VIEW_STATE, stateValue);
			if (null != stateValue) {
			} else {
			}
		} finally {
			context.setResponseWriter(originalWriter);
		}
	}

	private static final            Pattern PATTERN = Pattern.compile(".*<input.*(?:\\svalue=[\"\'](.*)[\"\']\\s).*name=[\"']"+ResponseStateManager.VIEW_STATE_PARAM+"[\"'].*>");

	private static final            Pattern PATTERN2 = Pattern.compile(".*<input .*name=[\"']"+ResponseStateManager.VIEW_STATE_PARAM+"[\"'].*(?:\\svalue=[\"\'](.*)[\"\']\\s).*>");


	/**
     * Parse content of the writed viewState hidden input field for a state value.
     * @param input
     * @return
     */
    private String getStateValue(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if(!matcher.matches()){
                matcher = PATTERN2.matcher(input);
                if(!matcher.matches()){
                        return null;
                }
        }
        return matcher.group(1);
}

    private static final Map<String,Class<?>> PRIMITIVE_CLASSES =
          new HashMap<String,Class<?>>(9, 1.0F);

    static {
        PRIMITIVE_CLASSES.put("boolean", boolean.class);
        PRIMITIVE_CLASSES.put("byte", byte.class);
        PRIMITIVE_CLASSES.put("char", char.class);
        PRIMITIVE_CLASSES.put("short", short.class);
        PRIMITIVE_CLASSES.put("int", int.class);
        PRIMITIVE_CLASSES.put("long", long.class);
        PRIMITIVE_CLASSES.put("float", float.class);
        PRIMITIVE_CLASSES.put("double", double.class);
        PRIMITIVE_CLASSES.put("void", void.class);
    }
    
    private static final Object handleRestoreState(FacesContext context, Object state) {
		if (ContextInitParameters.isSerializeServerState(context)) {
			ObjectInputStream ois = null;
	        try {
	        	ois = new ObjectInputStream(new ByteArrayInputStream((byte[]) state)) {
	        		@Override
	        		protected Class<?> resolveClass(ObjectStreamClass desc)
	        				throws IOException, ClassNotFoundException {
	        	        String name = desc.getName();
						try {
		        			return Class.forName(name, true, 
		        	                Thread.currentThread().getContextClassLoader());
	        	        } catch (ClassNotFoundException cnfe) {
	        	        	Class<?> clazz = PRIMITIVE_CLASSES.get(name);
	        	        	if (clazz != null) {
	        	        		return clazz;
	        	        	} else {
	        	        		throw cnfe;
	        	        	}
	        	        }
	        		}
	        	};
	        	return ois.readObject();
	        } catch (Exception e) {
	            throw new FacesException(e);
	        } finally {
	            if (ois != null) {
	                try {
	                    ois.close();
	                } catch (IOException ignored) { }
	            }
	        }
		} else {
			return state;
		}
	}

	private static final Object handleSaveState(FacesContext context, Object state) {
		if (ContextInitParameters.isSerializeServerState(context)) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
			ObjectOutputStream oas = null;
			try {
				oas = new ObjectOutputStream(baos);
				oas.writeObject(state);
				oas.flush();
			} catch (Exception e) {
				throw new FacesException(e);
			} finally {
				if (oas != null) {
					try {
						oas.close();
					} catch (IOException ignored) { }
				}
			}
			return baos.toByteArray();
		} else {
			return state;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.application.StateManager#restoreView(javax.faces.context.FacesContext,
	 *      java.lang.String, java.lang.String)
	 */
	public UIViewRoot restoreView(FacesContext context, String viewId,
			String renderKitId) {
		UIViewRoot viewRoot = null;
		ResponseStateManager responseStateManager = getRenderKit(context,
				renderKitId).getResponseStateManager();
		TreeStructureNode treeStructure = null;
		Object[] state = null;
		Object[] serializedView = null;
		if (isSavingStateInClient(context)) {
			serializedView = (Object[]) responseStateManager.getState(context,
					viewId);

			if (null != serializedView) {
				treeStructure = (TreeStructureNode) serializedView[0];
				state = (Object[]) serializedView[1];
			}
		} else {
			serializedView = restoreStateFromSession(context, viewId,
					renderKitId);

			if (null != serializedView) {
				treeStructure = (TreeStructureNode) serializedView[0];
				state = (Object[]) handleRestoreState(context, serializedView[1]);
			}
		}

		if (null != treeStructure) {
			viewRoot = (UIViewRoot) treeStructure.restore(componentLoader);
			if (null != viewRoot && null != state) {
				viewRoot.processRestoreState(context, state[0]);
				restoreAdditionalState(context, state[1]);
			}
		}
		return viewRoot;

	}

	@SuppressWarnings("deprecation")
	public SerializedView saveSerializedView(FacesContext context) {
		Object[] stateViewArray;
		if (null == seamStateManager) {
			stateViewArray = buildViewState(context);
		} else {
			// Delegate save method to seam State Manager.
			stateViewArray=(Object[]) seamStateManager.saveView(context);
		}
		return new SerializedView(stateViewArray[0],stateViewArray[1]);
	}

	@Override
	public Object saveView(FacesContext context) {
		if (null == seamStateManager) {
			return buildViewState(context);
		} else {
			// Delegate save method to seam State Manager.
			return seamStateManager.saveView(context);
		}
	}
	/**
	 * @param context
	 * @return
	 * @see javax.faces.application.StateManager#isSavingStateInClient(javax.faces.context.FacesContext)
	 */
	public boolean isSavingStateInClient(FacesContext context) {
		return parent.isSavingStateInClient(context);
	}

	protected Object[] restoreStateFromSession(FacesContext context,
			String viewId, String renderKitId) {
		String id = restoreLogicalViewId(context, viewId, renderKitId);
		StateHolder stateHolder = getStateHolder(context);
		Object[] restoredState = stateHolder.getState(context, viewId, id);
		
		if (restoredState != null && id != null) {
			context.getExternalContext().getRequestMap().put(AJAX_VIEW_SEQUENCE, id);
		}
		
		return restoredState;
	}

	/**
	 * @param context
	 * @return
	 */
	protected Object[] buildViewState(FacesContext context) {
		Object[] viewStateArray = null;
		UIViewRoot viewRoot = context.getViewRoot();
		if (null != viewRoot && !viewRoot.isTransient()) {
			TreeStructureNode treeStructure = (TreeStructureNode) getTreeStructureToSave(context);
			Object state = getComponentStateToSave(context);
			if (isSavingStateInClient(context)) {
				viewStateArray = new Object[]{treeStructure, state};
			} else {
				viewStateArray = saveStateInSession(context, treeStructure,
						handleSaveState(context, state));
			}

		}
		return viewStateArray;
	}

	/**
	 * @param context
	 * @param treeStructure
	 * @param state
	 * @return
	 */
	protected Object[] saveStateInSession(FacesContext context,
			Object treeStructure, Object state) {
		Object[] serializedView;
		UIViewRoot viewRoot = context.getViewRoot();
		StateHolder stateHolder = getStateHolder(context);
		String id = getLogicalViewId(context);
		stateHolder.saveState(context, viewRoot.getViewId(), id, new Object[] {
				treeStructure, state });
		serializedView = new Object[]{id, null};
		return serializedView;
	}

	/**
	 * @param context
	 * @return
	 */
	protected StateHolder getStateHolder(FacesContext context) {
		return AjaxStateHolder.getInstance(context);
	}

	protected Object getAdditionalState(FacesContext context) {
		Map<String, Object> keepAliveBeans = new HashMap<String, Object>();
		Map<String, Object> requestMap = context.getExternalContext()
				.getRequestMap();
		// Save all objects form request map wich marked by @KeepAlive
		// annotations
		for (Entry<String, Object> requestEntry : requestMap.entrySet()) {
			Object bean = requestEntry.getValue();
			// check value for a NULL -
			// http://jira.jboss.com/jira/browse/RF-3576
			if (null != bean
					&& bean.getClass().isAnnotationPresent(KeepAlive.class)) {
				keepAliveBeans.put(requestEntry.getKey(), bean);
			}
		}
		if (keepAliveBeans.size() > 0) {
			return UIComponentBase.saveAttachedState(context, keepAliveBeans);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected void restoreAdditionalState(FacesContext context, Object state) {
		if (null != state) {
			boolean isAjax = AjaxContext.getCurrentInstance(context).isAjaxRequest();
			
			// Append all saved beans to the request map.
			Map beansMap = (Map) UIComponentBase.restoreAttachedState(context,
					state);
			Map<String, Object> requestMap = context.getExternalContext()
					.getRequestMap();
			for (Object key : beansMap.keySet()) {
				Object bean = beansMap.get(key);
				if (bean != null) {
					KeepAlive annotation = bean.getClass().getAnnotation(KeepAlive.class);
					if (annotation != null) {
						if (!isAjax && annotation.ajaxOnly()) {

							//skip ajax-only beans for non-ajax requests
							continue;
						}
					}
				}

				requestMap.put((String) key, bean);
			}
		}
	}

	/**
	 * Restore logical view id from request.
	 * 
	 * @param context
	 * @param viewId
	 * @param renderKitId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected String restoreLogicalViewId(FacesContext context, String viewId,
			String renderKitId) {
		String id = (String) getRenderKit(context, renderKitId)
				.getResponseStateManager().getTreeStructureToRestore(context,
						viewId);
		return id;
	}

	/**
	 * Return logical Id for current request view state. For a faces requests,
	 * generate sequence numbers. For a ajax request, attempt to re-use id from
	 * request submit.
	 * 
	 * @param context
	 * @return
	 */
	protected String getLogicalViewId(FacesContext context) {
		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(context);
		ExternalContext externalContext = context.getExternalContext();
		Object id=null;
		Map<String, Object> requestMap = externalContext.getRequestMap();
		id = requestMap.get(ajaxContext.isAjaxRequest()?AJAX_VIEW_SEQUENCE:VIEW_SEQUENCE);
		if (null != id) {
			return id.toString();
		}
		// Store sequence in session, to avoyd claster configuration problem
		// see https://javaserverfaces.dev.java.net/issues/show_bug.cgi?id=662
		Object session = externalContext.getSession(true);
		int viewSequence;
		synchronized (session) {
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			Integer sequence = (Integer) sessionMap
					.get(VIEW_SEQUENCE_ATTRIBUTE);
			if (null != sequence) {
				viewSequence = sequence.intValue();
			} else {
				viewSequence = 0;
			}
			if (viewSequence++ == Character.MAX_VALUE) {
				viewSequence = 0;
			}
			sessionMap.put(VIEW_SEQUENCE_ATTRIBUTE, new Integer(viewSequence));
		}
		String logicalViewId = UIViewRoot.UNIQUE_ID_PREFIX + ((int) viewSequence);
		// Store new viewId in the request parameters, to avoid re-increments in the same request.
		requestMap.put(VIEW_SEQUENCE,logicalViewId);
		return logicalViewId;
	}

	protected RenderKit getRenderKit(FacesContext context) {
		String renderKitId = null;
		UIViewRoot viewRoot = context.getViewRoot();
		if (null != viewRoot) {
			renderKitId = viewRoot.getRenderKitId();
		}
		if (null == renderKitId) {
			renderKitId = context.getApplication().getViewHandler()
					.calculateRenderKitId(context);
		}
		return getRenderKit(context, renderKitId);
	}

	protected RenderKit getRenderKit(FacesContext context, String renderKitId) {
		RenderKit renderKit = context.getRenderKit();
		if (null == renderKit) {
			RenderKitFactory factory = (RenderKitFactory) FactoryFinder
					.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
			renderKit = factory.getRenderKit(context, renderKitId);
		}
		return renderKit;
	}

}
