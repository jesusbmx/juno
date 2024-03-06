
import juno.concurrent.Async;
import juno.concurrent.Dispatcher;

public class TestCallUserFunc {


  public static void main(String[] args) throws Exception {
    new TestCallUserFunc().init();
  }

  private void init() throws Exception {
    Async<Integer> call = Dispatcher.userFunc(this, "saludar", 10);
    int result = call.await();
    System.out.println("result: "  + result);
  }
  
  public int saludar(int len) {
    for (int i = 0; i < len; i++) {
      System.out.println("TestAsyn.saludar() : " + i);
    }
    return len;
  }
}
