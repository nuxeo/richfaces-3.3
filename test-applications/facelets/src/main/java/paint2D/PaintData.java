package paint2D;

import java.io.Serializable;

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

