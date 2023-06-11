
package juno.concurrent;

public interface Async<T> {
  /**
   * Executa la tarea de manera asíncrona y notifica su respuesta al callback
   * 
   * @param callback devolución de llamada
   */
  void then(Callback<T> callback);
  
  /**
   * Executa la tarea de manera asíncrona y notifica su respuesta al callback
   * 
   * @param onResponse
   * @param onError
   */
  void then(OnResponse<T> onResponse, OnError onError);
  
  /**
   * Executa la tarea de manera síncrona
   * 
   * @return resultado obtenido
   * @throws Exception 
   */
  T await() throws Exception;
  
  /**
   * Trata de cancelar la ejecución de esta tarea.
   *
   * @param mayInterruptIfRunning {@code true} valida se el hilo que se ejecuta 
   * debe ser interrumpido; de lo contrario, se permiten tareas en curso
   * hasta completar.
   * 
   * @return true si fue cancelado
   */
  boolean cancel(boolean mayInterruptIfRunning);
  
  /** 
   * @return Devuelve <tt>true</tt> si esta tarea estaba cancelada. 
   */
  boolean isCancelled();

  /** 
   * @return Devuelve <tt>true</tt> si esta tarea se completó. 
   */
  boolean isDone();
}
