package com.gsg.codingchallenge.vat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class TaxRateProvider {
    private final TaxRateRepository taxRateRepository;

    public Optional<BigDecimal> findByCountryIso(@NotNull String countryIso) {
        return taxRateRepository.findByCountryIso(countryIso).map(TaxRate::vatRate);
    }
}
