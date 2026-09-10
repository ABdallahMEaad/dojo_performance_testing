package org.example.pages;

import org.example.config.Config;
import org.example.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object for the registration page, including the
 * user-menu / logout flow that follows a successful registration.
 *
 * All Selenium locators live here and only here — nothing outside
 * this class should know about CSS selectors or XPath.
 */
public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By nameInput =
            By.cssSelector("[data-testid='register-name-input']");

    private final By emailInput =
            By.cssSelector("[data-testid='register-email-input']");

    private final By passwordInput =
            By.cssSelector("[data-testid='register-password-input']");

    private final By confirmPasswordInput =
            By.cssSelector("[data-testid='register-confirm-password-input']");

    private final By registerButton =
            By.cssSelector("[data-testid='register-submit-button']");

    private final By userMenuButton =
            By.cssSelector("[data-testid='user-menu-button']");

    private final By logoutButton =
            By.cssSelector("[data-testid='nav-logout-button']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Config.WAIT_TIMEOUT);
    }

    /** Navigates to the register page. */
    public void open() {
        driver.get(Config.REGISTER_URL);
    }

    /** Fills the form and submits it for the given user. */
    public void register(User user) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput))
                .clear();
        driver.findElement(nameInput).sendKeys(user.getName());

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(user.getEmail());

        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(user.getPassword());

        driver.findElement(confirmPasswordInput).clear();
        driver.findElement(confirmPasswordInput).sendKeys(user.getPassword());

        wait.until(ExpectedConditions.elementToBeClickable(registerButton))
                .click();
    }

    /** Waits until the app confirms the user is logged in. */
    public void waitUntilLoggedIn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userMenuButton));
    }

    /** Opens the top user menu (needed to reveal the logout option). */
    public void openUserMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(userMenuButton))
                .click();
    }

    /** Clicks logout, then waits until we're safely back on the register page. */
    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
    }
}
