package juno.content;

import java.util.Set;

public interface DataStorage {
       
    void delete(String key) throws Exception;
    
    String getString(String key, String defValue) throws Exception;

    void putString(String key, String value) throws Exception;

    Set<String> getStringSet(String key, Set<String> defValues) throws Exception;
    
    void putStringSet(String key, Set<String> values) throws Exception;
    
    Integer getInt(String key, Integer defValue) throws Exception;

    void putInt(String key, Integer value) throws Exception;
    
    Float getFloat(String key, Float defValue) throws Exception;

    void putFloat(String key, Float value) throws Exception;
    
    Double getDouble(String key, Double defValue) throws Exception;

    void putDouble(String key, Double value) throws Exception;
    
    Boolean getBoolean(String key, Boolean defValue) throws Exception;

    void putBoolean(String key, Boolean value) throws Exception;

    void commit() throws Exception;   
}