
package juno.concurrent;

public class Promise<T> extends PromiseExecutor<T> {
    
    public Promise(final Task<T> task, Dispatcher dispatcher) {
        super(new Executor<T>() {
            @Override
            public void execute(Sender<T> sender) throws Exception {
                final T value = task.doInBackground();
                sender.resolve(value);
            }
        }, dispatcher);
    }
    
    public Promise(final Task<T> task) {
        this(task, Dispatcher.get());
    }
}
