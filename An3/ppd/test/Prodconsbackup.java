import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Prodcons {
    static public int noProducers;
    static public int noConsumers;
    static public int total = 0;

    static public class Producer extends Thread {
        private SharedBuffer sharedBuffer;
        private int id;
        private int iterations;
        private int iItems;
        private int xWait;

        public Producer(int id, SharedBuffer sharedBuffer, int iterations, int iItems, int xWait) {
            this.id = id;
            this.sharedBuffer = sharedBuffer;
            this.iterations = iterations;
            this.iItems = iItems;
            this.xWait = xWait;
        }

        private List<Integer> generateItems() {
            List<Integer> items = new ArrayList<Integer>(iItems);
            Random random = new Random();
            for (int i = 0; i < iItems; i++) {
                items.add(random.nextInt(100));
            }
            return items;
        }

        @Override
        public void run() {
            for (int iteration = 0; iteration < iterations; iteration++) {
                List<Integer> items = generateItems();
                for (Integer item : items) {
                    // System.out.println("Producer " + id + " trying to enter condFull");

                    while (sharedBuffer.isFull()) {

                        // System.out.println("Producer " + id + " going to sleep cuz buffer full");
                        sharedBuffer.waitIsNotFull();
                        // System.out.println("Producer " + id + " woke up");

                    }
                    sharedBuffer.add(item);
                    // System.out.println("Producer " + id + " produced " + item);
                    sharedBuffer.notifyIsNotEmpty();
                    // System.out.println("Producer " + id + " notified");

                    // System.out.println("Producer " + id + " exiting condFull");
                }
                try {
                    Thread.sleep(xWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (sharedBuffer) {
                noProducers--;
                if (noProducers == 0) {
                    sharedBuffer.flagDoneProducing();
                    sharedBuffer.notifyDoneProducing();
                }
            }
            // System.out.println("Producer " + id + " got here");
        }
    }

    public static class Consumer extends Thread {
        private SharedBuffer sharedBuffer;
        private int id;
        private int jItems;
        private int yWait;
        private boolean doneProducing;

        public Consumer(int id, SharedBuffer sharedBuffer, int jItems, int yWait) {
            this.id = id;
            this.sharedBuffer = sharedBuffer;
            this.jItems = jItems;
            this.yWait = yWait;
        }

        @Override
        public void run() {
            boolean stop = false;
            while (!stop) {
                for (int i = 0; i < jItems; i++) {
                    // System.out.println("Consumer " + id + " trying to enter condEmpty");
                    if (sharedBuffer.isEmpty() && !sharedBuffer.isDoneProducing()) {

                        // System.out.println("Consumer " + id + " going to sleep cuz buffer empty");
                        sharedBuffer.waitIsNotEmpty();
                        // System.out.println("Consumer " + id + " woke up");

                    }
                    if (sharedBuffer.isDoneProducing() && sharedBuffer.isEmpty()) {
                        // System.out.println("Consumer " + id + " done consuming");
                        stop = true;
                        // return;
                        break;
                    }
                    Integer item = sharedBuffer.remove();
                    // System.out.println("Consumer " + id + " consumed " + item);
                    sharedBuffer.notifyIsNotFull();
                    // System.out.println("Consumer " + id + " notified");

                }
                try {
                    Thread.sleep(yWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static public class SharedBuffer {
        public Object condEmpty = new Object();
        public Object condFull = new Object();
        public int maxSize;
        public List<Integer> buffer;
        public boolean doneProducing = false;

        public SharedBuffer(int size) {
            this.maxSize = size;
            buffer = new ArrayList<Integer>(size);
        }

        public synchronized void flagDoneProducing() {
            doneProducing = true;
        }

        public synchronized boolean isDoneProducing() {
            return doneProducing;
        }

        public synchronized void add(Integer item) {
            buffer.add(item);
            total++;
        }

        public synchronized Integer remove() {
            if (!isEmpty()) {
                return buffer.remove(0);
            }
            return 0;
        }

        public synchronized Integer getSize() {
            return buffer.size();
        }

        public synchronized Integer getAt(int index) {
            return buffer.get(index);
        }

        public synchronized boolean isFull() {
            return getSize() >= maxSize;
        }

        public synchronized boolean isEmpty() {
            return getSize() == 0;
        }

        public void waitIsNotFull() {
            synchronized (condFull) {
                while (getSize() >= maxSize) {
                    try {
                        condFull.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void waitIsNotEmpty() {
            synchronized (condEmpty) {
                while (getSize() == 0) {
                    try {
                        condEmpty.wait(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void notifyIsNotFull() {
            synchronized (condFull) {
                condFull.notify();
            }
        }

        public void notifyIsNotEmpty() {
            synchronized (condEmpty) {
                condEmpty.notify();
            }
        }

        public void notifyDoneProducing() {
            synchronized (condEmpty) {
                System.out.println("All producers done, notifying -  " + total);
                condEmpty.notifyAll();
            }
        }

    }

    public static void main(String[] args) {
        noProducers = 3;
        noConsumers = 2;
        int nBufMax = 20;
        SharedBuffer sharedBuffer = new SharedBuffer(nBufMax);
        Thread[] producers = new Thread[noProducers];
        Thread[] consumers = new Thread[noConsumers];
        for (int i = 0; i < noProducers; i++) {
            producers[i] = new Producer(i, sharedBuffer, 100, 4, 10);
        }
        for (int i = 0; i < noConsumers; i++) {
            consumers[i] = new Consumer(i, sharedBuffer, 3, 8);
        }
        for (int i = 0; i < noProducers; i++) {
            producers[i].start();
        }
        for (int i = 0; i < noConsumers; i++) {
            consumers[i].start();
        }
        for (int i = 0; i < noProducers; i++) {
            try {
                producers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Main thread: producers are done");
        for (int i = 0; i < noConsumers; i++) {
            try {
                consumers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Main thread: consumers are done");

    }
}
