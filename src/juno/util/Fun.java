
package juno.util;

/**
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 *
 * @since 1.8
 */
public interface Fun<T, R> {

  public static final Fun OBJ_TO_STR = new Fun<Object, String>() {
    @Override public String apply(Object t) {
      return Convert.toString(t, null);
    }
  };
  
  /**
   * Applies this function to the given argument.
   *
   * @param t the function argument
   * @return the function result
   */
  R apply(T t);

}
