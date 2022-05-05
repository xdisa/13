package Twoo;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class ContextImpl implements Context{

    private final List<Future> futures;
    public ContextImpl(List<Future> futures) {
        this.futures = futures;
    }
    @Override
    public int getCompletedTaskCount() {
        AtomicInteger counter = new AtomicInteger();
        for (Future elem : futures) {
            if (elem.isDone()) {
                counter.incrementAndGet();
            }
        }
        return counter.get();
    }
    @Override
    public int getFailedTaskCount() {
        int counter = 0;
        for (Future elem : futures) {
            if (elem.isDone()) {
                try {
                    elem.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    counter++;
                }
            }
        }
        return counter;
    }
    @Override
    public int getInterruptedTaskCount() {
        int counter = 0;
        for (Future elem : futures) {
            if (elem.isCancelled()) {
                counter++;
            }
        }
        return counter;
    }
    @Override
    public void interrupt() {
        for (Future elem : futures) {
            elem.cancel(false);
        }
    }
    @Override
    public boolean isFinished() {
        int counter = 0;
        for (Future elem : futures) {
            if (elem.isDone()) {
                counter++;
            }
        }
        return counter == futures.size();
    }

}
