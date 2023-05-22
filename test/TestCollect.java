
import java.util.List;
import juno.util.Collect;
import juno.util.Convert;
import juno.util.Func;


public class TestCollect {
    
    public static void main(String[] args) {
        // Arrays
        List<String> strArray = Collect.listOf("1", "2", "3", "7", "9");
        List<Integer> intArray = Collect.map(strArray, new Func<String, Integer>() {
            @Override
            public Integer call(String it) {
                return Convert.toInt(it);
            }
        });

        if (Collect.hasIndex(strArray, 2)) {
          System.out.printf("strArray[2] = '%s'\n", strArray.get(2));
        }

        if (Collect.isEmpty(strArray)) {
          System.out.println("array is empty");
        }

        System.out.println(Collect.get(strArray, 50, "defaultVal"));

        System.out.println(Collect.join(strArray, ","));

        boolean some = Collect.some(strArray, new Func<String, Boolean>() {
            @Override
            public Boolean call(String t) {
                return t.equals("7");
            }
        });
        System.out.println(some);

        boolean every = Collect.every(intArray, new Func<Integer, Boolean>() {
            @Override
            public Boolean call(Integer t) {
                return t % 2 == 0;
            }
        });
        System.out.println(every);

        String find = Collect.find(strArray, new Func<String, Boolean>() {
            @Override
            public Boolean call(String t) {
                return t.equals("9");
            }
        });
        System.out.println(find);

        List<Integer> filter = Collect.filter(intArray, new Func<Integer, Boolean>() {
            @Override
            public Boolean call(Integer t) {
                return t > 5;
            }
        });
        System.out.println(Collect.join(filter, ","));

        List<String> fill = Collect.fill(strArray, "z");
        System.out.println(Collect.join(fill, ","));
    }
}
