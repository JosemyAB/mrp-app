package com.jmtebar.mrpapp.domain.productionorders;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ProductionOrdersService {

    Optional<ProductionOrder> rename(Long id, String newName);

}
