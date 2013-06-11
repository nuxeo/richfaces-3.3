package org.richfaces.seam;

import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.Local;

@Local
public interface Media {

	public void paint(OutputStream out, Object data) throws IOException;

	public  void initData();

	public void setData(MediaData data);

	public MediaData getData();

}
