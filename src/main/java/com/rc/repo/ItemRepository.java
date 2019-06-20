package com.rc.repo;

import com.rc.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author joe.zhang
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<List<Item>> findItemByOwnerId(long ownerId);
}
