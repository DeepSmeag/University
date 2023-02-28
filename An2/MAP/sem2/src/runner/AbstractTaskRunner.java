package runner;

import model.Task;

public abstract class AbstractTaskRunner implements TaskRunner {
    protected TaskRunner runner;

    public AbstractTaskRunner(TaskRunner runner) {
        this.runner = runner;
    }

    @Override
    public void executeAll() {
        while(hasTask()) {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task task) {
        this.runner.addTask(task);
    }

    @Override
    public boolean hasTask() {
        return runner.hasTask();
    }
}
