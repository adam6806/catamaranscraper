package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.database.model.BoatEntity;
import com.github.adam6806.catamaranscraper.database.model.ImageEntity;

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
        boatEntity.setSiteUrl("http://www.google.com");
        boatEntities = new ArrayList<>();
        imageEntities = new HashSet<>();
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setBoat(boatEntity);
        imageEntity.setUrl("http://newimages.yachtworld.com/resize/1/33/56/4883356_20170817151932287_1_XLARGE.jpg?f=/1/33/56/4883356_20170817151932287_1_XLARGE.jpg&w=924&h=693&t=1503012043000");
        imageEntities.add(imageEntity);
        boatEntity.setImages(imageEntities);
        boatEntities.add(boatEntity);
        return boatEntities;
    }
}
