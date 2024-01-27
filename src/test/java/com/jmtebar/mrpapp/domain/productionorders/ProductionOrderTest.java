package com.jmtebar.mrpapp.domain.productionorders;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

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
    void validatesNameIsPresentOnCreate() {
        Exception ex = assertThrows(NullPointerException.class, () -> ProductionOrder.create(null));
        assertEquals("Order name is mandatory", ex.getMessage());
    }

    @Test
    void renamesProductionOrder() throws Exception {
        String newName = "renamed-order";
        ProductionOrder order = ProductionOrder.create("test-order");
        assertEquals(newName, order.renameTo(newName).getName());
    }

    @Test
    void validatesNameIsPresentOnRename() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");

        Exception ex = assertThrows(NullPointerException.class, () -> order.renameTo(null));
        assertEquals("New name is mandatory", ex.getMessage());
    }

    @Test
    void validatesStatusIsDraftOnRename() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");
        setPrivateField(order, "state", ProductionOrder.ProductionOrderState.ACCEPTED);

        assertThrows(IllegalStateException.class, () -> order.renameTo("invalid-status"));
    }

    @Test
    void submitsProductionOrder() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");
        assertEquals(ProductionOrder.ProductionOrderState.SUBMITTED, order.submit().getState());
    }

    @Test
    void validatesStatusIsDraftOnSubmit() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");
        setPrivateField(order, "state", ProductionOrder.ProductionOrderState.ACCEPTED);

        assertThrows(IllegalStateException.class, order::submit);
    }


    @Test
    void acceptsProductionOrder() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");
        setPrivateField(order, "state", ProductionOrder.ProductionOrderState.SUBMITTED);
        assertEquals(ProductionOrder.ProductionOrderState.ACCEPTED, order.accept(LocalDate.now().plusDays(10)).getState());
    }

    @Test
    void validatesStatusIsSubmittedOnAccept() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");
        setPrivateField(order, "state", ProductionOrder.ProductionOrderState.SUBMITTED);

        assertThrows(IllegalStateException.class, order::submit);
    }

    @Test
    void validatesExpectedDateIsInFutureOnAccept() throws Exception {
        ProductionOrder order = ProductionOrder.create("test-order");
        setPrivateField(order, "state", ProductionOrder.ProductionOrderState.SUBMITTED);

        assertThrows(IllegalArgumentException.class, () -> order.accept(LocalDate.now().minusDays(1)));
    }


    private void setPrivateField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set private field " + fieldName, e);
        }
    }
}
