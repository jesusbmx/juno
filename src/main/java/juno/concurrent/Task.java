
package juno.concurrent;

public interface Task<T> {
  /**
   * Executa la tarea de manera asíncrona y notifica su respuesta al callback
   * 
   * @param callback devolución de llamada
   */
  void async(Callback<T> callback);
  
  /**
   * Executa la tarea de manera asíncrona y notifica su respuesta al callback
   * 
   * @param onResponse
   * @param onError
   */
  void async(OnResponse<T> onResponse, OnError onError);
  
  /**
   * Executa la tarea de manera síncrona (bloquea hasta devolver el resultado).
   * 
   * @return resultado obtenido
   * @throws Exception 
   */
  T sync() throws Exception;
  
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
  
  boolean isAlive();
}
