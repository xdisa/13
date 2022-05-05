package Onee;

import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool  implements ThreadPool{

    private final LinkedBlockingQueue queue;

    public FixedThreadPool(int nThreads){
        queue = new LinkedBlockingQueue();
        for (int i = 0; i < nThreads; i++) {
            start();
        }
    }


    @Override
    public void start() {
        new Thread(() -> {
            Task task;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("error while waiting queue" + e.getMessage());
                        }
                    }
                    task = (Task) queue.poll();
                }

                try {
                    System.out.println(task.get());
                } catch (Exception e) {
                    System.out.println("Thread pool terminated due to error" + e.getMessage());
                }
            }
        }).start();
    }


    @Override
    public void execute(Task task) {
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }
}
