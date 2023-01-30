
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import juno.util.Collect;
import juno.util.Fun;
import juno.text.Texts;
import juno.util.Util;


public class TestUtil {
  public static void main(String[] args) {
    List<Persona> list = new ArrayList<Persona>();
    list.add(new Persona(10, "Jesus", "B"));
    list.add(new Persona(12, "Juno", "X"));
    
    Fun<Persona, String> fun = new Fun<Persona, String>() {
      @Override public String apply(Persona t) {
        return t.toString();
      }
    };
    Collection<String> newList = Collect.transform(list, fun);
    
    System.out.println(Collect.join(newList, new Fun<Object, String>() {
      @Override public String apply(Object t) {
        return "\"" + t.toString() + "\"";
      }
    }));
    
    System.out.println(Texts.abbreviate("Hola mundo cruel sin esperanza", 22));
  }
  
  
  public static class Model {
    public long id;
  }
  
  public static class Persona extends Model {
    public String nombre;
    public String apellidos;

    public Persona(long id, String nombre, String apellidos) {
      this.id = id;
      this.nombre = nombre;
      this.apellidos = apellidos;
    }

    @Override public String toString() {
      return Util.concat(
              "{id=", id,
              ",nombre=", nombre,
              ",apellidos=", apellidos,
              "}");
    }
  }
  

}
