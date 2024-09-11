package com.hospital.HospitalMIS.producer;

// Producer class implementing Runnable
public class ProducerUsingWait implements Runnable {
    private final CoffeeShopUsingWait coffeeShop;

    public ProducerUsingWait(CoffeeShopUsingWait coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                CoffeeUsingWait coffee = new CoffeeUsingWait("Coffee " + i);
                coffeeShop.produce(coffee);
                Thread.sleep(100); // Simulate time taken to produce coffee
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
