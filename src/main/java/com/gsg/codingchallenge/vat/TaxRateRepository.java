package com.gsg.codingchallenge.vat;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaxRateRepository {
    public Optional<TaxRate> findByCountryIso(String countryIso) {
        return null;
    }
}
