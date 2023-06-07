
import juno.concurrent.AsyncCall;
import juno.concurrent.Call;
import juno.concurrent.OnError;
import juno.concurrent.OnResponse;
import juno.concurrent.Task;


public class TestAsync {
    
    public Call<String> saludar() {
        return new AsyncCall<String>(new Task<String>() {
            @Override
            public String doInBackground() throws Exception {
                throw new Exception("error");
                //return "Hola mundo"; 
            }
        });
    }
    
    public static void main(String[] args) {
        TestAsync test = new TestAsync();
        test.saludar().execute(new OnResponse<String>() {
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
