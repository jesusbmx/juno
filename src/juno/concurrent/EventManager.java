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
    
    private Executor executorDelivery = Dispatcher.getInstance()
            .getExecutorDelivery();
    
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
    
    /**
     * Obtiene todos los listeners registrados
     * @return 
     */
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
    
    /**
     * Obtiene un listener por su posicion
     * @param index
     * @return 
     */
    public EventListener getListener(int index) {
        return listeners.get(index);
    }
    
    /**
     * Obtiene el numero de listeners registrados
     * @return 
     */
    public int getListenerCount() {
        return listeners.size();
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
    
    /**
     * Elimina todos los listeners de este puente.
     */
    public void removeAllListener() {
        synchronized (listeners) {
            listeners.clear();
        }
    }
    
    /**
     * Remueve los listener que contengan el mismo nombre.
     * @param listenerName
     * @return 
     */
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
    
    /**
     * Registra un nuevo listener.
     * @param <V>
     * @param listener
     * @return 
     */
    public <V> EventListener<V> add(EventListener<V> listener) {
        synchronized (listeners) {
            listener.setEventHandler(this);
            listeners.add(listener);
            return listener;
        }
    }
    
    /**
     * Obtiene un Async de un once
     * @param <V>
     * @param listenerName
     * @return 
     */
    public <V> Async<V> sync(final String listenerName) {
        return new AsyncSender<V>(new Sender.Executor<V>() {
            
            @Override
            public void execute(final Sender<V> sender) throws Exception {
                once(listenerName, new OnMessage<V>() {
                    
                    @Override
                    public void onMessage(EventMessage<V> evt) {
                        try {
                            sender.resolve(evt.getValue());
                        } catch (Exception e) {
                            sender.reject(e);
                        }
                    }
                });
            }
        });
    }
    
    /**
     * Agrega un listener. El La próxima vez que se active, este agente de 
     * escucha se elimina.
     * @param <V>
     * @param listenerName
     * @param onMessage
     * @return 
     */
    public <V> EventListener<V> once(String listenerName, final OnMessage<V> onMessage) {
        return add(new EventListener<V>(listenerName) {
            @Override
            public void onMessage(EventMessage<V> evt) {
                evt.getListener().remove();
                onMessage.onMessage(evt);
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
            public void onMessage(EventMessage<V> e) {
                onMessage.onMessage(e);
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
                    executorDelivery.execute(new EventMessage(this, listener, value));
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
    
//    public static void main(String[] args) throws Exception {
//        EventManager receiver = EventManager.get("MyHandler");
//        receiver.on("log", new OnMessage<String>() {
//            @Override
//            public void onMessage(EventMessage<String> e) {
//                System.out.println(e.getValue());
//            }
//        });
//        
//        Async<String> async = receiver.sync("log");
//        async.then(new Callback<String>() {
//            @Override
//            public void onResponse(String result) throws Exception {
//                System.out.println(result);
//            }
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//            }
//        });
//        
//        Thread.sleep(1000);
//        
//        EventManager sender = EventManager.get("MyHandler");
//        sender.send("log", "Hola mundo");
//    }
}
