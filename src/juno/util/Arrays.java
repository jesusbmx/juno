
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
  public static <T> T[] of(T... elements) { return elements; }
  
  /**
   * Valida si una array esta vacia o es nulo.
   *
   * @param array a evaluar
   * @return boolean
   */
  public static <T> boolean isEmpty(T[] array) {
    return array == null || array.length == 0;
  }
  
  /**
   * Verifica si el índice existe en el array aunque sea nulo.
   *
   * @param array Un array con los índices para verificar
   * @param index índice para verificar.
   * @return @true si el array contiene el indice.
   */
  public static <T> boolean hasIndex(T[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static <T> T getValueOrDefault(T[] array, int index, T defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static <T> T getValue(T[] array, int index) {
    return getValueOrDefault(array, index, null);
  }
  
  public static <V> V getFirst(V[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static <V> V getLast(V[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
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
   
  public static String[] convertArrayToString(Object[] array, Func<Object, String> func) {
    return map(array, String.class, func);
  }
  
  public static String[] convertArrayToString(Object... array) {
    return map(array, String.class, Func.OBJ_TO_STR);
  }
  
  public static <V> V[] fill(V[] array, V value, int start, int end) {
    if (array == null) return null;
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
  
  public static <V> V[] filter(V[] array, Func<V, Boolean> func) {
    if (array == null) return null;
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
  
  public static <V> boolean every(V[] array, Func<V, Boolean> func) {
    if (array == null) return false;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (!func.call(object)) {
        return false;
      }
    }
    return true;
  }
  
  public static <V> boolean some(V[] array, Func<V, Boolean> func) {
    if (array == null) return false;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        return true;
      }
    }
    return false;
  }
  
  public static <V> V find(V[] array, Func<V, Boolean> func) {
    if (array == null) return null;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        return object;
      }
    }
    return null;
  }
  
  public static <I, R> R[] map(I[] array, Class<R> componentType, Func<I, R> func) {
    if (array == null) return null;
    final List<R> result = new ArrayList<R>(array.length);
    for (int i = 0; i < array.length; i++) {
      I object = array[i];
      result.add(func.call(object));
    }
    R[] a = (R[]) Array.newInstance(componentType, 0);
    return result.toArray(a);
  }
}
