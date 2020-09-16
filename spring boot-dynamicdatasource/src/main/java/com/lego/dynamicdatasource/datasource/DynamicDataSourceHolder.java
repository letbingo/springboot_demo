package com.lego.dynamicdatasource.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.text.MessageFormat;

/**
 * 利用AbstractRoutingDataSource来聚合多个数据源（数据源设置的核心类）
 */
public class DynamicDataSourceHolder extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceType = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        logger.debug(MessageFormat.format("threadId: {0}, switch datasource: {1}",
                Thread.currentThread().getId(), dataSourceType.get()));
        return dataSourceType.get();
    }

    public static void setDataSourceType(String typeName) {
        dataSourceType.set(typeName);
    }
}
