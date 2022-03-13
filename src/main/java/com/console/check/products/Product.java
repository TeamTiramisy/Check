package com.console.check.products;

public class Product {
    private int qua;
    private String name;
    private double cost;

    public Product(int qua, String name, double cost) {
        this.qua = qua;
        this.name = name;
        this.cost = cost;
    }

    public int getQua() {
        return qua;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}
