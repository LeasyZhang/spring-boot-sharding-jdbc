package com.rc.repo;

import com.rc.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    public Optional<List<Relation>> findByTableName(String tableName);
}
