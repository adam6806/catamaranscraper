package com.github.adam6806.catamaranscraper.main;

import com.github.adam6806.catamaranscraper.boatsite.BoatSite;
import com.github.adam6806.catamaranscraper.boatsite.BoatSiteFactory;
import com.github.adam6806.catamaranscraper.dao.BoatEntity;
import com.github.adam6806.catamaranscraper.dao.ImageEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        //creating configuration object
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");//populates the data of the configuration file

        //creating session factory object
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object
        Session session = factory.openSession();

        //creating transaction object
        Transaction t = session.beginTransaction();

        try {
            BoatSiteFactory boatSiteFactory = new BoatSiteFactory();
            List<BoatSite> boatSites = boatSiteFactory.getBoatSites();
            for (BoatSite boatSite : boatSites) {
                List<BoatEntity> boatEntities = boatSite.getBoatEntities();
                for (BoatEntity boatEntity : boatEntities) {
                    session.saveOrUpdate(boatEntity);
                }
                List<ImageEntity> imageEntities = boatSite.getImageEntities();
                for (ImageEntity imageEntity : imageEntities) {
                    session.saveOrUpdate(imageEntity);//persisting the object
                }
            }
            t.commit();//transaction is commited
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }
}
