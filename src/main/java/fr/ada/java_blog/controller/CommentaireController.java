package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.Commentaire;

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
public class CommentaireController {
    private final JdbcTemplate jdbcTemplate;

    public CommentaireController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/commentaires")
    public List<Commentaire> commentaires() {
        return jdbcTemplate.query(
                """
                        SELECT id, contenu, date
                        FROM commentaires
                        ORDER BY id
                        """,
                (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
                    return new Commentaire(
                            rs.getInt("id"),
                            rs.getString("contenu"),
                            date);
                });
    }

    @GetMapping("/commentaires/{id}")
    public Commentaire commentaireById(@PathVariable int id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT id, contenu, date
                        FROM commentaires
                        WHERE id = ?
                             """, (rs, rowNum) -> {
                    Timestamp timestamp = rs.getTimestamp("date");
                    LocalDateTime date = timestamp != null ? timestamp.toLocalDateTime() : null;
                    return new Commentaire(
                            rs.getInt("id"),
                            rs.getString("contenu"),
                            date);
                }, id);
    }

    @PostMapping("/commentaires")
    public Commentaire addCommentaire(@RequestBody Commentaire addCommentaire) {
        jdbcTemplate.update(
                """
                        INSERT INTO commentaires
                        (contenu)
                        VALUES
                        (?)
                        """,
                addCommentaire.getContenu());
        return addCommentaire;
    }

    @PutMapping("/commentaires/{id}")
    public Commentaire updateCommentaire(@PathVariable int id, @RequestBody Commentaire updateCommentaire) {
        jdbcTemplate.update(
                """
                        UPDATE commentaires
                        SET contenu = ?
                        WHERE id = ?
                        """,
                updateCommentaire.getContenu(),
                id);
        return updateCommentaire;
    }

    @PatchMapping("/commentaires/{id}")
    public Commentaire patchCommentaire(@PathVariable int id, @RequestBody Commentaire patchCommentaire) {
        jdbcTemplate.update(
                """
                        UPDATE commentaires
                        SET contenu = COALESCE(?, contenu)
                        WHERE id = ?
                        """,
                patchCommentaire.getContenu(),
                id);
        return patchCommentaire;
    }

    @DeleteMapping("/commentaires/{id}")
    public void deleteCommentaire(@PathVariable int id) {
        jdbcTemplate.update(
                """
                        DELETE FROM commentaires
                        WHERE id = ?
                        """, id);
    }
}