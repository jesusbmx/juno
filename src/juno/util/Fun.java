
package juno.util;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <Input> the type of the input to the function
 * @param <Result> the type of the result of the function
 *
 * @since 1.8
 */
public interface Fun<Input, Result> {

  public static final Fun OBJ_TO_STR = new Fun<Object, String>() {
    @Override public String apply(Object it) {
      return Convert.toString(it, null);
    }
  };
  
  /**
   * Applies this function to the given argument.
   *
   * @param it the function argument
   * @return the function result
   */
  Result apply(Input it);

}
