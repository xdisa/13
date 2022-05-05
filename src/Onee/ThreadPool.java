package Onee;


public interface ThreadPool {
    void start();
    void execute(Task task);
}
