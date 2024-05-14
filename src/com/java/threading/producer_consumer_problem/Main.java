package com.java.threading.producer_consumer_problem;

import com.java.threading.producer_consumer_problem.clients.ConsumerClient;
import com.java.threading.producer_consumer_problem.clients.ProducerClient;
import com.java.threading.producer_consumer_problem.clients.interfaces.QueueClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Queue queue = new Queue(10);

        ProducerClient p1 = new ProducerClient("p1", queue, 1000L);
        ProducerClient p2 = new ProducerClient("p2", queue, 1500L);
        ProducerClient p3 = new ProducerClient("p3", queue, 2000L);

        ConsumerClient c1 = new ConsumerClient("c1", queue, 500L);
        ConsumerClient c2 = new ConsumerClient("c2", queue, 1000L);

        ArrayList<QueueClient> clients = new ArrayList<>(Arrays.asList(p1, p2, p3, c1, c2));

        ExecutorService executors = Executors.newFixedThreadPool(5);
        clients.forEach(client -> executors.submit(client));
        executors.shutdown();

    }
}
