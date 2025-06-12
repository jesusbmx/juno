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
   * Convierte un array de objetos a una Lista.
   * Usa java.util.Arrays.asList() internamente.
   */
  public static <T> List<T> asList(T[] array) {
    if (array == null) return null;
    return java.util.Arrays.asList(array);
  }
  
  /**
   * Convierte un array de int a List<Integer>
   */
  public static List<Integer> asList(int[] array) {
    if (array == null) return null;
    List<Integer> list = new ArrayList<Integer>(array.length);
    for (int value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de long a List<Long>
   */
  public static List<Long> asList(long[] array) {
    if (array == null) return null;
    List<Long> list = new ArrayList<Long>(array.length);
    for (long value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de double a List<Double>
   */
  public static List<Double> asList(double[] array) {
    if (array == null) return null;
    List<Double> list = new ArrayList<Double>(array.length);
    for (double value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de float a List<Float>
   */
  public static List<Float> asList(float[] array) {
    if (array == null) return null;
    List<Float> list = new ArrayList<Float>(array.length);
    for (float value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de boolean a List<Boolean>
   */
  public static List<Boolean> asList(boolean[] array) {
    if (array == null) return null;
    List<Boolean> list = new ArrayList<Boolean>(array.length);
    for (boolean value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de byte a List<Byte>
   */
  public static List<Byte> asList(byte[] array) {
    if (array == null) return null;
    List<Byte> list = new ArrayList<Byte>(array.length);
    for (byte value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de short a List<Short>
   */
  public static List<Short> asList(short[] array) {
    if (array == null) return null;
    List<Short> list = new ArrayList<Short>(array.length);
    for (short value : array) {
      list.add(value);
    }
    return list;
  }
  
  /**
   * Convierte un array de char a List<Character>
   */
  public static List<Character> asList(char[] array) {
    if (array == null) return null;
    List<Character> list = new ArrayList<Character>(array.length);
    for (char value : array) {
      list.add(value);
    }
    return list;
  }
  
  // ============= isEmpty methods =============
  
  public static <T> boolean isEmpty(T[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(int[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(long[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(double[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(float[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(boolean[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(byte[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(short[] array) {
    return array == null || array.length == 0;
  }
  
  public static boolean isEmpty(char[] array) {
    return array == null || array.length == 0;
  }
  
  // ============= hasIndex methods =============
  
  public static <T> boolean hasIndex(T[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(int[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(long[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(double[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(float[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(boolean[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(byte[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(short[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  public static boolean hasIndex(char[] array, int index) {
    return array != null && index >= 0 && index < array.length;
  }
  
  // ============= getValueOrDefault methods =============
  
  public static <T> T getValueOrDefault(T[] array, int index, T defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static int getValueOrDefault(int[] array, int index, int defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static long getValueOrDefault(long[] array, int index, long defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static double getValueOrDefault(double[] array, int index, double defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static float getValueOrDefault(float[] array, int index, float defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static boolean getValueOrDefault(boolean[] array, int index, boolean defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static byte getValueOrDefault(byte[] array, int index, byte defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static short getValueOrDefault(short[] array, int index, short defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  public static char getValueOrDefault(char[] array, int index, char defaultVal) {
   return hasIndex(array, index) ? array[index] : defaultVal;
  }
  
  // ============= getValue methods =============
  
  public static <T> T getValue(T[] array, int index) {
    return getValueOrDefault(array, index, null);
  }
  
  public static Integer getValue(int[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Long getValue(long[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Double getValue(double[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Float getValue(float[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Boolean getValue(boolean[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Byte getValue(byte[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Short getValue(short[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  public static Character getValue(char[] array, int index) {
    return hasIndex(array, index) ? array[index] : null;
  }
  
  // ============= getFirst methods =============
  
  public static <V> V getFirst(V[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Integer getFirst(int[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Long getFirst(long[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Double getFirst(double[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Float getFirst(float[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Boolean getFirst(boolean[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Byte getFirst(byte[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Short getFirst(short[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  public static Character getFirst(char[] array) {
    return array.length == 0 ? null : array[0];
  }
  
  // ============= getLast methods =============
  
  public static <V> V getLast(V[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Integer getLast(int[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Long getLast(long[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Double getLast(double[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Float getLast(float[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Boolean getLast(boolean[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Byte getLast(byte[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Short getLast(short[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  public static Character getLast(char[] array) {
    final int len = array.length;
    return array.length == 0 ? null : array[len - 1];
  }
  
  // ============= reverse methods =============
  
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
  
  public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    int tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final int[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    long tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final long[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final double[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    double tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final double[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final float[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    float tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final float[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final boolean[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    boolean tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final boolean[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final byte[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    byte tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final byte[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final short[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    short tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final short[] array) {
    reverse(array, 0, array.length);
  }
  
  public static void reverse(final char[] array, final int startIndexInclusive, final int endIndexExclusive) {
    if (array == null) {
      return;
    }
    int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
    int j = Math.min(array.length, endIndexExclusive) - 1;
    char tmp;
    while (j > i) {
      tmp = array[j];
      array[j] = array[i];
      array[i] = tmp;
      j--;
      i++;
    }
  }
  
  public static void reverse(final char[] array) {
    reverse(array, 0, array.length);
  }
  
  // ============= indexOf methods =============
  
  public static <V> int indexOf(V[] array, V value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] != null && array[i].equals(value)) {
            return i;
        } else if (array[i] == null && value == null) {
            return i;
        }
    }
    return -1; // No encontrado
  }
  
  public static int indexOf(int[] array, int value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(long[] array, long value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(double[] array, double value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(float[] array, float value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(boolean[] array, boolean value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(byte[] array, byte value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(short[] array, short value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int indexOf(char[] array, char value) {
    for (int i = 0; i < array.length; i++) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  // ============= lastIndexOf methods =============
  
  public static <V> int lastIndexOf(V[] array, V value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] != null && array[i].equals(value)) {
            return i;
        } else if (array[i] == null && value == null) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(int[] array, int value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(long[] array, long value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(double[] array, double value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(float[] array, float value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(boolean[] array, boolean value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(byte[] array, byte value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(short[] array, short value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  public static int lastIndexOf(char[] array, char value) {
    if (array == null) return -1;
    for (int i = array.length - 1; i >= 0; i--) {
        if (array[i] == value) {
            return i;
        }
    }
    return -1;
  }
  
  // ============= fill methods =============
  
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
  
  public static int[] fill(int[] array, int value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static int[] fill(int[] array, int value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static int[] fill(int[] array, int value) {
    return fill(array, value, 0, array.length);
  }
  
  public static long[] fill(long[] array, long value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static long[] fill(long[] array, long value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static long[] fill(long[] array, long value) {
    return fill(array, value, 0, array.length);
  }
  
  public static double[] fill(double[] array, double value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static double[] fill(double[] array, double value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static double[] fill(double[] array, double value) {
    return fill(array, value, 0, array.length);
  }
  
  public static float[] fill(float[] array, float value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static float[] fill(float[] array, float value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static float[] fill(float[] array, float value) {
    return fill(array, value, 0, array.length);
  }
  
  public static boolean[] fill(boolean[] array, boolean value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static boolean[] fill(boolean[] array, boolean value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static boolean[] fill(boolean[] array, boolean value) {
    return fill(array, value, 0, array.length);
  }
  
  public static byte[] fill(byte[] array, byte value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static byte[] fill(byte[] array, byte value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static byte[] fill(byte[] array, byte value) {
    return fill(array, value, 0, array.length);
  }
  
  public static short[] fill(short[] array, short value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static short[] fill(short[] array, short value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static short[] fill(short[] array, short value) {
    return fill(array, value, 0, array.length);
  }
  
  public static char[] fill(char[] array, char value, int start, int end) {
    if (array == null) return null;
    for (int i = start; i < end; i++) {
      array[i] = value;
    }
    return array;
  }
  
  public static char[] fill(char[] array, char value, int start) {
    return fill(array, value, start, array.length);
  }
  
  public static char[] fill(char[] array, char value) {
    return fill(array, value, 0, array.length);
  }
  
  // ============= Conversion methods =============
  
  public static String[] convertArrayToString(Object[] array, Func<Object, String> func) {
    return map(array, String.class, func);
  }
  
  public static String[] convertArrayToString(Object... array) {
    return map(array, String.class, Func.OBJ_TO_STR);
  }
  
  // ============= Original methods that only work with Object arrays =============
  // These methods use reflection and functional interfaces, so they only work with Object arrays
  
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
  
  public static <V> Iterable<IndexedValue<V>> withIndex(V[] array) {
      return new IndexingIterable<V>(new ArrayIterator<V>(array));
  }
  
   // ============= contains methods =============
  
  public static <V> boolean contains(V[] array, V value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(int[] array, int value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(long[] array, long value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(double[] array, double value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(float[] array, float value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(boolean[] array, boolean value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(byte[] array, byte value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(short[] array, short value) {
    return indexOf(array, value) != -1;
  }
  
  public static boolean contains(char[] array, char value) {
    return indexOf(array, value) != -1;
  }
  
  // ============= concat methods =============
  
  @SuppressWarnings("unchecked")
  public static <T> T[] concat(T[] array1, T[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    T[] result = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static int[] concat(int[] array1, int[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    int[] result = new int[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static long[] concat(long[] array1, long[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    long[] result = new long[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static double[] concat(double[] array1, double[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    double[] result = new double[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static float[] concat(float[] array1, float[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    float[] result = new float[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static boolean[] concat(boolean[] array1, boolean[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    boolean[] result = new boolean[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static byte[] concat(byte[] array1, byte[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    byte[] result = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static short[] concat(short[] array1, short[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    short[] result = new short[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  public static char[] concat(char[] array1, char[] array2) {
    if (array1 == null && array2 == null) return null;
    if (array1 == null) return java.util.Arrays.copyOf(array2, array2.length);
    if (array2 == null) return java.util.Arrays.copyOf(array1, array1.length);
    
    char[] result = new char[array1.length + array2.length];
    System.arraycopy(array1, 0, result, 0, array1.length);
    System.arraycopy(array2, 0, result, array1.length, array2.length);
    return result;
  }
  
  // ============= subarray methods =============
  
  public static <T> T[] subarray(T[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) {
      @SuppressWarnings("unchecked")
      T[] empty = (T[]) Array.newInstance(array.getClass().getComponentType(), 0);
      return empty;
    }
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static <T> T[] subarray(T[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static int[] subarray(int[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new int[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static int[] subarray(int[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static long[] subarray(long[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new long[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static long[] subarray(long[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static double[] subarray(double[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new double[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static double[] subarray(double[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static float[] subarray(float[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new float[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static float[] subarray(float[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static boolean[] subarray(boolean[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new boolean[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static boolean[] subarray(boolean[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static byte[] subarray(byte[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new byte[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static byte[] subarray(byte[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static short[] subarray(short[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new short[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static short[] subarray(short[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  public static char[] subarray(char[] array, int startInclusive, int endExclusive) {
    if (array == null) return null;
    if (startInclusive < 0) startInclusive = 0;
    if (endExclusive > array.length) endExclusive = array.length;
    if (startInclusive >= endExclusive) return new char[0];
    return java.util.Arrays.copyOfRange(array, startInclusive, endExclusive);
  }
  
  public static char[] subarray(char[] array, int startInclusive) {
    return subarray(array, startInclusive, array == null ? 0 : array.length);
  }
  
  // ============= toArray methods (from List to array) =============
  
  /**
   * Convierte List<Integer> a int[]
   */
  public static int[] toIntArray(List<Integer> list) {
    if (list == null) return null;
    int[] array = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Integer value = list.get(i);
      array[i] = value != null ? value : 0;
    }
    return array;
  }
  
  /**
   * Convierte List<Long> a long[]
   */
  public static long[] toLongArray(List<Long> list) {
    if (list == null) return null;
    long[] array = new long[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Long value = list.get(i);
      array[i] = value != null ? value : 0L;
    }
    return array;
  }
  
  /**
   * Convierte List<Double> a double[]
   */
  public static double[] toDoubleArray(List<Double> list) {
    if (list == null) return null;
    double[] array = new double[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Double value = list.get(i);
      array[i] = value != null ? value : 0.0;
    }
    return array;
  }
  
  /**
   * Convierte List<Float> a float[]
   */
  public static float[] toFloatArray(List<Float> list) {
    if (list == null) return null;
    float[] array = new float[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Float value = list.get(i);
      array[i] = value != null ? value : 0.0f;
    }
    return array;
  }
  
  /**
   * Convierte List<Boolean> a boolean[]
   */
  public static boolean[] toBooleanArray(List<Boolean> list) {
    if (list == null) return null;
    boolean[] array = new boolean[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Boolean value = list.get(i);
      array[i] = value != null ? value : false;
    }
    return array;
  }
  
  /**
   * Convierte List<Byte> a byte[]
   */
  public static byte[] toByteArray(List<Byte> list) {
    if (list == null) return null;
    byte[] array = new byte[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Byte value = list.get(i);
      array[i] = value != null ? value : (byte) 0;
    }
    return array;
  }
  
  /**
   * Convierte List<Short> a short[]
   */
  public static short[] toShortArray(List<Short> list) {
    if (list == null) return null;
    short[] array = new short[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Short value = list.get(i);
      array[i] = value != null ? value : (short) 0;
    }
    return array;
  }
  
  /**
   * Convierte List<Character> a char[]
   */
  public static char[] toCharArray(List<Character> list) {
    if (list == null) return null;
    char[] array = new char[list.size()];
    for (int i = 0; i < list.size(); i++) {
      Character value = list.get(i);
      array[i] = value != null ? value : '\0';
    }
    return array;
  }
  
  /**
   * Convierte List<T> a T[] - método genérico para objetos
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] toArray(List<T> list, Class<T> componentType) {
    if (list == null) return null;
    T[] array = (T[]) Array.newInstance(componentType, list.size());
    return list.toArray(array);
  }
}