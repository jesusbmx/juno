
import juno.io.Paths;

public class TestPaths {
    
    public static void main(String[] args) {
        String path = "C:\\Users";
        
        System.out.println(Paths.join(path, "MyUser\\Desktop", "file.txt"));
                
        System.out.println(Paths.join(path, "MyUser", "Downloads", "file.txt"));
    }
}
