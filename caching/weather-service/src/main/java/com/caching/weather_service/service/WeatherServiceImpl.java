package com.caching.weather_service.service;

import com.caching.weather_service.entity.Weather;
import com.caching.weather_service.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final WeatherRepository weatherRepository;

    @Cacheable(value = "weather",key = "#city")
    @Override
    public String getWeatherByCity(String city) {
        Optional<Weather> weather = weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    @Override
    @CachePut(value = "weather", key = "#city")
    public String updateWeather(String city, String updateWeather) throws Exception {
        Weather weather = weatherRepository.findByCity(city)
            .orElseThrow(() -> new Exception("City not found: " + city));
            
        weather.setForecast(updateWeather);
        weatherRepository.save(weather);
        return updateWeather;
    }
    @CacheEvict(value = "weather", key = "#city")
    @Override
    public void deleteWeather(String city) {
        System.out.println("Deleting weather data for city: "+city);
        weatherRepository.deleteByCity(city);
    }
}
