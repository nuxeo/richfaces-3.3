/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean.tree;

import java.io.IOException;
import java.net.URL;

import org.ajax4jsf.bean.tree.rich.AudioLibrary;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.xml.sax.SAXException;

public class TreeBuilder {

    private static long id = 1;

    public static long nextId() {
        return id++;
    }

    public static void main(String[] args) {
        URL rulesUrl = TreeBuilder.class.getResource("/digester-rules.xml");
        Digester digester =     DigesterLoader.createDigester(rulesUrl);
        AudioLibrary library = new AudioLibrary();
        digester.push(library);

        try {
            digester.parse(TreeBuilder.class.getResourceAsStream("/audio-library.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
