package com.aloha.datamapping.generator;

import com.aloha.datamapping.mapping.DatabaseMapping;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.tomcat.util.buf.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class InsertSqlGenerator implements SqlGenerator {

    private String targetTableName;

    private Map<String, String> columnMapping;

    private List<Map<String, Object>> databaseResult;

    private LinkedList<String> targetColumnNames;

    private Map<String, Integer> columnMetaData;

    private DatabaseMapping databaseMapping;

    public InsertSqlGenerator(String targetTableName, Map<String, String> columnMapping, DatabaseMapping databaseMapping) {
        this.targetTableName = targetTableName;
        this.columnMapping = columnMapping;
        this.databaseMapping = databaseMapping;
    }

    public String generate(ResultSet resultSet) throws SQLException {
        this.columnMetaData = generateColumnMetaDate(resultSet);
        this.targetColumnNames = generateTargetColumnNames(columnMetaData);
        this.databaseResult = generateDatabaseResult(resultSet);

        String headers = getHeaderString();

        List<String> result = databaseResult.stream().map(row -> {
            this.databaseMapping.modifyRowValues(row);
            List<String> sql = Lists.newArrayList();
            for (String columnName : targetColumnNames) {
                sql.add(row.get(columnName).toString());
            }
            return "(" + StringUtils.join(sql, ',') + ")";
        }).collect(Collectors.toList());
        return result.stream().map(sql -> headers + sql + ";").reduce((sql1, sql2) -> sql1 + sql2).get();
    }

    private String getHeaderString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + getTargetTableName() + "( ");
        stringBuilder.append(StringUtils.join(targetColumnNames, ','));
        stringBuilder.append(") VALUES ");
        return stringBuilder.toString();
    }


    private LinkedList<String> generateTargetColumnNames(Map<String, Integer> columnMetaData) {
        LinkedList<String> columns = Lists.newLinkedList();
        columnMetaData.forEach((k, v) -> {
            columns.add(k);
        });
        return columns;
    }

    private List<Map<String, Object>> generateDatabaseResult(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> result = Lists.newArrayList();
        int columnCount = resultSet.getMetaData().getColumnCount();

        while (resultSet.next()) {
            Map<String, Object> row = Maps.newHashMap();
            for (int i = 0; i < columnCount; i++) {
                row.put(this.targetColumnNames.get(i), resultSet.getObject(i + 1));
            }
            result.add(row);
        }
        return result;
    }

    private Map<String, Integer> generateColumnMetaDate(ResultSet resultSet) throws SQLException {
        Map<String, String> mappingColumns = getColumnMapping();
        Map<String, Integer> result = Maps.newLinkedHashMap();
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            String columnName = resultSet.getMetaData().getColumnName(i + 1);
            if (mappingColumns != null) {
                String mappingColumn = mappingColumns.get(columnName);
                columnName = mappingColumn == null ? columnName : mappingColumn;
            }
            result.put(columnName, resultSet.getMetaData().getColumnType(i + 1));
        }
        return result;
    }

    @Override
    public String generate() throws Exception {
        return null;
    }
}
