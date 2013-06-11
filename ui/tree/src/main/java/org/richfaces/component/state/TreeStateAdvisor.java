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

package org.richfaces.component.state;

import org.richfaces.component.UITree;


/**
 * @author Nick Belaevski - mailto:nbelaevski@exadel.com
 * created 19.06.2007
 *
 * This interface is intended to "advising" changes to tree state. Tree component should queue user-provided
 * instance of the interface for changes during RENDER_RESPONSE phase. Changes advised by user should be 
 * immediately applied. Advisor methods are provided with {@link UITree} component instance to queue current
 * tree state.
 */
public interface TreeStateAdvisor {
	/**
	 * Advises new node opened/closed state
	 * @param tree {@link UITree} component state to queue state
	 * @return 
	 * 		<ul>
	 * 			<li><code>null</code> if changes are not needed</li>
	 * 			<li>{@link Boolean#TRUE} to advise node to be opened</li>
	 * 			<li>{@link Boolean#FALSE} to advise node to be closed</li>
	 * 		</ul>
	 */			
	public Boolean adviseNodeOpened(UITree tree);

	/**
	 * Advises new node selection
	 * @param tree {@link UITree} component state to queue state
	 * @return 
	 * 		<ul>
	 * 			<li><code>null</code> if changes are not needed</li>
	 * 			<li>{@link Boolean#TRUE} to advise current node to be selected</li>
	 * 			<li>{@link Boolean#FALSE} to advise current node to be unselected</li>
	 * 		</ul>
	 */
	public Boolean adviseNodeSelected(UITree tree);
}
