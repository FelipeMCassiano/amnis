package com.amnis.AnalyticsService.repositories;

import com.amnis.AnalyticsService.models.Analytics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AnalyticsRepository extends MongoRepository<Analytics, String> {
    Optional<Analytics> findByStreamId(Long streamId);
}
