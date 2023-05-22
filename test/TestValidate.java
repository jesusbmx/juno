
import juno.util.Validate;


public class TestValidate {
    
    public static void main(String[] args) {
        // Objs
        String a = null;
        String b = "b";
        System.out.println(Validate.eq(a, b));
        System.out.println(Validate.isNull(null));
        System.out.println(Validate.isNotNull(null));
    }
}
