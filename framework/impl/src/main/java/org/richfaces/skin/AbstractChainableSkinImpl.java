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

package org.richfaces.skin;

import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;

/**
 * @author nick belaevski
 * @since 3.2
 */

public abstract class AbstractChainableSkinImpl extends BasicSkinImpl {

    AbstractChainableSkinImpl(Map properties) {
	super(properties);
    }

    protected abstract Skin getBaseSkin(FacesContext context);
    
    protected Object localResolveSkinParameter(FacesContext context, String name) {
	return getSkinParams().get(name);
    }
    
    protected boolean localContainsProperty(FacesContext context, String name) {
	return getSkinParams().containsKey(name);
    }

    private static abstract class Operation {
	public Object doChainedCall(FacesContext context, AbstractChainableSkinImpl impl, String name, int[] singleInt) {
	    return impl.executeOperation(context, this, name, singleInt);
	}
	
	public abstract Object doLocalCall(FacesContext context, AbstractChainableSkinImpl impl, String name);

	public abstract Object doExternalCall(FacesContext context, Skin impl, String name);
    }

    private static final Operation RESOLVE = new Operation() {

	public Object doExternalCall(FacesContext context, Skin impl, String name) {
	    //TODO add warning because substitution can work incorrect and cyclic references
	    //won't be caught
	    return impl.getParameter(context, name);
	}

	public Object doLocalCall(FacesContext context, AbstractChainableSkinImpl impl, String name) {
	    return impl.localResolveSkinParameter(context, name);
	}
	
    };
    
    private static final Operation CONTAINS = new Operation() {

	private Object wrapBoolean(boolean value) {
	    return value ? Boolean.TRUE : null;
	}
	
	public Object doExternalCall(FacesContext context, Skin impl, String name) {
	    return wrapBoolean(impl.containsProperty(name));
	}

	public Object doLocalCall(FacesContext context, AbstractChainableSkinImpl impl, String name) {
	    return wrapBoolean(impl.localContainsProperty(context, name));
	}
	
    };

    protected Object executeOperation(FacesContext context, Operation operation, String name, int[] singleInt) {
	if (singleInt[0]++ > 1000) {
	    throw new FacesException(Messages.getMessage(
		    Messages.SKIN_CYCLIC_REFERENCE, name));
	}

	Object object = operation.doLocalCall(context, this, name);
	if (object == null) {
	    Skin baseSkin = getBaseSkin(context);
	    if (baseSkin != null) {
		if (baseSkin instanceof AbstractChainableSkinImpl) {
		    AbstractChainableSkinImpl skinImpl = (AbstractChainableSkinImpl) baseSkin;
		    object = operation.doChainedCall(context, skinImpl, name, singleInt);
		} else {
		    object = operation.doExternalCall(context, baseSkin, name);
		}
	    }
	}
	
	return object;
    }
    
    protected Object resolveSkinParameter(FacesContext context, String name, int[] singleInt) {
	return executeOperation(context, RESOLVE, name, singleInt);
    }
    
    protected boolean containsProperty(FacesContext context, String name, int[] singleInt) {
	return Boolean.TRUE.equals(executeOperation(context, CONTAINS, name, singleInt));
    }

    protected Object resolveSkinParameter(FacesContext context, String name) {
	int[] singleInt = new int[] {0};
	Object resolvedParameter = resolveSkinParameter(context, name, singleInt);
	
	while (resolvedParameter instanceof String) {
	    String string = (String) resolvedParameter;
	    if (string.length() > 0 && string.charAt(0) == '&') {
		resolvedParameter = resolveSkinParameter(context, string.substring(1), singleInt);
		if (resolvedParameter == null) {
		    throw new FacesException(Messages.getMessage(
			    Messages.SKIN_ILLEGAL_REFERENCE, name));
		}
	    } else {
		break;
	    }
	}
	
	return resolvedParameter;
    }

    /* (non-Javadoc)
     * @see org.richfaces.skin.Skin#containsProperty(java.lang.String)
     */
    public boolean containsProperty(String name) {
	return containsProperty(FacesContext.getCurrentInstance(), name, new int[] {0});
    }

    protected int computeHashCode(FacesContext context) {
	int hash = super.computeHashCode(context);
	Skin baseSkin = getBaseSkin(context);
	if (baseSkin != null) {
	    hash = 31*hash + baseSkin.hashCode(context);
	}

        return hash;
    }
    
}
