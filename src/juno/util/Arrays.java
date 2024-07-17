
package juno.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class Arrays {
    
  private Arrays() {}
  
  /**
   * Devuelve un arreglo con los elementos dados.
   *
   * @param <T> tipo de array
   * @param elements `1, 2, 3, 4, 5, 6, 7`
   * @return </T> 
   */
  public static <T> T[] arrayOf(T... elements) { return elements; }
  
  /**
   * Devuelve una nueva lista de elementos dados.
   *
   * @param <T> tipo de lista
   * @param elements `1, 2, 3, 4, 5, 6, 7`
   * @return </T> 
   */
  public static <T> ArrayList<T> listOf(T... elements) {
    if (Validate.isNull(elements)) return null;
    ArrayList<T> list = new ArrayList<T>(elements.length);
    Collect.addAll(list, elements);
    return list;
  }
  
  /**
   * Valida si una array esta vacia o es nulo.
   *
   * @param array a evaluar
   * @return boolean
   */
  public static <T> boolean isEmpty(T[] array) {
    return Validate.isNull(array) || array.length == 0;
  }
  
  /**
   * Verifica si el índice existe en el array aunque sea nulo.
   *
   * @param array Un array con los índices para verificar
   * @param index índice para verificar.
   * @return @true si el array contiene el indice.
   */
  public static <T> boolean hasIndex(T[] array, int index) {
    if (Validate.isNull(array)) return false;
    return index > -1 && array.length > index;
  }
  
  public static <T> boolean hasIndex(List<T> list, int index) {
    if (Validate.isNull(list)) return false;
    return index > -1 && list.size() > index;
  }
  
  public static <T> T getValueOrDefault(T[] array, int index, T defaultVal) {
    return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static <T> T getValue(T[] array, int index) {
    return getValueOrDefault(array, index,  null);
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
  
  public static <V> void reverse(final V[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    V tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static <V> void reverse(final V[] array) {
    reverse(array, 0, array.length);
  }
  
  public static List<String> convertListToString(List list, Func<Object, String> func) {
    return map(list, func);
  }
  
  public static List<String> convertListToString(List list) {
    return map(list, Func.OBJ_TO_STR);
  }
  
  public static String[] convertArrayToString(Object[] array, Func<Object, String> func) {
    return map(array, String.class, func);
  }
  
  public static String[] convertArrayToString(Object... array) {
    return map(array, String.class, Func.OBJ_TO_STR);
  }
  
  public static <V> List<V> fill(List<V> list, V value, int start, int end) {
    if (Validate.isNull(list)) return null;
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
  
  public static <V> V[] fill(V[] array, V value, int start, int end) {
    if (Validate.isNull(array)) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static <V> V[] fill(V[] array, V value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static <V> V[] fill(V[] array, V value) {
    return fill(array, value, 0, array.length);
  }
  
   public static <V> List<V> filter(Iterable<V> list, Func<V, Boolean> func) {
    if (Validate.isNull(list)) return null;
    List<V> result = new ArrayList<V>();
    for (V v : list) {
      if (func.call(v)) {
        result.add(v);
      } 
    }
    return result;
  }
  
  public static <V> V[] filter(V[] array, Func<V, Boolean> func) {
    if (Validate.isNull(array)) return null;
    final List<V> result = new ArrayList<V>(array.length);
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        result.add(object);
      }
    }
    
    final Class<V> componentType = (Class<V>) array.getClass().getComponentType();
    V[] a = (V[]) Array.newInstance(componentType, 0);
    return result.toArray(a);
  }
  
  public static <V> boolean every(Iterable<V> list, Func<V, Boolean> func) {
    if (Validate.isNull(list)) return false;
    for (V v : list) {
      if (!func.call(v)) {
        return false;
      }   
    }
    return true;
  }
  
  public static <V> boolean every(V[] array, Func<V, Boolean> func) {
    if (Validate.isNull(array)) return false;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (!func.call(object)) {
        return false;
      }
    }
    return true;
  }
  
  public static <V> boolean some(Iterable<V> list, Func<V, Boolean> func) {
    if (Validate.isNull(list)) return false;
    for (V v : list) {
      if (func.call(v)) {
        return true;
      }   
    }
    return false;
  }
  
  public static <V> boolean some(V[] array, Func<V, Boolean> func) {
    if (Validate.isNull(array)) return false;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        return true;
      }
    }
    return false;
  }
  
  public static <V> V find(Iterable<V> list, Func<V, Boolean> func) {
    if (Validate.isNull(list)) return null;
    for (V v : list) {
      if (func.call(v)) {
        return v;
      }
    }
    return null;
  }
  
  public static <V> V find(V[] array, Func<V, Boolean> func) {
    if (Validate.isNull(array)) return null;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        return object;
      }
    }
    return null;
  }
  
  public static <I, R> List<R> map(Iterable<I> list, Func<I, R> func) {
    if (Validate.isNull(list)) return null;
    List<R> result = new ArrayList<R>();
    for (I i : list) {
      result.add(func.call(i));
    }
    return result;
  }
  
  public static <I, R> R[] map(I[] array, Class<R> componentType, Func<I, R> func) {
    if (Validate.isNull(array)) return null;
    final List<R> result = new ArrayList<R>(array.length);
    for (int i = 0; i < array.length; i++) {
      I object = array[i];
      result.add(func.call(object));
    }
    R[] a = (R[]) Array.newInstance(componentType, 0);
    return result.toArray(a);
  }
}
