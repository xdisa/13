package Twoo;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Runnable callback = () -> System.out.println("done");

        Runnable run = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("done");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable[] tasks = new Runnable[10];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = run;
        }

        ExecutionManager executionManager = new ExecutioManagerImpl();
        executionManager.execute(callback, tasks);
    }

}

