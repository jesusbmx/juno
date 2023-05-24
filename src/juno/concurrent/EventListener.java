package juno.concurrent;

/**
 * Evento registrado par el watcher
 * @param <T>
 */
public class EventListener<T> implements OnMessage<T> {

    public final EventManager eventHandler;
    public final String name;
    public final OnMessage<T> onMessage;

    public EventListener(EventManager eventHandler, String name, OnMessage<T> onMessage) {
        this.eventHandler = eventHandler;
        this.name = name;
        this.onMessage = onMessage;
    }

    public void remove() {
        eventHandler.remove(this);
    }

    @Override
    public void onMessage(T value) {
        onMessage.onMessage(value);
    }
    

}