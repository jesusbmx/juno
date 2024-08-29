package juno.util;

public class IndexedValue<T> {
    
    public int index;
    public T value;

    public IndexedValue(int index, T value) {
        this.index = index;
        this.value = value;
    }
    
    public IndexedValue() {
        
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setValue(T value) {
        this.value = value;
    }
    
    
}
