package sb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.richfaces.component.html.HtmlSuggestionBox;
import org.richfaces.renderkit.html.SuggestionBoxRenderer;

import util.componentInfo.ComponentInfo;

public class Sb implements Serializable {

	private ArrayList cities;
	private ArrayList data;
	private String first;
	private int zindex;
	private double frequency;
	private String property;
	private String rows;
	private String cellspacing;
	private String cellpadding;
	private String minchars;
	private String rules;
	private String border;
	private String width;
	private String height;
	private String shadowOpacity;
	private String bgColor;
	private String shadowDepth;
	private Object tokens;
	private boolean focus;
	private boolean ajaxSingle;
	private boolean rendered;
	private boolean selfRendered;
	private String value;
	private String dir;
	private int requestDelay;
	private HtmlSuggestionBox mySuggestionBox = null;
	private String bindLabel;
	private boolean bypassUpdates;
	private String frame;
	private boolean ignoreDupResponses;
	private boolean immediate;
	private String nothingLabel;
	private boolean usingSuggestObjects;

	/*
	 * private static final String[] cit = { "Abba", "Abbeville", "Acworth",
	 * "Adairsville", "Adel", "Adrian", "Ailey", "Alamo", "Alapaha", "Albany",
	 * "Allenhurst", "Alma", "Alma", "Alpharetta", "Alston", "Amboy", "Ambrose",
	 * "Americus", "Appling", "Arlington", "Ashburn", "Athens", "Athens-Clarke
	 * County", "Atkinson", "Atlanta", "Attapulgus", "Auburn", "Augusta",
	 * "Augusta-Richmond County", "Austell", "Avondale Estates", "Axson" };
	 */
		private static final String[] cityAfrica = {"Africa", "Algeria", "Angola", "Bassas da India", "Benin", "Botswana", "Burkina Faso", "Burundi", "Cameroon", "Central African Republic", "Chad", "Comoros", "Democratic Republic of the Congo", "Djibouti", "Egypt", "Equatorial Guinea", "Eritrea", "Ethiopia", "Europa Island", "Gabon", "Gambia", "Ghana", "Glorioso Islands", "Guinea", "Guinea-Bissau", "Ivory Coast", "Juan de Nova Island", "Kenya", "Lesotho", "Liberia", "Libya", "Madagascar", "Malawi", "Mali", "Mauritania", "Mauritius Island", "Mayotte", "Morocco", "Mozambique", "Namibia", "Niger", "Nigeria", "Republic of the Congo", "Reunion", "Rwanda", "Saint Helena", "Sao Tome and Principe", "Senegal", "Seychelles", "Sierra Leone", "Somalia", "South Africa", "Sudan", "Swaziland", "Tanzania", "Togo", "Tromelin Island", "Tunisia", "Uganda", "Western Sahara", "Zambia", "Zimbabwe"};
	    private static final String[] cityAsia = {"Asia", "Afghanistan", "Armenia", "Azerbaijan", "Bangladesh", "Bhutan", "China", "Georgia", "India", "Japan", "Kazakhstan", "Korea, North", "Korea, South", "Kyrgyzstan", "Maldives", "Mongolia", "Nepal", "Pakistan", "Russia", "Sri Lanka", "Tajikistan", "Turkmenistan", "Uzbekistan"};
	    private static final String[] cityCAmerica = {"Central America", "Belize", "Costa Rica", "El Salvador", "Guatemala", "Honduras", "Nicaragua", "Panama"};
	    private static final String[] cityEurope = {"Europe", "Albania", "Andorra", "Austria", "Belarus", "Belgium", "Bosnia and Herzegovina", "Bulgaria", "Canary Islands", "Croatia", "Czech Republic", "Denmark", "Estonia", "Faroe Islands", "Finland", "France", "Germany", "Gibraltar", "Greece", "Guernsey", "Hungary", "Iceland", "Ireland", "Isle of Man", "Italy", "Jersey", "Latvia", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Malta", "Moldova", "Monaco", "Montenegro", "Netherlands", "Norway", "Poland", "Portugal", "Romania", "San Marino", "Serbia and Montenegro", "Slovakia", "Slovenia", "Spain", "Sweden", "Switzerland", "Ukraine", "United Kingdom", "Vatican The Holy See"};
	    private static final String[] cityMEast = {"Middle East", "Akrotiri", "Bahrain", "Cyprus", "Dhekelia", "Gaza Strip", "Iran", "Iraq", "Israel", "Jordan", "Kuwait", "Lebanon", "Oman", "Qatar", "Saudi Arabia", "Syria", "Turkey", "United Arab Emirates", "West Bank", "Yemen"};
	    private static final String[] cityNAmerica = {"North America", "Bermuda", "Canada", "Greenland", "Mexico", "Saint Pierre and Miquelon", "United States of America"};
	    private static final String[] cityOceania = {"Oceania", "American Samoa", "Australia", "Baker Island", "Cook Islands", "Coral Sea Islands", "Fiji", "French Polynesia", "Guam", "Howland Island", "Jarvis Island", "Johnston Atoll", "Kingman Reef", "Kiribati", "Marshall Islands", "Micronesia", "Midway Islands", "Nauru", "New Caledonia", "New Zealand", "Niue", "Norfolk Island", "Northern Mariana Islands", "Palau", "Palmyra Atoll", "Papua New Guinea", "Pitcairn Islands", "Samoa", "Solomon Islands", "Tokelau", "Tonga", "Tuvalu", "Vanuatu", "Wake Island", "Wallis and Futuna"};
	    private static final String[] citySAmerica = {"South America","Argentina", "Bolivia", "Brazil", "Chile", "Colombia", "Easter Island", "Ecuador", "Falkland Islands", "French Guiana", "Guyana", "Paraguay", "Peru", "Suriname", "Uruguay", "Venezuela"};
	    private static final String[] citySEAsia = {"SouthEast Asia", "Ashmore and Cartier Islands", "Brunei", "Burma", "Cambodia", "Christmas Island", "Cocos (Keeling) Islands", "East Timor", "Hong Kong", "Indonesia", "Laos", "Malaysia", "Paracel Islands", "Philippines", "Singapore", "Spratly Islands", "Taiwan", "Thailand", "Vietnam"};
	    private static final String[] cityCaribbean = {"The Caribbean", "Anguilla", "Antigua and Barbuda", "Aruba", "Barbados", "British Virgin Islands", "Cayman Islands", "Cuba", "Dominica", "Dominican Republic", "Grenada", "Guadeloupe", "Haiti", "Jamaica", "Martinique", "Montserrat", "Navassa Island", "Netherlands Antilles", "Puerto Rico", "Saint Barthelemy", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "The Bahamas", "Trinidad and Tobago", "Turks and Caicos Islands", "Virgin Islands"};

	    
		public void addHtmlSuggestionBox(){
			ComponentInfo info = ComponentInfo.getInstance();
			info.addField(mySuggestionBox);
		}
	  
	public Sb() {
		ignoreDupResponses = true;
		immediate = true;
		dir = "LTR";
		ajaxSingle = false;
		rendered = true;
		selfRendered = false;
		value = "a, ";
		requestDelay = 500;
		tokens = "[ ,";
		border = "1";
		width = "200";
		height = "150";
		shadowOpacity = "1";
		shadowDepth = Integer.toString(SuggestionBoxRenderer.SHADOW_DEPTH);
		zindex = 3;
		rows = "0";
		first = "0";
		cellspacing = "2";
		cellpadding = "2";
		minchars = "1";
		frequency = 0;
		rules = "none";
		bindLabel = "Not checked";
		bypassUpdates = true;
		frame = "void";
		nothingLabel = "Nothinf label works!";
		usingSuggestObjects = true;
		setCities(getAllData());
	}
	
	public void selectValue(ActionEvent actionEvent){
		System.out.println("----inside selectValue(ActionEvent actionEvent)-----");
	}
	
	public void checkBinding(ActionEvent actionEvent) {
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = mySuggestionBox.getClientId(context) + ";   param: " + mySuggestionBox.getParam();
	}
	
	public List autocomplete(Object event) {
		String pref = event.toString();
		ArrayList result = new ArrayList();

		Iterator iterator = getAllData().iterator();
		while (iterator.hasNext()) {
			Data elem = (Data) iterator.next();
			if ((elem != null && elem.getCity().toLowerCase().indexOf(
					pref.toLowerCase()) == 0)
					|| "".equals(pref)) {
				result.add(elem);
			}
		}
		return result;
	}

	public ArrayList getCities() {
		return cities;
	}

	public void setCities(ArrayList cities) {
		this.cities = cities;
	}

	private ArrayList<Data> addData(String[] mStr, ArrayList<Data> arr) {
		for (int i = 1; i < mStr.length; i++) {
			Data data = new Data(mStr[i], mStr[0], false);
			arr.add(data);
		}
		return arr;
	}

	public ArrayList getAllData() {
		/*
		 * for (int i = 0; i < cit.length; i++) { Data data = new Data(cit[i],
		 * String.valueOf(i + 1)); result.add(data); }
		 */
		ArrayList<Data> result = new ArrayList<Data>();
		result = addData(cityAfrica, result);
		result = addData(cityAsia, result);
		result = addData(cityCAmerica, result);
		result = addData(cityCaribbean, result);
		result = addData(cityEurope, result);
		result = addData(cityMEast, result);
		result = addData(cityNAmerica, result);
		result = addData(cityOceania, result);
		result = addData(citySAmerica, result);
		result = addData(citySEAsia, result);
		return result;
	}

	public ArrayList getData() {
		return data;
	}

	public void setData(ArrayList data) {
		this.data = data;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getCellpadding() {
		return cellpadding;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	public String getCellspacing() {
		return cellspacing;
	}

	public void setCellspacing(String cellspacing) {
		this.cellspacing = cellspacing;
	}

	public String getFirst() {
		return first;
	}

	public String getIntFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public String getMinchars() {
		return minchars;
	}

	public void setMinchars(String minchars) {
		this.minchars = minchars;
	}

	public String getRows() {
		return rows;
	}

	public int getIntRows() {
		return Integer.parseInt(getRows());
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public void OnSelect() {
		System.out.print("Onselect works!!!");
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getShadowOpacity() {
		return shadowOpacity;
	}

	public void setShadowOpacity(String shadowOpacity) {
		this.shadowOpacity = shadowOpacity;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public boolean isFocus() {
		return focus;
	}

	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}

	public String getShadowDepth() {
		return shadowDepth;
	}

	public void setShadowDepth(String shadowDepth) {
		this.shadowDepth = shadowDepth;
	}

	public void bTest1() {
		setBorder("2");
		setCellpadding("0");
		setCellspacing("0");
		setFrequency(0);
		setHeight("150");
		setWidth("200");
		setMinchars("1");
		setShadowDepth("11");
		setShadowOpacity("3");
		setZindex(3);
	}

	public void bTest2() {
		setBorder("0");
		setCellpadding("3");
		setCellspacing("3");
		setFrequency(2);
		setHeight("200");
		setWidth("200");
		setMinchars("1");
		setShadowDepth("1");
		setShadowOpacity("7");
		setZindex(3);
	}

	public void bTest3() {
		setBorder("1");
		setCellpadding("5");
		setCellspacing("0");
		setFrequency(1);
		setHeight("150");
		setWidth("200");
		setMinchars("2");
		setShadowDepth("1");
		setShadowOpacity("7");
		setZindex(3);
	}

	public void bTest4() {
		setBorder("1");
		setCellpadding("0");
		setCellspacing("5");
		setFrequency(3);
		setHeight("150");
		setWidth("200");
		setMinchars("2");
		setShadowDepth("7");
		setShadowOpacity("1");
		setZindex(3);
	}

	public void bTest5() {
		setBorder("4");
		setCellpadding("1");
		setCellspacing("1");
		setFrequency(5);
		setHeight("200");
		setWidth("400");
		setMinchars("2");
		setShadowDepth("5");
		setShadowOpacity("5");
		setZindex(1);
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	public Object getTokens() {
		return tokens;
	}

	public void setTokens(Object tokens) {
		this.tokens = tokens;
	}

	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isSelfRendered() {
		return selfRendered;
	}

	public void setSelfRendered(boolean selfRendered) {
		this.selfRendered = selfRendered;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getRequestDelay() {
		return requestDelay;
	}

	public void setRequestDelay(int requestDelay) {
		this.requestDelay = requestDelay;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public HtmlSuggestionBox getMySuggestionBox() {
		return mySuggestionBox;
	}

	public void setMySuggestionBox(HtmlSuggestionBox mySuggestionBox) {
		this.mySuggestionBox = mySuggestionBox;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public boolean isBypassUpdates() {
		return bypassUpdates;
	}

	public void setBypassUpdates(boolean bypassUpdates) {
		this.bypassUpdates = bypassUpdates;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public boolean isIgnoreDupResponses() {
		return ignoreDupResponses;
	}

	public void setIgnoreDupResponses(boolean ignoreDupResponses) {
		this.ignoreDupResponses = ignoreDupResponses;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getNothingLabel() {
		return nothingLabel;
	}

	public void setNothingLabel(String nothingLabel) {
		this.nothingLabel = nothingLabel;
	}

	public boolean isUsingSuggestObjects() {
		return usingSuggestObjects;
	}

	public void setUsingSuggestObjects(boolean usingSuggestObjects) {
		this.usingSuggestObjects = usingSuggestObjects;
	}
}
