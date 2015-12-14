package ru.fizteh.fivt.students.andrewgark.Threads;

public class Counter {
    private static volatile int printId;

    public static class ParameterException extends Exception {
        public ParameterException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        int n = 0;
        try {
            if (args.length != 1) {
                throw new ParameterException("Argument must be exactly one positive number");
            }
            n = Integer.valueOf(args[0]);
            if (n <= 0) {
                throw new ParameterException("Argument must be positive");
            }
        } catch (ParameterException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        printId = 0;
        for (int id = 0; id < n; id++) {
            CounterThread thread = new CounterThread(id, (id + 1) % n);
            thread.start();
        }
    }

    private static Object monitor = new Object();

    private static class CounterThread extends Thread {
        private int myId, nextId;

        CounterThread(int id1, int id2) {
            myId = id1;
            nextId = id2;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (monitor) {
                    while (myId != printId) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                            System.exit(1);
                        }
                    }
                    System.out.println("Thread-" + String.valueOf(myId + 1));
                    printId = nextId;
                    monitor.notifyAll();
                }
            }
        }
    }
}
