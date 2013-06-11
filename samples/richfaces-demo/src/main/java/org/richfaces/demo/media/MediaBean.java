package org.richfaces.demo.media;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class MediaBean {

	public void paint(OutputStream out, Object data) throws IOException{
		if (data instanceof MediaData) {

			MediaData paintData = (MediaData) data;
			BufferedImage img = new BufferedImage(paintData.getWidth(),paintData.getHeight(),BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = img.createGraphics();
			graphics2D.setBackground(paintData.getBackground());
			graphics2D.setColor(paintData.getDrawColor());
			graphics2D.clearRect(0,0,paintData.getWidth(),paintData.getHeight());
			graphics2D.drawLine(5,5,paintData.getWidth()-5,paintData.getHeight()-5);
			graphics2D.drawChars(new String("RichFaces").toCharArray(),0,9,40,15);
			graphics2D.drawChars(new String("mediaOutput").toCharArray(),0,11,5,45);

			ImageIO.write(img,"jpeg",out);

		}
	}

	private void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[2048];
		int read;
		
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
	
	public void paintFlash(OutputStream out, Object data) throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = getClass().getClassLoader();
		}
		
		InputStream stream = loader.getResourceAsStream("org/richfaces/demo/mediaoutput/text.swf");
		if (stream != null) {
			try {
				copy(stream, out);
			} finally {
				stream.close();
			}
		}
	}
}