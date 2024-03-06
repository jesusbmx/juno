package juno.concurrent;

import java.util.concurrent.Callable;

public class AsyncCallable<T> extends AbstractAsync<T> {

  public final Callable<T> callable;
  
  public AsyncCallable(Callable<T> callable, Dispatcher dispatcher) {
    super(dispatcher);
    this.callable = callable;
  }

  public AsyncCallable(Callable<T> task) {
    this(task, Dispatcher.getInstance());
  }

  @Override
  public T call() throws Exception {
    return callable != null ? callable.call() : null;
  }
}