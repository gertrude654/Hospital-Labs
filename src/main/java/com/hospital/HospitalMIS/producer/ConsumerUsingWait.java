package com.hospital.HospitalMIS.producer;


// Consumer class implementing Runnable
public class ConsumerUsingWait implements Runnable {
    private final CoffeeShopUsingWait coffeeShop;

    public ConsumerUsingWait(CoffeeShopUsingWait coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 100; i++) {
                coffeeShop.consume();
                Thread.sleep(150); // Simulate time taken to consume coffee
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}