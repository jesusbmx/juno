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
    
    public static EventManager get(Class<?> className) {
        return get(className.getSimpleName());
    }
    
    public static EventManager get() {
        return EventManager.get("DefaultEventHandler");
    }   
    
    public List<EventListener> getListeners() {
        return listeners;
    }
    
    public List<EventListener> getListeners(String listenerName) {
        List<EventListener> r = new ArrayList<EventListener>();
        for (int i = 0; i < listeners.size(); i++) {
            EventListener listener = listeners.get(i);
            if (listener.name.equals(listenerName)) {
                r.add(listener);
            }
        }
        return r;
    }
    
    public EventListener getListener(int index) {
        return listeners.get(index);
    }
    
    public int getListenerCount() {
        return listeners.size();
    }
    
    /**
     * Elimina todos los listeners de este puente
     */
    public void removeAllListener() {
        synchronized (listeners) {
            listeners.clear();
        }
    }
    
    /**
     * Remueve el listener de este puente
     * @param listener
     * @return 
     */
    public boolean removeListener(EventListener<?> listener) {
        synchronized (listeners) {
            return listeners.remove(listener);
        }
    }
    
    public boolean removeAllListener(String listenerName) {
        synchronized (listeners) {
            List<EventListener> r = new ArrayList<EventListener>();
            for (int i = 0; i < listeners.size(); i++) {
                EventListener listener = listeners.get(i);
                if (listener.name.equals(listenerName)) {
                    r.add(listener);
                }
            }
            return listeners.removeAll(r);
        }
    }
    
    public <V> EventListener<V> add(EventListener<V> listener) {
        synchronized (listeners) {
            listener.setEventHandler(this);
            listeners.add(listener);
            return listener;
        }
    }
    
    public <V> Async<V> once(final String listenerName) {
        return new AsyncSender<V>(new SenderTask<V>() {
            
            @Override
            public void execute(final Sender<V> sender) throws Exception {
                add(new EventListener<V>(listenerName) {
                    
                    @Override
                    public void onMessage(V value) {
                        this.remove();
                        
                        try {
                            sender.resolve(value);
                        } catch(Exception e) {
                            sender.reject(e);
                        }
                    }
                });
            }
        });
    }
      
    /**
     * Agrega un listener a este puente
     * @param <V>
     * @param listenerName nombre del listener
     * @param onMessage escucha cuando se envie un nuevo mensaje
     * @return 
     */
    public <V> EventListener<V> on(String listenerName, final OnMessage<V> onMessage) {
        return add(new EventListener<V>(listenerName) {
            @Override
            public void onMessage(V value) {
                onMessage.onMessage(value);
            }
        });
    }
    
    /**
     * Envia un mensaje a el puente libera la respuesta al listener
     * @param <V>
     * @param listenerName nombre del listener
     * @param value valor que se mandara
     */
    public <V> void send(final String listenerName, final V value) {
        synchronized (listeners) {
            for (int i = 0; i < listeners.size(); i++) {
                final EventListener listener = listeners.get(i);
                if (listener.name.equals(listenerName)) {

                    executorDelivery.execute(new Runnable() {
                        @Override
                        public void run() {
                            listener.onMessage(value);
                        }
                    });
                }
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
