package juno.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jesus
 */
public final class Types {

  private Types() {
  }
  
  private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
  static {
    WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>(18);
    WRAPPER_TYPE_MAP.put(Integer.class, int.class);
    WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
    WRAPPER_TYPE_MAP.put(Character.class, char.class);
    WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
    WRAPPER_TYPE_MAP.put(Double.class, double.class);
    WRAPPER_TYPE_MAP.put(Float.class, float.class);
    WRAPPER_TYPE_MAP.put(Long.class, long.class);
    WRAPPER_TYPE_MAP.put(Short.class, short.class);
    WRAPPER_TYPE_MAP.put(Void.class, void.class);
  }  
  
  public static Class<?> getPrimitiveType(Class clazz) {
    return WRAPPER_TYPE_MAP.get(clazz);
  }
  
  public static boolean isPrimitiveType(Class clazz) {
    return WRAPPER_TYPE_MAP.containsKey(clazz);
  }
  
  public static boolean isPrimitiveType(Object source) {
    return isPrimitiveType(source.getClass());
  }
  
  public static Class<?>[] getTypes(Object... params) {
    Class<?>[] types = new Class<?>[params.length];
    for (int i = 0; i < params.length; i++) {
      Class<?> type = params[i].getClass();
      Class<?> primitiveType = Types.getPrimitiveType(type);
      types[i] = primitiveType == null ? type : primitiveType;
    }
    return types;
  }
  
}
