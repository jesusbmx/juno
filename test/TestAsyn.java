
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
    call = Dispatcher.callUserfun(this, "saludar");
    call.execute();
  }
  
  public void saludar() {
    for (int i = 0; i < 10; i++) {
      System.out.println("TestAsyn.saludar() : " + i);
    }
  }
}
