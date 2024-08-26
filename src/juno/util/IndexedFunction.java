package juno.util;

public interface IndexedFunction<Input, Result> {
    
    public static final IndexedFunction OBJ_TO_STR = new IndexedFunction<Object, String>() {
        @Override public String call(Object element, int index) {
          return Convert.toString(element, null);
        }
    };
    
    /**
     * Applies this function to the given argument.
     *
     * @param element the function argument
     * @param index
     * @return the function result
     */
    Result call(Input element, int index);
 
}
