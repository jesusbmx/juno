package juno.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import juno.tuple.Pair;

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

    @Override
    public Set<String> getAllKeys() throws Exception {
        return values.keySet();
    }

    @Override
    public List<String> multiGet(List<String> keys) throws Exception {
        List<String> result = new ArrayList<String>(keys.size());
        for (int i = 0; i < keys.size(); i++) {
            String value = getItem(keys.get(i), null);
            result.add(value);
        }
        return result;
    }

    @Override
    public void multiSet(List<Pair<String, String>> keyValuePairs) throws Exception {
        for (int i = 0; i < keyValuePairs.size(); i++) {
            final Pair<String, String> pair = keyValuePairs.get(i);
            values.put(pair.getFirst(), pair.getSecond());
        }
    }

    @Override
    public void multiRemove(List<String> keys) throws Exception {
        for (int i = 0; i < keys.size(); i++) {
            values.remove(keys.get(i));
        }
    }
}
