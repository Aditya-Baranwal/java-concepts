package com.java.lock_using_synchronized;

public class Counter {

    private int count;

    public synchronized void increase() throws InterruptedException {
        ++count;
        System.out.format("Thread Id - %d, count value - %d", Thread.currentThread().getId(), count);
    }

    public synchronized void decrease() throws InterruptedException {
        ++count;
        System.out.format("Thread Id - %d, count value - %d", Thread.currentThread().getId(), count);
    }

    public synchronized int getValue() {
        return count;
    }

}
