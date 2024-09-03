
import juno.lang.Reflection;

public class TestReflection {


  public static void main(String[] args) throws Exception {
    new TestReflection().init();
  }

  private void init() throws Exception {
    int result = Reflection.invokeInstanceMethod(this, "saludar", 10);
    System.out.println("result: "  + result);
  }
  
  public int saludar(int len) {
    for (int i = 0; i < len; i++) {
      System.out.println("TestReflection.saludar() : " + i);
    }
    return len;
  }
}
