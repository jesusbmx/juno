package juno.util;

import java.util.LinkedHashMap;
import java.util.Map;
import juno.tuple.Pair;

public class Maps {
    
  /**
   * Devuelve un mapa con los contenidos especificados, dado como una lista de
   * pares donde el primer componente es la clave y el segundo es el valor.
   *
   * @param <K> tipo de clave
   * @param <V> tipo de valor
   * @param namesAndValues contenidos `"nombre", "jesus"`
   * @return HashMap</V></K> 
   */
  public static <K, V> LinkedHashMap<K, V> fromKeysAndValues(Object... namesAndValues) {
    if (Validate.isNull(namesAndValues)) return null;
    if (namesAndValues.length % 2 != 0) {
      throw new IllegalArgumentException("Expected alternating header names and values");
    }
    LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(namesAndValues.length / 2);
    putAll(map, namesAndValues);
    return map;
  }
  
  public static <K, V> LinkedHashMap<K, V> fromPairs(Pair<K, V>... pairs) {
    if (Validate.isNull(pairs)) return null;
    LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(pairs.length);
    putAll(map, pairs);
    return map;
  }
    
  public static boolean isEmpty(Map it) {
    return Validate.isNull(it) || it.isEmpty();
  }
  
   public static <K, V> boolean hasKey(Map<K, V> map, K key) {
    if (Validate.isNull(map)) return false;
    return map.containsKey(key);
  }
  
    public static <K, V> V get(Map<K, V> map, K key, V defaultVal) {
    return hasKey(map, key) ? map.get(key) : defaultVal;
  }
  
  public static <K, V> V get(Map<K, V> map, K key) {
    return get(map, key, null);
  }
  
  
  public static <K, V> void putAll(Map<K, V> out, Object... namesAndValues) {
    if (Validate.isNull(namesAndValues)) return;
    for (int i = 0; i < namesAndValues.length; i += 2) {
      K name = (K) namesAndValues[i];
      V value = (V) namesAndValues[i + 1];
      out.put(name, value);
    }
  }
  
  public static <K, V> void putAll(Map<K, V> out, Pair<K, V>... pairs) {
    if (Validate.isNull(pairs)) return;
    for (int i = 0; i < pairs.length; i++) {
      final Pair<K, V> pair = pairs[i];
      out.put(pair.getFirst(), pair.getSecond());
    }
  }
  
//    public static void main(String[] args) {
//        final Map<String, Object> map = Maps.fromPairs(
//            new Pair<String, Object>("name", "Jesus"),
//            new Pair<String, Object>("age", 29),
//            new Pair<String, Object>("color", "Green")
//        );
//        
//        System.out.println(map);
//    }
}
