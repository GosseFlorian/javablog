package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.Categorie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
public class CategorieController {
    private final JdbcTemplate jdbcTemplate;

    public CategorieController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/catégories")
    public List<Categorie> categories() {
        return jdbcTemplate.query(
                """
                        SELECT id, nom, description
                        FROM catégories
                        ORDER BY id
                        """,
                (rs, rowNum) -> {
                    return new Categorie(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"));
                });
    }

    @GetMapping("/catégories/{id}")
    public Categorie categorieById(@PathVariable int id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT id, nom, description
                        FROM catégories
                        WHERE id = ?
                        """,
                (rs, rowNum) -> {
                    return new Categorie(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("description"));
                }, id);
    }

    @PostMapping("/catégories")
    public Categorie addCategorie(@RequestBody Categorie addCategorie) {
        jdbcTemplate.update(
                """
                        INSERT INTO catégories
                        (nom, description)
                        VALUES
                        (?, ?)
                        """,
                addCategorie.getNom(),
                addCategorie.getDescription());
        return addCategorie;
    }

    @PutMapping("/catégories/{id}")
    public Categorie updateCategorie(@PathVariable int id, @RequestBody Categorie updateCategorie) {
        jdbcTemplate.update(
                """
                        UPDATE catégories
                        SET nom = ?, description = ?
                        WHERE id = ?
                        """,
                updateCategorie.getNom(),
                updateCategorie.getDescription(),
                id);
        return updateCategorie;
    }

    @PatchMapping("/catégories/{id}")
    public Categorie patchCategorie(@PathVariable int id, @RequestBody Categorie patchCategorie) {
        jdbcTemplate.update(
                """
                        UPDATE catégories
                        SET nom = COALESCE(?, nom),
                        description = COALESCE(?, description)
                        WHERE id = ?
                        """,
                patchCategorie.getNom(),
                patchCategorie.getDescription(),
                id);
        return patchCategorie;
    }

    @DeleteMapping("/catégories/{id}")
    public void deleteCategorie(@PathVariable int id) {
        jdbcTemplate.update(
                """
                        DELETE FROM catégories
                        WHERE id = ?
                        """, id);
    }
}
