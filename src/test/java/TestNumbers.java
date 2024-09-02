
import juno.util.Convert;
import juno.util.Numbers;


public class TestNumbers {
    
    public static void main(String[] args) {
        // Numbers
        String number = "-892768237.50";
        if (Numbers.isNumber(number)) {
          System.out.printf("'%f' is number\n", Convert.toDouble(number));
        } else {
          System.out.printf("'%s' not is number\n", number);
        }

        String str = "10.80";

        int i = Convert.toInt(str);
        System.out.printf("i = '%s'\n", i);

        float f = Convert.toFloat(str);
        System.out.printf("f = '%f'\n", f);

        double round = Numbers.roundAvoid(948.856099955012, 2);
        System.out.println(round);
    }
}
