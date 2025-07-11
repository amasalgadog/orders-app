package com.ejemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {
    // Arrange: preparar los objetos y valores necesarios para la prueba.
    private final OrderService service = new OrderService();

    @Test
    public void testWithoutDiscountAndStandardShipment() {
        // Act: ejecutar el método o acción que se quiere probar.
        double result = service.getTotal(100.0, false, false);
        // Assert: verificar que el resultado es el esperado.
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

    // Este test es una excepción, el Arrange no es necesario aquí porque no hay preparación previa
    @Test
    public void testNegativeSubtotal() {
        // Act & Assert
        // Verificamos que se lanza una IllegalArgumentException cuando el subtotal es negativo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.getTotal(-100.0, true, false));
        assertEquals("Subtotal no puede ser negativo", exception.getMessage());
    }
}