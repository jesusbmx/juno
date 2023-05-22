
import juno.util.Objects;
import juno.util.Strings;

public class TestStrings {
    
    public static void main(String[] args) {
        // Strings
        String txt = null;
        if (Strings.isEmpty(txt)) {
          System.out.println("txt is empty");
        }

        txt = Objects.ifNull(txt, "hola mundo");
        System.out.printf("txt = '%s'\n", txt);
        txt = Strings.abbreviate(txt, 7);
        System.out.printf("txt = '%s'\n", txt);

        String name = "jesus   ";
        name = Strings.trim(name);
        name = Strings.capitalize(name);
        System.out.printf("name = '%s'\n", name);

        System.out.println(Strings.subStr("[Hola mundo]", "[", "]"));
    }
}
