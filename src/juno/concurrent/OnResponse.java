package juno.concurrent;

public interface OnResponse<T> {
    
  /**
   * Se llama cuando se recibe una respuesta.
   *
   * @param result respuesta obtenida
   * @throws java.lang.Exception error al recibir la respueta
   */
  void onResponse(T result) throws Exception;
}
