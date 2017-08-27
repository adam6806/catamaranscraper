package com.github.adam6806.catamaranscraper.boatsite;

import driver.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.util.List;

public class TestSelenium {

    public static void main (String[] args) throws IOException {
        TestSelenium testSelenium = new TestSelenium();
        testSelenium.test();
    }

    public void test () throws IOException {
        Driver driver = WebDriverFactory.getWebDriver();
        driver.get("http://www.yachtworld.com/core/listing/cache/searchResults.jsp?toPrice=175000&fromPrice=25000&enid=101&Ntk=boatsEN&type=%28Sail%29+Catamaran&searchtype=advancedsearch&hmid=0&sm=3&enid=0&cit=true&luom=126&currencyid=100&boatsAddedSelected=-1&ftid=0&slim=quick&No=0&rid=100&rid=104&rid=105&rid=107&rid=112&rid=115&rid=125&fracts=1&ps=2000&Ns=PBoat_sortByPriceDesc|1");
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
