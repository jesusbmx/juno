package juno.util;

import java.util.regex.Pattern;

public final class Util {

  /** Representación compilada de una expresión regular para números.  */
  private static final Pattern NUMBER = Pattern.compile("-?\\d+.\\d+");
  
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
    if (Strings.isEmpty(source)) return false;
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
