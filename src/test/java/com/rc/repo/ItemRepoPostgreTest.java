package com.rc.repo;

import com.rc.entity.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class ItemRepoPostgreTest extends BaseTest {

    @Autowired
    private ItemRepository itemRepository;

    private List<Item> itemList = new ArrayList<>();

    @Before
    public void setUp() {
        Item item = new Item();
        item.setContent("test content ");
        item.setCreatedTime(Instant.now());
        item.setUpdatedTime(Instant.now());
        item.setOwnerId(1112300103);
        item.setUsername(UUID.randomUUID().toString());

        itemList.add(itemRepository.save(item));
    }

    @Test
    public void testQuery() {
        itemList.stream().forEach(item -> {
            Optional<List<Item>> saved = itemRepository.findItemByOwnerId(item.getOwnerId());
            saved.ifPresent(items -> {
                items.stream().forEach(i -> System.out.println(i));
            });
        });
    }

    @After
    public void clean() {
        itemRepository.delete(itemList.get(0));
    }
}
