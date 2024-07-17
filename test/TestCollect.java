
import java.util.List;
import juno.util.Arrays;
import juno.util.Collect;
import juno.util.Convert;
import juno.util.Func;
import juno.util.Strings;


public class TestCollect {
    
    public static void main(String[] args) {
        // Arrays
        List<String> strList = Arrays.listOf("1", "2", "3", "7", "9");
        List<Integer> intList = Arrays.map(strList, new Func<String, Integer>() {
            @Override
            public Integer call(String it) {
                return Convert.toInt(it);
            }
        });

        if (Arrays.hasIndex(strList, 2)) {
          System.out.printf("strArray[2] = '%s'\n", strList.get(2));
        }

        if (Collect.isEmpty(strList)) {
          System.out.println("array is empty");
        }

        System.out.println(Arrays.getValueOrDefault(strList, 50, "defaultVal"));

        System.out.println(Strings.join(strList, ","));

        boolean some = Arrays.some(strList, new Func<String, Boolean>() {
            @Override
            public Boolean call(String t) {
                return t.equals("7");
            }
        });
        System.out.println(some);

        boolean every = Arrays.every(intList, new Func<Integer, Boolean>() {
            @Override
            public Boolean call(Integer t) {
                return t % 2 == 0;
            }
        });
        System.out.println(every);

        String find = Arrays.find(strList, new Func<String, Boolean>() {
            @Override
            public Boolean call(String t) {
                return t.equals("9");
            }
        });
        System.out.println(find);

        List<Integer> filter = Arrays.filter(intList, new Func<Integer, Boolean>() {
            @Override
            public Boolean call(Integer t) {
                return t > 5;
            }
        });
        System.out.println(Strings.join(filter, ","));

        List<String> fill = Arrays.fill(strList, "z");
        System.out.println(Strings.join(fill, ","));
    }
}
