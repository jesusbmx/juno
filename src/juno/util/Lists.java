package juno.util;

import java.util.ArrayList;
import java.util.List;

public final class Lists {
    
  private Lists() {}  
    
  /**
   * Devuelve una nueva lista de elementos dados.
   *
   * @param <T> tipo de lista
   * @param elements `1, 2, 3, 4, 5, 6, 7`
   * @return </T> 
   */
  public static <T> ArrayList<T> of(T... elements) {
    if (elements == null) return null;
    ArrayList<T> list = new ArrayList<T>(elements.length);
    for (T element : elements) {
        list.add(element);
    }
    return list;
  }
  
   /**
   * Valida si una array esta vacia o es nulo.
   *
   * @param list a evaluar
   * @return boolean
   */
  public static <T> boolean isEmpty(List<T> list) {
    return list == null || list.isEmpty();
  }
  
  public static <T> boolean hasIndex(List<T> list, int index) {
    return list != null && index >= 0 && index < list.size();
  }
  
  public static <T> T getValueOrDefault(List<T> list, int index, T defaultVal) {
    return hasIndex(list, index) ? list.get(index) : defaultVal;
  }
  
  public static <T> T getValue(List<T> list, int index) {
    return getValueOrDefault(list, index,  null);
  }
  
  public static <V> V getFirst(List<V> collection) {
    return collection.isEmpty() ? null : collection.get(0);
  }
  
  public static <V> V getLast(List<V> collection) {
    final int len = collection.size();
    return collection.isEmpty() ? null : collection.get(len - 1);
  }
    
  /**
   * Corta una lista en sublistas segun la longitud establecida.
   * 
   * @param list lista a cortar.
   * @param len longitud para las sublistas
   * @return 
   */
  public static <V> List<List<V>> partition(List<V> list, int len) {
    List<List<V>> parts = new ArrayList<List<V>>();
    final int size = list.size();
    for (int i = 0; i < size; i += len) {
      parts.add(new ArrayList<V>(
              list.subList(i, Math.min(size, i + len))
      ));
    }
    return parts;
  }
  
  public static List<String> convertListToString(List list, Func<Object, String> func) {
    return map(list, func);
  }
  
  public static List<String> convertListToString(List list) {
    return map(list, Func.OBJ_TO_STR);
  }
  
  public static <V> List<V> fill(List<V> list, V value, int start, int end) {
    if (list == null) return null;
    for (int i = start; i < end; i++) {
      list.set(i, value);
    }
    return list;
  }
  
  public static <V> List<V> fill(List<V> list, V value, int start) {
    return fill(list, value, start, list.size());
  }
  
  public static <V> List<V> fill(List<V> list, V value) {
    return fill(list, value, 0, list.size());
  }
  
  public static <V> List<V> filter(Iterable<V> list, Func<V, Boolean> func) {
    if (list == null) return null;
    List<V> result = new ArrayList<V>();
    for (V v : list) {
      if (func.call(v)) {
        result.add(v);
      } 
    }
    return result;
  }
  
  public static <V> boolean every(Iterable<V> list, Func<V, Boolean> func) {
    if (list == null) return false;
    for (V v : list) {
      if (!func.call(v)) {
        return false;
      }
    }
    return true;
  }
  
   public static <V> boolean some(Iterable<V> list, Func<V, Boolean> func) {
    if (list == null) return false;
    for (V v : list) {
      if (func.call(v)) {
        return true;
      }   
    }
    return false;
  }
   
   public static <V> V find(Iterable<V> list, Func<V, Boolean> func) {
    if (list == null) return null;
    for (V v : list) {
      if (func.call(v)) {
        return v;
      }
    }
    return null;
  }
   
  public static <I, R> List<R> map(Iterable<I> list, Func<I, R> func) {
    if (list == null) return null;
    List<R> result = new ArrayList<R>();
    for (I it : list) {
      result.add(func.call(it));
    }
    return result;
  }
  
  public static <V> Iterable<IndexedValue<V>> withIndex(Iterable<V> iterable) {
      return new IndexingIterable<V>(iterable.iterator());
  }
}
