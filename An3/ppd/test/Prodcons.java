import java.io.File;
import java.io.FileWriter;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Prodcons {
    static public int noProducers;
    static public int noConsumers;
    static public int total = 0;
    static public Object totalLock = new Object();
    static public int totalConsume = 0;
    static public Object totalConsumeLock = new Object();
    static public Object prodLock = new Object();
    static public boolean doneSuper = false;
    static public Object superLock = new Object();

    static public class Producer extends Thread {
        private SharedBuffer[] sharedBuffers;
        private int id;
        private int iterations;
        private int iItems;
        private int xWait;

        public Producer(int id, SharedBuffer[] sharedBuffers, int iterations, int iItems, int xWait) {
            this.id = id;
            this.sharedBuffers = sharedBuffers;
            this.iterations = iterations;
            this.iItems = iItems;
            this.xWait = xWait;
        }

        private List<Call> generateItems() {
            List<Call> items = new ArrayList<Call>(iItems);
            Random random = new Random();
            for (int i = 0; i < iItems; i++) {
                items.add(new Call(random.nextInt(100), random.nextInt(3)));
            }
            return items;
        }

        @Override
        public void run() {
            for (int iteration = 0; iteration < iterations; iteration++) {
                List<Call> items = generateItems();
                for (Call item : items) {
                    // System.out.println("Producer " + id + " trying to enter condFull");
                    int diff = item.getDificultate();
                    while (sharedBuffers[diff].isFull()) {

                        // System.out.println("Producer " + id + " going to sleep cuz buffer full");
                        sharedBuffers[diff].waitIsNotFull();
                        // System.out.println("Producer " + id + " woke up");

                    }
                    sharedBuffers[diff].add(item);
                    // System.out.println("Producer " + id + " produced " + item);
                    sharedBuffers[diff].notifyIsNotEmpty();
                    // System.out.println("Producer " + id + " notified");

                    // System.out.println("Producer " + id + " exiting condFull");
                }
                try {
                    Thread.sleep(xWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (prodLock) {
                noProducers--;
            }
            if (noProducers == 0) {
                for (int i = 0; i < 3; i++) {
                    synchronized (sharedBuffers[i]) {
                        sharedBuffers[i].flagDoneProducing();
                        sharedBuffers[i].notifyDoneProducing();
                    }
                    // sharedBuffer.flagDoneProducing();
                    // sharedBuffer.notifyDoneProducing();
                }
                System.out.println("PRODUCERS FDONE NOTIFYING");
            }
            // System.out.println("Producer " + id + " got here");
        }
    }

    public static class Consumer extends Thread {
        private SharedBuffer[] sharedBuffers;
        private int id;
        private int jItems;
        private int yWait;
        private boolean doneProducing;
        private int difficulty;
        private CallMap callMap;

        public Consumer(int id, SharedBuffer[] sharedBuffers, int jItems, int yWait, int difficulty, CallMap callMap) {
            this.id = id;
            this.sharedBuffers = sharedBuffers;
            this.jItems = jItems;
            this.yWait = yWait;
            this.difficulty = difficulty;
            this.callMap = callMap;
        }

        @Override
        public void run() {
            boolean stop = false;
            while (!stop) {

                // System.out.println("Consumer " + id + " trying to enter condEmpty");
                if (sharedBuffers[difficulty].isEmpty() && !sharedBuffers[difficulty].isDoneProducing()) {

                    // System.out.println("Consumer " + id + " going to sleep cuz buffer empty");
                    sharedBuffers[difficulty].waitIsNotEmpty();
                    // System.out.println("Consumer " + id + " woke up");

                }
                if (sharedBuffers[difficulty].isDoneProducing() && sharedBuffers[difficulty].isEmpty()) {
                    // System.out.println("Consumer " + id + " done consuming");
                    stop = true;
                    return;
                    // break;
                }
                Call item = sharedBuffers[difficulty].remove();
                synchronized (totalConsumeLock) {
                    totalConsume++;
                }
                if (item.getId() == -1) {
                    continue;
                }
                item.setIdAgent(id);
                callMap.add(item);
                // System.out.println("Consumer " + id + " consumed " + item);
                sharedBuffers[difficulty].notifyIsNotFull();
                // System.out.println("Consumer " + id + " notified");

                try {
                    Thread.sleep(yWait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static public class CallMap {
        public Map<Integer, List<Call>> map = new HashMap<Integer, List<Call>>();

        public synchronized void add(Call call) {
            if (!map.containsKey(call.getIdAgent())) {
                map.put(call.getIdAgent(), new ArrayList<Call>());
                map.get(call.getIdAgent()).add(call);
            } else {
                map.get(call.getIdAgent()).add(call);
            }
        }

        public synchronized void getTotal() {
            int total = 0;
            for (int key : map.keySet()) {
                System.out.println("Agent " + key + " has " + map.get(key).size() + " calls");
                total += map.get(key).size();
            }
            System.out.println("Total calls: " + total);
        }

        public synchronized List<Integer> getAgentNumbers() {
            List<Integer> agents = new ArrayList<>();
            for (int key : map.keySet()) {
                agents.add(map.get(key).size());
            }
            return agents;
        }
    }

    static public class Call {
        public int id_apel;
        public int dificulate;
        public int id_agent;

        public Call(int id_apel, int dificulate) {
            this.id_apel = id_apel;
            this.dificulate = dificulate;
        }

        public int getId() {
            return id_apel;
        }

        public int getDificultate() {
            return dificulate;
        }

        public int getIdAgent() {
            return id_agent;
        }

        public void setIdAgent(int id_agent) {
            this.id_agent = id_agent;
        }
    }

    static public class SharedBuffer {
        public Object condEmpty = new Object();
        public Object condFull = new Object();
        public int maxSize;
        // public List<Integer> buffer;
        public Queue<Call> buffer;
        public boolean doneProducing = false;

        public SharedBuffer(int size) {
            this.maxSize = size;
            // buffer = new ArrayList<Integer>(size);
            buffer = new LinkedList<>();
        }

        public synchronized void flagDoneProducing() {
            doneProducing = true;
        }

        public synchronized boolean isDoneProducing() {
            return doneProducing;
        }

        public synchronized void add(Call item) {
            buffer.add(item);
            synchronized (totalLock) {
                total++;
            }
        }

        public synchronized Call remove() {
            if (!isEmpty()) {
                Call item = buffer.poll();
                return item;
            }
            return new Call(-1, -1);
        }

        public synchronized Integer getSize() {
            return buffer.size();
        }

        // public synchronized Call getAt(int index) {
        // return buffer.(index);
        // }

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
                if (getSize() == 0) {
                    try {
                        condEmpty.wait(10);
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

    public static class Superv extends Thread {
        public CallMap callMap;
        public SharedBuffer[] sharedBuffers;

        public Superv(CallMap callMap, SharedBuffer[] sharedBuffers) {
            this.callMap = callMap;
            this.sharedBuffers = sharedBuffers;
        }

        @Override
        public void run() {
            // Open file 'log.txt' with write
            try {
                FileWriter fileWriter = new FileWriter("log.txt");

                while (!doneSuper) {

                    java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(timestamp);
                    stringBuilder.append(" - ");
                    stringBuilder.append("queue 0-" + sharedBuffers[0].getSize());
                    stringBuilder.append(" queue 1-" + sharedBuffers[1].getSize());
                    stringBuilder.append(" queue 2-" + sharedBuffers[2].getSize());
                    for (Integer i : callMap.getAgentNumbers()) {
                        stringBuilder.append("Agent " + i + "= " + i + " | ");
                    }
                    stringBuilder.append("\n");
                    fileWriter.write(stringBuilder.toString());
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        noProducers = 5;
        noConsumers = 6;
        int nBufMax = 20;
        SharedBuffer[] sharedBuffers = new SharedBuffer[3];
        sharedBuffers[0] = new SharedBuffer(nBufMax);
        sharedBuffers[1] = new SharedBuffer(nBufMax);
        sharedBuffers[2] = new SharedBuffer(nBufMax);
        Thread[] producers = new Thread[noProducers];
        Thread[] consumers = new Thread[noConsumers];
        CallMap callMap = new CallMap();
        Thread superv = new Superv(callMap, sharedBuffers);
        for (int i = 0; i < noProducers; i++) {
            producers[i] = new Producer(i, sharedBuffers, 100, 1, 10);
        }
        for (int i = 0; i < noConsumers; i++) {
            consumers[i] = new Consumer(i, sharedBuffers, 1, 5, i % 3, callMap);
        }
        for (int i = 0; i < noProducers; i++) {
            producers[i].start();
        }
        for (int i = 0; i < noConsumers; i++) {
            consumers[i].start();
        }
        superv.start();
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
        synchronized (superLock) {
            doneSuper = true;
        }
        try {
            superv.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        callMap.getTotal();
    }
}
