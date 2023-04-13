import java.util.*;
public class Results {
    private Map<Integer, Integer> results = new HashMap<>();
    private final Object lock = new Object();

    public void addResult(int task, int result) {
        synchronized (lock) {
            results.put(task, result);
        }
    }

    public int getResult(int task) {
        synchronized (lock) {
            return results.get(task);
        }
    }
}
