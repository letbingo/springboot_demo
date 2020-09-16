package com.lego.dynamicdatasource.datasource;

/**
 * 管理所有数据源的标识
 */
public enum DynamicDataSourceType {
    primary(DynamicDataSourceConfiguration.PRIMARY_DATASOURCE),
    secondary(DynamicDataSourceConfiguration.SECONDARY_DATASOURCE);

    private String value;

    DynamicDataSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
