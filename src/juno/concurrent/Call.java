
package juno.concurrent;

public interface Call<T> {
  /**
   * Envíe de forma asíncrona la solicitud y notifica su respuesta o si se 
   * produjo un error al crear la solicitud o al procesar la respuesta.
   * @param callback devolución de llamada
   */
  void execute(Callback<T> callback);
  
  /**
   * Envíe de forma asíncrona la solicitud y notifica su respuesta o si se 
   * produjo un error al crear la solicitud o al procesar la respuesta.
   * @param onResponse
   * @param onError
   */
  void execute(OnResponse<T> onResponse, OnError onError);
  
  /**
   * Trata de cancelar la ejecución de esta tarea.
   *
   * @param mayInterruptIfRunning {@code true} valida se el hilo que se ejecuta 
   * debe ser interrumpido; de lo contrario, se permiten tareas en curso
   * hasta completar.
   */
  boolean cancel(boolean mayInterruptIfRunning);
  
  /** Devuelve <tt>true</tt> si esta tarea estaba cancelada. */
  boolean isCancelled();

  /** Devuelve <tt>true</tt> si esta tarea se completó. */
  boolean isDone();
}
