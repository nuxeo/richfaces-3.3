package org.ajax4jsf.bean;

import java.util.ArrayList;
import java.util.List;

public class SuggestionBoxBean {

    private List<Country> countries = new ArrayList<Country>();

    private String countryName;

    public SuggestionBoxBean() {
       countries.add(new Country("Armenia", "Yerevan", 603628D, 46372700L));
       countries.add(new Country("Azerbaijan", "Baku", 86600D, 8676000L));
       countries.add(new Country("Belarus", "Minsk", 207600D, 9689800L));
       countries.add(new Country("Georgia", "Tbilisi",  69700D, 4630841L));
       countries.add(new Country("Kazakhstan", "Astana", 2724900D, 15217711L));
       countries.add(new Country("Kyrgyzstan", "Bishkek", 199900D, 5264000L));
       countries.add(new Country("Moldova", "Kishinev", 33846D, 4128047L));
       countries.add(new Country("Russia", "Moscow", 17075400D, 142008838L));
       countries.add(new Country("Tajikistan", "Dushanbe", 143100D, 6920300L));
       countries.add(new Country("Turkmenistan", "Ashgabat", 488100D, 5110023L));
       countries.add(new Country("Ukraine", "Kiev", 603628D, 46372700L));
       countries.add(new Country("Uzbekistan", "Tashkent", 447400D, 27372000L));
    }

    public List<Country> getCountries() {
        return countries;
    }

    /**
     * Gets value of countryName field.
     * @return value of countryName field
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Set a new value for countryName field.
     * @param countryName a new value for countryName field
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<Country> autocomplete(Object suggestion) {
        String s = (String) suggestion;
        ArrayList<Country> retVal = new ArrayList<Country>();
        for (Country country : countries) {
            // if ("".equals(s) ||
            // country.getName().toLowerCase().startsWith(s.toLowerCase())) {
            if (country.getName().matches(".*" + s + ".*")) {
                retVal.add(country);
            }
        }
        return retVal;
    }

    public void reset() {
        this.countryName = "";
    }

    /**
     * Country
     */
    public static class Country {
        private String name;
        private String capital;
        private Double area;
        private Long population;

        public Country(String name, String capital, Double area, Long population) {
            this.name = name;
            this.capital = capital;
            this.area = area;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getCapital() {
            return capital;
        }

        public Double getArea() {
            return area;
        }

        public Long getPopulation() {
            return population;
        }
    }

}
