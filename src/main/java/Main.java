import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    TaskQueue tq = new TaskQueue();
    Results res = new Results();
    for(int i = 0; i < tq.getMaxQueueSize(); i++)
        tq.addTask(i);
    tq.initThreads(res);
    Scanner input = new Scanner(System.in);
    System.out.println("Type command");
    while(true)
    {
        String command = input.nextLine();
        String[] commandSplit = command.split(" ");
        if(commandSplit[0].compareTo("quit") == 0)
            break;
        else if(commandSplit[0].compareTo("compute") == 0) {
            tq.addTask(Integer.parseInt(commandSplit[1]));
            tq.initThreads(res);
        }
        else
            System.out.println("Invalid command");
    }
    tq.end();
    System.out.println("Quited");
    System.out.println(res);
    }
}
