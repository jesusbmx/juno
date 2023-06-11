
import juno.concurrent.AsyncTask;
import juno.concurrent.OnError;
import juno.concurrent.OnResponse;
import juno.concurrent.Task;
import juno.concurrent.Async;


public class TestAsync {
    
    public Async<String> saludar() {
        return new AsyncTask<String>(new Task<String>() {
            @Override
            public String doInBackground() throws Exception {
                //throw new Exception("error");
                return "Hola mundo"; 
            }
        });
    }
    
    public static void main(String[] args) {
        TestAsync test = new TestAsync();
        test.saludar().then(new OnResponse<String>() {
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
