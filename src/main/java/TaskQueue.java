import java.util.*;
public class TaskQueue {
    private Queue<Integer> queue = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public synchronized void addTask(int task) {
        while (queue.size() >= MAX_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.offer(task);
        notifyAll();
    }

    public synchronized int getTask() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int task = queue.poll();
        notifyAll();
        return task;
    }

    public int getMaxQueueSize() {
        return MAX_SIZE;
    }

    public void start(Results res)
    {
        for(int i = 0; i < MAX_SIZE; i++)
        {
            Thread th = new Thread(()->{
               while(true)
               {
                   int task = this.getTask();
                   try{
                       res.addResult(task, compute(task));
                       System.out.println("Task "+task+" computed");
                   }catch (InterruptedException e)
                   {
                       break;
                   }
               }
            });
            th.start();
            System.out.println(th);
        }
    }
    private int compute(int task) throws InterruptedException
    {
        Thread.sleep(100);
        int ret = task;
        if(task % 2 == 0)
            ret*=2;
        else
            ret*=3;
        return ret;
    }
}