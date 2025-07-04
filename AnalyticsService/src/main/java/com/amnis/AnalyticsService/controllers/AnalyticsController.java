package com.amnis.AnalyticsService.controllers;

import com.amnis.AnalyticsService.dtos.AnalyticsDTO;
import com.amnis.AnalyticsService.models.Analytics;
import com.amnis.AnalyticsService.services.AnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("analytics")
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("{streamId}/dashboard")
    public ResponseEntity<AnalyticsDTO> dashboard(@PathVariable Long streamId) {
        AnalyticsDTO response = analyticsService.generateAnalytics(streamId);
        return ResponseEntity.ok(response);
    }
    @GetMapping()
    public ResponseEntity<List<Analytics>> analytics() {
        return  ResponseEntity.ok(analyticsService.getAllAnalytics());

    }
}
