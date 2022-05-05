package Onee;

import java.util.concurrent.Callable;

public class TaskResult<T> implements Callable<T> {

    private final int id;

    public TaskResult(int id){
        this.id = id;
    }

    @Override
    public T call() {
        return (T) ("result" + id +"   thread" + Thread.currentThread().toString());
    }

}

