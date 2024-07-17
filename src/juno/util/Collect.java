package juno.util;

import java.util.Collection;
import java.util.LinkedHashSet;

public final class Collect {
  
  private Collect() {}
  
  
  public static boolean isEmpty(Collection it) {
    return Validate.isNull(it) || it.isEmpty();
  }
  
  public static <T> void addAll(Collection<T> out, T... elements) { 
    if (Validate.isNotNull(elements)) {
      for (T e : elements) {
        out.add(e);
      }
    }
  }
   
  /**
   * Devuelve un nuevo HashSet con los elementos dados.
   *
   * @param <T> tipo de lista
   * @param elements `1, 2, 3, 4, 5, 6, 7`
   * @return </T> 
   */
  public static <T> LinkedHashSet<T> setOf(T... elements) {
    if (Validate.isNull(elements)) return null;
    LinkedHashSet<T> list = new LinkedHashSet<T>(elements.length);
    addAll(list, elements);
    return list;
  }

  /**
   * Devuelve una colección que aplica {@code function} a cada elemento de 
   * {@code fromCollection}.
   *
   * La colección devuelta es una vista en vivo de {@code fromCollection};
   * los cambios a uno afectan al otros.
   */
  public static <I, R> TransformedCollect<I, R> transform(
      Collection<I> fromCollection, 
      Func<? super I, R> function
  ) {
    return new TransformedCollect<I, R>(fromCollection, function);
  }
}
