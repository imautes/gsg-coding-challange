package com.gsg.codingchallenge.vat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {TaxRateProvider.class})
@ContextConfiguration(classes = {ValidationAutoConfiguration.class})
class TaxRateProviderTest {
    @Autowired
    private TaxRateProvider taxRateProvider;
    @MockBean
    private TaxRateRepository taxRateRepository;

    @Test
    void findByCountryIso_shouldThrow_constraintViolationException() {
        assertThrows(ConstraintViolationException.class, () -> taxRateProvider.findByCountryIso(null));
    }

    @Test
    void findByCountryIso_shouldReturn_empty() {
        when(taxRateRepository.findByCountryIso("DE"))
                .thenReturn(Optional.empty());
        assertThat(taxRateProvider.findByCountryIso("DE")).isEmpty();
    }

    @Test
    void findByCountryIso_shouldReturn_taxRate() {
        when(taxRateRepository.findByCountryIso("DE"))
                .thenReturn(Optional.of(new TaxRate("DE", valueOf(0.20))));
        assertThat(taxRateProvider.findByCountryIso("DE")).isPresent().get()
                .isEqualTo(valueOf(0.20));
    }
}
