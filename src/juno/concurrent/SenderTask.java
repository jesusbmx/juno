package juno.concurrent;

public interface SenderTask<V> {
    
    void execute(Sender<V> sender) throws Exception;    
}
