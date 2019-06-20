package com.rc.config;

import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Autowired
    private Database0Config database0Config;

    @Autowired
    private Database1Config database1Config;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(getItemTableConfiguration());
        shardingRuleConfiguration.getBindingTableGroups().add("sh_item");
        shardingRuleConfiguration.setDefaultDataSourceName("sharding_db0");
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("owner_id", "sharding_db${owner_id % 2}"));
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("owner_id", "sh_item_${owner_id % 4}"));

        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(database0Config.getDatabaseName(), database0Config.createDataSource());
        dataSourceMap.put(database1Config.getDatabaseName(), database1Config.createDataSource());

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new HashMap<>(), new Properties());
    }

    TableRuleConfiguration getItemTableConfiguration() {
        TableRuleConfiguration config = new TableRuleConfiguration();
        config.setLogicTable("sh_item");
        config.setActualDataNodes("sharding_db${0..1}.sh_item_${0..3}");
        return config;
    }
}
