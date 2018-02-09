package com.aloha.datamapping.mapping;

import com.aloha.datamapping.sql.source.ClassPathSqlResource;
import com.aloha.datamapping.sql.source.SqlSource;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PersonDatabaseMapping extends AbstractDatabaseMapping {

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

    @Override
    public String getTargetTableName() {
        return "person_copy";
    }

}
