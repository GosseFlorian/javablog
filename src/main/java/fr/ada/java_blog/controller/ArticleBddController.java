package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.ArticleBdd;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ArticleBddController {

    private final JdbcTemplate jdbcTemplate;

    public ArticleBddController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/articles/recents")
    public List<ArticleBdd> cinqDerniers() {
        return jdbcTemplate.query(
                """
                        SELECT id, titre, contenu, statut, date
                        FROM articles
                        ORDER BY date DESC, id DESC
                        LIMIT 5
                        """,
                // rs = ligne courante (ResultSet)
                (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
                    return new ArticleBdd(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getBoolean("statut"),
                            date);
                });
    }

    @GetMapping("/articles/recents/count")
    public Integer countArticles() {
        Integer countArticles = jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*)
                        FROM articles
                        """,
                Integer.class);
        return countArticles;
    }

    @GetMapping("/articles/recents/publies")
    public List<ArticleBdd> cingDerniersPublies() {
        return jdbcTemplate.query(
                """
                        SELECT id, titre, contenu, statut, date
                        FROM articles
                        WHERE statut = true
                        ORDER BY date DESC, id DESC
                        LIMIT 5
                        """,
                (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
                    return new ArticleBdd(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getBoolean("statut"),
                            date);
                });
    }
}