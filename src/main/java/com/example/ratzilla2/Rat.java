package com.example.ratzilla2;

public class Rat {
    private int energy;
    private int satiety;
    String name;

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    Rat(String name) {
        this.energy = (int) (Math.random() * 90 + 10);
        this.satiety = (int) (Math.random() * 90 + 10);
        this.name = name;
    }

    public void decreaseEnergy() {
        energy = Math.max(0, energy - 1);
    }

    public void decreaseSatiety() {
        satiety = Math.max(0, satiety - 1);
    }

}
