package test;


import com.github.adam6806.catamaranscraper.webdriver.ScraperUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScraperUtilsTests {

    ScraperUtils scraperUtils = new ScraperUtils();

    @Test
    public void getTextByPatternTest() {

        String src1 = "I like blueberries";
        String src2 = "<strong>My Name is Berry BlueJeans</strong>     And I am dead<table><tr> Berry BlueJeans is cool</tr><table>";
        String src3 = "<br>January</br><br>February</br><br>March</br><br>April</br><br>May</br><br>June</br><br>July<br>";
        String src4 = "October, 3rd, 1983";
        String src5 = "Junmable, stuff stuff words, Fin blah blah blah $500000.0000 hey now!";

        String extractionPattern1 = "[A-Za-z\\s*]*(blueberries)";
        String extractionPattern2 = "[>|\\s*](Berry\\s*BlueJeans)[<|\\s*]";
        String extractionPattern3 = ">\\s*(March)\\s*<";
        String extractionPattern4 = "October,\\s*([0-9]{1})[a-z]*,\\s*[0-9]{4}";
        String extractionPattern5 = "\\$([0-9.]*)\\s*";

        String expectedMatchOnSrc1 = "blueberries";
        String expectedMatchOnSrc2 = "Berry BlueJeans";
        String expectedMatchOnSrc3 = "March";
        String expectedMatchOnSrc4 = "3";
        String expectedMatchOnSrc5 = "500000.0000";

        //test it can correctly bring back matches
        Assert.assertTrue(expectedMatchOnSrc1.contentEquals(scraperUtils.getTextByPattern(extractionPattern1, src1)));
        Assert.assertTrue(expectedMatchOnSrc2.contentEquals(scraperUtils.getTextByPattern(extractionPattern2, src2)));
        Assert.assertTrue(expectedMatchOnSrc3.contentEquals(scraperUtils.getTextByPattern(extractionPattern3, src3)));
        Assert.assertTrue(expectedMatchOnSrc4.contentEquals(scraperUtils.getTextByPattern(extractionPattern4, src4)));
        Assert.assertTrue(expectedMatchOnSrc5.contentEquals(scraperUtils.getTextByPattern(extractionPattern5, src5)));

        //text it can correctly bring back nothing
        Assert.assertTrue(StringUtils.isEmpty(scraperUtils.getTextByPattern(extractionPattern2, src1)));
        Assert.assertTrue(StringUtils.isEmpty(scraperUtils.getTextByPattern(extractionPattern3, src1)));
        Assert.assertTrue(StringUtils.isEmpty(scraperUtils.getTextByPattern(extractionPattern4, src1)));
        Assert.assertTrue(StringUtils.isEmpty(scraperUtils.getTextByPattern(extractionPattern5, src1)));


    }

    @Test
    public void testGetTextArrayByPattern() {
        String src1 = "<strong>My Name is Berry BlueJeans</strong>     And I am dead<table><tr> Berry BlueJeans is cool</tr><table>";
        String src2 = "<br>January</br><br>February</br><br>March</br><br>April</br><br>May</br><br>June</br><br>July</br>";
        String src3 = "$12 Junmable, stuff 650000 stuff words, Fin$60492.4950684 blah blah blah $500000.0000 hey now!";

        String extractionPattern1 = "(Berry\\s*BlueJeans)";
        String extractionPattern2 = "<[^>]*>(.*?)<";
        String extractionPattern3 = "\\$([0-9.]*)\\s*";

        String expectedMatchOnSrc1 = "Berry BlueJeans";

        List<String> expectedMatchesOnSrc2 = new ArrayList<>();
        expectedMatchesOnSrc2.add("January");
        expectedMatchesOnSrc2.add("February");
        expectedMatchesOnSrc2.add("March");
        expectedMatchesOnSrc2.add("April");
        expectedMatchesOnSrc2.add("May");
        expectedMatchesOnSrc2.add("June");
        expectedMatchesOnSrc2.add("July");

        List<String> expectedMatchesOnSrc3 = new ArrayList<>();
        expectedMatchesOnSrc3.add("12");
        expectedMatchesOnSrc3.add("60492.4950684");
        expectedMatchesOnSrc3.add("500000.0000");

        //test it can correctly bring back matches
        List<String> testSrc1Pattern = scraperUtils.getTextArrayByPattern(extractionPattern1, src1);
        Assert.assertTrue(testSrc1Pattern.size() == 2);
        for (String match : testSrc1Pattern) {
            Assert.assertTrue(expectedMatchOnSrc1.contentEquals(match));
        }


        List<String> testSrc2Pattern = scraperUtils.getTextArrayByPattern(extractionPattern2, src2);
        Assert.assertTrue(testSrc2Pattern.size() == expectedMatchesOnSrc2.size());

        for (int i = 0; i < testSrc2Pattern.size() && i < expectedMatchesOnSrc2.size(); i++) {
            Assert.assertTrue(testSrc2Pattern.get(i).equals(expectedMatchesOnSrc2.get(i)));
        }

        List<String> testSrc3Pattern = scraperUtils.getTextArrayByPattern(extractionPattern3, src3);
        Assert.assertTrue(testSrc3Pattern.size() == expectedMatchesOnSrc3.size());

        for (int i = 0; i < testSrc3Pattern.size() && i < expectedMatchesOnSrc3.size(); i++) {
            Assert.assertTrue(testSrc3Pattern.get(i).equals(expectedMatchesOnSrc3.get(i)));
        }


        //test it can correctly bring back an empty list
        List<String> testSrc1EmptyListPattern = scraperUtils.getTextArrayByPattern(extractionPattern1, src2);
        Assert.assertTrue(testSrc1EmptyListPattern.size() == 0);

        List<String> testSrc2EmptyListPattern = scraperUtils.getTextArrayByPattern(extractionPattern2, src3);
        Assert.assertTrue(testSrc2EmptyListPattern.size() == 0);

        List<String> testSrc3EmptyListPattern = scraperUtils.getTextArrayByPattern(extractionPattern3, src2);
        Assert.assertTrue(testSrc3EmptyListPattern.size() == 0);
    }


    @Test
    public void testCleanHtml() {
        String uncleanHTML = "    <div class=\"confirmation contactSellerFormSuccess\" id=\"contactSellerFormSuccess_2750007\" style=\"display:none\">\n" +
                "        <p>Thank You</p>\n" +
                "        <p>Your form has been submitted successfully. A representative will be in touch shortly</p>\n" +
                "    </div>\n" +
                "            \n" +
                "    <div class=\"confirmation contactSellerFormFailure\" id=\"contactSellerFormFailure_2750007\" style=\"display:none\">\n" +
                "        <p>Request cannot be completed</p>\n" +
                "        <p>You may have entered incorrect information or server is temporarily down. Please reload this page and try again later.</p>\n" +
                "    </div>\n" +
                "                    \n" +
                "    <div class=\"company contactSellerFormCompany\" id=\"contactSellerFormCompany_2750007\">The Multihull Company</div>";

        String cleanedHTML = scraperUtils.cleanHTML(uncleanHTML);

        Assert.assertTrue(!cleanedHTML.contains("\u0026"));
        Assert.assertTrue(!cleanedHTML.contains("\\n"));
        Assert.assertTrue(!cleanedHTML.contains("\\t"));
    }

}
