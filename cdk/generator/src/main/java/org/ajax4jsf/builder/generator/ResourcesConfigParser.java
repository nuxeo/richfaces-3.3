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

package org.ajax4jsf.builder.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.RendererBean;
import org.ajax4jsf.builder.config.TagBean;
import org.ajax4jsf.builder.config.TagHandlerBean;
import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.builder.TemplateCompiler;
import org.ajax4jsf.templatecompiler.elements.RootElement;
import org.ajax4jsf.templatecompiler.elements.TemplateElement;
import org.ajax4jsf.templatecompiler.elements.vcp.FResourceTemplateElement;
import org.ajax4jsf.templatecompiler.elements.vcp.HeaderResourceElement;
import org.ajax4jsf.templatecompiler.elements.vcp.HeaderScriptsElement;
import org.ajax4jsf.templatecompiler.elements.vcp.HeaderStylesElement;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */
public class ResourcesConfigParser {

	private JSFGeneratorConfiguration config;

	private Logger log;

	private File templates;
	
	private ResourcesConfigGeneratorBean resourcesConfigGeneratorBean;
	
	private Map<String, ResourcesConfigGeneratorBean> componentResourcesMap;
	
	public ResourcesConfigParser(JSFGeneratorConfiguration config, Logger log) {
		super();

		this.config = config;
		this.log = log;
		this.resourcesConfigGeneratorBean = new ResourcesConfigGeneratorBean();
		this.componentResourcesMap = new HashMap<String, ResourcesConfigGeneratorBean>();
	}

	public File getTemplates() {
		return templates;
	}
	
	public void setTemplates(File templates) {
		this.templates = templates;
	}
	
	private void addResources(ResourcesConfigGeneratorBean configBean,
			TemplateElement templateElement, String packageName,
			RendererBean renderer, BuilderConfig builderConfig)
			throws ClassNotFoundException {
		if (templateElement instanceof FResourceTemplateElement) {
			FResourceTemplateElement resourceTemplateElement = (FResourceTemplateElement) templateElement;
			String name = resourceTemplateElement.getName();

			configBean.addResource(name, packageName, null, true);
		} else if (templateElement instanceof RootElement) {
			RootElement rootElement = (RootElement) templateElement;

			CompilationContext compilationContext = rootElement
					.getComponentBean();

			addResource(configBean, renderer, builderConfig, compilationContext);
		} else if (templateElement instanceof HeaderResourceElement) {
			HeaderResourceElement resourceElement = (HeaderResourceElement) templateElement;

			ResourceType type = null;
			if (templateElement instanceof HeaderScriptsElement) {
				type = ResourceType.SCRIPT;
			} else if (templateElement instanceof HeaderStylesElement) {
				type = ResourceType.STYLE;
			}

			String[] paths = resourceElement.getResourcePaths();
			if (paths != null) {
				for (int i = 0; i < paths.length; i++) {
					String string = paths[i];

					configBean.addResource(string, packageName, type, false);
				}
			}
		}

		ArrayList<TemplateElement> subElements = templateElement
				.getSubElements();
		for (TemplateElement element : subElements) {
			addResources(configBean, element, packageName, renderer,
					builderConfig);
		}
	}

