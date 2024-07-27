
import juno.io.Paths;
import juno.util.Arrays;

public class TestPaths {
    
    public static void main(String[] args) {
        // Path
        String basePath = "/home";

        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
            basePath, "User/Desktop", "file.txt"
        )));

        // Path
        basePath = "/home/User/Documents/";

        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
            basePath, "file.txt"
        )));

        // Path
        basePath = "/home/User";

        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
            basePath, "Downloads", "file.txt"
        )));
        
        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
           "/home", "User2", "Downloads", "file.txt"
        )));
        
        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
           "/home", "User2", "Downloads"
        )));
        
    }
}
