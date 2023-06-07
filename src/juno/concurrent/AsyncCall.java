package juno.concurrent;

public class AsyncCall<T> extends AbstractCall<T> {

  public final CallTask<T> task;
  
  public AsyncCall(CallTask<T> task, Dispatcher dispatcher) {
    super(dispatcher);
    this.task = task;
  }

  public AsyncCall(CallTask<T> task) {
    this(task, Dispatcher.get());
  }

  @Override
  public T doInBackground() throws Exception {
    return task != null ? task.doInBackground() : null;
  }
}