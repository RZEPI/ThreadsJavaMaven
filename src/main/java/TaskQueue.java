import java.util.*;
public class TaskQueue {
    private Queue<Integer> queue = new LinkedList<>();
    private List<Thread> threads = new ArrayList<>();
    private final int N = 10000;
    private boolean runThreads = true;
    private final int MAX_SIZE = 10;
    private final int TASK_NUM = 4;

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
                return 0;
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

    for(int i = 0; i < TASK_NUM; i++)
        {
            Thread th = new Thread(()->{
               while(runThreads)
               {
                   try{
                       int task = getTask();
                       if(task != 0) {
                           compute(res, task);
                           System.out.println("Task " + task + " computed");
                       }
                   }
                   catch (InterruptedException e)
                   {
                       Thread.currentThread().interrupt();
                       System.out.println("Thread "+ Thread.currentThread().getName()+" killed");
                       break;
                   }
               }
            });
            th.start();
            threads.add(th);
            System.out.println(th);
        }
    }

    private void compute(Results res, int task) throws InterruptedException
    {
        double pi = 0.;
        for(int i = 1; i < task+1 ; i++ ) {
            pi += Math.pow(-1, i - 1) / (2 * i - 1);
            if(!this.runThreads)
                return;
        }
            pi*=4;
        Thread.sleep(1000);
        res.addResult(task, pi);
    }
    
    public void end()
    {
        this.runThreads = false;
        for (Thread th : threads) {
            th.interrupt();
        }
    }
}