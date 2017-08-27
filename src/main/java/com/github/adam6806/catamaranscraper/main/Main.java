package com.github.adam6806.catamaranscraper.main;

import com.github.adam6806.catamaranscraper.boatsite.BoatSite;
import com.github.adam6806.catamaranscraper.boatsite.BoatSiteFactory;
import com.github.adam6806.catamaranscraper.database.model.BoatEntity;
import com.github.adam6806.catamaranscraper.database.service.BoatService;
import com.github.adam6806.catamaranscraper.email.EmailHtmlGenerator;
import com.github.adam6806.catamaranscraper.email.EmailSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Log log = new SimpleLog(Main.class.getName());

        BoatService boatService = new BoatService();
        BoatSiteFactory boatSiteFactory = new BoatSiteFactory();
        List<BoatSite> boatSites = boatSiteFactory.getBoatSites();
        List<BoatEntity> newBoats = new ArrayList<>();
        for (BoatSite boatSite : boatSites) {
            saveBoatEntities(boatSite, newBoats, boatService, log);
        }
        if (!newBoats.isEmpty()) {
            System.out.println(newBoats);
            EmailSender.sendEmail(EmailHtmlGenerator.generateHTML(newBoats), "asmith0935@gmail.com");
        }

        System.exit(0);
    }

    private static void saveBoatEntities(BoatSite boatSite, List<BoatEntity> newBoats, BoatService boatService, Log log) {
        List<BoatEntity> boatEntities = boatSite.getBoatEntities();
        for (BoatEntity boatEntity : boatEntities) {
            BoatEntity boatEntityQuery = boatService.findByUrl(boatEntity.getUrl());
            if (boatEntityQuery == null) {
                boatService.save(boatEntity);
                newBoats.add(boatEntity);
            }
        }
    }
}