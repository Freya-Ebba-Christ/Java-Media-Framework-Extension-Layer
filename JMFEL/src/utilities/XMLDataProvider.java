/**
 *
 */
package utilities;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLDataProvider implements IXMLDataProvider {
    private Document document;
    
    public XMLDataProvider(String fileName) throws Exception {
        this.parse(fileName);
    }

    public int countNodesByName(Node parent, String nodeName) throws Exception {
        try {
            int nodeCount = 0;
            
            NodeList childs = parent.getChildNodes();
            for (int i = 0; i < childs.getLength(); i++) {
                if (childs.item(i).getNodeName().equals(nodeName)) {
                    nodeCount++;
                }
            }
            
            return nodeCount;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    

    public String getAttributeValue(Node node, String attributeName) throws Exception {
        try {
            return node.getAttributes().getNamedItem(attributeName).getNodeValue();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    public int getNumAttributes(Node node) throws Exception {
        try {
            return node.getAttributes().getLength();
        }		catch (Exception e) {
            throw new Exception(e);
        }
    }
    

    public Node[] getNodesByName(Node parent, String nodeName) throws Exception {
        try {
            int numNodes = this.countNodesByName(parent, nodeName);
            
            if (numNodes == 0) {
                return null;
            }
            
            Node [] nodes = new Node [numNodes];
            
            NodeList childs = parent.getChildNodes();
            int pos = 0;
            for (int i = 0; i < childs.getLength(); i++) {
                if (childs.item(i).getNodeName().equals(nodeName)) {
                    nodes [pos] = childs.item(i);
                    pos++;
                }
            }
            
            return nodes;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    

    public String getNodeValue(Node node) throws Exception {
        try {
            return node.getFirstChild().getNodeValue();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    

    public Node getRootNode() throws Exception {
        try {
            return this.document.getDocumentElement();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    
    

    public void parse(String fileName) throws Exception {
        try {
            DOMParser parser = new DOMParser();
            parser.parse(fileName);
            this.document = parser.getDocument();
        } catch (Exception e) {
            throw new Exception(e);
        }
        
    }
    
}
