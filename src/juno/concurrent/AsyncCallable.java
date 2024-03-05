package juno.concurrent;

import java.util.concurrent.Callable;

public class AsyncCallable<T> extends AbstractAsync<T> {

  public final Callable<T> task;
  
  public AsyncCallable(Callable<T> task, Dispatcher dispatcher) {
    super(dispatcher);
    this.task = task;
  }

  public AsyncCallable(Callable<T> task) {
    this(task, Dispatcher.get());
  }

  @Override
  public T call() throws Exception {
    return task != null ? task.call() : null;
  }
}