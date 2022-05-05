package Onee;

import java.util.concurrent.Callable;

public class Task<T> {

    Callable <? extends T> callable;
    private volatile T result;

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() throws CalculationException, Exception {
        if(result == null) {
            synchronized (this) {
                try {
                    if(result == null)
                        result = callable.call();
                } catch (CalculationException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}