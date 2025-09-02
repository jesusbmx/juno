
import java.util.concurrent.Callable;
import juno.concurrent.AsyncTask;
import juno.concurrent.AsyncSender;
import juno.concurrent.OnError;
import juno.concurrent.OnResponse;
import juno.concurrent.Sender;
import juno.concurrent.Task;


public class TestAsync {
    
    public Task<String> saludar1() {
        return new AsyncTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                //throw new Exception("error");
                return "Hola mundo"; 
            }
        });
    }
    
    public Task<String> saludar2() {
        return new AsyncSender<String>(new Sender.Executor<String>() {
            @Override
            public void execute(Sender<String> sender) throws Exception {
                //sender.reject(throw new Exception("error"));
                sender.resolve("Hola mundo");
            }
        });
    }
    
    public static void main(String[] args) {
        TestAsync test = new TestAsync();
        test.saludar1().async(new OnResponse<String>() {
            @Override
            public void onResponse(String result) throws Exception {
                System.out.println(result);
                System.exit(0);
            }
        }, new OnError() {
            @Override
            public void onFailure(Exception e) {
                System.err.println(e);
                System.exit(0);
            }
        });
    }
}
