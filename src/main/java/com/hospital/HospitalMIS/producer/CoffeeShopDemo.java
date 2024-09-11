package com.hospital.HospitalMIS.producer;

// Main class to run the producer-consumer scenario
public class CoffeeShopDemo {
    public static void main(String[] args) {
        CoffeeShopUsingWait coffeeShop = new CoffeeShopUsingWait(5); // Bounded capacity of 5

        // Create producer and consumer threads
        Thread producerThread = new Thread(new ProducerUsingWait(coffeeShop));
        Thread consumerThread = new Thread(new ConsumerUsingWait(coffeeShop));

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
