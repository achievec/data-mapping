package com.aloha.datamapping.sql.source;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class StringSqlSource implements SqlSource {

    @Setter
    private String source;

    @Override
    public String getSource() throws Exception {
        return source;
    }
}
