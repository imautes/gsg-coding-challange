package com.gsg.codingchallenge.price;

import com.gsg.codingchallenge.vat.TaxRateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class PriceCalculatorService {
    private final TaxRateProvider taxRateProvider;

    public Optional<BigDecimal> calculateNetPrice(@NotNull BigDecimal grossPrice, @NotNull String countryIso) {
        return taxRateProvider.findByCountryIso(countryIso)
                .map(taxRate -> grossPrice.subtract(grossPrice.multiply(taxRate)));
    }
}
