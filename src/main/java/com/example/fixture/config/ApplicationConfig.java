package com.example.fixture.config;

import org.apache.commons.lang3.StringUtils;

/** Immutable configuration loaded from environment variables. */
public record ApplicationConfig(String applicationName, String environment) {
    public static ApplicationConfig fromEnvironment() {
        String configuredEnvironment = System.getenv("FIXTURE_ENVIRONMENT");
        String environment = StringUtils.defaultIfBlank(configuredEnvironment, "local");
        return new ApplicationConfig("java-repo-indexer-fixture", environment);
    }
}
