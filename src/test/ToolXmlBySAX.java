package test;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.HashMap;  
import java.util.List;  
  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.parsers.SAXParser;  
import javax.xml.parsers.SAXParserFactory;  
  
import org.xml.sax.SAXException;  

import util.SaxHandler;
  
public class ToolXmlBySAX {  
      
    public static List<HashMap<String, String>> _readXml(InputStream input, String nodeName){  
        try {  
            SAXParserFactory spf = SAXParserFactory.newInstance();  
            SAXParser parser = spf.newSAXParser();  
            SaxHandler handler = new SaxHandler(nodeName);  
            parser.parse(input, handler);  
            input.close();  
            return handler.getList();  
        } catch (ParserConfigurationException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        } catch (SAXException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    public static void main(String[] args) {  
        try {  
            FileInputStream input = new FileInputStream(new File("d:\\itcast.xml"));  
            List<HashMap<String, String>> list = _readXml(input, "xml");  
            System.out.println("holyant:"+list);
            for(HashMap<String, String> p : list){  
                System.out.println(p.toString());  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
    }  
}  

