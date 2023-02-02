
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
        Files.write(f, "Hola mundo\n", /*append*/true, "UTF-8");
        
        String data = Files.readString(f);
        System.out.println(data);
        
        System.out.printf("name: %s\n",  Files.getName(f));
        System.out.printf("extension: %s\n", Files.getExtension(f));
        System.out.printf("base name: %s\n", Files.getBaseName(f));
    }
}
