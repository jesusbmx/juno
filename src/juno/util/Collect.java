package juno.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import static juno.util.Util.isNotNull;
import static juno.util.Util.isNull;

public final class Collect {
  
  private Collect() {}
  
  /**
   * Valida si una array esta vacia o es nulo.
   *
   * @param array a evaluar
   * @return boolean
   */
  public static <T> boolean isEmpty(T[] array) {
    return isNull(array) || array.length == 0;
  }
  
  public static boolean isEmpty(Collection it) {
    return isNull(it) || it.isEmpty();
  }
  
  public static boolean isEmpty(Map it) {
    return isNull(it) || it.isEmpty();
  }

  /**
   * Verifica si el índice existe en el array aunque sea nulo.
   *
   * @param array Un array con los índices para verificar
   * @param index índice para verificar.
   * @return @true si el array contiene el indice.
   */
  public static <T> boolean hasIndex(T[] array, int index) {
    if (isNull(array)) return false;
    return index > -1 && array.length > index;
  }
  
  public static <T> boolean hasIndex(List<T> list, int index) {
    if (isNull(list)) return false;
    return index > -1 && list.size() > index;
  }
  
  public static <K, V> boolean hasKey(Map<K, V> map, K key) {
    if (isNull(map)) return false;
    return map.containsKey(key);
  }
  
  public static <T> T get(T[] array, int index, T defaultVal) {
    return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static <T> T get(T[] array, int index) {
    return get(array, index,  null);
  }
    
  public static <T> T get(List<T> list, int index, T defaultVal) {
    return hasIndex(list, index) ? list.get(index) : defaultVal;
  }
  
  public static <T> T get(List<T> list, int index) {
    return get(list, index,  null);
  }
  
  public static <K, V> V get(Map<K, V> map, K key, V defaultVal) {
    return hasKey(map, key) ? map.get(key) : defaultVal;
  }
  
  public static <K, V> V get(Map<K, V> map, K key) {
    return get(map, key, null);
  }
  
  public static <T> void add(Collection<T> out, T... elements) { 
    if (isNotNull(elements))
      for (T e : elements) 
        out.add(e);
  }
  
  public static <K, V> void put(Map<K, V> out, Object... namesAndValues) {
    if (isNull(namesAndValues)) return;
    for (int i = 0; i < namesAndValues.length; i += 2) {
      K name = (K) namesAndValues[i];
      V value = (V) namesAndValues[i + 1];
      out.put(name, value);
    }
  }
  
  public static void append(StringBuilder out, Object... elements) {
    if (isNotNull(elements))
      for (Object e : elements) 
        out.append(e);
  }
  
  public static void append(StringBuilder out, Collection elements) {
    if (isNotNull(elements))
      for (Object e : elements) 
        out.append(e);
  }
  
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
    if (isNull(elements)) return null;
    ArrayList<T> list = new ArrayList<T>(elements.length);
    add(list, elements);
    return list;
  }

  /**
   * Devuelve un nuevo HashSet con los elementos dados.
   *
   * @param <T> tipo de lista
   * @param elements `1, 2, 3, 4, 5, 6, 7`
   * @return </T> 
   */
  public static <T> LinkedHashSet<T> setOf(T... elements) {
    if (isNull(elements)) return null;
    LinkedHashSet<T> list = new LinkedHashSet<T>(elements.length);
    add(list, elements);
    return list;
  }

