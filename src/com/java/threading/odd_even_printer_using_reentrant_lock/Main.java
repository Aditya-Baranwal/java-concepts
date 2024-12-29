package com.java.threading.odd_even_printer_using_reentrant_lock;

import com.java.threading.producer_consumer_problem.clients.interfaces.QueueClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {

        Counter counter = new Counter(100);

        /**
         * re-entrant lock is custom lock from java.util.concurrent.locks package,
         * it helps us to overcome issues faced by monitor_lock (synchronized lock)
         * 1. provides control on acquiring and releasing a lock
         * 2. provides tryLock() method, which helps thread from getting into wait state for entire life
         * 3. lock is not on shared resource object, but lock object is used for synchronization
        */
        ReentrantLock lock = new ReentrantLock();

        /** creating even printer */
        EvenPrinter ep1 = new EvenPrinter(counter, lock);

        /** creating odd printer */
        OddPrinter op1 = new OddPrinter(counter, lock);

        ArrayList<Printer> printers = new ArrayList<>(Arrays.asList(ep1, op1));

        /** submitting printers for execution */
        ExecutorService executors = Executors.newFixedThreadPool(2);
        printers.forEach(printer -> executors.submit(printer));
        executors.shutdown();

    }
}
