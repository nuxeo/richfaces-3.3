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

package org.richfaces.renderkit.html;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 *
 */
public class ControlsState {
	private boolean firstRendered = true;
	private boolean firstEnabled = true;

	private boolean lastRendered = true;
	private boolean lastEnabled = true;

	private boolean previousRendered = true;
	private boolean previousEnabled = true;

	private boolean nextRendered = true;
	private boolean nextEnabled = true;

	private boolean fastRewindRendered = true;
	private boolean fastRewindEnabled = true;

	private boolean fastForwardRendered = true;
	private boolean fastForwardEnabled = true;
	
	private boolean controlsSeparatorRendered = false;
	
	public boolean isFirstRendered() {
		return firstRendered;
	}
	public void setFirstRendered(boolean firstRendered) {
		this.firstRendered = firstRendered;
	}
	public boolean isFirstEnabled() {
		return firstEnabled;
	}
	public void setFirstEnabled(boolean firstEnabled) {
		this.firstEnabled = firstEnabled;
	}
	public boolean isLastRendered() {
		return lastRendered;
	}
	public void setLastRendered(boolean lastRendered) {
		this.lastRendered = lastRendered;
	}
	public boolean isLastEnabled() {
		return lastEnabled;
	}
	public void setLastEnabled(boolean lastEnabled) {
		this.lastEnabled = lastEnabled;
	}
	public boolean isPreviousRendered() {
		return previousRendered;
	}
	public void setPreviousRendered(boolean previousRendered) {
		this.previousRendered = previousRendered;
	}
	public boolean isPreviousEnabled() {
		return previousEnabled;
	}
	public void setPreviousEnabled(boolean previousEnabled) {
		this.previousEnabled = previousEnabled;
	}
	public boolean isNextRendered() {
		return nextRendered;
	}
	public void setNextRendered(boolean nextRendered) {
		this.nextRendered = nextRendered;
	}
	public boolean isNextEnabled() {
		return nextEnabled;
	}
	public void setNextEnabled(boolean nextEnabled) {
		this.nextEnabled = nextEnabled;
	}
	public boolean isFastRewindRendered() {
		return fastRewindRendered;
	}
	public void setFastRewindRendered(boolean fastRewindRendered) {
		this.fastRewindRendered = fastRewindRendered;
	}
	public boolean isFastRewindEnabled() {
		return fastRewindEnabled;
	}
	public void setFastRewindEnabled(boolean fastRewindEnabled) {
		this.fastRewindEnabled = fastRewindEnabled;
	}
	public boolean isFastForwardRendered() {
		return fastForwardRendered;
	}
	public void setFastForwardRendered(boolean fastForwardRendered) {
		this.fastForwardRendered = fastForwardRendered;
	}
	public boolean isFastForwardEnabled() {
		return fastForwardEnabled;
	}
	public void setFastForwardEnabled(boolean fastForwardEnabled) {
		this.fastForwardEnabled = fastForwardEnabled;
	}
	public boolean isControlsSeparatorRendered() {
		return controlsSeparatorRendered;
	}
	public void setControlsSeparatorRendered(boolean controlsSeparatorRendered) {
		this.controlsSeparatorRendered = controlsSeparatorRendered;
	}
}
