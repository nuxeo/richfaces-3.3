/*
 * Copyright 2004-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ajax4jsf.templatecompiler.utils;

/**
 * @author Martin Marinschek
 * @version $Revision: 1.1.2.1 $ $Date: 2006/12/20 18:56:39 $ <p/> $Log:
 *          CallbackListener.java,v $ Revision 1.1 2006/07/05 08:53:39
 *          alexsmirnov Refactor filter system ( move different functions for
 *          different classes ). Fix encoding for AJAX responses - forced to
 *          UTF-8 Fix issue #5 from tracker Fix error message for xml parsing.
 * 
 */
public interface CallbackListener {
	void openedStartTag(int charIndex, int tagIdentifier);

	void closedStartTag(int charIndex, int tagIdentifier);

	void openedEndTag(int charIndex, int tagIdentifier);

	void closedEndTag(int charIndex, int tagIdentifier);

	void attribute(int charIndex, int tagIdentifier, String key, String value);
}
