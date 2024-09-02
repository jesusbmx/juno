package juno.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Locale;
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
    public static <K, V> LinkedHashMap<K, V> fromPairs(Pair<? extends K, ? extends V>... pairs) {
        if (pairs == null) return null;
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(pairs.length);
        for (Pair<? extends K, ? extends V> pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return map;
    }
    
    public static <K, V> Map<K, V> fromEntries(Map.Entry<? extends K, ? extends V>... entries) {
        if (entries == null) return null;
        LinkedHashMap<K, V> map = new LinkedHashMap<K, V>(entries.length);
        for (Map.Entry<? extends K, ? extends V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
    
    public static Map<String, Object> fromObjectMethods(Object bean) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        
        Class<?> type = bean.getClass();
        boolean includeSuperClass = type.getClassLoader() != null;
        Method[] methods = includeSuperClass ? type.getMethods() : type.getDeclaredMethods();
        for (final Method method : methods) {
            final int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers)
                    && !Modifier.isStatic(modifiers)
                    && method.getParameterTypes().length == 0
                    && !method.isBridge()
                    && method.getReturnType() != Void.TYPE
                    && isValidMethodName(method.getName())) {
                final String key = getKeyNameFromMethod(method);
                if (key != null && !key.isEmpty()) {
                    try {
                        final Object result = method.invoke(bean);
                        if (result != null) {
                            map.put(key, result);
                        }
                    } catch (IllegalAccessException ignore) {
                    } catch (IllegalArgumentException ignore) {
                    } catch (InvocationTargetException ignore) {
                    }
                }
            }
        }
        
        return map;
    }
    
    public static Map<String, Object> fromObjectFields(Object bean) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        Class<?> type = bean.getClass();
        boolean includeSuperClass = type.getClassLoader() != null;
        Field[] fields = includeSuperClass ? type.getFields() : type.getDeclaredFields();

        for (final Field field : fields) {
            final int modifiers = field.getModifiers();

            if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
                try {
                    Object value = field.get(bean);
                    if (value != null) {
                        map.put(field.getName(), value);
                    }
                } catch (IllegalAccessException ignore) {
                    // Maneja la excepción si ocurre
                }
            }
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

    // Función existente para getString
    public static <K, V> String getString(Map<K, V> map, K key, String defaultValue) {
        if (map == null || key == null) {
            return defaultValue;
        }
        V value = map.get(key);
        return Convert.toString(value, defaultValue);
    }

    // Nueva función para getInt
    public static <K, V> int getInt(Map<K, V> map, K key, int defaultValue) {
        if (map == null || key == null) {
            return defaultValue;
        }
        V value = map.get(key);
        return Convert.toInt(value, defaultValue);
    }

    // Nueva función para getFloat
    public static <K, V> float getFloat(Map<K, V> map, K key, float defaultValue) {
        if (map == null || key == null) {
            return defaultValue;
        }
        V value = map.get(key);
        return Convert.toFloat(value, defaultValue);
    }

    // Nueva función para getDouble
    public static <K, V> double getDouble(Map<K, V> map, K key, double defaultValue) {
        if (map == null || key == null) {
            return defaultValue;
        }
        V value = map.get(key);
        return Convert.toDouble(value, defaultValue);
    }

    // Nueva función para getBoolean
    public static <K, V> boolean getBoolean(Map<K, V> map, K key, boolean defaultValue) {
        if (map == null || key == null) {
            return defaultValue;
        }
        V value = map.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            return Boolean.parseBoolean((String) value);
        }
        return defaultValue;
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
    
    public static <Key1, Value1, Key2, Value2> Map<Key2, Value2> convert(
            Map<Key1, Value1> originalMap,
            Func<Map.Entry<Key1, Value1>, Key2> keyMapper,
            Func<Map.Entry<Key1, Value1>, Value2> valueMapper) {
        
        if (originalMap == null) return null;
        LinkedHashMap<Key2, Value2> result = new LinkedHashMap<Key2, Value2>(originalMap.size());
        for (Map.Entry<Key1, Value1> it : originalMap.entrySet()) {
          result.put(keyMapper.call(it), valueMapper.call(it));
        }
        return result;
    }
    
     private static boolean isValidMethodName(String name) {
    return !"getClass".equals(name) && !"getDeclaringClass".equals(name);
  }

  private static String getKeyNameFromMethod(Method method) {
    String key;
    final String name = method.getName();
    if (name.startsWith("get") && name.length() > 3) {
        key = name.substring(3);
    } else if (name.startsWith("is") && name.length() > 2) {
        key = name.substring(2);
    } else {
        return null;
    }
    // if the first letter in the key is not uppercase, then skip.
    // This is to maintain backwards compatibility before PR406
    // (https://github.com/stleary/JSON-java/pull/406/)
    if (key.length() == 0 || Character.isLowerCase(key.charAt(0))) {
        return null;
    }
    if (key.length() == 1) {
        key = key.toLowerCase(Locale.ROOT);
    } else if (!Character.isUpperCase(key.charAt(1))) {
        key = key.substring(0, 1).toLowerCase(Locale.ROOT) + key.substring(1);
    }
    return key;
  }
}
