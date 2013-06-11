package sb;

public class Data {
	private String city;
	private String contry;
	private boolean flag;
	private String text;

	public Data(String city, String contry, boolean flag) {
		this.city = city;
		this.contry = contry;
		this.flag = flag;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}
	
	public String getText() {
		return city + " - " + contry;
	}
	
	public String toString() {
		return city + " (" + contry + ")";
	}

	public void setText(String text) {
		this.text = text;
	}
}
