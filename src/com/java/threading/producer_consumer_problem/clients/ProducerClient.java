package com.java.threading.producer_consumer_problem.clients;

import com.java.threading.producer_consumer_problem.Queue;
import com.java.threading.producer_consumer_problem.clients.interfaces.QueueClient;

import java.util.Random;

public class ProducerClient implements QueueClient {

    String clientName;
    Queue queue;
    Long delayInMs;

    public ProducerClient(String clientName, Queue queue, Long delayInMs) {
        this.queue = queue;
        this.clientName = clientName;
        this.delayInMs = delayInMs;
    }

    public void produce() {
        Random random = new Random();
        int min = 1, max = 10;
        int randomNum = random.nextInt((max - min) + 1) + min;
        synchronized (queue) {
            int temp = this.queue.push(randomNum);
            if(temp == -1) {
                System.out.println(String.format("%s ---------- queue is full, could not push message - %d", clientName, randomNum));
            } else {
                System.out.println(String.format("%s ---------- pushed message %d to queue at position %d", clientName, randomNum, temp));
            }
            queue.notifyAll();
        }
    }

    @Override
    public void run() {
        try {
            while(true) {
                produce();
                Thread.sleep(delayInMs);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
