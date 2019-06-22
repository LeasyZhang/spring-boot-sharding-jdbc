package com.rc.repo;

import com.rc.entity.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.sql.*;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author joe.zhang
 */
@Component
@Transactional
public class ItemRepository extends BaseRepository {

    public Optional<List<Item>> findItemByOwnerId(long ownerId) {
        List<Item> itemList = getSession().createNativeQuery("select * from sh_item where owner_id = :ownerId")
                .setParameter("ownerId", ownerId)
                .getResultList();

        return Optional.ofNullable(itemList);
    }

    @Modifying
    public Item save(Item entity) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into sh_item(owner_id, content, username, created_time, updated_time)")
                .append("values(?, ?, ?, ?, ?)")
                .append(" on conflict do nothing");

        Item result = getSession().doReturningWork(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, entity.getOwnerId());
            ps.setString(2, entity.getContent());
            ps.setString(3, entity.getUsername());
            ps.setTimestamp(4, Timestamp.from(entity.getCreatedTime()));
            ps.setTimestamp(5, Timestamp.from(entity.getUpdatedTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }
            return entity;
        });
        return result;
    }

    @Modifying
    public List<Item> bulkSave(List<Item> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            return Collections.emptyList();
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into sh_item(owner_id, content, username, created_time, updated_time)")
                .append("values(?, ?, ?, ?, ?)")
                .append(" on conflict do nothing");

        List<Item> resultList = getSession().doReturningWork(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
            try {
                for(Item item : itemList){
                    ps.setLong(1, item.getOwnerId());
                    ps.setString(2, item.getContent());
                    ps.setString(3, item.getUsername());
                    ps.setTimestamp(4, Timestamp.from(item.getCreatedTime()));
                    ps.setTimestamp(5, Timestamp.from(item.getUpdatedTime()));
                    ps.executeUpdate();
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        item.setId(rs.getLong(1));
                    }
                }
            }finally {
                ps.close();
            }

            return itemList;
        });

        return resultList;
    }


    public List<Item> findAll() {
        return getSession().createNativeQuery("select * from sh_item limit 50", Item.class)
                .getResultList();
    }

    @Modifying
    public void delete(Item entity) {
        getSession().delete(entity);
    }
}
