package com.gsg.codingchallenge.vat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "gsg")
@Validated
public class TaxRateRepository {
    @Getter @Setter
    private Map<String, BigDecimal> taxRates;

    public Optional<TaxRate> findByCountryIso(@NotNull String countryIso) {
        return taxRates.entrySet().stream()
                .filter(vatRate -> vatRate.getKey().equals(countryIso))
                .findAny()
                .map(vatRate -> new TaxRate(vatRate.getKey(), vatRate.getValue()));
    }
}
