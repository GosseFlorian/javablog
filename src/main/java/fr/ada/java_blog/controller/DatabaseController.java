package fr.ada.java_blog.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseController {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/db/ping")
    public String ping() {
        Integer nombreArticles = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM articles",
                Integer.class);
        Integer nombreUtilisateur = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users",
                Integer.class);
        return "Connexion ok - " + nombreUtilisateur + " Utilisateur(s) en base et " + nombreArticles
                + " Article(s) en base";
    }

    @GetMapping("/db/version")
    public String version() {
        String versionSql = jdbcTemplate.queryForObject(
                "SELECT version()",
                String.class);
        return "Ma version : " + versionSql;
    }

}