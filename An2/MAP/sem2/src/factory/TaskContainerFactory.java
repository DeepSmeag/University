package factory;

import container.Container;
import container.QueueContainer;
import container.StackContainer;
import container.Strategy;

import java.util.concurrent.locks.ReentrantLock;

// instanta unica sa fie thread-safe
public class TaskContainerFactory implements Factory{
    private static TaskContainerFactory instance;
    public static synchronized TaskContainerFactory getInstance() {
        if(instance == null){
            instance = new TaskContainerFactory();
        }
        return instance;
    }
    private TaskContainerFactory() {
    }

    @Override
    public Container createContainer(Strategy strategy) {
        if(strategy == Strategy.LIFO) {
            return new StackContainer();
        } else if (strategy == Strategy.FIFO) {
            return new QueueContainer();
        }
        return null;
    }
}
