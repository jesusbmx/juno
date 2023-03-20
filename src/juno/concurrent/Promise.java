package juno.concurrent;

import java.util.concurrent.Future;

public class Promise<T> implements Runnable, Sender<T> {

    protected final Dispatcher dispatcher;
    protected final Executor<T> executor;

    protected volatile T result;
    protected volatile boolean isResolve;
    protected volatile Exception error;
    protected volatile boolean isReject;

    protected Callback<T> callback;

    protected Future future;
    protected volatile boolean isCancel;
    protected volatile boolean isRunning;

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
            public void execute(final Sender<V> sender) throws Exception {
                final V result = task.doInBackground();
                sender.resolve(result);
            }
        });
    }
    
    public Promise<T> then(final OnResponse<T> responseListener, final OnError errorListener) {
        return this.then(new Callback<T>() {
            @Override
            public void onResponse(final T result) throws Exception {
                if (responseListener != null) {
                    responseListener.onResponse(result);
                }
            }
            @Override
            public void onFailure(final Exception e) {
                if (errorListener != null) {
                    errorListener.onFailure(e);
                }
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
        future = dispatcher.submit(this);
        isRunning = true;
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
   
    @Override public void resolve(T result) {
        this.result = result;
        this.isResolve = true;
        this.dispatcher.onResponse(callback, this.result);
    }

    @Override public void reject(Exception error) {
        this.error = error;
        this.isReject = true;
        this.dispatcher.onFailure(callback, this.error);
    }

    @Override
    public Promise<T> getPromise() {
        return this;
    }
    
     public Executor<T> getExecutor() {
        return executor;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public Callback<T> getCallback() {
        return callback;
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