package com.aloha.datamapping.mapping;

import com.aloha.datamapping.generator.InsertSqlGenerator;
import com.aloha.datamapping.generator.OracleInsertSqlGenerator;
import com.aloha.datamapping.sql.DBConfig;
import com.aloha.datamapping.sql.SqlExecutor;
import com.aloha.datamapping.sql.source.SqlSource;
import com.aloha.datamapping.sql.source.StringSqlSource;

import java.sql.ResultSet;
import java.util.Map;

public abstract class AbstractDatabaseMapping implements DatabaseMapping {

    @Override
    public void map() throws Exception {
        SqlExecutor sqlExecutor = new SqlExecutor(getSourceDBConfig(), getSqlSource());
        try (ResultSet resultSet = sqlExecutor.executeQuery()) {
            String sql = getSqlGenerator().generate(resultSet);
            sqlExecutor = new SqlExecutor(getTargetDBConfig(), new StringSqlSource(sql));
            sqlExecutor.execute();
        }
    }

    @Override
    public DBConfig getSourceDBConfig() {
        return DBConfig.MYSQL;
    }

    @Override
    public DBConfig getTargetDBConfig() {
        return DBConfig.ORACLE;
    }

    @Override
    public abstract String getTargetTableName();

    @Override
    public abstract SqlSource getSqlSource();

    @Override
    public InsertSqlGenerator getSqlGenerator() {
        return new OracleInsertSqlGenerator(getTargetTableName(), getMapping(), this);
    }

    @Override
    public void modifyRowValues(Map<String, Object> row) {
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            if (entry.getValue() instanceof String) {
                row.put(entry.getKey(), "\'" + entry.getValue() + "\'");
            }
        }
    }
}
