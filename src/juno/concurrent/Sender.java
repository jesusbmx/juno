package juno.concurrent;

public interface Sender<V> {

    void resolve(V result);

    void reject(Exception error);
    
    Promise<V> getPromise();
}