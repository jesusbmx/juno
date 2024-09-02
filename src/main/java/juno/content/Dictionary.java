package juno.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Dictionary implements Cloneable {

    protected final Map<String, Object> values;

    public Dictionary(Map<String, Object> values) {
        this.values = values;
    }

    public Dictionary() {
        this(new LinkedHashMap<String, Object>());
    }
    
    public Dictionary(Dictionary dictionary) {
        this(new LinkedHashMap<String, Object>(dictionary.values));
    }
    
    
    @Override
    public Dictionary clone() {
        return new Dictionary(this);
    }

    public int size() {
        return values.size();
    }
    
    public boolean contains(String key) {
        return values.containsKey(key);
    }

    public void remove(String key) {
        values.remove(key);
    }

    public void clear() {
        values.clear();
    }
        
    public Set<Map.Entry<String, Object>> entrySet() {
        return values.entrySet();
    }
    

    public boolean isNull(String key) {
        final Object value = values.get(key);
        return value == null;
    }
    
    public boolean isBoolean(String key) {
        Object value = values.get(key);
        return value instanceof Boolean;
    }

    public boolean isFloat(String key) {
        Object value = values.get(key);
        return value instanceof Float;
    }
    
    public boolean isInt(String key) {
        Object value = values.get(key);
        return value instanceof Integer;
    }
        
     public boolean isLong(String key) {
        Object value = values.get(key);
        return value instanceof Long;
    }

    public boolean isString(String key) {
        Object value = values.get(key);
        return value instanceof String;
    }

    public boolean isDictionary(String key) {
        final Object value = values.get(key);
        return value != null && value instanceof Dictionary;
    }

        
    public boolean getBoolean(String key, boolean defValue) {
        final Boolean value = (Boolean) values.get(key);
        return value == null ? defValue : value;
    }
    
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    
    public float getFloat(String key, float defValue) {
        final Float value = (Float) values.get(key);
        return value == null ? defValue : value;
    }

    public float getFloat(String key) {
        return getFloat(key, -1);
    }
    
    public int getInt(String key, int defValue) {
        final Integer value = (Integer) values.get(key);
        return value == null ? defValue : value;
    }
    
    public int getInt(String key) {
        return getInt(key, -1);
    }
    
    public long getLong(String key, long defValue) {
        final Long value = (Long) values.get(key);
        return value == null ? defValue : value;
    }
    
    public long getLong(String key) {
        return getLong(key, -1);
    }
    
    public String getString(String key, String defValue) {
        final String value = (String) values.get(key);
        return value == null ? defValue : value;
    }
    
    public String getString(String key) {
        return getString(key, null);
    }
    
    public List<String> getStringArray(String key, List<String> defValue) {
        final List<String> value = (List<String>) values.get(key);
        return value == null ? defValue : value;
    }
    
    public Dictionary getDictionary(String key, Dictionary defValue) {
        final Dictionary value = (Dictionary) values.get(key);
        return value == null ? defValue : value;
    }
    
    public Dictionary getDictionary(String key) {
        return getDictionary(key, null);
    }
    
    public Map<String, Object> getAll() {
        return new LinkedHashMap<String, Object>(values);
    }
        
    
    public void setBoolean(String key, boolean value) {
        values.put(key, value);
    }
    
    public void setFloat(String key, float value) {
        values.put(key, value);
    }
    
    public void setInt(String key, int value) {
        values.put(key, value);
    }

    public void setLong(String key, long value) {
        values.put(key, value);
    }
    
    public void setString(String key, String value) {
        values.put(key, value);
    }
    
    public void setStringArray(String key, List<String> value) {
        values.put(key, value);
    }

    public void setDictionary(String key, Dictionary value) {
        if (value == this) {
            throw new IllegalArgumentException("Cannot set the '" + key + "' dictionary to itself");
        }
        values.put(key, value);
    }
   
    public void setAll(Dictionary dict) {
        values.putAll(dict.values);
    }
        
    
    private static void writeIndentSpaces(StringBuilder out, int indentSpaces) {
        if (indentSpaces > 0) {
            final char[] tabs = new char[indentSpaces];
            Arrays.fill(tabs, '\t');
            out.append(new String(tabs));
        }
    }
        
    private static void write(StringBuilder out, Dictionary dict, int indentSpaces) {
        for (Map.Entry<String, Object> entry : dict.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();

            writeIndentSpaces(out, indentSpaces);
            out.append(key).append(": ");
            
            if (value instanceof Dictionary) {
                out.append("\n");
                write(out, (Dictionary) value, indentSpaces + 1);
                
            } else if (value instanceof List) {
                out.append("\n");
                List list = (List) value;
                for (Object object : list) {
                    writeIndentSpaces(out, indentSpaces + 1);
                    out.append("- ").append(object).append("\n");
                }
                
            } else  {
                out.append(value).append("\n");
            }
        }
    }

    public String toString(int indentSpaces) {
        final StringBuilder sb = new StringBuilder();
        write(sb, this, indentSpaces);
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return toString(0);
    }
    
    
    public synchronized void load(InputStream in) throws IOException {
        try {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            final Document doc = dBuilder.parse(new InputSource(new InputStreamReader(in)));
            doc.getDocumentElement().normalize();

            parseNode(doc.getDocumentElement(), this);

        } catch (Exception e) {
            throw new IOException("Error al leer el archivo XML", e);
        } 
    }
    
    private static void parseNode(Node node, Dictionary dictionary) {
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
                        
                    } else if ("String-Array".equals(nodeName)) {
                        final String key = element.getAttribute("key");
                        final NodeList itemNodes = element.getElementsByTagName("item");
                        List<String> stringArray = new ArrayList<String>(itemNodes.getLength());
                        for (int j = 0; j < itemNodes.getLength(); j++) {
                            Node itemNode = itemNodes.item(j);
                            stringArray.add(itemNode.getTextContent());
                        }
                        dictionary.setStringArray(key, stringArray);
                    }
                }
            }
        }
    }
    
    public void store(OutputStream out) throws IOException {
        try {
            final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            final Document doc = docBuilder.newDocument();

            // Creamos un elemento raíz
            final Element rootElement = doc.createElement("Dictionary");
            doc.appendChild(rootElement);

            // Pasamos el elemento raíz a writeDictionaryToXML
            writeDictionaryToXML(this, doc, rootElement);

            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            
            // Agregamos formato al XML
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            final DOMSource source = new DOMSource(doc);
            final StreamResult result = new StreamResult(out);
            
            transformer.transform(source, result);
            
        } catch (Exception e) {
            throw new IOException("Error al escribir en el archivo XML", e);
        }
    }

    private static void writeDictionaryToXML(Dictionary dictionary, Document doc, Element parentElement) {
        for (Map.Entry<String, Object> entry : dictionary.entrySet()) {
            final String key = entry.getKey();
            final Object value = entry.getValue();
            if (value == null || key.isEmpty()) {
                continue;
            }

            if (value instanceof Dictionary) {
                final Element element = doc.createElement("Dictionary");
                element.setAttribute("key", key);
                writeDictionaryToXML((Dictionary) value, doc, element);
                parentElement.appendChild(element);
                
            } else if (value instanceof List && !((List<?>) value).isEmpty()) {
                if (((List<?>) value).get(0) instanceof String) {
                    // If the value is a non-empty list of strings
                    final Element element = doc.createElement("String-Array");
                    element.setAttribute("key", key);
                    for (String item : (List<String>) value) {
                        Element itemElement = doc.createElement("item");
                        itemElement.setTextContent(item);
                        element.appendChild(itemElement);
                    }
                    parentElement.appendChild(element);
                }
                
            } else {
                final Element element = doc.createElement(value.getClass().getSimpleName());
                element.setAttribute("key", key);
                element.setTextContent(value.toString());
                parentElement.appendChild(element);
            }
            
        }
    }

 
