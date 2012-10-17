import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ProvinceData {
	
	/**我的省名*/
	private String myProvinceName;
	
	/**候选号段*/
	private String[] phoneSections;
	
	private class MapXMLHandler extends DefaultHandler{
		@Override
		public void startDocument() throws SAXException {
			System.out.println("Start parse province.xml");
			super.startDocument();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			System.out.println("element|"+uri+","+localName+","+qName+","+attributes.getValue("section"));
			if(qName.equals(myProvinceName)){
				String stringPhoneSections = attributes.getValue("section");
				phoneSections = stringPhoneSections.split(",");
			}
			super.startElement(uri, localName, qName, attributes);
		}
		
	}
	
	public ProvinceData(String myProvinceName){
		this.myProvinceName = myProvinceName;
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader xReader = parser.getXMLReader();
			MapXMLHandler myHandler = new MapXMLHandler();
			xReader.setContentHandler(myHandler);
			xReader.setErrorHandler(myHandler);
			InputStream is = this.getClass().getResourceAsStream("province.xml");
			xReader.parse(new InputSource(is));
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String randomPhoneNo(){
		assert(phoneSections!=null);
		Random rdm = new Random();
		String prefix = phoneSections[rdm.nextInt(phoneSections.length)];
		int rdmPhoneNo = 100000+rdm.nextInt(899999);
		return prefix+rdmPhoneNo;
	}
}
