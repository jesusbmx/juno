package juno.concurrent;

public interface Sender<V> {

    void resolve(V result) throws Exception;

    void reject(Exception error);
    
    public interface Executor<V> {
    
        void execute(Sender<V> sender) throws Exception;    
    }

}