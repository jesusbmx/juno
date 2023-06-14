package juno.concurrent;

public class EventMessage<T> implements Runnable {

    protected final EventManager manager;
    protected final EventListener<T> listener;
    protected final T value;

    public EventMessage(EventManager manager, EventListener<T> listener, T value) {
        this.manager = manager;
        this.listener = listener;
        this.value = value;
    }

    public EventManager getManager() {
        return manager;
    }
    
    public EventListener<T> getListener() {
        return listener;
    }

    public T getValue() {
        return value;
    }
    
    @Override
    public void run() {
        listener.onMessage(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
