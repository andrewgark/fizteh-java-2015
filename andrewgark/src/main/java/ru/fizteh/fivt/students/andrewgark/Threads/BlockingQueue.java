package ru.fizteh.fivt.students.andrewgark.Threads;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<T> {
    private int maxQueueSize;
    private Queue<T> queue;
    private Lock queueLock = new ReentrantLock();
    private Lock actionLock = new ReentrantLock();
    private Condition popWait = actionLock.newCondition();
    private Condition pushWait = actionLock.newCondition();

    public void offer(List<T> list) {
        actionLock.lock();
        try {
            boolean added = false;
            while (!added) {
                try {
                    queueLock.lock();
                    if (queue.size() + list.size() <= maxQueueSize) {
                        queue.addAll(list);
                        added = true;
                    }
                } finally {
                    queueLock.unlock();
                }
                if (!added) {
                    try {
                        popWait.await();
                    } catch (InterruptedException ex) {
                        return;
                    }
                }
            }
        } finally {
            pushWait.signalAll();
            actionLock.unlock();
        }
    }

    public List<Object> take(int n) {
        actionLock.lock();
        try {
            List<Object> ans = new ArrayList<>();
            while (ans.size() < n) {
                try {
                    queueLock.lock();
                    if (queue.size() >= n) {
                        for (int i = 0; i < n; i++) {
                            ans.add(queue.poll());
                        }
                    }
                } finally {
                    queueLock.unlock();
                    if (ans.size() == n) {
                        return ans;
                    }
                }
                if (ans.size() < n) {
                    try {
                        pushWait.await();
                    } catch (InterruptedException ex) {
                        return null;
                    }
                }
            }
            return ans;
        } finally {
            popWait.signalAll();
            actionLock.unlock();
        }
    }

   public BlockingQueue(int maxSize) {
       maxQueueSize = maxSize;
       queue = new ArrayDeque<T>();
    }
}
