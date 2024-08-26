
import java.util.List;
import juno.util.Convert;
import juno.util.IndexedFunction;
import juno.util.Lists;
import juno.util.Strings;

public class TestArrays {
    
    public static void main(String[] args) {
        // Arrays
        List<String> strList = Lists.of("1", "2", "3", "7", "9");
        List<Integer> intList = Lists.map(strList, new IndexedFunction<String, Integer>() {
            @Override
            public Integer call(String it, int index) {
                return Convert.toInt(it);
            }
        });

        if (Lists.hasIndex(strList, 2)) {
          System.out.printf("strArray[2] = '%s'\n", strList.get(2));
        }

        if (Lists.isEmpty(strList)) {
          System.out.println("array is empty");
        }

        System.out.println(Lists.getValueOrDefault(strList, 50, "defaultVal"));

        System.out.println(Strings.join(strList, ","));

        boolean some = Lists.some(strList, new IndexedFunction<String, Boolean>() {
            @Override
            public Boolean call(String t, int i) {
                return t.equals("7");
            }
        });
        System.out.println(some);

        boolean every = Lists.every(intList, new IndexedFunction<Integer, Boolean>() {
            @Override
            public Boolean call(Integer t, int index) {
                return t % 2 == 0;
            }
        });
        System.out.println(every);

        String find = Lists.find(strList, new IndexedFunction<String, Boolean>() {
            @Override
            public Boolean call(String t, int index) {
                return t.equals("9");
            }
        });
        System.out.println(find);

        List<Integer> filter = Lists.filter(intList, new IndexedFunction<Integer, Boolean>() {
            @Override
            public Boolean call(Integer t, int index) {
                return t > 5;
            }
        });
        System.out.println(Strings.join(filter, ","));

        List<String> fill = Lists.fill(strList, "z");
        System.out.println(Strings.join(fill, ","));
    }
}
