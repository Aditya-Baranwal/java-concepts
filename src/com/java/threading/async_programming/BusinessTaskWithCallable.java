package com.java.threading.async_programming;

import java.util.concurrent.Callable;

public class BusinessTaskWithCallable implements Callable<Long> {

    String name;
    Long time;

    BusinessTaskWithCallable(String name, Long time) {
        this.name = name;
        this.time = time;
    }

    /**
     * so here comes, callable it has a return type.
     * currently, it returns a Long value which indicates wait/processing time for thread;
     * " Callable interfaces is used for defining tasks that can be executed asynchronously. Callable can return results or throw exceptions "
     * It's not used to create threads as we do with runnable
     */
    @Override
    public Long call() throws Exception {
        System.out.format("Business Task With Callable - %s, started\n", name);
        Thread.sleep(this.time);
        System.out.format("Business Task With Callable- %s, completed\n", name);
        return time;
    }

}
