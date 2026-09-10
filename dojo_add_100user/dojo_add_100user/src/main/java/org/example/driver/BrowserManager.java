package org.example.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Owns the ChromeDriver instance. If the browser crashes or the session
 * otherwise dies mid-run, call restart() to get a fresh, working driver
 * instead of the whole run failing from that point on.
 */
public class BrowserManager {

    private WebDriver driver;

    public BrowserManager() {
        this.driver = new ChromeDriver();
    }

    public WebDriver getDriver() {
        return driver;
    }

    /** Closes the current (likely dead) session and opens a brand new browser. */
    public void restart() {
        try {
            driver.quit();
        } catch (Exception ignored) {
            // The browser is probably already gone; nothing to clean up.
        }
        driver = new ChromeDriver();
    }

    public void quit() {
        try {
            driver.quit();
        } catch (Exception ignored) {
            // Already closed.
        }
    }
}
