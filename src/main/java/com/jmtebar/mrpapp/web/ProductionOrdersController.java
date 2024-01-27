package com.jmtebar.mrpapp.web;

import com.jmtebar.mrpapp.domain.productionorders.ProductionOrder;
import com.jmtebar.mrpapp.domain.productionorders.ProductionOrders;
import lombok.NonNull;
import lombok.Value;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("api/productionOrders")
public class ProductionOrdersController implements RepresentationModelProcessor<EntityModel<ProductionOrder>> {

    public static final String REL_RENAME = "rename";

    private final Logger logger = (Logger) LoggerFactory.getLogger(ProductionOrdersController.class);

    @Autowired
    private ProductionOrders productionOrders;

    @PostMapping("/{id}/rename")
    public ResponseEntity<?> rename(@PathVariable Long id, @RequestBody RenameRequest request) {

        try {
            return productionOrders.findById(id).map(po -> po.renameTo(request.getNewName())).map(po -> ResponseEntity.ok().body(EntityModel.of(po)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (NullPointerException npe) {
            logger.error("BOOOM!");
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public EntityModel<ProductionOrder> process(EntityModel<ProductionOrder> model) {
        ProductionOrder order = model.getContent();
        model.add(linkTo(methodOn(getClass()).rename(order.getId(), null)).withRel(REL_RENAME));

        return model;
    }

    @Value
    static class RenameRequest {
        @NonNull String newName;
    }

}
