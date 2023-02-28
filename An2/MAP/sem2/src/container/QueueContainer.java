package container;

import model.Task;
import utils.Constants;

public class QueueContainer extends QSSuperContainer implements Container {


    public QueueContainer() {
        super();
    }

    @Override
    public Task remove() {
        if (!isEmpty()) {
            Task task = tasks[0];
            if (size == 1) {
                tasks[0] = tasks[1];
            } else {
                for (int i = size - 1; i > 0; i--) {
                    tasks[i - 1] = tasks[i];
                }

            }
            size--;

            return task;
        }
        return null;

    }
}
