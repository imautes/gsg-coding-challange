package com.gsg.codingchallenge.vat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolationException;

import static java.math.BigDecimal.valueOf;
import static java.util.Collections.emptyMap;
import static java.util.Map.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@SpringBootTest(classes = {TaxRateRepository.class})
@ContextConfiguration(classes = {ValidationAutoConfiguration.class})
class TaxRateRepositoryTest {
    @Autowired
    private TaxRateRepository taxRateRepository;

    @Test
    void findByCountryIso_shouldThrow_constraintViolationException() {
        assertThrows(ConstraintViolationException.class, () -> taxRateRepository.findByCountryIso(null));
    }

    @Test
    void findByCountryIso_shouldReturn_empty() {
        setField(taxRateRepository, "taxRates", emptyMap());
        assertThat(taxRateRepository.findByCountryIso("DE")).isEmpty();
    }

    @Test
    void findByCountryIso_shouldReturn_taxRate() {
        setField(taxRateRepository, "taxRates", of("DE", valueOf(0.20)));
        assertThat(taxRateRepository.findByCountryIso("DE")).isPresent().get()
                .hasFieldOrPropertyWithValue("countryIso", "DE")
                .hasFieldOrPropertyWithValue("vatRate", valueOf(0.20));
    }
}
