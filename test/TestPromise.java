
import juno.concurrent.Executor;
import juno.concurrent.OnError;
import juno.concurrent.OnResponse;
import juno.concurrent.Promise;
import juno.concurrent.Sender;

/**
 *
 * @author jesus
 */
public class TestPromise {
    
    Promise<String> saludar() {
      return new Promise<String>(new Executor<String>() {
        @Override
        public void execute(final Sender<String> sender) throws Exception {
            //sender.resolve("Hola Mundo");
            new Thread() {
                @Override
                public void run() {
                    int i = 0;
                    while (i < 3) {                        
                        i++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            
                        }
                    }
                    //sender.resolve("Hola mundo");
                    sender.reject(new Exception("error"));
                }
                
            }.start();
        }
      });
    }
    
    public void async() {
      saludar().then(new OnResponse<String>() {
          @Override
          public void onResponse(String result) throws Exception {
              System.out.println(result);
          }
      }, new OnError() {
          @Override
          public void onFailure(Exception e) {
              e.printStackTrace();
          }
      });
    }
    
    public void sync() {
      try {
          String result = saludar().await();
          System.out.println(result);
      } catch(Exception e) {
          e.printStackTrace();
      }
    }
    
    public static void main(String[] args) {
      TestPromise tp = new TestPromise();
      tp.sync();
    }
}
