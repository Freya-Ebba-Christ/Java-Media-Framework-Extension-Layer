package utilities;

import org.w3c.dom.Node;

/**
 * This interface provides access to test data that is organized
 * in typical XML structure.
 */

public interface IXMLDataProvider {
    
    /**
     * Returns the root node of the XML file.
     * @return	the root node of the XML file
     * @throws TestDataException	thrown, when parsing errors occur
     */
    public Node getRootNode() throws Exception;
    
    /**
     * Returns the number of XML data nodes matching the given node name
     * @param parent the parent node where to look for child nodes with the specified name
     * @param nodeName	the node name
     * @return	the number of nodes in the XML file
     * @throws TestDataException	usually thrown when parsing errors occur
     */
    public int countNodesByName(Node parent, String nodeName) throws Exception;
    
    /**
     * Returns an array containing all nodes matching thi given node name
     * @param parent	the parent node where to look for nodes with the specified node name
     * @param nodeName	the name of the node
     * @return	an array containing all node names, if there are no nodes matching the geven name, NULL is returned
     * @throws TestDataException	usually thrown when parsing errors occur
     */
    public Node [] getNodesByName(Node parent, String nodeName) throws Exception;
    
    /**
     * Returns the node value of a given node.
     * @param node	the requested node
     * @return	a string representing the node value
     * @throws TestDataException	usually thrown when parsing errors occur
     */
    public String getNodeValue(Node node) throws Exception;
    
    
    /**
     * Returns the attribute value of a given attribute name within the provided node.
     * @param node	the node where to look for the attribute
     * @param attributeName	the name of the attribute to look for
     * @return	a string representing the attribute value
     * @throws TestDataException	usually thrown when parsing errors occur
     */
    public String getAttributeValue(Node node, String attributeName) throws Exception;
    
    public int getNumAttributes(Node node) throws Exception ;
    
    /**
     * Parses an XML file specified by the given file name.
     * @param fileName	the file name of the XML file
     * @throws TestDataException	usually thrown when parsing errors occur
     */
    public void parse(String fileName) throws Exception;
}
