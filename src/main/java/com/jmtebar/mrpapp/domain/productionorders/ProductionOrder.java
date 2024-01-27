package com.jmtebar.mrpapp.domain.productionorders;

import com.jmtebar.mrpapp.ValidationException;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ProductionOrder {

    @Id
    private Long id;
    private String name;
    private LocalDate expectedCompletionDate;
    private ProductionOrderState state;


    public enum ProductionOrderState {DRAFT, SUBMITTED, ACCEPTED, COMPLETED}

    public static ProductionOrder create(String name) throws Exception {
        Objects.requireNonNull(name, "Order name is mandatory");

        ProductionOrder order = new ProductionOrder();
        order.name = name;
        order.state = ProductionOrderState.DRAFT;
        return order;
    }

    public ProductionOrder renameTo(String newName) {
        Objects.requireNonNull(newName, "New name is mandatory");

        if (state != ProductionOrderState.DRAFT) {
            throw new IllegalStateException("Cannot rename production order in state " + state);
        }

        this.name = newName;

        return this;
    }

    public ProductionOrder submit() {
        if (state != ProductionOrderState.DRAFT) {
            throw new IllegalStateException("Cannot submit order in state " + state);
        }

        this.state = ProductionOrderState.SUBMITTED;

        return this;
    }

    public ProductionOrder accept(LocalDate expectedCompletionDate) {

        if (state != ProductionOrderState.SUBMITTED) {
            throw new IllegalStateException("Cannot accept production order in state " + state);
        }

        Objects.requireNonNull(expectedCompletionDate, "expectedCompletionDate is required to submit a production order");
        if (expectedCompletionDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expected completion date must be in the future, but was " + expectedCompletionDate);
        }
        state = ProductionOrderState.ACCEPTED;
        this.expectedCompletionDate = expectedCompletionDate;
        return this;
    }

}
