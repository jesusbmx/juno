package juno.concurrent;

public interface OnError {
 
  /**
   * Método de devolución de llamada que indica que se ha producido un error con
   * el error proporcionado código y mensaje opcional legible por el usuario.
   * @param e error causado
   */
  void onFailure(Exception e);
}
