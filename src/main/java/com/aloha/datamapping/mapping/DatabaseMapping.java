package com.aloha.datamapping.mapping;

import com.aloha.datamapping.generator.SqlGenerator;
import com.aloha.datamapping.sql.DBConfig;
import com.aloha.datamapping.sql.source.SqlSource;

import java.util.Map;

public interface DatabaseMapping extends Mapping {

    SqlSource getSqlSource();

    default Map<String, String> getMapping() {
        return null;
    }

    String getTargetTableName();

    DBConfig getSourceDBConfig();

    DBConfig getTargetDBConfig();

    SqlGenerator getSqlGenerator();

    void modifyRowValues(Map<String, Object> row);
}
