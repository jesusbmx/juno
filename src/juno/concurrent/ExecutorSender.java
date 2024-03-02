package juno.concurrent;

public interface ExecutorSender<V> {
    
    void execute(Sender<V> sender) throws Exception;    
}
