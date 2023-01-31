
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
    String[] strArray = {"1", "2", "3", "7", "9"};
    Integer[] intArray = Collect.map(strArray, Integer.class, new Fun<String, Integer>() {
        @Override
        public Integer apply(String it) {
            return Integer.parseInt(it);
        }
    });
        
    if (Collect.hasIndex(strArray, 2)) {
      System.out.printf("strArray[2] = '%s'\n", strArray[2]);
    }
    
    if (Collect.isEmpty(strArray)) {
      System.out.println("array is empty");
    }
    
    System.out.println(Collect.join(strArray, ","));
    
    boolean some = Collect.some(strArray, new Fun<String, Boolean>() {
        @Override
        public Boolean apply(String t) {
            return t.equals("7");
        }
    });
    System.out.println(some);
    
    boolean every = Collect.every(intArray, new Fun<Integer, Boolean>() {
        @Override
        public Boolean apply(Integer t) {
            return t % 2 == 0;
        }
    });
    System.out.println(every);
    
    String find = Collect.find(strArray, new Fun<String, Boolean>() {
        @Override
        public Boolean apply(String t) {
            return t.equals("9");
        }
    });
    System.out.println(find);
    
    
    String[] fill = Collect.fill(strArray, "z");
    System.out.println(Collect.join(fill, ","));
    
    Integer[] filter = Collect.filter(intArray, new Fun<Integer, Boolean>() {
        @Override
        public Boolean apply(Integer t) {
            return t > 5;
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
