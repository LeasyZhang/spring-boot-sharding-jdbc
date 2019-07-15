package com.rc.repo;

import com.rc.entity.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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
        item.setOwnerId(1112300180);
        item.setUsername(UUID.randomUUID().toString());

        itemList.add(itemRepository.save(item));
    }

    @Test
    public void testQuery() {
        List<Item> saved = itemRepository.findAll();
        saved.forEach(item -> {
            System.out.println(item);
        });
    }

    @Test
    public void testBulkSave() {
        List<Item> items = new ArrayList<>();
        for(int i = 1; i < 10; i ++) {
            Item item = new Item();
            item.setContent("test content x");
            item.setCreatedTime(Instant.now());
            item.setUpdatedTime(Instant.now());
            item.setOwnerId(1112300130 + i);
            item.setUsername(UUID.randomUUID().toString());
            items.add(item);
        }
        List<Item> results = itemRepository.bulkSave(items);
        results.stream().forEach(item -> System.out.println(item));
    }

    @After
    public void clean() {
        if (!CollectionUtils.isEmpty(itemList)) {
            itemList.stream().forEach(item -> itemRepository.delete(item));
        }
    }
}
