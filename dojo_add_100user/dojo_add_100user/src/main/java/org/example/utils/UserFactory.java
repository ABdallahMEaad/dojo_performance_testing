package org.example.utils;

import org.example.config.Config;
import org.example.model.User;

/**
 * Generates unique, ready-to-register User objects.
 */
public final class UserFactory {

    private UserFactory() {
        // no instances
    }

    public static User createUser(int index) {

        String username = Config.USERNAME_PREFIX + index;

        String email = Config.USERNAME_PREFIX + index +
                "_" + System.currentTimeMillis() +
                "@example.com";

        return new User(username, email, Config.DEFAULT_PASSWORD);
    }
}
