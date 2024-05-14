package com.java.threading.producer_consumer_problem.clients;

import com.java.threading.producer_consumer_problem.Queue;
import com.java.threading.producer_consumer_problem.clients.interfaces.QueueClient;

public class ConsumerClient implements QueueClient {

    String clientName;
    Queue queue;
    Long delayInMs;

    public ConsumerClient(String clientName, Queue queue, Long delayInMs) {
        this.queue = queue;
        this.clientName = clientName;
        this.delayInMs = delayInMs;
    }

    public void consume() throws InterruptedException {
        synchronized (queue) {
            int message = this.queue.pull();
            if(message == -1) {
                System.out.println(String.format("%s ---------- no message to consume, message detail - %s", clientName, message));
            } else {
                System.out.println(String.format("%s ---------- message consume, message detail - %s", clientName, message));
            }
            queue.notifyAll();
        }
    }


    @Override
    public void run() {
        try {
            while(true) {
                consume();
                Thread.sleep(delayInMs);
            }
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
