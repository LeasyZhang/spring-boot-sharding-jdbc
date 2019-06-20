package com.rc.config;

import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class MasterDataSourceConfig {

    private Database0Config masterDataBase;

    private Database1Config slaveDatabase0;

    private Database1Config slaveDatabase1;

    public DataSource getDataSource() throws SQLException {
        return buildMasterSlaveDataSource();
    }

    public DataSource buildMasterSlaveDataSource() throws SQLException {
        // Configure actual data sources
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        dataSourceMap.put("ds_master", masterDataBase.createDataSource());

        dataSourceMap.put("ds_slave0", slaveDatabase0.createDataSource());
        dataSourceMap.put("ds_slave1", slaveDatabase1.createDataSource());

        // Configure read-write split rule
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("ds_master_slave", "ds_master", Arrays.asList("ds_slave0", "ds_slave1"));

        // Get data source
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, new HashMap<>(), new Properties());
        return dataSource;
    }

    TableRuleConfiguration getItemTableConfiguration() {
        TableRuleConfiguration config = new TableRuleConfiguration();
        config.setLogicTable("sh_item");
        config.setActualDataNodes("sharding_db${0..1}.sh_item_${0..3}");
        return config;
    }
}
