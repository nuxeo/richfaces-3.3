package util.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String[] statusIcon = { "/pics/error.gif",
			"/pics/fatal.gif", "/pics/info.gif", "/pics/passed.gif",
			"/pics/warn.gif" };
	public static final String[] status = { "error", "fatal", "info", "passed",
			"warn" };

	public static final String[] cityAfrica = { "Africa", "Algeria", "Angola",
			"Bassas da India", "Benin", "Botswana", "Burkina Faso", "Burundi",
			"Cameroon", "Central African Republic", "Chad", "Comoros",
			"Democratic Republic of the Congo", "Djibouti", "Egypt",
			"Equatorial Guinea", "Eritrea", "Ethiopia", "Europa Island",
			"Gabon", "Gambia", "Ghana", "Glorioso Islands", "Guinea",
			"Guinea-Bissau", "Ivory Coast", "Juan de Nova Island", "Kenya",
			"Lesotho", "Liberia", "Libya", "Madagascar", "Malawi", "Mali",
			"Mauritania", "Mauritius Island", "Mayotte", "Morocco",
			"Mozambique", "Namibia", "Nigeria", "Republic of the Congo",
			"Reunion", "Rwanda", "Niger", "Saint Helena",
			"Sao Tome and Principe", "Senegal", "Seychelles", "Sierra Leone",
			"Somalia", "South Africa", "Sudan", "Swaziland", "Tanzania",
			"Togo", "Tromelin Island", "Tunisia", "Uganda", "Western Sahara",
			"Zambia", "Zimbabwe" };
	public static final String[] cityAsia = { "Asia", "Afghanistan", "Armenia",
			"Azerbaijan", "Bangladesh", "Bhutan", "China", "Georgia", "India",
			"Japan", "Kazakhstan", "Korea, North", "Korea, South",
			"Kyrgyzstan", "Maldives", "Mongolia", "Nepal", "Pakistan",
			"Russia", "Sri Lanka", "Tajikistan", "Turkmenistan", "Uzbekistan" };
	public static final String[] cityCAmerica = { "Central America", "Belize",
			"Costa Rica", "El Salvador", "Guatemala", "Honduras", "Nicaragua",
			"Panama" };
	public static final String[] cityEurope = { "Europe", "Albania", "Andorra",
			"Austria", "Belarus", "Belgium", "Bosnia and Herzegovina",
			"Bulgaria", "Canary Islands", "Croatia", "Czech Republic",
			"Denmark", "Estonia", "Faroe Islands", "Finland", "France",
			"Germany", "Gibraltar", "Greece", "Guernsey", "Hungary", "Iceland",
			"Ireland", "Isle of Man", "Italy", "Jersey", "Latvia",
			"Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Malta",
			"Moldova", "Monaco", "Montenegro", "Netherlands", "Norway",
			"Poland", "Portugal", "Romania", "San Marino",
			"Serbia and Montenegro", "Slovakia", "Slovenia", "Spain", "Sweden",
			"Switzerland", "Ukraine", "United Kingdom", "Vatican The Holy See" };
	public static final String[] cityMEast = { "Middle East", "Akrotiri",
			"Bahrain", "Cyprus", "Dhekelia", "Gaza Strip", "Iran", "Iraq",
			"Israel", "Jordan", "Kuwait", "Lebanon", "Oman", "Qatar",
			"Saudi Arabia", "Syria", "Turkey", "United Arab Emirates",
			"West Bank", "Yemen" };
	public static final String[] cityNAmerica = { "North America", "Bermuda",
			"Canada", "Greenland", "Mexico", "Saint Pierre and Miquelon",
			"United States of America" };
	public static final String[] cityOceania = { "Oceania", "American Samoa",
			"Australia", "Baker Island", "Cook Islands", "Coral Sea Islands",
			"Fiji", "French Polynesia", "Guam", "Howland Island",
			"Jarvis Island", "Johnston Atoll", "Kingman Reef", "Kiribati",
			"Marshall Islands", "Micronesia", "Midway Islands", "Nauru",
			"New Caledonia", "New Zealand", "Niue", "Norfolk Island",
			"Northern Mariana Islands", "Palau", "Palmyra Atoll",
			"Papua New Guinea", "Pitcairn Islands", "Samoa", "Solomon Islands",
			"Tokelau", "Tonga", "Tuvalu", "Vanuatu", "Wake Island",
			"Wallis and Futuna" };
	public static final String[] citySAmerica = { "South America", "Argentina",
			"Bolivia", "Brazil", "Chile", "Colombia", "Easter Island",
			"Ecuador", "Falkland Islands", "French Guiana", "Guyana",
			"Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela" };
	public static final String[] citySEAsia = { "SouthEast Asia",
			"Ashmore and Cartier Islands", "Brunei", "Burma", "Cambodia",
			"Christmas Island", "Cocos (Keeling) Islands", "East Timor",
			"Hong Kong", "Indonesia", "Laos", "Malaysia", "Paracel Islands",
			"Philippines", "Singapore", "Spratly Islands", "Taiwan",
			"Thailand", "Vietnam" };
	public static final String[] cityCaribbean = { "The Caribbean", "Anguilla",
			"Antigua and Barbuda", "Aruba", "Barbados",
			"British Virgin Islands", "Cayman Islands", "Cuba", "Dominica",
			"Dominican Republic", "Grenada", "Guadeloupe", "Haiti", "Jamaica",
			"Martinique", "Montserrat", "Navassa Island",
			"Netherlands Antilles", "Puerto Rico", "Saint Barthelemy",
			"Saint Kitts and Nevis", "Saint Lucia",
			"Saint Vincent and the Grenadines", "The Bahamas",
			"Trinidad and Tobago", "Turks and Caicos Islands", "Virgin Islands" };

	private String str0;
	private String str1;
	private String str2;
	private String str3;
	private String str4;
	private int int0;
	private int int1;
	private int int2;
	private int int3;
	private boolean bool0;
	private boolean bool1;
	private boolean bool2;
	private boolean bool3;
	private Date date0;

	public Data() {
		this.str0 = "";
		this.str1 = "";
		this.str2 = "";
		this.str3 = "";
		this.str4 = "";
		this.int0 = 0;
		this.int1 = 0;
		this.int2 = 0;
		this.int3 = 0;
		this.bool0 = false;
		this.bool1 = false;
		this.bool2 = false;
		this.bool3 = false;
	}

	public Data(int int0, int int1, String str0, int int2, String str1,
			int int3, String str2, String str3, boolean bool0) {
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.int0 = int0;
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
		this.bool0 = bool0;
	}

	public Data(String str0, String str1, String str2, String str3, int int0,
			int int1, int int2, int int3, boolean bool0, boolean bool1,
			boolean bool2, boolean bool3) {
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.int0 = int0;
		this.int1 = int1;
		this.int2 = int2;
		this.int3 = int3;
		this.bool0 = bool0;
		this.bool1 = bool1;
		this.bool2 = bool2;
		this.bool3 = bool3;
	}

	public Data(String str0, String str1, String str2, String str3) {
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
	}

	public Data(int int0, String str0, String str1, String str2, String str3) {
		this.int0 = int0;
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
	}

	public Data(int int0, String str0, String str1, String str2, boolean bool0) {
		this.int0 = int0;
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.bool0 = bool0;
	}
	
	public Data(int int0, String str0, String str1, String str2, String str3, String str4){
		this.int0 = int0;
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.str4 = str4;
	}

	// for scrollableDataTable component
	public Data(int int0, String str0, String str1, String str2, String str3,
			Date date0) {
		this.int0 = int0;
		this.str0 = str0;
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
		this.date0 = date0;
	}

	public Data(String str0) {
		this.str0 = str0;
	}

	public Data(int int0) {
		this.int0 = int0;
	}

	public Data(String str0, String str1) {
		this.str0 = str0;
		this.str1 = str1;
	}

	public Data(String str0, int int0) {
		this.str0 = str0;
		this.int0 = int0;
	}

	public Data(String str0, int int0, boolean bool0) {
		this.str0 = str0;
		this.int0 = int0;
		this.bool0 = bool0;
	}

	public String getStr0() {
		return str0;
	}

	public void setStr0(String str0) {
		this.str0 = str0;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public int getInt0() {
		return int0;
	}

	public void setInt0(int int0) {
		this.int0 = int0;
	}

	public int getInt1() {
		return int1;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public int getInt2() {
		return int2;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public int getInt3() {
		return int3;
	}

	public void setInt3(int int3) {
		this.int3 = int3;
	}

	public boolean isBool0() {
		return bool0;
	}

	public void setBool0(boolean bool0) {
		this.bool0 = bool0;
	}

	public boolean isBool1() {
		return bool1;
	}

	public void setBool1(boolean bool1) {
		this.bool1 = bool1;
	}

	public boolean isBool2() {
		return bool2;
	}

	public void setBool2(boolean bool2) {
		this.bool2 = bool2;
	}

	public boolean isBool3() {
		return bool3;
	}

	public void setBool3(boolean bool3) {
		this.bool3 = bool3;
	}

	public static String Random(int size) {
		char[] c = new char[size];
		for (int i = 0; i < size; i++) {
			c[i] = (char) (new Random().nextInt(25) + 97);
		}
		return new String(c);
	}

	public static String RandomUp(int size) {
		char[] c = new char[size];
		for (int i = 0; i < size; i++) {
			c[i] = (char) (new Random().nextInt(25) + 65);
		}
		return new String(c);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + int0;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Data other = (Data) obj;
		if (int0 != other.int0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return str0 + ":" + int0;
	}

	public Date getDate0() {
		return date0;
	}

	public void setDate0(Date date0) {
		this.date0 = date0;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}
}
