import java.util.*;
public class TaskQueue {
    private Queue<Integer> queue = new LinkedList<>();
    private List<Thread> threads = new ArrayList<>();
    private boolean runThreads = true;
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
                break;
            }
        }
        int task = queue.poll();
        notifyAll();
        return task;
    }

    public int getMaxQueueSize() {
        return MAX_SIZE;
    }

    public void initThreads(Results res)
    {
        int currentQueueSize = queue.size();
        for(int i = 0; i < currentQueueSize; i++)
        {
            int task = this.getTask();
            Thread th = new Thread(()->{
               while(runThreads)
               {
                   try{
                       res.addResult(task, compute(res, task));
                       System.out.println("Task "+task+" computed");
                   }
                   catch (InterruptedException e)
                   {
                       Thread.currentThread().interrupt();
                       System.out.println("Task "+task+" computed");
                       break;
                   }
               }
            });
            th.start();
            threads.add(th);
            System.out.println(th);
        }
    }

    private int compute(Results res, int task) throws InterruptedException
    {
        Thread.sleep(10000);
        int ret = task;
        if(task % 2 == 0)
            ret*=2;
        else
            ret*=3;
        res.addResult(task, ret);
        throw new InterruptedException();
    }
    
    public void end()
    {
        this.runThreads = false;
        for(Thread th : this.threads)
            th.interrupt();
    }
}