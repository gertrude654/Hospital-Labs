package com.hospital.HospitalMIS.producer;

// Coffee class representing the coffee object
public class Coffee {
    private final int id;

    public Coffee(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Coffee{" + "id=" + id + '}';
    }
}
