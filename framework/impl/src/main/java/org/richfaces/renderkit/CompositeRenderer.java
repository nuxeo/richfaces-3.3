/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.renderkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.org.w3c.tidy.EntityTable;
import org.ajax4jsf.renderkit.AjaxChildrenRenderer;
import org.ajax4jsf.resource.InternetResource;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 18.12.2006
 * 
 */
public abstract class CompositeRenderer extends AjaxChildrenRenderer {
	private List<RendererContributor> renderers = new ArrayList<RendererContributor>();

	private List<AttributeParametersEncoder> parameterEncoders = new ArrayList<AttributeParametersEncoder>();
	
	private abstract static class Command {
		protected final void execute(Iterator<?> renderers, 
				FacesContext facesContext, 
				UIComponent component,
				Object argument,
				Class<?> clazz) {

			while (renderers.hasNext()) {
				RendererContributor contributor = (RendererContributor) renderers.next();
				Class<?> acceptableClass = contributor.getAcceptableClass();
				if (clazz == null || acceptableClass == null || acceptableClass.isAssignableFrom(clazz)) {
					execute(contributor, facesContext, component, argument);
				}
			}
		}

		protected abstract void execute(RendererContributor renderer, 
				FacesContext facesContext, 
				UIComponent component,
				Object argument);
	}

	private final Command DECODE_COMMAND = new Command() {

		protected void execute(RendererContributor renderer, FacesContext context, 
				UIComponent component, Object argument) {

			renderer.decode(context, component, CompositeRenderer.this);
		}
	};

	private static final Command MERGE_OPTIONS_COMMAND = new Command() {

		protected void execute(RendererContributor renderer, FacesContext context, 
				UIComponent component, Object argument) {

			ScriptOptions initialOptions = (ScriptOptions) argument;

			ScriptOptions builtOptions = renderer.buildOptions(context, component);
			if (builtOptions != null) {
				initialOptions.merge(builtOptions);
			}
		}

	};

	private static final Command MERGE_STYLE_DEPENDENCIES = new Command() {

		protected void execute(RendererContributor renderer,
				FacesContext facesContext, UIComponent component,
				Object argument) {

			String[] styleDependencies = renderer.getStyleDependencies();
			if (styleDependencies != null) {
				Set<String> styles = (Set<String>) argument;
				
				for (int i = 0; i < styleDependencies.length; i++) {
					styles.add(styleDependencies[i]);
				}
			}
		}
		
	};

	private static final Command MERGE_SCRIPT_DEPENDENCIES = new Command() {

		protected void execute(RendererContributor renderer,
				FacesContext facesContext, UIComponent component,
				Object argument) {

			String[] scriptDependencies = renderer.getScriptDependencies();
			if (scriptDependencies != null) {
				Set<String> scripts = (Set<String>) argument;
				
				for (int i = 0; i < scriptDependencies.length; i++) {
					scripts.add(scriptDependencies[i]);
				}
			}
		}
		
	};
	
	private static final Command GET_SCRIPT_CONTRIBUTIONS = new Command() {

		protected void execute(RendererContributor renderer,
				FacesContext context, UIComponent component,
				Object argument) {

			String contribution = renderer.getScriptContribution(context, component);
			if (contribution != null) {
				((List<String>) argument).add(contribution);
			}
		}
		
	};

	protected void doDecode(FacesContext context, UIComponent component) {
		super.doDecode(context, component);

		DECODE_COMMAND.execute(renderers.iterator(), context, component, null, component.getClass());
	}

	protected void mergeScriptOptions(ScriptOptions scriptOptions, FacesContext context, UIComponent component) {
		mergeScriptOptions(scriptOptions, context, component, null);
	}
	
	protected void mergeScriptOptions(ScriptOptions scriptOptions, FacesContext context, UIComponent component, Class<?> acceptableClass) {
		MERGE_OPTIONS_COMMAND.execute(renderers.iterator(), context, component, scriptOptions, acceptableClass);
	}

	protected InternetResource[] getScripts() {
		return getResources(null, MERGE_SCRIPT_DEPENDENCIES);
	}
	
	protected InternetResource[] getStyles() {
		return getResources(null, MERGE_STYLE_DEPENDENCIES);
	}
	
	protected InternetResource[] getScripts(Class<?> clazz) {
		return getResources(clazz, MERGE_SCRIPT_DEPENDENCIES);
	}
	
	protected InternetResource[] getStyles(Class<?> clazz) {
		return getResources(clazz, MERGE_STYLE_DEPENDENCIES);
	}

	private InternetResource[] getResources(Class<?> acceptableClass, Command command) {
		Set<String> scriptsSet = new LinkedHashSet<String>();

		command.execute(renderers.iterator(), null, null, scriptsSet, acceptableClass);
		
		if (!scriptsSet.isEmpty()) {
			
			Iterator<String> iterator = scriptsSet.iterator();
			InternetResource[] result = new InternetResource[scriptsSet.size()];
			
			for (int i = 0; i < result.length; i++) {
				result[i] = getResource(iterator.next());
			}
			
			return result;
		}

		return null;
	}
	
	protected String getScriptContributions(String varString, FacesContext context, UIComponent component) {
		return getScriptContributions(varString, context, component, component.getClass());
	}
	
	protected String getScriptContributions(String varString, FacesContext context, UIComponent component, Class<?> acceptableClass) {
		List<String> scriptContributions = new ArrayList<String>();
		
		GET_SCRIPT_CONTRIBUTIONS.execute(renderers.iterator(), context, component, scriptContributions, acceptableClass);
	
		StringBuffer result = new StringBuffer();
		for (Iterator<String> itr = scriptContributions.iterator(); itr.hasNext(); ) {
			result.append(varString);
			result.append(itr.next());
		}
		
		return result.toString();
	}
	
	protected void addContributor(RendererContributor renderer) {
		renderers.add(renderer);
	}
	
	protected RendererContributor[] getContributors() {
		return (RendererContributor[]) renderers.toArray(new RendererContributor[renderers.size()]);
	}
	
	protected void addParameterEncoder(AttributeParametersEncoder encoder) {
		parameterEncoders.add(encoder);
	}
	
	protected AttributeParametersEncoder[] getParameterEncoders() {
		return (AttributeParametersEncoder[]) parameterEncoders.toArray(new AttributeParametersEncoder[renderers.size()]);
	}

	public void encodeAttributeParameters(FacesContext context, UIComponent component) throws IOException {
		for (Iterator<AttributeParametersEncoder> iterator = parameterEncoders.iterator(); iterator.hasNext();) {
			AttributeParametersEncoder encoder = iterator.next();
			
			encoder.doEncode(context, component);
		}
	}
	
	protected String escapeHtmlEntities(CharSequence orig) {
		StringBuffer buff = new StringBuffer(orig);
		EntityTable defaultEntityTable = EntityTable.getDefaultEntityTable();
		Matcher matcher = Pattern.compile("\\&\\w+\\;").matcher(orig);
		int delta = 0;
		while (matcher.find()) {
			String name = matcher.group().substring(0, matcher.group().length() - 1);
			int code = defaultEntityTable.entityCode(name);
			if (0 != code) {
				String replacement = "&#" + code + ";";
				buff.replace(matcher.start() - delta, matcher.end() - delta, replacement);
				delta = delta + matcher.group().length() - replacement.length();
			}
		}
		return buff.toString();
	}
}
