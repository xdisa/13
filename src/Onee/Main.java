package Onee;

public class Main {
    public static void main(String[]args){
        FixedThreadPool pool = new FixedThreadPool(8);
        TaskResult taskResult;
        for(int i=0; i<=10; i++){
            taskResult = new TaskResult<String>(i);
            pool.execute((new Task(taskResult)));
        }
    }
}
