package juno.concurrent;

public interface CallTask<T> {
  
  /**
   * Ejecuta la tarea y devuelve una respuesta.
   * @return respuesta obtenida
   * @throws Exception 
   */
  T doInBackground() throws Exception;
}