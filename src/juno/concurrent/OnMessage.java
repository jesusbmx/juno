package juno.concurrent;

/**
 * Evento que se llamara al enviar el mensaje
 * @param <T>
 */
public interface OnMessage<T> {
    void onMessage(T value);
}