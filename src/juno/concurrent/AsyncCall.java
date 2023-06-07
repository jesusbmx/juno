package juno.concurrent;

public class AsyncCall<T> extends AbstractCall<T> {

  public final Task<T> task;
  
  public AsyncCall(Task<T> task, Dispatcher dispatcher) {
    super(dispatcher);
    this.task = task;
  }

  public AsyncCall(Task<T> task) {
    this(task, Dispatcher.get());
  }

  @Override
  public T doInBackground() throws Exception {
    return task != null ? task.doInBackground() : null;
  }
}