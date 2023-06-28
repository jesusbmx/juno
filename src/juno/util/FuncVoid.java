package juno.util;

public interface FuncVoid<Input> {
    
  /**
   * Applies this function to the given argument.
   *
   * @param it the function argument
   */
  void call(Input it);

  
  interface Throws<Input> {
      
    void call(Input it) throws Exception;
  }
}
