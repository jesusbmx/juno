package juno.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Promise<T> implements Runnable, Sender<T> {

    protected final Dispatcher dispatcher;
    protected final Executor<T> executor;

    protected T result;
    protected volatile boolean isResolve;
    protected Exception error;
    protected volatile boolean isReject;

    protected Callback<T> callback;

    protected Future future;
    protected boolean isCancel;
    protected boolean isRunning;

    public Promise(Executor<T> executor, Dispatcher dispatcher) {
        this.executor = executor;
        this.dispatcher = dispatcher;
    }
    
    public Promise(Executor<T> executor) {
        this(executor, Dispatcher.get());
    }
   
    public static <V> Promise<V> of(final Task<V> task) {
        return new Promise<V>(new Executor<V>() {
            @Override
            public void execute(Sender<V> sender) throws Exception {
                final V result = task.doInBackground();
                sender.resolve(result);
            }
        });
    }
    
    public Promise<T> then(final OnResponse<T> responseListener, final OnError errorListener) {
        return this.then(new Callback<T>() {
            @Override
            public void onResponse(T result) throws Exception {
                responseListener.onResponse(result);
            }
            @Override
            public void onFailure(Exception e) {
                errorListener.onFailure(e);
            }
        });
    }
    
    public Promise<T> then(Callback<T> callback) {
        this.callback = callback;
        this.enqueue();
        return this;
    }
    
    /////////////////////////////////////////////////////////////

    // async
    public void enqueue() {
        if (this.isCancelled() || this.isDone()) return;

        ExecutorService service = dispatcher.executorService();
        future = service.submit(this);
        isRunning = true;
        return;
    }

    // sync
    public synchronized T await() throws Exception {
        run();

        do {
        
            if (isReject)
                throw error;

            if (isResolve)
                return result;

        } while(true);
    }
    
    /////////////////////////////////////////////////////////////
    
    @Override public void run() {
        try {
            executor.execute(this);
        } catch (Exception e) {
            reject(e);
        }
    }
   
    @Override public void resolve(final T result) {
        this.result = result;
        this.isResolve = true;

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
        this.isReject = true;

        dispatcher.delivery(new Runnable() {
            @Override
            public void run() {
                onFailure(error);
            }
        });
    }

    public void onResponse(T response) throws Exception {
        if (callback != null)
            callback.onResponse(response);
    }

    public void onFailure(Exception error) {
        if (callback != null)
            callback.onFailure(error);
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