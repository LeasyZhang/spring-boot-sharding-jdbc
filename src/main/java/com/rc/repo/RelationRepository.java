package com.rc.repo;

import com.rc.entity.Relation;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class RelationRepository extends BaseRepository{

    public Relation save(Relation entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public List<Relation> findByItemId(long itemId) {
        List<Relation> relations = getSession()
                .createQuery("from Relation where itemId = :itemId", Relation.class)
                .setParameter("itemId", itemId)
                .getResultList();
        return relations;
    }

    public void delete(Relation entity) {
        getSession().delete(entity);
    }
}
