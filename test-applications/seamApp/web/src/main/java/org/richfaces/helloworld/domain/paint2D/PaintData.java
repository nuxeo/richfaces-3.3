package org.richfaces.helloworld.domain.paint2D;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("paintData")
@Scope(ScopeType.SESSION)
public class PaintData implements Serializable{
	private String text;

	public PaintData() 
	{
		text = "Paint2D";
	}

	public String getText() 
	{
		return text;
	}
	public void setText(String text) 
	{
		this.text = text;
	}
}

