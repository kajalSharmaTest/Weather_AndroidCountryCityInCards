package com.interview.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.interview.weather.util.ParentAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherClient weatherClient;
    private ArrayList<Country> countryCityList;
    private RecyclerView recyclerViewParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherClient = new WeatherClientImpl();
        setContentView(R.layout.activity_main);
        countryCityList = new ArrayList<>();
        recyclerViewParent = (RecyclerView) findViewById(R.id.rv_parent);
        try {
            Log.w(MainActivity.class.getSimpleName(), "Cities: " + weatherClient.getCities().cities.size());
            List<City> cityList = weatherClient.getCities().cities;
            for(int i=0;i<cityList.size();i++){
                Log.w(MainActivity.class.getSimpleName(), "Country name: " + cityList.get(i).countryName+ "  City name: " + cityList.get(i).name);
                int countryIndex = getCountryIndex(cityList.get(i).countryName);
                if(countryIndex == -1) {
                    Country country = new Country();
                    country.CountryName = cityList.get(i).countryName;
                    country.city.add(cityList.get(i).name);
                    countryCityList.add(country);
                } else {
                    countryCityList.get(countryIndex).city.add(cityList.get(i).name);
                }
            }
            setUpUI();
        } catch (final IOException e) {
            Log.e(MainActivity.class.getSimpleName(), "Error on getCities", e);
        }
    }

    private void setUpUI(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewParent.setLayoutManager(manager);
        recyclerViewParent.setHasFixedSize(true);
        ParentAdapter parentAdapter = new ParentAdapter(this, countryCityList);
        recyclerViewParent.setAdapter(parentAdapter);
    }

    private int getCountryIndex(String countryname){
        int index = -1;
        for (int i=0;i<countryCityList.size();i++){
            if(countryCityList.get(i).CountryName.equalsIgnoreCase(countryname)) {
                index = i;
            }
        }
        return index;
    }
}
