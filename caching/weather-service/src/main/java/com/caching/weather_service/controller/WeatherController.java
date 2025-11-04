package com.caching.weather_service.controller;

import com.caching.weather_service.entity.Weather;
import com.caching.weather_service.repository.WeatherRepository;
import com.caching.weather_service.service.CacheInspectionService;
import com.caching.weather_service.service.WeatherService;
import jakarta.transaction.Transactional;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherRepository weatherRepository;
    @Autowired
    private CacheInspectionService cacheInspectionService;
    ;

    @GetMapping
    public String getWeatherByCity(@RequestParam String city) {
        String weatherByCity = weatherService.getWeatherByCity(city);
        return weatherByCity;
    }

    @Transactional
    @PostMapping
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherRepository.save(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @GetMapping("/cache")
    public void printCacheContents() {
        cacheInspectionService.printCacheContents("weather");
    }

    @Transactional
    @DeleteMapping("/{city}")
    public ResponseEntity<String> deleteWeather(@PathVariable String city) {
        try {
            weatherService.deleteWeather(city);
            return ResponseEntity.ok("Weather data is deleted for city " + city);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/{city}")
    public ResponseEntity<String> deleteWeather(@PathVariable String city,@RequestParam String updateWeather) throws Exception {
        String result = weatherService.updateWeather(city, updateWeather);
        return ResponseEntity.ok(result);

    }
}
