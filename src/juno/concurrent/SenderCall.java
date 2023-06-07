package juno.concurrent;

public class SenderCall<T> extends AbstractCall<T> implements Sender<T> {

    protected volatile T result;
    protected volatile Exception error;

    public final SenderExecutor<T> executor;
    public final long waitTime;

    public SenderCall(SenderExecutor<T> executor, Dispatcher dispatcher, long waitTime) {
        super(dispatcher);
        this.executor = executor;
        this.waitTime = waitTime;
    }
    
    public SenderCall(SenderExecutor<T> executor, Dispatcher dispatcher) {
        this(executor, dispatcher, 100);
    }
    
    public SenderCall(SenderExecutor<T> executor) {
        this(executor, Dispatcher.get());
    }

    @Override
    public T doInBackground() throws Exception {
        synchronized (this) {
            executor.execute(this);

            while (true) {
                wait(waitTime);

                if (error != null) {
                    throw error;
                }

                if (result != null) {
                    return result;
                }
            }
        }
    }

    @Override
    public void resolve(T result) throws Exception {
        this.result = result;
    }

    @Override
    public void reject(Exception error) {
        this.error = error;
    }
}