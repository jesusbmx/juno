package juno.content;

public interface DataStorage {
       
    void removeItem(String key) throws Exception;
    
    String getItem(String key, String defValue) throws Exception;

    void setItem(String key, String value) throws Exception;
}