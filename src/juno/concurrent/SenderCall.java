package juno.concurrent;

public class SenderCall<T> extends AbstractCall<T> implements Sender<T> {

    protected volatile T result;
    protected volatile Exception error;

    public final SenderExecutor<T> executor;

    public SenderCall(SenderExecutor<T> executor, Dispatcher dispatcher) {
        super(dispatcher);
        this.executor = executor;
    }
    
    public SenderCall(SenderExecutor<T> executor) {
        this(executor, Dispatcher.get());
    }

    @Override
    public T doInBackground() throws Exception {
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