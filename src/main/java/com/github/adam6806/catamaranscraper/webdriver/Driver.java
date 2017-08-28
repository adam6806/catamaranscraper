package com.github.adam6806.catamaranscraper.webdriver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Driver implements WebDriver {

    private final int DEFAULT_TIME_OUT = 30;
    Log logger = new SimpleLog(Driver.class.getName());
    private WebDriver driver;
    private int timeout = DEFAULT_TIME_OUT;

    public Driver() {

        FirefoxOptions options = new FirefoxOptions();
        options.setLogLevel(java.util.logging.Level.SEVERE);
        driver = new FirefoxDriver(options);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void get(String s) {
        driver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    public void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (TimeoutException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }

    public void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public WebElement findElementById(String idStr) {
        By id = By.id(idStr);
        return driver.findElement(id);
    }

    public boolean isElementPresent(By by) {
        boolean isPresent = false;
        try {
            waitForLoad();

            if (!this.findElements(by).isEmpty()) {
                isPresent = true;
            }

            this.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        } catch (InvalidSelectorException e) {
            logger.error(e.getMessage());
        }

        return isPresent;
    }

    public void waitForLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            @Nullable
            @Override
            public Boolean apply(@Nullable WebDriver webDriver) {
                return "complete".equals(((JavascriptExecutor) driver).executeScript("return document.readyState"));
            }
        };
        WebDriverWait wait = new WebDriverWait(this, timeout);
        wait.until(pageLoadCondition);
        this.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    public void click(By by) {
        driver.findElement(by).click();
    }

    public String getElementHtml(By by) {
        WebElement element = driver.findElement(by);
        String html = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", element);
        return html;
    }
}
