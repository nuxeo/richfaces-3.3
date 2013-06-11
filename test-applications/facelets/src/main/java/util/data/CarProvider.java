package util.data;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class CarProvider extends DefaultHandler {

	private ArrayList<Car> allCars = null;

	public CarProvider() {
		if (allCars == null) {
			allCars = new ArrayList<Car>();
			try {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				sp.parse(new InputSource(getClass().getResourceAsStream(
						"cars.xml")), this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Car> getAllCars() {
		return allCars;
	}

	private String element = "";
	private String mak = "";
	private String mod = "";
	private Integer pr = 0;

	public void startDocument() {
		System.out.println("*** > Parser starts its work");
	}

	public void startElement(String namespaceURI, String localName,
			String rawName, Attributes attrs) {
		element = rawName;

//		System.out.print("<");
//		System.out.print(rawName);
//		if (attrs != null) {
//			int len = attrs.getLength();
//			for (int i = 0; i < len; i++) {
//				System.out.print(" ");
//				System.out.print(attrs.getQName(i));
//				System.out.print("=\"");
//				System.out.print(attrs.getValue(i));
//				System.out.print("\"");
//			}
//		}
//		System.out.print(">");
	}

	public void characters(char ch[], int start, int length) {
		String text = new String(ch, start, length);
//		System.out.print(text);

		if (element.equals("make")) {
			element = "";
			mak = text;
		} else if (element.equals("model")) {
			element = "";
			mod = text;
		} else if (element.equals("price")) {
			try {
				element = "";
				Integer p = Integer.parseInt(text);
				pr = p;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void endElement(String namespaceURI, String localName, String rawName) {
//		System.out.print("</");
//		System.out.print(rawName);
//		System.out.print(">");

		if (rawName.equals("car")) {
			allCars.add(new Car(mak, mod, pr));
		}
	}

	public void endDocument() {
		System.out.println("*** > Parser ends its work");
	}

	public void warning(SAXParseException ex) {
		System.err.println("[Warning] : " + ex.getMessage());
	}

	public void error(SAXParseException ex) {
		System.err.println("[Error] : " + ex.getMessage());
	}

	public void fatalError(SAXParseException ex) throws SAXException {
		System.err.println("[Fatal Error] : " + ex.getMessage());
		throw ex;
	}
}
