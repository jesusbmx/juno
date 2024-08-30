package juno.content;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MemoryDataStorage implements DataStorage {

    final Map<String, String> values;

    public MemoryDataStorage(Map<String, String> values) {
        this.values = values;
    }

    public MemoryDataStorage() {
        this(new HashMap<String, String>());
    }
    
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

    @Override
    public Set<String> getAllKeys() throws Exception {
        return values.keySet();
    }

    @Override
    public Map<String, String>  multiGet(List<String> keys) throws Exception {
        Map<String, String> result = new LinkedHashMap<String, String>(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            final String key = keys.get(i);
            final String value = values.get(key);
            result.put(key, value);
        }
        return result;
    }

    @Override
    public void multiSet(Map<String, String> keyValuePairs) throws Exception {
        for (Map.Entry<String, String> entry : keyValuePairs.entrySet()) {
            values.put(entry.getKey(), entry.getValue()); 
        }
    }

    @Override
    public void multiRemove(List<String> keys) throws Exception {
        for (int i = 0; i < keys.size(); i++) {
            values.remove(keys.get(i));
        }
    }
}
