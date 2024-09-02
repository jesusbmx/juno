
package juno.concurrent;

public class CallbackAdapter<T> implements Callback<T> {
    
  private OnResponse<T> onResponseListener;
  private OnError onErrorListener;

  public CallbackAdapter() {
      
  }
  
  public CallbackAdapter(OnResponse<T> onResponse, OnError onError) {
    this.onResponseListener = onResponse;
    this.onErrorListener = onError;
  }
  
  public OnError getOnErrorListener() {
    return onErrorListener;
  }

  public void setOnErrorListener(OnError onError) {
    this.onErrorListener = onError;
  }

  public OnResponse<T> getOnResponseListener() {
    return onResponseListener;
  }

  public void setOnResponseListener(OnResponse<T> onResponse) {
    this.onResponseListener = onResponse;
  }
  
  @Override 
  public void onResponse(T result) throws Exception {
    if (onResponseListener != null) {
      onResponseListener.onResponse(result);
    }
  }

  @Override 
  public void onFailure(Exception e) {
    if ( onErrorListener != null) {
      onErrorListener.onFailure(e);
    }
  }
}
