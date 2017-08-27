package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.database.model.BoatEntity;

import java.util.List;

public interface BoatSite {

    public List<BoatEntity> getBoatEntities();
}
