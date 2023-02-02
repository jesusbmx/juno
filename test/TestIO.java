
import java.io.File;
import java.io.IOException;
import juno.io.Files;

/**
 *
 * @author jesus
 */
public class TestIO {
    
    public static void main(String[] args) throws IOException {
        File f = new File("C:\\Users\\jesus\\Documents\\archivo.txt");
        Files.write(f, "Hola mundo\n", /*append*/true);
        
        String data = Files.readString(f);
        System.out.println(data);
        
        System.out.println(Files.ext(f));
        System.out.println(Files.basename(f));
    }
}
