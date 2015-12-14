package ru.fizteh.fivt.students.andrewgark.Threads;

import org.junit.Assert;
import org.junit.Test;
import java.util.*;

public class BlockingQueueTest {
    @Test
    public void testSimpleWithoutThreads() throws Exception {
        BlockingQueue queue = new BlockingQueue<Integer>(40);
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(3);
        list1.add(14);
        list1.add(1);
        ArrayList<Integer> list2 = new ArrayList();
        list2.add(3);
        list2.add(14);
        ArrayList<Integer> list3 = new ArrayList();
        list2.add(5);
        list2.add(6);
        ArrayList<Integer> list4 = new ArrayList();
        list2.add(1);
        list2.add(5);
        list2.add(6);
        queue.offer(list1);
        List<Integer> answer1 = queue.take(2);
        queue.offer(list3);
        List<Integer> answer2 = queue.take(3);
        Assert.assertEquals(answer1, list2);
        Assert.assertEquals(answer2, list4);
    }
}
