package com.hospital.HospitalMIS.producer;

import java.util.concurrent.BlockingQueue;

// Consumer class that consumes coffee
class Consumer implements Runnable {
    private final BlockingQueue<Coffee> queue;

    public Consumer(BlockingQueue<Coffee> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) { // Consume 10 cups of coffee
                Coffee coffee = queue.take(); // Blocks if the queue is empty
                System.out.println("Consuming " + coffee);
                Thread.sleep(700); // Simulate time taken to drink coffee
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted state
        }
    }
}