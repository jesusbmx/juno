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

    protected OnResponse<T> responseListener;
    protected OnError errorListener;

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
   
    public Promise<T> then(OnResponse<T> responseListener, OnError errorListener) {
        this.responseListener = responseListener;
        this.errorListener = errorListener;
        enqueue();
        return this;
    }
    
    /////////////////////////////////////////////////////////////

    // async
    public Promise<T> enqueue() {
        if (this.isCancelled() || this.isDone()) return this;

        ExecutorService service = dispatcher.executorService();
        future = service.submit(this);
        isRunning = true;
        return this;
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