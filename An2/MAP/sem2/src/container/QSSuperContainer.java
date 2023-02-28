package container;

import model.Task;
import utils.Constants;

public class QSSuperContainer {
    protected Task[] tasks;
    protected int size;
    public QSSuperContainer() {
        size = 0;
        tasks = new Task[Constants.INITIAL_TASK_SIZE];
    }
    private void resize() {
        Task[] newTasks = new Task[tasks.length + 1];
        System.arraycopy(tasks, 0, newTasks, 0, size);
        tasks = newTasks;
    }

    public void add(Task task) {
        if (size == tasks.length) {
            this.resize();
        }
        tasks[size] = task;
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
