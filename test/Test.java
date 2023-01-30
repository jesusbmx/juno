
import java.util.Date;
import juno.util.Collect;
import juno.util.Convert;
import juno.text.Formats;
import juno.text.Texts;
import juno.util.Fun;
import juno.util.Util;

public class Test {
  
  public static void main(String[] args) {
    // Strings
    String txt = null;
    if (Util.isEmpty(txt)) {
      System.out.println("txt is empty");
    }
    
    txt = Util.ifNull(txt, "hola mundo");
    System.out.printf("txt = '%s'\n", txt);
    txt = Texts.abbreviate(txt, 7);
    System.out.printf("txt = '%s'\n", txt);
    
    String name = "jesus   ";
    name = Util.trim(name);
    name = Texts.capitalize(name);
    System.out.printf("name = '%s'\n", name);
    
    
    // Numbers
    String number = "-892768237.50";
    if (Util.isNumber(number)) {
      System.out.printf("'%f' is number\n", Convert.toDouble(number));
    } else {
      System.out.printf("'%s' not is number\n", number);
    }
   
    String str = "10.80";
    
    int i = Convert.toInt(str);
    System.out.printf("i = '%s'\n", i);
    
    float f = Convert.toFloat(str);
    System.out.printf("f = '%f'\n", f);
    
    
    // Base64
    String encodeBase64 = Convert.toBase64("Hola mundo");
    System.out.println(encodeBase64);
    
    String decodeBase64 = Convert.fromBase64(encodeBase64);
    System.out.println(decodeBase64);
    
    // Arrays
    String[] array = {"a", "b", "c"};
    if (Collect.hasIndex(array, 2)) {
      System.out.printf("array[2] = '%s'\n", array[2]);
    }
    
    if (Collect.isEmpty(array)) {
      System.out.println("array is empty");
    }
    
    System.out.println(Collect.join(array, ","));
    
    boolean some = Collect.some(array, new Fun<String, Boolean>() {
        @Override
        public Boolean apply(String t) {
            return t.equals("b");
        }
    });
    System.out.println(some);
    
    String find = Collect.find(array, new Fun<String, Boolean>() {
        @Override
        public Boolean apply(String t) {
            return t.equals("c");
        }
    });
    System.out.println(find);
    
    
    String[] fill = Collect.fill(array, "z");
    System.out.println(Collect.join(fill, ","));
    
    String[] filter = Collect.filter(array, new Fun<String, Boolean>() {
        @Override
        public Boolean apply(String t) {
            return t.contains("a") || t.contains("b");
        }
    });
    System.out.println(Collect.join(filter, ","));
    
    // Formats
    System.out.println(Formats.date());
    System.out.println(Formats.datetime());
    
    System.out.println(Formats.date("yyyy"));
    System.out.println(Formats.date("yyyy-MM", new Date()));
    System.out.println(Formats.datef("dd").format(new Date()));
  }
}
