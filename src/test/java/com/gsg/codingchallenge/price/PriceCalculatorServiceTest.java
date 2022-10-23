package com.gsg.codingchallenge.price;

import com.gsg.codingchallenge.vat.TaxRateProvider;
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
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {PriceCalculatorService.class})
@ContextConfiguration(classes = {ValidationAutoConfiguration.class})
class PriceCalculatorServiceTest {
    @Autowired
    private PriceCalculatorService priceCalculatorService;
    @MockBean
    private TaxRateProvider taxRateProvider;

    @ParameterizedTest
    @CsvSource({
            ",",
            "100,",
            ",DE"
    })
    void calculateNetPrice_shouldThrow_constraintViolationException(BigDecimal grossPrice, String countryIso) {
        assertThrows(ConstraintViolationException.class,
                () -> priceCalculatorService.calculateNetPrice(grossPrice, countryIso));
    }

    @Test
    void calculateNetPrice_shouldReturn_empty() {
        when(taxRateProvider.findByCountryIso("DE"))
                .thenReturn(Optional.empty());
        assertThat(priceCalculatorService.calculateNetPrice(new BigDecimal(100), "DE")).isEmpty();
    }

    @Test
    void calculateNetPrice_shouldReturn_netPriceValue() {
        when(taxRateProvider.findByCountryIso("DE"))
                .thenReturn(Optional.of(valueOf(0.20)));
        assertThat(priceCalculatorService.calculateNetPrice(new BigDecimal(100), "DE")).isPresent();
    }

    @ParameterizedTest
    @CsvSource({
            "100,0.20,80",
            "250,0.5,125",
            "125.789,0.186,102.392246"
    })
    void calculateNetPrice_shouldReturn_correctNetPrice(BigDecimal grossPrice,
                                                        BigDecimal taxRate,
                                                        BigDecimal netPrice) {
        when(taxRateProvider.findByCountryIso("DE"))
                .thenReturn(Optional.of(taxRate));
        assertThat(priceCalculatorService.calculateNetPrice(grossPrice, "DE")).get()
                .usingComparator(BigDecimal::compareTo).isEqualTo(netPrice);
    }
}
