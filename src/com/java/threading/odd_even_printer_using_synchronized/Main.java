package com.java.threading.odd_even_printer_using_synchronized;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String args[]) {

        // creating a counter
        Counter counter = new Counter(100);
        // creating a thread which will print odd number.
        OddPrinter oddPrinter = new OddPrinter(counter);
        // creating a thread which will print even number
        EvenPrinter evenPrinter = new EvenPrinter(counter);

        ExecutorService executors = Executors.newFixedThreadPool(5);
        executors.submit(oddPrinter);
        executors.submit(evenPrinter);
        executors.shutdown();

    }

}




