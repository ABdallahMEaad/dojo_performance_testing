package org.example.runner;

import org.example.config.Config;
import org.example.driver.BrowserManager;
import org.example.model.User;
import org.example.pages.RegisterPage;
import org.example.utils.CsvUserWriter;
import org.example.utils.UserFactory;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;

/**
 * Orchestrates the full flow: for N users -> register, save to CSV, logout,
 * back to the register page, ready for the next one.
 *
 * One failed user does not stop the whole run. If the browser itself dies
 * mid-run (crash, manual close, etc.), the runner restarts it instead of
 * letting every remaining user fail.
 */
public class UserRegistrationRunner {

    private final BrowserManager browserManager;
    private RegisterPage registerPage;

    public UserRegistrationRunner(BrowserManager browserManager) {
        this.browserManager = browserManager;
        this.registerPage = new RegisterPage(browserManager.getDriver());
    }

    public void run() {

        try (CsvUserWriter csvWriter = new CsvUserWriter(Config.CSV_FILE_PATH)) {

            for (int i = 1; i <= Config.NUMBER_OF_USERS; i++) {

                System.out.println();
                System.out.println("======================================");
                System.out.println("Creating User " + i + " / " + Config.NUMBER_OF_USERS);
                System.out.println("======================================");

                User user = UserFactory.createUser(i);

                try {
                    registerOneUser(user, csvWriter);
                } catch (WebDriverException e) {
                    logFailure(i, user, e);
                    System.out.println("Browser session looks dead — restarting Chrome.");
                    browserManager.restart();
                    registerPage = new RegisterPage(browserManager.getDriver());
                } catch (Exception e) {
                    logFailure(i, user, e);
                }
            }

            System.out.println();
            System.out.println("======================================");
            System.out.println("ALL USERS PROCESS FINISHED");
            System.out.println("======================================");
            System.out.println("Total Users: " + Config.NUMBER_OF_USERS);
            System.out.println("CSV File: " + Config.CSV_FILE_PATH);

        } catch (IOException e) {
            System.out.println("Could not open CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void registerOneUser(User user, CsvUserWriter csvWriter) {

        registerPage.open();
        registerPage.register(user);

        System.out.println("Register button clicked.");

        registerPage.waitUntilLoggedIn();

        System.out.println("User registered and logged in:");
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());

        csvWriter.write(user);
        System.out.println("Saved to CSV.");

        registerPage.openUserMenu();
        System.out.println("User menu opened.");

        registerPage.logout();
        System.out.println("Logout clicked.");
        System.out.println("Logout successful.");
        System.out.println("Ready for next user.");
    }

    private void logFailure(int index, User user, Exception e) {
        System.out.println();
        System.out.println("FAILED User " + index);
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Error: " + e.getMessage());
        e.printStackTrace();
        System.out.println("Continuing with next user...");
    }
}
