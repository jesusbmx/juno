package juno.content;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import juno.io.IOUtils;
import juno.tuple.Pair;

public class FileDataStorage implements DataStorage {

    public final File file;
    public Properties properties;

    public FileDataStorage(File file) {
        this.file = file;
    }
    
    public FileDataStorage(String file) {
        this(new File(file));
    }
    
    private Properties getProperties() throws Exception {
        if (properties == null) {
            properties = new Properties();
            loadProperties(properties);
        }
        return properties;
    }
    
    private void loadProperties(Properties p) throws Exception {
        if (!file.exists()) {
            return;
        }
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            p.load(in);
            
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
    
    private void saveProperties(Properties p) throws Exception {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            p.store(out, null);
            
        } finally {
            IOUtils.closeQuietly(out);
        }
    }
    
    @Override
    public void removeItem(String key) throws Exception {
        final Properties p = getProperties();
        p.remove(key);
        saveProperties(p);
    }

    @Override
    public String getItem(String key, String defValue) throws Exception {
        return getProperties().getProperty(key, defValue);
    }

    @Override
    public void setItem(String key, String value) throws Exception {
        final Properties p = getProperties();
        p.setProperty(key, value);
        saveProperties(p);
    }
    
    @Override
    public Set<String> getAllKeys() throws Exception {
        return getProperties().stringPropertyNames();
    }
    
    @Override
    public List<String> multiGet(List<String> keys) throws Exception {
        List<String> result = new ArrayList<String>(keys.size());
        final Properties p = getProperties();
        for (int i = 0; i < keys.size(); i++) {
            result.add(p.getProperty(keys.get(i), null));
        }
        return result;
    }

    @Override
    public void multiSet(List<Pair<String, String>> keyValuePairs) throws Exception {
        final Properties p = getProperties();
        for (int i = 0; i < keyValuePairs.size(); i++) {
            final Pair<String, String> pair = keyValuePairs.get(i);
            p.put(pair.getFirst(), pair.getSecond());
        }
        saveProperties(p);
    }

    @Override
    public void multiRemove(List<String> keys) throws Exception {
        final Properties p = getProperties();
        for (int i = 0; i < keys.size(); i++) {
            p.remove(keys.get(i));
        }
        saveProperties(p);
    }
    
    //    public static void main(String[] args) throws Exception {
//        DataStorage storage = new FileDataStorage("test.txt");
//        storage.setItem("key1", "hola");
//        System.out.println(storage.getItem("key1", null));
//    }
}
