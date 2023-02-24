
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
        public void execute(Sender<String> sender) throws Exception {
            sender.resolve("Hola Mundo");
        }
      });
    }
    
    public static void main(String[] args) {
      TestPromise tp = new TestPromise();
      tp.saludar().then(new OnResponse<String>() {
          @Override
          public void onResponse(String result) throws Exception {
              System.out.println(result);
          }
      }, new OnError() {
          @Override
          public void onFailure(Exception e) {
              e.printStackTrace();
          }
      }).enqueue();
    }
}
