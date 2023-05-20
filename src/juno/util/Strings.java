package juno.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import static juno.util.Util.isNull;

public final class Strings {
  
  /** Representación compilada de una expresión regular para obtener las palabras. */
  private static final Pattern WORDS = Pattern.compile("\\W+");
  
  public static final String EMPTY = "";
  
  private Strings() {}
  
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
   * @param str expresión
   * @param altValue valor especificado
   * @return valor de la expresión
   */
  public static CharSequence ifEmpty(CharSequence str, CharSequence altValue) {
    return isEmpty(str) ? altValue : str;
  }
  
  public static String ifEmpty(String str, String altValue) {
    return isEmpty(str) ? altValue : str;
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
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Funcion para concatenear elementos
   *
   * @param elements
   * @return String
   */
  public static String concat(Object... elements) {
    StringBuilder sb = new StringBuilder();
    Collect.append(sb, elements);
    return sb.toString();
  }
  public static String concat(Collection elements) {
    StringBuilder sb = new StringBuilder();
    Collect.append(sb, elements);
    return sb.toString();
  }
  
  /**
   * Devuelve una copia de esta cadena que tiene su primera letra en mayúscula,
   * o la cadena original, si está vacía o ya comienza con una letra mayúscula.
   *
   * @param texto a convertir `hello world!`
   * @return cadena formateada `Hello world!`
   */
  public static String capitalize(String texto) {
    if (isEmpty(texto)) return EMPTY;
    for (int i = 0; i < texto.length(); i++) {
      char c = texto.charAt(i);
      if (Character.isUpperCase(c)) break;

      if (Character.isLetter(c)) {
        char[] chars = texto.toCharArray();
        chars[i] = Character.toUpperCase(c);
        return new String(chars);
      }
    }
    return texto;
  }
  
  /**
   * <p>Abreviaturas de cadena usando un marcador de reemplazo dado. Esto se convertirá
   * "Now is the time for all good men" en "...is the time for..." si "..." fue definido
   * como el marcador de reemplazo.</p>
   * 
   * <pre>
   * Util.abbreviate(null, null, *, *)                 = null
   * Util.abbreviate("abcdefghijklmno", null, *, *)    = "abcdefghijklmno"
   * Util.abbreviate("", "...", 0, 4)                  = ""
   * Util.abbreviate("abcdefghijklmno", "---", -1, 10) = "abcdefg---"
   * Util.abbreviate("abcdefghijklmno", ",", 0, 10)    = "abcdefghi,"
   * Util.abbreviate("abcdefghijklmno", ",", 1, 10)    = "abcdefghi,"
   * Util.abbreviate("abcdefghijklmno", ",", 2, 10)    = "abcdefghi,"
   * Util.abbreviate("abcdefghijklmno", "::", 4, 10)   = "::efghij::"
   * Util.abbreviate("abcdefghijklmno", "...", 6, 10)  = "...ghij..."
   * Util.abbreviate("abcdefghijklmno", "*", 9, 10)    = "*ghijklmno"
   * Util.abbreviate("abcdefghijklmno", "'", 10, 10)   = "'ghijklmno"
   * Util.abbreviate("abcdefghijklmno", "!", 12, 10)   = "!ghijklmno"
   * Util.abbreviate("abcdefghij", "abra", 0, 4)       = IllegalArgumentException
   * Util.abbreviate("abcdefghij", "...", 5, 6)        = IllegalArgumentException
   * </pre>
   * 
   * @param str  la cadena a comprobar, puede ser nula
   * @param abbrevMarker  la cadena utilizada como marcador de reemplazo
   * @param offset  borde izquierdo de la cadena de origen
   * @param maxWidth  longitud máxima de la cadena de resultados, debe ser al menos 4
   * @return String abreviado
   * 
   */
  public static String abbreviate(final String str, final String abbrevMarker, int offset, final int maxWidth) {
    if (isEmpty(str) || isEmpty(abbrevMarker)) {
      return str;
    }

    final int abbrevMarkerLength = abbrevMarker.length();
    final int minAbbrevWidth = abbrevMarkerLength + 1;
    final int minAbbrevWidthOffset = abbrevMarkerLength + abbrevMarkerLength + 1;

    if (maxWidth < minAbbrevWidth) {
      throw new IllegalArgumentException(String.format("Minimum abbreviation width is %d", minAbbrevWidth));
    }
    if (str.length() <= maxWidth) {
      return str;
    }
    if (offset > str.length()) {
      offset = str.length();
    }
    if (str.length() - offset < maxWidth - abbrevMarkerLength) {
      offset = str.length() - (maxWidth - abbrevMarkerLength);
    }
    if (offset <= abbrevMarkerLength+1) {
      return str.substring(0, maxWidth - abbrevMarkerLength) + abbrevMarker;
    }
    if (maxWidth < minAbbrevWidthOffset) {
      throw new IllegalArgumentException(String.format("Minimum abbreviation width with offset is %d", minAbbrevWidthOffset));
    }
    if (offset + maxWidth - abbrevMarkerLength < str.length()) {
      return abbrevMarker + abbreviate(str.substring(offset), abbrevMarker, maxWidth - abbrevMarkerLength);
    }
    return abbrevMarker + str.substring(str.length() - (maxWidth - abbrevMarkerLength));
  }
  
  public static String abbreviate(final String str, final int maxWidth) {
    final String defaultAbbrevMarker = "...";
    return abbreviate(str, defaultAbbrevMarker, 0, maxWidth);
  }
  
  public static String abbreviate(final String str, final int offset, final int maxWidth) {
    final String defaultAbbrevMarker = "...";
    return abbreviate(str, defaultAbbrevMarker, offset, maxWidth);
  }
  
  public static String abbreviate(final String str, final String abbrevMarker, final int maxWidth) {
    return abbreviate(str, abbrevMarker, 0, maxWidth);
  }
  
  public static List<String> splitNonRegex(String input, String delim, boolean returnDelims)
  {
    List<String> l = new ArrayList<String>();
    int delimlen = delim.length();
    int offset = 0;

    while (true)
    {
        int index = input.indexOf(delim, offset);
        if (index == -1)
        {
            l.add(input.substring(offset));
            return l;
        } else
        {
            int cut = returnDelims ? (index + delimlen) : index;
            l.add(input.substring(offset, cut));
            offset = (index + delim.length());
        }
    }
  }
  
  /**
   * Convertir una cadena de oraciones en una matriz de cadenas de palabras.
   * 
   * @param str
   * @return 
   */
  public static String[] words(String str) {
    if (isEmpty(str)) return new String[0];
    return WORDS.split(str);
  }

  /**
   * Obtiene un subcadena  
   * 
   * @param str "[Hola mundo]"
   * @param start "["
   * @param end "]"
   * @return "Hola mundo"
   */
  public static String subStr(String str, String start, String end) {
    int beginIndex = str.indexOf(start);
    if (beginIndex == -1) return null;
    int offset = beginIndex + start.length();
    int endIndex = str.indexOf(end, offset);
    if (endIndex == -1) endIndex = str.length();
    return str.substring(offset, endIndex);
  }
}
