package com.github.adam6806.catamaranscraper.database.service;

import com.github.adam6806.catamaranscraper.database.dao.BoatDAO;
import com.github.adam6806.catamaranscraper.database.model.BoatEntity;

import java.util.List;

public class BoatService implements Service<BoatEntity> {

    private BoatDAO boatDAO = new BoatDAO();

    @Override
    public void save(BoatEntity entity) {
        boatDAO.openSessionWithTransaction();
        boatDAO.save(entity);
        boatDAO.closeSessionWithTransaction();
    }

    @Override
    public void update(BoatEntity entity) {
        boatDAO.openSessionWithTransaction();
        boatDAO.update(entity);
        boatDAO.closeSessionWithTransaction();
    }

    @Override
    public void delete(BoatEntity entity) {
        boatDAO.openSessionWithTransaction();
        boatDAO.delete(entity);
        boatDAO.closeSessionWithTransaction();
    }

    @Override
    public void deleteAll() {
        boatDAO.openSessionWithTransaction();
        boatDAO.deleteAll();
        boatDAO.closeSessionWithTransaction();
    }

    @Override
    public BoatEntity findById(int id) {

        boatDAO.openSession();
        BoatEntity entity = boatDAO.findById(id);
        boatDAO.closeSession();
        return entity;
    }

    @Override
    public List<BoatEntity> findAll() {
        boatDAO.openSession();
        List<BoatEntity> entities = boatDAO.findAll();
        boatDAO.closeSession();
        return entities;
    }

    public BoatEntity findByUrl(String url) {
        boatDAO.openSession();
        BoatEntity entity = boatDAO.findByUrl(url);
        boatDAO.closeSession();
        return entity;
    }
}