//    public static void main(String[] args) throws IOException {
//        final java.io.File file = new java.io.File("Dictionary.xml");
//        
//        final Dictionary dictionary = new Dictionary();
//        if (file.exists()) {
//            dictionary.load(new java.io.FileInputStream(file));
//        }
//        
//        dictionary.setString("null", null);
//        dictionary.setString("text", "Hello, world");
//        dictionary.setInt("integer_number", 10);
//        dictionary.setFloat("float_number", 10.2f);
//        dictionary.setLong("long_number", System.currentTimeMillis());
//        dictionary.setBoolean("boolean", true);
//        
//        List<String> list = new ArrayList<String>();
//        list.add("apple");
//        list.add("banana");
//        list.add("orange");
//        dictionary.setStringArray("list", list);
//                
//        final Dictionary dictionary_1 = new Dictionary();
//        dictionary_1.setString("name", "Juan");
//        dictionary_1.setInt("age", 30);
//        dictionary.setDictionary("dictionary", dictionary_1);
//        
//        final Dictionary dictionary_2 = new Dictionary();
//        dictionary_2.setString("key1", "value1");
//        dictionary_2.setString("key2", "value2");
//        dictionary_1.setDictionary("another_dictionary", dictionary_2);
//
//        dictionary.store(new java.io.FileOutputStream(file));
//        System.out.println(dictionary);
//    }
}
