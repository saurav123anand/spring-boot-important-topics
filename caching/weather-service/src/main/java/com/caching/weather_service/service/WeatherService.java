package com.caching.weather_service.service;

public interface WeatherService {
    String getWeatherByCity(String city);
    String updateWeather(String city, String updateWeather) throws Exception;
    void deleteWeather(String city);
}
