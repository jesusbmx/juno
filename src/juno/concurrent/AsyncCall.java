package juno.concurrent;

import java.util.concurrent.Future;

public abstract class AsyncCall<T> 
  implements Call<T>, Callback<T>, Task<T>, Runnable {
  
  final Dispatcher dispatcher;
  Callback<T> callback;
  Future future;
  volatile boolean isCancel;
  volatile boolean isRunning = false;

  public AsyncCall() {
    this(Dispatcher.get());
  }
  
  public AsyncCall(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }
  
  @Override public boolean isCancelled() {
    return (future != null) ? future.isCancelled() : isCancel;
  }

  @Override public boolean isDone() {
    return (future != null) ? future.isDone() : false;
  }

  public boolean isRunning() {
    return isRunning;
  }
  
  @Override public void execute(Callback<T> callback) {
    this.callback = callback;
    execute();
  }

  @Override public void execute(final OnResponse<T> onResponse, final OnError onError) {
    this.execute(new Callback<T>() {
        @Override
        public void onResponse(T result) throws Exception {
          onResponse.onResponse(result);
        }
        @Override
        public void onFailure(Exception e) {
          onError.onFailure(e);
        }
    });
  }
  
  public void execute() {
    future = this.dispatcher.submit(this);
    isRunning = true;
  }
  
  public synchronized T await() throws Exception {
    return doInBackground();
  }
  
  @Override public void run() {
    try {
      T result = doInBackground();
      dispatcher.onResponse(this, result);
    } catch (Exception e) {
      dispatcher.onFailure(this, e);
    }
    isRunning = false;
  }
  
   @Override public boolean cancel(boolean mayInterruptIfRunning) {
    isRunning = false;
    if (future != null) { 
      return future.cancel(mayInterruptIfRunning);
    } else { 
      isCancel = true;
      return false;
    }
  }
  
  @Override public void onResponse(T result) throws Exception {
    if (callback != null) callback.onResponse(result);
  }

  @Override public void onFailure(Exception e) {
    if (callback != null) callback.onFailure(e);
  }

  
  public void delivery(Runnable run) {
    dispatcher.delivery(run);
  }
}