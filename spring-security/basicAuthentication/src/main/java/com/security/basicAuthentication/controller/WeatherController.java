package com.security.basicAuthentication.controller;


import com.security.basicAuthentication.entity.Weather;
import com.security.basicAuthentication.repository.WeatherRepository;
import com.security.basicAuthentication.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @GetMapping
    public String getWeather(@RequestParam String city) {
        String weatherByCity
                = weatherService.getWeatherByCity(city);
        return weatherByCity;
    }

    @PostMapping
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherRepository.save(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestParam String weatherUpdate) {
        return weatherService.updateWeather(city, weatherUpdate);
    }

    @DeleteMapping("/{city}")
    @PreAuthorize("hasAuthority('WEATHER_DELETE')")
    public String deleteWeather(@PathVariable String city) {
        weatherService.deleteWeather(city);
        return "Weather data for " + city + " has been deleted and cache evicted.";
    }

    @GetMapping("/health")
    public String getHealth() {
        return "Healthy";
    }
}