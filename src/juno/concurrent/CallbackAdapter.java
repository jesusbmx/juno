
package juno.concurrent;

public class CallbackAdapter<T> implements Callback<T> {

  @Override public void onResponse(T result) throws Exception {}

  @Override public void onFailure(Exception e) {}
}
