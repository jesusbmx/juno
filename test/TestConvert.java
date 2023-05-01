
import java.util.Arrays;
import java.util.Date;
import juno.text.Formats;
import juno.util.Convert;

/**
 *
 * @author Jesus
 */
public class TestConvert {

  public static void main(String[] args) {
    Object value;

    int to_i = 12;
    value = to_i;
    to_i = Convert.toInt(value);
    System.out.println(to_i);

    long to_l = 99999999;
    value = to_l;
    to_l = Convert.toLong(value);
    System.out.println(to_l);

    float to_f = 3.1416f;
    value = to_f;
    to_f = Convert.toFloat(value);
    System.out.println(to_f);

    double to_d = 3.1416d;
    value = to_d;
    to_d = Convert.toDouble(value);
    System.out.println(to_d);
    
    String to_b = "True";
    System.out.println(Convert.toBool(to_b));
    
    
    Date date = Convert.toDate("yyyy-MM-dd", "2023-04-30");
    System.out.println(Formats.datetime(date));
    
    // Define a byte array.
    byte[] bytes = { 2, 4, 6, 8, 10, 12, 14, 16, 18, 20 };
    System.out.printf("The byte array:\n");
    System.out.printf("   '%s'\n", Arrays.toString(bytes));
    
    // Convert the array to a base 64 string.
    String s = Convert.toBase64String(bytes);
    System.out.printf("The base 64 string:\n");
    System.out.printf("   '%s'\n", s);
    
    // Restore the byte array.
    byte[] newBytes = Convert.fromBase64String(s);
    System.out.printf("The restored byte array:\n");
    System.out.printf("   '%s'\n", Arrays.toString(newBytes));
    
    // The example displays the following output:
//     The byte array:
//        02-04-06-08-0A-0C-0E-10-12-14
//     
//     The base 64 string:
//        AgQGCAoMDhASFA==
//     
//     The restored byte array:
//        02-04-06-08-0A-0C-0E-10-12-14
  }
}
