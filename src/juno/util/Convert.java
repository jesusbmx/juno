package juno.util;

import java.io.IOException;
import java.nio.charset.Charset;

public final class Convert {

  static final int INT = 0;
  static final byte BYTE = 0;
  static final long LONG = 0L;
  static final float FLOAT = 0F;
  static final double DOUBLE = 0D;
  static final String STRING = "";
  
  private Convert() {
  }
  
   /*
   |--------------------------------------------------------------------------
   | P A R S E   V A R S
   |--------------------------------------------------------------------------
   */
  
  /**
   * Analiza el objeto como una cadena y devuelve el resultado.
   *
   * @param o objeto a convertir.
   * @param nullDefault valor a retora si 'o' es nulo.
   * @return String
   */
  public static String toString(Object o, String nullDefault) {
    return Validate.isNull(o) ? nullDefault : o.toString();
  }
  public static String toString(Object o) {
    return toString(o, STRING);
  }
  
  public static String toString(byte[] bytes) {
    if (Validate.isNull(bytes)) return null;
    return new String(bytes);
  }
  
  public static String toString(byte[] bytes, Charset charset) throws IOException {
    if (Validate.isNull(bytes)) return null;
    return new String(bytes, charset);
  }
  
  public static byte toByte(CharSequence str, byte defaultValue) {
    if (Strings.isEmpty(str)) return defaultValue;
    try {
      return Byte.parseByte(str.toString());
    } catch (NumberFormatException ignore) {
      return defaultValue;
    }
  }
  
  public static byte toByte(CharSequence str) {
    return toByte(str, BYTE);
  }
  
  public static byte toByte(Object o, byte defaultVal) {
    if (Validate.isNull(o)) return defaultVal;
    if (o instanceof Byte) return ((Byte) o);
    return toByte(o.toString(), defaultVal);
  }
  
  public static byte toByte(Object str) {
    return toByte(str, BYTE);
  }
  
  /**
   * Analiza la cadena como un número int y devuelve el resultado.
   *
   * @param str cadena `"12"`
   * @param defaultValue valor por default
   * @return int `12`
   */
  public static int toInt(CharSequence str, int defaultValue) {
    if (Strings.isEmpty(str)) return defaultValue;
    try {
      return Integer.parseInt(str.toString());
    } catch (NumberFormatException ignore) {
      return defaultValue;
    }
  }

  public static int toInt(CharSequence str) {
    return toInt(str, INT);
  }
  
  public static int toInt(Object o, int defaultVal) {
    if (Validate.isNull(o)) return defaultVal;
    if (o instanceof Number) return ((Number) o).intValue();
    return toInt(o.toString(), defaultVal);
  }
  
  public static int toInt(Object str) {
    return toInt(str, INT);
  }
  
  /**
   * Analiza la cadena como un número long y devuelve el resultado.
   *
   * @param str cadena `"12"`
   * @param defaultValue valor por default
   * @return long `12L`
   */
  public static long toLong(CharSequence str, long defaultValue) {
    if (Strings.isEmpty(str)) return defaultValue;
    try {
      return Long.parseLong(str.toString());
    } catch (NumberFormatException ignore) {
      return defaultValue;
    }
  }

  public static long toLong(CharSequence str) {
    return toLong(str, LONG);
  }
  
  public static long toLong(Object o, long defaultVal) {
    if (Validate.isNull(o)) return defaultVal;
    if (o instanceof Number) return ((Number) o).longValue();
    return toLong(o.toString(), defaultVal);
  }
  
  public static long toLong(Object str) {
    return toLong(str, LONG);
  }

  /**
   * Analiza la cadena como un número float y devuelve el resultado.
   *
   * @param str cadena `"12.50"`
   * @param defaultVal valor por default
   * @return float `12.50f`
   */
  public static float toFloat(CharSequence str, float defaultVal) {
    if (Strings.isEmpty(str)) return defaultVal;
    try {
      return Float.parseFloat(str.toString());
    } catch (NumberFormatException ignore) {
      return defaultVal;
    }
  }

  public static float toFloat(CharSequence str) {
    return toFloat(str, FLOAT);
  }
  
  public static float toFloat(Object o, float defaultVal) {
    if (Validate.isNull(o)) return defaultVal;
    if (o instanceof Number) return ((Number) o).floatValue();
    return toFloat(o.toString(), defaultVal);
  }
  
  public static float toFloat(Object str) {
    return toFloat(str, FLOAT);
  }

  /**
   * Analiza la cadena como un número doble y devuelve el resultado.
   *
   * @param str cadena `"12.50"`
   * @param defaultVal valor por default
   * @return double `12.50d`
   */
  public static double toDouble(CharSequence str, double defaultVal) {
    if (Strings.isEmpty(str)) return defaultVal;
    try {
      return Double.parseDouble(str.toString());
    } catch (NumberFormatException ignore) {
      return defaultVal;
    }
  }

  public static double toDouble(CharSequence str) {
    return toDouble(str, DOUBLE);
  }
  
  public static double toDouble(Object o, double defaultVal) {
    if (Validate.isNull(o)) return defaultVal;
    if (o instanceof Number) return ((Number) o).doubleValue();
    return toDouble(o.toString(), defaultVal);
  }
  
  public static double toDouble(Object str) {
    return toDouble(str, DOUBLE);
  }

  /**
   * Analiza la cadena como un booleano y devuelve el resultado.
   *
   * @param cs cadena `"true"`
   * @param defaultVal valor por default
   * @return boolean `true`
   */
  public static boolean toBool(CharSequence cs, boolean defaultVal) {
    if (Strings.isEmpty(cs)) return defaultVal;
    String str = cs.toString().toLowerCase();
    if (str.equals("true"))  return true;
    if (str.equals("false")) return false;
    return defaultVal;
  }

  public static boolean toBool(CharSequence cs) {
    return toBool(cs, false);
  }
  
  /**
   * Decodifica una cadena
   * @param base64Encoded cadena codificada en base64
   * @return bytes decodificados
   */
  public static byte[] fromBase64String(String base64Encoded) {
    Base64.Decoder decoder = Base64.getDecoder();
    return decoder.decode(base64Encoded);
  }

  /**
   * Decodifica una cadena
   * @param base64Encoded cadena codificada en base64
   * @return cadena decodificada
   */
  public static String fromBase64(String base64Encoded) {
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] decode = decoder.decode(base64Encoded);
    return new String(decode);
  }

  /**
   * Codifica una cadena a base64
   * @param plainTextBytes bytes a codificar
   * @return cadena codificada
   */
  public static String toBase64String(byte[] plainTextBytes) {
    Base64.Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(plainTextBytes);
  }
  
  /**
   * Codifica una cadena a base64
   * @param plainText cadena a codificar
   * @return cadena codificada
   */
  public static String toBase64(String plainText) {
    Base64.Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(plainText.getBytes());
  }
}
