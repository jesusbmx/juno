package juno.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public abstract class AsyncCall<T> 
  implements Call<T>, Callback<T>, Task<T>, Runnable {
  
  final Dispatcher dispatcher;
  Callback<T> callback;
  Future future;
  boolean cancel;

  public AsyncCall() {
    this(Dispatcher.get());
  }
  
  public AsyncCall(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }
  
  @Override public void execute(Callback<T> callback) {
    this.callback = callback;
    execute();
  }
  
  public void execute() {
    this.dispatcher.execute(this);
  }

  @Override public boolean cancel(boolean mayInterruptIfRunning) {
    if (future != null) { 
      return future.cancel(mayInterruptIfRunning);
    } else { 
      cancel = Boolean.TRUE;
      return Boolean.FALSE;
    }
  }

  @Override public boolean isCancelled() {
    return (future != null) ? future.isCancelled() : cancel;
  }

  @Override public boolean isDone() {
    return (future != null) ? future.isDone() : Boolean.FALSE;
  }
  
  
  @Override public void onResponse(T result) throws Exception {
    if (callback != null) callback.onResponse(result);
  }

  @Override public void onFailure(Exception e) {
    if (callback != null) callback.onFailure(e);
  }

   
  @Override public void run() {
    try {
      T result = doInBackground();
      dispatcher.onResponse(this, result);
    } catch (Exception e) {
      dispatcher.onFailure(this, e);
    }  
  }
  
  public void delivery(Runnable run) {
    dispatcher.executorDelivery().execute(run);
  }
}