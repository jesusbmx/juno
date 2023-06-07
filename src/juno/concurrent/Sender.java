package juno.concurrent;

public interface Sender<V> {

    void resolve(V result) throws Exception;

    void reject(Exception error);
}