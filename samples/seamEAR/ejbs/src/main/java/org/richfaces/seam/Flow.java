package org.richfaces.seam;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface Flow {

	public Date getTime();

	/**
	 * @return the lista
	 */
	public List<String> getLista();

	public String fillList();

}