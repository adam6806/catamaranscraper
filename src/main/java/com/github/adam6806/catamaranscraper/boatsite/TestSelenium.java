package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.main.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TestSelenium {

    public static void main (String[] args) throws IOException {
        TestSelenium testSelenium = new TestSelenium();
        testSelenium.test();
    }

    public void test () throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("drivers/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", resource.getPath());
        FirefoxDriver driver = new FirefoxDriver();
        driver.get(Main.URL);
        WebElement webElement = driver.findElementById("searchResultsDetailsABTest");
        List<WebElement> elements = webElement.findElements(By.className("image-container"));

        for (WebElement element : elements) {
            FirefoxDriver driver1 = new FirefoxDriver();
            WebElement a = element.findElement(By.tagName("a"));
            String href = a.getAttribute("href");
            driver1.get(href);
            driver1.close();
        }
    }

}
