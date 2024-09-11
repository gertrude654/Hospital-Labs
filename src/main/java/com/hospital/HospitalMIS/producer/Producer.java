package com.hospital.HospitalMIS.producer;


import java.util.concurrent.BlockingQueue;

// Producer class that produces coffee
public class Producer implements Runnable {
    private final BlockingQueue<Coffee> queue;

    public Producer(BlockingQueue<Coffee> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) { // Produce 10 cups of coffee
                Coffee coffee = new Coffee(i);
                System.out.println("Producing " + coffee);
                queue.put(coffee); // Blocks if the queue is full
                Thread.sleep(500); // Simulate time taken to make coffee
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted state
        }
    }
}