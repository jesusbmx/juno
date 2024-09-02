package juno.util;

public final class Validate {

  private Validate() {
  }
    
   /**
   * <p>Valida si el objeto es nulo.</p>
   *
   * <pre>
   * Objects.isNull(null)    = true
   * Objects.isNull("")      = false
   * Objects.isNull(" ")     = false
   * Objects.isNull("bob")   = false
   * Objects.isNull(1234)    = false
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
   * Objects.isNotNull(null)    = false
   * Objects.isNotNull("")      = true
   * Objects.isNotNull(" ")     = true
   * Objects.isNotNull("bob")   = true
   * Objects.isNotNull(1234)    = true
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
   * Objects.ifNull(null, "Hola mundo")  = "Hola mundo"
   * Objects.ifNull("A", "B")            = "A"
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
}
