package com.ejemplo;

public class OrderService {
    public double getTotal(double subtotal, boolean hasDiscount, boolean hasExpressShipment){
        // sin esta parte del código, el test de OrderServiceTest.java falla
        // este el error que se produce:
        // OrderServiceTest.testNegativeSubtotal:50 Expected java.lang.IllegalArgumentException to be thrown, but nothing was thrown.

        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal no puede ser negativo");
        }
        // Fin de la parte del código que evita el error en el test

        double discount = hasDiscount ? 0.10 : 0;
        double shipment = hasExpressShipment ? 20.0 : 10.0;
        return (subtotal * (1 - discount)) + shipment;
    }
}