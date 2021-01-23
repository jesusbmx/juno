
import java.util.Date;
import juno.util.Collect;
import juno.util.Convert;
import juno.util.Formats;
import juno.util.Texts;
import juno.util.Util;

public class Test {
  
  public static void main(String[] args) {
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
    
    
    
    String[] array = {"a","b", "c"};
    if (Collect.arrayHasIndex(array, 2)) {
      System.out.printf("array[2] = '%s'\n", array[2]);
    }
    
    if (Collect.isEmpty(array)) {
      System.out.println("array is empty");
    }
    
    
    System.out.println(Collect.joinToStr(array, ","));
    
    
    
    System.out.println(Formats.date());
    System.out.println(Formats.datetime());
    
    System.out.println(Formats.date("yyyy"));
    System.out.println(Formats.date("yyyy-MM", new Date()));
  }
}
