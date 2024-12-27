package com.sportify.reservationapi;

import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DatabaseScriptRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseScriptRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SneakyThrows
    @Override
    public void run(String... args) {
  /*      Path scriptPath = new ClassPathResource("db.script").getFile().toPath();
        String sql = Files.readString(scriptPath);
        jdbcTemplate.execute(sql);*/

        ClassPathResource resource = new ClassPathResource("db.script");
        String sql = new String(resource.getInputStream().readAllBytes());
        jdbcTemplate.execute(sql);
    }
}

