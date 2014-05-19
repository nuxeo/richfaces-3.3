/*
 * JBoss, Home of Professional Open Source
 * Copyright ${year}, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.ajax4jsf.util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Manages a shared pool of DocumentBuilders so that there is not a performance
 * hit for every request to create.
 *
 * @author balunasj@redhat.com
 *
 */
public final class DocumentBuilderPool {

        /**
         *  How big the pool is
         */
        private static final int DOC_BUILDER_POOL_SIZE = 100;

        /**
         * Creates and holds the pool using the Initialization On Demand Holder idiom.
         */
        private static class DocumentBuilderPoolHolder {
                  static LinkedList<DocumentBuilder> _documentBuilderPool = new LinkedList<DocumentBuilder>();
        }

        /**
         * Private constructor
         */
        private DocumentBuilderPool (){}

        /**
         * Get a pooled instance of DocumentBuilder
         * @return Pooled DocumentBulder
         * @throws ParserConfigurationException
         */
        public  static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException{
                DocumentBuilder builder = null;

                try {
                        //freeze the pool
                        synchronized (DocumentBuilderPoolHolder._documentBuilderPool) {
                                //pop the top builder - if empty it will through EmptyStackException
                                builder = (DocumentBuilder) DocumentBuilderPoolHolder._documentBuilderPool.removeFirst();
                        }
                } catch (NoSuchElementException e) {
                        //Stack is empty, create a new builder
                        builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                }

                return builder;
        }

        /**
         * Returns a builder to the pool
         * @param builder
         */
        public static void returnDocumentBuilder(DocumentBuilder builder) {
                //validate the builder
                if (null != builder) {
                        synchronized (DocumentBuilderPoolHolder._documentBuilderPool) {
                                //only add if the pool is full
                                if (DocumentBuilderPoolHolder._documentBuilderPool.size() < DOC_BUILDER_POOL_SIZE) {
                                        //reset the builder to initial state
                                        builder.reset();

                                        //push the builder back
                                        DocumentBuilderPoolHolder._documentBuilderPool.addFirst(builder);
                                }
                        }
                }
        }

}
