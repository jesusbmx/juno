package juno.concurrent;

public class AsyncSender<T> extends AbstractAsync<T> implements Sender<T> {

    protected volatile T result;
    protected volatile Exception error;

    public final SenderTask<T> task;

    public AsyncSender(SenderTask<T> task, Dispatcher dispatcher) {
        super(dispatcher);
        this.task = task;
    }
    
    public AsyncSender(SenderTask<T> task) {
        this(task, Dispatcher.get());
    }

    @Override
    public T doInBackground() throws Exception {
        synchronized (this) {
            task.execute(this);

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