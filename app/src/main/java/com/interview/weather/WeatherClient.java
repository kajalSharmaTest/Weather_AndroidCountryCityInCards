package com.interview.weather;

import java.io.IOException;

/**
 * Makes network requests to fetch weather data.
 */
public interface WeatherClient {

    GetCitiesResponse getCities() throws IOException;

    GetWeatherForCityResponse getWeatherForCity(String cityName) throws IOException;
}