	private String getResourceKey(String packageName, Object resource) {
		try {
			Class<? extends Object> resourceClass = resource.getClass();
			Method method = resourceClass.getMethod("getKey");
			String resourceKey = (String) method.invoke(resource);
			
			if (resourceKey != null && !resourceKey.equals(resourceClass.getName())) {
				ClassLoader loader = config.getClassLoader();

				if (loader.getResource(resourceKey) == null) {
					String packagePath = packageName.replace('.', '/');
					if (packagePath.length() != 0) {
						packagePath += "/"; 
					}
					
					if (loader.getResource(packagePath + resourceKey) == null) {
						resourceKey = null;
					}
				} else {
					if (!resourceKey.startsWith("/")) {
						resourceKey = "/" + resourceKey;
					}
				}
			}
			
			return resourceKey;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}
	
	private String addResource(ResourcesConfigGeneratorBean configBean,
			RendererBean renderer, BuilderConfig builderConfig,
			CompilationContext compilationContext)
			throws ClassNotFoundException {
		String packageName;
		String classname = renderer.getClassname();
		int idx = classname.lastIndexOf('.');
		if (idx != -1) {
			packageName = classname.substring(0, idx);
		} else {
			packageName = "";
		}

		ClassLoader loader = builderConfig.getLoader();
		Class<?> cl = null;

		try {
			cl = loader.loadClass(classname);
		} catch (ClassNotFoundException e) {
			String superclass = compilationContext.getBaseclassPackageName()
					+ "." + compilationContext.getBaseclassName();
			if (superclass != null) {
				cl = loader.loadClass(superclass);
			}
		}

		if (cl != null) {
			try {
				GetResourceInterceptor interceptor = new GetResourceInterceptor(builderConfig.getLoader());

				Object instance = AbstractClassStubBuilder.buildStub(cl,
						interceptor, builderConfig.getLoader());

				//clear list - some resources could be requested during class/instance initialization
				Object[] result = null;
				Method method = null;
				Set<String> locatedResources = new HashSet<String>();
				
				Class<?> cl1 = instance.getClass();
				while (cl1 != null && method == null) {
					try {
						method = cl1.getDeclaredMethod("getStyles");
					} catch (NoSuchMethodException e) {
						cl1 = cl1.getSuperclass();
					}
				}

				if (method != null) {
					method.setAccessible(true);
					result = (Object[]) method.invoke(instance);
					if (result instanceof Object[]) {
						for (Object object : result) {
							String resourceName = interceptor.getResourceName(object);

							if (resourceName == null) {
								resourceName = getResourceKey(packageName, object);
							}
							
							if (resourceName != null) {
								locatedResources.add(resourceName);
								configBean.addResource(resourceName, packageName, ResourceType.STYLE,
										false);
							}
						}
					}
				}

				method = null;
				cl1 = instance.getClass();
				while (cl1 != null && method == null) {
					try {
						method = cl1.getDeclaredMethod("getScripts");
					} catch (NoSuchMethodException e) {
						cl1 = cl1.getSuperclass();
					}
				}

				if (method != null) {
					method.setAccessible(true);
					result = (Object[]) method.invoke(instance);
					if (result instanceof Object[]) {
						for (Object object : result) {
							String resourceName = interceptor.getResourceName(object);

							if (resourceName == null) {
								resourceName = getResourceKey(packageName, object);
							}
							
							if (resourceName != null) {
								locatedResources.add(resourceName);
								configBean.addResource(resourceName, packageName, ResourceType.SCRIPT,
										false);
							}
						}
					}

					List<String> list = interceptor.getList();
					if (list != null) {
						list.removeAll(locatedResources);
						for (String resourceName : list) {
							log.warn("Error detecting resource type: " + resourceName);
							configBean.addResource(resourceName, packageName, null,
									false);
						}
					}
				}
			} catch (SecurityException e) {
				builderConfig.getLog().error(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				builderConfig.getLog().error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				builderConfig.getLog().error(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				builderConfig.getLog().error(e.getMessage(), e);
			}
		}
		return packageName;
	}

	private void addResources(ResourcesConfigGeneratorBean configBean,
			RendererBean renderer, BuilderConfig builderConfig)
			throws CompilationException, IOException, ClassNotFoundException {
		if (null != renderer) {
			CompilationContext compilationContext = new RendererCompilationContext(
					log, config.getClassLoader(), config);

			if (renderer.isGenerate()) {
				String templateString = renderer.getTemplate();
				if (templateString != null) {
					File template;
					if (null != getTemplates()) {
						template = new File(getTemplates(), templateString);
					} else {
						template = new File(templateString);
					}

					TemplateCompiler templateCompiler = new TemplateCompiler();
					InputStream templateStream = new FileInputStream(template);
					templateCompiler.processing(templateStream,
							compilationContext);

					TemplateElement root = compilationContext.getTree();

					String classname = renderer.getClassname();
					String packageName;
					int idx = classname.lastIndexOf('.');
					if (idx != -1) {
						packageName = classname.substring(0, idx);
					} else {
						packageName = "";
					}

					addResources(configBean, root, packageName, renderer,
							builderConfig);
				}
			} else {
				addResource(configBean, renderer, builderConfig,
						compilationContext);
			}
		}
	}

	public void parse(BuilderConfig config) throws CompilationException, IOException, ClassNotFoundException {
		List<ComponentBean> components = config.getComponents();
		for (ComponentBean componentBean : components) {
			ResourcesConfigGeneratorBean localBeanInstance = new ResourcesConfigGeneratorBean();
			RendererBean rendererBean = componentBean.getRenderer();

			addResources(localBeanInstance, rendererBean, config);

			String componentName = null;
			
			TagBean tag = componentBean.getTag();
			if (tag != null) {
				componentName = tag.getName();
			}
			
			if (componentName == null) {
				TagHandlerBean taghandler = componentBean.getTaghandler();
				if (taghandler != null) {
					componentName = taghandler.getName();
				}
			}
			
			if (componentName != null) {
				this.componentResourcesMap.put(componentName, localBeanInstance);
			}

			this.resourcesConfigGeneratorBean.merge(localBeanInstance);
		}

		List<RendererBean> renderers = config.getRenderers();
		for (RendererBean rendererBean : renderers) {
			addResources(this.resourcesConfigGeneratorBean, rendererBean, config);
		}
	}
	
	public ResourcesConfigGeneratorBean getResourcesConfigGeneratorBean() {
		return resourcesConfigGeneratorBean;
	}
	
	public Map<String, ResourcesConfigGeneratorBean> getComponentResourcesMap() {
		return componentResourcesMap;
	}
}

class GetResourceInterceptor implements MethodInterceptor {

	private static final Class<?>[] SIGNATURE = new Class<?>[] { String.class };

	private ClassLoader classLoader;
	
	private List<String> list = new ArrayList<String>();

	private Map<String, Object> resources = new HashMap<String, Object>();
	
	private Map<Object, String> resourcesInverse = new IdentityHashMap<Object, String>();

	public GetResourceInterceptor(ClassLoader loader) {
		this.classLoader = loader;
	}

	public List<String> getList() {
		return list;
	}
	
	public String getResourceName(Object resource) {
		return resourcesInverse.get(resource);
	}

	public void clearList() {
		list.clear();
	}

	public Object intercept(Object instance, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {

		if ("getResource".equals(method.getName())
				&& Arrays.equals(SIGNATURE, method.getParameterTypes())) {

			String resourceName = (String) args[0];
			list.add(resourceName);

			Object resource = resources.get(resourceName);
			if (resource == null) {
				Class<?> returnType = method.getReturnType();
				if (returnType.isInterface()) {
					Object interfaceStub = AbstractClassStubBuilder.buildInterfaceStub(returnType, classLoader);
					resources.put(resourceName, interfaceStub);
					resourcesInverse.put(interfaceStub, resourceName);
				
					return interfaceStub;
				}
			}
			
			return null;
		} else {
			return methodProxy.invokeSuper(instance, args);
		}
	}

}