package com.aloha.datamapping.generator;

import com.aloha.datamapping.utils.Try;
import com.mysql.cj.core.MysqlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.tomcat.util.buf.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
public class InsertSqlGenerator implements SqlGenerator {

    private String tableName;

    private Map<String, String> columnMapping;

    public String generate(ResultSet resultSet) throws SQLException {
        List<String> sourceColumns = IntStream.rangeClosed(1, resultSet.getMetaData().getColumnCount()).boxed()
                .map(Try.of(i -> resultSet.getMetaData().getColumnName(i))).collect(Collectors.toList());
        Map<String, String> mappingColumns = getColumnMapping();
        List<String> targetColumns;
        if (mappingColumns == null) {
            targetColumns = sourceColumns;
        } else {
            targetColumns = sourceColumns.stream().map(name -> {
                String result = mappingColumns.get(name);
                return result == null ? name : result;
            }).collect(Collectors.toList());
        }

        StringBuilder sb = new StringBuilder("INSERT INTO " + getTableName() + "( ");
        sb.append(StringUtils.join(targetColumns, ','));
        sb.append(") VALUES ");

        String headers = sb.toString();

        List<String> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(getRowString(resultSet));
        }
        return result.stream().map(sql -> headers + sql + ";").reduce((sql1, sql2) -> sql1 + sql2).get();
    }

    private String getRowString(ResultSet resultSet) {
        try {
            int count = resultSet.getMetaData().getColumnCount();
            List<String> result = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                int type = resultSet.getMetaData().getColumnType(i);
                if (type == MysqlType.VARCHAR.getJdbcType()) {
                    result.add("\'" + resultSet.getObject(i) + "\'");
                } else {
                    result.add(resultSet.getObject(i).toString());
                }
            }
            return "(" + StringUtils.join(result) + ")";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generate() throws Exception {
        return null;
    }
}
