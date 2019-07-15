package com.rc.repo;

import com.rc.entity.Relation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class RelationRepoPostgreTest extends BaseTest {

    @Autowired
    private RelationRepository relationRepository;

    private Relation entity;

    @Before
    public void setUp() {
        entity = new Relation();
        entity.setItemId(12);
        entity.setTableName("relation");
        entity.setCreatedTime(Instant.now());
        entity.setUpdatedTime(Instant.now());

        relationRepository.save(entity);
    }

    @Test
    public void testFindByItemId() {
        Relation relation = relationRepository.findByItemId(12).get(0);
        Assert.assertEquals(12, relation.getItemId());
        Assert.assertEquals("relation", relation.getTableName());
    }

    @After
    public void clean() {
        relationRepository.delete(entity);
    }
}
