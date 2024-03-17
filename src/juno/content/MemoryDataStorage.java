package juno.content;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MemoryDataStorage implements DataStorage {

    final Map<String, Object> values = new HashMap<String, Object>();

    @Override
    public void delete(String key) throws Exception {
        values.remove(key);
    }

    @Override
    public void commit() throws Exception {
    }

    @Override
    public String getString(String key, String defValue) throws Exception {
        Object value = values.get(key);
        return value != null ? value.toString() : defValue;
    }

    @Override
    public void putString(String key, String value) throws Exception {
        values.put(key, value);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) throws Exception {
        Set<String> value = (Set<String>) values.get(key);
        return value != null ? value : defValues;
    }

    @Override
    public void putStringSet(String key, Set<String> value) throws Exception {
        values.put(key, value);
    }

    @Override
    public Integer getInt(String key, Integer defValue) throws Exception {
        Object value = values.get(key);
        return value != null ? Integer.parseInt(value.toString()) : defValue;
    }

    @Override
    public void putInt(String key, Integer value) throws Exception {
        values.put(key, value);
    }

    @Override
    public Float getFloat(String key, Float defValue) throws Exception {
        Object value = values.get(key);
        return value != null ? Float.parseFloat(value.toString()) : defValue;
    }

    @Override
    public void putFloat(String key, Float value) throws Exception {
        values.put(key, value);
    }

    @Override
    public Double getDouble(String key, Double defValue) throws Exception {
        Object value = values.get(key);
        return value != null ? Double.parseDouble(value.toString()) : defValue;
    }

    @Override
    public void putDouble(String key, Double value) throws Exception {
        values.put(key, value);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defValue) throws Exception {
        Object value = values.get(key);
        return value != null ? Boolean.parseBoolean(value.toString()) : defValue;
    }

    @Override
    public void putBoolean(String key, Boolean value) throws Exception {
        values.put(key, value);
    }
}
