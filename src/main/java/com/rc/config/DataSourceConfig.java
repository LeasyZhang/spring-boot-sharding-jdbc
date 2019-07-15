package com.rc.config;

import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.NoneShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

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

    @Autowired
    private Environment environment;

    @Bean
    public DataSource getDataSource() throws SQLException {
        return buildDataSource();
    }

    private DataSource buildDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(getItemTableConfiguration());
        shardingRuleConfiguration.getBindingTableGroups().add("sh_item");
        shardingRuleConfiguration.setDefaultDataSourceName("sharding_db0");
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put(database0Config.getDatabaseName(), database0Config.createDataSource());

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new HashMap<>(), new Properties());
    }

    TableRuleConfiguration getItemTableConfiguration() {
        TableRuleConfiguration config = new TableRuleConfiguration();
        config.setLogicTable("sh_item");
        config.setActualDataNodes("sharding_db0.sh_item_${0..3}");
        config.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("owner_id", new ItemShardingStrategy()));
        return config;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws SQLException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setPackagesToScan(new String[]{"com.rc"});
        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }
}
