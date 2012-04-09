package final_project;
	import java.io.IOException;
   import java.io.StringReader;
	import javax.xml.XMLConstants;
	import javax.xml.namespace.QName;
	import javax.xml.parsers.*;
	import javax.xml.xpath.*;
	import org.w3c.dom.Document;
   import org.xml.sax.InputSource;
   import org.xml.sax.SAXException;
   
	public class XPathReader {
	    
	    private String xmlString;
	    private Document xmlDocument;
	    private XPath xPath;
	    
	    public XPathReader(String xml) {
	        this.xmlString = xml;
	        initObjects();
	    }
	    
	    private void initObjects(){        
	        try {       	
	        	InputSource inStream = new InputSource();
	        	inStream.setCharacterStream(new StringReader(xmlString));
	            xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inStream);
	            xPath =  XPathFactory.newInstance().
				newXPath();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (SAXException ex) {
	            ex.printStackTrace();
	        } catch (ParserConfigurationException ex) {
	            ex.printStackTrace();
	        }       
	    }
	    
	    
	    public Object read(String expression, 
				QName returnType){
	        try {
	            XPathExpression xPathExpression = 
				xPath.compile(expression);
		        return xPathExpression.evaluate
				(xmlDocument, returnType);
	        } catch (XPathExpressionException ex) {
	            ex.printStackTrace();
	            return null;
	        }
	    }
	}
