package juno.util;

import java.util.regex.Pattern;

public final class Numbers {
    
   /** Representación compilada de una expresión regular para números.  */
   private static final Pattern NUMBER = Pattern.compile("-?\\d+.\\d+");
  
   private Numbers() {
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
}
