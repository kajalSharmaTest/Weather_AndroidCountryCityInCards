package com.interview.weather;

import java.util.ArrayList;

/**
 * Created by P7111463 on 7/6/2018.
 */

public class Country {
    String CountryName;
    ArrayList<String> city = new ArrayList<>();

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public ArrayList<String> getCity() {
        return city;
    }

    public void setCity(ArrayList<String> city) {
        this.city = city;
    }
}
