package juno.content;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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
    
    public void putAll(Dictionary dict) {
        values.putAll(dict.values);
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
    
    public Set<String> keySet() {
        return values.keySet();
    }
    
    public Collection<Object> values() {
        return values.values();
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
    
    public Dictionary getDictionary(String key, Dictionary defValue) {
        final Dictionary value = (Dictionary) values.get(key);
        return value == null ? defValue : value;
    }
    
    public Dictionary getDictionary(String key) {
        return getDictionary(key, null);
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

    public void setDictionary(String key, Dictionary value) {
        if (value == this) {
            throw new IllegalArgumentException("Cannot set the '" + key + "' dictionary to itself");
        }
        values.put(key, value);
    }
   
    
    private static String getTabs(int count) {
        final char[] tabs = new char[count];
        Arrays.fill(tabs, '\t');
        return new String(tabs);
    }
    
    private static void write(StringBuilder out, Dictionary dict, int tab) {
        for (String key : dict.keySet()) {
            out.append(getTabs(tab));
            out.append(key).append(": ");
            
            if (dict.isNull(key)) {
                out.append("null").append("\n");
            } else if (dict.isString(key)) {
                out.append(dict.getString(key)).append("\n");
            } else if (dict.isInt(key)) {
                out.append(dict.getInt(key)).append("\n");
            } else if (dict.isBoolean(key)) {
                out.append(dict.getBoolean(key)).append("\n");
            } else if (dict.isLong(key)) {
                out.append(dict.getLong(key)).append("\n");
            } else if (dict.isFloat(key)) {
                out.append(dict.getFloat(key)).append("\n");
            } else if (dict.isDictionary(key)) {
                out.append("\n");
                write(out, dict.getDictionary(key, null), tab + 1);
            }
        }
    }

    public String toString(int tab) {
        final StringBuilder sb = new StringBuilder();
        write(sb, this, tab);
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return toString(0);
    }
    

    public static void main(String[] args) {
        final Dictionary dict = new Dictionary();
        dict.setString("null", null);
        dict.setString("string", "Hola mundo");
        dict.setInt("int", 10);
        dict.setFloat("float", 10.2f);
        dict.setLong("long", System.currentTimeMillis());
        dict.setBoolean("bool", true);
        final Dictionary clone = dict.clone();
        
        final Dictionary subdict1 = new Dictionary();
        subdict1.putAll(clone);
        dict.setDictionary("subdict1", subdict1);
        
        final Dictionary subdict2 = new Dictionary();
        subdict2.putAll(clone);
        subdict1.setDictionary("subdict2", subdict2);
        
        System.out.println(dict);
    }
}
