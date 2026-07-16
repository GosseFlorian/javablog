package fr.ada.java_blog.controller;

import fr.ada.java_blog.model.Media;
import fr.ada.java_blog.model.TypeMedia;

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
public class MediaController {
    private final JdbcTemplate jdbcTemplate;

    public MediaController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/médias")
    public List<Media> medias() {
        return jdbcTemplate.query(
                """
                        SELECT id, type, url
                        FROM médias
                        ORDER BY id
                        """,
                (rs, rowNum) -> {
                    return new Media(
                            rs.getInt("id"),
                            TypeMedia.valueOf(rs.getString("type")),
                            rs.getString("url"));
                });
    }

    @GetMapping("/médias/{id}")
    public Media mediaById(@PathVariable int id) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT id, type, url
                        FROM médias
                        WHERE id = ?
                        """,
                (rs, rowNum) -> {
                    return new Media(
                            rs.getInt("id"),
                            TypeMedia.valueOf(rs.getString("type")),
                            rs.getString("url"));
                }, id);
    }

    @PostMapping("/médias")
    public Media addMedia(@RequestBody Media addMedia) {
        jdbcTemplate.update(
                """
                        INSERT INTO médias
                        (type, url)
                        VALUES
                        (?::type, ?)
                        """,
                addMedia.getType().name(),
                addMedia.getUrl());
        return addMedia;
    }

    @PutMapping("/médias/{id}")
    public Media updateMedia(@PathVariable int id, @RequestBody Media updateMedia) {
        jdbcTemplate.update(
                """
                        UPDATE médias
                        SET type = ?::type, url = ?
                        WHERE id = ?
                        """,
                updateMedia.getType().name(),
                updateMedia.getUrl(),
                id);
        return updateMedia;
    }

    @PatchMapping("/médias/{id}")
    public Media patchMedia(@PathVariable int id, @RequestBody Media patchMedia) {
        jdbcTemplate.update(
                """
                        UPDATE médias
                        SET type = COALESCE(?::type, type),
                        url = COALESCE(?, url)
                        WHERE id = ?
                        """,
                patchMedia.getType().name(),
                patchMedia.getUrl(),
                id);
        return patchMedia;
    }

    @DeleteMapping("/médias/{id}")
    public void deleteMedia(@PathVariable int id) {
        jdbcTemplate.update(
                """
                        DELETE FROM médias
                        WHERE id = ?
                        """, id);
    }
}
