package juno.concurrent;

/**
 * Evento registrado par el watcher
 * @param <T>
 */
public abstract class EventListener<T> implements OnMessage<T> {

    private EventManager eventHandler;
    public final String name;

    public EventListener(String name) {
        this.name = name;
    }

    public EventManager getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventManager eventHandler) {
        this.eventHandler = eventHandler;
    }
    
    public void remove() {
        if (eventHandler != null) {
            eventHandler.removeListener(this);
        }
    }
}