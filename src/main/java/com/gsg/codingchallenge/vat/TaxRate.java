package com.gsg.codingchallenge.vat;

import java.math.BigDecimal;

public record TaxRate(
        String countryIso,
        BigDecimal vatRate
) {
}
