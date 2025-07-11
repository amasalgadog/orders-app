package com.ejemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceMockTest {
    
    @Test
    void testWithMockDiscount(){
        // Arrange
        // se crea un mock (objeto simulado) de la clase DiscountService
        DiscountService buster = mock(DiscountService.class);
        // se define el comportamiento del mock
        // when indica que cuando se llame al método getRate con el argumento "SALES10", se devolverá 0.10 (thenReturn)
        when(buster.getRate("SALES10")).thenReturn(0.10);
        // se crea una instancia de OrderService con el mock
        // esto permite que OrderService use el mock en lugar de una implementación real
        OrderService service = new OrderService(buster);

        // Act
        double total = service.getTotal(100.0, "SALES10", true);
        
        // Assert
        assertEquals(110.0, total);
         // 100 - 10 + 20
    }
}
