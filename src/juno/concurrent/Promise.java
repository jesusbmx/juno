package juno.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Promise<T> implements Runnable, Executor<T>, Sender<T> {

    protected final Dispatcher dispatcher;
    protected final Executor<T> executor;

    T result;
    Exception error;

    protected OnResponse<T> responseListener;
    protected OnError errorListener;

    Future future;
    boolean isCancel;
    boolean isRunning;

    public Promise(Executor<T> executor, Dispatcher dispatcher) {
        this.executor = executor;
        this.dispatcher = dispatcher;
    }
    
    public Promise(Executor<T> executor) {
        this(executor, Dispatcher.get());
    }
    
    public Promise<T> then(OnResponse<T> responseListener) {
        this.responseListener = responseListener;
        return this;
    }

    public Promise<T> error(OnError errorListener) {
        this.errorListener = errorListener;
        return this;
    }

    // queue
    public void enqueue() {
        if (this.isCancelled() || this.isDone()) return;

        ExecutorService service = dispatcher.executorService();
        future = service.submit(this);
        isRunning = true;
    }

    public T await() throws Exception {
        run();

        if (error != null)
            throw error;

        if (result != null)
            return result;

        throw new Exception("No sender");
    }

    @Override public void execute(Sender<T> sender) throws Exception {
        executor.execute(this);
    }
    
    @Override public void run() {
        try {
            execute(this);
        } catch (Exception e) {
            reject(e);
        }
    }

    @Override public void resolve(final T result) {
        this.result = result;

        dispatcher.delivery(new Runnable() {
            @Override
            public void run() {
                try {
                    onResponse(result);
                } catch (Exception error) {
                    onFailure(error);
                }
            }
        });
    }

    @Override public void reject(final Exception error) {
        this.error = error;

        dispatcher.delivery(new Runnable() {
            @Override
            public void run() {
                onFailure(error);
            }
        });
    }

    public void onResponse(T response) throws Exception {
        if (responseListener != null)
            responseListener.onResponse(response);
    }

    public void onFailure(Exception error) {
        if (errorListener != null)
            errorListener.onFailure(error);
    }

    public boolean isCancelled() {
        return (future != null) ? future.isCancelled() : isCancel;
    }

    public boolean isDone() {
        return future != null && future.isDone();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        isRunning = false;
        if (future != null) {
            return future.cancel(mayInterruptIfRunning);
        } else {
            isCancel = true;
            return false;
        }
    }
}