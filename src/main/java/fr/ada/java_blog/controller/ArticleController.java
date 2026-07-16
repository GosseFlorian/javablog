package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.Article;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ArticleController {

    private final JdbcTemplate jdbcTemplate;

    public ArticleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/articles")
    public List<Article> articles() {
        return jdbcTemplate.query(
                """
                        SELECT
                        id, titre, contenu, statut, date
                        FROM articles
                        ORDER BY id
                        """,
                (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
                    return new Article(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getBoolean("statut"),
                            date);
                });
    }

    @GetMapping("/articles/{id}")
    public Article articleById(@PathVariable int id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT
                        id, titre, contenu, statut, date
                        FROM articles
                        WHERE id = ?
                        """,
                (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
                    return new Article(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getBoolean("statut"),
                            date);
                }, id);
    }

    @PostMapping("/articles")
    public Article addArticle(@RequestBody Article addArticle) {
        jdbcTemplate.update(
                """
                        INSERT INTO articles
                        (titre, contenu, statut)
                        VALUES
                        (?, ?, ?)
                        """,
                addArticle.getTitre(),
                addArticle.getContenu(),
                addArticle.isPublie());
        return addArticle;
    }

    @PutMapping("/articles/{id}")
    public Article updateArticle(@PathVariable int id, @RequestBody Article updateArticle) {
        jdbcTemplate.update(
                """
                        UPDATE articles
                        SET titre = ?, contenu = ?, statut = ?
                        WHERE id = ?
                        """,
                updateArticle.getTitre(),
                updateArticle.getContenu(),
                updateArticle.isPublie(),
                id);
        return updateArticle;
    }

    @PatchMapping("/articles/{id}")
    public Article patchArticle(@PathVariable int id, @RequestBody Article patchArticle) {
        jdbcTemplate.update(
                """
                        UPDATE articles
                        SET titre = COALESCE(?, titre),
                        contenu = COALESCE(?, contenu),
                        statut = COALESCE(?, statut)
                        WHERE id = ?
                        """,
                patchArticle.getTitre(),
                patchArticle.getContenu(),
                patchArticle.isPublie(),
                id);
        return patchArticle;
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable int id) {
        jdbcTemplate.update(
                """
                        DELETE FROM articles
                        WHERE id = ?
                        """, id);
    }

    @GetMapping("/articles/recents")
    public List<Article> cinqDerniers() {
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
                    return new Article(
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
    public List<Article> cingDerniersPublies() {
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
                    return new Article(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getBoolean("statut"),
                            date);
                });
    }
}