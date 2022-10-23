package com.gsg.codingchallenge.price;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.math.BigDecimal.valueOf;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceCalculatorController.class)
class PriceCalculatorApiTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PriceCalculatorService priceCalculatorService;

    @ParameterizedTest
    @CsvSource({
            ",",
            ",DE"
    })
    void calculateNetPrice_shouldReturn_400(BigDecimal grossPrice, String countryIso) throws Exception {
        mockMvc.perform(get(format("/price-calculator/net-price/%f/%s", grossPrice, countryIso)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateNetPrice_shouldReturn_404() throws Exception {
        when(priceCalculatorService.calculateNetPrice(valueOf(100), "DE"))
                .thenReturn(empty());
        mockMvc.perform(get("/price-calculator/net-price/100/DE"))
                .andExpect(status().isNotFound());
    }

    @Test
    void calculateNetPrice_shouldReturn_200() throws Exception {
        when(priceCalculatorService.calculateNetPrice(valueOf(100), "DE"))
                .thenReturn(of(valueOf(80)));
        mockMvc.perform(get("/price-calculator/net-price/100/DE"))
                .andExpect(status().isOk());
    }

    @Test
    void calculateNetPrice_shouldReturn_netPrice() throws Exception {
        when(priceCalculatorService.calculateNetPrice(valueOf(100), "DE"))
                .thenReturn(of(valueOf(80)));
        assertThat(new BigDecimal(mockMvc.perform(get("/price-calculator/net-price/100/DE")).andReturn()
                .getResponse().getContentAsString())).isEqualByComparingTo(valueOf(80));
    }
}
