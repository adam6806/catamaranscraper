package com.github.adam6806.catamaranscraper.boatsitescraperutils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ScraperUtils {
    private static Log logger = new SimpleLog(ScraperUtils.class.getName());
    public static String getTextByPattern(String extractionPattern, String src) {
        try{
            Pattern pattern = Pattern.compile(extractionPattern);

            Matcher matcher = pattern.matcher(src);

            if(matcher.find() && (matcher.groupCount() >= 1)) {
                return matcher.group(1);
            }
        } catch(PatternSyntaxException e) {
            logger.error("getTextByPattern Error: " + e.getMessage());
        }

        return "";
    }

    public static List<String> getTextArrayByPattern(String extractionPattern, String src) {
        List<String> matchedStrings = new ArrayList<>();
        try{
            Pattern pattern = Pattern.compile(extractionPattern);

            Matcher matcher = pattern.matcher(src);

            while(matcher.find()) {
                if(matcher.groupCount() >= 1) {
                    matchedStrings.add(matcher.group(1));
                }
            }
        }catch(PatternSyntaxException e) {
            logger.error("getTextArrayByPattern Error: " + e.getMessage());
        }

        return matchedStrings;
    }

    public static String cleanHTML(String html) {
        html = html.trim();
        html = html.replaceAll("\\n", "");
        html = html.replaceAll("\\t", "");
        html = html.replaceAll("\\u0026", "");
        html = html.replaceAll("nbsp;", " ");
        return html;
    }

    public static String cleanPrice(String price) {
        return price.replaceAll(",", "");
    }
}
