package com.java.threading.producer_consumer_problem;

public class Queue {

    int size;
    int[] buffer;
    int pushPointer = -1;
    int pullPointer = -1;

    public Queue(int queueSize) {
        this.size = queueSize;
        buffer = new int[queueSize];
    }

    public int push(int value) {
        if(pullPointer <= pushPointer) {
            if(pushPointer == size-1) {
                if(pullPointer == 0) return -1;
                pushPointer = 0;
                buffer[pushPointer] = value;
                return pushPointer;
            }
            buffer[++pushPointer] = value;
        } else {
            if(pushPointer+1 == pullPointer) return -1;
            buffer[++pushPointer] = value;
        }
        return pushPointer;
    }

    public int pull() {
        if(pushPointer == -1 || pullPointer == pushPointer) return -1;
        if(pullPointer == size-1) {
            pullPointer = 0;
            return buffer[pullPointer];
        }
        return buffer[++pullPointer];
    }


}
