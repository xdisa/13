package Twoo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutioManagerImpl implements ExecutionManager{

    @Override
    public Context execute(Runnable callback, Runnable...tasks) {

        ExecutorService executorService = Executors.newFixedThreadPool(tasks.length);

        List<Future> futures = new ArrayList<>();

        for (Runnable task : tasks) {
            futures.add(executorService.submit(task));
        }

        new Thread(() -> {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                    if (!executorService.awaitTermination(15, TimeUnit.SECONDS)) {
                        System.out.println("dont stop...");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                if (executorService.isTerminated()) {
                    callback.run();
                    break;
                }
            }
        }).start();

        return new ContextImpl(futures);
    }

}
