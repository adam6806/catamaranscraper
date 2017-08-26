package com.github.adam6806.catamaranscraper.main;

import com.github.adam6806.catamaranscraper.boatsite.BoatSite;
import com.github.adam6806.catamaranscraper.boatsite.BoatSiteFactory;
import com.github.adam6806.catamaranscraper.persistence.BoatEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        Log log = new SimpleLog("Main");

        //creating configuration object
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        //creating session factory object
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object
        Session session = factory.openSession();

        try {
            BoatSiteFactory boatSiteFactory = new BoatSiteFactory();
            List<BoatSite> boatSites = boatSiteFactory.getBoatSites();
            List<BoatEntity> newBoats = new ArrayList<>();
            for (BoatSite boatSite : boatSites) {
                saveBoatEntities(boatSite, newBoats, session, log);
            }
            if (!newBoats.isEmpty()) System.out.println(newBoats);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        System.exit(0);
    }

    private static void saveBoatEntities(BoatSite boatSite, List<BoatEntity> newBoats, Session session, Log log) {
        List<BoatEntity> boatEntities = boatSite.getBoatEntities();
        for (BoatEntity boatEntity : boatEntities) {
            Query query = session.createQuery("from BoatEntity where url = :url ");
            query.setParameter("url", boatEntity.getUrl());
            BoatEntity boatEntityQuery = (BoatEntity) query.uniqueResult();
            if (boatEntityQuery == null) {
                try {
                    session.save(boatEntity);
                    newBoats.add(boatEntity);
                } catch (Exception ex) {
                    log.error("Boat Entity already exists: " + boatEntity);
                }
            }
        }
    }
}
