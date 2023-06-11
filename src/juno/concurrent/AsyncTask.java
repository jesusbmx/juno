package juno.concurrent;

public class AsyncTask<T> extends AbstractAsync<T> {

  public final Task<T> task;
  
  public AsyncTask(Task<T> task, Dispatcher dispatcher) {
    super(dispatcher);
    this.task = task;
  }

  public AsyncTask(Task<T> task) {
    this(task, Dispatcher.get());
  }

  @Override
  public T doInBackground() throws Exception {
    return task != null ? task.doInBackground() : null;
  }
}