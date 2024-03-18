package juno.content;

import java.util.List;
import java.util.Set;
import juno.tuple.Pair;

public interface DataStorage {
       
    void removeItem(String key) throws Exception;
    
    String getItem(String key, String defValue) throws Exception;

    void setItem(String key, String value) throws Exception;
    
    Set<String> getAllKeys() throws Exception;
    
    List<String> multiGet(List<String> keys) throws Exception;
    
    void multiSet(List<Pair<String, String>> keyValuePairs) throws Exception;
    
    void multiRemove(List<String> keys) throws Exception;
}