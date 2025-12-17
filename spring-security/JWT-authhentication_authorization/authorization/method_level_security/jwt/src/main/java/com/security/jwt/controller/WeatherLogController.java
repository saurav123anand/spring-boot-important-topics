package com.security.jwt.controller;

import com.security.jwt.entity.WeatherLog;
import com.security.jwt.service.WeatherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/logs")
public class WeatherLogController {

    @Autowired
    private WeatherLogService weatherLogService;

    @GetMapping("/{id}")
    public WeatherLog getLog(@PathVariable Long id) {
        return weatherLogService.getLogById(id);
    }

    @PostMapping
    public WeatherLog createLog(@RequestBody WeatherLog log, Principal principal) {
        log.setPerformedBy(principal.getName());
        log.setTimestamp(LocalDateTime.now());
        return weatherLogService.createLog(log);
    }

}