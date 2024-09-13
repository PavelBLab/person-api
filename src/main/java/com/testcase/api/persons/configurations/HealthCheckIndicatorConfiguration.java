package com.testcase.api.persons.configurations;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthCheckIndicatorConfiguration implements HealthIndicator {

    @Override
    public Health health() {
        return checkHealth() ?
            Health.up().withDetail("status", "Person Api is up and running").build() :
            Health.down().withDetail("status", "Person Api is down").build();
    }

    private boolean checkHealth() {
        return true;
    }

}