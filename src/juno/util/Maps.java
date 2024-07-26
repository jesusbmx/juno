package juno.util;

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
    public static <K, V> LinkedHashMap<K, V> createFromKeysAndValues(Object... keysAndValues) {
        if (Validate.isNull(keysAndValues)) return null;
        if (keysAndValues.length % 2 != 0) {
            throw new IllegalArgumentException("Se esperaban claves y valores alternados");
        }
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(keysAndValues.length / 2);
        putAll(map, keysAndValues);
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
    public static <K, V> LinkedHashMap<K, V> createFromPairs(Pair<K, V>... pairs) {
        if (Validate.isNull(pairs)) return null;
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(pairs.length);
        putAll(map, pairs);
        return map;
    }

    /**
     * Verifica si un mapa está vacío o es nulo.
     *
     * @param map mapa a verificar
     * @return true si el mapa es nulo o está vacío, false de lo contrario
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return Validate.isNull(map) || map.isEmpty();
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
        if (Validate.isNull(map)) return false;
        return map.containsKey(key);
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
     * Añade pares de claves y valores a un mapa.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa al que añadir los pares
     * @param keysAndValues pares alternados de clave y valor
     */
    public static <K, V> void putAll(Map<K, V> map, Object... keysAndValues) {
        if (Validate.isNull(keysAndValues)) return;
        for (int i = 0; i < keysAndValues.length; i += 2) {
            K key = (K) keysAndValues[i];
            V value = (V) keysAndValues[i + 1];
            map.put(key, value);
        }
    }

    /**
     * Añade pares de clave y valor a un mapa.
     *
     * @param <K> tipo de clave
     * @param <V> tipo de valor
     * @param map mapa al que añadir los pares
     * @param pairs pares de clave y valor
     */
    public static <K, V> void putAll(Map<K, V> map, Pair<K, V>... pairs) {
        if (Validate.isNull(pairs)) return;
        for (Pair<K, V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
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
        if (Validate.isNull(map)) return null;
        return map.remove(key);
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
        if (Validate.isNull(map)) return null;
        return map.replace(key, newValue);
    }
  
    public static void main(String[] args) {
        final Map<String, Object> map = Maps.createFromPairs(
            new Pair<String, Object>("name", "Jesus"),
            new Pair<String, Object>("age", 29),
            new Pair<String, Object>("color", "Green")
        );
        
        System.out.println(map);
    }
}