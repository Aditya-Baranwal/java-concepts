package com.java.threading.odd_even_printer_using_reentrant_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class OddPrinter  implements Printer {

    Counter counter;
    ReentrantLock lock;

    OddPrinter(Counter counter, ReentrantLock lock) {
        this.counter = counter;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(lock.tryLock(2000, TimeUnit.MILLISECONDS)) {
                    if(counter.getCount() >= counter.getMAX_COUNT_VALUE()) break;
                    if(counter.getCount()%2 == 0) {
                        counter.setCount(counter.getCount() + 1);
                        System.out.format("odd printer is printing %d\n", counter.getCount());
                    }
                    lock.unlock();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
