
import java.io.File;
import java.io.IOException;
import juno.io.Files;

/**
 *
 * @author jesus
 */
public class TestIO {
    
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\jesus\\Documents\\archivo.txt";
        File f = new File(path);
        
        Files.write(f, "Hola mundo\n", /*append*/true, "UTF-8");
        
        String data = Files.readString(f);
        System.out.println(data);
        
        System.out.printf("parent: %s\n", Files.getParent(path));
        System.out.printf("name: %s\n", Files.getName(path));
        System.out.printf("extension: %s\n", Files.getExtension(path));
        System.out.printf("base name: %s\n", Files.getBaseName(path));
    }
}
