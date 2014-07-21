package acme.me.thread;

import java.util.concurrent.Callable;

public class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int m) {
        id = m;
    }

    public String call() throws Exception {
        return "This thread is: " + id;
    }
    public static void main(String[] args){
        
    }
    
}
