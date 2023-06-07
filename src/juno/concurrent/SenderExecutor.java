package juno.concurrent;

public interface SenderExecutor<V> {
    
    void execute(Sender<V> sender) throws Exception;    
}
