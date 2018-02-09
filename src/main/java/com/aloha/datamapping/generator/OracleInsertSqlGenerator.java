package com.aloha.datamapping.generator;

import com.aloha.datamapping.mapping.DatabaseMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class OracleInsertSqlGenerator extends InsertSqlGenerator {

    public OracleInsertSqlGenerator(String tableName, Map<String, String> columnMapping, DatabaseMapping databaseMapping) {
        super(tableName, columnMapping, databaseMapping);
    }

    @Override
    public String generate(ResultSet resultSet) throws SQLException {
        return "begin " + super.generate(resultSet) + " end;";
    }

}
