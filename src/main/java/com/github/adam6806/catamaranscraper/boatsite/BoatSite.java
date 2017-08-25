package com.github.adam6806.catamaranscraper.boatsite;

import com.github.adam6806.catamaranscraper.dao.BoatEntity;
import com.github.adam6806.catamaranscraper.dao.ImageEntity;

import java.util.List;

public interface BoatSite {

    public List<BoatEntity> getBoatEntities();
    public List<ImageEntity> getImageEntities();
}
