package com.rc.repo;

import com.rc.entity.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepoPostgreTest {

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
            Optional<Item> saved = itemRepository.findItemByOwnerId(item.getOwnerId());
            saved.ifPresent(i -> System.out.println(i));
        });
    }

    @After
    public void clean() {
        itemRepository.delete(itemList.get(0));
    }
}
