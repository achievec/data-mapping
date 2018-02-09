package com.aloha.datamapping.sql.source;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.nio.charset.Charset;

@Data
@AllArgsConstructor
public class FileSqlSource implements SqlSource {

    private String path;

    public String getSource() throws Exception {
        return FileUtils.readFileToString(new File(path), Charset.forName("utf-8"));
    }
}
