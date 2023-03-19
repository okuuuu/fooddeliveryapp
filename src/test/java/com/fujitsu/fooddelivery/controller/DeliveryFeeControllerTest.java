package com.fujitsu.fooddelivery.controller;

import com.fujitsu.fooddelivery.service.DeliveryFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// A test class for the DeliveryFeeController, which uses Spring REST Docs to generate
// documentation snippets based on the test cases. It tests the /delivery-fee endpoint
// to ensure that it returns the correct responses for different input parameters.
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(DeliveryFeeController.class)
public class DeliveryFeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryFeeService deliveryFeeService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void testGetDeliveryFee() throws Exception {
        BigDecimal exampleDeliveryFee = BigDecimal.valueOf(4.0);
        String city = "Tallinn";
        String vehicle = "Car";

        when(deliveryFeeService.calculateDeliveryFee(city, vehicle)).thenReturn(exampleDeliveryFee);

        RestDocumentationResultHandler document = document("get-delivery-fee",
                RequestDocumentation.queryParameters(
                        RequestDocumentation.parameterWithName("city")
                                .description("City name: Tallinn, Tartu, or Pärnu"),
                        RequestDocumentation.parameterWithName("vehicle")
                                .description("Vehicle: Car, Scooter, or Bike")),
                PayloadDocumentation.responseBody());

        mockMvc.perform(MockMvcRequestBuilders.get("/delivery-fee")
                        .param("city", city)
                        .param("vehicle", vehicle))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(exampleDeliveryFee)))
                .andDo(document);
    }
}

