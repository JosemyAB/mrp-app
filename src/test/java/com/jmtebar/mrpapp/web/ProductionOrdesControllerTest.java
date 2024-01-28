package com.jmtebar.mrpapp.web;

import com.jmtebar.mrpapp.domain.productionorders.ProductionOrder;
import com.jmtebar.mrpapp.domain.productionorders.ProductionOrdersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.PropertiesMongoConnectionDetails;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductionOrdesControllerTest {

    @MockBean
    private ProductionOrdersService productionOrdersService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnDefaultMessage() throws Exception {
        Mockito.when(productionOrdersService.rename(1L, "rename")).thenReturn(Optional.of(ProductionOrder.create("rename")));

        this.mockMvc.perform(post("/api/productionOrders/1/rename").param("newName", "renamed"));

//        .andDo(print())
//                .andExpect(status().isOk()).andExpect(content().string(containsString("test")));

    }
}
