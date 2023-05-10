
import juno.concurrent.AsyncCall;
import juno.concurrent.Dispatcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jesus
 */
public class TestAsyn {

  AsyncCall call;
  
  public static void main(String[] args) {
    new TestAsyn().init();
  }

  private void init() {
    if (call != null) {
      call.cancel(true);
    }
    call = Dispatcher.callUserfunc(this, "saludar", 10);
    call.execute();
  }
  
  public void saludar(int len) {
    for (int i = 0; i < len; i++) {
      System.out.println("TestAsyn.saludar() : " + i);
    }
  }
}
