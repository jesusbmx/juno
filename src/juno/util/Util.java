package juno.util;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class Util {

  /** Representación compilada de una expresión regular para números.  */
  private static final Pattern NUMBER = Pattern.compile("-?\\d+.\\d+");
  
  private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP;
  static {
    WRAPPER_TYPE_MAP = new HashMap<Class<?>, Class<?>>(16);
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
  
  private Util() {}
          
  /**
   * <p>Valida si el objeto es nulo.</p>
   *
   * <pre>
   * Util.isNull(null)    = true
   * Util.isNull("")      = false
   * Util.isNull(" ")     = false
   * Util.isNull("bob")   = false
   * Util.isNull(1234)    = false
   * </pre>
   * 
   * @param o objeto a evaluar
   * @return boolean
   */
  public static boolean isNull(Object o) {
    return o == null;
  }
  
  /**
   * <p>Valida si el objeto no es nulo.</p>
   *
   * <pre>
   * Util.isNotNull(null)    = false
   * Util.isNotNull("")      = true
   * Util.isNotNull(" ")     = true
   * Util.isNotNull("bob")   = true
   * Util.isNotNull(1234)    = true
   * </pre>
   * 
   * @param o objeto a evaluar
   * @return boolean
   */
  public static boolean isNotNull(Object o) {
    return o != null;
  }

  /**
   * <p>Devuelva el valor especificado SI la expresión es NULL, de lo contrario,
   * devuelva la expresión.</p>
   * 
   * <pre>
   * Util.ifNull(null, "Hola mundo")  = "Hola mundo"
   * Util.ifNull("A", "B")            = "A"
   * </pre>
   *
   * @param obj expresión
   * @param altValue valor especificado
   * @return valor de la expresión
   */
  public static <V> V ifNull(V obj, V altValue) {
    return isNull(obj) ? altValue : obj;
  }

  /*
   |--------------------------------------------------------------------------
   | S T R I N G S
   |--------------------------------------------------------------------------
   */

  /**
   * <p>Valida si una cadena esta vacia o esta es nula.</p>
   *
   * <pre>
   * Util.isEmpty(null)      = true
   * Util.isEmpty("")        = true
   * Util.isEmpty(" ")       = false
   * Util.isEmpty("bob")     = false
   * Util.isEmpty("  bob  ") = false
   * </pre>
   * 
   * @param str cadena a evaluar
   * @return boolean
   */
  public static boolean isEmpty(CharSequence str) {
    return isNull(str) || str.length() == 0;
  }
  
  /**
   * <p>Comprueba si una cadena no está vacío y no es nulo.</p>
   *
   * <pre>
   * Util.isNotEmpty(null)             = false
   * Util.isNotEmpty("")               = false
   * Util.isNotEmpty("ab")             = true
   * </pre>
   *
   * @param str cadena a evaluar
   * @return boolean
   */
  public static boolean isNotEmpty(CharSequence str) {
    return !isEmpty(str);
  }
  
  /**
   * <p>Devuelva una cadena especifica SI la expresión es Vacia, de lo contrario,
   * devuelva la expresión.</p>
   * 
   * <pre>
   * Util.ifEmpty(null, "Hola mundo")  = "Hola mundo"
   * Util.ifEmpty("", "B")             = "B"
   * </pre>
   *
   * @param obj expresión
   * @param altValue valor especificado
   * @return valor de la expresión
   */
  public static CharSequence ifEmpty(CharSequence obj, CharSequence altValue) {
    return isEmpty(obj) ? altValue : obj;
  }
  
  public static String ifEmpty(String obj, String altValue) {
    return isEmpty(obj) ? altValue : obj;
  }

  /**
   * <p>Limpia los espacios en blanco de una cadena.</p>
   *
   * <pre>
   * Util.trim(null)          = ""
   * Util.trim("")            = ""
   * Util.trim("     ")       = ""
   * Util.trim("abc")         = "abc"
   * Util.trim("    abc    ") = "abc"
   * </pre>
   * 
   * @param str cadena a limpiar
   * @return String
   */
  public static String trim(CharSequence str) {
    return isNull(str) ? Convert.STRING : str.toString().trim();
  }
  
  /**
   * <p>Comprueba si un CharSequence está vacío (""), nulo o solo espacios en blanco.</p>
   *
   * <p>El espacio en blanco se define mediante {@link Character#isWhitespace(char)}.</p>
   *
   * <pre>
   * Util.isBlank(null)      = true
   * Util.isBlank("")        = true
   * Util.isBlank(" ")       = true
   * Util.isBlank("bob")     = false
   * Util.isBlank("  bob  ") = false
   * </pre>
   *
   * @param cs  el CharSequence a comprobar, puede ser nulo
   * @return {@code true} si CharSequence es nulo, vacío o solo espacios en blanco
   */
  public static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return TRUE;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return FALSE;
      }
    }
    return TRUE;
  }

  /**
   * Funcion para concatenear elementos
   *
   * @param elements
   * @return String
   */
  public static String concat(Object... elements) {
    StringBuilder sb = new StringBuilder();
    Collect.fill(sb, elements);
    return sb.toString();
  }
  public static String concat(Collection elements) {
    StringBuilder sb = new StringBuilder();
    Collect.fill(sb, elements);
    return sb.toString();
  }

  public static boolean eq(Object o1, Object o2) {
    return o1 == null ? o2 == null : o1.equals(o2);
  }

  /**
   * Una alternativa para comprobar que el String solo posee digitos como
   * caracter siempre es recorre el String y comprobarlo asi.
   *
   * @param source
   * @return
   */
  public static boolean isNumber(CharSequence source) {
    if (isEmpty(source)) return FALSE;
    return NUMBER.matcher(source).matches();
  }

  /**
   * Redondea la cantidad segun los decimales.
   *
   * @param value valor a redondear `948.856099955012`
   * @param places numero de decimales `2`
   * @return double redondeado `948.86`
   */
  public static double roundAvoid(double value, int places) {
    double scale = Math.pow(10, places);
    return Math.round(value * scale) / scale;
  }

  
  public static void delay(long millis) {
    try {
      Thread.sleep(millis);
    } catch(Exception ignore) {
    }
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
  
//  /** Le da formato de tipo URL a una cadena.  */
//  public static String urlOf(String url) {
//    url = trim(url);
//    if (!url.contains("http://") && !url.contains("https://")) 
//      url = "http://" + url;
//    return url;
//  }
//
//  /** Abre el navegador web.  */
//  public static void openUrlException(String url) {
//    java.awt.Desktop enlace = java.awt.Desktop.getDesktop();
//    try {
//      enlace.browse(new java.net.URI(url));
//    } catch (Exception e) {
//      throw new RuntimeException(e.getMessage(), e);
//    }
//  }
//
//  public static void openUrl(String url) {
//    try {
//      openUrlException(url);
//    } catch (Exception e) {
//      System.err.println(e.getMessage());
//    }
//  }
}
