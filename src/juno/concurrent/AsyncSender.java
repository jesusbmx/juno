package juno.concurrent;

public class AsyncSender<T> extends AbstractAsync<T> implements Sender<T> {

    protected volatile T result;
    protected volatile Exception error;

    public final Sender.Executor<T> executor;

    public AsyncSender(Sender.Executor<T> executor, Dispatcher dispatcher) {
        super(dispatcher);
        this.executor = executor;
    }
    
    public AsyncSender(Sender.Executor<T> executor) {
        this(executor, Dispatcher.get());
    }

    @Override
    public T call() throws Exception {
        synchronized (this) {
            executor.execute(this);

            do {

                if (error != null) {
                    throw error;
                }

                if (result != null) {
                    return result;
                }
                
                // Si no se detecta una respuesta dormimos el hilo hasta que
                // se llame alguno de los metodos resolve o reject
                wait();
                
            } while (true);
        }
    }

    @Override
    public void resolve(T result) throws Exception {
        synchronized (this) {
            this.result = result;
            notifyAll(); // depierta el hilo si esta dormido
        }
    }

    @Override
    public void reject(Exception error) {
        synchronized (this) {
            this.error = error;
            notifyAll(); // depierta el hilo si esta dormido
        }
    }
}