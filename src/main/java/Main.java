public class Main {
    public static void main(String[] args) {
    TaskQueue tq = new TaskQueue();
    Results res = new Results();
    for(int i = 0; i < tq.getMaxQueueSize(); i++)
        tq.addTask(i);
    tq.start(res);
    }
}
