package clases;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.OutputKeys;

public class SqlXml {
	

	public static Document toDocument(ResultSet rs) throws ParserConfigurationException, SQLException
	{
	   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   DocumentBuilder builder;
	
		builder = factory.newDocumentBuilder();
	
	   Document doc                   = builder.newDocument();

	   Element results = doc.createElement("Results");
	   doc.appendChild(results);

	   ResultSetMetaData rsmd = rs.getMetaData();
	   int colCount           = rsmd.getColumnCount();

	   while (rs.next())
	   {
	      Element row = doc.createElement("Row");
	      results.appendChild(row);

	      for (int i = 1; i <= colCount; i++)
	      {
	         String columnName = rsmd.getColumnName(i);
	         Object value      = rs.getObject(i);

	         Element node      = doc.createElement(columnName);
	         node.appendChild(doc.createTextNode(value.toString()));
	         row.appendChild(node);
	      }
	   }
	   
	   rs.first();
	   return doc;

	}

	
	
	public static void toFile(Document doc) {

	       try {

	            //for output to file, console
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            //for pretty print
	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	            DOMSource source = new DOMSource(doc);
	 
	            //write to console or file
	            StreamResult console = new StreamResult(System.out);
	            StreamResult file = new StreamResult(new File("emps.xml"));
	 
	            //write data
	            transformer.transform(source, console);
	            transformer.transform(source, file);
	            System.out.println("DONE");
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }	
	}
	
}
