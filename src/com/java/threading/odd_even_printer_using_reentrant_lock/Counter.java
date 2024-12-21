package com.java.threading.odd_even_printer_using_reentrant_lock;

public class Counter {

    Integer count = 0;
    private Integer MAX_COUNT_VALUE;

    Counter(Integer maxCountValue) {
        this.MAX_COUNT_VALUE = maxCountValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMAX_COUNT_VALUE() {
        return MAX_COUNT_VALUE;
    }

}
