package com.gsg.codingchallenge.vat;

import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Configuration
@Validated
public class TaxRateRepository {
    private Map<String, BigDecimal> taxRates;

    public Optional<TaxRate> findByCountryIso(@NotNull String countryIso) {
        return taxRates.entrySet().stream()
                .filter(vatRate -> vatRate.getKey().equals(countryIso))
                .findAny()
                .map(vatRate -> new TaxRate(vatRate.getKey(), vatRate.getValue()));
    }
}
