package com.sportify.reservationservice;

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

    @Override
    public void run(String... args) throws Exception {
        // db.script dosyasını oku
        Path scriptPath = new ClassPathResource("db.script").getFile().toPath();
        String sql = Files.readString(scriptPath);

        // SQL komutlarını çalıştır
        jdbcTemplate.execute(sql);

        System.out.println("db.script başarıyla çalıştırıldı.");
    }
}

