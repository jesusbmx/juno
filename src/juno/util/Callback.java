package juno.util;

public interface Callback<Result> {
    
    void call(Result result) throws Exception;
}
