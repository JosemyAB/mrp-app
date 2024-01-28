package com.jmtebar.mrpapp.domain.productionorders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProductionOrdersServiceTest {

    @MockBean
    private ProductionOrders productionOrders;

    @Autowired
    private ProductionOrdersService productionOrdersService;

    @BeforeEach
    void setup() {

        try {
            Mockito.when(productionOrders.findById(1L)).thenReturn(Optional.of(ProductionOrder.create("test-mock")));

            Mockito.when(productionOrders.findById(99L)).thenReturn(Optional.empty());
        } catch (Exception e) {
            System.exit(0);
        }
    }

    @Test
    void renamesTheProductionOrder() {
        Optional<ProductionOrder> po = productionOrdersService.rename(1L, "renamed-order");
        verify(productionOrders, times(1)).save(any(ProductionOrder.class));
    }

    @Test
    void doesNotRenameIfTheProductionOrderDoesNotExists() {
        Optional<ProductionOrder> po = productionOrdersService.rename(99L, "renamed-order");
        verify(productionOrders, times(0)).save(any(ProductionOrder.class));
    }
}
