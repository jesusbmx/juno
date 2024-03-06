package juno.concurrent;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import juno.Platform;
import juno.util.Types;

public final class Dispatcher implements ThreadFactory {
  private static Dispatcher instance;
  
  public final String poolName;
  public final int threadLimit;
  private int nextId;

  /** Livera las respuestas al hilo de la UI. */
  private Executor executorDelivery;
  
  /** Ejecuta las llamadas "Call". */
  private ExecutorService executorService;
  
  public Dispatcher(String poolName, int threadLimit) {
    this.poolName = poolName;
    this.threadLimit = threadLimit;
  }
  
  public synchronized static Dispatcher getInstance() {
    if (instance == null) {
      instance = new Dispatcher("Juno-Dispatcher", 4);
    }
    return instance;
  }

  public synchronized static void setInstance(Dispatcher instance) {
    Dispatcher.instance = instance;
  }
  
  @Override public Thread newThread(Runnable runnable) {
    final Thread result = new Thread(runnable, poolName + "-" + nextId);
    nextId++;
    result.setPriority(Thread.MIN_PRIORITY);
    return result;
  }
  
  public synchronized ExecutorService getExecutorService() {
    if (executorService == null) {
      executorService = new ThreadPoolExecutor(threadLimit, threadLimit, 
              0L, TimeUnit.MILLISECONDS, 
              new LinkedBlockingQueue<Runnable>(), this);
    }
    return executorService;
  }
  
  public void setExecutorService(ExecutorService es) {
    executorService = es;
  }
  
  public Executor getExecutorDelivery() {
    if (executorDelivery == null) {
      executorDelivery = Platform.get();
    }
    return executorDelivery;
  }
  
  public void setExecutorDelivery(Executor executor) {
    executorDelivery = executor;
  }
  
  /** 
   * Ejecuta la llamada en la cola de peticiones.
   */
  public Future<?> submit(Runnable runnable) { 
    // Propone una tarea Runnable para la ejecución y devuelve un Futuro.
    return getExecutorService().submit(runnable);
  }
  
  /** 
   * Executes the given command at some time in the future
   */
  public void execute(Runnable runnable) { 
    // Propone una tarea Runnable para la ejecución y devuelve un Futuro.
    getExecutorService().execute(runnable);
  }
    
  public void delivery(Runnable runnable) {
    getExecutorDelivery().execute(runnable);
  }
  
  public <V> Async<V> newAsync(Callable<V> callable) {
    return new AsyncCallable<V>(callable, this);
  }
  
  public <V> Async<V> newAsync(Sender.Executor<V> executorSender) {
    return new AsyncSender<V>(executorSender, this);
  }

  public <V> Async<V> newUserFunc(
    final Object obj, 
    final String method, 
    final Object... params
  ) { 
    return newAsync(new Callable<V>() {
        @Override
        public V call() throws Exception {
            Class<?>[] types = Types.getTypes(params);
            final Method declaredMethod = obj.getClass()
                    .getDeclaredMethod(method, types);

            return (V) declaredMethod.invoke(obj, params);
        }
    });
  }
    
  /**
   * 
   * @param <V>
   * @param classOf
   * @param method
   * @param params
   * @return 
   */
  public <V> Async<V> newUserStaticFunc(
    final Class classOf, 
    final String method, 
    final Object... params
  ) {
    return newAsync(new Callable<V>() {
        @Override
        public V call() throws Exception {
            Class<?>[] types = Types.getTypes(params);
            final Method declaredMethod = classOf
                    .getDeclaredMethod(method, types);

            return (V) declaredMethod.invoke(null, params);
        }
    });
  }
  
  /**
   * 
   * @param <V>
   * @param obj
   * @param method
   * @param params
   * @return 
   */
  public static <V> Async<V> userFunc(
    final Object obj, 
    final String method, 
    final Object... params
  ) { 
    return Dispatcher.getInstance()
            .newUserFunc(obj, method, params);
  }
  
  /**
   * 
   * @param <V>
   * @param classOf
   * @param method
   * @param params
   * @return 
   */
  public static <V> Async<V> userStaticFunc(
    final Class classOf, 
    final String method, 
    final Object... params
  ) {
    return Dispatcher.getInstance()
            .newUserStaticFunc(classOf, method, params);
  }
  

}