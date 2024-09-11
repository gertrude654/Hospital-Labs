package com.hospital.HospitalMIS.producer;
// Shared resource (CoffeeShop) with wait/notify mechanism
public class CoffeeShopUsingWait {
    private final CoffeeUsingWait[] coffeeShop; // Buffer to hold coffee
    private int count = 0; // Number of items in the buffer
    private final int capacity; // Capacity of the coffee shop

    CoffeeShopUsingWait(int capacity) {
        this.coffeeShop = new  CoffeeUsingWait[capacity];
        this.capacity = capacity;
    }


    public synchronized void produce(CoffeeUsingWait coffee) throws InterruptedException {
        while (count == capacity) { // Buffer is full
            wait(); // Wait until a consumer consumes
        }
        coffeeShop[count++] = coffee; // Add coffee to the buffer
        System.out.println("Produced: " + coffee.getType());
        notify(); // Notify consumers that a new item is available
    }

    public synchronized CoffeeUsingWait consume() throws InterruptedException {
        while (count == 0) { // Buffer is empty
            wait(); // Wait until a producer produces
        }
        CoffeeUsingWait coffee = coffeeShop[--count]; // Remove coffee from the buffer
        System.out.println("Consumed: " + coffee.getType());
        notify(); // Notify producers that space is available
        return coffee;
    }
}
