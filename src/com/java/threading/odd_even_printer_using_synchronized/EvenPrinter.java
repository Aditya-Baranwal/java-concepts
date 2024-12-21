package com.java.threading.odd_even_printer_using_synchronized;

public class EvenPrinter extends Thread {

    private Counter counter;

    public EvenPrinter(Counter ctr) {
        super();
        counter = ctr;
    }

    @Override
    public void run() {
        try {
            synchronized (counter) {
                while (true) {

                    if(counter.getCounter() == counter.getMAX_VALUE()) {
                        counter.notify();
                        break;
                    }

                    System.out.format("even counter checking\n");
                    if (counter.getCounter() % 2 == 1) {
                        counter.setCounter(counter.getCounter()+1);
                        System.out.format("even counter - %d\n", counter.getCounter());
                        counter.notify();
                    } else {
                        System.out.format("even counter waiting\n");
                        counter.wait();
                        System.out.format("even counter wait is over\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
