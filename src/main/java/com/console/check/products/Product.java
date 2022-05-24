package com.console.check.products;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return qua == product.qua && Double.compare(product.cost, cost) == 0 && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qua, name, cost);
    }
}
