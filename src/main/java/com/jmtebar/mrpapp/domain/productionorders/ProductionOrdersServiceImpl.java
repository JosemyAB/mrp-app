package com.jmtebar.mrpapp.domain.productionorders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductionOrdersServiceImpl implements ProductionOrdersService {

    @Autowired
    private ProductionOrders productionOrders;

    @Override
    public Optional<ProductionOrder> rename(Long id, String newName) {
        return productionOrders.findById(id).map(po -> productionOrders.save(po.renameTo(newName)));
    }
}
