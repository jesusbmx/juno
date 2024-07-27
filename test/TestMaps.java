
import java.util.AbstractMap;
import java.util.Map;
import juno.util.Func;
import juno.util.Maps;

public class TestMaps {
    
    public static void main(String[] args) {
//        final Map<String, Integer> map2 = Maps.ofPairs(
//           Pair.of("one", 1),
//           Pair.of("two", 2),
//            new Pair<String, Object>("three", 3)
//        );

        final Map<String, Integer> map = Maps.of(
                "one", 1, 
                "two", 2, 
                "three", 3
        );
        
        System.out.println(Maps.getValueOrDefault(map, "one", -1));
        
        final Map<Integer, String> newMap = Maps.convert(map, new Func<Map.Entry<String, Integer>, Map.Entry<Integer, String>>() {
            @Override
            public Map.Entry<Integer, String> call(Map.Entry<String, Integer> entry) {
                return new AbstractMap.SimpleImmutableEntry<Integer, String>(
                  entry.getValue(),
                    entry.getKey() + "-" + entry.getValue()
                );
            }
        });
        
        System.out.println(newMap);
    }
}
