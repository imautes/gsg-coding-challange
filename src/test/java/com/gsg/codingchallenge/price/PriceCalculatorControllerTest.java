package com.gsg.codingchallenge.price;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import javax.validation.ConstraintViolationException;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PriceCalculatorController.class})
@ContextConfiguration(classes = {ValidationAutoConfiguration.class})
class PriceCalculatorControllerTest {
    @Autowired
    private PriceCalculatorController priceCalculatorController;
    @MockBean
    private PriceCalculatorService priceCalculatorService;

    @ParameterizedTest
    @CsvSource({
            ",",
            "100,",
            ",DE"
    })
    void calculateNetPrice_shouldThrow_constraintViolationException(BigDecimal grossPrice, String countryIso) {
        assertThrows(ConstraintViolationException.class,
                () -> priceCalculatorController.calculateNetPrice(grossPrice, countryIso));
    }

    @Test
    void calculateNetPrice_shouldThrow_taxRateNotFoundException() {
        when(priceCalculatorService.calculateNetPrice(valueOf(100), "DE"))
                .thenReturn(empty());
        assertThrows(TaxRateNotFoundException.class,
                () -> priceCalculatorController.calculateNetPrice(valueOf(100), "DE"));
    }

    @Test
    void calculateNetPrice_shouldReturn_netPrice() {
        when(priceCalculatorService.calculateNetPrice(valueOf(100), "DE"))
                .thenReturn(of(valueOf(80)));
        assertThat(priceCalculatorController.calculateNetPrice(valueOf(100), "DE"))
                .hasFieldOrPropertyWithValue("body", valueOf(80));
    }
}
