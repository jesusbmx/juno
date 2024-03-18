package juno.content;

import java.util.HashMap;
import java.util.Map;

public class MemoryDataStorage implements DataStorage {

    final Map<String, String> values = new HashMap<String, String>();

    @Override
    public void removeItem(String key) throws Exception {
        values.remove(key);
    }

    @Override
    public String getItem(String key, String defValue) throws Exception {
        String value = values.get(key);
        return value != null ? value: defValue;
    }

    @Override
    public void setItem(String key, String value) throws Exception {
        values.put(key, value);
    }

    
}
