
import juno.concurrent.Timers;

public class TestTimers {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");

        final Timers.Timeout interval = Timers.setInterval(new Runnable() {
            @Override
            public void run() {
                System.out.println("tick");
            }
        }, 100);

        Timers.setTimeout(new Runnable() {
            @Override
            public void run() {
                Timers.clearInterval(interval);
                System.out.println("end");
            }
        }, 350);

        Thread.sleep(500);
    }
}
