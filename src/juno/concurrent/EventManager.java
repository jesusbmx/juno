package juno.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Control de mensajes
 */
public class EventManager {
    
    private static final Map<String, EventManager> INSTANCES = 
            new HashMap<String, EventManager>();

    public final String name;
    
    private final List<EventListener> listeners = 
            new ArrayList<EventListener>();
    
    private Executor executorDelivery = 
            Dispatcher.get().executorDelivery();
    
    private EventManager(String name) {
        this.name = name;
    }
    
    /**
     * Obtiene el puente por su nombre.
     * 
     * @param name
     * @return 
     */
    public static EventManager get(String name) {
        EventManager eventHandler = INSTANCES.get(name);
        if (eventHandler == null) {
            eventHandler = new EventManager(name);
            INSTANCES.put(name, eventHandler);
        }
        return eventHandler;
    }
    
    public static EventManager get() {
        return EventManager.get("DefaultEventHandler");
    }   
    
    /**
     * Elimina todos los listeners de este puente
     */
    public void removeAllListener() {
        listeners.clear();
    }
    
    /**
     * Remueve el listener de este puente
     * @param listener
     * @return 
     */
    public boolean remove(EventListener<?> listener) {
        return listeners.remove(listener);
    }
    
    public boolean remove(String listenerName) {
        boolean r = false;
        for (int i = 0; i < listeners.size(); i++) {
            EventListener listener = listeners.get(i);
            if (listener.name.equals(listenerName)) {
                r = remove(listener);
            }
        }
        return r;
    }

    public List<EventListener> getListeners() {
        return listeners;
    }
    
    public EventListener getListener(int index) {
        return listeners.get(index);
    }
    
    public int getListenerCount() {
        return listeners.size();
    }
    
    /**
     * Agrega un listener a este puente
     * @param <V>
     * @param name nombre del listener
     * @param onMessage escucha cuando se envie un nuevo mensaje
     * @return 
     */
    public <V> EventListener<V> on(String name, OnMessage<V> onMessage) {
        final EventListener<V> listener = new EventListener<V>(this, name, onMessage);
        listeners.add(listener);
        return listener;
    }
    
    /**
     * Envia un mensaje a el puente libera la respuesta al listener
     * @param <V>
     * @param name nombre del listener
     * @param value valor que se mandara
     */
    public <V> void send(final String name, final V value) {
        for (int i = 0; i < listeners.size(); i++) {
            final EventListener listener = listeners.get(i);
            if (listener.name.equals(name)) {
                
                executorDelivery.execute(new Runnable() {
                    @Override
                    public void run() {
                        listener.onMessage(value);
                    }
                });
            }
        }
    }

    public Executor getExecutorDelivery() {
        return executorDelivery;
    }

    public void setExecutorDelivery(Executor executorDelivery) {
        this.executorDelivery = executorDelivery;
    }
    
//    public static void main(String[] args) {
//        EventManager receiver = EventManager.get("MyHandler");
//        receiver.on("log", (String value) -> {
//            System.out.println(value);
//        });
//        
//        EventManager sender = EventManager.get("MyHandler");
//        sender.send("log", "Hola mundo");
//    }
}
