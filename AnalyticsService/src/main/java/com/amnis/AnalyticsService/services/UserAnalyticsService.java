package com.amnis.AnalyticsService.services;

import com.amnis.AnalyticsService.dtos.UsersJoinedAnalyticsDTO;
import com.amnis.AnalyticsService.models.UserJoined;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserAnalyticsService {
    public UsersJoinedAnalyticsDTO generateUsersJoinedAnalytics(List<UserJoined> users){
        return new UsersJoinedAnalyticsDTO ((long) getUniqueUsersJoined(users).size());
    }

    public Set<UserJoined> getUniqueUsersJoined(List<UserJoined> users){
        return  new HashSet<>(users);
    }

}
