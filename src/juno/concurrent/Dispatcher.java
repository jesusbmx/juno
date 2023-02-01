package juno.concurrent;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import juno.Platform;
import juno.util.Types;

public final class Dispatcher implements ThreadFactory {
  private static Dispatcher instance;

  /** Livera las respuestas al hilo de la UI. */
  private Executor executorDelivery;
  
  /** Ejecuta las llamadas "Call". */
  private ExecutorService executorService;
  
  public Dispatcher(ExecutorService executorService) {
    this.executorService = executorService;
  }

  public Dispatcher() {
  }
  
  public synchronized static Dispatcher get() {
    if (instance == null) {
      instance = new Dispatcher();
    }
    return instance;
  }
  
  @Override public Thread newThread(Runnable runnable) {
    Thread result = new Thread(runnable, "juno Dispatcher");
    result.setPriority(Thread.MIN_PRIORITY);
    return result;
  }
  
  public synchronized ExecutorService executorService() {
    if (executorService == null) {
      int nThreads = 1; //4
      executorService = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<Runnable>(), this);
    }
    return executorService;
  }
  public void setExecutorService(ExecutorService es) {
    executorService = es;
  }
    
  public static <V> AsyncCall<V> callUserfun(final Object obj, final String method, final Object... params) {
    Dispatcher dispatcher = Dispatcher.get(); 
    return new AsyncCall<V>(dispatcher) {
      @Override 
      public V doInBackground() throws Exception {
        Class<?>[] types = Types.getTypes(params);
        Method instanceMethod = obj.getClass()
                .getDeclaredMethod(method, types);
        
        return (V) instanceMethod.invoke(obj, params);
      }
      @Override
      public void onFailure(Exception e) {
        super.onFailure(e);
        e.printStackTrace();
      }
    };
  }
  
  public static <V> AsyncCall<V> callUserfun(final Class clazz, final String method, final Object... params) {
    Dispatcher dispatcher = Dispatcher.get(); 
    return new AsyncCall<V>(dispatcher) {
      @Override 
      public V doInBackground() throws Exception {
        Class<?>[] types = Types.getTypes(params);
        Method instanceMethod = clazz
                .getDeclaredMethod(method, types);
        
        return (V) instanceMethod.invoke(null, params);
      }
      @Override
      public void onFailure(Exception e) {
        super.onFailure(e);
        e.printStackTrace();
      }
    };
  }
  
  /** 
   * Crea una llamada. 
   * @param task tarea propuesta para la ejecución.
   */
  public static <V> AsyncCall<V> newCall(final Task<V> task) {
    Dispatcher dispatcher = Dispatcher.get(); 
    return new AsyncCall<V>(dispatcher) {
      @Override 
      public V doInBackground() throws Exception {
        return task != null ? task.doInBackground() : null;
      }
    };
  }
  
  /** Ejecuta la llamada en la cola de peticiones. */
  public synchronized boolean execute(AsyncCall<?> task) { 
    if (task.isCancelled() || task.isDone()) return false;
    // Propone una tarea Runnable para la ejecución y devuelve un Futuro.
    task.future = executorService().submit(task);
    return true;
  }
    
  public Executor executorDelivery() {
    if (executorDelivery == null) {
      executorDelivery = Platform.get();
    }
    return executorDelivery;
  }
  public void setExecutorDelivery(Executor executor) {
    executorDelivery = executor;
  }
  
  public void delivery(Runnable runnable) {
    executorDelivery().execute(runnable);
  }
  
  /**
   * Metodo que se encarga de liverar la respuesta obtenida, al hilo de la UI.
   */
  public <V> void onResponse(final Callback<V> callback, final V result) {
    delivery(new Runnable() {  
      @Override public void run() {
        try {
          callback.onResponse(result);
        } catch (Exception error) {
          callback.onFailure(error);
        }
      }
    });
  }

  /**
   * Metodo que se encarga de liverar el error obtenido, al hilo de la UI.
   */
  public void onFailure(final Callback<?> callback, final Exception error) {
    delivery(new Runnable() {
      @Override public void run() {
        callback.onFailure(error);
      }
    });
  }

}