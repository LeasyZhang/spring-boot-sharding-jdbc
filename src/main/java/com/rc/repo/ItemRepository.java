package com.rc.repo;

import com.rc.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author joe.zhang
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findItemByOwnerId(long ownerId);
}
