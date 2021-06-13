package Utils;




import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TestUtils {
    public static final long WAIT = 10;

    public HashMap<String,String> parseStringXML(InputStream file) throws Exception {
        HashMap<String, String> stringMap = new HashMap<String, String>();

        //get docs builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //build docs
        Document document =  builder.parse(file);


        //normalize xml struc
        document.getDocumentElement().normalize();

        //root nodes
        Element root = document.getDocumentElement();

        //get all elements
        NodeList nodeList = document.getElementsByTagName("string");


        for(int temp = 0 ; temp < nodeList.getLength() ; temp++)
        {
            Node node = nodeList.item(temp);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                //store ech element key value in map
                stringMap.put(element.getAttribute("name"),element.getTextContent());
            }
        }
        return stringMap;
    }

    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }




}
