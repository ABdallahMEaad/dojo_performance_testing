package org.example.config;

import java.time.Duration;

/**
 * Central place for all configuration values.
 * Change anything here instead of hunting through the code.
 */
public final class Config {

    private Config() {
        // no instances
    }

    public static final String REGISTER_URL =
            "https://dojo.upexgalaxy.com/register";

    public static final String DEFAULT_PASSWORD =
            "Test@123456";

    public static final int NUMBER_OF_USERS = 100;

    public static final String USERNAME_PREFIX =
            "abdallah";

    public static final String CSV_FILE_PATH =
            "registered_users.csv";

    public static final Duration WAIT_TIMEOUT =
            Duration.ofSeconds(15);
}
