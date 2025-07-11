package com.ejemplo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    // Arrange: preparar los objetos y valores necesarios para la prueba.
    private DiscountService repository;
    private OrderService service;

    @BeforeEach
    public void setUp() {
        // Inicializar el servicio de descuentos simulado
        repository = mock(DiscountService.class);
        // Crear una instancia del servicio de órdenes con el mock
        service = new OrderService(repository);
    }

    @Test
    public void testWithoutDiscountAndStandardShipment() {
        // Act: ejecutar el método o acción que se quiere probar.
        when(repository.getRate(null)).thenReturn(0.0);
        double result = service.getTotal(100.0, null, false);
        // Assert: verificar que el resultado es el esperado.
        assertEquals(110.0, result, 0.001);
    }

    @Test
    public void testWithDiscountAndStandardShipment() {
        when(repository.getRate("SALES10")).thenReturn(0.10);
        double result = service.getTotal(100.0, "SALES10", false);
        assertEquals(100.0, result, 0.001);  // 100 - 10 + 10
    }

    @Test
    public void testWithDiscountAndExpressShipment() {
        when(repository.getRate("SALES10")).thenReturn(0.10);

        double result = service.getTotal(200.0, "SALES10", true);
        assertEquals(200.0 * 0.9 + 20.0, result, 0.001); // 180 + 20 = 200
    }

    @Test
    public void testWithUnknownDiscountCode() {
        when(repository.getRate("UNKNOWN")).thenReturn(0.0);

        double result = service.getTotal(50.0, "UNKNOWN", true);
        assertEquals(50.0 + 20.0, result, 0.001); // sin descuento
    }

    @Test
    public void testZeroSubtotal() {
        when(repository.getRate("SALES10")).thenReturn(0.10);

        double result = service.getTotal(0.0, "SALES10", false);
        assertEquals(10.0, result, 0.001); // solo envío estándar
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
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> service.getTotal(-100.0, "", 
            false));
        assertEquals("Subtotal no puede ser negativo", exception.getMessage());
    }
}