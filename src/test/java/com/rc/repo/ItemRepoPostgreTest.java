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
        item.setOwnerId(1112300104);
        item.setUsername(UUID.randomUUID().toString());

        itemList.add(itemRepository.save(item));
    }

    @Test
    public void testQuery() {
        itemList.stream().forEach(item -> {
            List<Item> saved = itemRepository.findAll();
            saved.stream().forEach(item1 -> System.out.println(item1));
        });
    }

    @After
    public void clean() {
        //itemRepository.delete(itemList.get(0));
    }
}
