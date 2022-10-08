package juno.concurrent;

public interface Executor<V> {

    void execute(Sender<V> sender) throws Exception;
}