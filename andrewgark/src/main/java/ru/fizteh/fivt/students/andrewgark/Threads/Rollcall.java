package ru.fizteh.fivt.students.andrewgark.Threads;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Rollcall {
    public static class ParameterException extends Exception {
        public ParameterException(String message) {
            super(message);
        }
    }

    private static volatile boolean allYes = false;
    private static CyclicBarrier askingThreads, waitingAnswers;

    public static void main(String[] args) {
        Integer n = 0;
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

        RollcallThread[] threads = new RollcallThread[n];
        askingThreads = new CyclicBarrier(n + 1);
        waitingAnswers = new CyclicBarrier(n + 1);

        for (int i = 0; i < n; i++) {
            threads[i] = new RollcallThread();
            threads[i].start();
        }

        while (!allYes) {
            System.out.println("Are you ready?");
            allYes = true;
            try {
                askingThreads.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            askingThreads.reset();
            try {
                waitingAnswers.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
            waitingAnswers.reset();
            if (allYes) {
                System.exit(0);
            }
        }
    }

    private static class RollcallThread extends Thread {
        private Boolean answer;
        private Random rand = new Random();

        @Override
        public void run() {
            while (true) {
                try {
                    askingThreads.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
                answer = rand.nextInt(10) != 0;
                if (answer) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                if (!answer) {
                    allYes = false;
                }
                try {
                    waitingAnswers.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.err.println(e.getMessage());
                    System.exit(1);
                }
            }
        }
    }
}
