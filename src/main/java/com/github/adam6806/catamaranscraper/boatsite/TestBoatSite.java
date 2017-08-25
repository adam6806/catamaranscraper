package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.dao.BoatEntity;
import com.github.adam6806.catamaranscraper.dao.ImageEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestBoatSite implements BoatSite {

    ArrayList<BoatEntity> boatEntities;
    ArrayList<ImageEntity> imageEntities;

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
        boatEntities = new ArrayList<>();
        imageEntities = new ArrayList<>();
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setBoatByBoat(boatEntity);
        imageEntity.setUrl("http://www.google.com");
        imageEntities.add(imageEntity);
        boatEntities.add(boatEntity);
        return boatEntities;
    }

    @Override
    public List<ImageEntity> getImageEntities() {
        return imageEntities;
    }
}
