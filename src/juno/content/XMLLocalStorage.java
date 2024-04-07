package juno.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import juno.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLLocalStorage implements LocalStorage {

    public final File file;

    public XMLLocalStorage(File file) {
        this.file = file;
    }

    public XMLLocalStorage(String file) {
        this(new File(file));
    }

    @Override
    public Dictionary get() throws IOException {
        if (!file.exists()) {
            return new Dictionary();
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(new InputSource(new InputStreamReader(fis)));
            doc.getDocumentElement().normalize();

            final Dictionary dictionary = new Dictionary();
            parseNode(doc.getDocumentElement(), dictionary);
            return dictionary;

        } catch (Exception e) {
            throw new IOException("Error al leer el archivo XML", e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    @Override
    public void set(Dictionary dictionary) throws IOException {
        try {
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final Document doc = docBuilder.newDocument();

            // Creamos un elemento raíz
            final Element rootElement = doc.createElement("Dictionary");
            doc.appendChild(rootElement);

            // Pasamos el elemento raíz a writeDictionaryToXML
            writeDictionaryToXML(dictionary, doc, rootElement);

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            final DOMSource source = new DOMSource(doc);
            final StreamResult result = new StreamResult(new FileOutputStream(file));

            transformer.transform(source, result);
        } catch (Exception e) {
            throw new IOException("Error al escribir en el archivo XML", e);
        }
    }

    private void parseNode(Node node, Dictionary dictionary) {
        final NodeList nodeList = node.getChildNodes();
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            final Node childNode = nodeList.item(i);
            
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                final Element element = (Element) childNode;
                final String nodeName = element.getNodeName();
                final String value = element.getTextContent();
                
                if (!nodeName.isEmpty()) {
                    if ("String".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        dictionary.setString(key, value);
                    } else if ("Integer".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        dictionary.setInt(key, Integer.parseInt(value));
                    } else if ("Float".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        dictionary.setFloat(key, Float.parseFloat(value));
                    } else if ("Long".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        dictionary.setLong(key, Long.parseLong(value));
                    } else if ("Boolean".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        dictionary.setBoolean(key, Boolean.parseBoolean(value));
                    } else if ("Dictionary".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        final Dictionary subDict = new Dictionary();
                        parseNode(childNode, subDict);
                        dictionary.setDictionary(key, subDict);
                    }
                }
            }
        }
    }

    private void writeDictionaryToXML(Dictionary dictionary, Document doc, Element parentElement) {
        for (Map.Entry<String, Object> entry : dictionary.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            if (value == null || key.isEmpty()) {
                continue;
            }

            final Element element;
            if (value instanceof Dictionary) {
                element = doc.createElement("Dictionary");
                element.setAttribute("key", key);
                writeDictionaryToXML((Dictionary) value, doc, element);
            } else {
                element = doc.createElement(value.getClass().getSimpleName());
                element.setAttribute("key", key);
                element.setTextContent(value.toString());
            }
            parentElement.appendChild(element);
        }
    }

//    public static void main(String[] args) throws IOException {
//        final LocalStorage localStorage = new XMLLocalStorage("Dictionary.xml");
//
//        final Dictionary dict = localStorage.get();
//        dict.setString("null", null);
//        dict.setString("string", "Hola mundo");
//        dict.setInt("int", 10);
//        dict.setFloat("float", 10.2f);
//        dict.setLong("long", System.currentTimeMillis());
//        dict.setBoolean("bool", true);
//
//        final Dictionary subdict1 = new Dictionary();
//        subdict1.setString("null", null);
//        subdict1.setString("string", "Hola mundo");
//        subdict1.setInt("int", 10);
//        subdict1.setFloat("float", 10.2f);
//        subdict1.setLong("long", System.currentTimeMillis());
//        subdict1.setBoolean("bool", true);
//        dict.setDictionary("subdict1", subdict1);
//
//        localStorage.set(dict);
//        System.out.println(dict);
//    }
}
