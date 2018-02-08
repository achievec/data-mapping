package com.aloha.datamapping.sql.source;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileSqlSource implements SqlSource {

    private String path;

    public String getSource() throws Exception {
        return IOUtils.toString(new ClassPathResource(path).getInputStream());
    }
}
