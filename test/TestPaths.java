
import juno.io.Paths;
import juno.util.Arrays;

public class TestPaths {
    
    public static void main(String[] args) {
        // Path
        String path = "/home";

        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
                path, "User/Desktop", "file.txt"
        )));

        // Path
        path = "/home/User/Documents/";

        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
                path, "file.txt"
        )));

        // Path
        path = "/home/User";

        // Join various path components
        System.out.println(Paths.join('/', Arrays.of(
                path, "Downloads", "file.txt"
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
