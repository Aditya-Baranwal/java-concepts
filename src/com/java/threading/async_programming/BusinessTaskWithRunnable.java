package com.java.threading.async_programming;

public class BusinessTaskWithRunnable implements Runnable {

    String name;
    Long time;

    BusinessTaskWithRunnable(String name, Long time) {
        this.name = name;
        this.time = time;
    }

    /**
     * Note that, return type of runnable is void.
     * It mean that threads create with runnable interface returns nothing
     * what if we want to return some value once thread complete's its execution ?
     * for that we have callable interface
     */
    @Override
    public void run() {
        try {
            System.out.format("Business Task - %s, started\n", name);
            Thread.sleep(this.time);
            System.out.format("Business Task - %s, completed\n", name);
        } catch (Exception e) {

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
