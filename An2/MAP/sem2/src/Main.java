import container.Strategy;
import model.SortingTask;


public class Main {
    private static Strategy strategyFromString(String arg) {
        return Strategy.valueOf(arg);
    }

    public static void main(String[] args) {
        int num[] = {1, 2, 5, 6, 3, 4};
        SortingTask task = new SortingTask("id", "desc", "mergesort", num);
        task.execute();
        task.printNum();

        TestRunner.runDelay(strategyFromString("FIFO"));


    }
}