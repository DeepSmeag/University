
public class Main {
    public static void vectFill(int a[], int n) {
        for (int i = 0; i < n; i++) {
            a[i] = (int) (Math.random() * 1000);
        }
    }

    public static long secvential(int n) {
        int a[] = new int[n];
        int b[] = new int[n];
        // random fill the vectors
        vectFill(a, n);
        vectFill(b, n);
        // compute the sum
        int c[] = new int[n];
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            c[i] = a[i] + b[i];
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long linear(int n, int p) {
        int a[] = new int[n];
        int b[] = new int[n];
        // random fill the vectors
        vectFill(a, n);
        vectFill(b, n);
        int c[] = new int[n];
        Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            threads[i] = new MyThreadLinear(i * (n / p), (i + 1) * (n / p), a, b, c);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            threads[i].start();
        }
        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long linearPow(int n, int p) {
        int a[] = new int[n];
        int b[] = new int[n];
        // random fill the vectors
        vectFill(a, n);
        vectFill(b, n);
        int c[] = new int[n];
        Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            threads[i] = new MyThreadLinearPow(i * (n / p), (i + 1) * (n / p), a, b, c);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            threads[i].start();
        }
        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long cyclicPow(int n, int p) {
        int a[] = new int[n];
        int b[] = new int[n];
        // random fill the vectors
        vectFill(a, n);
        vectFill(b, n);
        int c[] = new int[n];
        Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            threads[i] = new MyThreadCyclicPow(i, p, a, b, c, n);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            threads[i].start();
        }
        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static long cyclic(int n, int p) {
        int a[] = new int[n];
        int b[] = new int[n];
        // random fill the vectors
        vectFill(a, n);
        vectFill(b, n);
        int c[] = new int[n];
        Thread[] threads = new Thread[p];
        for (int i = 0; i < p; i++) {
            threads[i] = new MyThreadCyclic(i, p, a, b, c, n);
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < p; i++) {
            threads[i].start();
        }
        for (int i = 0; i < p; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) {
        System.out.println("Secvential 100m: " + secvential(100000000));
        System.out.println("Linear 100m x 4: " + linear(100000000, 4));
        System.out.println("Cyclic 100m x 4: " + cyclic(100000000, 4));
        System.out.println("LinearPow 100m x 4: " + linearPow(100000000, 4));
        System.out.println("CyclicPow 100m x 4: " + cyclicPow(100000000, 4));
        // Now with small n = 1000
        System.out.println("Secvential 1000: " + secvential(1000));
        System.out.println("Linear 1000 x 4: " + linear(1000, 4));
        System.out.println("Cyclic 1000 x 4: " + cyclic(1000, 4));
        System.out.println("LinearPow 1000 x 4: " + linearPow(1000, 4));
        System.out.println("CyclicPow 1000 x 4: " + cyclicPow(1000, 4));
    }
}

// Class that extends Thread
class MyThreadLinear extends Thread {
    private int start;
    private int end;
    private int[] a;
    private int[] b;
    private int[] c;

    public MyThreadLinear(int start, int end, int[] a, int[] b, int[] c) {
        this.start = start;
        this.end = end;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            c[i] = a[i] + b[i];
        }
    }
}

class MyThreadLinearPow extends Thread {
    private int start;
    private int end;
    private int[] a;
    private int[] b;
    private int[] c;

    public MyThreadLinearPow(int start, int end, int[] a, int[] b, int[] c) {
        this.start = start;
        this.end = end;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            c[i] = (int) Math.pow(a[i], 3) + (int) Math.pow(b[i], 3);
        }
    }
}

// class that extends Thread to do cyclic calculation
class MyThreadCyclic extends Thread {
    private int start;
    private int step;
    private int[] a;
    private int[] b;
    private int[] c;
    private int n;

    public MyThreadCyclic(int start, int step, int[] a, int[] b, int[] c, int n) {
        this.start = start;
        this.step = step;
        this.a = a;
        this.b = b;
        this.c = c;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = start; i < n; i += step) {
            c[i] = a[i] + b[i];
        }
    }
}

class MyThreadCyclicPow extends Thread {
    private int start;
    private int step;
    private int[] a;
    private int[] b;
    private int[] c;
    private int n;

    public MyThreadCyclicPow(int start, int step, int[] a, int[] b, int[] c, int n) {
        this.start = start;
        this.step = step;
        this.a = a;
        this.b = b;
        this.c = c;
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = start; i < n; i += step) {
            c[i] = (int) Math.pow(a[i], 3) + (int) Math.pow(b[i], 3);
        }
    }
}