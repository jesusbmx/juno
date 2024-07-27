package juno.util;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import juno.tuple.Pair;

public class Maps {
    
   /**
     * Crea un mapa a partir de una lista de claves y valores alternados.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param keysAndValues alternados `"clave", "valor"`
     * @return LinkedHashMap con las claves y valores especificados
     */
    public static <K, V> LinkedHashMap<K, V> of(Object... keysAndValues) {
        if (keysAndValues == null) return null;
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Se esperaban claves y valores alternados");
        }
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(keysAndValues.length / 2);
        for (int i = 0; i < keysAndValues.length; i += 2) {
            map.put((K) keysAndValues[i], (V) keysAndValues[i + 1]);
        }
        return map;
    }

    /**
     * Crea un mapa a partir de una lista de pares.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param pairs pares de clave y valor
     * @return LinkedHashMap con los pares especificados
     */
    public static <K, V> LinkedHashMap<K, V> ofPairs(Pair<? extends K, ? extends V>... pairs) {
        if (pairs == null) return null;
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(pairs.length);
        for (Pair<? extends K, ? extends V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return map;
    }
    
    public static <K, V> Map<K, V> ofEntries(Map.Entry<? extends K, ? extends V>... entries) {
        if (entries == null) return null;
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(entries.length);
        for (Map.Entry<? extends K, ? extends V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * Verifica si un mapa está vacío o es nulo.
     *
     * @param map mapa a verificar
     * @return true si el mapa es nulo o está vacío, false de lo contrario
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Verifica si un mapa contiene una clave específica.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa a verificar
     * @param key clave a buscar
     * @return true si el mapa contiene la clave, false de lo contrario
     */
    public static <K, V> boolean containsKey(Map<K, V> map, K key) {
        return map != null && map.containsKey(key);
    }

    /**
     * Obtiene un valor de un mapa, o un valor por defecto si la clave no está presente.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa de donde obtener el valor
     * @param key clave a buscar
     * @param defaultValue valor por defecto si la clave no está presente
     * @return el valor asociado a la clave, o el valor por defecto
     */
    public static <K, V> V getValueOrDefault(Map<K, V> map, K key, V defaultValue) {
        return containsKey(map, key) ? map.get(key) : defaultValue;
    }

    /**
     * Obtiene un valor de un mapa, o null si la clave no está presente.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa de donde obtener el valor
     * @param key clave a buscar
     * @return el valor asociado a la clave, o null si la clave no está presente
     */
    public static <K, V> V getValue(Map<K, V> map, K key) {
        return getValueOrDefault(map, key, null);
    }

    /**
     * Elimina una clave de un mapa y devuelve el valor asociado.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa del que eliminar la clave
     * @param key clave a eliminar
     * @return el valor asociado a la clave eliminada, o null si no estaba presente
     */
    public static <K, V> V remove(Map<K, V> map, K key) {
        return map != null ? map.remove(key) : null;
    }

    /**
     * Reemplaza el valor asociado a una clave en un mapa.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa en el que reemplazar el valor
     * @param key clave cuyo valor será reemplazado
     * @param newValue nuevo valor a asociar con la clave
     * @return el valor anterior asociado a la clave, o null si no estaba presente
     */
    public static <K, V> V replace(Map<K, V> map, K key, V newValue) {
         return map != null ? map.replace(key, newValue) : null;
    }
    
    public static <Key1, Value1, Key2, Value2> Map<Key2, Value2> convert(
            Map<Key1, Value1> originalMap,
            Func<Map.Entry<Key1, Value1>, Map.Entry<Key2, Value2>> func) {
        
        if (originalMap == null) return null;
        LinkedHashMap<Key2, Value2> result = new LinkedHashMap<Key2, Value2>(originalMap.size());
        for (Map.Entry<Key1, Value1> it : originalMap.entrySet()) {
          final Map.Entry<Key2, Value2> entry = func.call(it);
          result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
  
    public static void main(String[] args) {
//        final Map<String, Object> map2 = Maps.ofPairs(
//           Pair.of("name", "Jesus"),
//           Pair.of("age", 29),
//            new Pair<String, Object>("color", "Green")
//        );

        final Map<String, Object> map = Maps.of(
            "name", "Jesus",
            "age", 29,
            "color", "Green"
        );
        
        System.out.println(Maps.getValueOrDefault(map, "name", "None"));
        
        final Map<String, String> newMap = Maps.convert(map, new Func<Map.Entry<String, Object>, Map.Entry<String, String>>() {
            @Override
            public Map.Entry<String, String> call(Map.Entry<String, Object> it) {
                return new AbstractMap.SimpleImmutableEntry<String, String>(it.getKey(), it.getValue().toString());
            }
        });
        
        System.out.println(newMap);
    }
}
