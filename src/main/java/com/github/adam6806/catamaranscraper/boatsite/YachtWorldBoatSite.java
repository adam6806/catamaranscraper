package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.database.model.BoatEntity;
import com.github.adam6806.catamaranscraper.database.model.ImageEntity;
import com.github.adam6806.catamaranscraper.webdriver.BoatSiteDownException;
import com.github.adam6806.catamaranscraper.webdriver.Driver;
import com.github.adam6806.catamaranscraper.webdriver.ScraperUtils;
import com.github.adam6806.catamaranscraper.webdriver.WebDriverFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class YachtWorldBoatSite implements BoatSite {

    private static final String URL = "http://www.yachtworld.com/core/listing/cache/searchResults.jsp?toPrice=175000&fromPrice=25000&enid=101&Ntk=boatsEN&type=%28Sail%29+Catamaran&searchtype=advancedsearch&hmid=0&sm=3&enid=0&cit=true&luom=126&currencyid=100&boatsAddedSelected=-1&ftid=0&slim=quick&No=0&rid=100&rid=104&rid=105&rid=107&rid=112&rid=115&rid=125&fracts=1&ps=2000&Ns=PBoat_sortByPriceDesc|1";
    private static final String MAIN_URL = "http://www.yachtworld.com";
    private Log logger = new SimpleLog(YachtWorldBoatSite.class.getName());
    private List<BoatEntity> entities;
    private Driver driver;

    public static void main(String[] args) {
        YachtWorldBoatSite yachtWorldBoatSite = new YachtWorldBoatSite();
        yachtWorldBoatSite.run();

    }

    private void setUp() throws FileNotFoundException {
        driver = WebDriverFactory.getWebDriver();
    }

    private void run() {
        try {
            setUp();
            navigateToUrl(URL);

            List<String> urls = getListingURLs();
            entities = navigateIntoURLAndScrapeListing(urls);
        } catch (IOException e) {
            //TODO: Send email with error information
            logger.error("Webfactory threw an exception: " + e.getMessage() + "Skipping boatsite.");
        } catch (BoatSiteDownException e) {
            //TODO: Send email with error information
            logger.error("WebsiteDownExecption: " + e.getMessage());
        }

        quit();
    }

    @Override
    public List<BoatEntity> getBoatEntities() {
        run();
        return entities;
    }

    private List<String> getListingURLs() throws BoatSiteDownException {
        logger.info("Getting listing URLS");
        List<String> urls = new ArrayList<>();
        By makeModelLoc = By.cssSelector("div.make-model a");
        //get all search results rows
        if (driver.isElementPresent(makeModelLoc)) {
            List<WebElement> rows = driver.findElements(makeModelLoc);

            //grab the url's to go to the listing page
            for (WebElement row : rows) {
                String url = row.getAttribute("href");
                if (url.contains("boat")) {
                    urls.add(url);
                } else {
                    logger.warn("URL found on page does not contain 'boat' may not be a boat listing: " + url);
                }
            }
        } else {
            throw new BoatSiteDownException("Could not find row listing locator. Unable to scrape Boat listing from Yacht World.");
        }
        return urls;
    }

    private List<BoatEntity> navigateIntoURLAndScrapeListing(List<String> urls) throws BoatSiteDownException {
        List<BoatEntity> boats = new ArrayList<>();

        for (String url : urls) {
            navigateToUrl(url);
            boats.add(scrapeListing());
        }

        return boats;
    }

    private void navigateToUrl(String url) {
        driver.get(url);
        driver.waitForLoad();
    }

    private BoatEntity scrapeListing() throws BoatSiteDownException {
        logger.info("Scraping Boat Entity");

        BoatEntity boat = new BoatEntity();

        boat = setBoatMakeModelYear(boat);
        boat = setBoatPrice(boat);
        boat = setLocation(boat);
        boat = setImages(boat);
        boat = setDetailsAndLength(boat);

        boat.setSiteUrl(MAIN_URL);
        boat.setUrl(driver.getCurrentUrl());
        boat.setActive(new Byte("1"));
        boat.setTimestamp(new java.sql.Date(new Date().getTime()));
        return boat;
    }

    private BoatEntity setBoatMakeModelYear(BoatEntity boat) throws BoatSiteDownException {
        logger.info("Setting make mode and year.");

        By boatTitleLoc = By.className("boat-title");
        if (driver.isElementPresent(boatTitleLoc)) {
            String boatTitle = ScraperUtils.cleanHTML(driver.getElementHtml(boatTitleLoc));
            boatTitle = boatTitle.replaceAll("<[/]?h1>", "");

            String makeModelPattern = "[0-9]{4}\\s*(.*?)$";
            String yearPattern = "([0-9]{4})\\s*";
            String makeModel = ScraperUtils.getTextByPattern(makeModelPattern, boatTitle);
            String year = ScraperUtils.getTextByPattern(yearPattern, boatTitle);

            try {
                boat.setYear(Integer.parseInt(year));
            } catch (NumberFormatException e) {
                throw new BoatSiteDownException("Unable to parse Year into an integer: " + year);
            }

            boat.setMakeModel(makeModel);
        } else {
            throw new BoatSiteDownException("Could not find boat title locator.");
        }

        return boat;
    }

    private BoatEntity setBoatPrice(BoatEntity boat) throws BoatSiteDownException {
        logger.info("Setting Price");

        By boatPriceLoc = By.className("boat-price");

        if (driver.isElementPresent(boatPriceLoc)) {
            String priceSrc = ScraperUtils.cleanHTML(driver.getElementHtml(boatPriceLoc));
            String priceUSExtractionPattern = "US\\$\\s*([0-9,.]*)";

            String price = ScraperUtils.cleanNumber(ScraperUtils.getTextByPattern(priceUSExtractionPattern, priceSrc));

            try {
                boat.setPrice(Long.parseLong(price));
            } catch (NumberFormatException e) {
                throw new BoatSiteDownException("Unable to parse price into a long: " + price);
            }

        } else {
            throw new BoatSiteDownException("Unable to find the Price locator.");
        }
        return boat;
    }

    private BoatEntity setLocation(BoatEntity boat) throws BoatSiteDownException {
        logger.info("Setting Location");

        By boatLocationLoc = By.className("boat-location");

        if (driver.isElementPresent(boatLocationLoc)) {
            String locationSrc = ScraperUtils.cleanHTML(driver.getElementHtml(boatLocationLoc));
            boat.setLocation(locationSrc);
        } else {
            throw new BoatSiteDownException("Unable to locate the location locator.");
        }
        return boat;
    }

    private BoatEntity setImages(BoatEntity boat) throws BoatSiteDownException {
        logger.info("Setting Images");
        Set<ImageEntity> imageEntities = new HashSet<>();

        By galleryCarousel = By.cssSelector("div[class^='galleria-thumbnails-container']");
        String imageSrcPattern = "<img.*?src=\"(.*?)\"";

        if (driver.isElementPresent(galleryCarousel)) {

            String caroselImageSrc = ScraperUtils.cleanHTML(driver.getElementHtml(galleryCarousel));
            List<String> divList = getDivList(caroselImageSrc);

            for (String div : divList) {
                if (StringUtils.isNotEmpty(div)
                        && div.contains("img")) {
                    String imageSrc = ScraperUtils.getTextByPattern(imageSrcPattern, div);

                    if (imageSrc.startsWith("http")) {
                        ImageEntity imageEntity = new ImageEntity();
                        imageEntity.setBoat(boat);
                        imageEntity.setUrl(imageSrc);
                        imageEntities.add(imageEntity);
                    }
                }
            }

        } else {
            throw new BoatSiteDownException("Unable to locate gallery images.");
        }

        boat.setImages(imageEntities);

        return boat;
    }

    private BoatEntity setDetailsAndLength(BoatEntity boat) throws BoatSiteDownException {
        logger.info("Setting details.");

        By boatDetailsLoc = By.cssSelector("div[class^='boatdetails']");

        if (driver.isElementPresent(boatDetailsLoc)) {
            List<WebElement> elementDetails = driver.findElements(boatDetailsLoc);
            StringBuilder stringBuilder = new StringBuilder();

            for (WebElement details : elementDetails) {
                String detailsText = details.getText();

                if (StringUtils.isNotEmpty(detailsText)) {
                    if (detailsText.contains("\n")) {
                        String[] splitDetails = detailsText.split("\\n");

                        for (int i = 0; i < splitDetails.length; i++) {
                            String detail = splitDetails[i];
                            String detailUpperCase = detail.toUpperCase();

                            if (detailUpperCase.contains("LENGTH")) {
                                try {
                                    String length = ScraperUtils.cleanNumber(splitDetails[i + 1]);
                                    boat.setLength(Integer.parseInt(length));
                                    i++;
                                } catch (NumberFormatException nfe) {
                                    throw new BoatSiteDownException("Unable to parse length into an integer: " + nfe.getMessage());
                                }
                            } else if(detailUpperCase.contains("YEAR")
                                    || detailUpperCase.contains("LOCATED IN")
                                    || detailUpperCase.contains("ENGINE/FUEL TYPE")
                                    || detailUpperCase.contains("HULL MATERIAL")
                                    || detailUpperCase.contains("YW#")
                                    || detailUpperCase.contains("CURRENT PRICE")) {
                                //This description information is not needed, and will need to be skipped.
                                i++;
                            } else {
                                stringBuilder.append(detail.trim()).append(" ");
                            }
                        }
                    } else {
                        stringBuilder.append(detailsText).append(" ");
                    }
                }
            }

            boat.setDescription(stringBuilder.toString().trim());
        } else {
            throw new BoatSiteDownException("Unable to locate details locators");
        }

        return boat;
    }

    private void quit() {
        logger.info("Ending Selenium Session");
        driver.quit();
    }

    private List<String> getDivList(String src) {
        String divPattern = "<div[^>]*>(.*?)</div>";
        return ScraperUtils.getTextArrayByPattern(divPattern, src);
    }

}
