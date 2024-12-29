package com.java.threading.async_programming;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * learning about future object, callable interface & completableFuture object
         */
        CompletableFutureObjectExamples();
    }

    private static void futureObjectExample() throws InterruptedException, ExecutionException {
        BusinessTaskWithRunnable bt1 = new BusinessTaskWithRunnable("bt1", 1000L);
        BusinessTaskWithRunnable bt2 = new BusinessTaskWithRunnable("bt2", 2000L);
        BusinessTaskWithRunnable bt3 = new BusinessTaskWithRunnable("bt3", 4000L);

        ExecutorService executors = Executors.newFixedThreadPool(5);
        /**
         * running 3 threads together
         * important to note that submit() method itself returns fututre object.
         */
        Future<?> task1 = executors.submit(bt1);
        Future<?> task2 = executors.submit(bt2);
        Future<?> task3 = executors.submit(bt3);

        /**
         * 1. waiting for tasks to get completed, and collecting result in future object.
         * 2. so futures are simply used to store result of thread or runnable instance.
         * 3. By defination :: " Future is a container for the result of an asynchronous task "
         * 4. future provides - get(), get(time, timeUnit), cancel(), isCancelled(), isDone()
         */
        task1.get();
        task2.get();
        task3.get();

        executors.shutdown();
    }

    private static void callableExample() throws InterruptedException, ExecutionException {
        BusinessTaskWithCallable bt1 = new BusinessTaskWithCallable("bt1", 1000L);
        BusinessTaskWithCallable bt2 = new BusinessTaskWithCallable("bt2", 2000L);
        BusinessTaskWithCallable bt3 = new BusinessTaskWithCallable("bt3", 4000L);

        ExecutorService executors = Executors.newFixedThreadPool(5);
        /** running 3 threads together */
        Future<Long> task1 = executors.submit(bt1);
        Future<Long> task2 = executors.submit(bt2);
        Future<Long> task3 = executors.submit(bt3);

        Long[] results = new Long[3];

        results[0] = task1.get();
        results[1] = task2.get();
        results[2] = task3.get();

        executors.shutdown();

        Long totalWaitTime = results[0]+results[1]+results[2];

        System.out.println("Total wait time = " + totalWaitTime);
    }

    private static void usingExecutorToReturnResultForRunnableInstances() throws InterruptedException, ExecutionException {

        BusinessTaskWithRunnable bt1 = new BusinessTaskWithRunnable("bt1", 1000L);
        BusinessTaskWithRunnable bt2 = new BusinessTaskWithRunnable("bt2", 2000L);
        BusinessTaskWithRunnable bt3 = new BusinessTaskWithRunnable("bt3", 4000L);

        ExecutorService executors = Executors.newFixedThreadPool(5);

        /**
         * Here we are passing object as second param, to which executor simply return's
         * this object is also accessable by runnable instance, so runnable instance can update value for that object
         * this way we can return a value even from runnable instance
         */
        Future<String> task1Output = executors.submit(bt1, bt1.getName());
        Future<String> task2Output = executors.submit(bt2, bt2.getName());
        Future<String> task3Output = executors.submit(bt3, bt3.getName());

        String[] result = new String[3];

        result[0] = task1Output.get();
        result[1] = task2Output.get();
        result[2] = task3Output.get();

        executors.shutdown();

        System.out.format("printing returned value after execution of runnable instances = %s -- %s -- %s\n", result[0], result[1], result[2]);
    }

    private static void CompletableFutureObjectExamples() throws InterruptedException, ExecutionException {
        /**
         * 1. callable + future is very similar to promise object in javascript
         *    as javascript provides various method, such as promise.all, promise.any, promise.allSettled ... inorder to manage code flow with async task
         *    similarly, "CompletableFuture" in java provides same functionality
         * 2. " CompletableFuture is a building block and a framework for composing, combining, and executing asynchronous computation steps and handling errors "
         * 3. CompletableFuture is class which implements Future and CompletionStage interface.
         * 4.
         */

        /**
         * 1. CompletableFuture Method's accept supplier instance.
         * 2. supplyAsync executes the supplier instance in different thread, we can pass executor, by default it make use of Fork-Join Executor.
         * 3. CompletableFututre provides various method such as
         *
         * supplyAsync      runAsync
         * get              join
         * thenApply        thenApplyAsync     --> the Apply functions apply another function and returns value to next method
         * thenAccept       thenAcceptAsync    --> the Accept functions consume this value and returns "void" to next method
         * thenCombine      thenCombineAsync   --> is useful to chain two independent CompletableFuture instances
         * thenCompose      thenComposeAsync   --> is useful to chain two dependent CompletableFuture instances
         * handle           handleAsync        --> https://www.baeldung.com/java-exceptions-completablefuture
         * exceptionally    exceptionallyAsync --> https://www.baeldung.com/java-exceptions-completablefuture
         * cancel ... there are a lot of methods
         */

        BusinessTaskWithSupplier bt1 = new BusinessTaskWithSupplier("bt1", 1000L);
        BusinessTaskWithSupplier bt2 = new BusinessTaskWithSupplier("bt2", 2000L);
        BusinessTaskWithSupplier bt3 = new BusinessTaskWithSupplier("bt3", 4000L);

        /** Example 1 */
        CompletableFuture<Long> totalProcessingTimeFuture1 = CompletableFuture
                .supplyAsync(bt1)
                .thenApply(processingTime -> processingTime + bt2.get())
                .thenApply(processingTime -> processingTime + bt3.get());
        Long totalProcessingTime = totalProcessingTimeFuture1.get();

        /** prints sum of result return by each supplier instances which is 1000+2000+4000 = 7000 */
        System.out.println(totalProcessingTime);

        /** Example 2 */
        CompletableFuture<Long> task1future = CompletableFuture.supplyAsync(bt1);
        CompletableFuture<Long> task2future = CompletableFuture.supplyAsync(bt2);
        CompletableFuture<Long> task3future = CompletableFuture.supplyAsync(bt3);

        CompletableFuture<List<Long>> allTaskFutures1 = CompletableFuture.allOf(
                task1future,
                task2future,
                task3future
        ).thenApply(Void -> Stream
                .of(task1future, task2future, task3future)
                .map(CompletableFuture::join)
                .collect(Collectors.toList())
        );

        totalProcessingTime = Collections.max(allTaskFutures1.get());

        /** prints max of result return by each supplier instances which is max.of(1000, 2000, 4000) = 4000 */
        System.out.println(totalProcessingTime);

        /** Example 3 */
        CompletableFuture<Long> task4future = CompletableFuture.supplyAsync(bt1);
        CompletableFuture<Long> task5future = CompletableFuture.supplyAsync(bt2);
        CompletableFuture<Long> task6future = CompletableFuture.supplyAsync(bt3);

        CompletableFuture<Object> allTaskFutures2 = CompletableFuture.anyOf(
                task4future,
                task5future,
                task6future
        );

        totalProcessingTime = (Long) allTaskFutures2.get();

        /** prints min of result return by each supplier instances which is min.of(1000, 2000, 4000) = 1000 */
        System.out.println(totalProcessingTime);
    }

}
