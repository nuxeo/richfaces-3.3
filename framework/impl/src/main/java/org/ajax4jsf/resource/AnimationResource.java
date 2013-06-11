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
package org.ajax4jsf.resource;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.ajax4jsf.resource.image.animatedgif.AnimatedGifEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AnimationResource extends InternetResourceBase {

	private static final Log log = LogFactory.getLog(AnimationResource.class);

	protected abstract Dimension getFrameSize(ResourceContext resourceContext);

	public AnimationResource() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}

	protected abstract int getNumberOfFrames();

	protected int getRepeat() {
		return -1;
	}

	private int[] delays;

	protected int[] getFrameDelays() {
		if (delays == null) {
			delays = new int[getNumberOfFrames()];
			Arrays.fill(delays, 0);
		}

		return delays;
	}

	private int currFrameIndex = 0;

	public void send(ResourceContext context) throws IOException {
		try {
			DataOutputStream output = new DataOutputStream(context.getOutputStream());
			Dimension frameSize = getFrameSize(context);
			int numberOfFrames = getNumberOfFrames();
			BufferedImage frame = null;
			currFrameIndex = 0;
			if (frameSize.getHeight() > 0.0 && frameSize.getWidth() > 0.0
					&& numberOfFrames > 0) {
				AnimatedGifEncoder encoder = new AnimatedGifEncoder();
				encoder.start(output);
				encoder.setRepeat(getRepeat());
				int[] delays = getFrameDelays();
				ImageRenderer renderer = (ImageRenderer) getRenderer(null);
				while (currFrameIndex < numberOfFrames) {
					frame = renderer.createImage(frameSize.width,
							frameSize.height);
					Graphics2D graphics = frame.createGraphics();
					paint(context, graphics, currFrameIndex++);
					graphics.dispose();
					encoder.addFrame(frame);
					if (delays != null && delays.length > currFrameIndex) {
						encoder.setDelay(delays[currFrameIndex]);
					}
				}
				encoder.finish();
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void paint(ResourceContext context, Graphics2D graphics2D, int frameIndex);

}
