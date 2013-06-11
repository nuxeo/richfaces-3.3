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

package org.richfaces.skin;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.faces.FacesException;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Test for Skin/skin factory methods.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/10 14:28:13 $
 *
 */
public class SkinTestCase extends AbstractAjax4JsfTestCase {

    public SkinTestCase(String name) {
	super(name);
    }

    public void setUp() throws Exception {
	super.setUp();
    }

    public void tearDown() throws Exception {
	SkinFactory.reset();
	super.tearDown();
    }

    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getInstance()'
     */
    public void testGetInstance() {
	SkinFactory factory = SkinFactory.getInstance();
	SkinFactory factory1 = SkinFactory.getInstance();
	assertSame(factory,factory1);
    }

    private void addParameters(Object[][] strings) {
	Map<Object,Object> baseMap = new HashMap<Object, Object>();
	for (Object[] objects : strings) {
	    baseMap.put(objects[0], objects[1]);
	}

	externalContext.getRequestMap().put("test", baseMap);
    }

    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkin(FacesContext)'
     */
    public void testGetSkin() {
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "test");

	addParameters(new Object[][]{new Object[] {"bean", "test.value"}});

	SkinFactory factory = SkinFactory.getInstance();
	// test call
	Skin skin = factory.getSkin(facesContext);
	assertNotNull("Null skin!",skin);
	// test properties
	assertEquals("string",skin.getParameter(facesContext, "string.property"));
	assertEquals("base.string",skin.getParameter(facesContext, "base.property"));
	assertEquals("test.value",skin.getParameter(facesContext, "bind.property"));
	//		assertEquals("HTML_BASIC",skin.getRenderKitId(mockContext));
    }

    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkin(FacesContext)'
     */
    public void testSkinReferences() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "test");

	// test call
	Skin skin = factory.getSkin(facesContext);
	assertNotNull("Null skin!",skin);
	assertEquals("default",skin.getParameter(facesContext, "c"));
	assertEquals("yyy",skin.getParameter(facesContext, "y"));
    }

    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkin(FacesContext)'
     */
    public void testSkinReferences1() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "style");
	servletContext.addInitParameter(SkinFactory.BASE_SKIN_PARAMETER, "style_base");

	// test call
	Skin skin = factory.getSkin(facesContext);
	assertNotNull("Null skin!",skin);
	assertEquals("#F5F0E7",skin.getParameter(facesContext, "intermediateTextColor"));
	assertEquals("10px",skin.getParameter(facesContext, "intermediateTextSize"));
	assertEquals("#F5F0E7",skin.getParameter(facesContext, "generalTextColor"));

	assertEquals("white.textcolor",skin.getParameter(facesContext, "additionalTextColor"));
    }

    public void testBaseSkin() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "dynatest");
	servletContext.addInitParameter(SkinFactory.BASE_SKIN_PARAMETER, "dynatest_base");
	addParameters(new Object[][]{new Object[] {"bean", "dynabase1"}});

	Skin skin = factory.getSkin(facesContext);
	assertEquals("default", skin.getParameter(facesContext, "default"));
	assertEquals("itself", skin.getParameter(facesContext, "selfValue"));
	assertEquals("#AAA", skin.getParameter(facesContext, "customFormColor"));

	Map map = (Map) externalContext.getRequestMap().get("test");
	map.put("bean", "dynabase2");

	assertEquals("xxx", skin.getParameter(facesContext, "default"));
	assertEquals("itself", skin.getParameter(facesContext, "selfValue"));
	assertEquals("#AAA", skin.getParameter(facesContext, "customFormColor"));
    }
    
    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkin(FacesContext)'
     */
    public void testCyclicSkinReferences() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "cyclic");

	try {
	    Skin skin = factory.getSkin(facesContext);
	    skin.getParameter(facesContext, "x");
	    fail();
	} catch(FacesException e){
	    //it's ok
	}
    }

    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkin(FacesContext)'
     */
    public void testBadSkinReferences() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "noref");

	// test call
	try {
	    Skin skin = factory.getSkin(facesContext);
	    skin.getParameter(facesContext, "x");
	    fail();
	} catch(FacesException e){
	    //it's ok
	}
    }
    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkin(FacesContext)'
     */
    public void testGetBindedSkin() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "#{test.skin}");

	addParameters(new Object[][] {
		new Object[] {"skin", "bindedtest"},
		new Object[] {"bean", "binded.test.value"}
	});

	// test call
	Skin skin = factory.getSkin(facesContext);
	assertNotNull("Null skin!",skin);
	// test properties
	assertEquals("bindedstring",skin.getParameter(facesContext, "string.property"));
	//		assertEquals("base.string",skin.getParameter(mockContext,"base.property"));
	assertEquals("binded.test.value",skin.getParameter(facesContext, "bind.property"));
	assertEquals("TEST",skin.getRenderKitId(facesContext));
    }

    public void testSkinHash() {
	SkinFactory factory = SkinFactory.getInstance();
	servletContext.addInitParameter(SkinFactory.SKIN_PARAMETER, "#{test.skin}");

	addParameters(new Object[][] {
		new Object[] {"skin", "bindedtest"},
		new Object[] {"bean", "binded.test.value"}
	});

	Skin skin = factory.getSkin(facesContext);
	Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();

	// test properties
	int hash = skin.hashCode(facesContext);
	assertTrue(requestMap.containsKey(BasicSkinImpl.REQUEST_HASH_CODES_MAP_PARAMETER));
	assertEquals(hash,skin.hashCode(facesContext));
	requestMap.remove(BasicSkinImpl.REQUEST_HASH_CODES_MAP_PARAMETER);
	assertEquals(hash,skin.hashCode(facesContext));
	// setup Value binding mock for different value - hash must differ.
	requestMap.remove(BasicSkinImpl.REQUEST_HASH_CODES_MAP_PARAMETER);

	Map map = (Map) requestMap.get("test");
	map.put("bean", "other.test.value");

	assertFalse( hash==skin.hashCode(facesContext) );

    }

    /*
     * Test method for 'org.richfaces.skin.SkinFactory.getSkinName(FacesContext)'
     */
    public void testGetSkinName() {

    }
    
    public void testGetTheme() throws Exception {
    	SkinFactory factory = SkinFactory.getInstance();
    	Theme theme = factory.getTheme(facesContext, "test");
    	assertNotNull(theme);
    	assertEquals("foo", theme.getRendererType());
	}

}
