package com.ejemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderServiceTest {

    private final OrderService service = new OrderService();

    @Test
    public void testWithoutDiscountAndStandardShipment() {
        double result = service.getTotal(100.0, false, false);
        assertEquals(110.0, result, 0.001);
    }

    @Test
    public void testWithDiscountAndStandardShipment() {
        double result = service.getTotal(100.0, true, false);
        assertEquals(100.0, result, 0.001);  // 100 - 10 + 10
    }

    @Test
    public void testWithoutDiscountAndExpressShipment() {
        double result = service.getTotal(100.0, false, true);
        assertEquals(120.0, result, 0.001);
    }

    @Test
    public void testWithDiscountAndExpressShipment() {
        double result = service.getTotal(100.0, true, true);
        assertEquals(110.0, result, 0.001); // 100 - 10 + 20
    }

    @Test
    public void testZeroSubtotal() {
        double result = service.getTotal(0.0, true, true);
        assertEquals(20.0, result, 0.001); // solo el costo de envío
    }
    
    /// este test no es correcto, ya que el subtotal no puede ser negativo
    /// este test debería dar una excepción o un error
    // @Test
    // public void testNegativeSubtotal() {
    //     double result = service.getTotal(-100.0, true, false);
    //     assertEquals(-100.0, result, 0.001); // comportamiento tal como está definido
    // }
    @Test
    public void testNegativeSubtotal() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.getTotal(-100.0, true, false));
        assertEquals("Subtotal no puede ser negativo", exception.getMessage());
    }
}