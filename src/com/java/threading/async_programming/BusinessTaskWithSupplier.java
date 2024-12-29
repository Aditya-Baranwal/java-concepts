package com.java.threading.async_programming;

import java.util.function.Supplier;

public class BusinessTaskWithSupplier implements Supplier<Long> {

    String name;
    Long time;

    BusinessTaskWithSupplier(String name, Long time) {
        this.name = name;
        this.time = time;
    }

    @Override
    public Long get() {
        try {
            System.out.format("Business Task - %s, started\n", name);
            Thread.sleep(this.time);
            System.out.format("Business Task - %s, completed\n", name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return time;
    }
}
