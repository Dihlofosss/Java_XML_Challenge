import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SortingTransformer {

    public String processXml(String path) throws Exception {
        try (InputStream stream = SortingTransformer.class.getResourceAsStream(path)) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(stream);
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("/document/item", doc.getDocumentElement(), XPathConstants.NODESET);
            List<Element> nodes = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element) nodeList.item(i);
                nodes.add(element);
            }
            // Change the code between here ...
            Collections.sort(nodes, new Comparator<Element>() {
                
                @Override
                public int compare(Element e1, Element e2) {
                    double d1 = Double.parseDouble(e1.getElementsByTagName("score").item(0).getTextContent());
                    double d2 = Double.parseDouble(e2.getElementsByTagName("score").item(0).getTextContent());
                    if (d1 == d2)
                        return 0;
                    else if (d1 > d2)
                        return 1;
                    else return -1;
                }
            });
            // ... and here!
            Document sorted = builder.newDocument();
            Element root = sorted.createElement("document");
            sorted.appendChild(root);
            for (Node node : nodes) {
                root.appendChild(sorted.adoptNode(node));
            }
            StringWriter writer = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(new DOMSource(sorted), new StreamResult(writer));
            return writer.toString();
        }
    }

}
