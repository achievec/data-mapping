package com.aloha.datamapping.sql.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

@Data
@AllArgsConstructor
public class ClassPathSqlResource implements SqlSource {

    String path;

    @Override
    public String getSource() throws Exception {
        return IOUtils.toString(new ClassPathResource(path).getInputStream());
    }
}
