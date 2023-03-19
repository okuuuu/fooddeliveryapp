package com.fujitsu.fooddelivery.controller;

import com.fujitsu.fooddelivery.service.DeliveryFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

// A REST controller that handles HTTP requests for calculating the delivery fee.
// Exposes the /delivery-fee endpoint, which accepts city and vehicle as
// input parameters and returns the calculated delivery fee or an error message.
@RestController
public class DeliveryFeeController {
    @Autowired
    private DeliveryFeeService deliveryFeeService;

    @GetMapping("/delivery-fee")
    public ResponseEntity<Object> getDeliveryFee(@RequestParam("city") String city,
                                                 @RequestParam("vehicle") String vehicle) {
        try {
            BigDecimal deliveryFee = deliveryFeeService.calculateDeliveryFee(city, vehicle);
            return ResponseEntity.ok(deliveryFee);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
