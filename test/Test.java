
import juno.util.Callback;
import juno.util.Func;


public class Test {
    
    void execute(Callback<String> callback) {
        try {
            callback.call("Hola mundo");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Test().execute(new Callback<String>() {
            @Override
            public void call(String result) {
                System.out.println(result);
            }
        });
    }
}