  /**
   * Devuelve un mapa con los contenidos especificados, dado como una lista de
   * pares donde el primer componente es la clave y el segundo es el valor.
   *
   * @param <K> tipo de clave
   * @param <V> tipo de valor
   * @param namesAndValues contenidos `"nombre", "jesus"`
   * @return HashMap</V></K> 
   */
  public static <K, V> LinkedHashMap<K, V> mapOf(Object... namesAndValues) {
    if (isNull(namesAndValues)) return null;
    if (namesAndValues.length % 2 != 0) {
      throw new IllegalArgumentException("Expected alternating header names and values");
    }
    LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(namesAndValues.length / 2);
    put(map, namesAndValues);
    return map;
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
  
  public static List<String> toListString(List list, Func<Object, String> func) {
    return map(list, func);
  }
  
  public static List<String> toListString(List list) {
    return map(list, Func.OBJ_TO_STR);
  }
  
  public static String[] toArrayString(Object[] array, Func<Object, String> func) {
    return map(array, String.class, func);
  }
  
  public static String[] toArrayString(Object... array) {
    return map(array, String.class, Func.OBJ_TO_STR);
  }
  
  /**
   * <p>Une todos los elementos de una colección en una cadena.</p>
   * 
   * @param args elementos
   * @param separator delimitador entre elementos
   * @param func función ha aplicar para cada elemento
   * @return String
   */
  public static String join(Iterable args, String separator, Func<Object, String> func) {
    final StringBuilder sb = new StringBuilder();
    int i = 0;
    for (Object arg : args) {
      if (i > 0) sb.append(separator);
      sb.append(func.call(arg));
      i++;
    }
    return sb.toString();
  }
  
  public static String join(Iterable args, String separator) {
    return join(args, separator, Func.OBJ_TO_STR);
  }
  
  public static String join(Iterable args, Func<Object, String> func) {
    return join(args, ", ", func);
  }
  
  public static String join(Iterable args) {
    return join(args, ", ");
  }
  
  /**
   * <p>Une todos los elementos de un arreglo en una cadena.</p>
   * 
   * @param args elementos
   * @param separator delimitador entre elementos
   * @param func función ha aplicar para cada elemento
   * @return String
   */
  public static String join(Object[] args, String separator, Func<Object, String> func) {
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < args.length; i++) {
      if (i > 0) sb.append(separator);
      sb.append(func.call(args[i]));
    }
    return sb.toString();
  }
  
  public static String join(Object[] args, String separator) {
    return join(args, separator, Func.OBJ_TO_STR);
  }
  
  public static String join(Object[] args, Func<Object, String> func) {
    return join(args, ", ", func);
  }
  
  public static String join(Object[] args) {
    return join(args, ", ");
  }
  
  public static <V> List<V> fill(List<V> list, V value, int start, int end) {
    if (isNull(list)) return null;
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
    if (isNull(array)) return null;
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
   
  public static <I, R> List<R> map(List<I> list, Func<I, R> func) {
    if (isNull(list)) return null;
    final List<R> result = new ArrayList<R>(list.size());
    for (int i = 0; i < list.size(); i++) {
      I object = list.get(i);
      result.add(func.call(object));
    }
    return result;
  }
  
  public static <I, R> R[] map(I[] array, Class<R> componentType, Func<I, R> func) {
    if (isNull(array)) return null;
    final List<R> result = new ArrayList<R>(array.length);
    for (int i = 0; i < array.length; i++) {
      I object = array[i];
      result.add(func.call(object));
    }
    R[] a = (R[]) Array.newInstance(componentType, 0);
    return result.toArray(a);
  }
  
  public static <V> List<V> filter(List<V> list, Func<V, Boolean> func) {
    if (isNull(list)) return null;
    final List<V> result = new ArrayList<V>(list.size());
    for (int i = 0; i < list.size(); i++) {
      V object = list.get(i);
      if (func.call(object)) {
        result.add(object);
      }
    }
    return result;
  }
  
  public static <V> V[] filter(V[] array, Func<V, Boolean> func) {
    if (isNull(array)) return null;
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
  
  public static <V> boolean every(List<V> list, Func<V, Boolean> fun) {
    if (isNull(list)) return false;
    for (int i = 0; i < list.size(); i++) {
      V object = list.get(i);
      if (!fun.call(object)) {
        return false;
      }
    }
    return true;
  }
  
  public static <V> boolean every(V[] array, Func<V, Boolean> func) {
    if (isNull(array)) return false;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (!func.call(object)) {
        return false;
      }
    }
    return true;
  }
  
  public static <V> boolean some(List<V> list, Func<V, Boolean> fun) {
    if (isNull(list)) return false;
    for (int i = 0; i < list.size(); i++) {
      V object = list.get(i);
      if (fun.call(object)) {
        return true;
      }
    }
    return false;
  }
  
  public static <V> boolean some(V[] array, Func<V, Boolean> func) {
    if (isNull(array)) return false;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        return true;
      }
    }
    return false;
  }
  
  public static <V> V find(List<V> list, Func<V, Boolean> func) {
    if (isNull(list)) return null;
    for (int i = 0; i < list.size(); i++) {
      V object = list.get(i);
      if (func.call(object)) {
        return object;
      }
    }
    return null;
  }
  
  public static <V> V find(V[] array, Func<V, Boolean> func) {
    if (isNull(array)) return null;
    for (int i = 0; i < array.length; i++) {
      V object = array[i];
      if (func.call(object)) {
        return object;
      }
    }
    return null;
  }
}
