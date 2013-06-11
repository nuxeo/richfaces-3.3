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

package org.ajax4jsf.webapp.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import org.ajax4jsf.renderkit.RendererUtils.HTML;


/**
 * Base tag for all components with common Html attributes.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:59:55 $
 *
 */
public abstract class HtmlComponentTagBase extends UIComponentTagBase {
    //HTML universal attributes
    private ValueExpression _dir;
    private ValueExpression _lang;
    private ValueExpression _style;
    private ValueExpression _styleClass;
    private ValueExpression _title;

    //HTML event handler attributes
    private ValueExpression _onclick;
    private ValueExpression _ondblclick;
    private ValueExpression _onkeydown;
    private ValueExpression _onkeypress;
    private ValueExpression _onkeyup;
    private ValueExpression _onmousedown;
    private ValueExpression _onmousemove;
    private ValueExpression _onmouseout;
    private ValueExpression _onmouseover;
    private ValueExpression _onmouseup;

    public void release() {
        super.release();

        _dir=null;
        _lang=null;
        _style=null;
        _styleClass=null;
        _title=null;
        _onclick=null;
        _ondblclick=null;
        _onkeydown=null;
        _onkeypress=null;
        _onkeyup=null;
        _onmousedown=null;
        _onmousemove=null;
        _onmouseout=null;
        _onmouseover=null;
        _onmouseup=null;

    }

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);
        setProperty(component, HTML.dir_ATTRIBUTE, _dir);
        setProperty(component, HTML.lang_ATTRIBUTE, _lang);
        setProperty(component, HTML.style_ATTRIBUTE, _style);
        setProperty(component, HTML.title_ATTRIBUTE, _title);
        setProperty(component, HTML.STYLE_CLASS_ATTR, _styleClass);
        setProperty(component, HTML.onclick_ATTRIBUTE, _onclick);
        setProperty(component, HTML.ondblclick_ATTRIBUTE, _ondblclick);
        setProperty(component, HTML.onmousedown_ATTRIBUTE, _onmousedown);
        setProperty(component, HTML.onmouseup_ATTRIBUTE, _onmouseup);
        setProperty(component, HTML.onmouseover_ATTRIBUTE, _onmouseover);
        setProperty(component, HTML.onmousemove_ATTRIBUTE, _onmousemove);
        setProperty(component, HTML.onmouseout_ATTRIBUTE, _onmouseout);
        setProperty(component, HTML.onkeypress_ATTRIBUTE, _onkeypress);
        setProperty(component, HTML.onkeydown_ATTRIBUTE, _onkeydown);
        setProperty(component, HTML.onkeyup_ATTRIBUTE, _onkeyup);
    }

    public void setStyleClass(ValueExpression styleClass)
    {
        _styleClass = styleClass;
    }

    public void setDir(ValueExpression dir)
    {
        _dir = dir;
    }

    public void setLang(ValueExpression lang)
    {
        _lang = lang;
    }

    public void setStyle(ValueExpression style)
    {
        _style = style;
    }

    public void setTitle(ValueExpression title)
    {
        _title = title;
    }

    public void setOnclick(ValueExpression onclick)
    {
        _onclick = onclick;
    }

    public void setOndblclick(ValueExpression ondblclick)
    {
        _ondblclick = ondblclick;
    }

    public void setOnmousedown(ValueExpression onmousedown)
    {
        _onmousedown = onmousedown;
    }

    public void setOnmouseup(ValueExpression onmouseup)
    {
        _onmouseup = onmouseup;
    }

    public void setOnmouseover(ValueExpression onmouseover)
    {
        _onmouseover = onmouseover;
    }

    public void setOnmousemove(ValueExpression onmousemove)
    {
        _onmousemove = onmousemove;
    }

    public void setOnmouseout(ValueExpression onmouseout)
    {
        _onmouseout = onmouseout;
    }

    public void setOnkeypress(ValueExpression onkeypress)
    {
        _onkeypress = onkeypress;
    }

    public void setOnkeydown(ValueExpression onkeydown)
    {
        _onkeydown = onkeydown;
    }

    public void setOnkeyup(ValueExpression onkeyup)
    {
        _onkeyup = onkeyup;
    }

}
