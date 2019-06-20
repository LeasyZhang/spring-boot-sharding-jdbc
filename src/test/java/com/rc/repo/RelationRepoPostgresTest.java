package com.rc.repo;


import com.rc.entity.Relation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class RelationRepoPostgresTest extends BaseTest{

    @Autowired
    private RelationRepository relationRepository;

    private Relation relation;

    @Before
    public void setUp() {
        relation = new Relation();
        relation.setItemId(10L);
        relation.setTableName("sh_item_0");
        relation.setCreatedTime(Instant.now());
        relation.setUpdatedTime(Instant.now());
        relationRepository.save(relation);
    }

    @Test
    public void testQuery() {
        Optional<List<Relation>> results = relationRepository.findByTableName("sh_item_0");
        results.ifPresent(relations -> {
            relations.stream().forEach(item -> System.out.println(item));
        });
    }

    @After
    public void clean() {
        relationRepository.delete(relation);
    }
}
