package org.ruananta.systemeio.payment;

public enum TaxNumberCountry {
    GERMANY("DE\\d{9}"), // DEXXXXXXXXX - Germany
    ITALY("IT\\d{11}"), // ITXXXXXXXXXXX - Italy
    GREECE("GR\\d{9}"), // GRXXXXXXXXX - Greece
    FRANCE("FR[A-Z]{2}\\d{9}"); // FRYYXXXXXXXXX - France

    private final String regex;

    TaxNumberCountry(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static TaxNumberCountry getCountryFromTaxNumber(String taxNumber) {
        for(TaxNumberCountry country : values()) {
            if(taxNumber.matches(country.getRegex())) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid tax number: " + taxNumber);
    }
}
