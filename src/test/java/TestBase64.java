
import juno.util.Convert;


public class TestBase64 {
    
    public static void main(String[] args) {
        // Base64
        String encodeBase64 = Convert.toBase64("Hola mundo");
        System.out.println(encodeBase64);

        String decodeBase64 = Convert.fromBase64(encodeBase64);
        System.out.println(decodeBase64);
    }
}
