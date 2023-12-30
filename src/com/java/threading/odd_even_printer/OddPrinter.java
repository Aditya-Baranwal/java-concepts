package com.java.threading.odd_even_printer;

public class OddPrinter extends Thread {

    private Counter counter;

    public OddPrinter(Counter ctr) {
        super();
        counter = ctr;
    }

    @Override
    public void run() {
        try {
            synchronized(counter) {
                while(true) {

                    if(counter.getCounter() == counter.getMAX_VALUE()) {
                        counter.notify();
                        break;
                    }

                    System.out.format("odd counter checking\n");
                    if (counter.getCounter() % 2 == 0) {
                        counter.setCounter(counter.getCounter()+1);
                        System.out.format("odd counter - %d\n", counter.getCounter());
                        counter.notify();
                    } else {
                        System.out.format("odd counter waiting\n");
                        counter.wait();
                        System.out.format("odd counter wait is over\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
