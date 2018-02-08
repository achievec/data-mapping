package com.aloha.datamapping;

import com.aloha.datamapping.sql.DBConfig;
import com.aloha.datamapping.sql.source.SqlSource;

import java.util.Map;

public interface DatabaseMapping extends Mapping {

    SqlSource getSqlSource();

    Map<String, String> getMapping();

    String getTargetTableName();

    DBConfig getSourceDBConfig();

    DBConfig getTargetDBConfig();
}
