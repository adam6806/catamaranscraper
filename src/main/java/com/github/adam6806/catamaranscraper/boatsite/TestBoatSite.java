package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.persistence.BoatEntity;
import com.github.adam6806.catamaranscraper.persistence.ImageEntity;

import java.util.*;

public class TestBoatSite implements BoatSite {

    ArrayList<BoatEntity> boatEntities;
    Set<ImageEntity> imageEntities;

    @Override
    public List<BoatEntity> getBoatEntities() {
        BoatEntity boatEntity = new BoatEntity();
        boatEntity.setTimestamp(new java.sql.Date(new Date().getTime()));
        boatEntity.setUrl("http://www.google.com");
        boatEntity.setLocation("location");
        boatEntity.setYear(2017);
        boatEntity.setLength(40);
        boatEntity.setMakeModel("make and model");
        boatEntity.setPrice(175000);
        boatEntity.setDescription("This is a lengthy description");
        boatEntity.setActive(new Byte("1"));
        boatEntity.setSiteUrl("siteurl");
        boatEntities = new ArrayList<>();
        imageEntities = new HashSet<>();
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setBoat(boatEntity);
        imageEntity.setUrl("http://www.google.com");
        imageEntities.add(imageEntity);
        boatEntity.setImages(imageEntities);
        boatEntities.add(boatEntity);
        return boatEntities;
    }
}
