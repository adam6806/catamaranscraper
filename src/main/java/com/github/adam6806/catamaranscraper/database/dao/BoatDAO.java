package com.github.adam6806.catamaranscraper.database.dao;

import com.github.adam6806.catamaranscraper.database.model.BoatEntity;
import org.hibernate.query.Query;

import java.util.List;

public class BoatDAO extends AbstractDAO<BoatEntity> {

    @Override
    public void save(BoatEntity entity) {
        getSession().save(entity);
    }

    @Override
    public void update(BoatEntity entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(BoatEntity entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        List<BoatEntity> entities = findAll();
        for (BoatEntity entity : entities) {
            delete(entity);
        }
    }

    @Override
    public BoatEntity findById(int id) {
        return getSession().get(BoatEntity.class, id);
    }

    @Override
    public List<BoatEntity> findAll() {
        return getSession().createQuery("from BoatEntity").list();
    }

    public BoatEntity findByUrl(String url) {
        Query query = getSession().createQuery("from BoatEntity where url = :url");
        query.setParameter("url", url);
        return (BoatEntity) query.uniqueResult();
    }
}
