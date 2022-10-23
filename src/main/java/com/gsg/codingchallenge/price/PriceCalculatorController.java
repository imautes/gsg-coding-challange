package com.gsg.codingchallenge.price;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/price-calculator/net-price")
@Validated
@RequiredArgsConstructor
public class PriceCalculatorController {
    private final PriceCalculatorService priceCalculatorService;

    @GetMapping("/{grossPrice}/{countryIso}")
    public ResponseEntity<BigDecimal> calculateNetPrice(@NotNull @PathVariable BigDecimal grossPrice,
                                                        @NotNull @PathVariable String countryIso) {
        return priceCalculatorService.calculateNetPrice(grossPrice, countryIso)
                .map(ResponseEntity::ok)
                .orElseThrow(TaxRateNotFoundException::new);
    }
}
