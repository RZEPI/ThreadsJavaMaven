import java.util.*;
public class Results {
    private Map<Integer, Double> results = new HashMap<>();
    private final Object lock = new Object();

    public void addResult(int task, Double result) {
        synchronized (lock) {
            results.put(task, result);
        }
    }

    @Override
    public String toString()
    {
        String output = "";
        for (Map.Entry<Integer, Double> entry: this.results.entrySet()) {
            output = output.concat("Result of thread "+entry.getKey()+":"+entry.getValue()+'\n');
        }
        return output;
    }
}
