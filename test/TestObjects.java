
import juno.util.Objects;


public class TestObjects {
    
    public static void main(String[] args) {
        // Objs
        String a = null;
        String b = "b";
        System.out.println(Objects.eq(a, b));
        System.out.println(Objects.isNull(null));
        System.out.println(Objects.isNotNull(null));
    }
}
