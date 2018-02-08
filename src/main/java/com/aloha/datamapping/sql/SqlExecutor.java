package com.aloha.datamapping.sql;

import com.aloha.datamapping.sql.source.SqlSource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;

@Data
@AllArgsConstructor
public class SqlExecutor {

    private DBConfig dbConfig;
    private SqlSource sqlSource;

    public ResultSet executeQuery() throws Exception {
        Connection connection = ConnectionFactory.getConnection(dbConfig);
        return connection.prepareStatement(sqlSource.getSource()).executeQuery();

    }

    public boolean execute() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection(dbConfig)) {
            return connection.createStatement().execute(sqlSource.getSource());
        }
    }

}
