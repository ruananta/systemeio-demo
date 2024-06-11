package org.ruananta.systemeio.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Configuration
@ImportResource("classpath:tax-config.xml")
public class TaxConfiguration {

    @Resource(name = "taxNumberCountryRegex")
    private Map<String, String> taxNumberCountryRegex;

    @Resource(name = "taxRates")
    private Map<String, BigDecimal> taxRates;

    public String getCountryFromTaxNumber(String taxNumber) {
        for (Map.Entry<String, String> entry : this.taxNumberCountryRegex.entrySet()) {
            if (taxNumber.matches(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid tax number: " + taxNumber);
    }

    public Optional<BigDecimal> getRateFromTaxNumber(String taxNumber) {
        String taxNumberCountry = getCountryFromTaxNumber(taxNumber);
        return getTaxRateFromCountry(taxNumberCountry);
    }

    public Optional<BigDecimal> getTaxRateFromCountry(String country) {
        for (Map.Entry<String, BigDecimal> entry : this.taxRates.entrySet()) {
            if(country.equals(entry.getKey())) {
                return Optional.ofNullable(taxRates.get(entry.getKey()));
            }
        }
        return Optional.empty();
    }
}
