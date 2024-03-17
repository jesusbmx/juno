package juno.content;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import org.w3c.dom.NodeList;

public class FileDataStorage implements DataStorage {

    private Document document;
    private final File xmlFile;
    private boolean hasUncommittedChanges;

    public FileDataStorage(String filePath) throws Exception {
        xmlFile = new File(filePath);
        if (!xmlFile.exists()) {
            createNewDocument();
        } else {
            loadDocument();
        }
    }

    private void createNewDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();
        Element rootElement = document.createElement("data");
        document.appendChild(rootElement);
        saveDocument();
    }

    private void loadDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }

    private void saveDocument() throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
        hasUncommittedChanges = false;
    }

    @Override
    public void delete(String key) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getChildNodes();

        // Buscar y eliminar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            if (entries.item(i) instanceof Element) {
                Element entry = (Element) entries.item(i);
                if (entry.getAttribute("key").equals(key)) {
                    rootElement.removeChild(entry);
                    hasUncommittedChanges = true;
                    return;
                }
            }
        }
    }

    @Override
    public String getString(String key, String defValue) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("string");

        // Buscar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                return entry.getTextContent();
            }
        }

        // Si no se encuentra la clave, devolver el valor predeterminado
        return defValue;
    }

    @Override
    public void putString(String key, String value) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("string");

        // Buscar si ya existe una entrada con la misma clave
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Si la entrada existe, actualizar su contenido y salir del método
                entry.setTextContent(value);
                hasUncommittedChanges = true;
                return;
            }
        }

        // Si no se encontró una entrada existente con la misma clave, crear una nueva
        Element newEntry = document.createElement("string");
        newEntry.setAttribute("key", key);
        newEntry.setTextContent(value);
        rootElement.appendChild(newEntry);
        hasUncommittedChanges = true;
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("stringSet");

        // Buscar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Convertir el contenido de la entrada a un conjunto de cadenas
                return parseStringSet(entry);
            }
        }

        // Si no se encuentra la clave, devolver el valor predeterminado
        return defValues;
    }
    
    // Método auxiliar para convertir un elemento XML a un conjunto de cadenas
    private Set<String> parseStringSet(Element stringSetElement) {
        Set<String> stringSet = new LinkedHashSet<String>();
        NodeList stringNodes = stringSetElement.getElementsByTagName("string");

        // Procesar todas las cadenas dentro del conjunto
        for (int i = 0; i < stringNodes.getLength(); i++) {
            Element stringNode = (Element) stringNodes.item(i);
            String stringValue = stringNode.getTextContent();
            stringSet.add(stringValue);
        }

        return stringSet;
    }

    @Override
    public void putStringSet(String key, Set<String> values) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("stringSet");

        // Buscar si ya existe una entrada con la misma clave
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Si la entrada existe, eliminarla para reemplazarla con la nueva
                rootElement.removeChild(entry);
                break;
            }
        }

        // Crear una nueva entrada y agregar los elementos del conjunto como cadenas dentro de la etiqueta <stringSet>
        Element newStringSetEntry = document.createElement("stringSet");
        newStringSetEntry.setAttribute("key", key);

        for (String value : values) {
            Element stringElement = document.createElement("string");
            stringElement.setTextContent(value);
            newStringSetEntry.appendChild(stringElement);
        }

        rootElement.appendChild(newStringSetEntry);
        hasUncommittedChanges = true;
    }

    @Override
    public Integer getInt(String key, Integer defValue) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("string");

        // Buscar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                String value = entry.getTextContent();
                return value != null ? Integer.parseInt(value) : defValue;
            }
        }

        // Si no se encuentra la clave, devolver el valor predeterminado
        return defValue;
    }

    @Override
    public void putInt(String key, Integer value) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("int");

        // Buscar si ya existe una entrada con la misma clave
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Si la entrada existe, actualizar su contenido y salir del método
                entry.setTextContent(value.toString());
                hasUncommittedChanges = true;
                return;
            }
        }

        // Si no se encontró una entrada existente con la misma clave, crear una nueva
        Element newEntry = document.createElement("int");
        newEntry.setAttribute("key", key);
        newEntry.setTextContent(value.toString());
        rootElement.appendChild(newEntry);
        hasUncommittedChanges = true;
    }

    @Override
    public Boolean getBoolean(String key, Boolean defValue) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("boolean");

        // Buscar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                String value = entry.getTextContent();
                return value != null ? Boolean.parseBoolean(value) : defValue;
            }
        }

        // Si no se encuentra la clave, devolver el valor predeterminado
        return defValue;
    }

    @Override
    public void putBoolean(String key, Boolean value) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("boolean");

        // Buscar si ya existe una entrada con la misma clave
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Si la entrada existe, actualizar su contenido y salir del método
                entry.setTextContent(value.toString());
                hasUncommittedChanges = true;
                return;
            }
        }

        // Si no se encontró una entrada existente con la misma clave, crear una nueva
        Element newEntry = document.createElement("boolean");
        newEntry.setAttribute("key", key);
        newEntry.setTextContent(value.toString());
        rootElement.appendChild(newEntry);
        hasUncommittedChanges = true;
    }

    @Override
    public Float getFloat(String key, Float defValue) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("float");

        // Buscar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                String value = entry.getTextContent();
                return value != null ? Float.parseFloat(value) : defValue;
            }
        }

        // Si no se encuentra la clave, devolver el valor predeterminado
        return defValue;
    }

    @Override
    public void putFloat(String key, Float value) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("float");

        // Buscar si ya existe una entrada con la misma clave
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Si la entrada existe, actualizar su contenido y salir del método
                entry.setTextContent(value.toString());
                hasUncommittedChanges = true;
                return;
            }
        }

        // Si no se encontró una entrada existente con la misma clave, crear una nueva
        Element newEntry = document.createElement("float");
        newEntry.setAttribute("key", key);
        newEntry.setTextContent(value.toString());
        rootElement.appendChild(newEntry);
        hasUncommittedChanges = true;
    }

    @Override
    public Double getDouble(String key, Double defValue) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("double");

        // Buscar la entrada con la clave dada
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                String value = entry.getTextContent();
                return value != null ? Double.parseDouble(value) : defValue;
            }
        }

        // Si no se encuentra la clave, devolver el valor predeterminado
        return defValue;
    }

    @Override
    public void putDouble(String key, Double value) throws Exception {
        Element rootElement = document.getDocumentElement();
        NodeList entries = rootElement.getElementsByTagName("double");

        // Buscar si ya existe una entrada con la misma clave
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);
            if (entry.getAttribute("key").equals(key)) {
                // Si la entrada existe, actualizar su contenido y salir del método
                entry.setTextContent(value.toString());
                hasUncommittedChanges = true;
                return;
            }
        }

        // Si no se encontró una entrada existente con la misma clave, crear una nueva
        Element newEntry = document.createElement("double");
        newEntry.setAttribute("key", key);
        newEntry.setTextContent(value.toString());
        rootElement.appendChild(newEntry);
        hasUncommittedChanges = true;
    }

    @Override
    public void commit() throws Exception {
        if (hasUncommittedChanges) {
            saveDocument();
        }
    }

//    public static void main(String[] args) {
//        try {
//            DataStorage storage = new FileDataStorage("data.xml");
//            //for (int i = 0; i < 10; i++) {
//
//                storage.putString("clave1", "valor1");
//                
//                Set<String> set = new LinkedHashSet<String>();
//                set.add("A");
//                set.add("B");
//                storage.putStringSet("clave2", set);
//
//                storage.putInt("clave3", 10);
//                storage.putFloat("clave4", 10.2f);
//                storage.putDouble("clave5", 5.2d);
//                storage.putBoolean("clave6", true);
//                storage.commit();
//
//                System.out.println(storage.getString("clave1", ""));
//                System.out.println(storage.getStringSet("clave2", null));
//                System.out.println(storage.getInt("clave3", -1));
//                System.out.println(storage.getFloat("clave4", -1f));
//                System.out.println(storage.getDouble("clave5", -1d));
//                System.out.println(storage.getBoolean("clave6", false));
//            //}
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
