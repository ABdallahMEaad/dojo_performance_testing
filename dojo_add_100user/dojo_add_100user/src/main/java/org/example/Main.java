package org.example;

import org.example.driver.BrowserManager;
import org.example.runner.UserRegistrationRunner;


public class Main {

    public static void main(String[] args) {

        BrowserManager browserManager = new BrowserManager();

        try {
            UserRegistrationRunner runner = new UserRegistrationRunner(browserManager);
            runner.run();

        } finally {
            browserManager.quit();
            System.out.println("Browser closed.");
        }
    }
}
