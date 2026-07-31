package juno.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * setTimeout/setInterval al estilo JavaScript.
 *
 * <pre>{@code
 * Timers.Timeout timeout = Timers.setTimeout(new Runnable() {
 *   public void run() {
 *     System.out.println("Hola mundo");
 *   }
 * }, 1000);
 *
 * Timers.clearTimeout(timeout);
 * }</pre>
 */
public final class Timers {

  private static final ScheduledExecutorService SCHEDULER = newScheduler();

  private Timers() {}

  private static ScheduledExecutorService newScheduler() {
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
      final AtomicInteger nextId = new AtomicInteger();
      @Override
      public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, "juno.concurrent.Timers-" + nextId.getAndIncrement());
        thread.setDaemon(true);
        thread.setPriority(Thread.MIN_PRIORITY);
        return thread;
      }
    });
    // Evita que los timeouts/intervalos cancelados se queden en la cola.
    executor.setRemoveOnCancelPolicy(true);
    return executor;
  }

  /**
   * Ejecuta {@code task} una sola vez luego de {@code delayMillis},
   * entregando la respuesta en el executor de entrega de {@link TaskDispatcher}.
   *
   * @param task tarea a ejecutar
   * @param delayMillis retraso en milisegundos
   * @return manejador para poder cancelar la tarea con {@link #clearTimeout(Timeout)}
   */
  public static Timeout setTimeout(Runnable task, long delayMillis) {
    return setTimeout(task, delayMillis, TaskDispatcher.getInstance().getExecutorDelivery());
  }

  /**
   * Igual que {@link #setTimeout(Runnable, long)} pero permite indicar en que
   * {@link Executor} se entrega la respuesta.
   */
  public static Timeout setTimeout(final Runnable task, long delayMillis, final Executor delivery) {
    ScheduledFuture<?> future = SCHEDULER.schedule(new Runnable() {
      @Override
      public void run() {
        delivery.execute(task);
      }
    }, delayMillis, TimeUnit.MILLISECONDS);
    return new Timeout(future);
  }

  /**
   * Cancela un timeout pendiente creado con {@link #setTimeout(Runnable, long)}.
   * Si ya se ejecuto o fue cancelado, no hace nada.
   */
  public static void clearTimeout(Timeout timeout) {
    if (timeout != null) {
      timeout.cancel();
    }
  }

  /**
   * Ejecuta {@code task} repetidamente cada {@code delayMillis},
   * entregando la respuesta en el executor de entrega de {@link TaskDispatcher}.
   *
   * @param task tarea a ejecutar
   * @param delayMillis retraso entre ejecuciones en milisegundos
   * @return manejador para poder detener la repeticion con {@link #clearInterval(Timeout)}
   */
  public static Timeout setInterval(Runnable task, long delayMillis) {
    return setInterval(task, delayMillis, TaskDispatcher.getInstance().getExecutorDelivery());
  }

  /**
   * Igual que {@link #setInterval(Runnable, long)} pero permite indicar en que
   * {@link Executor} se entrega la respuesta.
   */
  public static Timeout setInterval(final Runnable task, long delayMillis, final Executor delivery) {
    ScheduledFuture<?> future = SCHEDULER.scheduleWithFixedDelay(new Runnable() {
      @Override
      public void run() {
        delivery.execute(task);
      }
    }, delayMillis, delayMillis, TimeUnit.MILLISECONDS);
    return new Timeout(future);
  }

  /**
   * Detiene un intervalo creado con {@link #setInterval(Runnable, long)}.
   */
  public static void clearInterval(Timeout timeout) {
    if (timeout != null) {
      timeout.cancel();
    }
  }

  /**
   * Manejador devuelto por setTimeout/setInterval, equivalente al id que
   * retorna JavaScript.
   */
  public static final class Timeout {
    private final ScheduledFuture<?> future;

    private Timeout(ScheduledFuture<?> future) {
      this.future = future;
    }

    /** Cancela la tarea. @return true si logro cancelarla. */
    public boolean cancel() {
      return future.cancel(false);
    }

    public boolean isCancelled() {
      return future.isCancelled();
    }

    public boolean isDone() {
      return future.isDone();
    }
  }
}
