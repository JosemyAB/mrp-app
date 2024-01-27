package com.jmtebar.mrpapp.productionorders;

import com.jmtebar.mrpapp.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductionOrderTest {

    @Test
    void createsAProductionOrder() throws Exception {
        String orderName = "test-order";
        ProductionOrder order = ProductionOrder.create(orderName);

        assertEquals(orderName, order.getName());
        assertEquals(ProductionOrder.ProductionOrderState.DRAFT, order.getState());
        assertNull(order.getExpectedCompletionDate());
    }

  @Test
  void validatesNameIsPresent() {
      ValidationException ex = assertThrows(ValidationException.class, () -> ProductionOrder.create(null));
      assertEquals("Order name is mandatory", ex.getMessage());
  }
}
