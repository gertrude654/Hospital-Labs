package com.hospital.HospitalMIS.producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Main class to run the producer-consumer scenario
public class CoffeeShop {
    public static void main(String[] args) {
        BlockingQueue<Coffee> queue = new ArrayBlockingQueue<>(5); // Bounded queue with capacity of 5

        // Create producer and consumer threads
        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));

        // Start the threads
        producerThread.start();
        consumerThread.start();

        // Wait for the threads to finish
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted state
        }

        System.out.println("Coffee shop is closed.");
    }
}
