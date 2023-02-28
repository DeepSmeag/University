package runner;

import container.Container;
import container.Strategy;
import factory.TaskContainerFactory;
import model.Task;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
        TaskContainerFactory factory = TaskContainerFactory.getInstance();
        this.container = factory.createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
            container.remove().execute();
    }

    @Override
    public void executeAll() {
        while(!container.isEmpty()) {
            this.executeOneTask();
        }
    }

    @Override
    public void addTask(Task task) {
        container.add(task);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
