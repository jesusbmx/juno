package juno.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public abstract class AbstractTask<T>
        implements Task<T>, Callback<T>, Callable<T> {

    TaskDispatcher dispatcher;
    Callback<T> callback;
    Future future;
    volatile boolean isAlive;

    public AbstractTask() {
        this(TaskDispatcher.getInstance());
    }

    public AbstractTask(TaskDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public TaskDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(TaskDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public boolean isCancelled() {
        return (future != null) ? future.isCancelled() : false;
    }

    @Override
    public boolean isDone() {
        return (future != null) ? future.isDone() : false;
    }

    @Override
    public boolean isAlive() {
        return isAlive && !isCancelled();
    }

    @Override
    public void async(Callback<T> callback) {
        this.callback = callback;
        execute();
    }

    @Override
    public void async(final OnResponse<T> onResponse, final OnError onError) {
        this.async(new CallbackAdapter<T>(onResponse, onError));
    }

    public void execute() {
        isAlive = true; // Marca la tarea como "viva" al inicio
        future = this.dispatcher.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final T result = call();
                    isAlive = false; // Se marca como no "viva" al finalizar la ejecución
                    deliveryResponse(result);

                } catch (final Exception error) {
                    isAlive = false; // Se marca como no "viva" al finalizar la ejecución
                    deliveryError(error);
                } 
            }
        });
    }
    
    @Override
    public synchronized T sync() throws Exception {
        return call();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        if (future != null) {
            boolean cancelled = future.cancel(mayInterruptIfRunning);
            if (cancelled) {
                isAlive = false;
            }
            return cancelled;
        } else {
            return false;
        }
    }

    @Override
    public void onResponse(T result) throws Exception {
        if (callback != null) {
            callback.onResponse(result);
        }
    }

    @Override
    public void onFailure(Exception e) {
        if (callback != null) {
            callback.onFailure(e);
        }
    }

    /**
     * Libera comandos al hilo de la UI.
     *
     * @param run
     */
    public void delivery(Runnable run) {
        dispatcher.delivery(run);
    }
    
    /**
     * Libera el resutado al hilo de la UI.
     *
     * @param result
     */
    private void deliveryResponse(final T result) {
        delivery(new Runnable() {
            @Override
            public void run() {
                try {
                    onResponse(result);
                } catch (Exception e) {
                    onFailure(e);
                }
            }
        });
    }
    
    /**
     * Libera el error al hilo de la UI.
     *
     * @param error
     */
    private void deliveryError(final Exception error) {
        delivery(new Runnable() {
            @Override
            public void run() {
                onFailure(error);
            }
        });
    }
}
