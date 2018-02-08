package com.aloha.datamapping;

import com.aloha.datamapping.generator.InsertSqlGenerator;
import com.aloha.datamapping.sql.DBConfig;
import com.aloha.datamapping.sql.SqlExecutor;
import com.aloha.datamapping.sql.source.ClassPathSqlResource;
import com.aloha.datamapping.sql.source.SqlSource;
import com.aloha.datamapping.sql.source.StringSqlSource;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.Map;

@Component
public class PersonDatabaseMapping implements DatabaseMapping {

    @Override
    public void map() throws Exception {
        SqlExecutor sqlExecutor = new SqlExecutor(getSourceDBConfig(), getSqlSource());
        try (ResultSet resultSet = sqlExecutor.executeQuery()) {
            InsertSqlGenerator insertSqlGenerator = new InsertSqlGenerator(getTargetTableName(), getMapping());
            String sql = insertSqlGenerator.generate(resultSet);
            sqlExecutor = new SqlExecutor(getTargetDBConfig(), new StringSqlSource(sql));
            sqlExecutor.execute();
        }
    }

    @Override
    public SqlSource getSqlSource() {
        return new ClassPathSqlResource("mappingsql/person.sql");
    }

    @Override
    public Map<String, String> getMapping() {
        Map<String, String> map = Maps.newHashMap();
        map.put("name", "name_copy");
        return map;
    }

    public String getTargetTableName() {
        return "person_copy";
    }

    @Override
    public DBConfig getSourceDBConfig() {
        return DBConfig.MYSQL;
    }

    @Override
    public DBConfig getTargetDBConfig() {
        return DBConfig.MYSQL;
    }
}
