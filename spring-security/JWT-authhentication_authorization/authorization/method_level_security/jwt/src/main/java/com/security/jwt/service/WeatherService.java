package com.security.jwt.service;


import com.security.jwt.entity.Weather;
import com.security.jwt.repository.WeatherRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public String getWeatherByCity(String city) {
        System.out.println("Fetching data from DB for city: " + city);
        Optional<Weather> weather = weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    public String updateWeather(String city, String updatedWeather) {
        weatherRepository.findByCity(city).ifPresent(weather -> {
            weather.setForecast(updatedWeather);
            weatherRepository.save(weather);
        });
        return updatedWeather;
    }

    @Transactional
    @CacheEvict(value = "weather", key = "#city")
    public void deleteWeather(String city) {
        System.out.println("Removing weather data for city: " + city);
        weatherRepository.deleteByCity(city);
    }
}