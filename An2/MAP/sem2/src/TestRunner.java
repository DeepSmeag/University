import container.Strategy;
import model.MessageTask;
import runner.DelayTaskRunner;
import runner.PrinterTaskRunner;
import runner.StrategyTaskRunner;

import java.time.LocalDateTime;

public class TestRunner {

    private static MessageTask[] createMessageTasks(){
        MessageTask m1 = new MessageTask("1", "teme laborator",
                "ai primit o tema de laborator", "George", "Maria",
                LocalDateTime.now());
        MessageTask m2 = new MessageTask("2", "teme laborator",
                "ai o tema de seminar", "Mihai", "Maria",
                LocalDateTime.now());
        MessageTask m3 = new MessageTask("3", "teme seminar",
                "ai primit o tema de seminar", "George", "Andrei",
                LocalDateTime.now());
        return new MessageTask[]{m1, m2, m3};
    }

    public static void runStrategy(Strategy strategy) {
        MessageTask[] lista = createMessageTasks();
        StrategyTaskRunner runner = new StrategyTaskRunner(strategy);
        for(MessageTask task : lista) {
            runner.addTask(task);
        }

        runner.executeAll();
    }

    public static void runPrinter(Strategy strategy) {
        MessageTask[] lista = createMessageTasks();
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(strategy);
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);

        for(MessageTask task : lista) {
            printerTaskRunner.addTask(task);
        }

        printerTaskRunner.executeAll();
    }
    public static void runDelay(Strategy strategy) {
        MessageTask[] lista = createMessageTasks();
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(strategy);
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(strategyTaskRunner);
        DelayTaskRunner delayTaskRunner = new DelayTaskRunner(printerTaskRunner);
        for (MessageTask task : lista) {
            delayTaskRunner.addTask(task);
        }
        delayTaskRunner.executeAll();
    }

    public static void run(){
        MessageTask[] lista = createMessageTasks();
        for(MessageTask messageTask : lista)
        {
            messageTask.execute();
        }
    }
}
