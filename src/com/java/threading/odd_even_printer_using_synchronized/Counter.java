package com.java.threading.odd_even_printer_using_synchronized;

class Counter {

    private volatile int counter = 0;
    private volatile int MAX_VALUE;

    public Counter(int maxValue) {
        MAX_VALUE = maxValue;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getMAX_VALUE() {
        return MAX_VALUE;
    }

    public void setMAX_VALUE(int MAX_VALUE) {
        this.MAX_VALUE = MAX_VALUE;
    }

}
