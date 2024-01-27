package com.jmtebar.mrpapp.productionorders;

import com.jmtebar.mrpapp.ValidationException;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;

import java.util.Date;

@Getter
public class ProductionOrder {

    @Id
    private Long id;
    private String name;
    private Date expectedCompletionDate;
    private ProductionOrderState state;

    public enum ProductionOrderState {DRAFT, SUBMITTED, ACCEPTED, COMPLETED}

    public static ProductionOrder create(String name) throws Exception {

        if (!StringUtils.hasLength(name)) {
            throw new ValidationException("Order name is mandatory");
        }

        ProductionOrder order = new ProductionOrder();
        order.name = name;
        order.state = ProductionOrderState.DRAFT;
        return order;
    }
}
