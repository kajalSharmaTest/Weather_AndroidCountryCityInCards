package com.interview.weather;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WeatherClientImpl implements WeatherClient {

    private static final Random RANDOM = new Random();

    private static final Map<String, List<String>> COUNTRIES_WITH_CITIES = new HashMap<>();
    static {
        COUNTRIES_WITH_CITIES.put("Australia", Arrays.asList("Canberra", "Melbourne", "Perth", "Sydney"));
        COUNTRIES_WITH_CITIES.put("Canada", Arrays.asList("Toronto", "Vancouver", "Montreal", "Calgary"));
        COUNTRIES_WITH_CITIES.put("Ireland", Arrays.asList("Dublin", "Galway", "Kilkenny"));
        COUNTRIES_WITH_CITIES.put("Mexico", Arrays.asList("Guadalajara", "Mexico City", "Monterrey"));
        COUNTRIES_WITH_CITIES.put("Philippines", Arrays.asList("Manila", "Cebu City"));
        COUNTRIES_WITH_CITIES.put("United Kingdom", Arrays.asList("London", "Conwy", "Brighton", "Bath", "York", "Edinburgh"));
        COUNTRIES_WITH_CITIES.put("United States", Arrays.asList("San Francisco", "New York", "Boston", "Los Angeles"));
        COUNTRIES_WITH_CITIES.put("Uruguay", Arrays.asList("Montevideo", "Punta del Este", "Colonia del Sacramento"));
    }

    private static final List<String> WEATHER_DESCRIPTIONS = Arrays.asList(
            "Cloudy with a chance of meatballs",
            "Mostly sunny with afternoon tornadoes",
            "Plague of locusts",
            "Hot as an oven",
            "Sideways rain"
    );

    private static final List<String> WIND_DIRECTIONS = Arrays.asList(
            "North",
            "Northeast",
            "East",
            "Southeast",
            "South",
            "Southwest",
            "West",
            "Northwest"
    );

    @Override
    public GetCitiesResponse getCities() throws IOException {
        randomDelay();
        maybeThrowError();
        final int cityCount = RANDOM.nextInt(30);
        final List<City> cities = new ArrayList<>(cityCount);
        for (int i = 0; i < cityCount; ++i) {
            final int randomCountryIndex = RANDOM.nextInt(COUNTRIES_WITH_CITIES.size());
            final Map.Entry<String, List<String>> countryWithCities =
                    (Map.Entry<String, List<String>>) COUNTRIES_WITH_CITIES.entrySet().toArray()[randomCountryIndex];
            final int randomCityIndex = RANDOM.nextInt(countryWithCities.getValue().size());
            final String randomCityName = countryWithCities.getValue().get(randomCityIndex);
            cities.add(new City(
                    randomCityName,
                    countryWithCities.getKey(),
                    90 * RANDOM.nextGaussian(),
                    180 * RANDOM.nextGaussian()));
        }
        return new GetCitiesResponse(cities);
    }

    @Override
    public GetWeatherForCityResponse getWeatherForCity(final String cityName) throws IOException {
        randomDelay();
        maybeThrowError();
        return new GetWeatherForCityResponse(
                new CurrentWeather(
                        WEATHER_DESCRIPTIONS.get(RANDOM.nextInt(WEATHER_DESCRIPTIONS.size())),
                        randomTemperatureCelsius(),
                        (short) (100 * RANDOM.nextFloat()),
                        (short) (100 * RANDOM.nextFloat()),
                        (1000 + 20 * RANDOM.nextGaussian()) + " mbar",
                        WIND_DIRECTIONS.get(RANDOM.nextInt(WIND_DIRECTIONS.size())),
                        100 * RANDOM.nextFloat()),
                new HashMap<String, PredictedWeather>() {{
                    put("Sunday", randomPredictedWeather());
                    put("Monday", randomPredictedWeather());
                    put("Tuesday", randomPredictedWeather());
                    put("Wednesday", randomPredictedWeather());
                    put("Thursday", randomPredictedWeather());
                    put("Friday", randomPredictedWeather());
                    put("Saturday", randomPredictedWeather());
                }}
        );
    }

    private static PredictedWeather randomPredictedWeather() {
        final float minTemp = randomTemperatureCelsius();
        final float maxTemp = minTemp + 15 * RANDOM.nextFloat();
        return new PredictedWeather(
                minTemp,
                maxTemp,
                WEATHER_DESCRIPTIONS.get(RANDOM.nextInt(WEATHER_DESCRIPTIONS.size())));
    }

    private static float randomTemperatureCelsius() {
        return (float) (10 + 20 * RANDOM.nextGaussian());
    }

    private static void randomDelay() {
        try {
            Thread.sleep(RANDOM.nextInt(5000));
        } catch (InterruptedException e) {
            Log.e(WeatherClientImpl.class.getSimpleName(), "Interrupted sleep");
            Thread.currentThread().interrupt();
        }
    }

    private static void maybeThrowError() throws IOException {
        if (true) {
            return;
        }
        if (RANDOM.nextInt(5) == 0) {
            throw new IOException("fake network error");
        }
    }
}
