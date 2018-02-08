package com.aloha;

import com.aloha.datamapping.DatabaseMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    List<DatabaseMapping> mappings;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        mappings.stream().forEach(mapping -> {
            try {
                mapping.map();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

    }
}
