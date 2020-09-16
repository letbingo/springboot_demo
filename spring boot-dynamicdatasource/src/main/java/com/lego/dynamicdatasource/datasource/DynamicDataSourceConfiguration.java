package com.lego.dynamicdatasource.datasource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于初始化数据源
 */
@Configuration
public class DynamicDataSourceConfiguration {
    public static String PRIMARY_DATASOURCE = "PRIMARY_DATASOURCE";
    public static String SECONDARY_DATASOURCE = "SECONDARY_DATASOURCE";

    @Bean(name = "primary")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDatasource() {
        return DataSourceBuilder.create().type(BasicDataSource.class).build();
    }

    @Bean(name = "secondary")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDatasource() {
        return DataSourceBuilder.create().type(BasicDataSource.class).build();
    }

    @Bean
    public AbstractRoutingDataSource proxyDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>(3);
        targetDataSources.put(PRIMARY_DATASOURCE, primaryDatasource());
        targetDataSources.put(SECONDARY_DATASOURCE, secondaryDatasource());

        DynamicDataSourceHolder holder = new DynamicDataSourceHolder();
        holder.setDefaultTargetDataSource(primaryDatasource());
        holder.setTargetDataSources(targetDataSources);
        return holder;
    }

    /**
     * DataSourceTransactionManager??
     *
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(proxyDataSource());
    }

    /**
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(proxyDataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
