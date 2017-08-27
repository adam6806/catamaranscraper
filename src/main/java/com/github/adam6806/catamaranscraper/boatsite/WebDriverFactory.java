package com.github.adam6806.catamaranscraper.boatsite;

import driver.Driver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.net.URL;

public class WebDriverFactory {

    private static Driver webDriver;

    public static Driver getWebDriver() throws FileNotFoundException {
        ClassLoader classLoader = WebDriverFactory.class.getClassLoader();
        URL resource = classLoader.getResource("drivers/geckodriver.exe");
        if (resource == null) {
            throw new FileNotFoundException("You are missing the geckodriver.exe file. It should be put in resources/driver/geckodriver.exe");
        }
        System.setProperty("webdriver.gecko.driver", resource.getPath());
        if (webDriver == null) {
            webDriver = new Driver();
        }
        return webDriver;
    }
}
