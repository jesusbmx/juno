package juno.concurrent;

import java.util.concurrent.Callable;

public class AsyncTask<T> extends AbstractTask<T> {

  public final Callable<T> callable;
  
  public AsyncTask(Callable<T> callable, TaskDispatcher dispatcher) {
    super(dispatcher);
    this.callable = callable;
  }

  public AsyncTask(Callable<T> task) {
    this(task, TaskDispatcher.getInstance());
  }

  @Override
  public T call() throws Exception {
    return callable != null ? callable.call() : null;
  }
}