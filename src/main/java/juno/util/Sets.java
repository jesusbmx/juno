package juno.util;

import java.util.LinkedHashSet;
import java.util.Set;

public final class Sets {
  
  private Sets() {}
  
  
  public static boolean isEmpty(Set it) {
    return it == null || it.isEmpty();
  }
  
  /**
   * Devuelve un nuevo HashSet con los elementos dados.
   *
   * @param <T> tipo de lista
   * @param elements `1, 2, 3, 4, 5, 6, 7`
   * @return </T> 
   */
  public static <T> LinkedHashSet<T> of(T... elements) {
    if (Validate.isNull(elements)) return null;
    LinkedHashSet<T> list = new LinkedHashSet<T>(elements.length);
    for (T e : elements) {
      list.add(e);
    }
    return list;
  }
  
}
